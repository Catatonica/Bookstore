package izenka.hfad.com.bookstore.category;

import android.arch.paging.PositionalDataSource;
import android.support.annotation.NonNull;

import java.util.List;

import izenka.hfad.com.bookstore.DatabaseSingleton;
import izenka.hfad.com.bookstore.DatabaseCallback;
import izenka.hfad.com.bookstore.model.db_classes.Book;


public class CategorizedBooksDataSource extends PositionalDataSource<Book> {

    private String categoryID;

    CategorizedBooksDataSource(String categoryID) {
        this.categoryID = categoryID;
    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams params, @NonNull LoadInitialCallback<Book> callback) {
        DatabaseCallback<List<Book>> categorizedBooksCallback = bookList -> callback.onResult(bookList, params.requestedStartPosition);
        DatabaseSingleton.getInstance().getCategorizedPagedBookList(categoryID,
                                                                    params.requestedStartPosition,
                                                                    params.requestedLoadSize,
                                                                    categorizedBooksCallback);
    }

    @Override
    public void loadRange(@NonNull LoadRangeParams params, @NonNull LoadRangeCallback<Book> callback) {
        DatabaseCallback<List<Book>> categorizedBooksCallback = callback::onResult;
        DatabaseSingleton.getInstance().getCategorizedPagedBookList(categoryID,
                                                                    params.startPosition,
                                                                    params.loadSize,
                                                                    categorizedBooksCallback);
    }
}
