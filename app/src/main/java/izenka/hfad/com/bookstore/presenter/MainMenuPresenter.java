package izenka.hfad.com.bookstore.presenter;


import android.view.View;

import izenka.hfad.com.bookstore.BasketActivity;
import izenka.hfad.com.bookstore.view.book_list.BookListActivity;
import izenka.hfad.com.bookstore.FirebaseManager;
import izenka.hfad.com.bookstore.view.main_menu.IMainMenuView;
import izenka.hfad.com.bookstore.view.qr_code.QRCodeActivity;
import izenka.hfad.com.bookstore.R;
import izenka.hfad.com.bookstore.view.search.SearchActivity;
import izenka.hfad.com.bookstore.controller.order.OrdersActivity;

public class MainMenuPresenter {

    private IMainMenuView menuView;
    //IFirebaseModel
    private FirebaseManager firebaseManager;

    public MainMenuPresenter(IMainMenuView view) {
        this.menuView = view;
        firebaseManager = new FirebaseManager();
    }

    public void onViewCreated() {
        menuView.initViews();
        menuView.showCategoriesNames();
        firebaseManager.connectToFirebase();
    }

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
        menuView.startActivity(view, BookListActivity.class, "categoryID", extra);
    }

    public void onBasketClicked(View view) {
        menuView.startActivity(view, BasketActivity.class);
    }

    public void onSearchClicked() {
        menuView.startActivity(SearchActivity.class, "categoryID", -1);
    }

    public void onOrdersClicked(View view) {
        menuView.startActivity(view, OrdersActivity.class);
    }

    public void onScanClicked(View view) {
        menuView.startActivity(view, QRCodeActivity.class);
    }
}
