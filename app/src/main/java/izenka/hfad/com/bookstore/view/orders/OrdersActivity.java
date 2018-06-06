package izenka.hfad.com.bookstore.view.orders;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.View;

import izenka.hfad.com.bookstore.R;
import izenka.hfad.com.bookstore.model.db_operations.ReadOrders;
import izenka.hfad.com.bookstore.presenter.OrdersPresenter;
import stanford.androidlib.SimpleActivity;

public class OrdersActivity extends SimpleActivity implements IOrdersView {

    private OrdersPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders);

        setToolbar();

        if(presenter == null){
            presenter = new OrdersPresenter(this);
        }
        presenter.onViewCreated();
        presenter.showOrders(getSharedPreferences("ordersPref", MODE_PRIVATE));
    }

    private void setToolbar(){
        setSupportActionBar(findViewById(R.id.toolbar));
        ActionBar actionBar = getSupportActionBar();
        if(actionBar!=null){
            actionBar.setTitle(R.string.orders);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_arrow_back_white_24dp);
        }
    }

    @Override
    public void startActivity(Intent intent, Class activity) {
        intent.setClass(this, activity);
        startActivity(intent);
    }

    @Override
    public void startActivityWithAnimation(View view, Class activity) {

    }

    @Override
    public void startActivityWithAnimation(View view, Intent intent, Class activity) {

    }

    @Override
    public void initViews() {
//        $IV(R.id.btnGoBack).setOnClickListener(view->presenter.onBackClicked());
//        View orderView = LayoutInflater.from(this).inflate(R.layout.order, null, false);
//        orderView.setOnClickListener(view -> presenter.onOrderClicked(view));
    }


    @Override
    public void showOrder(String orderID) {
        ReadOrders.getOrder(this, orderID);
    }

    @Override
    public void onBackClick() {
        onBackPressed();
    }


    public void onOrderClick(View view) {
        presenter.onOrderClicked(view);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}
