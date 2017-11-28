package nhannt.foody.data.source.remote.googlemaps;

import nhannt.foody.data.source.remote.googlemaps.model.Direction;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by nhannt on 28/11/2017.
 */
public interface GoogleMapService {
    String API_KEY = "AIzaSyA5eg_OEkGsSQt1wRIygQb9LfcCj8fZnKI";
    String DIRECTION_URL = "https://maps.googleapis.com/maps/api/directions/json";

    @GET(DIRECTION_URL)
    Call<Direction> getDirection(@Query("origin") String currentLocation,
                                 @Query("destination") String destination);

}
