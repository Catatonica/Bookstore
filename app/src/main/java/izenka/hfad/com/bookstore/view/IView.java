package izenka.hfad.com.bookstore.view;

import android.content.Intent;
import android.view.View;

public interface IView {
    void initViews();
    void startActivity(Intent intent, Class activity);
    void startActivityWithAnimation(View view, Class activity);
    void startActivityWithAnimation(View view, Intent intent, Class activity);

//    void startActivity(View view, Class activity);
//    void startActivity(Class activity, String extraName, int extra);
//    void startActivity(View view, Class activity, String extraName, int extra);
}
