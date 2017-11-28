package nhannt.foody.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by nhannt on 12/11/2017.
 */
public class Comment implements Parcelable {
    private String mabinhluan;
    private double chamdiem;
    private long luotthich;
    private Member user;
    private String noidung, tieude, mauser;
    private ArrayList<String> listImage;

    public Comment() {
    }


    public String getMabinhluan() {
        return mabinhluan;
    }

    public void setMabinhluan(String mabinhluan) {
        this.mabinhluan = mabinhluan;
    }

    public ArrayList<String> getListImage() {
        return listImage;
    }

    public void setListImage(ArrayList<String> listImage) {
        this.listImage = listImage;
    }

    public String getMauser() {
        return mauser;
    }

    public void setMauser(String mauser) {
        this.mauser = mauser;
    }

    public double getChamdiem() {
        return chamdiem;
    }

    public void setChamdiem(double chamdiem) {
        this.chamdiem = chamdiem;
    }

    public long getLuotthich() {
        return luotthich;
    }

    public void setLuotthich(long luotthich) {
        this.luotthich = luotthich;
    }

    public Member getUser() {
        return user;
    }

    public void setUser(Member user) {
        this.user = user;
    }

    public String getNoidung() {
        return noidung;
    }

    public void setNoidung(String noidung) {
        this.noidung = noidung;
    }

    public String getTieude() {
        return tieude;
    }

    public void setTieude(String tieude) {
        this.tieude = tieude;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mabinhluan);
        dest.writeDouble(this.chamdiem);
        dest.writeLong(this.luotthich);
        dest.writeParcelable(this.user, flags);
        dest.writeString(this.noidung);
        dest.writeString(this.tieude);
        dest.writeString(this.mauser);
        dest.writeStringList(this.listImage);
    }

    protected Comment(Parcel in) {
        this.mabinhluan = in.readString();
        this.chamdiem = in.readDouble();
        this.luotthich = in.readLong();
        this.user = in.readParcelable(Member.class.getClassLoader());
        this.noidung = in.readString();
        this.tieude = in.readString();
        this.mauser = in.readString();
        this.listImage = in.createStringArrayList();
    }

    public static final Creator<Comment> CREATOR = new Creator<Comment>() {
        @Override
        public Comment createFromParcel(Parcel source) {
            return new Comment(source);
        }

        @Override
        public Comment[] newArray(int size) {
            return new Comment[size];
        }
    };
}
