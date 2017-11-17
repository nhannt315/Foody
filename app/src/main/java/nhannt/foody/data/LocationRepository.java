package nhannt.foody.data;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;

import nhannt.foody.FoodyApplication;

/**
 * Created by nhannt on 17/11/2017.
 */
public class LocationRepository {
    private static final String LOCATION_SHARE_PREF_KEY = "location";
    private static final String LATITUDE_SHARE_PREF_KEY = "latitude";
    private static final String LONGITUDE_SHARE_PREF_KEY = "longitude";
    private SharedPreferences mSharedPreferences;

    public LocationRepository() {
        mSharedPreferences = FoodyApplication.getAppContext().getSharedPreferences
            (LOCATION_SHARE_PREF_KEY, Context.MODE_PRIVATE);
    }

    public void saveLocation(float longitude, float latitude) {
        final SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putFloat(LATITUDE_SHARE_PREF_KEY, latitude);
        editor.putFloat(LONGITUDE_SHARE_PREF_KEY, longitude);
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                editor.commit();
                return null;
            }
        }.execute();
    }

    public float getCurrentLongitude() {
        return mSharedPreferences.getFloat(LONGITUDE_SHARE_PREF_KEY, 0);
    }

    public float getCurrentLatitude() {
        return mSharedPreferences.getFloat(LATITUDE_SHARE_PREF_KEY, 0);
    }
}
