package izenka.hfad.com.bookstore.orders;


import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import java.util.List;

import izenka.hfad.com.bookstore.DatabaseSingleton;
import izenka.hfad.com.bookstore.model.db_classes.Order;
import izenka.hfad.com.bookstore.order_registration.OrderRegistrationModel;

public class OrdersViewModel extends ViewModel {

    private MutableLiveData<List<OrderRegistrationModel>> orderListLiveData;

    public MutableLiveData<List<BookInOrderModel>> getBookAndCountListLiveData() {
        return bookAndCountListLiveData;
    }

    private void setBookAndCountListValue(OrderRegistrationModel order) {
        if(bookAndCountListLiveData == null){
            bookAndCountListLiveData = new MutableLiveData<>();
        }
        DatabaseSingleton.getInstance().getBookAndCountList(order.Books, bookAndCountList->{
            bookAndCountListLiveData.postValue(bookAndCountList);
        });
    }

    private MutableLiveData<List<BookInOrderModel>> bookAndCountListLiveData;

    private OrdersNavigator navigator;

    public void setNavigator(OrdersNavigator navigator) {
        this.navigator = navigator;
    }

    public MutableLiveData<List<OrderRegistrationModel>> getOrderListLiveData() {
        if (orderListLiveData == null) {
            orderListLiveData = new MutableLiveData<>();
            DatabaseSingleton.getInstance().getUser(user -> {
                DatabaseSingleton.getInstance().getOrderList(user.Orders, orderList -> {
                    orderListLiveData.postValue(orderList);
                });
            });
//            loadOrderListLiveData();
        }
        return orderListLiveData;
    }

//    private void loadOrderListLiveData() {
//        DatabaseReference db = FirebaseDatabase.getInstance().getReference();
//        String userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
//        db.child("bookstore").addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                List<OrderModel> orderList = new ArrayList<>();
//                for (DataSnapshot orderIDSnapshot : dataSnapshot.child("users").child(userID).child("Orders").getChildren()) {
//                    String orderID = orderIDSnapshot.getValue().toString();
//                    OrderRegistrationModel orderRegistrationModel = dataSnapshot.child("order").child(orderID).getValue(OrderRegistrationModel.class);
//                    OrderModel orderModel = new OrderModel();
//                    orderModel.date = orderRegistrationModel.date;
//                    orderModel.price = orderRegistrationModel.price;
//                    orderModel.status = orderRegistrationModel.status;
//                    for (String bookID : orderRegistrationModel.Books.keySet()) {
//                        int count = orderRegistrationModel.Books.get(bookID);
//                        Book book = dataSnapshot.child("book").child(bookID).getValue(Book.class);
////                        orderModel.booksMap.put(book, count);
//                        BookInOrderModel bookInOrderModel = new BookInOrderModel();
//                        bookInOrderModel.book = book;
//                        bookInOrderModel.count = count;
//                        orderModel.bookList.add(bookInOrderModel);
//                    }
//                    orderList.add(orderModel);
//                }
//                orderListLiveData.postValue(orderList);
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
//    }

    public void onReturnClicked() {
        navigator.onReturnClicked();
    }

    public void openDetailsScreen(OrderRegistrationModel order) {
        setBookAndCountListValue(order);
        navigator.openDetailsScreen();
    }
}
