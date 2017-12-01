package nhannt.foody.data.model;

/**
 * Created by nhannt on 01/12/2017.
 */
public class AddMenuModel {
    String mMenuCode;
    DishModel mDishModel;

    public AddMenuModel() {
    }

    public AddMenuModel(String menuCode,  DishModel dishModel) {
        mMenuCode = menuCode;
        mDishModel = dishModel;
    }

    public String getMenuCode() {
        return mMenuCode;
    }

    public void setMenuCode(String menuCode) {
        mMenuCode = menuCode;
    }


    public DishModel getDishModel() {
        return mDishModel;
    }

    public void setDishModel(DishModel dishModel) {
        mDishModel = dishModel;
    }
}
