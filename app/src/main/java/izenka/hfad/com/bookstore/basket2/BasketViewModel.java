package izenka.hfad.com.bookstore.basket2;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.paging.PagedList;

import java.util.List;
import java.util.concurrent.Executors;

import izenka.hfad.com.bookstore.DatabaseSingleton;
import izenka.hfad.com.bookstore.MainThreadExecutor;

public class BasketViewModel extends ViewModel {

    private MutableLiveData<PagedList<BookInBasketModel>> bookInBasketPagedListLiveData;

    private BasketNavigator navigator;

    public void setNavigator(BasketNavigator navigator) {
        this.navigator = navigator;
    }

    //TODO: List->PagedList
    public MutableLiveData<PagedList<BookInBasketModel>> getBookListLiveData() {
        if (bookInBasketPagedListLiveData == null) {
            bookInBasketPagedListLiveData = new MutableLiveData<>();

            DatabaseSingleton.getInstance().getUser(user -> {

                BooksInBasketDataSource dataSource = new BooksInBasketDataSource(user.Basket);

                PagedList.Config config = new PagedList.Config.Builder()
                        .setEnablePlaceholders(false)
                        .setInitialLoadSizeHint(6)
                        .setPageSize(3)
                        .build();

                PagedList<BookInBasketModel> bookList = new PagedList.Builder<>(dataSource, config)
                        .setFetchExecutor(Executors.newSingleThreadExecutor())
                        .setNotifyExecutor(new MainThreadExecutor())
                        .build();

                bookInBasketPagedListLiveData.postValue(bookList);
                navigator.loadingFinished();
            });
        }
        return bookInBasketPagedListLiveData;
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
//                bookInBasketPagedListLiveData.postValue(bookInBasketModelList);
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
