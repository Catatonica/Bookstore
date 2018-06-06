package izenka.hfad.com.bookstore.model.user;


/*
 Singleton
 We only have one user logged into our app at any one time and are going to create a singleton object
 that we can reach from any part of our code.
 */
public class CurrentUser {
    //    private static final String DEBUG_TAG = "tag";
    private String name;
    // Create instance
    private static CurrentUser user = new CurrentUser();

    // Protect class from being instantiated
    private CurrentUser() {
    }

    // Return only instance of user
    public static CurrentUser getUser() {
        return user;
    }

    // Set name
    protected void setName(String n) {
        name = n;
    }
}
