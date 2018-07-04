package izenka.hfad.com.bookstore.callbacks;


import java.util.List;

import izenka.hfad.com.bookstore.basket.BookInBasketModel;

@FunctionalInterface
public interface BooksFromBasketCallback {
    void onBooksLoaded(List<BookInBasketModel> bookList);
}
