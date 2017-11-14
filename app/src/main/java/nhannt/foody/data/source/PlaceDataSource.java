package nhannt.foody.data.source;

import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import nhannt.foody.data.model.Place;
import nhannt.foody.interfaces.OnLoadListItemListener;

/**
 * Created by nhannt on 09/11/2017.
 */
public interface PlaceDataSource {
    interface LocalDataSource {
    }

    interface RemoteDataSource {
        void getListPlace(OnLoadListItemListener<Place> listener);
    }
}
