package izenka.hfad.com.bookstore.orders;


import izenka.hfad.com.bookstore.view.IView;

public interface IOrdersView extends IView {
    void onBackClick();
    void showOrder(String orderID);
}
