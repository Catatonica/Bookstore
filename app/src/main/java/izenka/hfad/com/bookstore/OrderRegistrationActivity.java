package izenka.hfad.com.bookstore;

import android.content.Intent;
import android.support.annotation.IntegerRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import stanford.androidlib.SimpleActivity;

//TODO: make OrdersActivity and layout for it
//where user can view all orders and delete if necessary

public class OrderRegistrationActivity extends SimpleActivity {

    private DatabaseReference db;
    //private Map<String, String> idAndCountMap;
    private ArrayList<String> idAndCountList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_registration);

        Intent intent=getIntent();
        Log.d("idAndCount",intent.getStringArrayListExtra("idAndCount").toString());
        idAndCountList=intent.getStringArrayListExtra("idAndCount");
       // idAndCountMap=new HashMap<>();
       // for(int i=0; i<idAndCountList.size();i++){
       //     idAndCountMap.put(idAndCountList.get(i),idAndCountList.get(++i));
       // }
       // Log.d("idAndCount",idAndCountMap.toString());

        db= FirebaseDatabase.getInstance().getReference();
    }

    public void onReturnBackClick(View view) {
        finish();
    }

    public void onBtnRegistrateClick(View view) {
        Animation anim= AnimationUtils.loadAnimation(this,R.anim.alpha);
        view.startAnimation(anim);

        String key=db.child("bookstore/order").push().getKey();
        //TODO: write different methods and constructors for different fields fulling
        writeNewOrder(key,$ET(R.id.etPhoneNumber).getText().toString(),/*$ET(R.id.etName).getText().toString(),*/
                idAndCountList,$ET(R.id.etCity).getText().toString(),
                $ET(R.id.etStreet).getText().toString(),$ET(R.id.etHouse).getText().toString(),$ET(R.id.etFlatNumber).getText().toString());
    }

    private void writeNewOrder(String order_id, String userPhone, ArrayList < String> idAndCount,
                               String city,String street,String house,String flatNumber){
        Order order=new Order(order_id,userPhone,/* count, book_id,*/ city, street, house, flatNumber);
        Map<String,String> Books=new HashMap<>();
        for(int i=0 ; i<idAndCount.size();i++){
            order.defineIdAndCount(idAndCount.get(i),idAndCount.get(++i));
        }
        Books=order.booksToMap();
        Map<String,Object> Address=order.addressToMap();
        Map<String,String> newOrder=order.toMap();

        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("bookstore/order/"+order_id,newOrder);

        Map<String, Object> orderUpdates = new HashMap<>();
        orderUpdates.put("bookstore/order/"+order_id+"/Books",Books);
        orderUpdates.put("bookstore/order/"+order_id+"/Address",Address);

        db.updateChildren(childUpdates);
        db.updateChildren(orderUpdates);
    }
}
