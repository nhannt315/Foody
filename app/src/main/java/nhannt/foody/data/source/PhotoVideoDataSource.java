package nhannt.foody.data.source;

import java.util.ArrayList;

import nhannt.foody.data.model.ImageSelect;
import nhannt.foody.interfaces.OnLoadListItemListener;

/**
 * Created by nhannt on 29/11/2017.
 */
public interface PhotoVideoDataSource {
    interface Local {
        void getAllImageFromLocalStorage(OnLoadListItemListener<ImageSelect> listener);
        void cancel();
    }

    interface Remote {
    }
}
