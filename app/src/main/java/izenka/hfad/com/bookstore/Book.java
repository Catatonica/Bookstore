package izenka.hfad.com.bookstore;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Алексей on 23.10.2017.
 */

public class Book implements Serializable {
    int book_id;
    int book_publisher_id;
    int count;
    String description;
    int pages_number;
    String price;
    int publication_year;
    String title;
    String cover;
    int rating;
    int discount;


    public Book(){};

    public String toString(){
        return "Book{book_id=" +
                ""+book_id+book_publisher_id+", count"+count+"" +
                ", description="+description+", pages_number="+pages_number+", price="+price+", publication_year="+
                publication_year+", title="+title+", cover="+cover+", rating="+rating+", discount="+discount+"}";
                //+", Authors="+Authors+", Images="+Images+", book_publisher_id="+
               // book_publisher_id+"}";
    }
}
