package izenka.hfad.com.bookstore.orders;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;

import izenka.hfad.com.bookstore.R;
import izenka.hfad.com.bookstore.model.db_classes.OrderGet;

public class OrdersActivity extends AppCompatActivity {

//    private TextView tvTicker;
//    private TextView tvPrice;

    private LinearLayout llOrders;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_orders);
//        tvTicker = findViewById(R.id.ticker);
//        tvPrice = findViewById(R.id.price);
        llOrders = findViewById(R.id.llOrders);

        // Obtain a new or prior instance of HotStockViewModel from the
        // ViewModelProviders utility class.
        OrdersViewModel viewModel = ViewModelProviders.of(this).get(OrdersViewModel.class);

        LiveData<DataSnapshot> liveData = viewModel.getDataSnapshotLiveData();

        liveData.observe(this, new Observer<DataSnapshot>() {
            @Override
            public void onChanged(@Nullable DataSnapshot dataSnapshot) {
                if (dataSnapshot != null) {
                    // update the UI here with values in the snapshot
                    final View view = getLayoutInflater().inflate(R.layout.order, null);
//                    view.setTransitionName(order_id);

                    OrderGet order = dataSnapshot.getValue(OrderGet.class);

                    TextView tvDate = (TextView) view.findViewById(R.id.tvDate);
                    tvDate.setText(order.date);
                    TextView tvPrice = (TextView) view.findViewById(R.id.tvTotalPrice);
                    tvPrice.setText(String.valueOf(order.totalPrice));
                    TextView tvStatus = (TextView) view.findViewById(R.id.tvStatus);
                    tvStatus.setText(order.status);

                    llOrders.addView(view);
//                    String ticker = dataSnapshot.child("ticker").getValue(String.class);
//                    tvTicker.setText(ticker);
//                    Float price = dataSnapshot.child("price").getValue(Float.class);
//                    tvPrice.setText(String.format(Locale.getDefault(), "%.2f", price));
                }
            }
        });
    }

//    private OrdersPresenter presenter;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_orders);
//
//        setToolbar();
//
//        if(presenter == null){
//            presenter = new OrdersPresenter(this);
//        }
//        presenter.onViewCreated();
//        presenter.showOrders(getSharedPreferences("ordersPref", MODE_PRIVATE));
//    }
//
//    private void setToolbar(){
//        setSupportActionBar(findViewById(R.id.toolbar));
//        ActionBar actionBar = getSupportActionBar();
//        if(actionBar!=null){
//            actionBar.setTitle(R.string.orders);
//            actionBar.setDisplayHomeAsUpEnabled(true);
//            actionBar.setHomeAsUpIndicator(R.drawable.ic_arrow_back_white_24dp);
//        }
//    }
//
//    @Override
//    public void startActivity(Intent intent, Class activity) {
//        intent.setClass(this, activity);
//        startActivity(intent);
//    }
//
//    @Override
//    public void startActivityWithAnimation(View view, Class activity) {
//
//    }
//
//    @Override
//    public void startActivityWithAnimation(View view, Intent intent, Class activity) {
//
//    }
//
//    @Override
//    public void initViews() {
////        $IV(R.id.btnGoBack).setOnClickListener(view->presenter.onBackClicked());
////        View orderView = LayoutInflater.from(this).inflate(R.layout.order, null, false);
////        orderView.setOnClickListener(view -> presenter.onOrderClicked(view));
//    }
//
//
//    @Override
//    public void showOrder(String orderID) {
//        ReadOrders.getOrder(this, orderID);
//    }
//
//    @Override
//    public void onBackClick() {
//        onBackPressed();
//    }
//
//
//    public void onOrderClick(View view) {
//        presenter.onOrderClicked(view);
//    }
//
//    @Override
//    public boolean onSupportNavigateUp() {
//        onBackPressed();
//        return super.onSupportNavigateUp();
//    }
}
