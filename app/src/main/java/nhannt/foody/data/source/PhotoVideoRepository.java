package nhannt.foody.data.source;

import java.util.ArrayList;

import nhannt.foody.data.model.ImageSelect;
import nhannt.foody.data.source.local.PhotoVideoLocalDataSource;
import nhannt.foody.data.source.remote.PhotoVideoRemoteDataSource;
import nhannt.foody.interfaces.OnCompleteListener;
import nhannt.foody.interfaces.OnLoadListItemListener;

/**
 * Created by nhannt on 29/11/2017.
 */
public class PhotoVideoRepository
    implements PhotoVideoDataSource.Local, PhotoVideoDataSource.Remote {
    private PhotoVideoLocalDataSource mPhotoVideoLocalDataSource;
    private PhotoVideoRemoteDataSource mPhotoVideoRemoteDataSource;

    public PhotoVideoRepository() {
        mPhotoVideoRemoteDataSource = new PhotoVideoRemoteDataSource();
        mPhotoVideoLocalDataSource = new PhotoVideoLocalDataSource();
    }

    @Override
    public void getAllImageFromLocalStorage(OnLoadListItemListener<ImageSelect> listener) {
        mPhotoVideoLocalDataSource.getAllImageFromLocalStorage(listener);
    }

    @Override
    public void cancel() {
        mPhotoVideoLocalDataSource.cancel();
    }

    @Override
    public void uploadListImage(ArrayList<String> lstImage, OnCompleteListener<Void> listener) {
        mPhotoVideoRemoteDataSource.uploadListImage(lstImage, listener);
    }
}
