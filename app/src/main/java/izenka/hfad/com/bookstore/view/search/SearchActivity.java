package izenka.hfad.com.bookstore.view.search;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.GridLayout;

import izenka.hfad.com.bookstore.R;
import izenka.hfad.com.bookstore.model.db_operations.ReadBooks;
import izenka.hfad.com.bookstore.Helper;
import izenka.hfad.com.bookstore.presenter.SearchPresenter;
import stanford.androidlib.SimpleActivity;

public class SearchActivity extends SimpleActivity implements ISearchView {

    private EditText etSearch;
    private SearchPresenter presenter;
    private GridLayout gridLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        if (presenter == null) {
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
        $B(R.id.btnBack).setOnClickListener(view -> presenter.onBackClicked());
        $B(R.id.btnDeleteInput).setOnClickListener(view -> presenter.onDeleteInputClicked());
        View bookView = LayoutInflater.from(this).inflate(R.layout.book, null, false);
        bookView.setOnClickListener(view -> presenter.onBookClicked(view));
    }

    @Override
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
        ReadBooks.getBooksFromSearch(this, R.id.gridLayoutSearch, categoryID);
    }

    @Override
    public void onSearch(int categoryID) {
        Helper.hideKeyboard(this, etSearch);
        showBooksFromSearch(categoryID);
    }


}
