package izenka.hfad.com.bookstore.orders;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import izenka.hfad.com.bookstore.R;


public class BookListAdapter extends RecyclerView.Adapter<BookListAdapter.BookAndCountViewHolder> {

    private List<BookInOrderModel> bookList;

    BookListAdapter(List<BookInOrderModel> bookList) {
        this.bookList = bookList;
    }

    void setBookInOrderList(List<BookInOrderModel> bookList) {
        this.bookList = bookList;
        notifyDataSetChanged();
    }

    static class BookAndCountViewHolder extends RecyclerView.ViewHolder {

        @BindView((R.id.tvTitle)) TextView title;
        @BindView((R.id.tvPriceForOne)) TextView priceForOne;
        @BindView((R.id.tvCount)) TextView count;

        BookAndCountViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    @NonNull
    @Override
    public BookAndCountViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View bookView = LayoutInflater.from(parent.getContext()).inflate(R.layout.book_in_order, parent, false);
        return new BookAndCountViewHolder(bookView);
    }

    @Override
    public void onBindViewHolder(@NonNull BookAndCountViewHolder holder, int position) {
        BookInOrderModel bookInOrder = bookList.get(position);
        if (bookInOrder != null) {
            holder.title.setText(String.format("\"%s\"", bookInOrder.getBook().getTitle()));
            holder.priceForOne.setText(bookInOrder.getBook().getPrice());
            holder.count.setText(String.valueOf(bookInOrder.getCount()));
        }
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public int getItemCount() {
        return bookList.size();
    }
}
