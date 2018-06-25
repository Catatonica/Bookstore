package izenka.hfad.com.bookstore.category;


import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.List;

import izenka.hfad.com.bookstore.R;
import izenka.hfad.com.bookstore.model.db_classes.Author;
import izenka.hfad.com.bookstore.model.db_classes.Book;

public class BookListAdapter extends RecyclerView.Adapter<BookListAdapter.BookViewHolder> {

    private List<Book> bookList;
    private BookListViewModel viewModel;
    private static boolean shouldBeScaled;

     BookListAdapter(List<Book> bookList, BookListViewModel viewModel, boolean itemShouldBeScaled) {
        this.bookList = bookList;
        this.viewModel = viewModel;
        shouldBeScaled = itemShouldBeScaled;
    }

    static class BookViewHolder extends RecyclerView.ViewHolder {
        ImageView imgBtnBook;
        TextView tvBookName;
        TextView tvBookAuthor;
        TextView tvBookPrise;

         BookViewHolder(View itemView) {
            super(itemView);
             if(shouldBeScaled){
                 itemView.setScaleX(0.8f);
                 itemView.setScaleY(0.8f);
             }
            imgBtnBook = itemView.findViewById(R.id.imgBtnBook);
            tvBookName = itemView.findViewById(R.id.tvBookName);
            tvBookAuthor = itemView.findViewById(R.id.tvBookAuthor);
            tvBookPrise = itemView.findViewById(R.id.tvBookPrise);
        }
    }

    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View bookView = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.book, parent, false);
        return new BookViewHolder(bookView);
    }

    @Override
    public void onBindViewHolder(BookViewHolder holder, int position) {
        Book book = bookList.get(position);
        holder.itemView.setId(book.book_id);
        holder.tvBookName.setText(book.title);
        holder.tvBookPrise.setText(book.price);
        StringBuilder bookAuthors = new StringBuilder();
        for (Author author : book.authors) {
            String authorName = author.author_name.substring(0, 1);
            String authorSurname = author.author_surname;
            bookAuthors.append(authorSurname).append(" ").append(authorName).append("., ");
        }
        holder.tvBookAuthor.setText(bookAuthors.substring(0, bookAuthors.length() - 2));
        holder.imgBtnBook.setId(book.book_id);

        FirebaseStorage storage = FirebaseStorage.getInstance();
        String bookImage = book.imagesPaths.get(0);
        StorageReference imageRef = storage.getReference().child(bookImage);
        Glide.with(holder.itemView.getContext()/* context */)
             .using(new FirebaseImageLoader())
             .load(imageRef)
             .into(holder.imgBtnBook);
        holder.itemView.setOnClickListener(view -> {
//            viewModel.setBook(book);
            viewModel.onBookClicked(book);
        });
    }

    @Override
    public int getItemCount() {
        return bookList.size();
    }

}
