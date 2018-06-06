package izenka.hfad.com.bookstore.main_menu.presenter;


import android.view.MenuItem;
import android.view.View;

import izenka.hfad.com.bookstore.presenter.IPresenter;

public interface IMainMenuPresenter extends IPresenter {
    void onCategoryClicked(View view);
    void onBasketClicked();
    void onSearchClicked();
    void onOrdersClicked();
    void onScanClicked();
    boolean onNavigationItemSelected(MenuItem menuItem);
    void showCategoriesNames();
}
