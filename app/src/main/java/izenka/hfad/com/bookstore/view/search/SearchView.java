package izenka.hfad.com.bookstore.view.search;


import izenka.hfad.com.bookstore.view.IView;

public interface SearchView extends IView {
     void onBackClick();
     void onDeleteInputClick();
     void removeAllBookViews();
     void showBooksFromSearch(int categoryID);
     void onSearch(int categoryID);
}
