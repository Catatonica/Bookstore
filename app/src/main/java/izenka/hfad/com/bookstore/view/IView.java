package izenka.hfad.com.bookstore.view;

import android.view.View;

public interface IView {
    void initViews();
    void startActivity(View view, Class activity);
    void startActivity(Class activity, String extraName, int extra);
    void startActivity(View view, Class activity, String extraName, int extra);
}
