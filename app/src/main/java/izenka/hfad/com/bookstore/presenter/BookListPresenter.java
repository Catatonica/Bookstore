package izenka.hfad.com.bookstore.presenter;


import android.view.View;

import java.util.List;

import izenka.hfad.com.bookstore.BasketActivity;
import izenka.hfad.com.bookstore.BookActivity;
import izenka.hfad.com.bookstore.view.book_list.IBookListView;
import izenka.hfad.com.bookstore.view.search.SearchActivity;
import izenka.hfad.com.bookstore.controller.db_operations.GetBooksHelper;
import izenka.hfad.com.bookstore.controller.order.OrdersActivity;
import izenka.hfad.com.bookstore.model.db_classes.Book;

public class BookListPresenter {

    private int categoryID;
    private IBookListView bookListView;

    public BookListPresenter(IBookListView bookListView, int categoryID) {
        this.bookListView = bookListView;
        this.categoryID = categoryID;
    }

    public void onViewCreated() {
        bookListView.initViews();
        bookListView.setCategoryName(categoryID);
        showBookList();
    }

    private void showBookList() {
        waitForBooksLoading();
        List<Book> bookList = GetBooksHelper.getBooksList();
        bookListView.showBookList(bookList);
    }

    private void waitForBooksLoading() {
        Thread loadingBooksThread = new Thread(new Runnable() {
            @Override
            public void run() {
                GetBooksHelper.loadBooksFromCategory(categoryID);
            }
        });
        loadingBooksThread.start();
        try {
            loadingBooksThread.join(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void onOrdersClicked(View view) {
        bookListView.startActivity(view, OrdersActivity.class);
    }

    public void onBasketClicked(View view) {
        bookListView.startActivity(view, BasketActivity.class);
    }

    public void onBookClicked(View view) {
        bookListView.startActivity(view, BookActivity.class, "bookID", view.getId());
    }

    public void onSearchClicked() {
        bookListView.startActivity(SearchActivity.class, "categoryID", categoryID);
    }
}
