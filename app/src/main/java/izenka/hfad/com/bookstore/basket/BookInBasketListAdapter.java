package izenka.hfad.com.bookstore.basket;


import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.List;

import izenka.hfad.com.bookstore.DatabaseSingleton;
import izenka.hfad.com.bookstore.R;
import izenka.hfad.com.bookstore.callbacks.AuthorListCallback;
import izenka.hfad.com.bookstore.model.db_classes.Author;
import izenka.hfad.com.bookstore.model.db_classes.Book;

public class BookInBasketListAdapter extends RecyclerView.Adapter<BookInBasketListAdapter.BookViewHolder> {

    private BasketViewModel viewModel;
    private List<BookInBasketModel> bookInBasketModelList;
    private boolean isChecked = false;
    private int checkedNum = 0;

    BookInBasketListAdapter(BasketViewModel viewModel, List<BookInBasketModel> bookInBasketModelList) {
        this.viewModel = viewModel;
        this.bookInBasketModelList = bookInBasketModelList;
    }

    public void setList(List<BookInBasketModel> bookInBasketModelList) {
        this.bookInBasketModelList = bookInBasketModelList;
    }

    static class BookViewHolder extends RecyclerView.ViewHolder {
        //TODO: продумать лоступность книги(если нет, при добавлении в корзину)
        CheckBox checkBox;
        TextView tvTitle;
        TextView tvAuthor;
        //        TextView tvPublicationYear;
        TextView tvPriseForOne;
        TextView tvPriseForSeveral;
        NumberPicker npBooksCount;
        ImageView ivBook;
        RelativeLayout rlBackground;
        RelativeLayout rlForeground;

        BookViewHolder(View itemView) {
            super(itemView);
            rlBackground = itemView.findViewById(R.id.rlBackground);
            rlForeground = itemView.findViewById(R.id.rlForeground);
            checkBox = itemView.findViewById(R.id.checkBox);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvAuthor = itemView.findViewById(R.id.tvAuthor);
//            tvPublicationYear = itemView.findViewById(R.id.tvPublicationYear);
            tvPriseForOne = itemView.findViewById(R.id.tvPriseForOne);
            tvPriseForSeveral = itemView.findViewById(R.id.tvPriseForSeveral);
            npBooksCount = itemView.findViewById(R.id.npBooksCount);
            ivBook = itemView.findViewById(R.id.ivBook);
        }
    }

    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.book_in_basket, parent, false);
        return new BookViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookViewHolder holder, int position) {
        BookInBasketModel bookInBasket = bookInBasketModelList.get(position);
        Book book = bookInBasket.book;
        int count = bookInBasket.count;
        holder.tvTitle.setText(book.title);
//        Log.d("OnBindViewHolder", " title = " + book.title + ", bookID = " + bookID);
        holder.npBooksCount.setMinValue(1);
        holder.npBooksCount.setMaxValue(book.count);
        holder.npBooksCount.setValue(1);
        holder.tvPriseForOne.setText(book.price);
//        holder.checkBox.setOnClickListener(cb->{
//            if(holder.checkBox.isChecked()){
//                checkedNum++;
//            } else {
//                checkedNum--;
//            }
//            if(checkedNum>0){
//                viewModel.enableButtonRegister(true);
//            } else{
//                viewModel.enableButtonRegister(false);
//            }
//        });

        BookIdAndCountModel bookIdAndCountModel = new BookIdAndCountModel(book.book_id, holder.npBooksCount.getValue());
//        bookIdAndCountModel.bookID = book.book_id;
//        bookIdAndCountModel.count = holder.npBooksCount.getValue();

        holder.npBooksCount.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                bookIdAndCountModel.count = i1;
                float priceForSeveral = Float.valueOf(book.price.substring(0, book.price.length() - 3)) * i1;
                Log.d("priceForSeveral", String.valueOf(priceForSeveral));
                holder.tvPriseForSeveral.setText(" / " + String.valueOf(priceForSeveral) + " р.");
                if (holder.checkBox.isChecked()) {
                    if (i < i1) {
                        viewModel.addToTotalPrice(Math.abs(Float.valueOf(book.price.substring(0, book.price.length() - 3)) * i - priceForSeveral));
                    } else {
                        viewModel.subtractFromTotalPrice(Math.abs(Float.valueOf(book.price.substring(0, book.price.length() - 3)) * i - priceForSeveral));
                    }
                }
            }
        });

        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    checkedNum++;
                    viewModel.addBookInBasketModel(bookIdAndCountModel);
//                    String priseForSeveral = holder.tvPriseForSeveral.getText().toString();
//                    if(priseForSeveral.isEmpty()){
                    viewModel.addToTotalPrice(Float.valueOf(book.price.substring(0, book.price.length() - 3)) * holder.npBooksCount.getValue());
//                    } else {
//                        viewModel.addToTotalPrice(Float.valueOf(priseForSeveral.substring(3, priseForSeveral.length() - 3)));
//                    }
                } else {
                    checkedNum--;
                    viewModel.removeBookInBasketModel(bookIdAndCountModel);
//                    String priseForSeveral = holder.tvPriseForSeveral.getText().toString();
//                    if(priseForSeveral.isEmpty()){
                    viewModel.subtractFromTotalPrice(Float.valueOf(book.price.substring(0, book.price.length() - 3)) * holder.npBooksCount.getValue());
//                    } else{
//                        viewModel.subtractFromTotalPrice(Float.valueOf(priseForSeveral.substring(3, priseForSeveral.length() - 3))*holder.npBooksCount.getValue());
//                    }
                }
                if (checkedNum > 0) {
                    viewModel.enableButtonRegister(true);
                } else {
                    viewModel.enableButtonRegister(false);
                }
            }
        });
        holder.checkBox.setChecked(isChecked);

        AuthorListCallback authorListCallback = authorList -> {
            StringBuilder authorsStringBuilder = new StringBuilder();
            for (Author author : authorList) {
                authorsStringBuilder.append(author.author_surname)
                                    .append(" ")
                                    .append(author.author_name.substring(0, 1))
                                    .append("., ")
                                    .append('\n');
            }
            authorsStringBuilder.delete(authorsStringBuilder.length() - 3, authorsStringBuilder.length() );
            holder.tvAuthor.setText(authorsStringBuilder);
        };
        DatabaseSingleton.getInstance().getAuthorList(book.authorsIDs, authorListCallback);

        FirebaseStorage storage = FirebaseStorage.getInstance();
        String bookImage = book.imagesPaths.get(0);
        StorageReference imageRef = storage.getReference().child(bookImage);
        Glide.with(holder.itemView.getContext()/* context */)
             .using(new FirebaseImageLoader())
             .load(imageRef)
             .into(holder.ivBook);
    }

    @Override
    public int getItemCount() {
        return bookInBasketModelList.size();
    }

    public void checkAll() {
        if (isChecked) {
            isChecked = false;
        } else {
            isChecked = true;
        }
        notifyDataSetChanged();
    }

    public void removeItem(RecyclerView.ViewHolder viewHolder) {
        int position = viewHolder.getAdapterPosition();
        viewModel.deleteBookFromBasket(bookInBasketModelList.get(position).book.book_id);
        bookInBasketModelList.remove(position);
        ((CheckBox) viewHolder.itemView.findViewById(R.id.checkBox)).setChecked(false);
        // notify the item removed by position
        // to perform recycler view delete animations
        // NOTE: don't call notifyDataSetChanged()
        if (bookInBasketModelList.isEmpty()) {
            viewModel.setEmptyBasket();
        }
        notifyItemRemoved(position);
    }
}
