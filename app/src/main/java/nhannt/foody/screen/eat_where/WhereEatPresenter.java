package nhannt.foody.screen.eat_where;

import android.location.Location;

import java.util.ArrayList;

import nhannt.foody.data.source.LocationRepository;
import nhannt.foody.data.model.Place;
import nhannt.foody.data.source.PlaceRepository;
import nhannt.foody.interfaces.OnLoadListItemListener;

/**
 * Created by nhannt on 09/11/2017.
 */
public class WhereEatPresenter implements WhereEatContract.Presenter {
    private WhereEatContract.View mView;
    private PlaceRepository mPlaceRepository;
    private LocationRepository mLocationRepository;

    public WhereEatPresenter(PlaceRepository placeRepository,
                             LocationRepository locationRepository) {
        mPlaceRepository = placeRepository;
        mLocationRepository = locationRepository;
    }

    @Override
    public void setView(WhereEatContract.View view) {
        mView = view;
    }

    @Override
    public Location getCurrentLocation() {
        Location currentLocation = new Location("");
        currentLocation.setLatitude(Double.parseDouble(mLocationRepository.getCurrentLatitude()));
        currentLocation.setLongitude(Double.parseDouble(mLocationRepository.getCurrentLongitude()));
        return currentLocation;
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
    public void getListPlace() {
        mView.showProgress();
        mPlaceRepository.getListPlace(new OnLoadListItemListener<Place>() {
            @Override
            public void onLoadItemComplete(ArrayList<Place> list) {
                mView.setListPlace(list);
                mView.hideProgress();
            }
        }, 3 + mView.getLoadedItemCount(), mView.getLoadedItemCount());
    }
}
