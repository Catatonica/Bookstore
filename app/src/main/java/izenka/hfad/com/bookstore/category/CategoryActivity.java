package izenka.hfad.com.bookstore.category;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import izenka.hfad.com.bookstore.R;
import izenka.hfad.com.bookstore.basket.BasketActivity;
import izenka.hfad.com.bookstore.orders.OrdersActivity;
import izenka.hfad.com.bookstore.book.BookActivity;
import izenka.hfad.com.bookstore.view.qr_code.QRCodeActivity;
import izenka.hfad.com.bookstore.view.search.SearchActivity;

public class CategoryActivity extends AppCompatActivity implements BookListNavigator {

    private int categoryID;
    private BookListViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        Log.d("Lifecycle", "CategoryActivity: onCreate()");
        categoryID = getIntent().getIntExtra("categoryID", 0);
        Log.d("Lifecycle", "CategoryActivity: categoryID = "+categoryID);
        viewModel = ViewModelProviders.of(this).get(BookListViewModel.class);
        viewModel.setNavigator(this);
        viewModel.setCategoryID(categoryID);
//        viewModel.setBookListLiveData(categoryID);
//        initViews();
        setToolbar();
        setFragment(new BookListFragment());
    }


    private void setToolbar() {
        setSupportActionBar(findViewById(R.id.toolbar));
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(getResources().getStringArray(R.array.categoriesNames)[categoryID]);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_arrow_back_white_24dp);
        }
    }

//    private void initViews() {
//        TextView tvCategoryName = findViewById(R.id.tvCategoryName);
//        tvCategoryName.setText(getResources().getStringArray(R.array.categoriesNames)[categoryID]);
//        tvCategoryName.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/5.ttf"));
//        tvCategoryName.setTextSize(35);
////        findViewById(R.id.etCategorySearch).setOnClickListener(view -> {
////            Intent intent = new Intent();
////            intent.putExtra("categoryID", categoryID);
////            intent.setClass(CategoryActivity.this, SearchActivity.class);
////            startActivity(intent);
////        });
//    }

    private void setFragment(Fragment fragment) {
        Fragment bookListFragment = getSupportFragmentManager().findFragmentById(R.id.content_frame);
        if (bookListFragment == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.content_frame, fragment)
                    .commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.actionbar_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = new Intent();
        switch (item.getItemId()) {
            case R.id.action_basket:
                intent.setClass(this, BasketActivity.class);
                startActivity(intent);
                break;

            case R.id.action_orders:
                intent.setClass(this, OrdersActivity.class);
                startActivity(intent);
                break;

            case R.id.action_qrcode:
                intent.setClass(this, QRCodeActivity.class);
                startActivity(intent);
                break;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    @Override
    public void onBookClicked(int bookID) {
        Intent intent = new Intent();
        intent.putExtra("bookID", bookID);
        intent.setClass(this, BookActivity.class);
        startActivity(intent);
    }

    @Override
    public void onSearchInCategoryClicked() {
        Intent intent = new Intent();
        intent.putExtra("categoryID", categoryID);
        intent.setClass(this, SearchActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("Lifecycle", "CategoryActivity: onStart()");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("Lifecycle", "CategoryActivity: onResume()");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("Lifecycle", "CategoryActivity: onPause()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("Lifecycle", "CategoryActivity: onStop()");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("Lifecycle", "CategoryActivity: onRestart()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("Lifecycle", "CategoryActivity: onDestroy()");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d("Lifecycle", "CategoryActivity: onSaveInstanceState()");
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.d("Lifecycle", "CategoryActivity: onRestoreInstanceState()");
    }
}
