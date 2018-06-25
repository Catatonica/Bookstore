package izenka.hfad.com.bookstore.presenter;


import android.content.Intent;
import android.widget.Toast;

import izenka.hfad.com.bookstore.book.BookActivity;
import izenka.hfad.com.bookstore.view.qr_code.IQRCodeView;

public class QRCodePresenter implements IPresenter{

    private IQRCodeView qrCodeView;

    public QRCodePresenter(IQRCodeView qrCodeView) {
        this.qrCodeView = qrCodeView;
    }

    @Override
    public void onViewCreated(){
        qrCodeView.initViews();
        qrCodeView.showMessage("Разместите QR-код внутри области", Toast.LENGTH_LONG);
    }

    public void onPaused(){
        qrCodeView.stopCamera();
    }

    public void onResultHandled(int result){
        Intent intent = new Intent();
        intent.putExtra("bookID", result);
        qrCodeView.startActivity(intent, BookActivity.class);
    }
}
