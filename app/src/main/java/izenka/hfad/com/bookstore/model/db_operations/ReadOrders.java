package izenka.hfad.com.bookstore.model.db_operations;

import android.app.Activity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import izenka.hfad.com.bookstore.R;
import izenka.hfad.com.bookstore.model.db_classes.OrderGet;

public class ReadOrders {

    public static void getOrder(Activity activity, String order_id){
        DatabaseReference db = FirebaseDatabase.getInstance().getReference();
        DatabaseReference orderRef = db.child("bookstore/order/" + order_id);

        final LinearLayout llOrders = (LinearLayout) activity.findViewById(R.id.llOrders);

        orderRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final View view = activity.getLayoutInflater().inflate(R.layout.order, null);
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
}
