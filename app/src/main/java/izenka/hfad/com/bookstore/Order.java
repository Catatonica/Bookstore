package izenka.hfad.com.bookstore;

import com.google.firebase.database.IgnoreExtraProperties;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@IgnoreExtraProperties
public class Order implements Serializable {

    public String order_id;
  //  public double totalPrice;
    public String userPhone;
    public String userName;

    public Map<String, Integer> Books;
      public int count;
      public int book_id;

    public Map<String, String> Address;
      public String city;
      public String street;
      public String house;
      public String flatNumber;
      public int floor;
      public int porchNumber;

    HashMap<String,String> result=new HashMap<>();


    public Order(){}

    public Order(String order_id,/* double totalPrice,*/ String userPhone/*, String userName*/
   /* ,int count,int book_id*/,String city,String street,String house,String flatNumber/*,
                 int floor, int porchNumber*/){
        this.order_id=order_id;
    //    this.totalPrice=totalPrice;
        this.userPhone=userPhone;
    //    this.userName=userName;
    //    this.count=count;
    //    this.book_id=book_id;
        this.city=city;
        this.street=street;
        this.house=house;
        this.flatNumber=flatNumber;
    }

    public Map<String, String> toMap(){
        HashMap<String,String> result=new HashMap<>();
        result.put("order_id",order_id);
        result.put("userPhone", userPhone);

        return result;
    }

    public void defineIdAndCount(String book_id, String count){
        result.put(book_id,count);
    }

    public Map<String, String> booksToMap(){
        return result;
    }

    public Map<String,Object> addressToMap(){
        HashMap<String, Object> result=new HashMap<>();
        result.put("city",city);
        result.put("street", street);
        result.put("house", house);
        result.put("flatNumber", flatNumber);
   //     result.put("floor",floor);
   //     result.put("porchNumber", porchNumber);

        return result;
    }
}
