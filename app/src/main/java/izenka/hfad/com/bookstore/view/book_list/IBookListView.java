package izenka.hfad.com.bookstore.view.book_list;


import java.util.List;

import izenka.hfad.com.bookstore.model.db_classes.Book;
import izenka.hfad.com.bookstore.view.IView;

public interface IBookListView extends IView {
    void showBookList(List<Book> bookList);
    void setCategoryName(int categoryID);
}
