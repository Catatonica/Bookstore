package izenka.hfad.com.bookstore.store_map;


import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class StoresViewModel extends ViewModel{

    private MutableLiveData<List<StoreModel>> storeListLiveData;

    public MutableLiveData<List<StoreModel>> getStoreListLiveData() {
        if(storeListLiveData == null){
            storeListLiveData = new MutableLiveData<>();
            loadStoreList();
        }
        return storeListLiveData;
    }

    private void loadStoreList() {
        DatabaseReference db = FirebaseDatabase.getInstance().getReference("bookstore");
        db.child("store").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<StoreModel> storeList = new ArrayList<>();
                for(DataSnapshot storeDataSnapshot: dataSnapshot.getChildren()){
                    storeList.add(storeDataSnapshot.getValue(StoreModel.class));
                }
                storeListLiveData.postValue(storeList);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

}
