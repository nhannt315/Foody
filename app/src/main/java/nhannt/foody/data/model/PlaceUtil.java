package nhannt.foody.data.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by nhannt on 27/11/2017.
 */
public class PlaceUtil implements Parcelable {
    private String hinhtienich, tentienich, matienich;

    public PlaceUtil() {
    }

    public String getMatienich() {
        return matienich;
    }

    public void setMatienich(String matienich) {
        this.matienich = matienich;
    }

    public String getHinhtienich() {
        return hinhtienich;
    }

    public void setHinhtienich(String hinhtienich) {
        this.hinhtienich = hinhtienich;
    }

    public String getTentienich() {
        return tentienich;
    }

    public void setTentienich(String tentienich) {
        this.tentienich = tentienich;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.hinhtienich);
        dest.writeString(this.tentienich);
        dest.writeString(this.matienich);
    }

    protected PlaceUtil(Parcel in) {
        this.hinhtienich = in.readString();
        this.tentienich = in.readString();
        this.matienich = in.readString();
    }

    public static final Creator<PlaceUtil> CREATOR = new Creator<PlaceUtil>() {
        @Override
        public PlaceUtil createFromParcel(Parcel source) {
            return new PlaceUtil(source);
        }

        @Override
        public PlaceUtil[] newArray(int size) {
            return new PlaceUtil[size];
        }
    };
}
