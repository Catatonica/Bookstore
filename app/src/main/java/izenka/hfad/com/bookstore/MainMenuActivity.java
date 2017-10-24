package izenka.hfad.com.bookstore;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import stanford.androidlib.SimpleActivity;
import stanford.androidlib.data.SimpleDatabase;

public class MainMenuActivity extends SimpleActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
       // SimpleDatabase.with(this)
         // .executeSqlFile("bookstore");

        SQLiteDatabase db=openOrCreateDatabase("bookstore", MODE_PRIVATE,null);
        Cursor cr=db.rawQuery("SELECT category_name FROM category ORDER BY category_id",null);
        String categoryName[] = new String[6];
        int i=0;
        if(cr.moveToFirst()){
            do{
                categoryName[i]=cr.getString(cr.getColumnIndex("category_name"));
                i++;
            }while(cr.moveToNext());
            cr.close();
            setButtonText(categoryName);
        }
    }

    private void setButtonText(String [] categoryName){
        $B(R.id.btnForeign).setText(categoryName[0]);
        $B(R.id.btnKid).setText(categoryName[1]);
        $B(R.id.btnBusieness).setText(categoryName[2]);
        $B(R.id.btnFiction).setText(categoryName[3]);
        $B(R.id.btnStudy).setText(categoryName[4]);
        $B(R.id.btnNonfiction).setText(categoryName[5]);
    }

    public void onKindOfBookClick(View view) {
        Intent intent=new Intent(this, BookListActivity.class);
        int categoryID=0;
        switch(view.getId()){
            case R.id.btnBusieness:
                categoryID=3;
                intent.putExtra("categoryID", categoryID);
                break;
            case R.id.btnFiction:
                categoryID=4;
                intent.putExtra("categoryID", categoryID);
                break;
            case R.id.btnForeign:
                categoryID=1;
                intent.putExtra("categoryID", categoryID);
                break;
            case R.id.btnKid:
                categoryID=2;
                intent.putExtra("categoryID", categoryID);
                break;
            case R.id.btnNonfiction:
                categoryID=6;
                intent.putExtra("categoryID", categoryID);
                break;
            case R.id.btnStudy:
                categoryID=5;
                intent.putExtra("categoryID", categoryID);
                break;
        }
        startActivity(intent);
    }

    public void onShopCartClick(View view) {
        Intent intent=new Intent(this, BasketActivity.class);
        startActivity(intent);
    }
}
