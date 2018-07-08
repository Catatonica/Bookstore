package izenka.hfad.com.bookstore.basket;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import java.util.List;

import izenka.hfad.com.bookstore.DatabaseSingleton;

public class BasketViewModel extends ViewModel {

    private MutableLiveData<List<BookInBasketModel>> bookInBasketLiveData;
    private BasketNavigator navigator;
    private ButtonsClickListener buttonsClickListener;

    public void setNavigator(BasketNavigator navigator) {
        this.navigator = navigator;
    }

    MutableLiveData<List<BookInBasketModel>> getBookListLiveData() {
        if (bookInBasketLiveData == null) {
            bookInBasketLiveData = new MutableLiveData<>();
            DatabaseSingleton.getInstance().getUser(user -> {
                if (user.Basket.isEmpty()) {
                    bookInBasketLiveData.postValue(null);
                } else {
                    DatabaseSingleton.getInstance().getBooksFromBasket(user.Basket, bookList -> {
                        bookInBasketLiveData.postValue(bookList);
                    });
                }
            });
        }
        return bookInBasketLiveData;
    }

    void setBookInBasketLiveData(){
        DatabaseSingleton.getInstance().getUser(user -> {
            if (user.Basket.isEmpty()) {
                bookInBasketLiveData.postValue(null);
            } else {
                DatabaseSingleton.getInstance().getBooksFromBasket(user.Basket, bookList -> {
                    bookInBasketLiveData.postValue(bookList);
                });
            }
        });
    }

    void setButtonsClickListener(ButtonsClickListener buttonsClickListener) {
        this.buttonsClickListener = buttonsClickListener;
    }

    void enableButtonRegister(boolean enable) {
        buttonsClickListener.enableButtonRegister(enable);
    }

    void onRegisterClicked(List<BookIdAndCountModel> BookIdAndCountModelList, float totalPrice) {
        navigator.onRegisterClicked(BookIdAndCountModelList, totalPrice);
    }

    void addBookInBasketModel(BookIdAndCountModel bookIdAndCountModel) {
        buttonsClickListener.addBookInBasketModel(bookIdAndCountModel);
    }

    void removeBookInBasketModel(BookIdAndCountModel bookIdAndCountModel) {
        buttonsClickListener.removeBookInBasketModel(bookIdAndCountModel);
    }

    void deleteBookFromBasket(String bookID) {
        DatabaseSingleton.getInstance().deleteBookFromBasket(bookID);
    }

    void addToTotalPrice(float value) {
        buttonsClickListener.addToTotalPrice(value);
    }

    void subtractFromTotalPrice(float value) {
        buttonsClickListener.subtractFromTotalPrice(value);
    }

    void setEmptyBasket() {
        navigator.setFragment(new EmptyBasketFragment());
    }

    public void onBackClicked() {
        navigator.onBackClicked();
    }
}
