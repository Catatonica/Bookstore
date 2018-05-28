package izenka.hfad.com.bookstore.model.repositories;


import java.util.List;

import izenka.hfad.com.bookstore.model.db_classes.Book;

public class BookListRepository {
    private List<Book> bookList;

//    public BookListRepository(){
//        bookList = new ArrayList<>();
//    }

    public List<Book> getBookList() {
        return bookList;
    }

    public void setBookList(List<Book> bookList) {
        this.bookList = bookList;
    }

    public int getItemCount() {
        return bookList.size();
    }
}
