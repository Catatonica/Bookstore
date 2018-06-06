package izenka.hfad.com.bookstore.view.qr_code;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.google.zxing.Result;

import izenka.hfad.com.bookstore.R;
import izenka.hfad.com.bookstore.presenter.QRCodePresenter;
import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class QRCodeActivity extends AppCompatActivity implements IQRCodeView {

    private ZXingScannerView mScannerView;
    private QRCodePresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcode);

        if (presenter == null) {
            presenter = new QRCodePresenter(this);
        }
        presenter.onViewCreated();
    }

    public void onPause() {
        super.onPause();
        presenter.onPaused();
    }

    @Override
    public void stopCamera() {
        mScannerView.stopCamera();
    }

    @Override
    public void showMessage(String message, int duration) {
        Toast.makeText(this, message, duration).show();
    }

    @Override
    public void handleResult(Result result) {
        mScannerView.stopCamera();
        presenter.onResultHandled(Integer.valueOf(result.getText()));
    }

    @Override
    public void initViews() {
        mScannerView = new ZXingScannerView(this);
        setContentView(mScannerView);
        mScannerView.setResultHandler(this);
        mScannerView.startCamera();
    }

    @Override
    public void startActivity(Intent intent, Class activity) {
        intent.setClass(this, activity);
        startActivity(intent);
        finish();
    }

    @Override
    public void startActivityWithAnimation(View view, Class activity) {

    }

    @Override
    public void startActivityWithAnimation(View view, Intent intent, Class activity) {

    }


}
