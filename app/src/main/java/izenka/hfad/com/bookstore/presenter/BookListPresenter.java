package izenka.hfad.com.bookstore.presenter;


<<<<<<< HEAD
import android.content.Intent;
import android.view.View;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import izenka.hfad.com.bookstore.view.basket.BasketActivity;
import izenka.hfad.com.bookstore.view.book.BookActivity;
import izenka.hfad.com.bookstore.model.db_operations.ReadBooks;
import izenka.hfad.com.bookstore.model.repositories.BookListRepository;
import izenka.hfad.com.bookstore.model.db_classes.Book;
import izenka.hfad.com.bookstore.view.book_list.recycler_view.IBookView;
import izenka.hfad.com.bookstore.view.book_list.IBookListView;
import izenka.hfad.com.bookstore.view.orders.OrdersActivity;
import izenka.hfad.com.bookstore.view.search.SearchActivity;

public class BookListPresenter implements IPresenter {

    private int categoryID;
    private IBookListView bookListView;
    private BookListRepository bookListRepository;
=======
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
>>>>>>> 713af467c300756a5d42d3eca411d50cbcc356d3

    public BookListPresenter(IBookListView bookListView, int categoryID) {
        this.bookListView = bookListView;
        this.categoryID = categoryID;
<<<<<<< HEAD
        bookListRepository = new BookListRepository();
    }

    @Override
=======
    }

>>>>>>> 713af467c300756a5d42d3eca411d50cbcc356d3
    public void onViewCreated() {
        bookListView.initViews();
        bookListView.setCategoryName(categoryID);
        showBookList();
    }

    private void showBookList() {
        waitForBooksLoading();
<<<<<<< HEAD
        bookListRepository.setBookList(ReadBooks.getBooksList());
        bookListView.showBookList(bookListRepository.getBookList());
=======
        List<Book> bookList = GetBooksHelper.getBooksList();
        bookListView.showBookList(bookList);
>>>>>>> 713af467c300756a5d42d3eca411d50cbcc356d3
    }

    private void waitForBooksLoading() {
        Thread loadingBooksThread = new Thread(new Runnable() {
            @Override
            public void run() {
<<<<<<< HEAD
                ReadBooks.loadBooksFromCategory(categoryID);
=======
                GetBooksHelper.loadBooksFromCategory(categoryID);
>>>>>>> 713af467c300756a5d42d3eca411d50cbcc356d3
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
<<<<<<< HEAD
        bookListView.startActivityWithAnimation(view, OrdersActivity.class);
    }

    public void onBasketClicked(View view) {
        bookListView.startActivityWithAnimation(view, BasketActivity.class);
    }

    public void onBookClicked(View view) {
        Intent intent = new Intent();
        intent.putExtra("bookID", view.getId());
        bookListView.startActivityWithAnimation(view, intent, BookActivity.class);
    }

    public void onSearchClicked() {
        Intent intent = new Intent();
        intent.putExtra("categoryID", categoryID);
        bookListView.startActivity(intent, SearchActivity.class);
    }

    public int getItemCount() {
        return bookListRepository.getItemCount();
    }

    public void onBindViewHolder(IBookView holder, int position) {
        Book book = bookListRepository.getBookList().get(position);
        holder.setId(book.book_id);
        holder.setTitle(book.title);
        holder.setPrise(book.price);

        String authors = "";
        for (String a : book.authors) {
            authors += a + ", ";
        }

//        authors = authors.replace(authors.charAt(authors.length()-2), ' ');
        holder.setAuthor(authors);

        holder.setImageId(book.book_id);
        FirebaseStorage storage = FirebaseStorage.getInstance();
        String bookImage = book.imagesPaths.get(0);
        StorageReference imageRef = storage.getReference().child(bookImage);
        holder.setImage(imageRef);
=======
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
>>>>>>> 713af467c300756a5d42d3eca411d50cbcc356d3
    }
}
