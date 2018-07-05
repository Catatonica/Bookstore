package izenka.hfad.com.bookstore.orders;


import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import izenka.hfad.com.bookstore.R;
import izenka.hfad.com.bookstore.order_registration.OrderRegistrationModel;

public class OrderListAdapter extends RecyclerView.Adapter<OrderListAdapter.OrderViewHolder> {

    private List<OrderRegistrationModel> orderList;
    private OrdersViewModel viewModel;

    OrderListAdapter(List<OrderRegistrationModel> orderList, OrdersViewModel viewModel) {
        this.orderList = orderList;
        this.viewModel = viewModel;
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order, parent, false);
        return new OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        OrderRegistrationModel order = orderList.get(position);
        holder.tvDate.setText(order.getDate());
        holder.tvStatus.setText(order.getStatus());
        holder.tvPrice.setText(String.format("%.1f р.", order.getPrice()));
        holder.itemView.setOnClickListener(item -> viewModel.openDetailsScreen(order));
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    public void setList(List<OrderRegistrationModel> orderList) {
        this.orderList = orderList;
    }

    static class OrderViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tvDate) TextView tvDate;
        @BindView(R.id.tvPrice) TextView tvPrice;
        @BindView(R.id.tvStatus) TextView tvStatus;

        OrderViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
