package izenka.hfad.com.bookstore;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class BookListActivity extends AppCompatActivity {

    private GridLayout gridLayout;
    //private Map<String,String> categoryDef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_list);

        gridLayout=(GridLayout) findViewById(R.id.gridLayout);

        getBooksInCategory();

    }

   /* private void makeMap(){
        categoryDef =new HashMap<>();
        categoryDef.put("busieness","Бизнес-литература");
        categoryDef.put("fiction","Художественная литература");
        categoryDef.put("nonfiction","Нехудожественная литература");
        categoryDef.put("kid","Детская литература");
        categoryDef.put("study","Учебная литература");
        categoryDef.put("foreign","Иностранная литература");
    }
    */

   private void getBooksInCategory(){
       Intent intent=getIntent();
       int categoryID=intent.getIntExtra("categoryID",0);
       SQLiteDatabase db=openOrCreateDatabase("bookstore", MODE_PRIVATE,null);
       Cursor crBooksCategories=db.rawQuery("SELECT book_id FROM booksCategories WHERE category_id="+categoryID+"",null);
       Cursor crCategory=db.rawQuery("SELECT category_name from category where category_id="+categoryID+"",null);

       TextView tvKindOfBook=(TextView) findViewById(R.id.tvKindOfBook);
       tvKindOfBook.setText(crCategory.getString(crCategory.getColumnIndex("category_name")));
       crCategory.close();

       if(crBooksCategories.moveToFirst()){
           do{
               int bookID=crBooksCategories.getInt(crBooksCategories.getColumnIndex("book_id"));
               Cursor crBook=db.rawQuery("SELECT book_title, book_prise FROM book WHERE book_id="+bookID+"",null);
               Cursor crBooksAuthors=db.rawQuery("SELECT author_id FROM booksAuthors WHERE book_id"+bookID+"",null);
               int authorID=crBooksAuthors.getInt(crBooksAuthors.getColumnIndex("author_id"));
               Cursor crAuthor=db.rawQuery("SELECT author_name FROM author WHERE author_id="+authorID+"",null);

               String bookName=crBook.getString(crBook.getColumnIndex("book_title"));
               String bookAuthor=crAuthor.getString(crAuthor.getColumnIndex("author_name"));
               String bookPrise=crBook.getString(crBook.getColumnIndex("book_prise"));

               View view=getLayoutInflater().inflate(R.layout.book, null);

               ImageButton imgBtnBook=(ImageButton) view.findViewById(R.id.imgBtnBook);
               //R.drawable.bus_books
              // int bookImageID=getResources().getIdentifier(bookImageName,"drawable", getPackageName());
             //  imgBtnBook.setImageResource(bookImageID);
               TextView tvBookName=(TextView) view.findViewById(R.id.tvBookName);
               tvBookName.setText(bookName);
               TextView tvBookAuthor=(TextView) view.findViewById(R.id.tvBookAuthor);
               tvBookAuthor.setText(bookAuthor);
               TextView tvBookPrise=(TextView) view.findViewById(R.id.tvBookPrise);
               tvBookPrise.setText(bookPrise);

               gridLayout.addView(view);
           }while(crBooksCategories.moveToNext());
           crBooksCategories.close();
       }
   }

   /*
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
            tvKindOfBook.setText(categoryDef.get(kindOfBook));

            gridLayout.addView(view);
        }
    }
    */

    public void onShopCartClick(View view) {
        Intent intent=new Intent(this, BasketActivity.class);
        startActivity(intent);
    }

    public void onBookClick(View view) {
        Intent intent=new Intent(this, BookActivity.class);
        startActivity(intent);
    }
}
