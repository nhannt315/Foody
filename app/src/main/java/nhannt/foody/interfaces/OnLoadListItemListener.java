package nhannt.foody.interfaces;

import java.util.ArrayList;

/**
 * Created by nhannt on 09/11/2017.
 */
public interface OnLoadListItemListener<T> {
    void onLoadItemComplete(ArrayList<T> list);
}
