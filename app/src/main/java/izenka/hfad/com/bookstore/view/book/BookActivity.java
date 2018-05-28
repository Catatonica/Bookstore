package izenka.hfad.com.bookstore.view.book;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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
import izenka.hfad.com.bookstore.view.basket.BasketActivity;
import izenka.hfad.com.bookstore.view.orders.OrdersActivity;
import izenka.hfad.com.bookstore.model.db_classes.Author;
import izenka.hfad.com.bookstore.model.db_classes.Book;
import izenka.hfad.com.bookstore.model.db_classes.Publisher;
import izenka.hfad.com.bookstore.view.main_menu.MainMenuActivity;
import mehdi.sakout.fancybuttons.FancyButton;
import stanford.androidlib.SimpleActivity;

public class BookActivity extends SimpleActivity {

    private boolean bool = true;
    private DatabaseReference fb;
    private int bookID;
    private SharedPreferences sp;
    private Animation anim;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);

        anim = AnimationUtils.loadAnimation(this, R.anim.my_alpha);
        mehdi.sakout.fancybuttons.FancyButton putInBasket = (mehdi.sakout.fancybuttons.FancyButton) findViewById(R.id.putInBasket);
        putInBasket.setIconResource(R.drawable.basket2);
        putInBasket.setIconPosition(FancyButton.POSITION_LEFT);
        putInBasket.getIconImageObject().setLayoutParams(new LinearLayout.LayoutParams(60, 60));

        Intent intent = getIntent();
        bookID = intent.getIntExtra("bookID", 0);

        fillViews(bookID);
    }

    private void fillViews(int bookID) {
        fb = FirebaseDatabase.getInstance().getReference();
        DatabaseReference bookRef = fb.child("bookstore/book");
        Query query = bookRef.orderByChild("book_id").equalTo(bookID);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot data) {
                final Book book = data.getChildren().iterator().next().getValue(Book.class);
                $TV(R.id.tvTitle).setText("\"" + book.title + "\"");
                $TV(R.id.tvYear).setText(String.valueOf(book.publication_year));


                List<Integer> Authors = new ArrayList<>();
                for (DataSnapshot authID : data.getChildren().iterator().next().child("Authors").getChildren()) {
                    Authors.add(Integer.parseInt(String.valueOf(authID.getValue())));
                }
                setAuthor(Authors);

                List<String> Images = new ArrayList<>();
                for (DataSnapshot imagesID : data.getChildren().iterator().next().child("Images").getChildren()) {
                    Images.add(imagesID.getValue().toString());
                }
                setImage(Images, book.book_id);


                $TV(R.id.tvPrise).setText(book.price);
                TextView tvAvailability = $TV(R.id.tvAvailability);
                if (book.count != 0) {
                    tvAvailability.setText("в наличии");
                } else {
                    tvAvailability.setText("нет в наличии");
                }
                $TV(R.id.tvAnnot).setText(book.description);

                mehdi.sakout.fancybuttons.FancyButton btnParameters = (mehdi.sakout.fancybuttons.FancyButton) findViewById(R.id.btnParameters);
                btnParameters.setOnClickListener(new View.OnClickListener() {
                    Boolean isClicked = false;

                    @Override
                    public void onClick(View v) {
                        v.startAnimation(anim);
                        LinearLayout llParameters = (LinearLayout) findViewById(R.id.llParameters);
                        if (isClicked) {
                            llParameters.removeAllViews();
                            isClicked = false;
                        } else {
                            final View view = getLayoutInflater().inflate(R.layout.book_parameters, null);
                            TextView tvCount = (TextView) view.findViewById(R.id.tvCount);
                            tvCount.setText(String.valueOf(book.count));
                            TextView tvPages = (TextView) view.findViewById(R.id.tvPages);
                            tvPages.setText(String.valueOf(book.pages_number));
                            setPublisher(book.book_publisher_id, view);
                            TextView tvCover = (TextView) view.findViewById(R.id.tvCover);
                            tvCover.setText(book.cover);

                            llParameters.addView(view);
                            isClicked = true;
                        }
                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private void setImage(String bookImage) {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference imageRef = storage.getReference().child(bookImage);

        Glide.with(this /* context */)
             .using(new FirebaseImageLoader())
             .load(imageRef)
             .into($IV(R.id.ivBookImage));

    }

    private void setAuthor(List<Integer> authorsID) {
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
                    TextView tvBookAuthor = (TextView) findViewById(R.id.tvAuthor);
                    tvBookAuthor.setText(auth.substring(1, auth.length() - 1));
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
    }

    private void setImage(List<String> Images, int bookID) {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        String bookImage = Images.get(0);
        StorageReference imageRef = storage.getReference().child(bookImage);
        log("imageRef" + imageRef);

        ImageView imgBtnBook = (ImageView) findViewById(R.id.ivBookImage);
        imgBtnBook.setId(bookID);

        Glide.with(this /* context */)
             .using(new FirebaseImageLoader())
             .load(imageRef)
             .into(imgBtnBook);

    }


  /*  public void onParametersClick(View view) {
        View glParameters=getLayoutInflater().inflate(R.layout.book_parameters, null);
        TextView tvCount=(TextView) glParameters.findViewById(R.id.tvCount);
        tvCount.setText(book.count);
        TextView tvPages=(TextView) glParameters.findViewById(R.id.tvPages);
        tvPages.setText(book.pages_number);
        setPublisher(book.book_publisher_id, glParameters);

        LinearLayout llParameters=(LinearLayout) findViewById(R.id.llParameters);
        llParameters.addView(glParameters);
    }
    */


    private void setPublisher(int publisherID, final View view) {
        DatabaseReference publRef = fb.child("bookstore/publisher");
        Query query = publRef.orderByChild("publisher_id").equalTo(publisherID);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot data) {
                Publisher publisher = data.getChildren().iterator().next().getValue(Publisher.class);

                TextView tvPublisher = (TextView) view.findViewById(R.id.tvPublisher);
                tvPublisher.setText(publisher.publisher_name);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void onPutInBasketClick(View view) {
        view.startAnimation(anim);

        sp = getSharedPreferences("myPref", MODE_PRIVATE);
        // MainMenuActivity.user.getBooksIDs().add(bookID+"");
        //  MainMenuActivity.booksIDs.add(bookID+"");
        SharedPreferences.Editor e = sp.edit();
        MainMenuActivity.stringSet.add(String.valueOf(bookID));
        e.putStringSet("booksIDs", MainMenuActivity.stringSet);
        e.apply();
        Toast.makeText(this, "добавлено", Toast.LENGTH_SHORT).show();
    }

    public void onExpandClick(View view) {
        TextView tvAnnot = $TV(R.id.tvAnnot);
        ImageButton ibExpand = $IB(R.id.ibExpand);
        if (bool) {
            tvAnnot.setMaxLines(30);
            ibExpand.setBackground(getDrawable(R.drawable.narrow));
            bool = false;
        } else {
            tvAnnot.setMaxLines(5);
            bool = true;
            ibExpand.setBackground(getDrawable(R.drawable.expand));
        }
    }

    public void onReturnBackClick(View view) {
        Animation anim3 = AnimationUtils.loadAnimation(this, R.anim.translate);
        view.startAnimation(anim3);
        finish();
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
        //SharedPreferences.Editor e = sp.edit();
        // e.putStringSet("booksIDs",MainMenuActivity.stringSet);
        //e.apply();
        //  e.commit();
        view.startAnimation(anim2);
        Intent intent = new Intent(this, BasketActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.diagonaltranslate, R.anim.alpha2);
    }
}

