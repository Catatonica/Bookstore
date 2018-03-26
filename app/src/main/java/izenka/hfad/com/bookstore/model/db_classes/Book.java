package izenka.hfad.com.bookstore.model.db_classes;

import java.io.Serializable;

public class Book implements Serializable {
    public int book_id;
    public int book_publisher_id;
    public int count;
    public String description;
    public int pages_number;
    public String price;
    public int publication_year;
    public String title;
    public String cover;
    public int rating;
    public int discount;


    public Book() {
    }


    public String toString() {
        return "Book{book_id=" +
                "" + book_id + book_publisher_id + ", count" + count + "" +
                ", description=" + description + ", pages_number=" + pages_number + ", price=" + price + ", publication_year=" +
                publication_year + ", title=" + title + ", cover=" + cover + ", rating=" + rating + ", discount=" + discount + "}";
        //+", Authors="+Authors+", Images="+Images+", book_publisher_id="+
        // book_publisher_id+"}";
    }
}
