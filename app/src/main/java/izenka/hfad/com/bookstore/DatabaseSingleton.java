package izenka.hfad.com.bookstore;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import izenka.hfad.com.bookstore.account.User;
import izenka.hfad.com.bookstore.basket.BookInBasketModel;
import izenka.hfad.com.bookstore.callbacks.AuthorListCallback;
import izenka.hfad.com.bookstore.callbacks.BookCallback;
import izenka.hfad.com.bookstore.callbacks.BookCountCallback;
import izenka.hfad.com.bookstore.callbacks.BooksFromBasketCallback;
import izenka.hfad.com.bookstore.callbacks.CategorizedBooksCallback;
import izenka.hfad.com.bookstore.callbacks.DatabaseCallback;
import izenka.hfad.com.bookstore.callbacks.UserCallback;
import izenka.hfad.com.bookstore.model.db_classes.Author;
import izenka.hfad.com.bookstore.model.db_classes.Book;
import izenka.hfad.com.bookstore.model.db_classes.Publisher;
import izenka.hfad.com.bookstore.order_registration.OrderRegistrationModel;
import izenka.hfad.com.bookstore.orders.BookInOrderModel;
import izenka.hfad.com.bookstore.stores_map.StoreModel;


public class DatabaseSingleton {

    private static DatabaseSingleton singleton = new DatabaseSingleton();

//    private static DatabaseReference database = FirebaseDatabase.getInstance().getReference("bookstore");

    //    public static FirebaseAuth auth = FirebaseAuth.getInstance();
//    public static FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    private DatabaseSingleton() {

    }

    public static DatabaseSingleton getInstance() {
        return singleton;
    }

//    public void enabledPersistence() {
//        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
//    }


    public void getCategorizedPagedBookList(int categoryID, int startPosition, int loadSize, CategorizedBooksCallback callback) {
        FirebaseDatabase
                .getInstance()
                .getReference("bookstore")
                .child("book")
                .orderByChild("Categories/" + categoryID)
                .equalTo(true)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        List<Book> bookList = new ArrayList<>();
                        List<DataSnapshot> bookSnapshotsList = new ArrayList<>();
                        for (DataSnapshot bookData : dataSnapshot.getChildren()) {
                            bookSnapshotsList.add(bookData);
                        }
                        int rightLoadSize;
                        if (bookSnapshotsList.size() - startPosition < loadSize) {
                            rightLoadSize = bookSnapshotsList.size() - startPosition;
                        } else {
                            rightLoadSize = loadSize;
                        }

                        for (DataSnapshot bookData : bookSnapshotsList.subList(startPosition, startPosition + rightLoadSize)) {
                            Book book = bookData.getValue(Book.class);
                            for (DataSnapshot imagesID : bookData.child("Images").getChildren()) {
                                book.imagesPaths.add(imagesID.getValue().toString());
                            }
                            for (DataSnapshot authID : bookData.child("Authors").getChildren()) {
                                book.authorsIDs.add(String.valueOf(authID.getKey()));
                            }
                            bookList.add(book);
                        }
                        callback.onBooksLoaded(bookList);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
    }

    public void getAuthorList(List<String> authorsIDs, AuthorListCallback callback) {
        List<Author> authorList = new ArrayList<>();
        for (String authorID : authorsIDs) {
            FirebaseDatabase
                    .getInstance()
                    .getReference("bookstore")
                    .child("author")
                    .child(authorID)
                    .addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            Author author = dataSnapshot.getValue(Author.class);
                            authorList.add(author);
                            if (authorList.size() == authorsIDs.size()) {
                                callback.onAuthorsReceived(authorList);
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
        }
    }

    public void getBook(String bookID, BookCallback callback) {
        FirebaseDatabase
                .getInstance()
                .getReference("bookstore")
                .child("book")
                .child(bookID)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot data) {
                        Book book = data.getValue(Book.class);
                        for (DataSnapshot imagesID : data.child("Images").getChildren()) {
                            book.imagesPaths.add(imagesID.getValue().toString());
                        }
                        for (DataSnapshot authID : data.child("Authors").getChildren()) {
                            book.authorsIDs.add(String.valueOf(authID.getKey()));
                        }
                        callback.onBookLoaded(book);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
    }

    public void addBookToUserBasket(int bookID) {
        String userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        FirebaseDatabase
                .getInstance()
                .getReference("bookstore")
                .child("users/" + userID)
                .child("Basket")
                .child(String.valueOf(bookID))
                .setValue(bookID);
    }


    public void getBookCount(String bookID, BookCountCallback callback) {
        FirebaseDatabase
                .getInstance()
                .getReference("bookstore")
                .child("book")
                .child(bookID)
                .child("count")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        int count = dataSnapshot.getValue(Integer.TYPE);
                        callback.onBookCountLoaded(count);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
    }

    public void getUser(UserCallback callback) {
        FirebaseDatabase
                .getInstance()
                .getReference("bookstore")
                .child("users")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        User user = dataSnapshot.getValue(User.class);
                        callback.onUserLoaded(user);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
    }

    public void createUser() {
        FirebaseDatabase
                .getInstance()
                .getReference("bookstore")
                .child("users")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .child("email")
                .setValue(FirebaseAuth.getInstance().getCurrentUser().getEmail());
    }

    public void updateUserInfo(Map<String, Object> updates) {
        FirebaseDatabase
                .getInstance()
                .getReference("bookstore")
                .child("users")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .updateChildren(updates);
    }

    public void deleteBookFromBasket(String bookID) {
        FirebaseDatabase
                .getInstance()
                .getReference("bookstore").child("users/" + FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Basket").child(bookID).removeValue();
    }

    public void getPagedBooksFromBasket(Map<String, Integer> booksIDsAndCount,
                                        int startPosition,
                                        int loadSize,
                                        BooksFromBasketCallback callback) {

        List<BookInBasketModel> bookInBasketModelList = new ArrayList<>();

        String[] booksIDs = booksIDsAndCount.keySet().toArray(new String[0]);
        for (int i = startPosition; i < loadSize; i++) {
            int finalI = i;
            FirebaseDatabase
                    .getInstance()
                    .getReference("bookstore")
                    .child("book")
                    .child(booksIDs[i])
                    .addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            BookInBasketModel bookInBasketModel = new BookInBasketModel();
                            Book book = dataSnapshot.getValue(Book.class);
                            book.imagesPaths = new ArrayList<>();

                            //TODO: auto-convert : List -> Map
                            for (DataSnapshot imagesID : dataSnapshot.child("Images").getChildren()) {
                                book.imagesPaths.add(imagesID.getValue().toString());
                            }
                            for (DataSnapshot authID : dataSnapshot.child("Authors").getChildren()) {
                                book.authorsIDs.add(String.valueOf(authID.getKey()));
                            }

                            bookInBasketModel.count = booksIDsAndCount.get(booksIDs[finalI]);
                            bookInBasketModel.book = book;
                            bookInBasketModelList.add(bookInBasketModel);
                            if (bookInBasketModelList.size() == booksIDs.length) {
                                callback.onBooksLoaded(bookInBasketModelList);
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
        }

    }

    public void getBooksFromBasket(Map<String, Integer> booksIDsAndCount, BooksFromBasketCallback callback) {
        List<BookInBasketModel> bookInBasketModelList = new ArrayList<>();
        for (String bookID : booksIDsAndCount.keySet()) {
            FirebaseDatabase
                    .getInstance()
                    .getReference("bookstore")
                    .child("book")
                    .child(bookID)
                    .addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            BookInBasketModel bookInBasketModel = new BookInBasketModel();
                            Book book = dataSnapshot.getValue(Book.class);
                            book.imagesPaths = new ArrayList<>();

                            //TODO: auto-convert : List -> Map
                            for (DataSnapshot imagesID : dataSnapshot.child("Images").getChildren()) {
                                book.imagesPaths.add(imagesID.getValue().toString());
                            }
                            for (DataSnapshot authID : dataSnapshot.child("Authors").getChildren()) {
                                book.authorsIDs.add(String.valueOf(authID.getKey()));
                            }

                            bookInBasketModel.count = booksIDsAndCount.get(bookID);
                            bookInBasketModel.book = book;
                            bookInBasketModelList.add(bookInBasketModel);
                            if (bookInBasketModelList.size() == booksIDsAndCount.size()) {
                                callback.onBooksLoaded(bookInBasketModelList);
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
        }

    }

    public void getBookPublisher(String publisherID, DatabaseCallback<Publisher> callback) {
        FirebaseDatabase
                .getInstance()
                .getReference("bookstore")
                .child("publisher")
                .child(publisherID)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        callback.onResult(dataSnapshot.getValue(Publisher.class));
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
    }

    //        DatabaseReference db =  FirebaseDatabase.getInstance().getReference("/bookstore");
//        String key = db.child("orders").push().getKey();
//        Map<String, Object> newOrder = orderModel.toMap();
//        db.child("order").child(key).setValue(newOrder).addOnSuccessListener(aVoid -> subtractBookCount(db, orderModel));
//        String userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
//        db.child("users").child(userID).child("Orders").child(orderModel.date).setValue(key).addOnSuccessListener(aVoid ->{
//          cleanBasket(db, orderModel, userID);
//        });

    public void writeNewOrder(OrderRegistrationModel orderModel) {
        Map<String, Object> newOrder = orderModel.toMap();
        String key = FirebaseDatabase
                .getInstance()
                .getReference("bookstore")
                .child("orders")
                .push()
                .getKey();
        FirebaseDatabase
                .getInstance()
                .getReference("bookstore")
                .child("order")
                .child(key)
                .setValue(newOrder).addOnSuccessListener(aVoid -> subtractBookCount(orderModel));
        String userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        FirebaseDatabase
                .getInstance()
                .getReference("bookstore")
                .child("users")
                .child(userID)
                .child("Orders")
                .child(orderModel.date)
                .setValue(key)
                .addOnSuccessListener(aVoid -> {
                    cleanBasket(orderModel);
                });
    }

    private void cleanBasket(OrderRegistrationModel orderModel) {
        for (String bookID : orderModel.Books.keySet()) {
            FirebaseDatabase
                    .getInstance()
                    .getReference("bookstore")
                    .child("users")
                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                    .child("Basket")
                    .child(bookID)
                    .removeValue();
        }
    }

    private void subtractBookCount(OrderRegistrationModel orderModel) {
        for (Map.Entry<String, Integer> bookIDAndCount : orderModel.Books.entrySet()) {
            FirebaseDatabase
                    .getInstance()
                    .getReference("bookstore")
                    .child("book")
                    .child(bookIDAndCount.getKey())
                    .child("count")
                    .addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            long oldCount = (long) dataSnapshot.getValue();
                            long newCount = oldCount - bookIDAndCount.getValue();
                            FirebaseDatabase
                                    .getInstance()
                                    .getReference("bookstore")
                                    .child("book")
                                    .child(bookIDAndCount.getKey())
                                    .child("count")
                                    .setValue(newCount);
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
        }
    }

    public void getOrderList(Map<String, String> ordersIDs, DatabaseCallback<List<OrderRegistrationModel>> callback) {
        List<OrderRegistrationModel> orderList = new ArrayList<>();
        for (String orderID : ordersIDs.values()) {
            FirebaseDatabase
                    .getInstance()
                    .getReference("bookstore")
                    .child("order")
                    .child(orderID)
                    .addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            OrderRegistrationModel order = dataSnapshot.getValue(OrderRegistrationModel.class);
                            orderList.add(order);
                            if (orderList.size() == ordersIDs.size()) {
                                callback.onResult(orderList);
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
        }
    }

    public void getBookAndCountList(Map<String, Integer> booksIDsAndCount,
                                    DatabaseCallback<List<BookInOrderModel>> callback) {
        List<BookInOrderModel> bookInOrderModelList = new ArrayList<>();
        for (String bookID : booksIDsAndCount.keySet()) {
            FirebaseDatabase
                    .getInstance()
                    .getReference("bookstore")
                    .child("book")
                    .child(bookID)
                    .addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            BookInOrderModel bookInOrderModel = new BookInOrderModel();
                            bookInOrderModel.book = dataSnapshot.getValue(Book.class);
                            bookInOrderModel.count = booksIDsAndCount.get(bookID);
                            bookInOrderModelList.add(bookInOrderModel);
                            if (bookInOrderModelList.size() == booksIDsAndCount.size()) {
                                callback.onResult(bookInOrderModelList);
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
        }
    }

    public void getStoreList(DatabaseCallback<List<StoreModel>> callback) {
        FirebaseDatabase
                .getInstance()
                .getReference("bookstore")
                .child("store")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        List<StoreModel> storeList = new ArrayList<>();
                        for (DataSnapshot storeDataSnapshot : dataSnapshot.getChildren()) {
                            storeList.add(storeDataSnapshot.getValue(StoreModel.class));
                        }
                        callback.onResult(storeList);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
    }

    public void getSearchedBookList(String searchedText, DatabaseCallback<List<Book>> callback) {
        FirebaseDatabase
                .getInstance()
                .getReference("bookstore")
                .child("book")
                .orderByKey()
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        List<Book> bookList = new ArrayList<>();
                        for (DataSnapshot bookData : dataSnapshot.getChildren()) {
                            Book book = bookData.getValue(Book.class);
                            for (DataSnapshot imagesID : bookData.child("Images").getChildren()) {
                                book.imagesPaths.add(imagesID.getValue().toString());
                            }
                            for (DataSnapshot authID : bookData.child("Authors").getChildren()) {
                                book.authorsIDs.add(String.valueOf(authID.getKey()));
                            }
                            if (book.title.toLowerCase().contains(searchedText.toLowerCase())) {
                                bookList.add(book);
                            }
                            searchAuthors(book.authorsIDs, searchedText, bool -> {
                                if (bool && !bookList.contains(book)) {
                                    bookList.add(book);
                                }
                            });
                        }
                        callback.onResult(bookList);
//                        List<Book> bookList = new ArrayList<>();
//                        for (DataSnapshot bookData : dataSnapshot.child("book").getChildren()) {
//                            Book book = bookData.getValue(Book.class);
//                            if (book.title.toLowerCase().contains(searchedText.toLowerCase())) {
//                                bookList.add(book);
//                            }
//
//                            for (DataSnapshot imagesID : bookData.child("Images").getChildren()) {
//                                book.imagesPaths.add(imagesID.getValue().toString());
//                            }
//                            for (DataSnapshot authID : bookData.child("Authors").getChildren()) {
//                                book.authorsIDs.add(String.valueOf(authID.getKey()));
//                            }
//                            book.authors = new ArrayList<>();
//                            for (String authorID : book.authorsIDs) {
//                                book.authors.add(dataSnapshot.child("author/" + authorID).getValue(Author.class));
//                            }
//                            for (Author author : book.authors) {
//                                if ((author.author_name.toLowerCase().contains(searchedText.toLowerCase()) ||
//                                        author.author_surname.toLowerCase().contains(searchedText.toLowerCase()))
//                                        && !bookList.contains(book)) {
//                                    bookList.add(book);
//                                }
//                            }
//                            searchedBookList.onResult(bookList);
//                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
    }

    public void getSearchedCategorizedBookList(String categoryID,
                                               String searchedText,
                                               DatabaseCallback<List<Book>> callback) {
        FirebaseDatabase
                .getInstance()
                .getReference("bookstore")
                .child("book")
                .orderByChild("Categories/" + categoryID)
                .equalTo(true)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        List<Book> bookList = new ArrayList<>();
                        for (DataSnapshot bookData : dataSnapshot.getChildren()) {
                            Book book = bookData.getValue(Book.class);
                            for (DataSnapshot imagesID : bookData.child("Images").getChildren()) {
                                book.imagesPaths.add(imagesID.getValue().toString());
                            }
                            for (DataSnapshot authID : bookData.child("Authors").getChildren()) {
                                book.authorsIDs.add(String.valueOf(authID.getKey()));
                            }
                            if (book.title.toLowerCase().contains(searchedText.toLowerCase())) {
                                bookList.add(book);
                            }
                            searchAuthors(book.authorsIDs, searchedText, bool -> {
                                if (bool && !bookList.contains(book)) {
                                    bookList.add(book);
                                }
                            });
                        }
                        callback.onResult(bookList);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
    }

    private void searchAuthors(List<String> authorsIDs, String searchedText, DatabaseCallback<Boolean> callback) {
        for (String authorID : authorsIDs) {
            FirebaseDatabase
                    .getInstance()
                    .getReference("bookstore")
                    .child("author/" + authorID)
                    .addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            Author author = dataSnapshot.getValue(Author.class);
                            if ((author.author_name.toLowerCase().contains(searchedText.toLowerCase()) ||
                                    author.author_surname.toLowerCase().contains(searchedText.toLowerCase()))) {
                                callback.onResult(true);
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
        }
    }
}
