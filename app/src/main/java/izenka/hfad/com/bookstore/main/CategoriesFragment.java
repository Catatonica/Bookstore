package izenka.hfad.com.bookstore.main;


import android.arch.lifecycle.ViewModelProviders;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import izenka.hfad.com.bookstore.R;

public class CategoriesFragment extends Fragment /*implements IMainMenuFragmentView*/ {

    private MainMenuViewModel viewModel;

//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        if(context!=null){
//            listener = (CategoriesNavigator) context;
//        }
//    }

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
        viewModel = ViewModelProviders.of(getActivity()).get(MainMenuViewModel.class);
//        showCategoriesNames(view);
        view.findViewById(R.id.btnForeign).setOnClickListener(view1 -> viewModel.onCategoryClicked(0));
        view.findViewById(R.id.btnKid).setOnClickListener(view1 -> viewModel.onCategoryClicked(1));
        view.findViewById(R.id.btnBusiness).setOnClickListener(view1 -> viewModel.onCategoryClicked(2));
        view.findViewById(R.id.btnFiction).setOnClickListener(view1 -> viewModel.onCategoryClicked(3));
        view.findViewById(R.id.btnStudy).setOnClickListener(view1 -> viewModel.onCategoryClicked(4));
        view.findViewById(R.id.btnNonfiction).setOnClickListener(view1 -> viewModel.onCategoryClicked(5));
    }

//    private void showCategoriesNames(@NonNull View view) {
//        Button[] btnArray = {
//                view.findViewById(R.id.btnForeign),
//                view.findViewById(R.id.btnKid),
//                view.findViewById(R.id.btnBusiness),
//                view.findViewById(R.id.btnFiction),
//                view.findViewById(R.id.btnStudy),
//                view.findViewById(R.id.btnNonfiction)
//        };
//
//        String[] stringArray = getResources().getStringArray(R.array.categoriesNames);
//
//        int containerHeight = view.getHeight();
//        int containerWidth = view.getWidth();
//
//        int btnHeight = containerHeight/3 - 33;
//        int btnWidth = containerWidth/2 - 22;
//
//        for (int i = 0; i < btnArray.length; i++) {
//            Button btn = btnArray[i];
//            btn.setWidth(btnWidth);
//            btn.setHeight(btnHeight);
//            btn.setText(stringArray[i]);
//            btn.setTextSize(36);
//            btn.setTypeface(Typeface.createFromAsset(view.getContext().getAssets(), "fonts/5.ttf"));
//        }
//    }
}
