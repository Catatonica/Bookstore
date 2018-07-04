package izenka.hfad.com.bookstore.basket2;


import android.arch.paging.PositionalDataSource;
import android.support.annotation.NonNull;

import java.util.Map;

import izenka.hfad.com.bookstore.DatabaseSingleton;
import izenka.hfad.com.bookstore.callbacks.BooksFromBasketCallback;

public class BooksInBasketDataSource extends PositionalDataSource<BookInBasketModel> {

    private Map<String, Integer> booksIDsAndCount;

    public BooksInBasketDataSource(Map<String, Integer> booksIDsAndCount) {
        this.booksIDsAndCount = booksIDsAndCount;
    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams params, @NonNull LoadInitialCallback<BookInBasketModel> callback) {
//        BooksFromBasketCallback booksFromBasketCallback = bookList -> callback.onResult(bookList, params.requestedStartPosition);
//        DatabaseSingleton.getInstance().getPagedBooksFromBasket(booksIDsAndCount,
//                                                                params.requestedStartPosition,
//                                                                params.requestedLoadSize,
//                                                                booksFromBasketCallback);
    }

    @Override
    public void loadRange(@NonNull LoadRangeParams params, @NonNull LoadRangeCallback<BookInBasketModel> callback) {
//        BooksFromBasketCallback booksFromBasketCallback = callback::onResult;
//        DatabaseSingleton.getInstance().getPagedBooksFromBasket(booksIDsAndCount,
//                                                                params.startPosition,
//                                                                params.loadSize,
//                                                                booksFromBasketCallback);
    }

}
