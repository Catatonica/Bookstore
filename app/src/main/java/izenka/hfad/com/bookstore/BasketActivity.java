package izenka.hfad.com.bookstore;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.RelativeLayout;
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

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import stanford.androidlib.SimpleActivity;

public class BasketActivity extends SimpleActivity {

    private DatabaseReference fb;
    private LinearLayout llBasket;
    private LinearLayout llBooks;
    private SharedPreferences sp;
    private Set<View> viewSet=new HashSet<>();
    private Set<View> checkedViewSet=new HashSet<>();
    private Animation anim;
    private double totalPrice;
    private double priceForSeveral;
    private View filledBasket;
    private View emptyBasket;
    private LinearLayout activityBasket;

    //TODO: to show book's price and total price

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basket);
       // setContentView(R.layout.empty_basket);
        activityBasket=(LinearLayout) findViewById(R.id.activityBasket);

        anim = AnimationUtils.loadAnimation(this, R.anim.alpha);
        sp=getSharedPreferences("myPref",MODE_PRIVATE);

        MainMenuActivity.stringSet = sp.getStringSet("booksIDs",null);
        Log.d("stringSet",MainMenuActivity.stringSet.toString());
        //if there are no orders in the basket, then show layout of empty_basket, otherwise -- activity_basket
        if(MainMenuActivity.stringSet.isEmpty()){
            Log.d("stringSet", " is empty");
            //setContentView(R.layout.empty_basket);
            emptyBasket= getLayoutInflater().inflate(R.layout.empty_basket,null);
            activityBasket.addView(emptyBasket);
        }
        else{
            filledBasket= getLayoutInflater().inflate(R.layout.filled_basket,null);
            activityBasket.addView(filledBasket);
            fillTheBasket();
        }
    }

    private void fillTheBasket(){
        llBasket = (LinearLayout) findViewById(R.id.llBasket);
        llBooks = (LinearLayout) llBasket.findViewById(R.id.llBooks);
        fb = FirebaseDatabase.getInstance().getReference();
        final DatabaseReference bookRef = fb.child("bookstore/book");

        if (!MainMenuActivity.stringSet.isEmpty()) {
            for (final String bookID : MainMenuActivity.stringSet) {

                final Query queryBook = bookRef.orderByChild("book_id").equalTo(Integer.parseInt(bookID));
                queryBook.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot data) {
                        final View oneBookInBasketView = getLayoutInflater().inflate(R.layout.book_in_basket, null);
                        oneBookInBasketView.setId(Integer.parseInt(bookID));

                        priceForSeveral=0;

                        final Book book = data.getChildren().iterator().next().getValue(Book.class);
                        TextView tvBookTitle = (TextView) oneBookInBasketView.findViewById(R.id.tvBookTitle);
                        tvBookTitle.setText(book.title);
                        TextView tvPublicationYear = (TextView) oneBookInBasketView.findViewById(R.id.tvPublicationYear);
                        tvPublicationYear.setText(String.valueOf(book.publication_year));

                        TextView bookPrice=(TextView) oneBookInBasketView.findViewById(R.id.tvPriceForOne);
                        bookPrice.setText(book.price);
                        //TODO: make price double
                        final TextView tvPriceForSeveral=(TextView) oneBookInBasketView.findViewById(R.id.tvPriceForSeveral);
                        tvPriceForSeveral.setText("/"+book.price+" р.");

                        CheckBox checkBox=(CheckBox) oneBookInBasketView.findViewById(R.id.checkBox);
                        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                RelativeLayout rlAction=(RelativeLayout) llBasket.findViewById(R.id.rlAction);
                                if(isChecked){
                                    checkedViewSet.add(oneBookInBasketView);
                                    rlAction.setVisibility(View.VISIBLE);
                                }
                                else if(!isChecked){
                                    checkedViewSet.remove(oneBookInBasketView);
                                    if(checkedViewSet.isEmpty())
                                        rlAction.setVisibility(View.INVISIBLE);
                                }
                                setTotalPrice();
                            }
                        });

                        final NumberPicker numberPicker = (NumberPicker) oneBookInBasketView.findViewById(R.id.npBookCount);
                        numberPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                            @Override
                            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                                String [] bookPrice=book.price.split(" ");
                                priceForSeveral=Double.parseDouble(bookPrice[0])*picker.getValue();
                                tvPriceForSeveral.setText("/"+priceForSeveral+" р.");
                                if(oneBookInBasketView.findViewById(R.id.checkBox).isEnabled())setTotalPrice();
                            }
                        });

                        TextView tvBookAvailability = (TextView) oneBookInBasketView.findViewById(R.id.tvBookAvailability);
                        if (book.count != 0) {
                            tvBookAvailability.setText("в наличии");
                            numberPicker.setMaxValue(book.count);
                            numberPicker.setMinValue(1);
                        } else {
                            tvBookAvailability.setText("нет в наличии");
                            numberPicker.setMaxValue(0);
                            numberPicker.setMinValue(0);
                        }

                        List<Integer> Authors = new ArrayList<>();
                        for (DataSnapshot authID : data.getChildren().iterator().next().child("Authors").getChildren()) {
                            Authors.add(Integer.parseInt(String.valueOf(authID.getValue())));
                        }
                        setAuthor(Authors, oneBookInBasketView);

                        List<String> Images = new ArrayList<>();
                        for (DataSnapshot imagesID : data.getChildren().iterator().next().child("Images").getChildren()) {
                            Images.add(imagesID.getValue().toString());
                        }
                        setImage(Images, book.book_id, oneBookInBasketView);

                        llBooks.addView(oneBookInBasketView);
                        viewSet.add(oneBookInBasketView);

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        } else {
            View emptyBasketView = getLayoutInflater().inflate(R.layout.empty_basket, null);
            llBasket.addView(emptyBasketView);
        }
    }

    private void setTotalPrice(){
        totalPrice=0;
        for(View v: checkedViewSet) {
                // idAndCountMap.put(v.getId(),numberPicker.getValue());
                TextView tvPriceForSeveral=(TextView) v.findViewById(R.id.tvPriceForSeveral);
                String[] price1=tvPriceForSeveral.getText().toString().split("/");
                String[] price2=price1[1].split(" ");
                totalPrice+=Double.parseDouble(price2[0]);
                //  totalPrice+=Double.parseDouble(price[0])*count;

        }
        TextView tvTotalPrice=(TextView) llBasket.findViewById(R.id.tvTotalPriceForAll) ;
        tvTotalPrice.setText(String.valueOf(totalPrice)+" р.");
    }

    public void onBtnBackClick(View view) {
        view.startAnimation(anim);
        finish();
    }

    private void setAuthor( List<Integer> authorsID, final View view) {
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
                    TextView tvBookAuthor = (TextView) view.findViewById(R.id.tvAuthorName);
                    tvBookAuthor.setText(auth.substring(1, auth.length() - 1));
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
    }

    private void setImage(List <String> Images, int bookID, View view){
        FirebaseStorage storage = FirebaseStorage.getInstance();
        String bookImage=Images.get(0);
        StorageReference imageRef = storage.getReference().child(bookImage);

        ImageView imgBtnBook=(ImageView) view.findViewById(R.id.ivBook);
        imgBtnBook.setId(bookID);

        Glide.with(this /* context */)
                .using(new FirebaseImageLoader())
                .load(imageRef)
                .into(imgBtnBook);

    }

    public void onReturnBackClick(View view) {
        finish();
    }

    public void onDeleteClick(View view) {
        view.startAnimation(anim);

         for(final View v: checkedViewSet){
        // for(View v: viewSet){
            CheckBox checkBox=(CheckBox) v.findViewById(R.id.checkBox);
          //  if(checkBox.isChecked()){
               // String str=booksIDs.toString();
                //str.replace(String.valueOf(v.getId()),"");
                MainMenuActivity.stringSet.remove(String.valueOf(v.getId()));
                SharedPreferences.Editor editor=sp.edit();
                Log.d("stringSet",MainMenuActivity.stringSet.toString());
                editor.putStringSet("booksIDs",MainMenuActivity.stringSet);
                editor.apply();
                //llBooks.removeViewAt(v.getId());
             checkedViewSet.remove(v);
             setTotalPrice();
             llBooks.post(new Runnable() {
                public void run() {
                 llBooks.removeView(findViewById(v.getId()));
                }
                 });

             //llBooks.removeViewInLayout(findViewById(v.getId()));
               // llBooks.updateViewLayout(llBooks.getFocusedChild(),llBooks.getLayoutParams());
           // }
        }



        RelativeLayout rlAction=(RelativeLayout) llBasket.findViewById(R.id.rlAction);
        rlAction.setVisibility(View.INVISIBLE);

        if(MainMenuActivity.stringSet.isEmpty()){
            emptyBasket= getLayoutInflater().inflate(R.layout.empty_basket,null);
            activityBasket.removeView(filledBasket);
            activityBasket.addView(emptyBasket);
           // setContentView(R.layout.empty_basket);
        }
    }

    public void onRegisterClick(View view) {
        view.startAnimation(anim);

       // Map<Integer, Integer> idAndCountMap=new HashMap<>();
        ArrayList<String> idAndCountMap=new ArrayList<>();
        //go through all checked views and get their ids and count
        for(View v: viewSet) {
            CheckBox checkBox = (CheckBox) v.findViewById(R.id.checkBox);
            NumberPicker numberPicker=(NumberPicker) v.findViewById(R.id.npBookCount);
            if (checkBox.isChecked()) {
               // idAndCountMap.put(v.getId(),numberPicker.getValue());
                int count=numberPicker.getValue();
           //     TextView tvPriceForSeveral=(TextView) v.findViewById(R.id.tvPriceForSeveral);
            //    String[] price1=tvPriceForSeveral.getText().toString().split("/");
             //   String[] price2=price1[1].split(" ");
             //   totalPrice+=Double.parseDouble(price2[0]);
             //   Log.d("totalPrice",String.valueOf(totalPrice));
              //  totalPrice+=Double.parseDouble(price[0])*count;
                idAndCountMap.add(String.valueOf(v.getId()));
                idAndCountMap.add(String.valueOf(count));
            }
        }

        if(!checkedViewSet.isEmpty()){
            Intent intent=new Intent(this,OrderRegistrationActivity.class);
            //TODO: put extra
            // intent.putExtra("idAndCount",idAndCountMap.toString());
            intent.putStringArrayListExtra("idAndCount",idAndCountMap);
            startActivity(intent);
        }
            else{
            Toast.makeText(this,"Прежде выберите товар",Toast.LENGTH_SHORT).show();
        }

    }

    public void onChooseEthClick(View view) {
        mehdi.sakout.fancybuttons.FancyButton btnOnChooseEthClick=(mehdi.sakout.fancybuttons.FancyButton) llBasket.findViewById(R.id.btnChooseEth);

        btnOnChooseEthClick.startAnimation(anim);

        if(btnOnChooseEthClick.getText().equals("выбрать все")){
            for(View v: viewSet){
                CheckBox checkBox=(CheckBox) v.findViewById(R.id.checkBox);
                checkBox.setChecked(true);
            }
            btnOnChooseEthClick.setText("снять все");
        }
        else {
            for (View v : viewSet) {
                CheckBox checkBox = (CheckBox) v.findViewById(R.id.checkBox);
                checkBox.setChecked(false);
            }
            btnOnChooseEthClick.setText("выбрать все");

        }
    }
}