package izenka.hfad.com.bookstore.main;

import android.arch.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class MainMenuViewModel extends ViewModel {

    CategoriesNavigator navigator;

    public void setNavigator(CategoriesNavigator navigator) {
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
}
