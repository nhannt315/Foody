package nhannt.foody.screen.updatewifi;

import android.support.annotation.Nullable;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import nhannt.foody.data.model.PlaceWifi;
import nhannt.foody.data.source.WifiRepository;
import nhannt.foody.interfaces.OnCompleteListener;
import nhannt.foody.utils.Utils;

/**
 * Created by nhannt on 28/11/2017.
 */
public class UpdateWifiPresenter implements UpdateWifiContract.Presenter {
    private UpdateWifiContract.View mView;
    private WifiRepository mWifiRepository;

    public UpdateWifiPresenter() {
        mWifiRepository = new WifiRepository();
    }

    @Override
    public void setView(UpdateWifiContract.View view) {
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
    public void updateWifi(String placeCode, String wifiName, String wifiPassword) {
        PlaceWifi wifi = new PlaceWifi();
        wifi.setTen(wifiName);
        wifi.setMatkhau(wifiPassword);
        wifi.setNgaydang(Utils.getCurrentDate("dd/MM/yyyy"));
        mWifiRepository.updateNewWifi(placeCode, wifi, new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@Nullable Void result) {
                mView.updateWifiDone();
            }
        });
    }
}
