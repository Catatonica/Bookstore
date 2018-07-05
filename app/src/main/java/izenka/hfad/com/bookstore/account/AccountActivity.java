package izenka.hfad.com.bookstore.account;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import izenka.hfad.com.bookstore.AccountListener;
import izenka.hfad.com.bookstore.R;
import izenka.hfad.com.bookstore.main.MainMenuActivity;
import izenka.hfad.com.bookstore.stores_map.MapsActivity;

public class AccountActivity extends AppCompatActivity implements AccountActivityNavigator, AccountListener {

    @BindView(R.id.drawer_layout) DrawerLayout mDrawerLayout;
    @BindView(R.id.nav_view) NavigationView mNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        ButterKnife.bind(this);

        mNavigationView.setCheckedItem(R.id.nav_profile);
        setToolbar();
        setNavigationDrawer();

        AccountViewModel viewModel = ViewModelProviders.of(this).get(AccountViewModel.class);
        viewModel.setAccountActivityNavigator(this);

        if (getUser() == null) {
            setInitialFragment(new UserRegistrationFragment());
        } else {
            setInitialFragment(new UserProfileFragment());
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                View view = this.getCurrentFocus();
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (view != null && imm != null) {
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
                mDrawerLayout.openDrawer(GravityCompat.START);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setNavigationDrawer() {
        if (getUser() != null) {
            setUserHeader();
        }
        mNavigationView.setNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.nav_catalogue:
                    Intent catalogueIntent = new Intent(AccountActivity.this, MainMenuActivity.class);
                    item.setChecked(true);
                    openScreen(catalogueIntent);
                    break;
                case R.id.nav_map:
                    Intent mapIntent = new Intent(AccountActivity.this, MapsActivity.class);
                    item.setChecked(true);
                    openScreen(mapIntent);
                    break;
                case R.id.nav_profile:
                    item.setChecked(true);
                    mDrawerLayout.closeDrawers();
                    break;
                default:
                    mDrawerLayout.closeDrawers();
                    break;
            }
            return true;
        });
    }

    private void openScreen(Intent intent) {
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
                startActivity(intent);
            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });
    }

    private void setInitialFragment(Fragment fragment) {
        Fragment accountFragment = getSupportFragmentManager().findFragmentById(R.id.content_frame);
        if (accountFragment == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.content_frame, fragment)
                    .commit();
        }
    }

    private void setUserHeader() {
        View headerView = mNavigationView.getHeaderView(0);
        headerView.findViewById(R.id.ibAddNewUser).setVisibility(View.GONE);
        headerView.findViewById(R.id.ivUserPhoto).setVisibility(View.VISIBLE);
        headerView.findViewById(R.id.rlUserEmailAndExit).setVisibility(View.VISIBLE);
        TextView tvUserEmail = headerView.findViewById(R.id.tvUserEmail);
        tvUserEmail.setText(getUserEmail());
        ImageButton btnSignOut = headerView.findViewById(R.id.ibSignOut);
        btnSignOut.setOnClickListener(btn -> {
            signOut();
            setNewUserHeader();
            setFragment(new UserRegistrationFragment());
        });
    }

    private void setNewUserHeader() {
        View headerView = mNavigationView.getHeaderView(0);
        ImageButton ibAddNewUser = headerView.findViewById(R.id.ibAddNewUser);
        ibAddNewUser.setVisibility(View.VISIBLE);
        ibAddNewUser.setOnClickListener(btn -> mDrawerLayout.closeDrawers());
        headerView.findViewById(R.id.ivUserPhoto).setVisibility(View.GONE);
        headerView.findViewById(R.id.rlUserEmailAndExit).setVisibility(View.GONE);
    }

    private void setToolbar() {
        setSupportActionBar(findViewById(R.id.toolbar));
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(R.string.account);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu_white_24dp);
        }
    }

    @Override
    public void setFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.content_frame, fragment)
                .commit();
        if (fragment.getClass() == UserRegistrationFragment.class) {
            setNewUserHeader();
        } else if (fragment.getClass() == UserProfileFragment.class) {
            setUserHeader();
        }
    }

    @Override
    public void showMessage() {
        Toast.makeText(this, "Изменения сохранены", Toast.LENGTH_SHORT).show();
    }
}
