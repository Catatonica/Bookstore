package izenka.hfad.com.bookstore.orders;


import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import izenka.hfad.com.bookstore.R;

public class OrderListAdapter extends RecyclerView.Adapter<OrderListAdapter.OrderViewHolder> {

    private List<OrderModel> orderList;
    private OrdersViewModel viewModel;

    public OrderListAdapter(List<OrderModel> orderList, OrdersViewModel viewModel) {
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
        OrderModel order = orderList.get(position);
        holder.tvDate.setText(order.date);
        holder.tvStatus.setText(order.status);
        holder.tvPrice.setText(String.format("%.1f р.", order.price));
        holder.itemView.setOnClickListener(item->viewModel.openDetailsScreen(order));
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    static class OrderViewHolder extends RecyclerView.ViewHolder{

        TextView tvDate;
        TextView tvPrice;
        TextView tvStatus;

        public OrderViewHolder(View itemView) {
            super(itemView);
            tvDate = itemView.findViewById(R.id.tvDate);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            tvStatus = itemView.findViewById(R.id.tvStatus);
        }
    }

}
