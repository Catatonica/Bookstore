package izenka.hfad.com.bookstore.basket2;


import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import izenka.hfad.com.bookstore.R;
import mehdi.sakout.fancybuttons.FancyButton;

public class BooksInBasketFragment extends Fragment implements ButtonsClickListener, RecyclerItemTouchHelperListener {
//    private IBasketPresenter presenter;

    //    private LinearLayout llBooks;
//    private LinearLayout llAction;
//    private TextView tvTotalPrise;
    private BasketViewModel viewModel;

    private RecyclerView rvBookList;
    private FancyButton btnRegister;
    private FancyButton btnDelete;
    private FancyButton btnChooseEth;
    private TextView tvTotalPrise;

    private boolean checkAll = false;
    private List<BookIdAndCountModel> bookInBasketModelList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_filled_basket, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rvBookList = view.findViewById(R.id.rvBookList);
        LinearLayoutManager manager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        rvBookList.setLayoutManager(manager);

        ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new RecyclerItemTouchHelper(0, ItemTouchHelper.LEFT, this);
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(rvBookList);

        btnChooseEth = view.findViewById(R.id.btnChooseEth);
        btnChooseEth.setOnClickListener(btn -> {
            if (checkAll) {
                ((FancyButton) btn).setText(getString(R.string.checkAll));
                checkAll = false;
            } else {
                ((FancyButton) btn).setText(getString(R.string.uncheckAll));
                checkAll = true;
            }
        });
//        btnDelete = view.findViewById(R.id.btnDelete);
        btnRegister = view.findViewById(R.id.btnRegister);
        btnRegister.setEnabled(false);

//        llBooks = (LinearLayout) view.findViewById(R.id.llBooks);
//        llAction = view.findViewById(R.id.llAction);
        tvTotalPrise = view.findViewById(R.id.tvTotalPriceForAll);
//        fillTheBasket();
    }

//    private void fillTheBasket() {
//////        if (!MainMenuActivity.stringSet.isEmpty()) {
////        for (final String bookID : MainMenuActivity.stringSet) {
////            ReadBooksForBasket.queryBook(getActivity(), bookID, presenter.getCheckedViewSet(), presenter);
////        }
//////        } else {
//////            basketView.addEmptyBasketView();
//////        }
//    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        BooksInBasketAdapter adapter = new BooksInBasketAdapter(DIFF_CALLBACK);
        viewModel = ViewModelProviders.of(requireActivity()).get(BasketViewModel.class);
        viewModel.setButtonsClickListener(this);
        adapter.setViewModel(viewModel);
        //TODO: книги должны динамически изменяться
        viewModel.getBookListLiveData().observe(this, adapter::submitList);
        rvBookList.setAdapter(adapter);
        btnChooseEth.setOnClickListener(btn -> {
            adapter.checkAll();
        });
        btnRegister.setOnClickListener(btn -> {
            viewModel.onRegisterClicked(bookInBasketModelList, Float.valueOf(tvTotalPrise.getText().toString()));
        });
    }

    @Override
    public void enableButtonRegister(boolean enable) {
        if (btnRegister.isEnabled() != enable) {
            btnRegister.setEnabled(enable);
        }
    }

    @Override
    public void addBookInBasketModel(BookIdAndCountModel bookIdAndCountModel) {
        bookInBasketModelList.add(bookIdAndCountModel);
    }

    @Override
    public void removeBookInBasketModel(BookIdAndCountModel bookIdAndCountModel) {
        bookInBasketModelList.remove(bookIdAndCountModel);
    }

    @Override
    public void addToTotalPrice(float value) {
        float oldPrice = Float.valueOf(tvTotalPrise.getText().toString());
        float newPrice = oldPrice + value;
        tvTotalPrise.setText(String.valueOf(newPrice));
    }

    @Override
    public void subtractFromTotalPrice(float value) {
        float oldPrice = Float.valueOf(tvTotalPrise.getText().toString());
        float newPrice = oldPrice - value;
        tvTotalPrise.setText(String.valueOf(newPrice));
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {
        if (viewHolder instanceof BooksInBasketAdapter.BookViewHolder) {
//            // get the removed item name to display it in snack bar
//            String name = bookInBasketModelList.get(viewHolder.getAdapterPosition()).bogetName();
//
//            // backup of removed item for undo purpose
//            final BookIdAndCountModel deletedItem = bookInBasketModelList.get(viewHolder.getAdapterPosition());
//            final int deletedIndex = viewHolder.getAdapterPosition();

            // remove the item from recycler view
            ((BooksInBasketAdapter) rvBookList.getAdapter()).removeItem(viewHolder);

//            // showing snack bar with Undo option
//            Snackbar snackbar = Snackbar.make(coordinatorLayout, "Item removed from cart!", Snackbar.LENGTH_LONG);
//            snackbar.setAction("UNDO", new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    // undo is selected, restore the deleted item
//                    ((BooksInBasketAdapter)rvBookList.getAdapter()).restoreItem(deletedItem, deletedIndex);
//                }
//            });
//            snackbar.setActionTextColor(Color.YELLOW);
//            snackbar.show();
        }
    }


//    @Override
//    public void addBookView(View oneBookInBasketView) {
//        llBooks.addView(oneBookInBasketView);
//    }
//
//    @Override
//    public void removeBook(int id) {
//        llBooks.post(new Runnable() {
//            public void run() {
//                llBooks.removeView(llBooks.findViewById(id));
//            }
//        });
//    }
//
//    @Override
//    public void onDeleteClick() {
//        llAction.setVisibility(View.INVISIBLE);
//
////        if (MainMenuActivity.stringSet.isEmpty()) {
////            emptyBasket = getLayoutInflater().inflate(R.layout.empty_basket, null);
////            activityBasket.removeView(filledBasket);
////            activityBasket.addView(emptyBasket);
//////            initEmptyBasketViews();
////            // setContentView(R.layout.empty_basket);
////        }
//    }
//
//    @Override
//    public void setTotalPrise(String prise) {
//        tvTotalPrise.setText(prise);
//    }
//
//    @Override
//    public void loadImage(StorageReference imageRef, ImageView imgBtnBook) {
//        Glide.with(this /* context */)
//             .using(new FirebaseImageLoader())
//             .load(imageRef)
//             .into(imgBtnBook);
//    }

    public static final DiffUtil.ItemCallback<BookInBasketModel> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<BookInBasketModel>() {
                @Override
                public boolean areItemsTheSame(
                        @NonNull BookInBasketModel oldBook, @NonNull BookInBasketModel newBook) {
                    return oldBook.book.book_id == newBook.book.book_id;
                }

                @Override
                public boolean areContentsTheSame(
                        @NonNull BookInBasketModel oldBook, @NonNull BookInBasketModel newBook) {
                    return oldBook.book.equals(newBook.book);
                }
            };
}
