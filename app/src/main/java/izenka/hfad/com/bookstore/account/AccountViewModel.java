package izenka.hfad.com.bookstore.account;


import android.app.Activity;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AccountViewModel extends ViewModel {

    private FirebaseAuth mAuth;
    private AccountActivityNavigator accountActivityNavigator;
    private RegistrationFragmentNavigator registrationFragmentNavigator;
//    private HeaderNavigator headerNavigator;

//    private MutableLiveData<FirebaseUser> userLiveData;
//
//    public MutableLiveData<FirebaseUser> getUserLiveData(){
//        if(userLiveData == null){
//            userLiveData = new MutableLiveData<>();
//            mAuth = FirebaseAuth.getInstance();
//            userLiveData.setValue(mAuth.getCurrentUser());
//        }
//        return userLiveData;
//    }

    private static final String TAG = "EmailPassword";

    public FirebaseUser getUser() {
        if (mAuth == null) {
            mAuth = FirebaseAuth.getInstance();
        }
        return mAuth.getCurrentUser();
    }

    public void createAccount(Activity activity, String email, String password) {
//        showProgressDialog();
        mAuth.createUserWithEmailAndPassword(email, password)
             .addOnCompleteListener(activity, task -> {
                 if (task.isSuccessful()) {
                     createUserNode(task.getResult().getUser());
                     // Sign in success, update UI with the signed-in user's information
//                         Log.d(TAG, "createUserWithEmail:success");
//                         FirebaseUser user = mAuth.getCurrentUser();
                     accountActivityNavigator.setFragment(new UserProfileFragment());
                     loadUser();
//                         updateUI(user);
                 } else {
//                         accountActivityNavigator.onRegistrationFailed();
//                         // If sign in fails, display a message to the user.
//                         Log.w(TAG, "createUserWithEmail:failure", task.getException());
//                         Toast.makeText(AccountActivity, "Authentication failed.", Toast.LENGTH_SHORT).show();
//                         updateUI(null);
                     registrationFragmentNavigator.onFailedRegistration();
                 }
//                     hideProgressDialog();
             });
    }

    public void signIn(Activity activity, String email, String password) {
        Log.d(TAG, "signIn:" + email);

//        showProgressDialog();
//        AuthCredential credential = EmailAuthProvider.getCredential(email, password);
        mAuth.signInWithEmailAndPassword(email, password)
             .addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
                 @Override
                 public void onComplete(@NonNull Task<AuthResult> task) {
                     if (task.isSuccessful()) {
                         accountActivityNavigator.setFragment(new UserProfileFragment());
                         loadUser();
                         // Sign in success, update UI with the signed-in user's information
//                         Log.d(TAG, "signInWithEmail:success");
//                         FirebaseUser user = mAuth.getCurrentUser();
//                         updateUI(user);
                     } else {
                         registrationFragmentNavigator.onFailedRegistration();
                         // If sign in fails, display a message to the user.
//                         Log.w(TAG, "signInWithEmail:failure", task.getException());
//                         Toast.makeText(EmailPasswordActivity.this, "Authentication failed.",
//                                        Toast.LENGTH_SHORT).show();
//                         updateUI(null);
                     }

//                     // [START_EXCLUDE]
//                     if (!task.isSuccessful()) {
//                         mStatusTextView.setText(R.string.auth_failed);
//                     }
//                     hideProgressDialog();
//                     // [END_EXCLUDE]
                 }
             });
        // [END sign_in_with_email]
    }

    public void signOut() {
        mAuth.signOut();
        accountActivityNavigator.setFragment(new UserRegistrationFragment());
//        updateUI(null);
    }

    private void createUserNode(FirebaseUser user) {
        String userID = user.getUid();
//        User user = new User();
//        user.email = mAuth.getCurrentUser().getEmail();
        DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        database.child("/bookstore/users").child(userID).child("email").setValue(user.getEmail());
//        database.child("/bookstore/users").child(userID).setValue(user);
        Log.d("createUser", "true");
    }

    public void setAccountActivityNavigator(AccountActivityNavigator accountActivityNavigator) {
        this.accountActivityNavigator = accountActivityNavigator;
    }

    public void setRegistrationFragmentNavigator(RegistrationFragmentNavigator registrationFragmentNavigator) {
        this.registrationFragmentNavigator = registrationFragmentNavigator;
    }

//    public void setHeaderNavigator(HeaderNavigator headerNavigator){
//        this.headerNavigator = headerNavigator;
//    }

    public void saveChanges(String name,
                            String surname,
                            String phone,
                            String city,
                            String street,
                            String house,
                            String flat) {
        DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        String userID = getUser().getUid();
//        assert name == null :
                database.child("/bookstore/users").child(userID).child("name").setValue(name);
//        assert surname == null :
                database.child("/bookstore/users").child(userID).child("surname").setValue(surname);
//        assert phone == null :
                database.child("/bookstore/users").child(userID).child("phone").setValue(phone);
//        assert city == null :
                database.child("/bookstore/users").child(userID).child("Address").child("city").setValue(city);
//        assert street == null :
                database.child("/bookstore/users").child(userID).child("Address").child("street").setValue(street);
//        assert house == null :
                database.child("/bookstore/users").child(userID).child("Address").child("house").setValue(house);
//        assert flat == null :
                database.child("/bookstore/users").child(userID).child("Address").child("flat").setValue(flat);
        accountActivityNavigator.showMessage("Изменения сохранены");
    }

    private MutableLiveData<User> userLiveData;

    public MutableLiveData<User> getUserLiveData(){
        Log.d("userInfo", "getUserLiveData");
        if(userLiveData == null){
            userLiveData = new MutableLiveData<>();
            loadUser();
        }
        return userLiveData;
    }

    private void loadUser(){
//        new Thread(() -> {
            String userID = getUser().getUid();
            DatabaseReference database = FirebaseDatabase.getInstance().getReference();
            database.child("/bookstore/users").child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    User user = dataSnapshot.getValue(User.class);
                    Log.d("userInfo", "postValue: "+user.name+user.Address.get("city"));
                    userLiveData.postValue(user);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
//        }).start();
    }
}
