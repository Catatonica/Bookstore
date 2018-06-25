package izenka.hfad.com.bookstore.category;


import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import izenka.hfad.com.bookstore.model.db_classes.Author;
import izenka.hfad.com.bookstore.model.db_classes.Book;

public class BookListViewModel extends ViewModel {

    private int categoryID;
    private BookListNavigator navigator;
    private MutableLiveData<List<Book>> bookListLiveData;

    public void setNavigator(BookListNavigator navigator) {
        this.navigator = navigator;
    }
    public void onBookClicked(Book book) {
        navigator.onBookClicked(book.book_id);
    }
    public void onSearchInCategoryClicked() {
        navigator.onSearchInCategoryClicked();
    }

    public void setBookListLiveData() {
//        if (bookListLiveData == null) {
//            Log.d("setBookListLD", "true");
//            bookListLiveData = new MutableLiveData<>();

//        new Thread(new Runnable() {
//            @Override
//            public void run() {
                List<Book> bookList = new ArrayList<>();
                FirebaseDatabase.getInstance()
                                .getReference("bookstore/")
                                .addListenerForSingleValueEvent(new ValueEventListener() { //TODO: make table Category with ids of books
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        for (DataSnapshot bookData : dataSnapshot.child("book").getChildren()) {
                                            for (DataSnapshot id : bookData.child("Categories").getChildren()) {
                                                if (String.valueOf(id.getValue()).equals(String.valueOf(categoryID))) {
                                                    final Book book = bookData.getValue(Book.class);
                                                    List<String> imagesPaths = new ArrayList<>();
                                                    for (DataSnapshot imagesID : bookData.child("Images").getChildren()) {
                                                        imagesPaths.add(imagesID.getValue().toString());
                                                    }
                                                    book.imagesPaths = imagesPaths;

                                                    final List<Integer> authorsIDs = new ArrayList<>();
                                                    for (DataSnapshot authID : bookData.child("Authors").getChildren()) {
                                                        authorsIDs.add(Integer.parseInt(String.valueOf(authID.getValue())));
                                                    }
                                                    book.authors = new ArrayList<>();
                                                    for(int authorID: authorsIDs){
                                                        book.authors.add(dataSnapshot.child("author/"+authorID).getValue(Author.class));
                                                    }
                                                    bookList.add(book);
                                                }
                                            }
                                        }
                                        bookListLiveData.postValue(bookList);
                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }
                                });
//            }
//        }).start();
//        }
    }

//    public static void loadAuthors(final List<Long> authorsID, final Book book) {
//        book.authorList = new ArrayList<>();
//        for (long authorID : authorsID) {
//            DatabaseReference authorRef = FirebaseDatabase.getInstance().getReference().child("bookstore/author/" + authorID);
//            authorRef.addListenerForSingleValueEvent(new ValueEventListener() {
//                @Override
//                public void onDataChange(DataSnapshot dataSnapshot) {
//                    Author author = dataSnapshot.getValue(Author.class);
//                    String authorName = author.author_name.substring(0, 1);
//                    String authorSurname = author.author_surname;
//                    book.authorList.add(authorSurname + " " + authorName + ".");
//                }
//
//                @Override
//                public void onCancelled(DatabaseError databaseError) {
//
//                }
//            });
//        }
//    }

    public MutableLiveData<List<Book>> getBookListLiveData() {
        if (bookListLiveData == null) {
//            Log.d("setBookListLD", "true");
            bookListLiveData = new MutableLiveData<>();
            setBookListLiveData();
        }
        return bookListLiveData;
    }

    public void setCategoryID(int categoryID) {
        this.categoryID = categoryID;
    }
}
