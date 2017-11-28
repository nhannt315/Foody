package nhannt.foody.data.source.local;

import android.content.Context;
import android.content.SharedPreferences;
import android.location.Location;
import android.os.AsyncTask;

import nhannt.foody.FoodyApplication;
import nhannt.foody.data.source.LocationDataSource;

/**
 * Created by nhannt on 28/11/2017.
 */
public class LocationLocalDataSource implements LocationDataSource.Local {

    private static final String LOCATION_SHARE_PREF_KEY = "location";
    private static final String LATITUDE_SHARE_PREF_KEY = "latitude";
    private static final String LONGITUDE_SHARE_PREF_KEY = "longitude";
    private SharedPreferences mSharedPreferences;

    public LocationLocalDataSource(){
        mSharedPreferences = FoodyApplication.getAppContext().getSharedPreferences
            (LOCATION_SHARE_PREF_KEY, Context.MODE_PRIVATE);
    }

    @Override
    public String getCurrentLongitude() {
        return mSharedPreferences.getString(LONGITUDE_SHARE_PREF_KEY, "0");
    }

    @Override
    public String getCurrentLatitude() {
        return mSharedPreferences.getString(LATITUDE_SHARE_PREF_KEY, "0");
    }

    @Override
    public void saveLocation(double longitude, double latitude) {
        final SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString(LATITUDE_SHARE_PREF_KEY, String.valueOf(latitude));
        editor.putString(LONGITUDE_SHARE_PREF_KEY, String.valueOf(longitude));
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                editor.commit();
                return null;
            }
        }.execute();
    }

    @Override
    public Location getCurrentLocation() {
        Location location = new Location("");
        location.setLatitude(Double.parseDouble(getCurrentLatitude()));
        location.setLongitude(Double.parseDouble(getCurrentLongitude()));
        return location;
    }
}
