package nhannt.foody.data.model;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by nhannt on 09/11/2017.
 */
public class Place implements Parcelable {
    private boolean giaohang;
    private String maquanan;
    private String giomocua;
    private String giodongcua;
    private String tenquanan;
    private String videogioithieu;
    private ArrayList<String> tienich;
    private ArrayList<String> hinhanhquanan;
    private ArrayList<Comment> binhluanList;
    private long luotthich;
    private ArrayList<Branch> lstBranch;
    private ArrayList<Bitmap> lstImageBitmap;

    public Place() {
    }

    protected Place(Parcel in) {
        giaohang = in.readByte() != 0;
        maquanan = in.readString();
        giomocua = in.readString();
        giodongcua = in.readString();
        tenquanan = in.readString();
        videogioithieu = in.readString();
        tienich = in.createStringArrayList();
        hinhanhquanan = in.createStringArrayList();
        luotthich = in.readLong();
        lstImageBitmap = in.createTypedArrayList(Bitmap.CREATOR);
        lstBranch = in.createTypedArrayList(Branch.CREATOR);
        binhluanList = in.createTypedArrayList(Comment.CREATOR);
    }

    public static final Creator<Place> CREATOR = new Creator<Place>() {
        @Override
        public Place createFromParcel(Parcel in) {
            return new Place(in);
        }

        @Override
        public Place[] newArray(int size) {
            return new Place[size];
        }
    };

    public ArrayList<Bitmap> getLstImageBitmap() {
        return lstImageBitmap;
    }

    public void setLstImageBitmap(ArrayList<Bitmap> lstImageBitmap) {
        this.lstImageBitmap = lstImageBitmap;
    }

    public ArrayList<Branch> getLstBranch() {
        return lstBranch;
    }

    public void setLstBranch(ArrayList<Branch> lstBranch) {
        this.lstBranch = lstBranch;
    }

    public ArrayList<Comment> getBinhluanList() {
        return binhluanList;
    }

    public void setBinhluanList(ArrayList<Comment> binhluanList) {
        this.binhluanList = binhluanList;
    }

    public ArrayList<String> getHinhanhquanan() {
        return hinhanhquanan;
    }

    public void setHinhanhquanan(ArrayList<String> hinhanhquanan) {
        this.hinhanhquanan = hinhanhquanan;
    }

    public long getLuotthich() {
        return luotthich;
    }

    public void setLuotthich(long luotthich) {
        this.luotthich = luotthich;
    }

    public boolean isGiaohang() {
        return giaohang;
    }

    public void setGiaohang(boolean giaohang) {
        this.giaohang = giaohang;
    }

    public String getMaquanan() {
        return maquanan;
    }

    public void setMaquanan(String maquanan) {
        this.maquanan = maquanan;
    }

    public String getGiomocua() {
        return giomocua;
    }

    public void setGiomocua(String giomocua) {
        this.giomocua = giomocua;
    }

    public String getGiodongcua() {
        return giodongcua;
    }

    public void setGiodongcua(String giodongcua) {
        this.giodongcua = giodongcua;
    }

    public String getTenquanan() {
        return tenquanan;
    }

    public void setTenquanan(String tenquanan) {
        this.tenquanan = tenquanan;
    }

    public String getVideogioithieu() {
        return videogioithieu;
    }

    public void setVideogioithieu(String videogioithieu) {
        this.videogioithieu = videogioithieu;
    }

    public ArrayList<String> getTienich() {
        return tienich;
    }

    public void setTienich(ArrayList<String> tienich) {
        this.tienich = tienich;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte((byte) (giaohang ? 1 : 0));
        dest.writeString(maquanan);
        dest.writeString(giomocua);
        dest.writeString(giodongcua);
        dest.writeString(tenquanan);
        dest.writeString(videogioithieu);
        dest.writeStringList(tienich);
        dest.writeStringList(hinhanhquanan);
        dest.writeLong(luotthich);
        dest.writeTypedList(lstImageBitmap);
        dest.writeTypedList(lstBranch);
        dest.writeTypedList(binhluanList);
    }
}
