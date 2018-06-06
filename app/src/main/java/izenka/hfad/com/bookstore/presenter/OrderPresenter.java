package izenka.hfad.com.bookstore.presenter;


import android.content.Intent;

import izenka.hfad.com.bookstore.view.order.IOrderView;

public class OrderPresenter implements IPresenter {
    private IOrderView orderView;
    private Intent intent;

    public OrderPresenter(IOrderView orderView, Intent intent){
        this.orderView = orderView;
        this.intent = intent;
    }

    @Override
    public void onViewCreated(){
        orderView.initViews();
//        String heading = "Заказ " + intent.getStringExtra("date");
//        orderView.setHeading(heading);
        String totalPrice = intent.getStringExtra("price");
        orderView.setPrise(totalPrice);
        String orderID = intent.getStringExtra("orderID");
        orderView.setBooksIDAndCount(orderID);
    }

//    public void onBackClicked(){
//        orderView.onBackClick();
//    }
}
