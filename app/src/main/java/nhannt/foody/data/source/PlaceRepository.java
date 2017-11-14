package nhannt.foody.data.source;

import com.google.firebase.database.ValueEventListener;

import nhannt.foody.data.model.Place;
import nhannt.foody.interfaces.OnLoadListItemListener;

/**
 * Created by nhannt on 09/11/2017.
 */
public class PlaceRepository implements PlaceDataSource.RemoteDataSource, PlaceDataSource.LocalDataSource {

    private PlaceDataSource.LocalDataSource mLocalDataSource;
    private PlaceDataSource.RemoteDataSource mRemoteDataSource;

    public PlaceRepository(PlaceDataSource.LocalDataSource localDataSource,
                           PlaceDataSource.RemoteDataSource remoteDataSource) {
        mLocalDataSource = localDataSource;
        mRemoteDataSource = remoteDataSource;
    }

    @Override
    public void getListPlace(OnLoadListItemListener<Place> listener) {
        mRemoteDataSource.getListPlace(listener);
    }
}
