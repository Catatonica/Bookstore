package izenka.hfad.com.bookstore.basket2;


import android.support.v4.app.Fragment;

import java.util.List;

public interface BasketNavigator {
    void setFragment(Fragment fragment);

    void onRegisterClicked(List<BookIdAndCountModel> BookIdAndCountModelList, float totalPrice);

    void onBackClicked();

    void loadingFinished();
}
