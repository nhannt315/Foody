package nhannt.foody.data.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by nhannt on 17/11/2017.
 */
public class Branch implements Parcelable {
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.diachi);
        dest.writeDouble(this.latitude);
        dest.writeDouble(this.longitude);
        dest.writeDouble(this.distanceToCurrent);
    }

    protected Branch(Parcel in) {
        this.diachi = in.readString();
        this.latitude = in.readDouble();
        this.longitude = in.readDouble();
        this.distanceToCurrent = in.readDouble();
    }

    public static final Creator<Branch> CREATOR = new Creator<Branch>() {
        @Override
        public Branch createFromParcel(Parcel source) {
            return new Branch(source);
        }

        @Override
        public Branch[] newArray(int size) {
            return new Branch[size];
        }
    };
}
