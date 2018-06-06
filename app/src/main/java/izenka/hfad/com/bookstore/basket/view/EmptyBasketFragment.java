package izenka.hfad.com.bookstore.basket.view;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import izenka.hfad.com.bookstore.R;
import izenka.hfad.com.bookstore.basket.presenter.IBasketPresenter;

public class EmptyBasketFragment extends Fragment implements IBasketFragmentView {
    private IBasketPresenter presenter;

    @Override
    public void setPresenter(@NonNull IBasketPresenter presenter) {
        this.presenter = presenter;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.empty_basket, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.findViewById(R.id.btnReturnFromBasket).setOnClickListener(view1 -> presenter.onBackClicked());
    }
}
