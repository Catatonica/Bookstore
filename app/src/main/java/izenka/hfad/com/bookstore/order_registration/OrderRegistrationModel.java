package izenka.hfad.com.bookstore.order_registration;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class OrderRegistrationModel implements Serializable{
    public float price;
    public String date;
    public String userName;
    public String userPhone;
    public String userID;
    public String userEmail;
    public String fullAddress;
    public String status;
    public Map<String, Integer> Books = new HashMap<>();
    public Map<String, Object> Address = new HashMap<>();


    public OrderRegistrationModel() {

    }

    public OrderRegistrationModel(String date, float price, String userName, String userPhone, String userID, String userEmail,
                                  String fullAddress, Map<String, Integer> Books, Map<String, Object> Address, String status) {
        this.date = date;
        this.price = price;
        this.userName = userName;
        this.userPhone = userPhone;
        this.userID = userID;
        this.userEmail = userEmail;
        this.fullAddress = fullAddress;
        this.Books = Books;
        this.Address = Address;
        this.status = status;
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
        result.put("status", status);
        return result;
    }
}
