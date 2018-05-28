package izenka.hfad.com.bookstore.view.book_list.recycler_view;

//TODO: extends Adapter<>

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import izenka.hfad.com.bookstore.R;
import izenka.hfad.com.bookstore.presenter.BookListPresenter;

public class BookListAdapter extends RecyclerView.Adapter<BookViewHolder> {

    private BookListPresenter presenter;

    public BookListAdapter(BookListPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public BookViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View bookView = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.book, parent, false);
        bookView.setOnClickListener(view -> presenter.onBookClicked(view));
        return new BookViewHolder(bookView);
    }

    @Override
    public void onBindViewHolder(BookViewHolder holder, int position) {
        presenter.onBindViewHolder(holder, position);
    }

    @Override
    public int getItemCount() {
        return presenter.getItemCount();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
}
