package izenka.hfad.com.bookstore.basket;


import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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

public class BookInBasketListFragment extends Fragment implements ButtonsClickListener, RecyclerItemTouchHelperListener {

    private BasketViewModel viewModel;

    private RecyclerView rvBookList;
    private FancyButton btnRegister;
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
        btnRegister = view.findViewById(R.id.btnRegister);
        btnRegister.setEnabled(false);

        tvTotalPrise = view.findViewById(R.id.tvTotalPriceForAll);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = ViewModelProviders.of(requireActivity()).get(BasketViewModel.class);
        List<BookInBasketModel> bookList = new ArrayList<>();
        BookInBasketListAdapter adapter = new BookInBasketListAdapter(viewModel, bookList);
        viewModel.setButtonsClickListener(this);
        viewModel.getBookListLiveData().observe(this, adapter::setList);
        rvBookList.setAdapter(adapter);
        btnChooseEth.setOnClickListener(btn -> {
            ((BookInBasketListAdapter) rvBookList.getAdapter()).checkAll();
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
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
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
        if (viewHolder instanceof BookInBasketListAdapter.BookViewHolder) {
            ((BookInBasketListAdapter) rvBookList.getAdapter()).removeItem(viewHolder);
        }
    }

}
