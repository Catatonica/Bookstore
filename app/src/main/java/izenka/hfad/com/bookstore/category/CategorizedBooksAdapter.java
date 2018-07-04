package izenka.hfad.com.bookstore.category;


import android.arch.paging.PagedListAdapter;
import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;
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

import izenka.hfad.com.bookstore.DatabaseSingleton;
import izenka.hfad.com.bookstore.R;
import izenka.hfad.com.bookstore.callbacks.AuthorListCallback;
import izenka.hfad.com.bookstore.model.db_classes.Author;
import izenka.hfad.com.bookstore.model.db_classes.Book;

public class CategorizedBooksAdapter extends PagedListAdapter<Book, CategorizedBooksAdapter.BookViewHolder> {

    private CategorizedBooksViewModel viewModel;
    private static boolean itemShouldBeScaled;

    CategorizedBooksAdapter(@NonNull DiffUtil.ItemCallback<Book> diffCallback) {
        super(diffCallback);
    }

    void shouldBeScaled(boolean scaled) {
        itemShouldBeScaled = scaled;
    }

    void setViewModel(CategorizedBooksViewModel viewModel) {
        this.viewModel = viewModel;
    }

    static class BookViewHolder extends RecyclerView.ViewHolder {
        ImageView imgBtnBook;
        TextView tvBookName;
        TextView tvBookAuthor;
        TextView tvBookPrise;

        BookViewHolder(View itemView) {
            super(itemView);
            if (itemShouldBeScaled) {
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
        Book book = getItem(position);
        if (book != null) {
            holder.itemView.setId(book.book_id);
            holder.tvBookName.setText(book.title);
            holder.tvBookPrise.setText(book.price);

            AuthorListCallback authorListCallback = authorList -> {
                StringBuilder authorsStringBuilder = new StringBuilder();
                for (Author author : authorList) {
                    authorsStringBuilder.append(author.author_surname)
                                        .append(" ")
                                        .append(author.author_name.substring(0, 1))
                                        .append("., ")
                                        .append('\n');
                }
                authorsStringBuilder.delete(authorsStringBuilder.length() - 3, authorsStringBuilder.length());
                holder.tvBookAuthor.setText(authorsStringBuilder);
            };
            DatabaseSingleton.getInstance().getAuthorList(book.authorsIDs, authorListCallback);
            FirebaseStorage storage = FirebaseStorage.getInstance();
            String bookImage = book.imagesPaths.get(0);
            StorageReference imageRef = storage.getReference().child(bookImage);
            Glide.with(holder.itemView.getContext()/* context */)
                 .using(new FirebaseImageLoader())
                 .load(imageRef)
                 .into(holder.imgBtnBook);
            holder.itemView.setOnClickListener(view -> viewModel.onBookClicked(book));
        }
    }
}
