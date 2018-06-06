package izenka.hfad.com.bookstore.main_menu.view;


import android.support.annotation.NonNull;

import izenka.hfad.com.bookstore.main_menu.presenter.IMainMenuPresenter;

public interface IMainMenuFragmentView {
    void setPresenter(@NonNull IMainMenuPresenter presenter);
}
