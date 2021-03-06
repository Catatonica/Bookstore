package izenka.hfad.com.bookstore.search;


import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import java.util.List;

import izenka.hfad.com.bookstore.DatabaseSingleton;
import izenka.hfad.com.bookstore.model.db_classes.Book;

public class SearchViewModel extends ViewModel {

    private MutableLiveData<List<Book>> bookListLiveData;
    private MutableLiveData<List<Book>> categorizedBookListLiveData;

    private SearchNavigator navigator;

    public void setNavigator(SearchNavigator navigator) {
        this.navigator = navigator;
    }

    MutableLiveData<List<Book>> getBookListLiveData(String searchedText) {
        if (bookListLiveData == null) {
            bookListLiveData = new MutableLiveData<>();
        }
        DatabaseSingleton.getInstance().getSearchedBookList(searchedText, bookList -> {
            bookListLiveData.postValue(bookList);
        });
        return bookListLiveData;
    }

    MutableLiveData<List<Book>> getBookListLiveData(String categoryID, String searchedText) {
        if (categorizedBookListLiveData == null) {
            categorizedBookListLiveData = new MutableLiveData<>();
        }
        DatabaseSingleton.getInstance().getSearchedCategorizedBookList(categoryID, searchedText, bookList -> {
            categorizedBookListLiveData.postValue(bookList);
        });
        return categorizedBookListLiveData;
    }

    public void onBookClicked(Book book) {
        navigator.onBookClicked(book.getBook_id());
    }
}
