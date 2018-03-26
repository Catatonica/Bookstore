package izenka.hfad.com.bookstore.controller;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import izenka.hfad.com.bookstore.R;
import izenka.hfad.com.bookstore.controller.order.OrdersActivity;
import stanford.androidlib.SimpleActivity;

public class BookListActivity extends SimpleActivity {

    private DatabaseReference fb;
    private int categoryID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_list);

        fb = FirebaseDatabase.getInstance().getReference();

        Intent intent = getIntent();
        categoryID = intent.getIntExtra("categoryID", 0);

        setCategoryName(categoryID);

        final Activity activity = this;
        GetBooksHelper.getBooksFromCategory(this, categoryID);

        Thread myThread = new Thread( // создаём новый поток
                                      new Runnable() { // описываем объект Runnable в конструкторе
                                          public void run() {
                                              setCategoryName(categoryID);
                                              GetBooksHelper.getBooksFromCategory(activity, categoryID);

                                          }
                                      }
        );
    }


    private void setCategoryName(int categoryID) {
        DatabaseReference catRef = fb.child("bookstore/category/" + categoryID + "/category_name");
        catRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String categoryName = dataSnapshot.getValue().toString();
                TextView tvKindOfBook = $TV(R.id.tvKindOfBook);
                tvKindOfBook.setText(categoryName);
                tvKindOfBook.setTypeface(Typeface.createFromAsset(
                        getAssets(), "fonts/5.ttf"));
                tvKindOfBook.setTextSize(42);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
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
