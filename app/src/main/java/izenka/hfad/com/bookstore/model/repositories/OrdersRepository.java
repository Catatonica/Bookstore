package izenka.hfad.com.bookstore.model.repositories;


import java.util.LinkedList;

public class OrdersRepository {
    private LinkedList<String> ordersIDs ;

    public OrdersRepository(){
        ordersIDs = new LinkedList<>();
    }

    public void addOrderID(String id){
        ordersIDs.addFirst(id);
    }

    public LinkedList<String> getOrdersIDs(){
        return ordersIDs;
    }

}
