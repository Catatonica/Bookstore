package izenka.hfad.com.bookstore.orders;

import android.arch.lifecycle.ViewModelProviders;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import izenka.hfad.com.bookstore.R;

public class OrdersActivity extends AppCompatActivity implements OrdersNavigator {

    private ProgressBar pbLoadingProgress;

    private FrameLayout flBase;
    private FrameLayout flOrderList;
    private FrameLayout flOrder;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders);

        pbLoadingProgress = findViewById(R.id.pbLoadingProgress);
        setToolbar();

        OrdersViewModel viewModel = ViewModelProviders.of(this).get(OrdersViewModel.class);
        viewModel.setNavigator(this);

        if (viewModel.getOrderListLiveData().getValue() == null) {
            pbLoadingProgress.setVisibility(View.VISIBLE);
        }

        viewModel.getOrderListLiveData().observe(this, orderList -> {
            pbLoadingProgress.setVisibility(View.GONE);
            if (orderList == null || orderList.isEmpty()) {
                setEmptyFragment();
            } else {
                setOrderListFragment();
            }
        });
    }

//    private void initViews() {
//        flBase = findViewById(R.id.flBase);
//        flOrderList = findViewById(R.id.flOrderList);
//        flOrder = findViewById(R.id.flOrder);
//    }

    private void setToolbar() {
        setSupportActionBar(findViewById(R.id.toolbar));
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(R.string.orders);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_arrow_back_white_24dp);
    }

    private void setEmptyFragment() {
        switch (getResources().getConfiguration().orientation) {
            case Configuration.ORIENTATION_LANDSCAPE:
                findViewById(R.id.flBase).setVisibility(View.VISIBLE);
                findViewById(R.id.flOrderList).setVisibility(View.GONE);
                findViewById(R.id.flOrder).setVisibility(View.GONE);
                break;
            case Configuration.ORIENTATION_PORTRAIT:
                findViewById(R.id.flBase).setVisibility(View.VISIBLE);
                break;
        }

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.flBase, new EmptyOrdersFragment())
                .commit();

    }

    private void setOrderListFragment() {
        switch (getResources().getConfiguration().orientation) {
            case Configuration.ORIENTATION_LANDSCAPE:
                findViewById(R.id.flBase).setVisibility(View.GONE);
                findViewById(R.id.flOrderList).setVisibility(View.VISIBLE);
                findViewById(R.id.flOrder).setVisibility(View.VISIBLE);
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.flOrderList, new OrderListFragment(), "list")
                        .commit();
                Fragment orderDetailsFragment = getSupportFragmentManager().findFragmentByTag("details");
                if (orderDetailsFragment != null) {
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.flOrder, new OrderDetailsFragment(), "details")
                            .commit();
                }
                break;
            case Configuration.ORIENTATION_PORTRAIT:
                findViewById(R.id.flBase).setVisibility(View.VISIBLE);
                Fragment orderDetailsFragment2 = getSupportFragmentManager().findFragmentByTag("details");
                if (orderDetailsFragment2 == null) {
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.flBase, new OrderListFragment(), "list")
                            .commit();
                } else {
                    getSupportActionBar().setTitle(R.string.order);
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.flBase, new OrderDetailsFragment(), "details")
                            .commit();
                }
                break;
        }

//        Fragment ordersFragment = getSupportFragmentManager().findFragmentById(R.id.flOrderList);
//        if (ordersFragment == null) {

//        }
    }

    @Override
    public void onReturnClicked() {
        onBackPressed();
    }

    @Override
    public void openDetailsScreen() {
        switch (getResources().getConfiguration().orientation) {
            case Configuration.ORIENTATION_LANDSCAPE:
                findViewById(R.id.flBase).setVisibility(View.GONE);
                findViewById(R.id.flOrderList).setVisibility(View.VISIBLE);
                findViewById(R.id.flOrder).setVisibility(View.VISIBLE);
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.flOrder, new OrderDetailsFragment(), "details")
                        .commit();
                break;
            case Configuration.ORIENTATION_PORTRAIT:
                findViewById(R.id.flBase).setVisibility(View.VISIBLE);
//                getSupportActionBar().setTitle(R.string.order);
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.flBase, new OrderDetailsFragment(), "details")
                        .commit();
                break;
        }
    }

    private void setListFragment(int frameID) {
//        Fragment orderListFragment = getSupportFragmentManager().findFragmentByTag("list");
//        if (orderListFragment == null) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(frameID, new OrderListFragment(), "list")
                .commit();
//        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        Fragment detailsFragmentPortrait = getSupportFragmentManager().findFragmentByTag("details");
        Fragment detailsFragmentLand = getSupportFragmentManager().findFragmentById(R.id.flOrder);
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT && detailsFragmentPortrait != null) {
            getSupportActionBar().setTitle(R.string.orders);
            if(detailsFragmentLand == null){
                getSupportFragmentManager()
                        .beginTransaction()
                        .remove(detailsFragmentPortrait)
                        .replace(R.id.flBase, new OrderListFragment(), "list")
                        .commit();
            } else{
                getSupportFragmentManager()
                        .beginTransaction()
                        .remove(detailsFragmentPortrait)
                        .remove(detailsFragmentLand)
                        .replace(R.id.flBase, new OrderListFragment(), "list")
                        .commit();
            }
        } else {
            onBackPressed();
        }

        return super.onSupportNavigateUp();
    }
}