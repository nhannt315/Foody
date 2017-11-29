package nhannt.foody.screen.selectimage;

import android.util.Log;

import java.util.ArrayList;

import nhannt.foody.data.model.ImageSelect;
import nhannt.foody.data.source.PhotoVideoRepository;
import nhannt.foody.interfaces.OnLoadListItemListener;

/**
 * Created by nhannt on 29/11/2017.
 */
public class SelectImagePresenter implements SelectImageContract.Presenter {
    private SelectImageContract.View mView;
    private PhotoVideoRepository mPhotoVideoRepository;

    public SelectImagePresenter() {
        mPhotoVideoRepository = new PhotoVideoRepository();
    }

    @Override
    public void setView(SelectImageContract.View view) {
        mView = view;
    }

    @Override
    public void onStart() {
    }

    @Override
    public void onStop() {
        mPhotoVideoRepository.cancel();
    }

    @Override
    public void detachView() {
        mView = null;
    }

    @Override
    public void getAllImage() {
        mPhotoVideoRepository.getAllImageFromLocalStorage(
            new OnLoadListItemListener<ImageSelect>() {
                @Override
                public void onLoadItemComplete(ArrayList<ImageSelect> list) {
                    mView.setImage(list);
                }
            });
    }
}
