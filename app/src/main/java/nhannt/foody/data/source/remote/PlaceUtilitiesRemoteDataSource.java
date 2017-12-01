package nhannt.foody.data.source.remote;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import nhannt.foody.data.model.PlaceUtil;
import nhannt.foody.data.source.PlaceUtilitiesDataSource;
import nhannt.foody.interfaces.OnCompleteListener;

/**
 * Created by nhannt on 30/11/2017.
 */
public class PlaceUtilitiesRemoteDataSource implements PlaceUtilitiesDataSource.Remote {
    @Override
    public void getAllUtilities(final OnCompleteListener<ArrayList<PlaceUtil>> callback) {
        FirebaseDatabase.getInstance().getReference().child("quanlytienichs")
            .addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    ArrayList<PlaceUtil> utilArrayList = new ArrayList<>();
                    for (DataSnapshot valueUtil : dataSnapshot.getChildren()) {
                        String utilCode = valueUtil.getKey();
                        PlaceUtil placeUtil = valueUtil.getValue(PlaceUtil.class);
                        placeUtil.setMatienich(utilCode);
                        utilArrayList.add(placeUtil);
                    }
                    callback.onComplete(utilArrayList);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    callback.onError();
                }
            });
    }
}
