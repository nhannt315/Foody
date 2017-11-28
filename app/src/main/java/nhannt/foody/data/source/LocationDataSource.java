package nhannt.foody.data.source;

import android.location.Location;

import nhannt.foody.data.source.remote.googlemaps.GoogleMapDirectionListener;

/**
 * Created by nhannt on 28/11/2017.
 */
public interface LocationDataSource {
    interface Local {
        void saveLocation(double longitude, double latitude);
        Location getCurrentLocation();
        String getCurrentLongitude();
        String getCurrentLatitude();
    }

    interface Remote {
        void getGoogleMapDirection(Location current, Location destination,
                                   GoogleMapDirectionListener listener);
    }
}
