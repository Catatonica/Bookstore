package izenka.hfad.com.bookstore.orders;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import izenka.hfad.com.bookstore.R;
import izenka.hfad.com.bookstore.model.db_classes.Author;


public class BookListAdapter extends BaseAdapter {

    private List<BookInOrderModel> bookList;

    BookListAdapter(List<BookInOrderModel> bookList){
        this.bookList = bookList;
    }

    @Override
    public int getCount() {
        return bookList.size();
    }

    @Override
    public Object getItem(int i) {
        return bookList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View bookView = view;
        if(view == null){
            bookView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.book_in_order, viewGroup, false);
        }
        BookInOrderModel bookInOrder = bookList.get(i);
        ((TextView) bookView.findViewById(R.id.tvTitle)).setText(String.format("\"%s\"",bookInOrder.book.title));
        ((TextView) bookView.findViewById(R.id.tvPriceForOne)).setText(bookInOrder.book.price);
        ((TextView) bookView.findViewById(R.id.tvCount)).setText(String.valueOf(bookInOrder.count));
//        StringBuilder authors = new StringBuilder();
//        for(Author author:bookInOrder.book.authors){
//            authors.append(author.author_surname).append(author.author_name.substring(0, 1)).append(".");
//        }
//        ((TextView) bookView.findViewById(R.id.tvAuthor)).setText(authors);
        return bookView;
    }
}
