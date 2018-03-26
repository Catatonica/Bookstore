package izenka.hfad.com.bookstore.model.db_classes;

import java.io.Serializable;
import java.util.ArrayList;

public class Categories implements Serializable {
    ArrayList<Integer> category_id;

    public Categories() {
    }

    public String toString() {
        return "Categories{ category_id=" + category_id + "}";
    }
}
