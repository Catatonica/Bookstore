package izenka.hfad.com.bookstore.main_menu.view;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import izenka.hfad.com.bookstore.R;
import izenka.hfad.com.bookstore.main_menu.presenter.IMainMenuPresenter;

public class MainMenuFragment extends Fragment implements IMainMenuFragmentView {

    private IMainMenuPresenter presenter;

    @Override
    public void setPresenter(@NonNull IMainMenuPresenter presenter) {
        this.presenter = presenter;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main_menu, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter.showCategoriesNames();
        view.findViewById(R.id.tvRunningLine).setSelected(true);
        view.findViewById(R.id.etSearch).setOnClickListener(view1 -> presenter.onSearchClicked());
        view.findViewById(R.id.btnForeign).setOnClickListener(view1 -> presenter.onCategoryClicked(view1));
        view.findViewById(R.id.btnKid).setOnClickListener(view1 -> presenter.onCategoryClicked(view1));
        view.findViewById(R.id.btnBusiness).setOnClickListener(view1 -> presenter.onCategoryClicked(view1));
        view.findViewById(R.id.btnFiction).setOnClickListener(view1 -> presenter.onCategoryClicked(view1));
        view.findViewById(R.id.btnStudy).setOnClickListener(view1 -> presenter.onCategoryClicked(view1));
        view.findViewById(R.id.btnNonfiction).setOnClickListener(view1 -> presenter.onCategoryClicked(view1));
    }
}
