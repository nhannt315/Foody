package nhannt.foody.screen.eat_where;

import android.location.Location;

import java.util.ArrayList;

import nhannt.foody.data.model.Place;
import nhannt.foody.screen.BasePresenter;
import nhannt.foody.screen.BaseView;

/**
 * Created by nhannt on 09/11/2017.
 */

public interface WhereEatContract {
    interface View extends BaseView{
        void setListPlace(ArrayList<Place> listPlace);
        void showProgress();
        void hideProgress();
        void onGetListFailed();
        int getLoadedItemCount();
    }

    interface Presenter extends BasePresenter<View>{
        void getListPlace();
        Location getCurrentLocation();
    }
}
