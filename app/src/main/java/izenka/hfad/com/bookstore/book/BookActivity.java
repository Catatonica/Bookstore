package izenka.hfad.com.bookstore.book;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import izenka.hfad.com.bookstore.R;
import izenka.hfad.com.bookstore.basket.BasketActivity;
import izenka.hfad.com.bookstore.orders.OrdersActivity;
import izenka.hfad.com.bookstore.qr_code.QRCodeActivity;
import izenka.hfad.com.bookstore.search.SearchActivity;

public class BookActivity extends AppCompatActivity implements BookNavigator {

    private String bookID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);

        Intent intent = getIntent();

        bookID = intent.getStringExtra("bookID");

        BookViewModel viewModel = ViewModelProviders.of(this).get(BookViewModel.class);
        viewModel.setBookID(bookID);
        viewModel.setNavigator(this);
        viewModel.getBookLiveData().observe(this, book -> {
            if (book != null) {
                setToolbar(book.getTitle());
            }
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

    @Override
    public void onPutInBasketClicked() {
        Toast.makeText(this, "добавлено", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void notifyUser(String title) {
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this, "123")
                .setSmallIcon(R.drawable.ic_notifications_black_24dp)
                .setContentTitle("Новые поступления!")
                .setContentText("В продаже появилась книга \"" + title + "\"")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true);

        Intent intent = new Intent(this, BookActivity.class);
        intent.putExtra("bookID", bookID);
//        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK/*Intent.FLAG_ACTIVITY_CLEAR_TOP*/);

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(BookActivity.class);
        stackBuilder.addNextIntent(intent);

        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(
                        0,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );
        mBuilder.setContentIntent(resultPendingIntent);
        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
// mId allows you to update the notification later on.
        mNotificationManager.notify(12345, mBuilder.build());
//        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

//        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this, "123")
//                .setSmallIcon(R.drawable.ic_notifications_black_24dp)
//                .setContentTitle("Новые поступления!")
//                .setContentText("В продаже появилась книга \"" + title + "\"")
//                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
//                .setContentIntent(pendingIntent)
//                .setAutoCancel(true);
//        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
//        // notificationId is a unique int for each notification that you must define
//        notificationManager.notify(12345, mBuilder.build());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.actionbar_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_basket:
                Intent intent = new Intent(this, BasketActivity.class);
                intent.putExtra("bookID", bookID);
                startActivity(intent);
                break;

            case R.id.action_orders:
                openScreen(OrdersActivity.class);
                break;

            case R.id.action_search:
                openScreen(SearchActivity.class);
                break;

            case R.id.action_qrcode:
                openScreen(QRCodeActivity.class);
                break;

            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

    private void openScreen(Class activityClass) {
        Intent intent = new Intent();
        intent.setClass(this, activityClass);
        startActivity(intent);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}

