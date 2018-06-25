package izenka.hfad.com.bookstore.presenter;


import android.content.Intent;
import android.view.View;

import izenka.hfad.com.bookstore.book.BookActivity;
import izenka.hfad.com.bookstore.view.search.ISearchView;

public class SearchPresenter implements IPresenter {
    private ISearchView searchView;
    private int categoryID;

    public SearchPresenter(ISearchView searchView, int categoryID){
        this.searchView = searchView;
        this.categoryID = categoryID;
    }

    @Override
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
        Intent intent = new Intent();
        intent.putExtra("bookID", view.getId());
        searchView.startActivityWithAnimation(view, intent, BookActivity.class);
    }

    public void onBackClicked(){
        searchView.onBackClick();
    }
}
