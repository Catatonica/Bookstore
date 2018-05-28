package izenka.hfad.com.bookstore.view.book_list.recycler_view;


import com.google.firebase.storage.StorageReference;

public interface IBookView {

    void setTitle(String title);
    void setPrise(String prise);
    void setId(int id);
    void setAuthor(String author);
    void setImageId(int id);
    void setImage(StorageReference imageRef);
}
