package izenka.hfad.com.bookstore.view.basket;


import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.firebase.storage.StorageReference;

import java.util.Set;

import izenka.hfad.com.bookstore.view.IView;

public interface IBasketView extends IView {
    void addEmptyBasketView();
    void addFilledBasketView();
    void initFilledBasketViews();
    void initEmptyBasketViews();
    void onBackClick();
    void animate(View view);
    void removeBook(int id);
    void onDeleteClick();
    void showToast(String message, int duration);
    void queryBook(String bookID, Set<View> checkedViewSet);
    void setTotalPrise(String prise);
    void loadImage(StorageReference imageRef, ImageView imgBtnBook);
    void addBookView(View oneBookInBasketView);
}
