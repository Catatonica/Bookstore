package izenka.hfad.com.bookstore.view.order;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import izenka.hfad.com.bookstore.R;
import izenka.hfad.com.bookstore.model.db_operations.Read;
import izenka.hfad.com.bookstore.presenter.OrderPresenter;
import stanford.androidlib.SimpleActivity;

public class OrderActivity extends SimpleActivity implements IOrderView {

    private OrderPresenter presenter;

    private TextView tvHeading;
    private TextView tvPrise;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        Intent intent = getIntent();

        if (presenter == null) {
            presenter = new OrderPresenter(this, intent);
        }
        presenter.onViewCreated();
    }

    @Override
    public void setHeading(String heading) {
        tvHeading.setText(heading);
    }

    @Override
    public void setPrise(String prise) {
        tvPrise.setText(prise);
    }

    @Override
    public void initViews() {
        $B(R.id.btnGoBack).setOnClickListener(view -> presenter.onBackClicked());
        tvHeading = $TV(R.id.tvHeading);
        tvPrise = $TV(R.id.tvTotalPrice);
    }

    @Override
    public void onBackClick() {
        onBackPressed();
    }

    @Override
    public void setBooksIDAndCount(String orderID) {
        Read.setBookIDAndCount(this, orderID);
    }
}
