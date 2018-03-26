package izenka.hfad.com.bookstore.model.db_classes;

import java.io.Serializable;

public class Category implements Serializable {
    int category_id;
    String category_name;
    String image_path;

    public Category() {
    }

    public String toString() {
        return "Category { category_id=" + category_id + ", category_name=" + category_name + ", image_path=" + image_path + "}";
    }
}
