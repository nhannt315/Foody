package nhannt.foody.data.source.remote.googlemaps;

import android.location.Location;

import nhannt.foody.FoodyApplication;
import nhannt.foody.R;
import nhannt.foody.data.source.remote.googlemaps.model.Direction;
import nhannt.foody.data.source.remote.googlemaps.model.Route;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by nhannt on 28/11/2017.
 */
public class GoogleMapApi {
    private GoogleMapDirectionListener mListener;
    private GoogleMapService mGoogleMapService;

    public GoogleMapApi() {
        mGoogleMapService = NetworkClient.provideGoogleMapService();
    }

    private class GoogleMapDirectionCallback implements Callback<Direction> {
        @Override
        public void onResponse(Call<Direction> call, Response<Direction> response) {
            if (response.isSuccessful()) {
                Direction direction = response.body();
                if (direction.getRoutes() != null && direction.getRoutes().size() > 0) {
                    Route route = direction.getRoutes().get(0);
                    if (route.getOverviewPolyline() != null && mListener != null) {
                        mListener.onSuccess(route.getOverviewPolyline().getPoints());
                    }
                }
            }
        }

        @Override
        public void onFailure(Call<Direction> call, Throwable t) {
            if (mListener != null)
                mListener
                    .onError(FoodyApplication.getAppContext().getString(R.string.error_happend));
        }
    }

    public void getPolylinePoints(Location current, Location destination,
                                    GoogleMapDirectionListener listener) {
        mListener = listener;
        String strCurrent = current.getLatitude() + "," + current.getLongitude();
        String strDes = destination.getLatitude() + "," + destination.getLongitude();
        mGoogleMapService.getDirection(strCurrent, strDes)
            .enqueue(new GoogleMapDirectionCallback());
    }
}
