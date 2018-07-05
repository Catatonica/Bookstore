package izenka.hfad.com.bookstore.category;


import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.paging.PagedList;

import java.util.concurrent.Executors;

import izenka.hfad.com.bookstore.MainThreadExecutor;
import izenka.hfad.com.bookstore.model.db_classes.Book;

public class CategorizedBooksViewModel extends ViewModel {

    private int categoryID;
    private CategoryNavigator navigator;
    private MutableLiveData<PagedList<Book>> bookPagedListLiveData;

    public void setCategoryID(int categoryID) {
        this.categoryID = categoryID;
    }

    public void setNavigator(CategoryNavigator navigator) {
        this.navigator = navigator;
    }

    public void onBookClicked(Book book) {
        navigator.onBookClicked(book.getBook_id());
    }

    void onSearchInCategoryClicked() {
        navigator.onSearchInCategoryClicked();
    }

    MutableLiveData<PagedList<Book>> getBookListLiveData() {
        if (bookPagedListLiveData == null) {
            bookPagedListLiveData = new MutableLiveData<>();

            CategorizedBooksDataSource dataSource = new CategorizedBooksDataSource(categoryID);

            PagedList.Config config = new PagedList.Config.Builder()
                    .setEnablePlaceholders(false)
                    .setInitialLoadSizeHint(4)
                    .setPageSize(4)
                    .build();

            PagedList<Book> bookList = new PagedList.Builder<>(dataSource, config)
                    .setFetchExecutor(Executors.newSingleThreadExecutor())
                    .setNotifyExecutor(new MainThreadExecutor())
                    .build();

            bookPagedListLiveData.postValue(bookList);
        }
        return bookPagedListLiveData;
    }
}
