package izenka.hfad.com.bookstore.account;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class User /*implements Serializable*/ {
    public String email;
    public String name;
    public String surname;
    public String phone;
    public String photoPath;
    public String birthday;
    public Map<String, Object> Address = new HashMap<>();
    public Map<String, Integer> Cart = new HashMap<>();
    public Map<String, String> Orders = new HashMap<>();

    public User() {

    }
}
