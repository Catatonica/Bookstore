package izenka.hfad.com.bookstore.basket;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.paging.PagedList;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import izenka.hfad.com.bookstore.DatabaseSingleton;
import izenka.hfad.com.bookstore.model.db_classes.Author;
import izenka.hfad.com.bookstore.model.db_classes.Book;

public class BasketViewModel extends ViewModel {

    private MutableLiveData<List<BookInBasketModel>> bookInBasketLiveData;

    private BasketNavigator navigator;

    public void setNavigator(BasketNavigator navigator) {
        this.navigator = navigator;
    }

    public MutableLiveData<List<BookInBasketModel>> getBookListLiveData() {
        if (bookInBasketLiveData == null) {
            bookInBasketLiveData = new MutableLiveData<>();
            DatabaseSingleton.getInstance().getUser(user -> {
                if(user.Basket.isEmpty()){
                    bookInBasketLiveData.postValue(null);
                } else{
                    DatabaseSingleton.getInstance().getBooksFromBasket(user.Basket, bookList -> {
                        bookInBasketLiveData.postValue(bookList);
                    });
                }
            });
        }
        return bookInBasketLiveData;
    }

//    private void loadBookList() {
//        DatabaseReference database = FirebaseDatabase.getInstance().getReference();
//        String userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
//        database.child("bookstore").addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                List<BookInBasketModel> bookInBasketModelList = new ArrayList<>();
//                DataSnapshot basketDataSnapshot = dataSnapshot.child("users").child(userID).child("Basket");
//                for (DataSnapshot data : basketDataSnapshot.getChildren()) {
//                    BookInBasketModel bookInBasketModel = new BookInBasketModel();
//                    String bookID = data.getKey();
//                    Integer booksCount = Integer.valueOf(data.getValue().toString());
//
//                    Book book = dataSnapshot.child("book").child(bookID).getValue(Book.class);
//                    book.imagesPaths = new ArrayList<>();
//                    for (DataSnapshot imagesID : dataSnapshot.child("book").child(bookID).child("Images").getChildren()) {
//                        book.imagesPaths.add(imagesID.getValue().toString());
//                    }
//                    final List<Integer> authorsIDs = new ArrayList<>();
//                    for (DataSnapshot authID : dataSnapshot.child("book").child(bookID).child("Authors").getChildren()) {
//                        authorsIDs.add(Integer.parseInt(String.valueOf(authID.getValue())));
//                    }
//                    book.authors = new ArrayList<>();
//                    for (int authorID : authorsIDs) {
//                        book.authors.add(dataSnapshot.child("author/" + authorID).getValue(Author.class));
//                    }
//
//                    bookInBasketModel.count = booksCount;
//                    bookInBasketModel.book = book;
//                    bookInBasketModelList.add(bookInBasketModel);
//                }
//                bookInBasketLiveData.postValue(bookInBasketModelList);
//                navigator.loadingFinished();
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
//    }

    private ButtonsClickListener buttonsClickListener;

    public void setButtonsClickListener(ButtonsClickListener buttonsClickListener) {
        this.buttonsClickListener = buttonsClickListener;
    }

    public void enableButtonRegister(boolean enable) {
        buttonsClickListener.enableButtonRegister(enable);
    }

    public void onRegisterClicked(List<BookIdAndCountModel> BookIdAndCountModelList, float totalPrice) {
        navigator.onRegisterClicked(BookIdAndCountModelList, totalPrice);
    }

    public void addBookInBasketModel(BookIdAndCountModel bookIdAndCountModel) {
        buttonsClickListener.addBookInBasketModel(bookIdAndCountModel);
    }

    public void removeBookInBasketModel(BookIdAndCountModel bookIdAndCountModel) {
        buttonsClickListener.removeBookInBasketModel(bookIdAndCountModel);
    }

    public void deleteBookFromBasket(int bookID) {
        DatabaseSingleton.getInstance().deleteBookFromBasket(String.valueOf(bookID));
    }

    public void addToTotalPrice(float value){
        buttonsClickListener.addToTotalPrice(value);
    }

    public void subtractFromTotalPrice( float value) {
        buttonsClickListener.subtractFromTotalPrice(value);
    }

    public void setEmptyBasket(){
        navigator.setFragment(new EmptyBasketFragment());
    }

    public void onBackClicked() {
        navigator.onBackClicked();
    }
}
