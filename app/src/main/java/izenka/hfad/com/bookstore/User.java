package izenka.hfad.com.bookstore;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Алексей on 08.11.2017.
 */
 class User {

    private String phone;

    public Set<String> getBooksIDs() {
        return booksIDs;
    }

    public void setBooksIDs(Set<String> booksIDs) {
        this.booksIDs = booksIDs;
    }

    private Set<String> booksIDs = new HashSet<>();

    public String getPhone() {return phone;}
    public void setPhone(String phone) {this.phone = phone;}



}
