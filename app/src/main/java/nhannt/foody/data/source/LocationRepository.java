package nhannt.foody.data.source;

import android.content.Context;
import android.content.SharedPreferences;
import android.location.Location;
import android.os.AsyncTask;

import nhannt.foody.FoodyApplication;
import nhannt.foody.data.source.local.LocationLocalDataSource;
import nhannt.foody.data.source.remote.LocationRemoteDataSource;
import nhannt.foody.data.source.remote.googlemaps.GoogleMapApi;
import nhannt.foody.data.source.remote.googlemaps.GoogleMapDirectionListener;

/**
 * Created by nhannt on 17/11/2017.
 */
public class LocationRepository implements LocationDataSource.Local, LocationDataSource.Remote {
    private LocationLocalDataSource mLocationLocalDataSource;
    private LocationRemoteDataSource mLocationRemoteDataSource;

    public LocationRepository() {
        mLocationLocalDataSource = new LocationLocalDataSource();
        mLocationRemoteDataSource = new LocationRemoteDataSource();
    }

    @Override
    public void saveLocation(double longitude, double latitude) {
        mLocationLocalDataSource.saveLocation(longitude, latitude);
    }

    @Override
    public Location getCurrentLocation() {
        return mLocationLocalDataSource.getCurrentLocation();
    }

    @Override
    public String getCurrentLongitude() {
        return mLocationLocalDataSource.getCurrentLongitude();
    }

    @Override
    public String getCurrentLatitude() {
        return mLocationLocalDataSource.getCurrentLatitude();
    }

    @Override
    public void getGoogleMapDirection(Location current, Location destination,
                                      GoogleMapDirectionListener listener) {
        mLocationRemoteDataSource.getGoogleMapDirection(current, destination, listener);
    }
}
