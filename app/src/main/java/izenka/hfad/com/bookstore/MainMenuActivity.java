package izenka.hfad.com.bookstore;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class MainMenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
    }

    public void onKindOfBookClick(View view) {
        Intent intent=new Intent(this, BookListActivity.class);
        String kindOfBook="";
        switch(view.getId()){
            case R.id.btnBusieness:
                kindOfBook="busieness";
                intent.putExtra("kindOfBook", kindOfBook);
                intent.putExtra("bookImageName",kindOfBook);
                break;
            case R.id.btnFiction:
                kindOfBook="fiction";
                intent.putExtra("kindOfBook", kindOfBook);
                intent.putExtra("bookImageName",kindOfBook);
                break;
            case R.id.btnForeign:
                kindOfBook="foreign";
                intent.putExtra("kindOfBook", kindOfBook);
                intent.putExtra("bookImageName",kindOfBook);
                break;
            case R.id.btnKid:
                kindOfBook="kid";
                intent.putExtra("kindOfBook", kindOfBook);
                intent.putExtra("bookImageName",kindOfBook);
                break;
            case R.id.btnNonfiction:
                kindOfBook="nonfiction";
                intent.putExtra("kindOfBook", kindOfBook);
                intent.putExtra("bookImageName",kindOfBook);
                break;
            case R.id.btnStudy:
                kindOfBook="study";
                intent.putExtra("kindOfBook", kindOfBook);
                intent.putExtra("bookImageName",kindOfBook);
                break;
        }
        startActivity(intent);
    }

    public void onShopCartClick(View view) {
        Intent intent=new Intent(this, BasketActivity.class);
        startActivity(intent);
    }
}
