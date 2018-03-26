package izenka.hfad.com.bookstore.model.db_classes;

import java.io.Serializable;


public class OrderGet implements Serializable {
    public double totalPrice;
    public String date;
    public String status;

    public OrderGet(double totalPrice, String date, String status) {
        this.totalPrice = totalPrice;
        this.date = date;
        this.status = status;
    }

    public OrderGet() {
    }
}
