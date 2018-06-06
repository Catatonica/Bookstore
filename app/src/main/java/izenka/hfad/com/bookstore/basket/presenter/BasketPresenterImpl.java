package izenka.hfad.com.bookstore.basket.presenter;


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
import izenka.hfad.com.bookstore.basket.view.EmptyBasketFragment;
import izenka.hfad.com.bookstore.basket.view.FilledBasketFragment;
import izenka.hfad.com.bookstore.basket.view.IBasketFragmentView;
import izenka.hfad.com.bookstore.basket.view.IBasketView;
import izenka.hfad.com.bookstore.basket.view.IFilledBasketFragmentView;
import izenka.hfad.com.bookstore.main_menu.view.IMainMenuFragmentView;
import izenka.hfad.com.bookstore.main_menu.view.MainMenuActivity;
import izenka.hfad.com.bookstore.main_menu.view.MainMenuFragment;
import izenka.hfad.com.bookstore.presenter.IPresenter;
import izenka.hfad.com.bookstore.view.registration.RegistrationActivity;
import mehdi.sakout.fancybuttons.FancyButton;

//TODO: onRegistrationBack
public class BasketPresenterImpl implements IBasketPresenter {
    private IBasketView basketView;
    private SharedPreferences sp;
    private Set<View> viewSet = new HashSet<>();

    public Set<View> getCheckedViewSet() {
        return checkedViewSet;
    }

    private Set<View> checkedViewSet = new HashSet<>();
    private double totalPrice;

    private IFilledBasketFragmentView filledFragment;

    public BasketPresenterImpl(IBasketView basketView, SharedPreferences sp) {
        this.basketView = basketView;
        this.sp = sp;
    }

    public void onBackClicked() {
        basketView.onBackClick();
    }

    @Override
    public void onViewCreated() {
        basketView.initViews();
        basketView.setToolbar();
        MainMenuActivity.stringSet = sp.getStringSet("booksIDs", null);
        if (MainMenuActivity.stringSet == null||MainMenuActivity.stringSet.isEmpty()) {
//            basketView.addEmptyBasketView();
//            basketView.initEmptyBasketViews();
            IBasketFragmentView emptyFragment = new EmptyBasketFragment();
            emptyFragment.setPresenter(this);
            basketView.setFragment((EmptyBasketFragment)emptyFragment);
        } else {
//            basketView.addFilledBasketView();
//            basketView.initFilledBasketViews();
//            fillTheBasket();
            filledFragment = new FilledBasketFragment();
            filledFragment.setPresenter(this);
            basketView.setFragment((FilledBasketFragment)filledFragment);
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

//        if (!MainMenuActivity.stringSet.isEmpty()) {
            for (final String bookID : MainMenuActivity.stringSet) {
                basketView.queryBook(bookID, checkedViewSet);
            }
//        } else {
//            basketView.addEmptyBasketView();
//        }
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
        filledFragment.addBookView(oneBookInBasketView);
//        basketView.addBookView(oneBookInBasketView);
        viewSet.add(oneBookInBasketView);
    }
}
