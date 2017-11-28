package nhannt.foody.data.source;

import nhannt.foody.data.model.PlaceWifi;
import nhannt.foody.interfaces.OnCompleteListener;
import nhannt.foody.interfaces.OnLoadListItemListener;

/**
 * Created by nhannt on 27/11/2017.
 */
public interface WifiDataSource {
    interface Remote {
        void getWifiListOfPlace(String placeCode, OnLoadListItemListener<PlaceWifi> callback);
        void updateNewWifi(String placeCode, PlaceWifi wifi, OnCompleteListener<Void> callback);
    }
}
