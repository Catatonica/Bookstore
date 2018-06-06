package izenka.hfad.com.bookstore.presenter;


import android.content.Intent;

import izenka.hfad.com.bookstore.basket.view.BasketActivity;
import izenka.hfad.com.bookstore.view.book.IBookView;
import izenka.hfad.com.bookstore.view.orders.OrdersActivity;

public class BookPresenter implements IPresenter, IToolbarPresenter {

    private IBookView bookView;

    public BookPresenter(IBookView bookView){
        this.bookView = bookView;
    }

    @Override
    public void onViewCreated() {

    }

    @Override
    public void onOrdersClicked() {
        Intent intent = new Intent();
        bookView.startActivity(intent, OrdersActivity.class);
    }

    @Override
    public void onBasketClicked() {
        Intent intent = new Intent();
        bookView.startActivity(intent, BasketActivity.class);
    }
}
