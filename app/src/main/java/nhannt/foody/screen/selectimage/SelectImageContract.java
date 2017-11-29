package nhannt.foody.screen.selectimage;

import java.util.ArrayList;

import nhannt.foody.data.model.ImageSelect;
import nhannt.foody.screen.BasePresenter;
import nhannt.foody.screen.BaseView;

/**
 * Created by nhannt on 29/11/2017.
 */
public interface SelectImageContract {
    interface View extends BaseView {
        boolean checkReadStoragePermission();
        void requirePermission();
        void setImage(ArrayList<ImageSelect> listImage);
    }

    interface Presenter extends BasePresenter<View> {
        void getAllImage();
    }
}
