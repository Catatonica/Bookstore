package izenka.hfad.com.bookstore.presenter;


<<<<<<< HEAD
import android.content.Intent;
import android.view.View;

import izenka.hfad.com.bookstore.view.basket.BasketActivity;
import izenka.hfad.com.bookstore.view.book_list.BookListActivity;
import izenka.hfad.com.bookstore.model.FirebaseManager;
=======
import android.view.View;

import izenka.hfad.com.bookstore.BasketActivity;
import izenka.hfad.com.bookstore.view.book_list.BookListActivity;
import izenka.hfad.com.bookstore.FirebaseManager;
>>>>>>> 713af467c300756a5d42d3eca411d50cbcc356d3
import izenka.hfad.com.bookstore.view.main_menu.IMainMenuView;
import izenka.hfad.com.bookstore.view.qr_code.QRCodeActivity;
import izenka.hfad.com.bookstore.R;
import izenka.hfad.com.bookstore.view.search.SearchActivity;
<<<<<<< HEAD
import izenka.hfad.com.bookstore.view.orders.OrdersActivity;

public class MainMenuPresenter implements IPresenter {
=======
import izenka.hfad.com.bookstore.controller.order.OrdersActivity;

public class MainMenuPresenter {
>>>>>>> 713af467c300756a5d42d3eca411d50cbcc356d3

    private IMainMenuView menuView;
    //IFirebaseModel
    private FirebaseManager firebaseManager;

    public MainMenuPresenter(IMainMenuView view) {
        this.menuView = view;
        firebaseManager = new FirebaseManager();
    }

<<<<<<< HEAD
    @Override
=======
>>>>>>> 713af467c300756a5d42d3eca411d50cbcc356d3
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
<<<<<<< HEAD
        Intent intent = new Intent();
        intent.putExtra("categoryID", extra);
        menuView.startActivityWithAnimation(view, intent, BookListActivity.class);
    }

    public void onBasketClicked(View view) {
        menuView.startActivityWithAnimation(view, BasketActivity.class);
    }

    public void onSearchClicked() {
        Intent intent = new Intent();
        intent.putExtra("categoryID", -1);
        menuView.startActivity(intent, SearchActivity.class);
    }

    public void onOrdersClicked(View view) {
        menuView.startActivityWithAnimation(view, OrdersActivity.class);
    }

    public void onScanClicked(View view) {
        menuView.startActivityWithAnimation(view, QRCodeActivity.class);
=======
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
>>>>>>> 713af467c300756a5d42d3eca411d50cbcc356d3
    }
}
