package izenka.hfad.com.bookstore;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

public class BasketActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basket);
        LinearLayout llBasket=(LinearLayout) findViewById(R.id.llBasket);
        View view=getLayoutInflater().inflate(R.layout.empty_basket,null);
        llBasket.addView(view);
    }

    public void onBtnBackClick(View view) {
        finish();
    }
}
