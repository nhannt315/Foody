package nhannt.foody.data.source.remote;

import android.location.Location;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import nhannt.foody.data.LocationRepository;
import nhannt.foody.data.model.Branch;
import nhannt.foody.data.model.Comment;
import nhannt.foody.data.model.Member;
import nhannt.foody.data.model.Place;
import nhannt.foody.data.source.PlaceDataSource;
import nhannt.foody.interfaces.OnLoadListItemListener;
import nhannt.foody.utils.Utils;

/**
 * Created by nhannt on 09/11/2017.
 */
public class PlaceRemoteDataSource implements PlaceDataSource.RemoteDataSource {
    private DatabaseReference mData = FirebaseDatabase.getInstance().getReference();
    private LocationRepository mLocationRepository = new LocationRepository();

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
                    // Lay danh sach chi nhanh
                    ArrayList<Branch> lstBranch = new ArrayList<>();
                    DataSnapshot snapshotBranch = dataSnapshot.child("chinhanhquanans")
                        .child(dataValuePlace.getKey());
                    for (DataSnapshot dataBranch : snapshotBranch.getChildren()) {
                        Branch branch = dataBranch.getValue(Branch.class);
                        Location branchLocation = new Location("");
                        branchLocation.setLongitude(branch.getLongitude());
                        branchLocation.setLatitude(branch.getLatitude());
                        branch.setDistanceToCurrent(
                            mLocationRepository.getCurrentLocation().distanceTo(branchLocation)/1000
                        );
                        lstBranch.add(branch);
                    }
                    place.setLstBranch(lstBranch);
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
