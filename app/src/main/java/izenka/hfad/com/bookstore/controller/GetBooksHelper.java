package izenka.hfad.com.bookstore.controller;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

import izenka.hfad.com.bookstore.R;
import izenka.hfad.com.bookstore.model.db_classes.Author;
import izenka.hfad.com.bookstore.model.db_classes.Book;


public abstract class GetBooksHelper {

    static void getBooksFromSearch(final Activity activity, final int gridLayoutID, final int categoryID) {

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
                                setAuthor(Authors, view);

                                List<String> Images = new ArrayList<>();
                                for (DataSnapshot imagesID : bookData.child("Images").getChildren()) {
                                    Images.add(imagesID.getValue().toString());
                                }
                                int bookID = book.book_id;
                                setImage(activity, Images, bookID, view);

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
                                            setAuthor(Authors, view);

                                            List<String> Images = new ArrayList<>();
                                            for (DataSnapshot imagesID : bookData.child("Images").getChildren()) {
                                                Images.add(imagesID.getValue().toString());
                                            }
                                            int bookID = book.book_id;
                                            setImage(activity, Images, bookID, view);

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
                setAuthor(Authors, view);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    static void getBooksFromCategory(final Activity activity, final int categoryID) {
        final DatabaseReference bookRef = FirebaseDatabase.getInstance().getReference().child("bookstore/book");
        final Query queryBook = bookRef.orderByChild("book_id");
        queryBook.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot data) {
                for (DataSnapshot bookData : data.getChildren())
                    for (DataSnapshot id : bookData.child("Categories").getChildren())
                        if (String.valueOf(id.getValue()).equals(String.valueOf(categoryID))) {
                            {
                                Book book = bookData.getValue(Book.class);
                                Log.d("book", book.toString());
                                final View view = activity.getLayoutInflater().inflate(R.layout.book, null);

                                String title = book.title;
                                String price = book.price;

                                List<Long> Authors = new ArrayList<>();
                                for (DataSnapshot authID : bookData.child("Authors").getChildren()) {
                                    Authors.add((Long) authID.getValue());
                                }
                                setAuthor(Authors, view);

                                List<String> Images = new ArrayList<>();
                                for (DataSnapshot imagesID : bookData.child("Images").getChildren()) {
                                    Images.add(imagesID.getValue().toString());
                                }
                                int bookID = book.book_id;
                                setImage(activity, Images, bookID, view);

                                view.setId(bookID);

                                TextView tvBookPrise = (TextView) view.findViewById(R.id.tvBookPrise);
                                tvBookPrise.setText(price);
                                TextView tvBookName = (TextView) view.findViewById(R.id.tvBookName);
                                tvBookName.setText("\"" + title + "\"");

                                GridLayout gridLayout = (GridLayout) activity.findViewById(R.id.gridLayout);
                                gridLayout.addView(view);
                            }
                        }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    static void setAuthor(List<Long> authorsID, final View view) {
        final List<String> authorSurName = new ArrayList<>();
        DatabaseReference authorRef = FirebaseDatabase.getInstance().getReference().child("bookstore/author");
        for (long authorID : authorsID) {
            Log.d("List", authorID + "");
            Query queryAuthor = authorRef.orderByChild("author_id").equalTo(authorID);
            queryAuthor.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Author author = dataSnapshot.getChildren().iterator().next().getValue(Author.class);
                    String authorName = author.author_name.substring(0, 1);
                    String authorSurname = author.author_surname;
                    authorSurName.add(authorSurname + " " + authorName + ".");
                    String auth = authorSurName.toString();
                    TextView tvBookAuthor = (TextView) view.findViewById(R.id.tvBookAuthor);
                    tvBookAuthor.setText(auth.substring(1, auth.length() - 1));
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }

    }

    static void setImage(Activity activity, List<String> Images, int bookID, View view) {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        String bookImage = Images.get(0);
        StorageReference imageRef = storage.getReference().child(bookImage);

        ImageView imgBtnBook = (ImageView) view.findViewById(R.id.imgBtnBook);
        imgBtnBook.setId(bookID);

        Glide.with(activity /* context */)
             .using(new FirebaseImageLoader())
             .load(imageRef)
             .into(imgBtnBook);

    }
}
