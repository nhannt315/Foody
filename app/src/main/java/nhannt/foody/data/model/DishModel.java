package nhannt.foody.data.model;

/**
 * Created by nhannt on 29/11/2017.
 */
public class DishModel {
    private String mamon;
    private long giatien;
    private String tenmon;
    private String hinhanh;

    public DishModel() {

    }

    public DishModel(String mamon, long giatien, String tenmon, String hinhanh) {
        this.mamon = mamon;
        this.giatien = giatien;
        this.tenmon = tenmon;
        this.hinhanh = hinhanh;
    }

    public String getMamon() {
        return mamon;
    }

    public void setMamon(String mamon) {
        this.mamon = mamon;
    }

    public long getGiatien() {
        return giatien;
    }

    public void setGiatien(long giatien) {
        this.giatien = giatien;
    }

    public String getTenmon() {
        return tenmon;
    }

    public void setTenmon(String tenmon) {
        this.tenmon = tenmon;
    }

    public String getHinhanh() {
        return hinhanh;
    }

    public void setHinhanh(String hinhanh) {
        this.hinhanh = hinhanh;
    }
}
