package izenka.hfad.com.bookstore.view.order;

import stanford.androidlib.SimpleActivity;

public class OrderActivity extends SimpleActivity /*implements IOrderView*/ {

//    private OrderPresenter presenter;
//
//    private TextView tvHeading;
//    private TextView tvPrise;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_order);
//
//        Intent intent = getIntent();
//        setToolbar("Заказ "+intent.getStringExtra("date"));
//
//        if (presenter == null) {
//            presenter = new OrderPresenter(this, intent);
//        }
//        presenter.onViewCreated();
//    }
//
//    private void setToolbar(String title){
//        setSupportActionBar(findViewById(R.id.toolbar));
//        ActionBar actionBar = getSupportActionBar();
//        if(actionBar!=null){
//            actionBar.setTitle(title);
//            actionBar.setDisplayHomeAsUpEnabled(true);
//            actionBar.setHomeAsUpIndicator(R.drawable.ic_arrow_back_white_24dp);
//        }
//    }
//
////    @Override
////    public void setHeading(String heading) {
////        tvHeading.setText(heading);
////    }
//
//    @Override
//    public void setPrise(String prise) {
//        tvPrise.setText(prise);
//    }
//
//    @Override
//    public void initViews() {
////        $B(R.id.btnGoBack).setOnClickListener(view -> presenter.onBackClicked());
////        tvHeading = $TV(R.id.tvHeading);
//        tvPrise = $TV(R.id.tvTotalPrice);
//    }
//
//    @Override
//    public boolean onSupportNavigateUp() {
//        onBackPressed();
//        return super.onSupportNavigateUp();
//    }
//
//    //    @Override
////    public void onBackClick() {
////        onBackPressed();
////    }
//
//    @Override
//    public void setBooksIDAndCount(String orderID) {
//        Read.setBookIDAndCount(this, orderID);
//    }
}
