package izenka.hfad.com.bookstore.model.db_classes;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Order implements Serializable {

    public String order_id;
    public double totalPrice;
    public String userPhone;
    public String userName;
    public String status;

    public Map<String, Integer> Books;
    //int count;
    // int book_id;

    public Map<String, String> Address;
    public String city;
    public String street;
    public String house;
    public String flatNumber;
    public String floor;
    public String porchNumber;
    public String date;

    private HashMap<String, String> result = new HashMap<>();


    public Order() {
    }

    public Order(String order_id, String date, double totalPrice,/* double totalPrice,*/ String userPhone, String userName,/*, String userName*/
   /* ,int count,int book_id*/String city, String street, String house, String flatNumber, String porchNumber, String floor/*,
                 int floor, int porchNumber*/) {
        this.order_id = order_id;
        this.totalPrice = totalPrice;
        this.userPhone = userPhone;
        this.userName = userName;
        //   this.count=count;
        //    this.book_id=book_id;
        this.city = city;
        this.street = street;
        this.house = house;
        this.flatNumber = flatNumber;
        this.floor = floor;
        this.porchNumber = porchNumber;
        this.date = date;
        this.status = "выполняется";
    }

    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("order_id", order_id);
        result.put("totalPrice", totalPrice);
        result.put("userName", userName);
        result.put("userPhone", userPhone);
        result.put("date", date);
        result.put("status", status);

        return result;
    }

    public void defineIdAndCount(String book_id, String count) {
        result.put(book_id, count);
    }

    public Map<String, String> booksToMap() {
        return result;
    }

    public Map<String, String> addressToMap() {
        HashMap<String, String> result = new HashMap<>();
        result.put("city", city);
        result.put("street", street);
        result.put("house", house);
        result.put("flatNumber", flatNumber);
        result.put("floor", floor);
        result.put("porchNumber", porchNumber);

        return result;
    }
}
