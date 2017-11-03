package izenka.hfad.com.bookstore;

import java.io.Serializable;

/**
 * Created by Алексей on 22.10.2017.
 */

public class Category implements Serializable {
    int category_id;
    String category_name;
    String image_path;

    public Category(){};

    public String toString(){
        return "Category { category_id="+category_id+", category_name="+category_name+", image_path="+image_path+"}";
    }
}
