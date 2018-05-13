package izenka.hfad.com.bookstore.controller;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.TextView;

import izenka.hfad.com.bookstore.R;
import izenka.hfad.com.bookstore.controller.db_operations.GetBooksHelper;
import izenka.hfad.com.bookstore.controller.helper.Helper;
import stanford.androidlib.SimpleActivity;

public class SearchActivity extends SimpleActivity {

    EditText etSearch;
    private Thread myThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        final Activity activity = this;

        Intent intent = getIntent();
        final int categoryID = intent.getIntExtra("categoryID", 0);

        etSearch = $ET(R.id.etSearch);
        etSearch.addTextChangedListener(new TextWatcher() {

            GridLayout gridLayout = (GridLayout) findViewById(R.id.gridLayoutSearch);

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                gridLayout.removeAllViews();

                if (!s.toString().isEmpty() && !s.toString().endsWith(" ")) {
                    GetBooksHelper.getBooksFromSearch(activity, R.id.gridLayoutSearch, categoryID);
                }
               /* else{
                    GridLayout gridLayout = (GridLayout) findViewById(R.id.gridLayoutSearch);
                    gridLayout.removeAllViews();
                }*/
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        etSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    Helper.hideKeyboard(activity, etSearch);
                    GetBooksHelper.getBooksFromSearch(activity, R.id.gridLayoutSearch, categoryID);
                    //getBooks(categoryID);
                    return true;
                }
                return false;
            }
        });


        myThread = new Thread( // создаём новый поток
                               new Runnable() { // описываем объект Runnable в конструкторе
                                   public void run() {
                                       GetBooksHelper.getBooksFromSearch(activity, R.id.gridLayoutSearch, categoryID);
                                   }
                               }
        );

        //  ScrollView scrollView=$SCV(R.id.scrollView);
        // scrollView.setOnTouchListener(new View.OnTouchListener() {
        //    @Override
        //     public boolean onTouch(View v, MotionEvent event) {
        //       hideKeyboard();
        //        return false;
        //    }
        // });
    }

    public void onBtnDeleteInputClick(final View view) {

        GridLayout gridLayout = (GridLayout) findViewById(R.id.gridLayoutSearch);
        gridLayout.removeAllViews();
        $ET(R.id.etSearch).setText("");
    }

    public void onReturnBackClick(View view) {
        // InputMethodManager imm = (InputMethodManager)getSystemService(this.INPUT_METHOD_SERVICE);
        // imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        Helper.hideKeyboard(this, etSearch);
        finish();
    }

    public void onBookClick(View view) {
        Animation anim = AnimationUtils.loadAnimation(this, R.anim.my_alpha);
        view.startAnimation(anim);
        Intent intent = new Intent(this, BookActivity.class);
        intent.putExtra("bookID", view.getId());
        startActivity(intent);
    }
}
