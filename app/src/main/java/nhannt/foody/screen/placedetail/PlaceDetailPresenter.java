package nhannt.foody.screen.placedetail;

import android.net.Uri;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

/**
 * Created by nhannt on 18/11/2017.
 */
public class PlaceDetailPresenter implements PlaceDetailContract.Presenter {

    private PlaceDetailContract.View mView;

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
}
