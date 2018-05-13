package izenka.hfad.com.bookstore.controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import java.util.List;

import izenka.hfad.com.bookstore.BookListAdapter;
import izenka.hfad.com.bookstore.R;
import izenka.hfad.com.bookstore.controller.db_operations.GetBooksHelper;
import izenka.hfad.com.bookstore.controller.db_operations.SetHelper;
import izenka.hfad.com.bookstore.controller.order.OrdersActivity;
import izenka.hfad.com.bookstore.model.db_classes.Author;
import izenka.hfad.com.bookstore.model.db_classes.Book;
import stanford.androidlib.SimpleActivity;

public class BookListActivity extends SimpleActivity {

    private int categoryID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_list);

        Intent intent = getIntent();
        categoryID = intent.getIntExtra("categoryID", 0);

        SetHelper.setName(this, "category/" + categoryID + "/category_name/", R.id.tvKindOfBook);

        waitForBooksLoading();

        displayBooks();

    }

//    private void waitForAuthorsLoading() {
//        Thread loadingAuthorsThread = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                GetBooksHelper.loadAuthors();
//            }
//        });
//        loadingAuthorsThread.start();
//        try {
//            loadingAuthorsThread.join(2000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//    }

    private void waitForBooksLoading(){
        Thread loadingBooksThread = new Thread(new Runnable(){
            @Override
            public void run() {
                GetBooksHelper.loadBooksFromCategory(categoryID);
//                GetBooksHelper.findBooksFromCategory(categoryID);
            }
        });
        loadingBooksThread.start();
        try {
            loadingBooksThread.join(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void displayBooks(){
        List<Book> bookList = GetBooksHelper.getBooksList();
//        List<Long> booksIDs = GetBooksHelper.getBooksIDs();
        RecyclerView rvBookList = findViewById(R.id.rvBookList);
        GridLayoutManager manager = new GridLayoutManager(this, 2);
        rvBookList.setLayoutManager(manager);
//        BookListAdapter adapter = new BookListAdapter(bookList);
        BookListAdapter adapter = new BookListAdapter(bookList);
        rvBookList.setAdapter(adapter);
    }

    public void onOrdersClick(View view) {
        Animation anim2 = AnimationUtils.loadAnimation(this, R.anim.alpha);
        view.startAnimation(anim2);
        Intent intent = new Intent(this, OrdersActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.diagonaltranslate3, R.anim.alpha2);

    }

    public void onShopCartClick(View view) {
        Animation anim2 = AnimationUtils.loadAnimation(this, R.anim.alpha);
        view.startAnimation(anim2);
        Intent intent = new Intent(this, BasketActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.diagonaltranslate, R.anim.alpha2);
    }

    public void onBookClick(View view) {
        Animation anim = AnimationUtils.loadAnimation(this, R.anim.my_alpha);
        view.startAnimation(anim);
        Intent intent = new Intent(this, BookActivity.class);
        intent.putExtra("bookID", view.getId());
        startActivity(intent);
    }

    public void onReturnBackClick(View view) {
        finish();
    }

    public void onSearchClick(View view) {
        Intent intent = new Intent(this, SearchActivity.class);
        intent.putExtra("categoryID", categoryID);
        startActivity(intent);
    }
}
