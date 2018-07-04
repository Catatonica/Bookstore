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
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import izenka.hfad.com.bookstore.AccountListener;
import izenka.hfad.com.bookstore.R;
import izenka.hfad.com.bookstore.main.MainMenuActivity;
import izenka.hfad.com.bookstore.stores_map.MapsActivity;

public class AccountActivity extends AppCompatActivity implements AccountActivityNavigator, AccountListener {

//    private FirebaseAuth mAuth;

//    private EditText etUserEmail;
//    private EditText etUserPassword;
//    private Button btnCreateAccount;

    //    private static final String TAG = "EmailPassword";
//    private NavigationView navigationView;
    private Intent feedbackIntent;
    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        initViews();
        setToolbar();
        setNavigationDrawer();

        AccountViewModel viewModel = ViewModelProviders.of(this).get(AccountViewModel.class);
        viewModel.setAccountActivityNavigator(this);
//        FirebaseUser currentUser = viewModel.getUser();

//        FirebaseUser currentUser = viewModel.getUserLiveData().getValue();
//        mAuth = FirebaseAuth.getInstance();
//        FirebaseUser currentUser = mAuth.getCurrentUser();

//        navigationView = findViewById(R.id.nav_view);

        feedbackIntent = new Intent();

        if (getUser() == null) {
//            setNewUserHeader();
            feedbackIntent.putExtra("signedIn", false);
            setInitialFragment(new UserRegistrationFragment());
//            setNewUserHeader();
            Log.d("Lifecycle", "signedInCreate = false");
//            setResult(RESULT_OK, feedbackIntent);
        } else {
//            setUserHeader();
            feedbackIntent.putExtra("signedIn", true);
            setInitialFragment(new UserProfileFragment());
//            setUserHeader();
            Log.d("Lifecycle", "signedInCreate = true");
//            setResult(RESULT_OK, feedbackIntent);
        }
//        initViews();
    }

    private void initViews() {
        mDrawerLayout = findViewById(R.id.drawer_layout);
        mNavigationView = findViewById(R.id.nav_view);
        mNavigationView.setCheckedItem(R.id.nav_profile);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                View view = this.getCurrentFocus();
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                if (view != null && imm!=null) {
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
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                // Add code here to update the UI based on the item selected
                // For example, swap UI fragments here
                switch (item.getItemId()) {
                    case R.id.nav_catalogue:
                        Intent catalogueIntent = new Intent(AccountActivity.this, MainMenuActivity.class);
                        item.setChecked(true);
                        openScreen(catalogueIntent);
                        break;
                    case R.id.nav_info:
                        item.setChecked(true);
//                        mDrawerLayout.closeDrawers();
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
//                item.setChecked(true);
//                mDrawerLayout.closeDrawers();
                return true;
            }
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
        ibAddNewUser.setOnClickListener(btn -> {
            mDrawerLayout.closeDrawers();
        });
        headerView.findViewById(R.id.ivUserPhoto).setVisibility(View.GONE);
        headerView.findViewById(R.id.rlUserEmailAndExit).setVisibility(View.GONE);
    }

//    private void setNewUserHeader(){
//        navigationView.getHeaderView(0).findViewById(R.id.ibAddNewUser).setVisibility(View.VISIBLE);
//        navigationView.getHeaderView(0).findViewById(R.id.ivUserPhoto).setVisibility(View.GONE);
//        navigationView.getHeaderView(0).findViewById(R.id.rlUserEmailAndExit).setVisibility(View.GONE);
//    }
//
//    private void setUserHeader(){
//        navigationView.getHeaderView(0).findViewById(R.id.ibAddNewUser).setVisibility(View.GONE);
//        navigationView.getHeaderView(0).findViewById(R.id.ivUserPhoto).setVisibility(View.VISIBLE);
//        navigationView.getHeaderView(0).findViewById(R.id.rlUserEmailAndExit).setVisibility(View.VISIBLE);
//    }

//    @Override
//    protected void onStart() {
//        super.onStart();
//        // Check if user is signed in (non-null) and update UI accordingly.
//        FirebaseUser currentUser = mAuth.getCurrentUser();
//        updateUI(currentUser);
//    }
//

//    private void initViews() {
//        etUserEmail = findViewById(R.id.etUserEmail);
//        etUserPassword = findViewById(R.id.etUserPassword);
//        btnCreateAccount = findViewById(R.id.btnCreateAccount);
//
//        btnCreateAccount.setOnClickListener(btn -> {
//            createAccount(etUserEmail.getText().toString(), etUserPassword.getText().toString());
//        });
//    }

//    private void createAccount(String email, String password) {
//        if (!validateForm()) {
//            return;
//        }
////        showProgressDialog();
//        mAuth.createUserWithEmailAndPassword(email, password)
//             .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
//                 @Override
//                 public void onComplete(@NonNull Task<AuthResult> task) {
//                     if (task.isSuccessful()) {
//                         // Sign in success, update UI with the signed-in user's information
//                         Log.d(TAG, "createUserWithEmail:success");
//                         FirebaseUser user = mAuth.getCurrentUser();
//                         updateUI(user);
//                     } else {
//                         // If sign in fails, display a message to the user.
//                         Log.w(TAG, "createUserWithEmail:failure", task.getException());
//                         Toast.makeText(AccountActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
//                         updateUI(null);
//                     }
////                     hideProgressDialog();
//                 }
//             });
//    }
//
//    private boolean validateForm() {
//        boolean valid = true;
//
//        String email = etUserEmail.getText().toString();
//        if (TextUtils.isEmpty(email)) {
//            etUserEmail.setError("Required.");
//            valid = false;
//        } else {
//            etUserEmail.setError(null);
//        }
//
//        String password = etUserPassword.getText().toString();
//        if (TextUtils.isEmpty(password)) {
//            etUserPassword.setError("Required.");
//            valid = false;
//        } else {
//            etUserPassword.setError(null);
//        }
//
//        return valid;
//    }
//
//    private void updateUI(FirebaseUser user) {
////        hideProgressDialog();
//        if (user != null) {
//            TextView tvRegistrationSuccess = findViewById(R.id.tvRegistrationSuccess);
//            tvRegistrationSuccess.setText("success, " + user.getEmail() + user.isEmailVerified());
//            TextView tvRegistrationInfo = findViewById(R.id.tvRegistrationInfo);
//            tvRegistrationInfo.setText("info: " + user.getUid());
//            btnCreateAccount.setEnabled(!user.isEmailVerified());
////            mStatusTextView.setText(getString(R.string.emailpassword_status_fmt,
////                                              user.getEmail(), user.isEmailVerified()));
////            mDetailTextView.setText(getString(R.string.firebase_status_fmt, user.getUid()));
////
////            findViewById(R.id.email_password_buttons).setVisibility(View.GONE);
////            findViewById(R.id.email_password_fields).setVisibility(View.GONE);
////            findViewById(R.id.signed_in_buttons).setVisibility(View.VISIBLE);
////
////            findViewById(R.id.verify_email_button).setEnabled(!user.isEmailVerified());
//        } else {
////            mStatusTextView.setText(R.string.signed_out);
////            mDetailTextView.setText(null);
////
////            findViewById(R.id.email_password_buttons).setVisibility(View.VISIBLE);
////            findViewById(R.id.email_password_fields).setVisibility(View.VISIBLE);
////            findViewById(R.id.signed_in_buttons).setVisibility(View.GONE);
//        }
//    }

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
//            feedbackIntent.putExtra("signedIn", false);
//            Log.d("Lifecycle", "signedIn: false");
//
////            setNewUserHeader();
        } else if (fragment.getClass() == UserProfileFragment.class) {
            setUserHeader();
//            feedbackIntent.putExtra("signedIn", true);
//            Log.d("Lifecycle", "signedIn: true");
        }
//        setResult(RESULT_OK, feedbackIntent);
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("Lifecycle", "AccountActivity: onPause()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("Lifecycle", "AccountActivity: onStop()");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("Lifecycle", "AccountActivity: onRestart()");
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("Lifecycle", "AccountActivity: onDestroy()");
    }
}
