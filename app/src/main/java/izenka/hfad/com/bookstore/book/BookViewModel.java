package izenka.hfad.com.bookstore.book;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import java.util.List;

import izenka.hfad.com.bookstore.DatabaseSingleton;
import izenka.hfad.com.bookstore.model.db_classes.Author;
import izenka.hfad.com.bookstore.model.db_classes.Book;
import izenka.hfad.com.bookstore.model.db_classes.Publisher;

public class BookViewModel extends ViewModel {

    private int bookID;

    private MutableLiveData<Book> bookLiveData;
    private MutableLiveData<List<Author>> authorListLiveData;
    private MutableLiveData<Publisher> publisherLiveData;

    private BookNavigator navigator;

    public void setBookID(int bookID) {
        this.bookID = bookID;
    }

    public void setNavigator(BookNavigator navigator) {
        this.navigator = navigator;
    }

    MutableLiveData<Book> getBookLiveData() {
        if (bookLiveData == null) {
            bookLiveData = new MutableLiveData<>();
            DatabaseSingleton.getInstance().getBook(String.valueOf(bookID),
                                                    book -> bookLiveData.postValue(book));
        }
        return bookLiveData;
    }

    MutableLiveData<List<Author>> getAuthorListLiveData(List<String> authorIDs) {
        if (authorListLiveData == null) {
            authorListLiveData = new MutableLiveData<>();
            DatabaseSingleton.getInstance().getAuthorList(authorIDs, authorList -> {
                authorListLiveData.postValue(authorList);
            });
        }
        return authorListLiveData;
    }

    MutableLiveData<Publisher> getPublisherLiveData(String publisherID) {
        if (publisherLiveData == null) {
            publisherLiveData = new MutableLiveData<>();
            DatabaseSingleton.getInstance().getBookPublisher(publisherID, publisher -> {
                publisherLiveData.postValue(publisher);
            });
        }
        return publisherLiveData;
    }

    void onPutInBasketClicked() {
        DatabaseSingleton.getInstance().addBookToUserBasket(bookID);
        navigator.onPutInBasketClicked();
    }

    void notifyOfBookAppearance() {
        DatabaseSingleton.getInstance().getBookCount(String.valueOf(bookID), count -> {
            if (count > 0 && bookLiveData.getValue() != null) {
                navigator.notifyUser(bookLiveData.getValue().getTitle());
            }
        });
    }


//    private void createNotificationChannel() {
//        // Create the NotificationChannel, but only on API 26+ because
//        // the NotificationChannel class is new and not in the support library
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            CharSequence name = getString(R.string.channel_name);
//            String description = getString(R.string.channel_description);
//            int importance = NotificationManager.IMPORTANCE_DEFAULT;
//            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
//            channel.setDescription(description);
//            // Register the channel with the system; you can't change the importance
//            // or other notification behaviors after this
//            NotificationManager notificationManager = getSystemService(NotificationManager.class);
//            notificationManager.createNotificationChannel(channel);
//        }
//    }

}
