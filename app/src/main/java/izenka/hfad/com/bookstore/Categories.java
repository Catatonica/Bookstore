package izenka.hfad.com.bookstore;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Алексей on 22.10.2017.
 */

public class Categories implements Serializable {
    ArrayList<Integer> category_id;

    public Categories(){};

    public String toString(){
        return "Categories{ category_id="+category_id+"}";
    }
}
