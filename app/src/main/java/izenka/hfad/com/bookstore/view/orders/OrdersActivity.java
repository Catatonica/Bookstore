package izenka.hfad.com.bookstore.view.orders;

import android.content.Intent;
import android.os.Bundle;
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

        if(presenter == null){
            presenter = new OrdersPresenter(this);
        }
        presenter.onViewCreated();
        presenter.showOrders(getSharedPreferences("ordersPref", MODE_PRIVATE));
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
        $IV(R.id.btnGoBack).setOnClickListener(view->presenter.onBackClicked());
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
}
