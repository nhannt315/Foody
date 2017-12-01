package nhannt.foody.data.source;

import java.util.ArrayList;

import nhannt.foody.data.model.MenuModel;
import nhannt.foody.data.source.remote.MenuRemoteDataSource;
import nhannt.foody.interfaces.OnCompleteListener;

/**
 * Created by nhannt on 29/11/2017.
 */
public class MenuRepository implements MenuDataSource.Remote, MenuDataSource.Local {
    private MenuRemoteDataSource mMenuRemoteDataSource;

    public MenuRepository() {
        mMenuRemoteDataSource = new MenuRemoteDataSource();
    }

    @Override
    public void getMenuListOfPlace(String placeCode,
                                   OnCompleteListener<ArrayList<MenuModel>> callback) {
        mMenuRemoteDataSource.getMenuListOfPlace(placeCode, callback);
    }

    @Override
    public void getAllMenu(OnCompleteListener<ArrayList<MenuModel>> callback) {
        mMenuRemoteDataSource.getAllMenu(callback);
    }
}
