package izenka.hfad.com.bookstore.callbacks;

@FunctionalInterface
public interface DatabaseCallback<T> {
    void onResult(T t);
}
