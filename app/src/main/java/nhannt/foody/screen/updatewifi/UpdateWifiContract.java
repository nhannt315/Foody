package nhannt.foody.screen.updatewifi;

import nhannt.foody.screen.BasePresenter;
import nhannt.foody.screen.BaseView;

/**
 * Created by nhannt on 28/11/2017.
 */
public interface UpdateWifiContract {
    interface View extends BaseView {
        void showProgress();
        void hideProgress();
        void updateWifiDone();
    }

    interface Presenter extends BasePresenter<View> {
        void updateWifi(String placeCode, String wifiName, String wifiPassword);
    }
}
