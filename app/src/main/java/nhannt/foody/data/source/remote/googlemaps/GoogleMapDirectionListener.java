package nhannt.foody.data.source.remote.googlemaps;

/**
 * Created by nhannt on 28/11/2017.
 */
public interface GoogleMapDirectionListener {
    void onSuccess(String OverviewPoly);
    void onError(String message);
}
