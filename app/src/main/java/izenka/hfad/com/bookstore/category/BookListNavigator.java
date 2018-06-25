package izenka.hfad.com.bookstore.category;


import izenka.hfad.com.bookstore.model.db_classes.Book;

public interface BookListNavigator {
    void onBookClicked(int bookID);
    void onSearchInCategoryClicked();
}
