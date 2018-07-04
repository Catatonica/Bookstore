package izenka.hfad.com.bookstore.callbacks;


import izenka.hfad.com.bookstore.account.User;

@FunctionalInterface
public interface UserCallback {
    void onUserLoaded(User user);
}
