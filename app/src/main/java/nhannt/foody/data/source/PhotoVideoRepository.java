package nhannt.foody.data.source;

import nhannt.foody.data.model.ImageSelect;
import nhannt.foody.data.source.local.PhotoVideoLocalDataSource;
import nhannt.foody.interfaces.OnLoadListItemListener;

/**
 * Created by nhannt on 29/11/2017.
 */
public class PhotoVideoRepository implements PhotoVideoDataSource.Local {
    private PhotoVideoLocalDataSource mPhotoVideoLocalDataSource;

    public PhotoVideoRepository() {
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
}
