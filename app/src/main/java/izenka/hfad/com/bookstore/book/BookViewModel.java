package izenka.hfad.com.bookstore.book;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.os.Build;
import android.support.v4.app.NotificationCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import izenka.hfad.com.bookstore.R;
import izenka.hfad.com.bookstore.model.db_classes.Author;
import izenka.hfad.com.bookstore.model.db_classes.Book;

public class BookViewModel extends ViewModel {

    private int bookID;
    private MutableLiveData<Book> bookLiveData;
    private BookNavigator navigator;

    public void setBookID(int bookID){
        this.bookID = bookID;
    }
    public void setNavigator(BookNavigator navigator) {
        this.navigator = navigator;
    }

    public void setBookLiveData() {
        FirebaseDatabase.getInstance()
                        .getReference("bookstore/")
                        .addValueEventListener(new ValueEventListener() { // TODO: or SingleEventListener?
                            @Override
                            public void onDataChange(DataSnapshot data) {
//                                    final Book book = data.getChildren().iterator().next().getValue(Book.class);
                                Book book = data.child("book/" + bookID).getValue(Book.class);

                                List<String> imagePathList = new ArrayList<>();
                                for (DataSnapshot imagesID : data.child("book/" + bookID + "/Images").getChildren()) {
                                    imagePathList.add(imagesID.getValue().toString());
                                }
                                book.imagesPaths = imagePathList;

                                List<Integer> authorsIDs = new ArrayList<>();
                                for (DataSnapshot authID : data.child("book/" + bookID + "/Authors").getChildren()) {
                                    authorsIDs.add(Integer.parseInt(String.valueOf(authID.getValue())));
                                }

                                book.authors = new ArrayList<>();
                                for (int authorID : authorsIDs) {
                                    book.authors.add(data.child("author/" + authorID).getValue(Author.class));
                                }

                                bookLiveData.postValue(book);
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
    }

    public MutableLiveData<Book> getBookLiveData() {
        if (bookLiveData == null) {
            bookLiveData = new MutableLiveData<>();
            setBookLiveData();
        }
        return bookLiveData;
    }

    public void onPutInBasketClicked() {
        String userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        database.child("bookstore/users/" + userID).child("Basket").child(String.valueOf(bookID)).setValue(bookID);
//        database.child("bookstore/users/" + userID).child("Basket").child(String.valueOf(bookID)).child("count").setValue(1);
        navigator.onPutInBasketClicked();
    }

    public void notifyOfBookAppearance() {
        DatabaseReference database = FirebaseDatabase.getInstance().getReference("/bookstore");
        database.child("book").child(String.valueOf(bookID)).child("count").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                long count = (long) dataSnapshot.getValue();
                if(count>0){
                    navigator.notifyUser(bookLiveData.getValue().title);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

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
