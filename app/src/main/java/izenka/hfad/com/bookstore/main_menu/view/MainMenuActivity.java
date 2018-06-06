package izenka.hfad.com.bookstore.main_menu.view;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

import java.util.HashSet;
import java.util.Set;

import izenka.hfad.com.bookstore.R;
import izenka.hfad.com.bookstore.main_menu.presenter.IMainMenuPresenter;
import izenka.hfad.com.bookstore.main_menu.presenter.MainMenuPresenterImpl;
import stanford.androidlib.SimpleActivity;

public class MainMenuActivity extends SimpleActivity implements IMainMenuView {

    public static Set<String> stringSet = new HashSet<>();
    public static Set<String> ordersSet = new HashSet<>();

    private IMainMenuPresenter presenter;
    private DrawerLayout mDrawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        setToolbar();

//        stringSet =  getSharedPreferences("myPref", MODE_PRIVATE).getStringSet("booksIDs", null);
//        if(stringSet == null){
//            stringSet  = new HashSet<>();
//        }

        if (presenter == null) {
            presenter = new MainMenuPresenterImpl(this);
        }
        presenter.onViewCreated();
    }

    @Override
    public void setToolbar() {
        setSupportActionBar(findViewById(R.id.toolbar));
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu_white_24dp);
        }
    }

    @Override
    public void initViews() {
        mDrawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(menuItem -> presenter.onNavigationItemSelected(menuItem));
        //TODO: uncomment, create fragment
//        $TV(R.id.tvRunningLine).setSelected(true);
//        $ET(R.id.etSearch).setOnClickListener(view -> presenter.onSearchClicked());
//        $B(R.id.btnForeign).setOnClickListener(view -> presenter.onCategoryClicked(view));
//        $B(R.id.btnKid).setOnClickListener(view -> presenter.onCategoryClicked(view));
//        $B(R.id.btnBusiness).setOnClickListener(view -> presenter.onCategoryClicked(view));
//        $B(R.id.btnFiction).setOnClickListener(view -> presenter.onCategoryClicked(view));
//        $B(R.id.btnStudy).setOnClickListener(view -> presenter.onCategoryClicked(view));
//        $B(R.id.btnNonfiction).setOnClickListener(view -> presenter.onCategoryClicked(view));
//        $B(R.id.btnScan).setOnClickListener(view -> presenter.onScanClicked(view));
    }

    @Override
    public void setFragment(Fragment fragment) {
        Fragment mainMenuFragment = getSupportFragmentManager().findFragmentById(R.id.content_frame);
        if (mainMenuFragment == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.content_frame, fragment)
                    .commit();
        }
    }

    @Override
    public boolean onNavigationItemSelect(MenuItem menuItem) {
//        // set item as selected to persist highlight
//        menuItem.setChecked(true);
        // close drawer when item is tapped
        mDrawerLayout.closeDrawers();

        switch (menuItem.getItemId()) {
            case R.id.nav_basket:
                return true;
            case R.id.nav_catalogue:
                return true;
            case R.id.nav_info:
                return true;
            case R.id.nav_map:
                return true;
            case R.id.nav_profile:
                return true;
            case R.id.nav_purchases:
                return true;
            default:
                return true;
        }
        // Add code here to update the UI based on the item selected
        // For example, swap UI fragments here
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.actionbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_basket:
                presenter.onBasketClicked();
                return true;

            case R.id.action_orders:
                presenter.onOrdersClicked();
                return true;

            case R.id.action_qrcode:
                presenter.onScanClicked();
                return true;

            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }


    @Override
    public void startActivityWithAnimation(View view, Class activity) {
        Animation anim = AnimationUtils.loadAnimation(this, R.anim.alpha);
        view.startAnimation(anim);
        Intent intent = new Intent(this, activity);
        startActivity(intent);
    }

    @Override
    public void startActivity(Intent intent, Class activity) {
        intent.setClass(this, activity);
        startActivity(intent);
    }

    @Override
    public void startActivityWithAnimation(View view, Intent intent, Class activity) {
        Animation anim = AnimationUtils.loadAnimation(this, R.anim.alpha);
        view.startAnimation(anim);
        intent.setClass(this, activity);
        startActivity(intent);
    }

    @Override
    public void showCategoriesNames() {
        //TODO: Make repository btnArray CatNamesArray
        Button[] btnArray = {
                $B(R.id.btnForeign),
                $B(R.id.btnKid),
                $B(R.id.btnBusiness),
                $B(R.id.btnFiction),
                $B(R.id.btnStudy),
                $B(R.id.btnNonfiction)
        };

        String[] stringArray = getResources().getStringArray(R.array.categoriesNames);

        for (int i = 0; i < btnArray.length; i++) {
            btnArray[i].setText(stringArray[i]);
            btnArray[i].setTextSize(36);
            btnArray[i].setTypeface(Typeface.createFromAsset(getAssets(), "fonts/5.ttf"));
        }
    }

//    @Override
//    public void onViewCreated() {
//        presenter.showCategoriesNames();
//        $TV(R.id.tvRunningLine).setSelected(true);
//        $ET(R.id.etSearch).setOnClickListener(view -> presenter.onSearchClicked());
//        $B(R.id.btnForeign).setOnClickListener(view -> presenter.onCategoryClicked(view));
//        $B(R.id.btnKid).setOnClickListener(view -> presenter.onCategoryClicked(view));
//        $B(R.id.btnBusiness).setOnClickListener(view -> presenter.onCategoryClicked(view));
//        $B(R.id.btnFiction).setOnClickListener(view -> presenter.onCategoryClicked(view));
//        $B(R.id.btnStudy).setOnClickListener(view -> presenter.onCategoryClicked(view));
//        $B(R.id.btnNonfiction).setOnClickListener(view -> presenter.onCategoryClicked(view));
////        $B(R.id.btnScan).setOnClickListener(view -> presenter.onScanClicked(view));
//    }
}