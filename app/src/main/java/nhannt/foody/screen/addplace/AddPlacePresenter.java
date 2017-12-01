package nhannt.foody.screen.addplace;

import android.support.annotation.Nullable;

import java.util.ArrayList;

import nhannt.foody.data.model.MenuModel;
import nhannt.foody.data.model.PlaceUtil;
import nhannt.foody.data.source.MenuRepository;
import nhannt.foody.data.source.PlaceRepository;
import nhannt.foody.data.source.PlaceUtilitiesRepository;
import nhannt.foody.data.source.remote.PlaceRemoteDataSource;
import nhannt.foody.interfaces.OnCompleteListener;
import nhannt.foody.screen.addcomment.AddCommentContract;

/**
 * Created by nhannt on 30/11/2017.
 */
public class AddPlacePresenter implements AddPlaceContract.Presenter {
    private AddPlaceContract.View mView;
    private PlaceRepository mPlaceRepository;
    private MenuRepository mMenuRepository;
    private PlaceUtilitiesRepository mPlaceUtilitiesRepository;

    public AddPlacePresenter() {
        mPlaceRepository = new PlaceRepository(null, new PlaceRemoteDataSource());
        mMenuRepository = new MenuRepository();
        mPlaceUtilitiesRepository = new PlaceUtilitiesRepository();
    }

    @Override
    public void setView(AddPlaceContract.View view) {
        mView = view;
    }

    @Override
    public void onStart() {
    }

    @Override
    public void onStop() {
    }

    @Override
    public void detachView() {
        mView = null;
    }

    @Override
    public void getAreaList() {
        mPlaceRepository.getAreaList(new OnCompleteListener<ArrayList<String>>() {
            @Override
            public void onComplete(@Nullable ArrayList<String> result) {
                if (mView != null)
                    mView.setPlaceList(result);
            }

            @Override
            public void onError() {
            }
        });
    }

    @Override
    public void getMenuList() {
        mMenuRepository.getAllMenu(new OnCompleteListener<ArrayList<MenuModel>>() {
            @Override
            public void onComplete(@Nullable ArrayList<MenuModel> result) {
                if (mView != null)
                    mView.setMenuList(result);
            }

            @Override
            public void onError() {
            }
        });
    }

    @Override
    public void getListUtilities() {
        mPlaceUtilitiesRepository.getAllUtilities(new OnCompleteListener<ArrayList<PlaceUtil>>() {
            @Override
            public void onComplete(@Nullable ArrayList<PlaceUtil> result) {
                if(mView != null)
                    mView.setListUtilities(result);
            }

            @Override
            public void onError() {
            }
        });
    }
}
