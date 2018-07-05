package izenka.hfad.com.bookstore.orders;


import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import java.util.List;

import izenka.hfad.com.bookstore.DatabaseSingleton;
import izenka.hfad.com.bookstore.order_registration.OrderRegistrationModel;

public class OrdersViewModel extends ViewModel {

    private MutableLiveData<List<OrderRegistrationModel>> orderListLiveData;

    MutableLiveData<List<BookInOrderModel>> getBookAndCountListLiveData(/*OrderRegistrationModel order*/) {
//        if(bookAndCountListLiveData == null){
//            bookAndCountListLiveData = new MutableLiveData<>();
//        }
//        DatabaseSingleton.getInstance().getBookAndCountList(order.getBooks(), bookAndCountList -> {
//            bookAndCountListLiveData.postValue(bookAndCountList);
//        });
        return bookAndCountListLiveData;
    }

    private void setBookIDAndCountList(OrderRegistrationModel order){
        if(bookAndCountListLiveData == null){
            bookAndCountListLiveData = new MutableLiveData<>();
        }
        DatabaseSingleton.getInstance().getBookAndCountList(order.getBooks(), bookAndCountList -> {
            bookAndCountListLiveData.postValue(bookAndCountList);
        });
    }

    private MutableLiveData<List<BookInOrderModel>> bookAndCountListLiveData;

    private OrdersNavigator navigator;

    public void setNavigator(OrdersNavigator navigator) {
        this.navigator = navigator;
    }

    MutableLiveData<List<OrderRegistrationModel>> getOrderListLiveData() {
        if (orderListLiveData == null) {
            orderListLiveData = new MutableLiveData<>();
            DatabaseSingleton.getInstance().getUser(user -> {
                DatabaseSingleton.getInstance().getOrderList(user.Orders, orderList -> {
                    orderListLiveData.postValue(orderList);
                });
            });
        }
        return orderListLiveData;
    }

    void onReturnClicked() {
        navigator.onReturnClicked();
    }

    void openDetailsScreen(OrderRegistrationModel order) {
        setBookIDAndCountList(order);
        navigator.openDetailsScreen(order);
    }
}
