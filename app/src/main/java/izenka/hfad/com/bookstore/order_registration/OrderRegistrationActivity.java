package izenka.hfad.com.bookstore.order_registration;

import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import izenka.hfad.com.bookstore.AccountListener;
import izenka.hfad.com.bookstore.R;
import izenka.hfad.com.bookstore.account.AccountViewModel;
import izenka.hfad.com.bookstore.basket.BookIdAndCountModel;
import izenka.hfad.com.bookstore.book.BookActivity;
import izenka.hfad.com.bookstore.orders.OrdersActivity;
import mehdi.sakout.fancybuttons.FancyButton;

//TODO: make OrdersActivity and layout for it
//where user can view all orders and delete if necessary

public class OrderRegistrationActivity extends AppCompatActivity implements AccountListener{

    private DatabaseReference db;
    //    private double totalPrice;
    //private Map<String, String> idAndCountMap;
    private ArrayList<String> idAndCountList;

    private static final int PLACE_PICKER_REQUEST = 1;


    private EditText etPhoneNumber;
    private EditText etName;
    private EditText etCity;
    private EditText etStreet;
    private EditText etHouse;
    private EditText etPorchNumber;
    private EditText etFlatNumber;
    private EditText etFloor;
    private EditText etAddress;
    private FancyButton btnRegister;
    private EditText etEmail;
    private Button btnAddLocation;

    private float totalPrice;
    private AccountViewModel accountViewModel;
    private Map<String, Integer> ordersMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        Intent intent = getIntent();
//        Log.d("idAndCount", intent.getStringArrayListExtra("idAndCount").toString());

        totalPrice = intent.getFloatExtra("totalPrice", 0);
        List<BookIdAndCountModel> bookIDsAndCount = intent.getParcelableArrayListExtra("bookIDsAndCount");
        ordersMap = new HashMap<>();
        for (BookIdAndCountModel b : bookIDsAndCount) {
            ordersMap.put(String.valueOf(b.bookID), b.count);
        }
//        idAndCountList = intent.getStringArrayListExtra("idAndCount");
//        totalPrice = intent.getDoubleExtra("totalPrice", 0);

        initViews();
        setToolbar();

        accountViewModel = ViewModelProviders.of(this).get(AccountViewModel.class);
//        viewModel.loadUser();
        accountViewModel.getUserLiveData().observe(this, user -> {
            Log.d("userInfo", "observeUserLiveData, user = " + user);
            if (user != null) {
                etEmail.setText(user.email);
                etName.setText(String.format("%s %s", user.name, user.surname));
                etPhoneNumber.setText(user.phone);
                if (user.Address != null) {
                    etCity.setText((String) user.Address.get("city"));
                    etStreet.setText((String) user.Address.get("street"));
                    etHouse.setText((String) user.Address.get("house"));
                    etFlatNumber.setText((String) user.Address.get("flat"));
                }
            }
        });
//        etEmail.setText(accountViewModel.getUser().getEmail());

        // idAndCountMap=new HashMap<>();
        // for(int i=0; i<idAndCountList.size();i++){
        //     idAndCountMap.put(idAndCountList.get(i),idAndCountList.get(++i));
        // }
        // Log.d("idAndCount",idAndCountMap.toString());
        db = FirebaseDatabase.getInstance().getReference();
    }

    private void initViews() {
        etPhoneNumber = findViewById(R.id.etPhoneNumber);
        etName = findViewById(R.id.etName);
        etCity = findViewById(R.id.etCity);
        etStreet = findViewById(R.id.etStreet);
        etPorchNumber = findViewById(R.id.etPorchNumber);
        etHouse = findViewById(R.id.etHouse);
        etFlatNumber = findViewById(R.id.etFlatNumber);
        etFloor = findViewById(R.id.etFloor);
        etEmail = findViewById(R.id.etEmail);
        etAddress = findViewById(R.id.etAddress);
        btnAddLocation = findViewById(R.id.btnAddLocation);
        btnAddLocation.setOnClickListener(btn -> addLocation());
        btnRegister = findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(btn -> {
            register();
        });
    }

    private void addLocation() {
        PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
        try {
            startActivityForResult(builder.build(this), PLACE_PICKER_REQUEST);
        } catch (GooglePlayServicesRepairableException e) {
            Log.d("place", "GooglePlayServicesRepairableException");
            e.printStackTrace();
        } catch (GooglePlayServicesNotAvailableException e) {
            Log.d("place", "GooglePlayServicesNotAvailableException");
            e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("place", "result code = "+resultCode);
        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == RESULT_OK) {
                Place place = PlacePicker.getPlace(this, data);
                etAddress.setText(place.getAddress());
                etCity.setText("");
                etCity.setHint("");
                etStreet.setText("");
                etStreet.setHint("");
                etHouse.setText("");
                etHouse.setHint("");
                etFlatNumber.setText("");
                etPorchNumber.setText("");
                etFloor.setText("");
//                String toastMsg = String.format("Place: %s", place.getName());
//                Toast.makeText(this, toastMsg, Toast.LENGTH_LONG).show();
            }
        }
    }

    private void setToolbar() {
        setSupportActionBar(findViewById(R.id.toolbar));
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(R.string.orderRegistration);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_arrow_back_white_24dp);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    public void register(/*View view*/) {
//        Animation anim = AnimationUtils.loadAnimation(this, R.anim.alpha);
//        view.startAnimation(anim);

//        String key = db.child("bookstore/order").push().getKey();

        String phoneNumber = etPhoneNumber.getText().toString();
        String city = etCity.getText().toString();
        String street = etStreet.getText().toString();
        String house = etHouse.getText().toString();
        String flat = etFlatNumber.getText().toString();
        String porch = etPorchNumber.getText().toString();
        String floor = etFloor.getText().toString();
        String name = etName.getText().toString();
        String email = etEmail.getText().toString();


        if (phoneNumber.isEmpty() || city.isEmpty() || street.isEmpty() || house.isEmpty() /*|| flat.isEmpty()*/) {
            Toast.makeText(this, "Заполните все поля, помеченные знаком *", Toast.LENGTH_SHORT).show();
        } else {

            DateFormat df = new SimpleDateFormat("EEE, d MMM yyyy, HH:mm", Locale.getDefault());
            String date = df.format(Calendar.getInstance().getTime());

            Map<String, Object> addressMap = new HashMap<>();
            addressMap.put("city", city);
            addressMap.put("street", street);
            addressMap.put("house", house);
            addressMap.put("flat", flat);
            addressMap.put("porch", porch);
            addressMap.put("floor", floor);

            String fullAddress = String.format("%s, %s, %s", city, street, house);

            OrderRegistrationModel order = new OrderRegistrationModel(date,
                                                                      totalPrice,
                                                                      name,
                                                                      phoneNumber,
                                                                      getUser().getUid(),
                                                                      email,
                                                                      fullAddress,
                                                                      ordersMap,
                                                                      addressMap,
                                                                      "выполняется"
            );
            OrderRegistrationViewModel orderViewModel = ViewModelProviders.of(this).get(OrderRegistrationViewModel.class);
            orderViewModel.writeNewOrder(order);
//            writeNewOrder(key, idAndCountList, totalPrice,
//                          etPhoneNumber.getText().toString(),
//                          etName.getText().toString(),
//                          etCity.getText().toString(),
//                          etStreet.getText().toString(),
//                          etHouse.getText().toString(),
//                          etFlatNumber.getText().toString(),
//                          etPorchNumber.getText().toString(),
//                          etFloor.getText().toString()
//            );
            showSucceedMessage();
        }
    }

//    private void writeNewOrder(String order_id, ArrayList<String> idAndCount, Double price, String userPhone,
//                               String username, String city, String street, String house, String flatNumber,
//                               String porchNumber, String floor) {
//
//        DateFormat df = new SimpleDateFormat("EEE, d MMM yyyy, HH:mm", Locale.getDefault());
//        String date = df.format(Calendar.getInstance().getTime());
//        Log.d("date", date);
//
//        Order order = new Order(order_id, date, price, userPhone, username,/* count, book_id,*/ city, street, house, flatNumber
//                , porchNumber, floor);
//        for (int i = 0; i < idAndCount.size(); i++) {
//            order.defineIdAndCount(idAndCount.get(i), idAndCount.get(++i));
//        }
//
//        Map<String, String> Books = order.booksToMap();
//        Map<String, String> Address = order.addressToMap();
//        Map<String, Object> newOrder = order.toMap();
//
//        Map<String, Object> childUpdates = new HashMap<>();
//        childUpdates.put("bookstore/order/" + order_id, newOrder);
//
//        Map<String, Object> orderUpdates = new HashMap<>();
//        orderUpdates.put("bookstore/order/" + order_id + "/Books", Books);
//        orderUpdates.put("bookstore/order/" + order_id + "/Address", Address);
//
//        db.updateChildren(childUpdates);
//        db.updateChildren(orderUpdates);
//
//        SharedPreferences sp = getSharedPreferences("ordersPref", MODE_PRIVATE);
//        SharedPreferences.Editor editor = sp.edit();
//        if (!MainMenuActivity.ordersSet.contains(order_id)) {
//            MainMenuActivity.ordersSet.add(order_id);
//        }
//        editor.putStringSet("ordersIDs", MainMenuActivity.ordersSet);
//        editor.apply();
//    }

    private void showSucceedMessage() {
//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        LayoutInflater inflater = this.getLayoutInflater();
//        builder.setView(inflater.inflate(R.layout.succeed_message, null)).show();

        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.MyDialogTheme);
        builder.setTitle(getString(R.string.registration));
        builder.setMessage(getString(R.string.succeed_message));

        String positiveText = getString(android.R.string.ok);
        builder.setPositiveButton(positiveText,
                                  new DialogInterface.OnClickListener() {
                                      @Override
                                      public void onClick(DialogInterface dialog, int which) {
                                          // positive button logic
                                          onBtnOKClicked();
                                      }
                                  });

        String negativeText = getString(R.string.to_orders);
        builder.setNegativeButton(negativeText,
                                  new DialogInterface.OnClickListener() {
                                      @Override
                                      public void onClick(DialogInterface dialog, int which) {
                                          // negative button logic
                                          onBtnToOrdersClicked();
                                      }
                                  });

        AlertDialog dialog = builder.create();
        // display dialog
        dialog.show();
    }


    private void onBtnOKClicked() {
        //TODO:remove books count from firebase
//        for (int i = 0; i < idAndCountList.size(); i += 2) {
//            MainMenuActivity.stringSet.remove(idAndCountList.get(i));

        //TODO
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
        //TODO
//        }

//        SharedPreferences sp = getSharedPreferences("myPref", MODE_PRIVATE);
//        SharedPreferences.Editor editor = sp.edit();
//        editor.putStringSet("booksIDs", MainMenuActivity.stringSet);
//        editor.apply();
        Intent intent = new Intent(getApplicationContext(), BookActivity.class);
        intent.putExtra("bookID", getIntent().getIntExtra("bookID", 0));
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    private void onBtnToOrdersClicked() {
        Intent intent = new Intent(this, OrdersActivity.class);
        startActivity(intent);
    }
}
