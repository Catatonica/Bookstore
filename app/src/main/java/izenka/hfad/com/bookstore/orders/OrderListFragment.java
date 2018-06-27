package izenka.hfad.com.bookstore.orders;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import izenka.hfad.com.bookstore.R;

public class OrderListFragment extends Fragment {

    private RecyclerView rvOrderList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_order_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rvOrderList = view.findViewById(R.id.rvOrderList);
        rvOrderList.setLayoutManager(new LinearLayoutManager(view.getContext(), LinearLayoutManager.VERTICAL, false));
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        OrdersViewModel viewModel = ViewModelProviders.of(requireActivity()).get(OrdersViewModel.class);
        viewModel.getOrderListLiveData().observe(this, orderList->{
            OrderListAdapter adapter = new OrderListAdapter(orderList, viewModel);
            rvOrderList.setAdapter(adapter);
        });
    }
}
