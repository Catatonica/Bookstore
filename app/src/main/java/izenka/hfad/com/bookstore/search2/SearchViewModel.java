package izenka.hfad.com.bookstore.search2;


import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.paging.PagedList;


import java.util.concurrent.Executors;

import izenka.hfad.com.bookstore.MainThreadExecutor;
import izenka.hfad.com.bookstore.model.db_classes.Book;

public class SearchViewModel extends ViewModel {

    private MutableLiveData<PagedList<Book>> bookListLiveData;
    private SearchNavigator navigator;

    public void setNavigator(SearchNavigator navigator) {
        this.navigator = navigator;
    }

    public LiveData<PagedList<Book>> getBookListLiveData(String searchedText) {

        if (bookListLiveData == null) {
            bookListLiveData = new MutableLiveData<>();
        }
        SearchedBookListDataSource dataSource = new SearchedBookListDataSource(searchedText);

        PagedList.Config config = new PagedList.Config.Builder()
                .setEnablePlaceholders(false)
                .setPageSize(4)
                .build();

        PagedList<Book> bookList = new PagedList.Builder<>(dataSource, config)
                .setFetchExecutor(new MainThreadExecutor())
                .setNotifyExecutor(Executors.newSingleThreadExecutor())
                .build();

        bookListLiveData.postValue(bookList);
        return bookListLiveData;
    }

//    private void findBooks(String searchedText) {
//        DatabaseReference db = FirebaseDatabase.getInstance().getReference("bookstore");
//        db.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                List<Book> bookList = new ArrayList<>();
//                for(DataSnapshot bookData: dataSnapshot.child("book").getChildren()) {
//                    Book book = bookData.getValue(Book.class);
//                    if (book.title.toLowerCase().contains(searchedText.toLowerCase())) {
//                        bookList.add(book);
//                    }
//                    List<String> imagesPaths = new ArrayList<>();
//                    for (DataSnapshot imagesID : bookData.child("Images").getChildren()) {
//                        imagesPaths.add(imagesID.getValue().toString());
//                    }
//                    book.imagesPaths = imagesPaths;
//                    final List<Integer> authorsIDs = new ArrayList<>();
//                    for (DataSnapshot authID : bookData.child("Authors").getChildren()) {
//                        authorsIDs.add(Integer.parseInt(String.valueOf(authID.getValue())));
//                    }
//                    book.authors = new ArrayList<>();
//                    for (int authorID : authorsIDs) {
//                        book.authors.add(dataSnapshot.child("author/" + authorID).getValue(Author.class));
//                    }
//                    for (Author author : book.authors) {
//                        if ((author.author_name.toLowerCase().contains(searchedText.toLowerCase()) ||
//                                author.author_surname.toLowerCase().contains(searchedText.toLowerCase()))
//                                && !bookList.contains(book)) {
//                            bookList.add(book);
//                        }
//                    }
//                }
//                bookListLiveData.postValue(bookList);
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
//    }


    public void onBookClicked(Book book) {
        navigator.onBookClicked(book.book_id);
    }
}
