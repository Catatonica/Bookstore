package izenka.hfad.com.bookstore;

import java.io.Serializable;

/**
 * Created by Алексей on 26.10.2017.
 */

public class Publisher implements Serializable {
    int publisher_id;
    String publisher_name;

    public Publisher(){};

    public String toString() {
        return "Publisher={ publisher_id=" + publisher_id + ", publisher_name=" +
                publisher_name+"}";
    }
}
