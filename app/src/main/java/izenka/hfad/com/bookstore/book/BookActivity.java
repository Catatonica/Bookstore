package izenka.hfad.com.bookstore.book;

import android.app.PendingIntent;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import izenka.hfad.com.bookstore.R;
import izenka.hfad.com.bookstore.basket.BasketActivity;
import izenka.hfad.com.bookstore.view.orders.OrdersActivity;
import izenka.hfad.com.bookstore.view.qr_code.QRCodeActivity;

public class BookActivity extends AppCompatActivity implements BookNavigator {

    private int bookID;
//    private SharedPreferences sp;
//    private Animation anim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);

//        setToolbar();

//        anim = AnimationUtils.loadAnimation(this, R.anim.my_alpha);
        Intent intent = getIntent();
        bookID = intent.getIntExtra("bookID", 0);

        BookViewModel viewModel = ViewModelProviders.of(this).get(BookViewModel.class);
        viewModel.setBookID(bookID);
        viewModel.setNavigator(this);
        viewModel.getBookLiveData().observe(this, book -> {
            assert book != null;
            setToolbar(book.title);
        });

        setFragment(new BookFragment());
    }

    private void setToolbar(String title) {
        setSupportActionBar(findViewById(R.id.toolbar));
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(title);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_arrow_back_white_24dp);
        }
    }

    private void setFragment(Fragment fragment) {
        Fragment bookFragment = getSupportFragmentManager().findFragmentById(R.id.content_frame);
        if (bookFragment == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.content_frame, fragment)
                    .commit();
        }
    }

//    private void setPublisher(int publisherID, final View view) {
//        DatabaseReference publRef = fb.child("bookstore/publisher");
//        Query query = publRef.orderByChild("publisher_id").equalTo(publisherID);
//        query.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot data) {
//                Publisher publisher = data.getChildren().iterator().next().getValue(Publisher.class);
//
//                TextView tvPublisher = (TextView) view.findViewById(R.id.tvPublisher);
//                tvPublisher.setText(publisher.publisher_name);
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
//    }

    @Override
    public void onPutInBasketClicked() {
//        view.startAnimation(anim);

//        sp = getSharedPreferences("myPref", MODE_PRIVATE);
//        // MainMenuActivity.user.getBooksIDs().add(bookID+"");
//        //  MainMenuActivity.booksIDs.add(bookID+"");
//        SharedPreferences.Editor e = sp.edit();
////        if(MainMenuActivity.stringSet == null){
////            MainMenuActivity.stringSet = new HashSet<>();
////        }
//        MainMenuActivity.stringSet.add(String.valueOf(bookID));
//        e.putStringSet("booksIDs", MainMenuActivity.stringSet);
//        e.apply();
        Toast.makeText(this, "добавлено", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void notifyUser(String title) {
        Intent intent = new Intent(this, BookActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this, "123")
                .setSmallIcon(R.drawable.ic_notifications_black_24dp)
                .setContentTitle("Новые поступления!")
                .setContentText("В продаже появилась книга \"/" + title + "\"")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        // notificationId is a unique int for each notification that you must define
        notificationManager.notify(12345, mBuilder.build());
        /*
        The setFlags() method shown above helps preserve the user's expected navigation experience after
         they open your app via the notification. But whether you want to use that depends on what type
         of activity you're starting, which may be one of the following:
                    An activity that exists exclusively for responses to the notification.
                     There's no reason the user would navigate to this activity during normal app use,
                     so the activity starts a new task instead of being added to your app's existing
                     task and back stack. This is the type of intent created in the sample above.
                     An activity that exists in your app's regular app flow. In this case, starting the
                        activity should create a back stack so that the user's expectations for the
                      Back and Up buttons is preserved.
         */
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.actionbar_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = new Intent();
        switch (item.getItemId()) {
            case R.id.action_basket:
                intent.setClass(this, BasketActivity.class);
                startActivity(intent);
                break;

            case R.id.action_orders:
                intent.setClass(this, OrdersActivity.class);
                startActivity(intent);
                break;

            case R.id.action_qrcode:
                intent.setClass(this, QRCodeActivity.class);
                startActivity(intent);
                break;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}

