package izenka.hfad.com.bookstore.view.search;

<<<<<<< HEAD
=======
import android.app.Activity;
>>>>>>> 713af467c300756a5d42d3eca411d50cbcc356d3
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
<<<<<<< HEAD
=======
import android.view.KeyEvent;
>>>>>>> 713af467c300756a5d42d3eca411d50cbcc356d3
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.GridLayout;
<<<<<<< HEAD

import izenka.hfad.com.bookstore.R;
import izenka.hfad.com.bookstore.model.db_operations.ReadBooks;
import izenka.hfad.com.bookstore.Helper;
import izenka.hfad.com.bookstore.presenter.SearchPresenter;
import stanford.androidlib.SimpleActivity;

public class SearchActivity extends SimpleActivity implements ISearchView {
=======
import android.widget.TextView;

import izenka.hfad.com.bookstore.BookActivity;
import izenka.hfad.com.bookstore.R;
import izenka.hfad.com.bookstore.controller.db_operations.GetBooksHelper;
import izenka.hfad.com.bookstore.controller.helper.Helper;
import izenka.hfad.com.bookstore.presenter.SearchPresenter;
import stanford.androidlib.SimpleActivity;

public class SearchActivity extends SimpleActivity implements SearchView {
>>>>>>> 713af467c300756a5d42d3eca411d50cbcc356d3

    private EditText etSearch;
    private SearchPresenter presenter;
    private GridLayout gridLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

<<<<<<< HEAD
        if (presenter == null) {
=======
        if(presenter == null){
>>>>>>> 713af467c300756a5d42d3eca411d50cbcc356d3
            presenter = new SearchPresenter(this, getIntent().getIntExtra("categoryID", 0));
        }

        presenter.onViewCreated();
    }

    @Override
    public void removeAllBookViews() {
        gridLayout.removeAllViews();
    }

    @Override
    public void initViews() {
        etSearch = $ET(R.id.etSearch);
        gridLayout = (GridLayout) findViewById(R.id.gridLayoutSearch);
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                presenter.onTextChanged(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        etSearch.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                presenter.onActionSearch();
                return true;
            }
            return false;
        });
<<<<<<< HEAD
        $B(R.id.btnBack).setOnClickListener(view -> presenter.onBackClicked());
        $B(R.id.btnDeleteInput).setOnClickListener(view -> presenter.onDeleteInputClicked());
=======
        $B(R.id.btnBack).setOnClickListener(view->presenter.onBackClicked());
        $B(R.id.btnDeleteInput).setOnClickListener(view->presenter.onDeleteInputClicked());
>>>>>>> 713af467c300756a5d42d3eca411d50cbcc356d3
        View bookView = LayoutInflater.from(this).inflate(R.layout.book, null, false);
        bookView.setOnClickListener(view -> presenter.onBookClicked(view));
    }

    @Override
<<<<<<< HEAD
    public void startActivity(Intent intent, Class activity) {

    }

    @Override
    public void startActivityWithAnimation(View view, Class activity) {

    }

    @Override
    public void startActivityWithAnimation(View view, Intent intent, Class activity) {
        Animation anim = AnimationUtils.loadAnimation(this, R.anim.alpha);
        view.startAnimation(anim);
        intent.setClass(this, activity);
        startActivity(intent);
    }

    @Override
=======
>>>>>>> 713af467c300756a5d42d3eca411d50cbcc356d3
    public void onBackClick() {
        Helper.hideKeyboard(this, etSearch);
        finish();
    }

    @Override
    public void onDeleteInputClick() {
        //GridLayout gridLayout = (GridLayout) findViewById(R.id.gridLayoutSearch);
        gridLayout.removeAllViews();
        $ET(R.id.etSearch).setText("");
    }

    @Override
    public void showBooksFromSearch(int categoryID) {
<<<<<<< HEAD
        ReadBooks.getBooksFromSearch(this, R.id.gridLayoutSearch, categoryID);
=======
        GetBooksHelper.getBooksFromSearch(this, R.id.gridLayoutSearch, categoryID);
>>>>>>> 713af467c300756a5d42d3eca411d50cbcc356d3
    }

    @Override
    public void onSearch(int categoryID) {
        Helper.hideKeyboard(this, etSearch);
        showBooksFromSearch(categoryID);
    }

<<<<<<< HEAD

=======
    @Override
    public void startActivity(View view, Class activity) {

    }

    @Override
    public void startActivity(Class activity, String extraName, int extra) {

    }

    @Override
    public void startActivity(View view, Class activity, String extraName, int extra) {
        Animation anim = AnimationUtils.loadAnimation(this, R.anim.alpha);
        view.startAnimation(anim);
        Intent intent = new Intent(this, activity);
        intent.putExtra(extraName, extra);
        startActivity(intent);
    }
>>>>>>> 713af467c300756a5d42d3eca411d50cbcc356d3
}
