package nhannt.foody.data.source.remote;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import nhannt.foody.data.model.Comment;
import nhannt.foody.data.model.Member;
import nhannt.foody.data.model.Place;
import nhannt.foody.data.source.PlaceDataSource;
import nhannt.foody.interfaces.OnLoadListItemListener;

/**
 * Created by nhannt on 09/11/2017.
 */
public class PlaceRemoteDataSource implements PlaceDataSource.RemoteDataSource {
    private DatabaseReference mData = FirebaseDatabase.getInstance().getReference();

    @Override
    public void getListPlace(final OnLoadListItemListener<Place> listener) {
        final List<Place> lstPlace = new ArrayList<>();
        mData.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                DataSnapshot dataSnapshotPlace = dataSnapshot.child("quanans");
                // Lay danh sach quan an
                for (DataSnapshot dataValuePlace : dataSnapshotPlace.getChildren()) {
                    Place place = dataValuePlace.getValue(Place.class);
                    ArrayList<String> lstImage = new ArrayList<>();
                    // Lay danh sach hinh anh cua quan an theo ma
                    DataSnapshot dataSnapshotImage = dataSnapshot.child("hinhanhquanans").child
                        (dataValuePlace.getKey());
                    for (DataSnapshot dataValueImage : dataSnapshotImage.getChildren()) {
                        lstImage.add(dataValueImage.getValue(String.class));
                    }
                    place.setHinhanhquanan(lstImage);
                    // lay danh sach binh luan cua quan an
                    DataSnapshot snapshotComment = dataSnapshot.child("binhluans").child
                        (dataValuePlace.getKey());
                    ArrayList<Comment> lstComment = new ArrayList<>();
                    for (DataSnapshot valueComment : snapshotComment.getChildren()) {
                        Comment comment = valueComment.getValue(Comment.class);
                        comment.setMabinhluan(valueComment.getKey());
                        Member member = dataSnapshot.child("thanhviens").child(comment.getMauser
                            ()).getValue(Member.class);
                        comment.setUser(member);
                        ArrayList<String> listImage = new ArrayList<>();
                        DataSnapshot dataImageComment = dataSnapshot.child("hinhanhbinhluans").child
                            (comment.getMabinhluan());
                        for (DataSnapshot valueImageComment : dataImageComment.getChildren()) {
                            listImage.add(valueImageComment.getValue(String.class));
                        }
                        comment.setListImage(listImage);
                        lstComment.add(comment);
                    }
                    place.setBinhluanList(lstComment);
                    lstPlace.add(place);
                }
                listener.onLoadItemComplete((ArrayList<Place>) lstPlace);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }
}
