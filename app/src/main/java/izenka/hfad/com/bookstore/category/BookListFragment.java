package izenka.hfad.com.bookstore.category;

import android.arch.lifecycle.ViewModelProviders;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.beardedhen.androidbootstrap.BootstrapEditText;

import izenka.hfad.com.bookstore.R;

public class BookListFragment extends Fragment {

    private RecyclerView rvBookList;
    private BookListViewModel viewModel;
    private boolean itemShouldBeScaled;
    private BootstrapEditText etSearchCategory;;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_category, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        etSearchCategory = view.findViewById(R.id.etCategorySearch);
        etSearchCategory.setOnClickListener(view1 -> {
            viewModel.onSearchInCategoryClicked();
        });

        rvBookList = view.findViewById(R.id.rvBookList);
        GridLayoutManager layoutManager = null;
        switch (getResources().getConfiguration().orientation){
            case Configuration.ORIENTATION_LANDSCAPE:
                layoutManager = new GridLayoutManager(view.getContext(), 4);
                itemShouldBeScaled = true;
                break;
            case Configuration.ORIENTATION_PORTRAIT:
                layoutManager = new GridLayoutManager(view.getContext(), 2);
                itemShouldBeScaled = false;
                break;
        }
        rvBookList.setLayoutManager(layoutManager);
        //TODO: do sth
//        int initialHeight = etSearchCategory.getLayoutParams().height;
//        rvBookList.addOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//                super.onScrolled(recyclerView, dx, dy);
//                if (dy > 0) {
//                    etSearchCategory.setHeight(0);
//                    etSearchCategory.setVisibility(View.INVISIBLE);
//                } else if (dy < 0) {
//                    etSearchCategory.setVisibility(View.VISIBLE);
//                    etSearchCategory.setHeight(initialHeight);
//                }
//            }
//        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = ViewModelProviders.of(getActivity()).get(BookListViewModel.class);
        //TODO this or through getActivity??
        viewModel.getBookListLiveData().observe(this, books -> {
            BookListAdapter adapter = new BookListAdapter(books, viewModel, itemShouldBeScaled);
            rvBookList.setAdapter(adapter);
        });
    }
}
