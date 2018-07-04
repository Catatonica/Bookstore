package izenka.hfad.com.bookstore.search2;

import android.arch.paging.PositionalDataSource;
import android.support.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import izenka.hfad.com.bookstore.model.db_classes.Book;



public class SearchedBookListDataSource extends PositionalDataSource<Book> {

   private String searchedText;

    SearchedBookListDataSource(String searchedText){
        this.searchedText = searchedText;
    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams params, @NonNull LoadInitialCallback<Book> callback) {

//        List<Book> bookList = new ArrayList<>();
//        DatabaseReference db = FirebaseDatabase.getInstance().getReference();
//        db.child("bookstore").child("book")
//          .limitToFirst(params.requestedLoadSize)
//          .orderByValue()
//          .startAt(params.requestedStartPosition).addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                for (DataSnapshot data : dataSnapshot.getChildren()) {
//                    Book book = data.getValue(Book.class);
//                    bookList.add(book);
//                }
//                callback.onResult(bookList, params.requestedStartPosition);
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
    }

    @Override
    public void loadRange(@NonNull LoadRangeParams params, @NonNull LoadRangeCallback<Book> callback) {
//        List<Book> bookList = new ArrayList<>();
//        DatabaseReference db = FirebaseDatabase.getInstance().getReference();
//        db.child("bookstore").child("book")
//          .limitToFirst(params.loadSize)
//          .orderByValue()
//          .startAt(params.startPosition).addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                for (DataSnapshot data : dataSnapshot.getChildren()) {
//                    Book book = data.getValue(Book.class);
//                    bookList.add(book);
//                }
//                callback.onResult(bookList);
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
    }
}
