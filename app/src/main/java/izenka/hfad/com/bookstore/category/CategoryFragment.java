package izenka.hfad.com.bookstore.category;

import android.arch.lifecycle.ViewModelProviders;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.beardedhen.androidbootstrap.BootstrapEditText;

import izenka.hfad.com.bookstore.R;
import izenka.hfad.com.bookstore.model.db_classes.Book;

public class CategoryFragment extends Fragment {

    private RecyclerView rvBookList;
    private CategorizedBooksViewModel viewModel;
    private boolean itemShouldBeScaled;
    private BootstrapEditText etSearchCategory;

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
        switch (getResources().getConfiguration().orientation) {
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
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        CategorizedBooksAdapter adapter = new CategorizedBooksAdapter(DIFF_CALLBACK);
        adapter.shouldBeScaled(itemShouldBeScaled);
        viewModel = ViewModelProviders.of(requireActivity()).get(CategorizedBooksViewModel.class);
        adapter.setViewModel(viewModel);
        viewModel.getBookListLiveData().observe(this, adapter::submitList);
        rvBookList.setAdapter(adapter);
    }

    public static final DiffUtil.ItemCallback<Book> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<Book>() {
                @Override
                public boolean areItemsTheSame(
                        @NonNull Book oldBook, @NonNull Book newBook) {
                    return oldBook.book_id == newBook.book_id;
                }

                @Override
                public boolean areContentsTheSame(
                        @NonNull Book oldBook, @NonNull Book newBook) {
                    return oldBook.equals(newBook);
                }
            };
}
