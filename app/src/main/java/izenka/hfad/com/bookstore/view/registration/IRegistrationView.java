package izenka.hfad.com.bookstore.view.registration;


import android.view.View;

import izenka.hfad.com.bookstore.view.IView;

public interface IRegistrationView extends IView{
    void onBackClick();
    void onRegisterClick(View view);
    void showToast(String message, int duration);
    void showSucceedMessage();
}
