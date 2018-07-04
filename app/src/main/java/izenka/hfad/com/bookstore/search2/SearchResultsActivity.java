package izenka.hfad.com.bookstore.search2;

import android.arch.lifecycle.ViewModelProviders;
import android.arch.paging.PagedList;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.widget.Toast;

import java.util.concurrent.Executors;

import izenka.hfad.com.bookstore.R;
import izenka.hfad.com.bookstore.book.BookActivity;
import izenka.hfad.com.bookstore.model.db_classes.Book;

public class SearchResultsActivity extends AppCompatActivity implements SearchNavigator {

    private RecyclerView rvBookList;
    private SearchViewModel viewModel;
//    private CategorizedBooksAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);

        setToolbar();

        viewModel = ViewModelProviders.of(this).get(SearchViewModel.class);
        viewModel.setNavigator(this);

        rvBookList = findViewById(R.id.rvBookList);
        GridLayoutManager manager = new GridLayoutManager(this, 2);
        rvBookList.setLayoutManager(manager);


//        adapter = (new CategorizedBooksAdapter(new ArrayList<>(), viewModel, false));
//        rvBookList.setAdapter(adapter);

//        SearchedBookListDataSource dataSource = new SearchedBookListDataSource();
//
//        PagedList.Config config = new PagedList.Config.Builder()
//                .setEnablePlaceholders(false)
//                .setPageSize(4)
//                .build();
//
//        PagedList<Book> bookList = new PagedList.Builder<Integer, Book>(dataSource, config)
//                .setFetchExecutor(Executors.newSingleThreadExecutor())
//                .setNotifyExecutor(new MainThreadExecutor())
//                .setInitialKey(0)
//                .build();
//
////        PagedList.Builder<Integer, Book> builder = new PagedList.Builder<Integer, Book>(dataSource, 4);
////        PagedList<Book> bookList = builder
////                .setNotifyExecutor(Executors.newSingleThreadExecutor())
////                .setFetchExecutor(new MainThreadExecutor())
////                .build();
//
////        CategorizedBooksAdapter adapter = new CategorizedBooksAdapter(DIFF_CALLBACK);
//        SearchedBookListAdapter adapter = new SearchedBookListAdapter(DIFF_CALLBACK);
//        adapter.submitList(bookList);
//        rvBookList.setAdapter(adapter);

//        rvBookList.setAdapter(adapter);


//        FirebaseDataSourceFactory factory = new FirebaseDataSourceFactory();
//
//        PagedList.Config config = new PagedList.Config.Builder()
//                .setEnablePlaceholders(false)
//                .setPageSize(4)
//                .build();
//
//        LiveData<PagedList<Book>> bookList = new LivePagedListBuilder<>(factory, config)
//                .setFetchExecutor(new MainThreadExecutor())
//                .build();
//
//
//        CategorizedBooksAdapter adapter = new CategorizedBooksAdapter(DIFF_CALLBACK);
//
//        bookList.observe(this, new Observer<PagedList<Book>>() {
//            @Override
//            public void onChanged(@Nullable PagedList<Book> books) {
//                adapter.submitList(books);
//            }
//        });

//        rvBookList.setAdapter(adapter);
    }

    private void setToolbar() {
        setSupportActionBar(findViewById(R.id.toolbar));
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(R.string.search);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_arrow_back_white_24dp);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu, menu);
//        menu.findItem(R.id.search).expandActionView();
        SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setIconifiedByDefault(true);
        searchView.setIconified(false);
        searchView.requestFocus();
        //TODO: create ContentProvider for suggections
        searchView.setQueryHint("Название книги / автор");
        BookListAdapter adapter = new BookListAdapter(DIFF_CALLBACK);


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
//                if (newText.isEmpty()) {
//                    int size = adapter.getBookList().size();
//                    adapter.getBookList().clear();
//                    adapter.notifyItemRangeRemoved(0, size);
//                } else {
                viewModel.getBookListLiveData(newText).observe(SearchResultsActivity.this, bookList -> {
//                    adapter.submitList(bookList);
//                    rvBookList.setAdapter(adapter);
                });


//                        ((CategorizedBooksAdapter) rvBookList.getAdapter()).setBookList(bookList);
//                    });
//                }
                return true;
            }
        });

        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                Toast.makeText(SearchResultsActivity.this, "onClose", Toast.LENGTH_LONG).show();
                return false;
            }
        });

////        searchView.setIconified(false);
//        searchView.setIconified(true);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    @Override
    public void onBookClicked(int bookID) {
        Intent intent = new Intent();
        intent.putExtra("bookID", bookID);
        intent.setClass(this, BookActivity.class);
        startActivity(intent);
    }

    public static final DiffUtil.ItemCallback<Book> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<Book>() {
                @Override
                public boolean areItemsTheSame(
                        @NonNull Book oldBook, @NonNull Book newBook) {
                    // User properties may have changed if reloaded from the DB, but ID is fixed
                    return oldBook.book_id == newBook.book_id;
                }

                @Override
                public boolean areContentsTheSame(
                        @NonNull Book oldBook, @NonNull Book newBook) {
                    // NOTE: if you use equals, your object must properly override Object#equals()
                    // Incorrectly returning false here will result in too many animations.
                    return oldBook.equals(newBook);
                }
            };
}
