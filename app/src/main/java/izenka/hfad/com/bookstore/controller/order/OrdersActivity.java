package izenka.hfad.com.bookstore.controller.order;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.LinkedList;
import java.util.Set;

import izenka.hfad.com.bookstore.R;
import izenka.hfad.com.bookstore.model.db_classes.OrderGet;

public class OrdersActivity extends AppCompatActivity {

    private LinkedList<String> ordersIDs = new LinkedList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders);

        showOrders();
    }

    private void showOrders() {

        SharedPreferences sp = getSharedPreferences("ordersPref", MODE_PRIVATE);

        //NullPointerEception - means, that set is empty
        Set<String> orderIDsSet= sp.getStringSet("ordersIDs",null);
        if(orderIDsSet!=null){
            for (String id : orderIDsSet) {
                ordersIDs.addFirst(id);
            }

            for (String order_id : ordersIDs) {
                fillOneOrderWithInfo(order_id);
            }
        }


    }

    private void fillOneOrderWithInfo(final String order_id) {
        DatabaseReference db = FirebaseDatabase.getInstance().getReference();
        DatabaseReference orderRef = db.child("bookstore/order/" + order_id);

        final LinearLayout llOrders = (LinearLayout) findViewById(R.id.llOrders);

        orderRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final View view = getLayoutInflater().inflate(R.layout.order_in_list, null);
                view.setTransitionName(order_id);

                OrderGet order = dataSnapshot.getValue(OrderGet.class);

                TextView tvDate = (TextView) view.findViewById(R.id.tvDate);
                tvDate.setText(order.date);
                TextView tvPrice = (TextView) view.findViewById(R.id.tvTotalPrice);
                tvPrice.setText(String.valueOf(order.totalPrice));
                TextView tvStatus = (TextView) view.findViewById(R.id.tvStatus);
                tvStatus.setText(order.status);

                llOrders.addView(view);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void onReturnBackClick(View view) {
        finish();
    }

    public void onOneOrderClick(View view) {
        Intent intent = new Intent(this, OneOrderActivity.class);
        TextView tvDate = (TextView) view.findViewById(R.id.tvDate);
        intent.putExtra("date", tvDate.getText().toString());
        TextView tvPrice = (TextView) view.findViewById(R.id.tvTotalPrice);
        intent.putExtra("price", tvPrice.getText().toString());
        intent.putExtra("orderID", view.getTransitionName());
        startActivity(intent);
    }
}
