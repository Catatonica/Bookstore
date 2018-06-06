package izenka.hfad.com.bookstore.main_menu.presenter;


import android.content.Intent;
import android.support.v4.app.Fragment;
import android.view.MenuItem;
import android.view.View;

import izenka.hfad.com.bookstore.basket.view.BasketActivity;
import izenka.hfad.com.bookstore.main_menu.view.IMainMenuFragmentView;
import izenka.hfad.com.bookstore.main_menu.view.MainMenuFragment;
import izenka.hfad.com.bookstore.view.book_list.BookListActivity;
import izenka.hfad.com.bookstore.model.FirebaseManager;
import izenka.hfad.com.bookstore.main_menu.view.IMainMenuView;
import izenka.hfad.com.bookstore.view.qr_code.QRCodeActivity;
import izenka.hfad.com.bookstore.R;
import izenka.hfad.com.bookstore.view.search.SearchActivity;
import izenka.hfad.com.bookstore.view.orders.OrdersActivity;

public class MainMenuPresenterImpl implements IMainMenuPresenter {

    private IMainMenuView menuView;
    //IFirebaseModel
    private FirebaseManager firebaseManager;

    public MainMenuPresenterImpl(IMainMenuView view) {
        this.menuView = view;
        firebaseManager = new FirebaseManager();
    }

    @Override
    public void onViewCreated() {
        menuView.initViews();
        //TODO: uncomment
//        menuView.showCategoriesNames();
        menuView.setToolbar();
        IMainMenuFragmentView fragment = new MainMenuFragment();
        fragment.setPresenter(this);
        menuView.setFragment((Fragment) fragment);
        firebaseManager.connectToFirebase();
    }

    @Override
    public void onCategoryClicked(View view) {
        int extra = 0;
        switch (view.getId()) {
            case R.id.btnForeign:
                extra = 0;
                break;
            case R.id.btnKid:
                extra = 1;
                break;
            case R.id.btnBusiness:
                extra = 2;
                break;
            case R.id.btnFiction:
                extra = 3;
                break;
            case R.id.btnStudy:
                extra = 4;
                break;
            case R.id.btnNonfiction:
                extra = 5;
                break;
        }
        Intent intent = new Intent();
        intent.putExtra("categoryID", extra);
        menuView.startActivityWithAnimation(view, intent, BookListActivity.class);
    }

    @Override
    public void onBasketClicked() {
        Intent intent = new Intent();
        menuView.startActivity(intent, BasketActivity.class);
    }

    @Override
    public void onSearchClicked() {
        Intent intent = new Intent();
        intent.putExtra("categoryID", -1);
        menuView.startActivity(intent, SearchActivity.class);
    }

    @Override
    public void onOrdersClicked() {
        Intent intent = new Intent();
        menuView.startActivity(intent, OrdersActivity.class);
    }

    @Override
    public void onScanClicked() {
        Intent intent = new Intent();
        menuView.startActivity(intent, QRCodeActivity.class);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem menuItem) {
        return menuView.onNavigationItemSelect(menuItem);
    }

    @Override
    public void showCategoriesNames() {
        menuView.showCategoriesNames();
    }
}
