package izenka.hfad.com.bookstore;

import java.io.Serializable;

/**
 * Created by Алексей on 23.10.2017.
 */

public class Author implements Serializable {
    int author_id;
    String author_name;
    String author_surname;

    public Author(){};

    public String toString(){
        return "Author { author_id="+author_id+", author_name="+author_name+", author_surname="+author_surname+"}";
    }

}

