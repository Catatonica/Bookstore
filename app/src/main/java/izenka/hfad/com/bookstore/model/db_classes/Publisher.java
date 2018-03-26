package izenka.hfad.com.bookstore.model.db_classes;

import java.io.Serializable;

public class Publisher implements Serializable {
    public int publisher_id;
    public String publisher_name;

    public Publisher() {
    }

    public String toString() {
        return "Publisher={ publisher_id=" + publisher_id + ", publisher_name=" +
                publisher_name + "}";
    }
}
