package izenka.hfad.com.bookstore.controller.db_operations;


import android.app.Activity;
import android.graphics.Typeface;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
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

import izenka.hfad.com.bookstore.R;
import izenka.hfad.com.bookstore.model.db_classes.Author;


public class SetHelper {

    public static void setName(final Activity activity, final String path, final int viewID) {
        DatabaseReference fb = FirebaseDatabase.getInstance().getReference();
        //path = category/categoryID/category_name
        DatabaseReference catRef = fb.child("bookstore/" + path);
        catRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String text = dataSnapshot.getValue().toString();
                //viewID = R.id.tvKindOfBook
                TextView tvView = (TextView) activity.findViewById(viewID);
                tvView.setText(text);
                String type = path.split("/")[0];
                switch (type) {
                    case "category": {
                        tvView.setTypeface(Typeface.createFromAsset(
                                activity.getAssets(), "fonts/5.ttf"));
                        tvView.setTextSize(42);
                    }
                    break;
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    static void setAuthor(List<Long> authorsID, final View view) {
        final List<String> authorSurName = new ArrayList<>();
        DatabaseReference authorRef = FirebaseDatabase.getInstance().getReference().child("bookstore/author");
        for (long authorID : authorsID) {
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
                    TextView tvBookAuthor = (TextView) view.findViewById(R.id.tvBookAuthor);
                    tvBookAuthor.setText(auth.substring(1, auth.length() - 1));
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }

    }

    static void setImage(Activity activity, List<String> Images, int bookID, View view) {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        String bookImage = Images.get(0);
        StorageReference imageRef = storage.getReference().child(bookImage);

        ImageView imgBtnBook = (ImageView) view.findViewById(R.id.imgBtnBook);
        imgBtnBook.setId(bookID);

        Glide.with(activity /* context */)
             .using(new FirebaseImageLoader())
             .load(imageRef)
             .into(imgBtnBook);

    }
}
