package izenka.hfad.com.bookstore.orders;


import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import izenka.hfad.com.bookstore.model.db_classes.Book;
import izenka.hfad.com.bookstore.order_registration.OrderRegistrationModel;

public class OrdersViewModel extends ViewModel {

    private MutableLiveData<List<OrderModel>> orderListLiveData;

    public MutableLiveData<OrderModel> getOrderLiveData() {
        return orderLiveData;
    }

    private void setOrderLiveDataValue(OrderModel order) {
        if(orderLiveData == null){
            orderLiveData = new MutableLiveData<>();
        }
        orderLiveData.setValue(order);
    }

    private MutableLiveData<OrderModel> orderLiveData;

    private OrdersNavigator navigator;

    public void setNavigator(OrdersNavigator navigator) {
        this.navigator = navigator;
    }

    public MutableLiveData<List<OrderModel>> getOrderListLiveData() {
        if (orderListLiveData == null) {
            orderListLiveData = new MutableLiveData<>();
            loadOrderListLiveData();
        }
        return orderListLiveData;
    }

    private void loadOrderListLiveData() {
        DatabaseReference db = FirebaseDatabase.getInstance().getReference();
        String userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        db.child("bookstore").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<OrderModel> orderList = new ArrayList<>();
                for (DataSnapshot orderIDSnapshot : dataSnapshot.child("users").child(userID).child("Orders").getChildren()) {
                    String orderID = orderIDSnapshot.getValue().toString();
                    OrderRegistrationModel orderRegistrationModel = dataSnapshot.child("order").child(orderID).getValue(OrderRegistrationModel.class);
                    OrderModel orderModel = new OrderModel();
                    orderModel.date = orderRegistrationModel.date;
                    orderModel.price = orderRegistrationModel.price;
                    orderModel.status = orderRegistrationModel.status;
                    for (String bookID : orderRegistrationModel.Books.keySet()) {
                        int count = orderRegistrationModel.Books.get(bookID);
                        Book book = dataSnapshot.child("book").child(bookID).getValue(Book.class);
//                        orderModel.booksMap.put(book, count);
                        BookInOrderModel bookInOrderModel = new BookInOrderModel();
                        bookInOrderModel.book = book;
                        bookInOrderModel.count = count;
                        orderModel.bookList.add(bookInOrderModel);
                    }
                    orderList.add(orderModel);
                }
                orderListLiveData.postValue(orderList);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void onReturnClicked() {
        navigator.onReturnClicked();
    }

    public void openDetailsScreen(OrderModel order) {
        setOrderLiveDataValue(order);
        navigator.openDetailsScreen();
    }
}
