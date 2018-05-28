package izenka.hfad.com.bookstore.presenter;


import android.content.Intent;
import android.view.View;
<<<<<<< HEAD

import izenka.hfad.com.bookstore.view.book.BookActivity;
import izenka.hfad.com.bookstore.view.search.ISearchView;

public class SearchPresenter implements IPresenter {
    private ISearchView searchView;
    private int categoryID;

    public SearchPresenter(ISearchView searchView, int categoryID){
=======
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
>>>>>>> 713af467c300756a5d42d3eca411d50cbcc356d3
        this.searchView = searchView;
        this.categoryID = categoryID;
    }

<<<<<<< HEAD
    @Override
=======
>>>>>>> 713af467c300756a5d42d3eca411d50cbcc356d3
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
<<<<<<< HEAD
        Intent intent = new Intent();
        intent.putExtra("bookID", view.getId());
        searchView.startActivityWithAnimation(view, intent, BookActivity.class);
=======
        searchView.startActivity(view, BookActivity.class, "bookID", view.getId());
>>>>>>> 713af467c300756a5d42d3eca411d50cbcc356d3
    }

    public void onBackClicked(){
        searchView.onBackClick();
    }
}
