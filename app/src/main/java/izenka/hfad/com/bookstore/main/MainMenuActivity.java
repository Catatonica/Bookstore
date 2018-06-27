package izenka.hfad.com.bookstore.main;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.firebase.database.FirebaseDatabase;

import java.util.HashSet;
import java.util.Set;

import izenka.hfad.com.bookstore.R;
import izenka.hfad.com.bookstore.account.AccountActivity;
import izenka.hfad.com.bookstore.basket.BasketActivity;
import izenka.hfad.com.bookstore.category.CategoryActivity;
import izenka.hfad.com.bookstore.store_map.MapsActivity;
import izenka.hfad.com.bookstore.model.FirebaseManager;
import izenka.hfad.com.bookstore.orders.OrdersActivity;
import izenka.hfad.com.bookstore.view.qr_code.QRCodeActivity;
import izenka.hfad.com.bookstore.view.search.SearchActivity;


//TODO: create Firebase singletones
public class MainMenuActivity extends AppCompatActivity implements CategoriesNavigator {

    public static Set<String> stringSet = new HashSet<>();
    public static Set<String> ordersSet = new HashSet<>();

    //    private IMainMenuPresenter presenter;
    private DrawerLayout mDrawerLayout;
    private NavigationView navigationView;
    private FirebaseManager firebaseManager;

    private MainMenuViewModel viewModel;

    public static final int PICK_USER_REQUEST = 1234;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("Lifecycle", "MainActivity: onCreate()");
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

//        firebaseManager = new FirebaseManager();
//        firebaseManager.connectToFirebase();


        FirebaseDatabase.getInstance().setPersistenceEnabled(true);

        viewModel = ViewModelProviders.of(this).get(MainMenuViewModel.class);
        viewModel.setNavigator(this);

        initViews();
        setToolbar();

//        stringSet =  getSharedPreferences("myPref", MODE_PRIVATE).getStringSet("booksIDs", null);
//        if(stringSet == null){
//            stringSet  = new HashSet<>();
//        }
        setNavigationDrawer();
        setFragment(new CategoriesFragment());
//
//        if (presenter == null) {
//            presenter = new MainMenuPresenterImpl(this);
//        }
//        presenter.onViewCreated();
    }

    private void initViews() {
        findViewById(R.id.etSearch).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.putExtra("categoryID", -1);
                intent.setClass(MainMenuActivity.this, SearchActivity.class);
                startActivity(intent);
            }
        });
        findViewById(R.id.tvRunningLine).setSelected(true);
    }

    private void setNavigationDrawer() {
        mDrawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        navigationView.setCheckedItem(R.id.nav_catalogue);
        if(viewModel.getUser()!=null){
            setUserHeader();
        }
        navigationView.getHeaderView(0).findViewById(R.id.ibAddNewUser).setOnClickListener(btn->{
            Intent intent = new Intent(MainMenuActivity.this, AccountActivity.class);
            startActivityForResult(intent, PICK_USER_REQUEST);
        });
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                // Add code here to update the UI based on the item selected
                // For example, swap UI fragments here
                switch (item.getItemId()) {
                    case R.id.nav_basket:
                        openScreen(BasketActivity.class);
                        item.setChecked(true);
                        mDrawerLayout.closeDrawers();
                        break;
                    case R.id.nav_catalogue:
                        item.setChecked(true);
                        mDrawerLayout.closeDrawers();
                        break;
                    case R.id.nav_info:
                        item.setChecked(true);
                        mDrawerLayout.closeDrawers();
                        break;
                    case R.id.nav_map:
                        Intent mapIntent = new Intent(MainMenuActivity.this, MapsActivity.class);
                        item.setChecked(true);
                        mDrawerLayout.closeDrawers();
                        mDrawerLayout.addDrawerListener(new DrawerLayout.DrawerListener() {
                            @Override
                            public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {

                            }

                            @Override
                            public void onDrawerOpened(@NonNull View drawerView) {

                            }

                            @Override
                            public void onDrawerClosed(@NonNull View drawerView) {
                                startActivity(mapIntent);
                            }

                            @Override
                            public void onDrawerStateChanged(int newState) {

                            }
                        });
                        break;
                    case R.id.nav_profile:
                        //TODO: switch if user is registered or not
                        Intent profileIntent = new Intent(MainMenuActivity.this, AccountActivity.class);
                        startActivityForResult(profileIntent, PICK_USER_REQUEST);
                        item.setChecked(true);
                        mDrawerLayout.closeDrawers();
                        break;
                    case R.id.nav_purchases:
                        openScreen(OrdersActivity.class);
                        item.setChecked(true);
                        mDrawerLayout.closeDrawers();
                        break;
                    default:
                        mDrawerLayout.closeDrawers();
                        break;
                }
//                item.setChecked(true);
//                mDrawerLayout.closeDrawers();
                return true;
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("Lifecycle", "onActivityResult");
        if (requestCode == PICK_USER_REQUEST) {
            if (resultCode == RESULT_OK) {
                Log.d("Lifecycle", "resultOK");
                boolean userIsSigned = data.getBooleanExtra("signedIn", false);
                if (userIsSigned) {
                    setUserHeader();
                } else {
                    setNewUserHeader();
                }
            }
        }
    }

    private void setToolbar() {
        setSupportActionBar(findViewById(R.id.toolbar));
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu_white_24dp);
        }
    }

    private void setFragment(Fragment fragment) {
        Fragment mainMenuFragment = getSupportFragmentManager().findFragmentById(R.id.content_frame);
        if (mainMenuFragment == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.content_frame, fragment)
                    .commit();
        }
    }

    private void setNewUserHeader() {
        Log.d("Lifecycle", "newUserHeader");
        View headerView = navigationView.getHeaderView(0);
        headerView.findViewById(R.id.ibAddNewUser).setVisibility(View.VISIBLE);
        headerView.findViewById(R.id.ivUserPhoto).setVisibility(View.GONE);
        headerView.findViewById(R.id.rlUserEmailAndExit).setVisibility(View.GONE);
    }

    private void setUserHeader() {
        Log.d("Lifecycle", "userHeader");
        View headerView = navigationView.getHeaderView(0);
        headerView.findViewById(R.id.ibAddNewUser).setVisibility(View.GONE);
        headerView.findViewById(R.id.ivUserPhoto).setVisibility(View.VISIBLE);
        headerView.findViewById(R.id.rlUserEmailAndExit).setVisibility(View.VISIBLE);
        TextView tvUserEmail = headerView.findViewById(R.id.tvUserEmail);
        tvUserEmail.setText(viewModel.getUserEmail());
        ImageButton btnSignOut = headerView.findViewById(R.id.ibSignOut);
        btnSignOut.setOnClickListener(btn->{ viewModel.signOut(); setNewUserHeader();});
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.actionbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
//        Intent intent = new Intent();
        switch (item.getItemId()) {
            case R.id.action_basket:
                openScreen(BasketActivity.class);
//                intent.setClass(this, BasketActivity.class);
//                startActivity(intent);
                break;

            case R.id.action_orders:
                openScreen(OrdersActivity.class);
//                intent.setClass(this, OrdersActivity.class);
//                startActivity(intent);
                break;

            case R.id.action_qrcode:
                openScreen(QRCodeActivity.class);
//                intent.setClass(this, QRCodeActivity.class);
//                startActivity(intent);
                break;

            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                break;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
        return true;
    }

    private void openScreen(Class activityClass) {
        Intent intent = new Intent();
        intent.setClass(this, activityClass);
        startActivity(intent);
    }

    @Override
    public void onCategoryClicked(int categoryID) {
        Log.d("Lifecycle", "MainActivity: categoryID = " + categoryID);
        Intent intent = new Intent();
        intent.putExtra("categoryID", categoryID);
        intent.setClass(this, CategoryActivity.class);
        startActivity(intent);
//        startActivityWithAnimation(view, intent, CategoryActivity.class);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("Lifecycle", "MainActivity: onStart()");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("Lifecycle", "MainActivity: onResume()");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("Lifecycle", "MainActivity: onPause()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("Lifecycle", "MainActivity: onStop()");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        navigationView.setCheckedItem(R.id.nav_catalogue);
        Log.d("Lifecycle", "MainActivity: onRestart()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("Lifecycle", "MainActivity: onDestroy()");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d("Lifecycle", "MainActivity: onSaveInstanceState()");
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.d("Lifecycle", "MainActivity: onRestoreInstanceState()");
    }
}