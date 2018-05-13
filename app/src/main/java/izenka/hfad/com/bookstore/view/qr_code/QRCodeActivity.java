package izenka.hfad.com.bookstore.view.qr_code;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.zxing.Result;

import izenka.hfad.com.bookstore.BookActivity;
import izenka.hfad.com.bookstore.R;
import izenka.hfad.com.bookstore.presenter.QRCodePresenter;
import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class QRCodeActivity extends AppCompatActivity implements QRCodeView {

    private ZXingScannerView mScannerView;
    private QRCodePresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcode);

        if(presenter == null){
            presenter = new QRCodePresenter(this);
        }
        presenter.onViewCreated();
    }

    public void onPause() {
        super.onPause();
        presenter.onPaused();
    }

    @Override
    public void stopCamera(){
        mScannerView.stopCamera();
    }

    @Override
    public void showMessage(String message, int duration){
        Toast.makeText(this, message, duration).show();
    }

    @Override
    public void handleResult(Result result) {
        mScannerView.stopCamera();
        startActivity(BookActivity.class, "bookID", Integer.valueOf(result.getText()));
        finish();
    }

    @Override
    public void initViews() {
        mScannerView = new ZXingScannerView(this);
        setContentView(mScannerView);
        mScannerView.setResultHandler(this);
        mScannerView.startCamera();
    }

    @Override
    public void startActivity(Class activity, String extraName, int extra) {
        Intent intent = new Intent(this, activity);
        intent.putExtra(extraName, extra);
        startActivity(intent);
    }

    @Override
    public void startActivity(View view, Class activity) {

    }

    @Override
    public void startActivity(View view, Class activity, String extraName, int extra) {

    }
}
