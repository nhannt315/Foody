package nhannt.foody.data.source;

import java.util.ArrayList;

import nhannt.foody.data.model.PlaceUtil;
import nhannt.foody.data.source.remote.PlaceUtilitiesRemoteDataSource;
import nhannt.foody.interfaces.OnCompleteListener;

/**
 * Created by nhannt on 30/11/2017.
 */
public class PlaceUtilitiesRepository implements PlaceUtilitiesDataSource.Remote {

    private PlaceUtilitiesRemoteDataSource mRemoteDataSource;

    public PlaceUtilitiesRepository(){
        mRemoteDataSource = new PlaceUtilitiesRemoteDataSource();
    }

    @Override
    public void getAllUtilities(OnCompleteListener<ArrayList<PlaceUtil>> callback) {
        mRemoteDataSource.getAllUtilities(callback);
    }
}
