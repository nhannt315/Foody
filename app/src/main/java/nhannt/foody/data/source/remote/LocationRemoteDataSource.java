package nhannt.foody.data.source.remote;

import android.location.Location;

import nhannt.foody.data.source.LocationDataSource;
import nhannt.foody.data.source.remote.googlemaps.GoogleMapApi;
import nhannt.foody.data.source.remote.googlemaps.GoogleMapDirectionListener;

/**
 * Created by nhannt on 28/11/2017.
 */
public class LocationRemoteDataSource implements LocationDataSource.Remote {
    @Override
    public void getGoogleMapDirection(Location current, Location destination,
                                      GoogleMapDirectionListener listener) {
        new GoogleMapApi().getPolylinePoints(current, destination, listener);
    }
}
