package izenka.hfad.com.bookstore.controller.order;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

import izenka.hfad.com.bookstore.R;
import izenka.hfad.com.bookstore.controller.GetBooksHelper;
import stanford.androidlib.SimpleActivity;

public class OneOrderActivity extends SimpleActivity {

    private Map<Integer, String> booksIDandCount = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one_order);

        Intent intent = getIntent();

        String heading = "Заказ " + intent.getStringExtra("date");
        $TV(R.id.tvHeading).setText(heading);
        String totalPrice = intent.getStringExtra("price");
        $TV(R.id.tvTotalPrice).setText(totalPrice);

        String orderID = intent.getStringExtra("orderID");
        Log.d("oneOrderID", orderID);

        setBooksIDandCount(orderID);
    }

    private void setBooksIDandCount(String orderID) {
        DatabaseReference db = FirebaseDatabase.getInstance().getReference();
        DatabaseReference booksInfoRef = db.child("bookstore/order/" + orderID + "/Books");
        booksInfoRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot el : dataSnapshot.getChildren()) {

                    // Log.d("oneOrder",el.toString());
                    // booksIDandCount.put(el.getKey(), );
                    // Log.d("oneOrder", booksIDandCount.toString());

                    LinearLayout llOrderedBooks = (LinearLayout) findViewById(R.id.llOrderedBooks);

                    // for (int bookID : booksIDandCount.keySet()) {
                    View view = getLayoutInflater().inflate(R.layout.book_in_order, null);
                    TextView tvCount = (TextView) view.findViewById(R.id.tvCount);
                    tvCount.setText(el.getValue().toString());
                    GetBooksHelper.getBook(view, Integer.parseInt(el.getKey()));
                    llOrderedBooks.addView(view);

                    // }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void onBtnBackClick(View view) {
        finish();
    }
}
