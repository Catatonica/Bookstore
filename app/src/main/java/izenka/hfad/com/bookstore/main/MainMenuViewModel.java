package izenka.hfad.com.bookstore.main;

import android.arch.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class MainMenuViewModel extends ViewModel {

    MainMenuNavigator navigator;

    public void setNavigator(MainMenuNavigator navigator) {
        this.navigator = navigator;
    }

    public void onCategoryClicked(int categoryID){
        navigator.onCategoryClicked(categoryID);
    }

    public void signOut() {
        FirebaseAuth.getInstance().signOut();
    }

    public String getUserEmail() {
        return FirebaseAuth.getInstance().getCurrentUser().getEmail();
    }

    public FirebaseUser getUser() {
        return FirebaseAuth.getInstance().getCurrentUser();
    }

    public void onSearchClicked() {
        navigator.onSearchClicked();
    }
}
