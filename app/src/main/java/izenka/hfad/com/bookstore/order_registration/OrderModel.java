package izenka.hfad.com.bookstore.order_registration;

import java.util.HashMap;
import java.util.Map;

public class OrderModel {
    public float price;
    public String date;
    public String userName;
    public String userPhone;
    public String userID;
    public String userEmail;
    public String fullAddress;
    public Map<String, Integer> Books = new HashMap<>();
    public Map<String, Object> Address = new HashMap<>();


    public OrderModel() {

    }

    public OrderModel(String date, float price, String userName, String userPhone, String userID, String userEmail,
                      String fullAddress, Map<String, Integer> Books, Map<String, Object> Address) {
        this.date = date;
        this.price = price;
        this.userName = userName;
        this.userPhone = userPhone;
        this.userID = userID;
        this.userEmail = userEmail;
        this.fullAddress = fullAddress;
        this.Books = Books;
        this.Address = Address;
    }

    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("date", date);
        result.put("price", price);
        result.put("userName", userName);
        result.put("userPhone", userPhone);
        result.put("userID", userID);
        result.put("userEmail", userEmail);
        result.put("fullAddress", fullAddress);
        result.put("Books", Books);
        result.put("Address", Address);
        return result;
    }
}
