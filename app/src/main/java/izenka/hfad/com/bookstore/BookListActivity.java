package izenka.hfad.com.bookstore;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import stanford.androidlib.SimpleActivity;

public class BookListActivity extends SimpleActivity {

    private DatabaseReference fb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_list);

        Intent intent=getIntent();
        String categoryName=intent.getStringExtra("categoryName");
        TextView tvKindOfBook=$TV(R.id.tvKindOfBook);
        tvKindOfBook.setText(categoryName);
        tvKindOfBook.setTypeface(Typeface.createFromAsset(
         getAssets(), "fonts/5.ttf"));
        tvKindOfBook.setTextSize(42);
        int categoryID=intent.getIntExtra("categoryID", 0);
        getBooksInCategory(categoryID);
    }

   private void getBooksInCategory(final int categoryID){

       fb= FirebaseDatabase.getInstance().getReference();
       final DatabaseReference bookRef=fb.child("bookstore/book");
       final Query queryBook=bookRef.orderByChild("book_id");
       queryBook.addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange(DataSnapshot data) {
               for (DataSnapshot bookData : data.getChildren())
                   for (DataSnapshot id : bookData.child("Categories").getChildren())
                       if (String.valueOf(id.getValue()).equals(String.valueOf(categoryID))) {
                               {
                                   Book book = bookData.getValue(Book.class);
                                   Log.d("book", book.toString());
                                   final View view = getLayoutInflater().inflate(R.layout.book, null);

                                   String title = book.title;
                                   String price = book.price;

                                   List<Integer> Authors=new ArrayList<>();
                                   for(DataSnapshot authID: bookData.child("Authors").getChildren()){
                                       Authors.add( Integer.parseInt(String.valueOf(authID.getValue())));
                                   }
                                   setAuthor(Authors, view);
                                   List <String> Images=new ArrayList<String>();
                                   for(DataSnapshot imagesID: bookData.child("Images").getChildren()){
                                       Images.add( imagesID.getValue().toString());
                                   }
                                   int bookID = book.book_id;
                                           setImage(Images, bookID, view);


                                   TextView tvBookPrise = (TextView) view.findViewById(R.id.tvBookPrise);
                                   tvBookPrise.setText(price);
                                   TextView tvBookName = (TextView) view.findViewById(R.id.tvBookName);
                                   tvBookName.setText("\"" + title + "\"");

                                   //R.drawable.bus_books
                                   // int bookImageID=getResources().getIdentifier(bookImageName,"drawable", getPackageName());
                                   //  imgBtnBook.setImageResource(bookImageID);


                                   GridLayout gridLayout = (GridLayout) findViewById(R.id.gridLayout);
                                   gridLayout.addView(view);
                               }
                           }
           }

        @Override
           public void onCancelled(DatabaseError databaseError) {

           }
       });
   }

   private void setAuthor( List<Integer> authorsID, final View view){
       final List<String> authorSurName=new ArrayList<>();
       DatabaseReference authorRef=fb.child("bookstore/author");
       for(int authorID:authorsID){
           Log.d("List", authorID+"");
           Query queryAuthor=authorRef.orderByChild("author_id").equalTo(authorID);
           queryAuthor.addValueEventListener(new ValueEventListener() {
               @Override
               public void onDataChange(DataSnapshot dataSnapshot) {
                   Author author=dataSnapshot.getChildren().iterator().next().getValue(Author.class);
                   String authorName=author.author_name.substring(0,1);
                   String authorSurname=author.author_surname;
                   authorSurName.add(authorSurname+" "+authorName+".");
                   String auth=authorSurName.toString();
                   TextView tvBookAuthor=(TextView) view.findViewById(R.id.tvBookAuthor);
                   tvBookAuthor.setText(auth.substring(1,auth.length()-1));
               }

               @Override
               public void onCancelled(DatabaseError databaseError) {

               }
           });
       }


     //  tvBookAuthor.setText(String.valueOf(authorSurName));
   }

    private void setImage(List <String> Images, int bookID, View view){
        FirebaseStorage storage = FirebaseStorage.getInstance();
        String bookImage=Images.get(0);
        StorageReference imageRef = storage.getReference().child(bookImage);
        log("imageRef"+imageRef);

        ImageButton imgBtnBook=(ImageButton) view.findViewById(R.id.imgBtnBook);
        imgBtnBook.setId(bookID);

        Glide.with(this /* context */)
                .using(new FirebaseImageLoader())
                .load(imageRef)
                .into(imgBtnBook);

    }


    public void onShopCartClick(View view) {
        Intent intent=new Intent(this, BasketActivity.class);
        startActivity(intent);
    }

    public void onBookClick(View view) {
        Intent intent=new Intent(this, BookActivity.class);
        intent.putExtra("bookID", view.getId());
        startActivity(intent);
    }

    public void onReturnBackClick(View view) {
        finish();
    }
}
