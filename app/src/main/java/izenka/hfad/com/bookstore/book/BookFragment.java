package izenka.hfad.com.bookstore.book;


import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import izenka.hfad.com.bookstore.R;
import izenka.hfad.com.bookstore.model.db_classes.Author;
import izenka.hfad.com.bookstore.model.db_classes.Book;
import mehdi.sakout.fancybuttons.FancyButton;

public class BookFragment extends Fragment {

    private BookViewModel viewModel;

    private TextView tvTitle;
    private TextView tvYear;
    private TextView tvAvailability;
    private TextView tvPrise;
    private TextView tvAuthor;
    private TextView tvPublisher;
    private TextView tvDescription;
    private mehdi.sakout.fancybuttons.FancyButton btnPutInBasket;
    private ImageView ivBookImage;
    private ImageButton ibExpand;
    private FancyButton btnNotify;
    private TextView tvCount;
    private TextView tvPages;
    private TextView tvCover;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_book, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
    }

    private void initViews(View view) {
        btnPutInBasket = view.findViewById(R.id.putInBasket);
        btnPutInBasket.setIconResource(R.drawable.ic_shopping_basket_black_24dp);
        btnPutInBasket.setIconPosition(FancyButton.POSITION_LEFT);
        btnPutInBasket.getIconImageObject().setLayoutParams(new LinearLayout.LayoutParams(60, 60));
        btnNotify = view.findViewById(R.id.btnNotify);
        tvTitle = view.findViewById(R.id.tvTitle);
        tvYear = view.findViewById(R.id.tvYear);
        tvAvailability = view.findViewById(R.id.tvAvailability);
        tvPrise = view.findViewById(R.id.tvPrise);
        tvCount = view.findViewById(R.id.tvCount);
        tvPages = view.findViewById(R.id.tvPages);
        tvCover = view.findViewById(R.id.tvCover);
        tvAuthor = view.findViewById(R.id.tvAuthor);
        ivBookImage = view.findViewById(R.id.ivBookImage);
        tvPublisher = view.findViewById(R.id.tvPublisher);
        ibExpand = view.findViewById(R.id.ibExpand);
        tvDescription = view.findViewById(R.id.tvDescription);

        final boolean[] isExpanded = {false};
        ibExpand.setOnClickListener(btn -> {
            if (!isExpanded[0]) {
                tvDescription.setMaxLines(30);
                ibExpand.setBackground(view.getContext().getDrawable(R.drawable.narrow));
                isExpanded[0] = true;
            } else {
                tvDescription.setMaxLines(5);
                ibExpand.setBackground(view.getContext().getDrawable(R.drawable.expand));
                isExpanded[0] = true;
            }
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = ViewModelProviders.of(requireActivity()).get(BookViewModel.class);
        viewModel.getBookLiveData().observe(this, book -> {
            if (book != null) {
                setBookInfo(book);
            }
        });
        btnNotify.setOnClickListener(btn -> {
            viewModel.notifyOfBookAppearance();
        });
        btnPutInBasket.setOnClickListener(btn -> {
            viewModel.onPutInBasketClicked();
        });
    }

    private void setBookInfo(Book book) {
        tvCount.setText(String.valueOf(book.count));
        tvPages.setText(String.valueOf(book.pages_number));
        tvCover.setText(book.cover);
        tvTitle.setText(String.format("\"%s\"", book.title));
        tvYear.setText(String.valueOf(book.publication_year));
        tvPrise.setText(book.price);
        if (book.count != 0) {
            //TODO: notificate user that book is available
            tvAvailability.setText("в наличии");
            btnPutInBasket.setVisibility(View.VISIBLE);
            btnNotify.setVisibility(View.GONE);
        } else {
            tvAvailability.setText("нет в наличии");
            btnPutInBasket.setVisibility(View.GONE);
            btnNotify.setVisibility(View.VISIBLE);
        }
        tvDescription.setText(book.description);

        viewModel.getPublisherLiveData(String.valueOf(book.book_publisher_id)).observe(this, publisher -> {
            if (publisher != null) {
                tvPublisher.setText(publisher.publisher_name);
            }
        });

        viewModel.getAuthorListLiveData(book.authorsIDs).observe(this, authorList -> {
            if (authorList != null) {
                StringBuilder authorsStringBuilder = new StringBuilder();
                for (Author author : authorList) {
                    authorsStringBuilder.append(author.author_surname)
                                        .append(" ")
                                        .append(author.author_name.substring(0, 1))
                                        .append("., ")
                                        .append('\n');
                }
                authorsStringBuilder.delete(authorsStringBuilder.length() - 3, authorsStringBuilder.length());
                tvAuthor.setText(authorsStringBuilder);
            }
        });
        setImage(book);
    }

    private void setImage(Book book) {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        String bookImage = book.imagesPaths.get(0);
        StorageReference imageRef = storage.getReference().child(bookImage);

        Glide.with(this)
             .using(new FirebaseImageLoader())
             .load(imageRef)
             .into(ivBookImage);
    }
}
