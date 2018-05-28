package izenka.hfad.com.bookstore.view.qr_code;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
<<<<<<< HEAD
=======
import android.util.Log;
>>>>>>> 713af467c300756a5d42d3eca411d50cbcc356d3
import android.view.View;
import android.widget.Toast;

import com.google.zxing.Result;

<<<<<<< HEAD
=======
import izenka.hfad.com.bookstore.BookActivity;
>>>>>>> 713af467c300756a5d42d3eca411d50cbcc356d3
import izenka.hfad.com.bookstore.R;
import izenka.hfad.com.bookstore.presenter.QRCodePresenter;
import me.dm7.barcodescanner.zxing.ZXingScannerView;

<<<<<<< HEAD
public class QRCodeActivity extends AppCompatActivity implements IQRCodeView {
=======
public class QRCodeActivity extends AppCompatActivity implements QRCodeView {
>>>>>>> 713af467c300756a5d42d3eca411d50cbcc356d3

    private ZXingScannerView mScannerView;
    private QRCodePresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcode);

<<<<<<< HEAD
        if (presenter == null) {
=======
        if(presenter == null){
>>>>>>> 713af467c300756a5d42d3eca411d50cbcc356d3
            presenter = new QRCodePresenter(this);
        }
        presenter.onViewCreated();
    }

    public void onPause() {
        super.onPause();
        presenter.onPaused();
    }

    @Override
<<<<<<< HEAD
    public void stopCamera() {
=======
    public void stopCamera(){
>>>>>>> 713af467c300756a5d42d3eca411d50cbcc356d3
        mScannerView.stopCamera();
    }

    @Override
<<<<<<< HEAD
    public void showMessage(String message, int duration) {
=======
    public void showMessage(String message, int duration){
>>>>>>> 713af467c300756a5d42d3eca411d50cbcc356d3
        Toast.makeText(this, message, duration).show();
    }

    @Override
    public void handleResult(Result result) {
        mScannerView.stopCamera();
<<<<<<< HEAD
        presenter.onResultHandled(Integer.valueOf(result.getText()));
=======
        startActivity(BookActivity.class, "bookID", Integer.valueOf(result.getText()));
        finish();
>>>>>>> 713af467c300756a5d42d3eca411d50cbcc356d3
    }

    @Override
    public void initViews() {
        mScannerView = new ZXingScannerView(this);
        setContentView(mScannerView);
        mScannerView.setResultHandler(this);
        mScannerView.startCamera();
    }

    @Override
<<<<<<< HEAD
    public void startActivity(Intent intent, Class activity) {
        intent.setClass(this, activity);
        startActivity(intent);
        finish();
    }

    @Override
    public void startActivityWithAnimation(View view, Class activity) {
=======
    public void startActivity(Class activity, String extraName, int extra) {
        Intent intent = new Intent(this, activity);
        intent.putExtra(extraName, extra);
        startActivity(intent);
    }

    @Override
    public void startActivity(View view, Class activity) {
>>>>>>> 713af467c300756a5d42d3eca411d50cbcc356d3

    }

    @Override
<<<<<<< HEAD
    public void startActivityWithAnimation(View view, Intent intent, Class activity) {

    }


=======
    public void startActivity(View view, Class activity, String extraName, int extra) {

    }
>>>>>>> 713af467c300756a5d42d3eca411d50cbcc356d3
}
