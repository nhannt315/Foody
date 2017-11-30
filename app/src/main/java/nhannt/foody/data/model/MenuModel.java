package nhannt.foody.data.model;

import java.util.ArrayList;

/**
 * Created by nhannt on 29/11/2017.
 */
public class MenuModel {
    private String mathucdon, tenthucdon;
    private ArrayList<DishModel> mDishList;

    public MenuModel() {
    }

    public MenuModel(String mathucdon, String tenthucdon) {
        this.mathucdon = mathucdon;
        this.tenthucdon = tenthucdon;
    }

    public ArrayList<DishModel> getDishList() {
        return mDishList;
    }

    public void setDishList(ArrayList<DishModel> dishList) {
        mDishList = dishList;
    }

    public String getMathucdon() {
        return mathucdon;
    }

    public void setMathucdon(String mathucdon) {
        this.mathucdon = mathucdon;
    }

    public String getTenthucdon() {
        return tenthucdon;
    }

    public void setTenthucdon(String tenthucdon) {
        this.tenthucdon = tenthucdon;
    }
}
