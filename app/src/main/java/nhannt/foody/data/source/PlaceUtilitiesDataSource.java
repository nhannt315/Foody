package nhannt.foody.data.source;

import java.util.ArrayList;

import nhannt.foody.data.model.PlaceUtil;
import nhannt.foody.interfaces.OnCompleteListener;

/**
 * Created by nhannt on 30/11/2017.
 */
public interface PlaceUtilitiesDataSource {
    interface Remote {
        void getAllUtilities(OnCompleteListener<ArrayList<PlaceUtil>> callback);
    }
}
