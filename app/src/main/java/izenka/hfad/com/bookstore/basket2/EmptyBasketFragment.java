package izenka.hfad.com.bookstore.basket2;


import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import izenka.hfad.com.bookstore.R;

public class EmptyBasketFragment extends Fragment /*implements IBasketFragmentView */{
//    private IBasketPresenter presenter;

//    @Override
//    public void setPresenter(@NonNull IBasketPresenter presenter) {
//        this.presenter = presenter;
//    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_empty_basket, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        BasketViewModel viewModel = ViewModelProviders.of(getActivity()).get(BasketViewModel.class);
        view.findViewById(R.id.btnReturnFromBasket).setOnClickListener(btn->viewModel.onBackClicked());
    }
}
