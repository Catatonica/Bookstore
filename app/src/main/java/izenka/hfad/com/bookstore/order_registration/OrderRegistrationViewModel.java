package izenka.hfad.com.bookstore.order_registration;


import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

public class OrderRegistrationViewModel extends ViewModel{

    private MutableLiveData<OrderModel> orderLiveData;

    public MutableLiveData<OrderModel> getOrderLiveData(){
        if(orderLiveData == null){
            orderLiveData = new MutableLiveData<>();
        }
        return orderLiveData;
    }

    public void writeNewOrder(OrderModel orderModel){
        DatabaseReference db =  FirebaseDatabase.getInstance().getReference("/bookstore");
        String key = db.child("orders").push().getKey();
        Map<String, Object> newOrder = orderModel.toMap();
        db.child("order").child(key).setValue(newOrder).addOnSuccessListener(aVoid -> subtractBookCount(db, orderModel));
        String userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        db.child("users").child(userID).child("Orders").child(orderModel.date).setValue(key).addOnSuccessListener(aVoid ->{
          cleanBasket(db, orderModel, userID);
        });
    }

    private void cleanBasket(DatabaseReference db, OrderModel orderModel, String userID) {
        for(String bookID: orderModel.Books.keySet()){
            db.child("users").child(userID).child("Basket").child(bookID).removeValue();
        }
    }

    private void subtractBookCount(DatabaseReference db, OrderModel orderModel){
        for(Map.Entry<String, Integer> bookIDAndCount: orderModel.Books.entrySet()){
            Log.d("new Count ", " bookID = "+bookIDAndCount.getKey());
            db.child("book").child(bookIDAndCount.getKey()).child("count").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    long oldCount = (long) dataSnapshot.getValue();
                    long newCount = oldCount-bookIDAndCount.getValue();
                    Log.d("newCount", String.valueOf(newCount)+"bookID = "+bookIDAndCount.getKey());
                    db.child("book").child(bookIDAndCount.getKey()).child("count").setValue(newCount);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
    }
}
