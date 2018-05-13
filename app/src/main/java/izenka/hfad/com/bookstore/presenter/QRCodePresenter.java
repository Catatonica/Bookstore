package izenka.hfad.com.bookstore.presenter;


import android.widget.Toast;

import izenka.hfad.com.bookstore.view.qr_code.QRCodeView;
import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class QRCodePresenter {

    private QRCodeView qrCodeView;

    public QRCodePresenter(QRCodeView qrCodeView) {
        this.qrCodeView = qrCodeView;
    }

    public void onViewCreated(){
        qrCodeView.initViews();
        qrCodeView.showMessage("Разместите QR-код внутри области", Toast.LENGTH_LONG);
    }

    public void onPaused(){
        qrCodeView.stopCamera();
    }

}
