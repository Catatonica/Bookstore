package izenka.hfad.com.bookstore.presenter;


import android.content.Intent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.GridLayout;

import izenka.hfad.com.bookstore.BookActivity;
import izenka.hfad.com.bookstore.R;
import izenka.hfad.com.bookstore.controller.db_operations.GetBooksHelper;
import izenka.hfad.com.bookstore.view.search.SearchView;

public class SearchPresenter {
    private SearchView searchView;
    private int categoryID;

    public SearchPresenter(SearchView searchView, int categoryID){
        this.searchView = searchView;
        this.categoryID = categoryID;
    }

    public void onViewCreated(){
        searchView.initViews();
        searchView.showBooksFromSearch(categoryID);
    }

    public void onTextChanged(CharSequence s){
        searchView.removeAllBookViews();
        if (!s.toString().isEmpty() && !s.toString().endsWith(" ")) {
            searchView.showBooksFromSearch(categoryID);
        }
    }

    public void onActionSearch(){
        searchView.onSearch(categoryID);
    }

    public void onDeleteInputClicked() {
        searchView.onDeleteInputClick();
    }

    public void onBookClicked(View view){
        searchView.startActivity(view, BookActivity.class, "bookID", view.getId());
    }

    public void onBackClicked(){
        searchView.onBackClick();
    }
}
