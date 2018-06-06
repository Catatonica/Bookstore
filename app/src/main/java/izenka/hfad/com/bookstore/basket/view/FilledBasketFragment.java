package izenka.hfad.com.bookstore.basket.view;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import izenka.hfad.com.bookstore.R;
import izenka.hfad.com.bookstore.basket.presenter.IBasketPresenter;
import izenka.hfad.com.bookstore.main_menu.view.MainMenuActivity;
import izenka.hfad.com.bookstore.model.db_operations.ReadBooksForBasket;
import mehdi.sakout.fancybuttons.FancyButton;

public class FilledBasketFragment extends Fragment implements IFilledBasketFragmentView {
    private IBasketPresenter presenter;

    private LinearLayout llBooks;

    @Override
    public void setPresenter(@NonNull IBasketPresenter presenter) {
        this.presenter = presenter;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.filled_basket, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.findViewById(R.id.btnChooseEth).setOnClickListener(view1 -> presenter.onChooseEthClicked((FancyButton) view1));
        view.findViewById(R.id.btnDelete).setOnClickListener(view1 -> presenter.onDeleteClicked(view1));
        view.findViewById(R.id.btnRegister).setOnClickListener(view1 -> presenter.onRegisterClicked(view1));
        llBooks = (LinearLayout) view.findViewById(R.id.llBooks);
//        tvTotalPrise = (TextView) llBasket.findViewById(R.id.tvTotalPriceForAll);
        fillTheBasket();
    }

    private void fillTheBasket() {
//        if (!MainMenuActivity.stringSet.isEmpty()) {
        for (final String bookID : MainMenuActivity.stringSet) {
            ReadBooksForBasket.queryBook(getActivity(), bookID, presenter.getCheckedViewSet(), presenter);
        }
//        } else {
//            basketView.addEmptyBasketView();
//        }
    }

    @Override
    public void addBookView(View oneBookInBasketView) {
        llBooks.addView(oneBookInBasketView);
    }
}
