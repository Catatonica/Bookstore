package izenka.hfad.com.bookstore.callbacks;

import izenka.hfad.com.bookstore.model.db_classes.Book;

@FunctionalInterface
public interface BookCallback {
    void onBookLoaded(Book book);
}
