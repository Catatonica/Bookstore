package izenka.hfad.com.bookstore.view.book_list;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import java.util.List;

import izenka.hfad.com.bookstore.BookListAdapter;
import izenka.hfad.com.bookstore.R;
import izenka.hfad.com.bookstore.controller.db_operations.SetHelper;
import izenka.hfad.com.bookstore.model.db_classes.Book;
import izenka.hfad.com.bookstore.presenter.BookListPresenter;
import stanford.androidlib.SimpleActivity;

public class BookListActivity extends SimpleActivity implements IBookListView {

    private BookListPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_list);

        if (presenter == null) {
            presenter = new BookListPresenter(this, getIntent().getIntExtra("categoryID", 0));
        }
        presenter.onViewCreated();
    }

    @Override
    public void startActivity(View view, Class activity) {
        Animation anim = AnimationUtils.loadAnimation(this, R.anim.alpha);
        view.startAnimation(anim);
        Intent intent = new Intent(this, activity);
        startActivity(intent);
    }

    @Override
    public void startActivity(Class activity, String extraName, int extra) {
        Intent intent = new Intent(this, activity);
        intent.putExtra(extraName, extra);
        startActivity(intent);
    }

    @Override
    public void startActivity(View view, Class activity, String extraName, int extra) {
        Animation anim = AnimationUtils.loadAnimation(this, R.anim.alpha);
        view.startAnimation(anim);
        Intent intent = new Intent(this, activity);
        intent.putExtra(extraName, extra);
        startActivity(intent);
    }

    @Override
    public void initViews() {
        $IB(R.id.imgBtnOrders).setOnClickListener(view -> presenter.onOrdersClicked(view));
        $IB(R.id.imgBtnBasket).setOnClickListener(view -> presenter.onBasketClicked(view));
        $IV(R.id.btnGoBack).setOnClickListener(view -> onBackPressed());
        $ET(R.id.etCategorySearch).setOnClickListener(view -> presenter.onSearchClicked());
        View bookView = LayoutInflater.from(this).inflate(R.layout.book, null, false);
        bookView.setOnClickListener(view -> presenter.onBookClicked(view));
    }

    @Override
    public void setCategoryName(int categoryID){
        SetHelper.setName(this, "category/" + categoryID + "/category_name/", R.id.tvCategory);
    }

    @Override
    public void showBookList(List<Book> bookList) {
        RecyclerView rvBookList = findViewById(R.id.rvBookList);
        GridLayoutManager manager = new GridLayoutManager(this, 2);
        rvBookList.setLayoutManager(manager);
        BookListAdapter adapter = new BookListAdapter(bookList);
        rvBookList.setAdapter(adapter);
    }

}
