package nhannt.foody.screen.placedetail;

import android.net.Uri;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.android.gms.location.places.Place;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

import nhannt.foody.data.model.Comment;
import nhannt.foody.data.model.MenuModel;
import nhannt.foody.data.model.PlaceUtil;
import nhannt.foody.data.model.PlaceWifi;
import nhannt.foody.data.source.CommentRepository;
import nhannt.foody.data.source.MenuRepository;
import nhannt.foody.data.source.PhotoVideoRepository;
import nhannt.foody.data.source.WifiRepository;
import nhannt.foody.interfaces.OnCompleteListener;
import nhannt.foody.interfaces.OnLoadListItemListener;

/**
 * Created by nhannt on 18/11/2017.
 */
public class PlaceDetailPresenter implements PlaceDetailContract.Presenter {
    private PlaceDetailContract.View mView;
    private WifiRepository mWifiRepository;
    private CommentRepository mCommentRepository;
    private PhotoVideoRepository mPhotoVideoRepository;
    private MenuRepository mMenuRepository;

    public PlaceDetailPresenter() {
        mWifiRepository = new WifiRepository();
        mCommentRepository = new CommentRepository();
        mPhotoVideoRepository = new PhotoVideoRepository();
        mMenuRepository = new MenuRepository();
    }

    @Override
    public void setView(PlaceDetailContract.View view) {
        mView = view;
    }

    @Override
    public void onStart() {
    }

    @Override
    public void onStop() {
    }

    @Override
    public void detachView() {
        mView = null;
    }

    @Override
    public void getPlaceImage(String imageTitle) {
        StorageReference storageImage = FirebaseStorage.getInstance().getReference()
            .child("hinhanh").child(imageTitle);
        storageImage.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                mView.setPlaceImage(uri.toString());
            }
        });
    }

    @Override
    public void downloadUtilImage(ArrayList<String> utilsList) {
        if (utilsList == null)
            return;
        for (String util : utilsList) {
            DatabaseReference nodeUtil = FirebaseDatabase.getInstance().getReference().child
                ("quanlytienichs").child(util);
            nodeUtil.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    PlaceUtil util1Model = dataSnapshot.getValue(PlaceUtil.class);
                    StorageReference storageUtils =
                        FirebaseStorage.getInstance().getReference().child
                            ("hinhtienich");
                    storageUtils.child(util1Model.getHinhtienich()).getDownloadUrl()
                        .addOnSuccessListener(
                            new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    mView.appendUtilImage(uri.toString());
                                }
                            });
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            });
        }
    }

    @Override
    public void getWifiList(String placeCode) {
        mWifiRepository.getWifiListOfPlace(placeCode, new OnLoadListItemListener<PlaceWifi>() {
            @Override
            public void onLoadItemComplete(ArrayList<PlaceWifi> list) {
                mView.showListWifi(list);
            }
        });
    }

    @Override
    public void getListComment(String placeCode) {
        mCommentRepository.getCommentListOfPlace(placeCode,
            new OnCompleteListener<ArrayList<Comment>>() {
                @Override
                public void onComplete(@Nullable ArrayList<Comment> result) {
                    if (mView != null)
                        mView.showListComment(result);
                }

                @Override
                public void onError() {
                }
            });
    }

    @Override
    public void getVideoUrl(String link) {
        mPhotoVideoRepository.getVideoDownloadUrl(link, new OnCompleteListener<String>() {
            @Override
            public void onComplete(@Nullable String result) {
                if (mView != null)
                    mView.setVideo(result);
            }

            @Override
            public void onError() {
            }
        });
    }

    @Override
    public void getMenuList(String placeCode) {
        mMenuRepository.getMenuListOfPlace(placeCode,
            new OnCompleteListener<ArrayList<MenuModel>>() {
                @Override
                public void onComplete(@Nullable ArrayList<MenuModel> result) {
                    if (mView != null)
                        mView.setMenuList(result);
                }

                @Override
                public void onError() {
                }
            });
    }
}
