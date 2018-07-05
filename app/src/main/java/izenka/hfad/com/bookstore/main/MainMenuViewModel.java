package izenka.hfad.com.bookstore.main;

import android.arch.lifecycle.ViewModel;


public class MainMenuViewModel extends ViewModel {

    private MainMenuNavigator navigator;

    public void setNavigator(MainMenuNavigator navigator) {
        this.navigator = navigator;
    }

    void onCategoryClicked(int categoryID) {
        navigator.onCategoryClicked(categoryID);
    }

    void onSearchClicked() {
        navigator.onSearchClicked();
    }
}
