package nhannt.foody.data.model;

/**
 * Created by nhannt on 17/11/2017.
 */
public class Branch {
    private String diachi;
    double latitude, longitude, distanceToCurrent;

    public Branch() {
    }

    public String getDiachi() {
        return diachi;
    }

    public void setDiachi(String diachi) {
        this.diachi = diachi;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getDistanceToCurrent() {
        return distanceToCurrent;
    }

    public void setDistanceToCurrent(double distanceToCurrent) {
        this.distanceToCurrent = distanceToCurrent;
    }
}
