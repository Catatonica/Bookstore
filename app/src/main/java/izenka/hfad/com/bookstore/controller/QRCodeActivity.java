package izenka.hfad.com.bookstore.controller;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.zxing.Result;

import izenka.hfad.com.bookstore.R;
import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class QRCodeActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler {

    private ZXingScannerView mScannerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //mScannerView = new ZXingScannerView(this);   // Programmatically initialize the scanner view
        super.onCreate(savedInstanceState);
//        LinearLayout llQRcode = (LinearLayout) findViewById(R.id.llQRcode);
//        llQRcode.addView(llQRcode);
        setContentView(R.layout.activity_qrcode);
        //setContentView(mScannerView);

        qrScanner();
//        mScannerView.setResultHandler(this); // Register ourselves as a handler for scan results.
//        mScannerView.startCamera();
        Toast.makeText(this, "Разместите QR-код внутри области", Toast.LENGTH_LONG).show();

    }

    private void qrScanner() {
        mScannerView = new ZXingScannerView(this);   // Programmatically initialize the scanner view
        setContentView(mScannerView);
        mScannerView.setResultHandler(this); // Register ourselves as a handler for scan results.
        mScannerView.startCamera();         // Start camera
    }

    public void onPause() {
        super.onPause();
        mScannerView.stopCamera();
        // Stop camera on pause
    }

    @Override
    public void handleResult(Result result) {
        // Do something with the result here
        Log.e("handler", result.getText()); // Prints scan results
        Log.e("handler", result.getBarcodeFormat().toString()); // Prints the scan format (qrcode)
        // show the scanner result into dialog box.

        final QRCodeActivity that = this;
//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder.setTitle("Scan Result");
//        builder.setMessage(result.getText());
//        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i) {
//                mScannerView.stopCamera();
//                finish();
//            }
//        });
//        builder.setNeutralButton("resume", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i) {
//                mScannerView.resumeCameraPreview(that);
//            }
//        });
//        AlertDialog alert1 = builder.create();
//        alert1.show();

        mScannerView.stopCamera();

        Intent intent = new Intent(this, BookActivity.class);
        intent.putExtra("bookID", Integer.valueOf(result.getText()));
        startActivity(intent);
        finish();
        // If you would like to resume scanning, call this method below:
        // mScannerView
    }

}
