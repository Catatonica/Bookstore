package izenka.hfad.com.bookstore.presenter;


import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import izenka.hfad.com.bookstore.model.db_operations.WriteOrder;
import izenka.hfad.com.bookstore.model.db_classes.Order;
import izenka.hfad.com.bookstore.main_menu.view.MainMenuActivity;
import izenka.hfad.com.bookstore.view.orders.OrdersActivity;
import izenka.hfad.com.bookstore.view.registration.IRegistrationView;

public class RegistrationPresenter implements IPresenter {

    private IRegistrationView registrationView;

    private Intent intent;
    private DatabaseReference db;
    private double totalPrice;
    private ArrayList<String> idAndCountList;
    private SharedPreferences sp;

    public RegistrationPresenter(IRegistrationView registrationView,
                                 Intent intent,
                                 SharedPreferences sp) {
        this.registrationView = registrationView;
        this.intent = intent;
        this.sp = sp;
    }

    @Override
    public void onViewCreated() {
        registrationView.initViews();
        idAndCountList = intent.getStringArrayListExtra("idAndCount");
        totalPrice = intent.getDoubleExtra("totalPrice", 0);
        db = FirebaseDatabase.getInstance().getReference();
    }

    public void onBackClicked() {
        registrationView.onBackClick();
    }

    public void onRegisterClicked(View view) {
        registrationView.onRegisterClick(view);
    }

    public void showToast() {
        registrationView.showToast("Заполните все поля, помеченные знаком *", Toast.LENGTH_SHORT);
    }

    public void writeNewOrder(String phoneNumber,
                              String name,
                              String city,
                              String street,
                              String house,
                              String flatNumber,
                              String porchNumber,
                              String floor) {

        String key = db.child("bookstore/order").push().getKey();
        DateFormat df = new SimpleDateFormat("EEE, d MMM yyyy, HH:mm", Locale.getDefault());
        String date = df.format(Calendar.getInstance().getTime());
        // Log.d("date", date);

        Order order = new Order(key, date, totalPrice, phoneNumber, name,/* count, book_id,*/ city, street, house, flatNumber, porchNumber, floor);
        for (int i = 0; i < idAndCountList.size(); i++) {
            order.defineIdAndCount(idAndCountList.get(i), idAndCountList.get(++i));
        }

        WriteOrder.writeNewOrder(key, order);

        SharedPreferences.Editor editor = sp.edit();
        if (!MainMenuActivity.ordersSet.contains(key)) {
            MainMenuActivity.ordersSet.add(key);
        }
        editor.putStringSet("ordersIDs", MainMenuActivity.ordersSet);
        editor.apply();
        registrationView.showSucceedMessage();
    }

    public void onOkClicked(SharedPreferences sp) {
        for (int i = 0; i < idAndCountList.size(); i += 2) {
            MainMenuActivity.stringSet.remove(idAndCountList.get(i));
        }
        SharedPreferences.Editor editor = sp.edit();
        editor.putStringSet("booksIDs", MainMenuActivity.stringSet);
        editor.apply();
    }

    public void onToOrdersClicked() {
        registrationView.startActivity(intent, OrdersActivity.class);
    }


}
