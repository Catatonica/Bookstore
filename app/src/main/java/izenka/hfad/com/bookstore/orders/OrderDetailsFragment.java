package izenka.hfad.com.bookstore.orders;


import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

import izenka.hfad.com.bookstore.R;

public class OrderDetailsFragment extends Fragment{

    private ListView lvBookList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_order_details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        lvBookList = view.findViewById(R.id.lvBookList);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        OrdersViewModel viewModel = ViewModelProviders.of(requireActivity()).get(OrdersViewModel.class);
        viewModel.getOrderLiveData().observe(this, order->{
            lvBookList.setAdapter(new BookListAdapter(order.bookList));
        });
    }
}
