package izenka.hfad.com.bookstore;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

import stanford.androidlib.SimpleActivity;

public class BookActivity extends SimpleActivity {

    private boolean bool = true;
    private DatabaseReference fb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);

        Intent intent=getIntent();
        int bookID=intent.getIntExtra("bookID",0);

       fillViews(bookID);
    }

    private void fillViews(int bookID){
        fb=FirebaseDatabase.getInstance().getReference();
        DatabaseReference bookRef=fb.child("bookstore/book");
        Query query=bookRef.orderByChild("book_id").equalTo(bookID);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot data) {
                final Book book=data.getChildren().iterator().next().getValue(Book.class);
                $TV(R.id.tvTitle).setText("\""+book.title+"\"");
                $TV(R.id.tvYear).setText(""+book.publication_year);


                List<Integer> Authors=new ArrayList<>();
                for(DataSnapshot authID: data.getChildren().iterator().next().child("Authors").getChildren()){
                    Authors.add( Integer.parseInt(String.valueOf(authID.getValue())));
                }
                setAuthor(Authors);
               // setImage(book.book_image);
               // setAuthor(book.book_author_id);
                $TV(R.id.tvPrise).setText(book.price);
                TextView tvAvailability=$TV(R.id.tvAvailability);
                if(book.count!=0){
                    tvAvailability.setText("в наличии");
                }
                else{
                    tvAvailability.setText("нет в наличии");
                }
                $TV(R.id.tvAnnot).setText(book.description);

                $B(R.id.btnParameters).setOnClickListener(new View.OnClickListener() {
                    Boolean isClicked=false;
                    @Override
                    public void onClick(View v) {
                        LinearLayout llParameters=(LinearLayout) findViewById(R.id.llParameters);
                        if(isClicked) {
                            llParameters.removeAllViews();
                            isClicked=false;
                        }

                        else {
                            final View view = getLayoutInflater().inflate(R.layout.book_parameters, null);
                            TextView tvCount = (TextView) view.findViewById(R.id.tvCount);
                            tvCount.setText("" + book.count);
                            TextView tvPages = (TextView) view.findViewById(R.id.tvPages);
                            tvPages.setText("" + book.pages_number);
                            setPublisher(book.book_publisher_id, view);
                            TextView tvCover = (TextView) view.findViewById(R.id.tvCover);
                            tvCover.setText(book.cover);

                            llParameters.addView(view);
                            isClicked = true;
                        }
                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private void setImage(String bookImage ){
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference imageRef = storage.getReference().child(bookImage);

        Glide.with(this /* context */)
                .using(new FirebaseImageLoader())
                .load(imageRef)
                .into($IV(R.id.ivBookImage));

    }

    private void setAuthor( List<Integer> authorsID) {
        final List<String> authorSurName = new ArrayList<>();
        DatabaseReference authorRef = fb.child("bookstore/author");
        for (int authorID : authorsID) {
            Log.d("List", authorID + "");
            Query queryAuthor = authorRef.orderByChild("author_id").equalTo(authorID);
            queryAuthor.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Author author = dataSnapshot.getChildren().iterator().next().getValue(Author.class);
                    String authorName = author.author_name.substring(0, 1);
                    String authorSurname = author.author_surname;
                    authorSurName.add(authorSurname + " " + authorName + ".");
                    String auth = authorSurName.toString();
                    TextView tvBookAuthor = (TextView) findViewById(R.id.tvAuthor);
                    tvBookAuthor.setText(auth.substring(1, auth.length() - 1));
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
    }


  /*  public void onParametersClick(View view) {
        View glParameters=getLayoutInflater().inflate(R.layout.book_parameters, null);
        TextView tvCount=(TextView) glParameters.findViewById(R.id.tvCount);
        tvCount.setText(book.count);
        TextView tvPages=(TextView) glParameters.findViewById(R.id.tvPages);
        tvPages.setText(book.pages_number);
        setPublisher(book.book_publisher_id, glParameters);

        LinearLayout llParameters=(LinearLayout) findViewById(R.id.llParameters);
        llParameters.addView(glParameters);
    }
    */


    private void setPublisher(int publisherID, final View view){
        DatabaseReference publRef=fb.child("bookstore/publisher");
        Query query=publRef.orderByChild("publisher_id").equalTo(publisherID);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot data) {
                Publisher publisher=data.getChildren().iterator().next().getValue(Publisher.class);

                TextView tvPublisher=(TextView) view.findViewById(R.id.tvPublisher);
                tvPublisher.setText(publisher.publisher_name);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void onPutInBasketClick(View view) {
    }

    public void onExpandClick(View view) {
         TextView tvAnnot = $TV(R.id.tvAnnot);
         ImageButton ibExpand = $IB(R.id.ibExpand);
        if (bool) {
            tvAnnot.setMaxLines(30);
            ibExpand.setBackground(getDrawable(R.drawable.narrow));
            bool = false;
        } else {
            tvAnnot.setMaxLines(3);
            bool = true;
            ibExpand.setBackground(getDrawable(R.drawable.expand));
        }
    }

    public void onReturnBackClick(View view) {
        finish();
    }
}

