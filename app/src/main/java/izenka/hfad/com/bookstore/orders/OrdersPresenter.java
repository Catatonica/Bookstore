package izenka.hfad.com.bookstore.orders;


import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.TextView;

import java.util.Set;

import izenka.hfad.com.bookstore.R;
import izenka.hfad.com.bookstore.model.repositories.OrdersRepository;
import izenka.hfad.com.bookstore.presenter.IPresenter;
import izenka.hfad.com.bookstore.view.order.OrderActivity;
import izenka.hfad.com.bookstore.view.orders.IOrdersView;

public class OrdersPresenter implements IPresenter {
    private IOrdersView ordersView;
    private OrdersRepository ordersRepository;

    public OrdersPresenter(IOrdersView ordersView){
        this.ordersView = ordersView;
        ordersRepository = new OrdersRepository();
    }

    @Override
    public void onViewCreated(){
        ordersView.initViews();
    }

    public void onBackClicked(){
        ordersView.onBackClick();
    }

    public void onOrderClicked(View view){
        Intent intent = new Intent();
        TextView tvDate = (TextView) view.findViewById(R.id.tvDate);
        intent.putExtra("date", tvDate.getText().toString());
        TextView tvPrice = (TextView) view.findViewById(R.id.tvTotalPrice);
        intent.putExtra("price", tvPrice.getText().toString());
        intent.putExtra("orderID", view.getTransitionName());
        ordersView.startActivity(intent, OrderActivity.class);
    }

    public void showOrders(SharedPreferences sp ) {
        //NullPointerEception - means, that set is empty
        Set<String> orderIDsSet= sp.getStringSet("ordersIDs", null);
        if(orderIDsSet!=null){
            for (String id : orderIDsSet) {
                ordersRepository.addOrderID(id);
            }

            for (String orderID : ordersRepository.getOrdersIDs()) {
                ordersView.showOrder(orderID);
            }
        }
    }

}
