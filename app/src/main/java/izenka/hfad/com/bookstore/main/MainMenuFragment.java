package izenka.hfad.com.bookstore.main;


import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import izenka.hfad.com.bookstore.R;

public class MainMenuFragment extends Fragment {

    private MainMenuViewModel viewModel;

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
        viewModel = ViewModelProviders.of(requireActivity()).get(MainMenuViewModel.class);
        view.findViewById(R.id.btnForeign).setOnClickListener(view1 -> viewModel.onCategoryClicked(0));
        view.findViewById(R.id.btnKid).setOnClickListener(view1 -> viewModel.onCategoryClicked(1));
        view.findViewById(R.id.btnBusiness).setOnClickListener(view1 -> viewModel.onCategoryClicked(2));
        view.findViewById(R.id.btnFiction).setOnClickListener(view1 -> viewModel.onCategoryClicked(3));
        view.findViewById(R.id.btnStudy).setOnClickListener(view1 -> viewModel.onCategoryClicked(4));
        view.findViewById(R.id.btnNonfiction).setOnClickListener(view1 -> viewModel.onCategoryClicked(5));
        view.findViewById(R.id.etSearch).setOnClickListener(view1 -> viewModel.onSearchClicked());
    }
}
