package izenka.hfad.com.bookstore.view.book_list.recycler_view;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.firebase.storage.StorageReference;

import izenka.hfad.com.bookstore.R;

public class BookViewHolder extends RecyclerView.ViewHolder implements IBookView {

    private ImageView imgBtnBook;
    private TextView tvBookName;
    private TextView tvBookAuthor;
    private TextView tvBookPrise;

    public BookViewHolder(View itemView) {
        super(itemView);
        imgBtnBook = itemView.findViewById(R.id.imgBtnBook);
        tvBookName = itemView.findViewById(R.id.tvBookName);
        tvBookAuthor = itemView.findViewById(R.id.tvBookAuthor);
        tvBookPrise = itemView.findViewById(R.id.tvBookPrise);
    }

    @Override
    public void setTitle(String title) {
        tvBookName.setText("\"" +title + "\"");
    }

    @Override
    public void setPrise(String prise) {
        tvBookPrise.setText(prise);
    }

    @Override
    public void setId(int id) {
        itemView.setId(id);
    }

    @Override
    public void setAuthor(String author) {
        tvBookAuthor.setText(author);
    }

    @Override
    public void setImageId(int id) {
        imgBtnBook.setId(id);
    }

    @Override
    public void setImage(StorageReference imageRef) {
        Glide.with(itemView.getContext()/* context */)
             .using(new FirebaseImageLoader())
             .load(imageRef)
             .into(imgBtnBook);
    }
}
