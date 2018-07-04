package izenka.hfad.com.bookstore.category;

import android.arch.paging.PositionalDataSource;
import android.support.annotation.NonNull;

import izenka.hfad.com.bookstore.DatabaseSingleton;
import izenka.hfad.com.bookstore.callbacks.CategorizedBooksCallback;
import izenka.hfad.com.bookstore.model.db_classes.Book;


public class CategorizedBooksDataSource extends PositionalDataSource<Book> {

    private int categoryID;

    CategorizedBooksDataSource(int categoryID) {
        this.categoryID = categoryID;
    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams params, @NonNull LoadInitialCallback<Book> callback) {
        CategorizedBooksCallback categorizedBooksCallback = bookList -> callback.onResult(bookList, params.requestedStartPosition);
        DatabaseSingleton.getInstance().getCategorizedPagedBookList(categoryID,
                                                                    params.requestedStartPosition,
                                                                    params.requestedLoadSize,
                                                                    categorizedBooksCallback);
    }

    @Override
    public void loadRange(@NonNull LoadRangeParams params, @NonNull LoadRangeCallback<Book> callback) {
        CategorizedBooksCallback categorizedBooksCallback = callback::onResult;
        DatabaseSingleton.getInstance().getCategorizedPagedBookList(categoryID,
                                                                    params.startPosition,
                                                                    params.loadSize,
                                                                    categorizedBooksCallback);
    }
}
