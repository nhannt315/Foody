package nhannt.foody.screen.placedirection;

import android.location.Location;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

import nhannt.foody.screen.BasePresenter;
import nhannt.foody.screen.BaseView;

/**
 * Created by nhannt on 28/11/2017.
 */
public interface PlaceDirectionContract {
    interface View extends BaseView {
        void getDirectionComplete(List<LatLng> latLngsList);
    }

    interface Presenter extends BasePresenter<View> {
        Location getCurrentLocation();
        void getDirection(double latitudeDes, double longitudeDes);
    }
}
