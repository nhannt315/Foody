package nhannt.foody.screen.wifi;

import android.content.Context;
import android.widget.Toast;

import java.util.ArrayList;

import nhannt.foody.data.model.PlaceWifi;
import nhannt.foody.data.source.WifiRepository;
import nhannt.foody.interfaces.OnLoadListItemListener;

/**
 * Created by nhannt on 28/11/2017.
 */
public class WifiDetailPresenter implements WifiDetailContract.Presenter {
    private WifiDetailContract.View mView;
    private WifiRepository mWifiRepository;

    public WifiDetailPresenter() {
        mWifiRepository = new WifiRepository();
    }

    @Override
    public void setView(WifiDetailContract.View view) {
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
    public void getWifiList(String placeCode) {
        if(mView == null)
            return;
        mView.showProgress();

        mWifiRepository.getWifiListOfPlace(placeCode, new OnLoadListItemListener<PlaceWifi>() {
            @Override
            public void onLoadItemComplete(ArrayList<PlaceWifi> list) {

                mView.setWifiList(list);
                mView.hideProgress();
            }
        });
    }
}
