package izenka.hfad.com.bookstore.category;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import izenka.hfad.com.bookstore.R;
import izenka.hfad.com.bookstore.basket.BasketActivity;
import izenka.hfad.com.bookstore.book.BookActivity;
import izenka.hfad.com.bookstore.orders.OrdersActivity;
import izenka.hfad.com.bookstore.search.SearchActivity;
import izenka.hfad.com.bookstore.qr_code.QRCodeActivity;

public class CategoryActivity extends AppCompatActivity implements CategoryNavigator {

    private int categoryID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        categoryID = getIntent().getIntExtra("categoryID", 0);
        CategorizedBooksViewModel viewModel = ViewModelProviders.of(this).get(CategorizedBooksViewModel.class);
        viewModel.setNavigator(this);
        viewModel.setCategoryID(categoryID);
        setToolbar();
        setFragment(new CategoryFragment());
    }

    private void openScreen(Class activityClass) {
        Intent intent = new Intent();
        intent.setClass(this, activityClass);
        startActivity(intent);
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
}
