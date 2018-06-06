package izenka.hfad.com.bookstore.model.user;

import java.util.HashSet;
import java.util.Set;

class User {

    private String phone;

    public Set<String> getBooksIDs() {
        return booksIDs;
    }

    public void setBooksIDs(Set<String> booksIDs) {
        this.booksIDs = booksIDs;
    }

    private Set<String> booksIDs = new HashSet<>();

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
