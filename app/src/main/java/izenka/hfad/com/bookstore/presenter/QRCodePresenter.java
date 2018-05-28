package izenka.hfad.com.bookstore.presenter;


<<<<<<< HEAD
import android.content.Intent;
import android.widget.Toast;

import izenka.hfad.com.bookstore.view.book.BookActivity;
import izenka.hfad.com.bookstore.view.qr_code.IQRCodeView;

public class QRCodePresenter implements IPresenter{

    private IQRCodeView qrCodeView;

    public QRCodePresenter(IQRCodeView qrCodeView) {
        this.qrCodeView = qrCodeView;
    }

    @Override
=======
import android.widget.Toast;

import izenka.hfad.com.bookstore.view.qr_code.QRCodeView;
import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class QRCodePresenter {

    private QRCodeView qrCodeView;

    public QRCodePresenter(QRCodeView qrCodeView) {
        this.qrCodeView = qrCodeView;
    }

>>>>>>> 713af467c300756a5d42d3eca411d50cbcc356d3
    public void onViewCreated(){
        qrCodeView.initViews();
        qrCodeView.showMessage("Разместите QR-код внутри области", Toast.LENGTH_LONG);
    }

    public void onPaused(){
        qrCodeView.stopCamera();
    }

<<<<<<< HEAD
    public void onResultHandled(int result){
        Intent intent = new Intent();
        intent.putExtra("bookID", result);
        qrCodeView.startActivity(intent, BookActivity.class);
    }
=======
>>>>>>> 713af467c300756a5d42d3eca411d50cbcc356d3
}
