package izenka.hfad.com.bookstore;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public interface AccountListener {
     default void signOut() {
        FirebaseAuth.getInstance().signOut();
    }

     default String getUserEmail() {
        return FirebaseAuth.getInstance().getCurrentUser().getEmail();
    }

     default FirebaseUser getUser() {
        return FirebaseAuth.getInstance().getCurrentUser();
    }
}
