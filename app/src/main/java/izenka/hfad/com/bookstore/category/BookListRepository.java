package izenka.hfad.com.bookstore.category;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import izenka.hfad.com.bookstore.model.db_classes.Author;
import izenka.hfad.com.bookstore.model.db_classes.Book;

public class BookListRepository {
    private static List<Book> bookList;

    public static List<Book> getBookList( /*int categoryID*/) {
//        if (bookList == null) {
//            bookList = new ArrayList<>();
//            Thread loadingBooksThread = new Thread(() -> loadBooksFromCategory(categoryID));
//            loadingBooksThread.start();
//            try {
//                loadingBooksThread.join(2000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
        return bookList;
    }

    public static void loadBooksFromCategory( int categoryID) {
        bookList = new ArrayList<>();
        final DatabaseReference bookRef = FirebaseDatabase.getInstance().getReference().child("bookstore/book");
        final Query queryBook = bookRef.orderByChild("book_id");
        queryBook.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot data) {
                for (DataSnapshot bookData : data.getChildren())
                    for (DataSnapshot id : bookData.child("Categories").getChildren())
                        if (String.valueOf(id.getValue()).equals(String.valueOf(categoryID))) {
                            {
                                final Book book = bookData.getValue(Book.class);
                                List<String> imagesPaths = new ArrayList<>();
                                for (DataSnapshot imagesID : bookData.child("Images").getChildren()) {
                                    imagesPaths.add(imagesID.getValue().toString());
                                }
                                book.imagesPaths = imagesPaths;

                                final List<Long> authorsIDs = new ArrayList<>();
                                for (DataSnapshot authID : bookData.child("Authors").getChildren()) {
                                    authorsIDs.add((Long) authID.getValue());
                                }

                                book.authors = new ArrayList<>();
                                loadAuthors(authorsIDs, book);

                                bookList.add(book);
                            }
                        }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });
    }

    public static void loadAuthors(final List<Long> authorsID, final Book book) {
        for (long authorID : authorsID) {
            DatabaseReference authorRef = FirebaseDatabase.getInstance().getReference().child("bookstore/author/" + authorID);
            authorRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Author author = dataSnapshot.getValue(Author.class);
                    String authorName = author.author_name.substring(0, 1);
                    String authorSurname = author.author_surname;
                    book.authorList.add(authorSurname + " " + authorName + ".");
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
    }
}
