package izenka.hfad.com.bookstore.callbacks;


import java.util.List;

import izenka.hfad.com.bookstore.model.db_classes.Author;

public interface AuthorListCallback {
    void onAuthorsReceived(List<Author> authorList);
}
