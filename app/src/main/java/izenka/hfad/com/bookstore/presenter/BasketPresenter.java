package izenka.hfad.com.bookstore.presenter;


import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import izenka.hfad.com.bookstore.R;
import izenka.hfad.com.bookstore.view.basket.IBasketView;
import izenka.hfad.com.bookstore.view.main_menu.MainMenuActivity;
import izenka.hfad.com.bookstore.view.registration.RegistrationActivity;
import mehdi.sakout.fancybuttons.FancyButton;

//TODO: onRegistrationBack
public class BasketPresenter implements IPresenter{
    private IBasketView basketView;
    private SharedPreferences sp;
    private Set<View> viewSet = new HashSet<>();
    private Set<View> checkedViewSet = new HashSet<>();
    private double totalPrice;

    public BasketPresenter(IBasketView basketView, SharedPreferences sp) {
        this.basketView = basketView;
        this.sp = sp;
    }

    public void onBackClicked() {
        basketView.onBackClick();
    }

    @Override
    public void onViewCreated() {
        basketView.initViews();
        MainMenuActivity.stringSet = sp.getStringSet("booksIDs", null);
        //if there are no orders in the basket, then show layout of empty_basket, otherwise -- activity_basket
        if (MainMenuActivity.stringSet.isEmpty()) {
            basketView.addEmptyBasketView();
            basketView.initEmptyBasketViews();
        } else {
            basketView.addFilledBasketView();
            basketView.initFilledBasketViews();
            fillTheBasket();
        }
    }

    public void onChooseEthClicked(FancyButton button) {
        basketView.animate(button);
        if (button.getText().equals("выбрать все")) {
            for (View v : viewSet) {
                CheckBox checkBox = (CheckBox) v.findViewById(R.id.checkBox);
                checkBox.setChecked(true);
            }
            button.setText("снять все");
        } else {
            for (View v : viewSet) {
                CheckBox checkBox = (CheckBox) v.findViewById(R.id.checkBox);
                checkBox.setChecked(false);
            }
            button.setText("выбрать все");
        }
    }

    public void onDeleteClicked(View view) {
        basketView.animate(view);

        for (final View v : checkedViewSet) {
            MainMenuActivity.stringSet.remove(String.valueOf(v.getId()));
            SharedPreferences.Editor editor = sp.edit();
            editor.putStringSet("booksIDs", MainMenuActivity.stringSet);
            editor.apply();
            basketView.removeBook(v.getId());
        }

        checkedViewSet.clear();
        setTotalPrice();
        basketView.onDeleteClick();
    }

    public void setTotalPrice() {
        totalPrice = 0;
        for (View v : checkedViewSet) {
            // idAndCountMap.put(v.getId(),numberPicker.getValue());
            TextView tvPriceForSeveral = (TextView) v.findViewById(R.id.tvPriceForSeveral);
            String priceForSeveral = tvPriceForSeveral.getText().toString();
            if (priceForSeveral.isEmpty()) {
                TextView tvPrice = (TextView) v.findViewById(R.id.tvPriceForOne);
                String[] price1 = tvPrice.getText().toString().split("/");
                String[] price2 = price1[0].split(" ");
                totalPrice += Double.parseDouble(price2[0]);
            } else {
                String[] price1 = priceForSeveral.split("/");
                String[] price2 = price1[1].split(" ");
                totalPrice += Double.parseDouble(price2[0]);
            }
        }
        basketView.setTotalPrise(String.valueOf(totalPrice) + " р.");
    }

    private void fillTheBasket() {

        if (!MainMenuActivity.stringSet.isEmpty()) {
            for (final String bookID : MainMenuActivity.stringSet) {
                basketView.queryBook(bookID, checkedViewSet);
            }
        } else {
            basketView.addEmptyBasketView();
        }
    }

    public void onRegisterClicked(View view) {
        basketView.animate(view);

        ArrayList<String> idAndCountMap = new ArrayList<>();
        for (View v : viewSet) {
            CheckBox checkBox = (CheckBox) v.findViewById(R.id.checkBox);
            NumberPicker numberPicker = (NumberPicker) v.findViewById(R.id.npBookCount);
            if (checkBox.isChecked()) {
                int count = numberPicker.getValue();
                idAndCountMap.add(String.valueOf(v.getId()));
                idAndCountMap.add(String.valueOf(count));
            }
        }

        if (!checkedViewSet.isEmpty()) {
            Intent intent = new Intent();
            intent.putExtra("totalPrice", totalPrice);
            intent.putStringArrayListExtra("idAndCount", idAndCountMap);
            basketView.startActivity(intent, RegistrationActivity.class);
        } else {
            basketView.showToast("Прежде выберите товар", Toast.LENGTH_SHORT);
        }
    }

    public void loadImage(StorageReference imageRef, ImageView imgBtnBook) {
        basketView.loadImage(imageRef, imgBtnBook);
    }

    public void addBookView(View oneBookInBasketView) {
        basketView.addBookView(oneBookInBasketView);
        viewSet.add(oneBookInBasketView);
    }
}
