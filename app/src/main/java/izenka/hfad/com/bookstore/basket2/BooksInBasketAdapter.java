package izenka.hfad.com.bookstore.basket2;


import android.arch.paging.PagedListAdapter;
import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;
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

import izenka.hfad.com.bookstore.R;
import izenka.hfad.com.bookstore.model.db_classes.Author;
import izenka.hfad.com.bookstore.model.db_classes.Book;

public class BooksInBasketAdapter extends PagedListAdapter<BookInBasketModel, BooksInBasketAdapter.BookViewHolder> {

    protected BooksInBasketAdapter(@NonNull DiffUtil.ItemCallback<BookInBasketModel> diffCallback) {
        super(diffCallback);
    }

    private BasketViewModel viewModel;
    private boolean isChecked = false;
    private int checkedNum = 0;

    public void setViewModel(BasketViewModel viewModel) {
        this.viewModel = viewModel;
    }


    static class BookViewHolder extends RecyclerView.ViewHolder {
        //TODO: продумать лоступность книги(если нет, при добавлении в корзину)
        CheckBox checkBox;
        TextView tvTitle;
        TextView tvAuthor;
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
        BookInBasketModel bookInBasket = getItem(position);
        if (bookInBasket != null) {
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

//        holder.tvPublicationYear.setText(String.valueOf(book.publication_year));
            StringBuilder bookAuthors = new StringBuilder();
            for (Author author : book.authors) {
                String authorName = author.author_name.substring(0, 1);
                String authorSurname = author.author_surname;
                bookAuthors.append(authorSurname).append(" ").append(authorName).append("., ");
            }
            holder.tvAuthor.setText(bookAuthors.substring(0, bookAuthors.length() - 2));
            FirebaseStorage storage = FirebaseStorage.getInstance();
            String bookImage = book.imagesPaths.get(0);
            StorageReference imageRef = storage.getReference().child(bookImage);
            Glide.with(holder.itemView.getContext()/* context */)
                 .using(new FirebaseImageLoader())
                 .load(imageRef)
                 .into(holder.ivBook);
        }
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
        viewModel.deleteBookFromBasket(getItem(position).book.book_id);
        removeItem(viewHolder);
        ((CheckBox) viewHolder.itemView.findViewById(R.id.checkBox)).setChecked(false);
        // notify the item removed by position
        // to perform recycler view delete animations
        // NOTE: don't call notifyDataSetChanged()
        if (getItemCount() == 0) {
            viewModel.setEmptyBasket();
        }
        notifyItemRemoved(position);
    }
}
