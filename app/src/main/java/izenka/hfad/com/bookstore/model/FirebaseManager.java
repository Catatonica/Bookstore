package izenka.hfad.com.bookstore.model;


import com.google.firebase.auth.FirebaseAuth;

public class FirebaseManager {

//    private final String FIREBASE_USERNAME = "izenka666@gmail.com";
//    private final String FIREBASE_PASSWORD = "sepultura777";

    public void connectToFirebase() {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        mAuth.signInAnonymously();
//        mAuth.signInWithEmailAndPassword(FIREBASE_USERNAME, FIREBASE_PASSWORD);
    }
}
