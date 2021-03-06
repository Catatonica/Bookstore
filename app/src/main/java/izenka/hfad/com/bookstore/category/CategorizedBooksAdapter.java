package izenka.hfad.com.bookstore.category;


import android.animation.Animator;
import android.arch.paging.PagedListAdapter;
import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import izenka.hfad.com.bookstore.DatabaseCallback;
import izenka.hfad.com.bookstore.DatabaseSingleton;
import izenka.hfad.com.bookstore.R;
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
        @BindView((R.id.imgBtnBook)) ImageView imgBtnBook;
        @BindView((R.id.tvBookName)) TextView tvBookName;
        @BindView((R.id.tvBookAuthor)) TextView tvBookAuthor;
        @BindView((R.id.tvBookPrise)) TextView tvBookPrise;

        BookViewHolder(View itemView) {
            super(itemView);
            if (itemShouldBeScaled) {
                itemView.setScaleX(0.8f);
                itemView.setScaleY(0.8f);
            }
            ButterKnife.bind(this, itemView);
        }
    }

    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View bookView = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.book, parent, false);
        return new BookViewHolder(bookView);
    }

    @Override
    public void onBindViewHolder(@NonNull BookViewHolder holder, int position) {
        Book book = getItem(position);
        if (book != null) {
//            holder.itemView.setId(book.getBook_id());
            holder.tvBookName.setText(book.getTitle());
            holder.tvBookPrise.setText(book.getPrice());
            holder.itemView.setOnClickListener(view -> {
                animateView(view);
                viewModel.onBookClicked(book);
            });
            setAuthors(book, holder);
            setImage(book, holder);
        }
    }

    private void animateView(View view) {
        int cx = view.getWidth() / 2;
        int cy = view.getHeight() / 2;

        float finalRadius = (float) Math.hypot(cx, cy);

        Animator anim = ViewAnimationUtils.createCircularReveal(view, cx, cy, 0, finalRadius);

        view.setVisibility(View.VISIBLE);
        anim.start();
    }

    private void setImage(Book book, BookViewHolder holder) {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        String bookImage = book.getImagesPaths().get(0);
        StorageReference imageRef = storage.getReference().child(bookImage);
        Glide.with(holder.itemView.getContext())
             .using(new FirebaseImageLoader())
             .load(imageRef)
             .into(holder.imgBtnBook);
    }

    private void setAuthors(Book book, BookViewHolder holder) {
        DatabaseCallback<List<Author>> authorListCallback = authorList -> {
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
        DatabaseSingleton.getInstance().getAuthorList(book.getAuthorsIDs(), authorListCallback);
    }
}
