package izenka.hfad.com.bookstore;

import android.app.Activity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

public abstract class Helper {
    public static void hideKeyboard(Activity activity, View view) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
