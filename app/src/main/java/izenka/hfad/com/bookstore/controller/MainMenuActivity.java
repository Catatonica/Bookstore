package izenka.hfad.com.bookstore.controller;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

import java.util.HashSet;
import java.util.Set;

import izenka.hfad.com.bookstore.R;
import izenka.hfad.com.bookstore.controller.order.OrdersActivity;
import stanford.androidlib.SimpleActivity;

public class MainMenuActivity extends SimpleActivity {

    private static final String FIREBASE_USERNAME = "izenka666@gmail.com";
    private static final String FIREBASE_PASSWORD = "sepultura777";

    private static final String CATEGORY_ID = "categoryID";

    public static Set<String> stringSet = new HashSet<>();
    public static Set<String> ordersSet = new HashSet<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        $TV(R.id.tvRunningLine).setSelected(true);

        connectToDB();
        setCategoriesNames();
    }

    private void connectToDB() {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        mAuth.signInWithEmailAndPassword(FIREBASE_USERNAME, FIREBASE_PASSWORD);
    }

    private void setCategoriesNames() {

        Button[] btnArray = {
                $B(R.id.btnForeign),
                $B(R.id.btnKid),
                $B(R.id.btnBusieness),
                $B(R.id.btnFiction),
                $B(R.id.btnStudy),
                $B(R.id.btnNonfiction)
        };

        String[] stringArray = getResources().getStringArray(R.array.categoriesNames);

        for (int i = 0; i < btnArray.length; i++) {
            btnArray[i].setText(stringArray[i]);
            btnArray[i].setTextSize(36);
            btnArray[i].setTypeface(Typeface.createFromAsset(
                    getAssets(), "fonts/5.ttf"));
        }
    }

    public void onKindOfBookClick(View view) {
        Intent intent = new Intent(this, BookListActivity.class);

        switch (view.getId()) {
            case R.id.btnForeign:
                intent.putExtra(CATEGORY_ID, 0);
                break;
            case R.id.btnKid:
                intent.putExtra(CATEGORY_ID, 1);
                break;
            case R.id.btnBusieness:
                intent.putExtra(CATEGORY_ID, 2);
                break;
            case R.id.btnFiction:
                intent.putExtra(CATEGORY_ID, 3);
                break;
            case R.id.btnStudy:
                intent.putExtra(CATEGORY_ID, 4);
                break;
            case R.id.btnNonfiction:
                intent.putExtra(CATEGORY_ID, 5);
                break;
        }

        Animation anim = AnimationUtils.loadAnimation(this, R.anim.my_alpha);
        view.startAnimation(anim);
        startActivity(intent);
    }

    public void onShopCartClick(View view) {
        Animation anim = AnimationUtils.loadAnimation(this, R.anim.alpha);
        view.startAnimation(anim);
        Intent intent = new Intent(this, BasketActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.diagonaltranslate, R.anim.alpha2);
    }

    public void onSearchClick(View view) {
        Intent intent = new Intent(this, SearchActivity.class);
        intent.putExtra("categoryID", -1);
        startActivity(intent);
    }

    public void onOrdersClick(View view) {
        Animation anim = AnimationUtils.loadAnimation(this, R.anim.alpha);
        view.startAnimation(anim);
        Intent intent = new Intent(this, OrdersActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.diagonaltranslate3, R.anim.alpha2);

    }

//    public void onInfoClick(View view) {
//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        LayoutInflater inflater = this.getLayoutInflater();
//        builder.setView(inflater.inflate(R.layout.company_info, null))
//               .setCancelable(true)
//               .show();
//    }

    public void onBtnOKClick(View view) {

    }

    public void onBtnScanClick(View view) {
        Animation anim = AnimationUtils.loadAnimation(this, R.anim.alpha);
        view.startAnimation(anim);
        Intent intent = new Intent(this, QRCodeActivity.class);
        startActivity(intent);
    }
}