package izenka.hfad.com.bookstore.model.db_operations;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import izenka.hfad.com.bookstore.R;
import izenka.hfad.com.bookstore.model.db_classes.Author;
import izenka.hfad.com.bookstore.model.db_classes.Book;


public abstract class ReadBooks {

    public static void getBooksFromSearch(final Activity activity, final int gridLayoutID, final int categoryID) {

        final EditText editText = (EditText) activity.findViewById(R.id.etSearch);
        final String inputText = editText.getText().toString().toLowerCase();

        final DatabaseReference bookRef = FirebaseDatabase.getInstance().getReference().child("bookstore/book");
        final Query queryBook = bookRef.orderByChild("book_id");
        queryBook.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot data) {
                for (DataSnapshot bookData : data.getChildren()) {
                    switch (categoryID) {
                        case -1: {
                            if (bookData.child("title").getValue().toString().toLowerCase().contains(inputText)) {
                                Book book = bookData.getValue(Book.class);
                                Log.d("book", book.toString());
                                final View view = activity.getLayoutInflater().inflate(R.layout.book, null);

                                String title = book.title;
                                String price = book.price;

                                List<Long> Authors = new ArrayList<>();
                                for (DataSnapshot authID : bookData.child("Authors").getChildren()) {
                                    Authors.add((Long) authID.getValue());
                                }
                                Read.setAuthor(Authors, view);

                                List<String> Images = new ArrayList<>();
                                for (DataSnapshot imagesID : bookData.child("Images").getChildren()) {
                                    Images.add(imagesID.getValue().toString());
                                }
                                int bookID = book.book_id;
                                Read.setImage(activity, Images, bookID, view);

                                view.setId(bookID);

                                TextView tvBookPrise = (TextView) view.findViewById(R.id.tvBookPrise);
                                tvBookPrise.setText(price);
                                TextView tvBookName = (TextView) view.findViewById(R.id.tvBookName);
                                tvBookName.setText("\"" + title + "\"");


                                GridLayout gridLayout = (GridLayout) activity.findViewById(gridLayoutID);
                                gridLayout.addView(view);
                            }
                        }
                        break;

                        default: {
                            for (DataSnapshot id : bookData.child("Categories").getChildren())
                                if (String.valueOf(id.getValue()).equals(String.valueOf(categoryID))) {
                                    {
                                        if (bookData.child("title").getValue().toString().toLowerCase().contains(inputText)) {
                                            Book book = bookData.getValue(Book.class);
                                            Log.d("book", book.toString());
                                            final View view = activity.getLayoutInflater().inflate(R.layout.book, null);

                                            String title = book.title;
                                            String price = book.price;

                                            List<Long> Authors = new ArrayList<>();
                                            for (DataSnapshot authID : bookData.child("Authors").getChildren()) {
                                                Authors.add((Long) authID.getValue());
                                            }
                                            Read.setAuthor(Authors, view);

                                            List<String> Images = new ArrayList<>();
                                            for (DataSnapshot imagesID : bookData.child("Images").getChildren()) {
                                                Images.add(imagesID.getValue().toString());
                                            }
                                            int bookID = book.book_id;
                                            Read.setImage(activity, Images, bookID, view);

                                            view.setId(bookID);

                                            TextView tvBookPrise = (TextView) view.findViewById(R.id.tvBookPrise);
                                            tvBookPrise.setText(price);
                                            TextView tvBookName = (TextView) view.findViewById(R.id.tvBookName);
                                            tvBookName.setText("\"" + title + "\"");

                                            GridLayout gridLayout = (GridLayout) activity.findViewById(gridLayoutID);
                                            gridLayout.addView(view);
                                        }
                                    }
                                }
                        }
                        break;
                    }
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public static void getBook(final View view, final int bookID) {
        final DatabaseReference bookRef = FirebaseDatabase.getInstance().getReference()
                                                          .child("bookstore/book/" + bookID);
        bookRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot data) {
                Book book = data.getValue(Book.class);
                TextView tvTitle = (TextView) view.findViewById(R.id.tvBooksTitle);
                tvTitle.setText(book.title);
                TextView tvYear = (TextView) view.findViewById(R.id.tvPublicationYear);
                tvYear.setText(String.valueOf(book.publication_year));
                TextView tvPriceForOne = (TextView) view.findViewById(R.id.tvPriceForOne);
                tvPriceForOne.setText(String.valueOf(book.price));

                List<Long> Authors = new ArrayList<>();
                for (DataSnapshot authID : data.child("Authors").getChildren()) {
                    Authors.add((Long) authID.getValue());
                }
                Read.setAuthor(Authors, view);
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
                    book.authors.add(authorSurname + " " + authorName + ".");
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
    }

    private static List<Book> bookList;

    public static List<Book> getBooksList() {
        return bookList;
    }

    public static void loadBooksFromCategory(final int categoryID) {
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

//    public static List<Long> getBooksIDs() {
//        return booksIDs;
//    }
//
//    private static List<Long> booksIDs;
//
//    public static void findBooksFromCategory(final int categoryID) {
//        booksIDs = new ArrayList<>();
//
//        final DatabaseReference bookRef = FirebaseDatabase.getInstance().getReference().child("bookstore/book");
//        bookRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot data) {
//                for (DataSnapshot bookData : data.getChildren())
//                    for (DataSnapshot id : bookData.child("Categories").getChildren())
//                        if (String.valueOf(id.getValue()).equals(String.valueOf(categoryID))) {
//                            {
//                                booksIDs.add((Long) bookData.child("book_id").getValue());
//                            }
//                        }
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//
//        });
//    }
//
//    private static Book loadingBook;
//
//    public static Book getBook() {
//        return loadingBook;
//    }
//
//    public static void loadBook(long bookID) {
//        final DatabaseReference bookRef = FirebaseDatabase.getInstance().getReference().child("bookstore/book/" + bookID);
//        bookRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot data) {
//                final Book book = data.getValue(Book.class);
//                List<String> imagesPaths = new ArrayList<>();
//                for (DataSnapshot imagesID : data.child("Images").getChildren()) {
//                    imagesPaths.add(imagesID.getValue().toString());
//                }
//                book.imagesPaths = imagesPaths;
//
//                final List<Long> authorsIDs = new ArrayList<>();
//                for (DataSnapshot authID : data.child("Authors").getChildren()) {
//                    authorsIDs.add((Long) authID.getValue());
//                }
//
//                book.authors = new ArrayList<>();
//                loadAuthors(authorsIDs, book);
//                loadingBook = book;
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//
//        });
//    }
}
