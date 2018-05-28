package izenka.hfad.com.bookstore.view.qr_code;


import izenka.hfad.com.bookstore.view.IView;
import me.dm7.barcodescanner.zxing.ZXingScannerView;

public interface IQRCodeView extends IView, ZXingScannerView.ResultHandler {
    void showMessage(String message, int duration);

    void stopCamera();
}
