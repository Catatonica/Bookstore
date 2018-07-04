package izenka.hfad.com.bookstore.callbacks;


@FunctionalInterface
public interface BookCountCallback {
    void onBookCountLoaded(int count);
}
