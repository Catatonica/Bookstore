package izenka.hfad.com.bookstore.store_map;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import izenka.hfad.com.bookstore.AccountListener;
import izenka.hfad.com.bookstore.R;
import izenka.hfad.com.bookstore.account.AccountActivity;
import izenka.hfad.com.bookstore.basket.BasketActivity;
import izenka.hfad.com.bookstore.main.MainMenuActivity;
import izenka.hfad.com.bookstore.orders.OrdersActivity;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback, AccountListener {

    private GoogleMap mMap;

    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigationView;

    private ProgressBar pbLoadingProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        setToolbar();
        init();

        pbLoadingProgress = findViewById(R.id.pbLoadingProgress);
        pbLoadingProgress.setVisibility(View.VISIBLE);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    private void init() {
        mDrawerLayout = findViewById(R.id.drawer_layout);
        mNavigationView = findViewById(R.id.nav_view);
        mNavigationView.setCheckedItem(R.id.nav_map);
        if (getUser() != null) {
            setUserHeader();
        } else {
            setNewUserHeader();
        }
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                // Add code here to update the UI based on the item selected
                // For example, swap UI fragments here
                switch (item.getItemId()) {
                    case R.id.nav_basket:
                        openScreen(BasketActivity.class);
                        break;
                    case R.id.nav_catalogue:
                        openScreen(MainMenuActivity.class);
                        break;
                    case R.id.nav_info:
                        break;
                    case R.id.nav_map:
                        break;
                    case R.id.nav_profile:
                        openScreen(AccountActivity.class);
                        //TODO: switch if user is registered or not
//                        Intent profileIntent = new Intent(MainMenuActivity.this, AccountActivity.class);
//                        startActivityForResult(profileIntent, PICK_USER_REQUEST);
                        break;
                    case R.id.nav_purchases:
                        openScreen(OrdersActivity.class);
                        break;
                    default:
                        break;
                }
                item.setChecked(true);
                mDrawerLayout.closeDrawers();
                return true;
            }
        });
    }

    private void setNewUserHeader() {
        View headerView = mNavigationView.getHeaderView(0);
        headerView.findViewById(R.id.ibAddNewUser).setVisibility(View.VISIBLE);
        headerView.findViewById(R.id.ivUserPhoto).setVisibility(View.GONE);
        headerView.findViewById(R.id.rlUserEmailAndExit).setVisibility(View.GONE);
    }

    private void setUserHeader() {
        View headerView = mNavigationView.getHeaderView(0);
        headerView.findViewById(R.id.ibAddNewUser).setVisibility(View.GONE);
        headerView.findViewById(R.id.ivUserPhoto).setVisibility(View.VISIBLE);
        headerView.findViewById(R.id.rlUserEmailAndExit).setVisibility(View.VISIBLE);
        TextView tvUserEmail = headerView.findViewById(R.id.tvUserEmail);
        tvUserEmail.setText(getUserEmail());
        ImageButton btnSignOut = headerView.findViewById(R.id.ibSignOut);
        btnSignOut.setOnClickListener(btn -> {
            signOut();
            setNewUserHeader();
        });
    }

    private void openScreen(Class activityClass) {
        Intent intent = new Intent();
        intent.setClass(this, activityClass);
        startActivity(intent);
    }

    private void setToolbar() {
        setSupportActionBar(findViewById(R.id.toolbar));
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("Карта магазинов");
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu_white_24dp);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                break;

            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        addMarkers();
        LatLng center = new LatLng(48, 12);
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                Log.d("marker", "position = " + marker.getPosition());
//                CameraPosition position = new CameraPosition.Builder().target(marker.getPosition())
//                                                                      .zoom(15.5f)
//                                                                      .bearing(0)
//                                                                      .tilt(25)
//                                                                      .build();

                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(marker.getPosition(), 15f));
                mMap.animateCamera(CameraUpdateFactory.zoomIn());
                Button btnBack = findViewById(R.id.btnBack);
                FloatingActionButton fab = findViewById(R.id.fab);
                fab.setOnClickListener(btn -> dialWithStore(marker.getSnippet().replace("Тел. ", "")));
                fab.setVisibility(View.VISIBLE);
                btnBack.setVisibility(View.VISIBLE);
                btnBack.setOnClickListener(btn -> {
                    btn.setVisibility(View.GONE);
                    fab.setVisibility(View.GONE);
//                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(center, 15f), 2000, null);
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(center, 4.5f));
                    mMap.animateCamera(CameraUpdateFactory.zoomOut());
                });
                return false;
            }
        });

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(center, 3.5f));
    }

    private void dialWithStore(String phone) {
        Uri number = Uri.parse("tel:" + phone);
        Intent callIntent = new Intent(Intent.ACTION_DIAL, number);
        startActivity(callIntent);
    }

    private void addMarkers() {
        StoresViewModel viewModel = ViewModelProviders.of(this).get(StoresViewModel.class);
        viewModel.getStoreListLiveData().observe(this, storeList -> {
            if (storeList != null) {
                for (StoreModel store : storeList) {
                    mMap.addMarker(new MarkerOptions()
                                           .position(new LatLng(store.geolocation.get("latitude"), store.geolocation.get("longitude")))
                                           .title(store.address)
                                           .snippet("Тел. " + store.phone));
                }
            }
            pbLoadingProgress.setVisibility(View.GONE);
        });
//        mMap.addMarker(new MarkerOptions()
//                               .position(new LatLng(53.931264, 27.646241))
//                               .title("просп. Независимости, 116")
//                               .snippet("Тел. 267-03-88"));
//        mMap.addMarker(new MarkerOptions()
//                               .position(new LatLng(51.523745, -0.158508))
//                               .title("Baker Street, 221B")
//                               .snippet("Тел. 267-03-88"));
//        mMap.addMarker(new MarkerOptions()
//                               .position(new LatLng(41.902276, 12.453362))
//                               .title("Viale Vaticano")
//                               .snippet("Тел. 267-03-88"));
//                               .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_shop)));

//                               .icon(BitmapDescriptorFactory.fromBitmap(((BitmapDrawable)getDrawable(R.drawable.ic_shop)).getBitmap())));


    }
}
