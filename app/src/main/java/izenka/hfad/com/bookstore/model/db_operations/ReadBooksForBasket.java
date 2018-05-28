package izenka.hfad.com.bookstore.model.db_operations;


import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.RelativeLayout;
import android.widget.TextView;

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
import java.util.Set;

import izenka.hfad.com.bookstore.R;
import izenka.hfad.com.bookstore.model.db_classes.Author;
import izenka.hfad.com.bookstore.model.db_classes.Book;
import izenka.hfad.com.bookstore.presenter.BasketPresenter;

public class ReadBooksForBasket {
    private static DatabaseReference fb = FirebaseDatabase.getInstance().getReference();
    private static final DatabaseReference bookRef = fb.child("bookstore/book");
    private static double priceForSeveral;

    public static void queryBook(Activity activity, String bookID, Set<View> checkedViewSet, BasketPresenter presenter) {
        final Query queryBook = bookRef.orderByChild("book_id").equalTo(Integer.parseInt(bookID));
        queryBook.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot data) {

                final View oneBookInBasketView = activity.getLayoutInflater().inflate(R.layout.book_in_basket, null);
                oneBookInBasketView.setId(Integer.parseInt(bookID));

                priceForSeveral = 0;

                final Book book = data.getChildren().iterator().next().getValue(Book.class);
                TextView tvBookTitle = (TextView) oneBookInBasketView.findViewById(R.id.tvBookTitle);
                tvBookTitle.setText(book.title);
                TextView tvPublicationYear = (TextView) oneBookInBasketView.findViewById(R.id.tvPublicationYear);
                tvPublicationYear.setText(String.valueOf(book.publication_year));

                TextView bookPrice = (TextView) oneBookInBasketView.findViewById(R.id.tvPriceForOne);
                bookPrice.setText(book.price);
                //TODO: make price double
                final TextView tvPriceForSeveral = (TextView) oneBookInBasketView.findViewById(R.id.tvPriceForSeveral);
                // tvPriceForSeveral.setText("/"+book.price+" р.");

                CheckBox checkBox = (CheckBox) oneBookInBasketView.findViewById(R.id.checkBox);
                checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        RelativeLayout rlAction = (RelativeLayout) activity.findViewById(R.id.rlAction); //activity vs llBasket ?
                        if (isChecked) {
                            checkedViewSet.add(oneBookInBasketView);
                            rlAction.setVisibility(View.VISIBLE);
                        } else {
                            checkedViewSet.remove(oneBookInBasketView);
                            if (checkedViewSet.isEmpty())
                                rlAction.setVisibility(View.INVISIBLE);
                        }
                        presenter.setTotalPrice();
                    }
                });

                final NumberPicker numberPicker = (NumberPicker) oneBookInBasketView.findViewById(R.id.npBookCount);
                numberPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                    @Override
                    public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                        String[] bookPrice = book.price.split(" ");
                        priceForSeveral = Double.parseDouble(bookPrice[0]) * picker.getValue();
                        tvPriceForSeveral.setText("/" + priceForSeveral + " р.");
                        if (oneBookInBasketView.findViewById(R.id.checkBox).isEnabled())
                            presenter.setTotalPrice();
                    }
                });

                TextView tvBookAvailability = (TextView) oneBookInBasketView.findViewById(R.id.tvBookAvailability);
                //TODO: if in db count <0?
                if (book.count > 0) {
                    tvBookAvailability.setText("в наличии");
                    numberPicker.setMaxValue(book.count);
                    numberPicker.setMinValue(1);
                } else {
                    tvBookAvailability.setText("нет в наличии");
                    numberPicker.setMaxValue(0);
                    numberPicker.setMinValue(0);
                }

                List<Integer> Authors = new ArrayList<>();
                for (DataSnapshot authID : data.getChildren().iterator().next().child("Authors").getChildren()) {
                    Authors.add(Integer.parseInt(String.valueOf(authID.getValue())));
                }
                setAuthor(Authors, oneBookInBasketView);

                List<String> Images = new ArrayList<>();
                for (DataSnapshot imagesID : data.getChildren().iterator().next().child("Images").getChildren()) {
                    Images.add(imagesID.getValue().toString());
                }
                setImage(Images, book.book_id, oneBookInBasketView, presenter);

                presenter.addBookView(oneBookInBasketView);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private static void setAuthor(List<Integer> authorsID, final View view) {
        final List<String> authorSurName = new ArrayList<>();
        DatabaseReference authorRef = fb.child("bookstore/author");
        for (int authorID : authorsID) {
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
                    TextView tvBookAuthor = (TextView) view.findViewById(R.id.tvAuthorName);
                    tvBookAuthor.setText(auth.substring(1, auth.length() - 1));
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
    }

    private static void setImage(List<String> Images, int bookID, View view, BasketPresenter presenter) {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        String bookImage = Images.get(0);
        StorageReference imageRef = storage.getReference().child(bookImage);

        ImageView imgBtnBook = (ImageView) view.findViewById(R.id.ivBook);
        imgBtnBook.setId(bookID);

        presenter.loadImage(imageRef, imgBtnBook);
    }

}
