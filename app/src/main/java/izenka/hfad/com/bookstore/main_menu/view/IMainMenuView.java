package izenka.hfad.com.bookstore.main_menu.view;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.view.MenuItem;

import izenka.hfad.com.bookstore.view.IView;

public interface IMainMenuView extends IView {
    void showCategoriesNames();

    boolean onNavigationItemSelect(MenuItem menuItem);

    void setFragment(Fragment fragment);

    void setToolbar();
}
