package nhannt.foody.screen.wifi;

import java.util.ArrayList;

import nhannt.foody.data.model.PlaceWifi;
import nhannt.foody.screen.BasePresenter;
import nhannt.foody.screen.BaseView;

/**
 * Created by nhannt on 28/11/2017.
 */
public interface WifiDetailContract {
    interface View extends BaseView{
        void setWifiList(ArrayList<PlaceWifi> listWifi);
        void showProgress();
        void hideProgress();
    }

    interface Presenter extends BasePresenter<View>{
        void getWifiList(String placeCode);
    }
}
