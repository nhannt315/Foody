package nhannt.foody.screen.placedetail;

import nhannt.foody.screen.BasePresenter;
import nhannt.foody.screen.BaseView;

/**
 * Created by nhannt on 18/11/2017.
 */
public interface PlaceDetailContract {
    interface View extends BaseView {
        void setPlaceImage(String url);
    }

    interface Presenter extends BasePresenter<View> {
        void getPlaceImage(String imageTitle);
    }
}
