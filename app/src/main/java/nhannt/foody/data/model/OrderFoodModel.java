package nhannt.foody.data.model;

/**
 * Created by nhannt on 30/11/2017.
 */
public class OrderFoodModel {
    private String dishName;
    private int count;

    public OrderFoodModel() {
    }

    public OrderFoodModel(String dishName, int count) {
        this.dishName = dishName;
        this.count = count;
    }

    public String getDishName() {
        return dishName;
    }

    public void setDishName(String dishName) {
        this.dishName = dishName;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
