package izenka.hfad.com.bookstore.orders;


import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class OrdersViewModel extends ViewModel {
    private static final DatabaseReference ORDERS_REF = FirebaseDatabase.getInstance().getReference("/bookstore/order/-L-5zS8aVAgJZxMWL-ip");

    private final FirebaseQueryLiveData liveData = new FirebaseQueryLiveData(ORDERS_REF);

    @NonNull
    public LiveData<DataSnapshot> getDataSnapshotLiveData() {
        return liveData;
    }
}
