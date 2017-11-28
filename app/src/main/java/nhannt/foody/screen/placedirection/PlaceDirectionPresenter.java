package nhannt.foody.screen.placedirection;

import android.location.Location;

import com.google.maps.android.PolyUtil;

import nhannt.foody.data.source.LocationRepository;
import nhannt.foody.data.source.remote.googlemaps.GoogleMapDirectionListener;

/**
 * Created by nhannt on 28/11/2017.
 */
public class PlaceDirectionPresenter implements PlaceDirectionContract.Presenter {
    private PlaceDirectionContract.View mView;
    private LocationRepository mLocationRepository;

    public PlaceDirectionPresenter(){
        mLocationRepository = new LocationRepository();
    }

    @Override
    public void setView(PlaceDirectionContract.View view) {
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
    public Location getCurrentLocation() {
        return mLocationRepository.getCurrentLocation();
    }

    @Override
    public void getDirection(double latitudeDes, double longitudeDes) {
        Location destination = new Location("");
        destination.setLongitude(longitudeDes);
        destination.setLatitude(latitudeDes);
        Location current = mLocationRepository.getCurrentLocation();
        mLocationRepository.getGoogleMapDirection(current, destination,
            new GoogleMapDirectionListener() {
                @Override
                public void onSuccess(String OverviewPoly) {
                    if(mView != null)
                        mView.getDirectionComplete(PolyUtil.decode(OverviewPoly));
                }

                @Override
                public void onError(String message) {
                }
            });
    }
}
