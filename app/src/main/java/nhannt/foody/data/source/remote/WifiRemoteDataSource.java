package nhannt.foody.data.source.remote;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import nhannt.foody.data.model.PlaceWifi;
import nhannt.foody.data.source.WifiDataSource;
import nhannt.foody.interfaces.OnCompleteListener;
import nhannt.foody.interfaces.OnLoadListItemListener;

/**
 * Created by nhannt on 27/11/2017.
 */
public class WifiRemoteDataSource implements WifiDataSource.Remote {
    @Override
    public void getWifiListOfPlace(String placeCode,
                                   final OnLoadListItemListener<PlaceWifi> callback) {
        final ArrayList<PlaceWifi> wifiArrayList = new ArrayList<>();
        DatabaseReference nodeWifi = FirebaseDatabase.getInstance().getReference().child
            ("wifiquanans").child(placeCode);
        nodeWifi.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot dataWifi : dataSnapshot.getChildren()) {
                    PlaceWifi wifi = dataWifi.getValue(PlaceWifi.class);
                    wifiArrayList.add(wifi);
                }
                callback.onLoadItemComplete(wifiArrayList);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    @Override
    public void updateNewWifi(String placeCode, PlaceWifi wifi, final OnCompleteListener<Void> callback) {
        DatabaseReference dataNodeWifi = FirebaseDatabase.getInstance().getReference().child
            ("wifiquanans").child(placeCode);
        dataNodeWifi.push().setValue(wifi, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError,
                                   DatabaseReference databaseReference) {
                callback.onComplete(null);
            }
        });
    }
}
