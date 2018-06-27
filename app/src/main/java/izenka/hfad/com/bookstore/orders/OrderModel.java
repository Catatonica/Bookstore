package izenka.hfad.com.bookstore.orders;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import izenka.hfad.com.bookstore.model.db_classes.Book;

public class OrderModel {
    public String date;
    public float price;
    public String status;
    public List<BookInOrderModel> bookList = new ArrayList<>();
//    public Map<Book, Integer> booksMap = new HashMap<>();
}
