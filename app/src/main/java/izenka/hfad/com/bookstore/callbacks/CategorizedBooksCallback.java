package izenka.hfad.com.bookstore.callbacks;


import java.util.List;

import izenka.hfad.com.bookstore.model.db_classes.Book;

@FunctionalInterface
public interface CategorizedBooksCallback {
    void onBooksLoaded(List<Book> bookList);
}
