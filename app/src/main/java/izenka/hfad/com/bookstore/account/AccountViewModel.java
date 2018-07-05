package izenka.hfad.com.bookstore.account;


import android.app.Activity;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseAuth;

import java.util.Map;

import izenka.hfad.com.bookstore.DatabaseSingleton;

public class AccountViewModel extends ViewModel {

    private MutableLiveData<User> userLiveData;

    private AccountActivityNavigator accountActivityNavigator;
    private RegistrationFragmentNavigator registrationFragmentNavigator;

    void createAccount(Activity activity, String email, String password) {
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(activity, task -> {
                        if (task.isSuccessful()) {
                            createUserNode();
                            FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password);
                            accountActivityNavigator.setFragment(new UserProfileFragment());
                            loadUser();
                        } else {
                            registrationFragmentNavigator.onFailedRegistration();
                        }
                    });
    }

    void signIn(Activity activity, String email, String password) {
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(activity, task -> {
                        if (task.isSuccessful()) {
                            accountActivityNavigator.setFragment(new UserProfileFragment());
                            loadUser();
                        } else {
                            registrationFragmentNavigator.onFailedRegistration();
                        }
                    });
    }

    void signOut() {
        FirebaseAuth.getInstance().signOut();
        accountActivityNavigator.setFragment(new UserRegistrationFragment());
    }

    private void createUserNode() {
        DatabaseSingleton.getInstance().createUser();
    }

    void setAccountActivityNavigator(AccountActivityNavigator accountActivityNavigator) {
        this.accountActivityNavigator = accountActivityNavigator;
    }

    void setRegistrationFragmentNavigator(RegistrationFragmentNavigator registrationFragmentNavigator) {
        this.registrationFragmentNavigator = registrationFragmentNavigator;
    }

    void saveChanges(Map<String, Object> updates) {
        DatabaseSingleton.getInstance().updateUserInfo(updates);
        accountActivityNavigator.showMessage();
    }

    public MutableLiveData<User> getUserLiveData() {
        if (userLiveData == null) {
            userLiveData = new MutableLiveData<>();
            loadUser();
        }
        return userLiveData;
    }

    private void loadUser() {
        DatabaseSingleton.getInstance().getUser(user -> userLiveData.postValue(user));
    }
}
