package nhannt.foody.data.source;

import nhannt.foody.data.model.PlaceWifi;
import nhannt.foody.data.source.remote.WifiRemoteDataSource;
import nhannt.foody.interfaces.OnCompleteListener;
import nhannt.foody.interfaces.OnLoadListItemListener;

/**
 * Created by nhannt on 27/11/2017.
 */
public class WifiRepository implements WifiDataSource.Remote {
    private WifiRemoteDataSource mWifiRemoteDataSource;

    public WifiRepository() {
        mWifiRemoteDataSource = new WifiRemoteDataSource();
    }

    @Override
    public void getWifiListOfPlace(String placeCode, OnLoadListItemListener<PlaceWifi> callback) {
        mWifiRemoteDataSource.getWifiListOfPlace(placeCode, callback);
    }

    @Override
    public void updateNewWifi(String placeCode, PlaceWifi wifi, OnCompleteListener<Void> callback) {
        mWifiRemoteDataSource.updateNewWifi(placeCode, wifi, callback);
    }
}
