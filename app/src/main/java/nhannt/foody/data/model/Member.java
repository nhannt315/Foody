package nhannt.foody.data.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by nhannt on 09/11/2017.
 */
public class Member implements Parcelable {
    private String hoten,hinhanh,mathanhvien;

    public Member() {
    }

    public Member(String hoten, String hinhanh) {
        this.hoten = hoten;
        this.hinhanh = hinhanh;
    }

    public Member(String hoten, String hinhanh, String mathanhvien) {
        this.hoten = hoten;
        this.hinhanh = hinhanh;
        this.mathanhvien = mathanhvien;
    }



    public String getHoten() {
        return hoten;
    }

    public void setHoten(String hoten) {
        this.hoten = hoten;
    }

    public String getHinhanh() {
        return hinhanh;
    }

    public void setHinhanh(String hinhanh) {
        this.hinhanh = hinhanh;
    }

    public String getMathanhvien() {
        return mathanhvien;
    }

    public void setMathanhvien(String mathanhvien) {
        this.mathanhvien = mathanhvien;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.hoten);
        dest.writeString(this.hinhanh);
        dest.writeString(this.mathanhvien);
    }

    protected Member(Parcel in) {
        this.hoten = in.readString();
        this.hinhanh = in.readString();
        this.mathanhvien = in.readString();
    }

    public static final Creator<Member> CREATOR = new Creator<Member>() {
        @Override
        public Member createFromParcel(Parcel source) {
            return new Member(source);
        }

        @Override
        public Member[] newArray(int size) {
            return new Member[size];
        }
    };
}
