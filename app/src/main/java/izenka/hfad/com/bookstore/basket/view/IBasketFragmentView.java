package izenka.hfad.com.bookstore.basket.view;


import android.support.annotation.NonNull;

import izenka.hfad.com.bookstore.basket.presenter.IBasketPresenter;

public interface IBasketFragmentView {
    void setPresenter(@NonNull IBasketPresenter presenter);
}
