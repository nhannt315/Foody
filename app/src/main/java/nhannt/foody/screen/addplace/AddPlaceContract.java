package nhannt.foody.screen.addplace;

import java.util.ArrayList;

import nhannt.foody.data.model.MenuModel;
import nhannt.foody.data.model.PlaceUtil;
import nhannt.foody.screen.BasePresenter;
import nhannt.foody.screen.BaseView;

/**
 * Created by nhannt on 30/11/2017.
 */
public interface AddPlaceContract {
    interface View extends BaseView {
        void setPlaceList(ArrayList<String> placeList);
        void setMenuList(ArrayList<MenuModel> menuList);
        void setListUtilities(ArrayList<PlaceUtil> listUtilities);
    }

    interface Presenter extends BasePresenter<View> {
        void getAreaList();
        void getMenuList();
        void getListUtilities();
    }
}
