package izenka.hfad.com.bookstore.basket.presenter;


import android.view.View;
import android.widget.ImageView;

import com.google.firebase.storage.StorageReference;

import java.util.Set;

import izenka.hfad.com.bookstore.presenter.IPresenter;
import mehdi.sakout.fancybuttons.FancyButton;

public interface IBasketPresenter extends IPresenter {
    void onBackClicked();
    void onChooseEthClicked(FancyButton button);
    void onDeleteClicked(View view);
    void setTotalPrice();
    void onRegisterClicked(View view);
    void loadImage(StorageReference imageRef, ImageView imgBtnBook);
    void addBookView(View oneBookInBasketView);
    Set<View> getCheckedViewSet();
}
