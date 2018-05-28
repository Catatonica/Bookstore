package izenka.hfad.com.bookstore.model.db_operations;


import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

import izenka.hfad.com.bookstore.model.db_classes.Order;

public class WriteOrder {

    public static void writeNewOrder(String key, Order order){

        DatabaseReference db = FirebaseDatabase.getInstance().getReference();

        Map<String, String> Books = order.booksToMap();
        Map<String, String> Address = order.addressToMap();
        Map<String, Object> newOrder = order.toMap();

        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("bookstore/order/" + key, newOrder);

        Map<String, Object> orderUpdates = new HashMap<>();
        orderUpdates.put("bookstore/order/" + key + "/Books", Books);
        orderUpdates.put("bookstore/order/" + key + "/Address", Address);

        db.updateChildren(childUpdates);
        db.updateChildren(orderUpdates);
    }
}
