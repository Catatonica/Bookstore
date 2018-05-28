package izenka.hfad.com.bookstore.view.basket;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.StorageReference;

import java.util.Set;

import izenka.hfad.com.bookstore.R;
import izenka.hfad.com.bookstore.model.db_operations.ReadBooksForBasket;
import izenka.hfad.com.bookstore.presenter.BasketPresenter;
import izenka.hfad.com.bookstore.view.main_menu.MainMenuActivity;
import mehdi.sakout.fancybuttons.FancyButton;
import stanford.androidlib.SimpleActivity;

public class BasketActivity extends SimpleActivity implements IBasketView {

    private DatabaseReference fb;
    private LinearLayout llBasket;
    private LinearLayout llBooks;
    private RelativeLayout rlAction;
    private View filledBasket;
    private View emptyBasket;
    private LinearLayout activityBasket;
    private TextView tvTotalPrise;

    private BasketPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basket);
        // setContentView(R.layout.empty_basket);
        if (presenter == null) {
            presenter = new BasketPresenter(this, getSharedPreferences("myPref", MODE_PRIVATE));
        }
        presenter.onViewCreated();
    }

    @Override
    public void initViews() {
        activityBasket = (LinearLayout) findViewById(R.id.activityBasket);
    }

    @Override
    public void addEmptyBasketView() {
        emptyBasket = getLayoutInflater().inflate(R.layout.empty_basket, null);
        activityBasket.addView(emptyBasket);
    }

    @Override
    public void addFilledBasketView() {
        filledBasket = getLayoutInflater().inflate(R.layout.filled_basket, null);
        activityBasket.addView(filledBasket);
    }

    @Override
    public void onBackClick() {
        onBackPressed();
    }

    @Override
    public void queryBook(String bookID, Set<View> checkedViewSet) {
        ReadBooksForBasket.queryBook(this, bookID, checkedViewSet, presenter);
    }

    @Override
    public void animate(View view) {
        Animation anim = AnimationUtils.loadAnimation(this, R.anim.alpha);
        view.startAnimation(anim);
    }

    @Override
    public void initEmptyBasketViews() {
        findViewById(R.id.btnReturnFromBasket).setOnClickListener(view -> presenter.onBackClicked());
    }

    @Override
    public void removeBook(int id) {
        llBooks.post(new Runnable() {
            public void run() {
                llBooks.removeView(findViewById(id));
            }
        });
    }

    @Override
    public void onDeleteClick() {
        rlAction.setVisibility(View.INVISIBLE);

        if (MainMenuActivity.stringSet.isEmpty()) {
            emptyBasket = getLayoutInflater().inflate(R.layout.empty_basket, null);
            activityBasket.removeView(filledBasket);
            activityBasket.addView(emptyBasket);
            initEmptyBasketViews();
            // setContentView(R.layout.empty_basket);
        }
    }

    @Override
    public void initFilledBasketViews() {
        llBasket = (LinearLayout) findViewById(R.id.llBasket);
        llBooks = (LinearLayout) llBasket.findViewById(R.id.llBooks);
        $IV(R.id.btnGoBackFromBasket).setOnClickListener(view -> presenter.onBackClicked());
        llBasket.findViewById(R.id.btnChooseEth).setOnClickListener(view -> presenter.onChooseEthClicked((FancyButton) view));
        rlAction = (RelativeLayout) llBasket.findViewById(R.id.rlAction);
        rlAction.findViewById(R.id.btnDelete).setOnClickListener(view -> presenter.onDeleteClicked(view));
        rlAction.findViewById(R.id.btnRegister).setOnClickListener(view -> presenter.onRegisterClicked(view));
        tvTotalPrise = (TextView) llBasket.findViewById(R.id.tvTotalPriceForAll);
    }

    @Override
    public void loadImage(StorageReference imageRef, ImageView imgBtnBook) {
        Glide.with(this /* context */)
             .using(new FirebaseImageLoader())
             .load(imageRef)
             .into(imgBtnBook);
    }

    @Override
    public void setTotalPrise(String prise) {
        tvTotalPrise.setText(prise);
    }

    @Override
    public void startActivity(Intent intent, Class activity) {
        intent.setClass(this, activity);
        startActivity(intent);
        finish();
    }

    @Override
    public void startActivityWithAnimation(View view, Class activity) {

    }

    @Override
    public void startActivityWithAnimation(View view, Intent intent, Class activity) {

    }

    @Override
    public void showToast(String message, int duration) {
        Toast.makeText(this, message, duration).show();
    }

    @Override
    public void addBookView(View oneBookInBasketView) {
        llBooks.addView(oneBookInBasketView);
    }
}