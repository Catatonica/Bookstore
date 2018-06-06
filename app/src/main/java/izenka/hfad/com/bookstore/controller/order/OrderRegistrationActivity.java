package izenka.hfad.com.bookstore.controller.order;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import izenka.hfad.com.bookstore.R;
import izenka.hfad.com.bookstore.main_menu.view.MainMenuActivity;
import izenka.hfad.com.bookstore.model.db_classes.Order;
import izenka.hfad.com.bookstore.view.orders.OrdersActivity;
import stanford.androidlib.SimpleActivity;

//TODO: make OrdersActivity and layout for it
//where user can view all orders and delete if necessary

public class OrderRegistrationActivity extends SimpleActivity {

    private DatabaseReference db;
    private double totalPrice;
    //private Map<String, String> idAndCountMap;
    private ArrayList<String> idAndCountList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        Intent intent = getIntent();
        Log.d("idAndCount", intent.getStringArrayListExtra("idAndCount").toString());
        idAndCountList = intent.getStringArrayListExtra("idAndCount");
        totalPrice = intent.getDoubleExtra("totalPrice", 0);
        // idAndCountMap=new HashMap<>();
        // for(int i=0; i<idAndCountList.size();i++){
        //     idAndCountMap.put(idAndCountList.get(i),idAndCountList.get(++i));
        // }
        // Log.d("idAndCount",idAndCountMap.toString());

        db = FirebaseDatabase.getInstance().getReference();
    }

    public void onReturnBackClick(View view) {
        finish();
    }

    public void onBtnRegisterClick(View view) {
        Animation anim = AnimationUtils.loadAnimation(this, R.anim.alpha);
        view.startAnimation(anim);

        String key = db.child("bookstore/order").push().getKey();

        if ($ET(R.id.etPhoneNumber).getText().toString().isEmpty() ||
                $ET(R.id.etCity).getText().toString().isEmpty() ||
                $ET(R.id.etStreet).getText().toString().isEmpty() ||
                $ET(R.id.etHouse).getText().toString().isEmpty() ||
                $ET(R.id.etFlatNumber).getText().toString().isEmpty()) {
            Toast.makeText(this, "Заполните все поля, помеченные знаком *", Toast.LENGTH_SHORT).show();
        } else {
            writeNewOrder(key, idAndCountList, totalPrice,
                          $ET(R.id.etPhoneNumber).getText().toString(),
                          $ET(R.id.etName).getText().toString(),
                          $ET(R.id.etCity).getText().toString(),
                          $ET(R.id.etStreet).getText().toString(),
                          $ET(R.id.etHouse).getText().toString(),
                          $ET(R.id.etFlatNumber).getText().toString(),

                          $ET(R.id.etPorchNumber).getText().toString(),
                          $ET(R.id.etFloor).getText().toString()
            );
            showSucceedMessage();
        }
    }

    private void writeNewOrder(String order_id, ArrayList<String> idAndCount, Double price, String userPhone,
                               String username, String city, String street, String house, String flatNumber,
                               String porchNumber, String floor) {

        DateFormat df = new SimpleDateFormat("EEE, d MMM yyyy, HH:mm", Locale.getDefault());
        String date = df.format(Calendar.getInstance().getTime());
        Log.d("date", date);

        Order order = new Order(order_id, date, price, userPhone, username,/* count, book_id,*/ city, street, house, flatNumber
                , porchNumber, floor);
        for (int i = 0; i < idAndCount.size(); i++) {
            order.defineIdAndCount(idAndCount.get(i), idAndCount.get(++i));
        }

        Map<String, String> Books = order.booksToMap();
        Map<String, String> Address = order.addressToMap();
        Map<String, Object> newOrder = order.toMap();

        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("bookstore/order/" + order_id, newOrder);

        Map<String, Object> orderUpdates = new HashMap<>();
        orderUpdates.put("bookstore/order/" + order_id + "/Books", Books);
        orderUpdates.put("bookstore/order/" + order_id + "/Address", Address);

        db.updateChildren(childUpdates);
        db.updateChildren(orderUpdates);


        SharedPreferences sp = getSharedPreferences("ordersPref", MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        if (!MainMenuActivity.ordersSet.contains(order_id)) {
            MainMenuActivity.ordersSet.add(order_id);
        }
        editor.putStringSet("ordersIDs", MainMenuActivity.ordersSet);
        editor.apply();
    }

    private void showSucceedMessage() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.succeed_message, null))
               .show();

/*

        builder.setTitle("Заказ оформлен")
                .setMessage("Ожидайте получение заказа в течение недели.\nДля уточнения информации звоните по телефону:\n+375297770707")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // continue with delete
                    }
                })
                .setNegativeButton("просмотреть заказ", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();

                */
    }


    public void onBtnOKClick(View view) {
        //TODO:remove books count from firebase
        for (int i = 0; i < idAndCountList.size(); i += 2) {
            MainMenuActivity.stringSet.remove(idAndCountList.get(i));



/*
           j=i;
            final double count=Double.parseDouble(idAndCountList.get(++i));

            DatabaseReference bookRef=db.child("bookstore/book/"+i+"/count");
            bookRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    double oldCount=Double.parseDouble(dataSnapshot.getValue().toString());
                    double newCount=oldCount-count;
                    Log.d("old_count",String.valueOf(oldCount));
                    Log.d("new_count",String.valueOf(newCount));
                    db.child("bookstore/book/"+j+"/count").setValue(newCount);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

            /*
            DatabaseReference catRef=fb.child("bookstore/category/"+categoryID+"/category_name");
        catRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String categoryName=dataSnapshot.getValue().toString();
                TextView tvKindOfBook=$TV(R.id.tvKindOfBook);
                tvKindOfBook.setText(categoryName);
                tvKindOfBook.setTypeface(Typeface.createFromAsset(
                        getAssets(), "fonts/5.ttf"));
                tvKindOfBook.setTextSize(42);
            }

            DatabaseReference bookRef=db.child("bookstore/book/"+i+"/count");
            bookRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    double oldCount=Double.parseDouble(dataSnapshot.getValue().toString());
                    double newCount=oldCount-Double.parseDouble(idAndCountList.get(++j));
                    Log.d("old_count",String.valueOf(oldCount));
                    Log.d("new_count",String.valueOf(newCount));
                    db.child("bookstore/book/"+(--j)+"/count").setValue(newCount);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
*/
        }
        SharedPreferences sp = getSharedPreferences("myPref", MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putStringSet("booksIDs", MainMenuActivity.stringSet);
        editor.apply();
        finish();
    }

    public void onBtnToOrdersClick(View view) {
        Intent intent = new Intent(this, OrdersActivity.class);
        startActivity(intent);
    }
}
