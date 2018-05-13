package izenka.hfad.com.bookstore;

//TODO: extends Adapter<>

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

import izenka.hfad.com.bookstore.model.db_classes.Book;

public class BookListAdapter extends RecyclerView.Adapter<BookListAdapter.BookViewHolder> {

    private List<Book> bookList;

    public BookListAdapter(List<Book> bookList) {
        this.bookList = bookList;
    }

    static class BookViewHolder extends RecyclerView.ViewHolder {

        ImageView imgBtnBook;
        TextView tvBookName;
        TextView tvBookAuthor;
        TextView tvBookPrise;

        BookViewHolder(View itemView) {
            super(itemView);
            imgBtnBook = itemView.findViewById(R.id.imgBtnBook);
            tvBookName = itemView.findViewById(R.id.tvBookName);
            tvBookAuthor = itemView.findViewById(R.id.tvBookAuthor);
            tvBookPrise = itemView.findViewById(R.id.tvBookPrise);
        }
    }

    @Override
    public BookViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View bookView = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.book, parent, false);
        return new BookViewHolder(bookView);
    }

    @Override
    public void onBindViewHolder(BookViewHolder holder, int position) {
        //presenter.bindViewHolder
        Book book = bookList.get(position);

        holder.itemView.setId(book.book_id);
        holder.tvBookName.setText("\"" + book.title + "\"");
        holder.tvBookPrise.setText(book.price);

        String authors = "";
//        for(String a: book.authors){
//            authors+=a+", ";
//        }

        for (int i = 0; i < book.authors.size(); i++) {
            authors += book.authors.get(i) + ", ";
        }
        holder.tvBookAuthor.setText(authors);
//        holder.tvBookAuthor.setText(auth);

        holder.imgBtnBook.setId(book.book_id);
        FirebaseStorage storage = FirebaseStorage.getInstance();
        String bookImage = book.imagesPaths.get(0);
        StorageReference imageRef = storage.getReference().child(bookImage);
        Glide.with(holder.itemView.getContext() /* context */)
             .using(new FirebaseImageLoader())
             .load(imageRef)
             .into(holder.imgBtnBook);
    }

    @Override
    public int getItemCount() {
        return bookList.size();
    }

//    @Override
//    public void onBindViewHolder(BookViewHolder holder, int position) {
//        //presenter.bindViewHolder
//        final Long bookID = booksIDs.get(position);
//        waitForBookToLoad(bookID);
//        Book book = GetBooksHelper.getBook();
//        holder.itemView.setId(book.book_id);
//        holder.tvBookName.setText("\"" + book.title + "\"");
//        holder.tvBookPrise.setText(book.price);
//
//        String authors = "";
//        for (String a : book.authors) {
//            authors += a + ", ";
//        }
//
//        holder.tvBookAuthor.setText(authors);
//
//        holder.imgBtnBook.setId(book.book_id);
//        FirebaseStorage storage = FirebaseStorage.getInstance();
//        String bookImage = book.imagesPaths.get(0);
//        StorageReference imageRef = storage.getReference().child(bookImage);
//        Glide.with(holder.itemView.getContext() /* context */)
//             .using(new FirebaseImageLoader())
//             .load(imageRef)
//             .into(holder.imgBtnBook);
//
//    }
//
//    private void waitForBookToLoad(final Long bookID) {
//        Thread loadingBookThread = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                GetBooksHelper.loadBook(bookID);
//            }
//        });
//        loadingBookThread.start();
//        try {
//            loadingBookThread.join(2000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//
//    }
//
//    @Override
//    public int getItemCount() {
//        return booksIDs.size();
//    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
}
