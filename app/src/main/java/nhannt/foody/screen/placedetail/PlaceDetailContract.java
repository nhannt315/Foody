package nhannt.foody.screen.placedetail;

import java.util.ArrayList;

import nhannt.foody.data.model.Comment;
import nhannt.foody.data.model.Place;
import nhannt.foody.data.model.PlaceWifi;
import nhannt.foody.screen.BasePresenter;
import nhannt.foody.screen.BaseView;

/**
 * Created by nhannt on 18/11/2017.
 */
public interface PlaceDetailContract {
    interface View extends BaseView {
        void setPlaceImage(String url);
        void appendUtilImage(String url);
        void showListWifi(ArrayList<PlaceWifi> lstWifi);
        void showListComment(ArrayList<Comment> lstComment);
    }

    interface Presenter extends BasePresenter<View> {
        void getPlaceImage(String imageTitle);
        void downloadUtilImage(ArrayList<String> utilsList);
        void getWifiList(String placeCode);
        void getListComment(String placeCode);
    }
}
