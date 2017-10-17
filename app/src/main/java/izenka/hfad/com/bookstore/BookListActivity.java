package izenka.hfad.com.bookstore;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class BookListActivity extends AppCompatActivity {

    private GridLayout gridLayout;
    private Map<String,String> cathegoryDef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_list);

        gridLayout=(GridLayout) findViewById(R.id.gridLayout);

        Intent intent=getIntent();
        String kindOfBook=intent.getStringExtra("kindOfBook");
        makeMap();
        getBooks(kindOfBook);
    }

    private void makeMap(){
        cathegoryDef=new HashMap<>();
        cathegoryDef.put("busieness","Бизнес-литература");
        cathegoryDef.put("fiction","Художественная литература");
        cathegoryDef.put("nonfiction","Нехудожественная литература");
        cathegoryDef.put("kid","Детская литература");
        cathegoryDef.put("study","Учебная литература");
        cathegoryDef.put("foreign","Иностранная литература");
    }

    private void getBooks(String kindOfBook){

        //R.raw.bus_books
        int kindOfBookID=getResources().getIdentifier(kindOfBook,"raw", getPackageName());

        Scanner scanner=new Scanner(getResources().openRawResource(kindOfBookID));
        while (scanner.hasNextLine()){
            String bookData[]=scanner.nextLine().split("\t");
            String bookName=bookData[0];
            String bookAuthor=bookData[1];
            String bookPrise=bookData[2];
            String bookImageName=bookData[3];

            View view=getLayoutInflater().inflate(R.layout.book, null);

            ImageButton imgBtnBook=(ImageButton) view.findViewById(R.id.imgBtnBook);
            //R.drawable.bus_books
            int bookImageID=getResources().getIdentifier(bookImageName,"drawable", getPackageName());
            imgBtnBook.setImageResource(bookImageID);
            TextView tvBookName=(TextView) view.findViewById(R.id.tvBookName);
            tvBookName.setText(bookName);
            TextView tvBookAuthor=(TextView) view.findViewById(R.id.tvBookAuthor);
            tvBookAuthor.setText(bookAuthor);
            TextView tvBookPrise=(TextView) view.findViewById(R.id.tvBookPrise);
            tvBookPrise.setText(bookPrise);
            TextView tvKindOfBook=(TextView) findViewById(R.id.tvKindOfBook);
            tvKindOfBook.setText(cathegoryDef.get(kindOfBook));

            gridLayout.addView(view);
        }
    }

    public void onShopCartClick(View view) {
        Intent intent=new Intent(this, BasketActivity.class);
        startActivity(intent);
    }

    public void onBookClick(View view) {
        Intent intent=new Intent(this, BookActivity.class);
        startActivity(intent);
    }
}
