package nhannt.foody.data.source;

import java.util.ArrayList;

import nhannt.foody.data.model.MenuModel;
import nhannt.foody.interfaces.OnCompleteListener;

/**
 * Created by nhannt on 29/11/2017.
 */
public interface MenuDataSource {
    interface Remote {
        void getMenuListOfPlace(String placeCode, OnCompleteListener<ArrayList<MenuModel>>
            callback);
        void getAllMenu(OnCompleteListener<ArrayList<MenuModel>> callback);
    }

    interface Local {
    }
}
