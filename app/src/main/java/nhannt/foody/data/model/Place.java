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
    private long giatoida;
    private long giatoithieu;
    private ArrayList<String> tienich;
    private ArrayList<String> hinhanhquanan;
    private ArrayList<Comment> binhluanList;
    private long luotthich;
    private ArrayList<Branch> lstBranch;
    private ArrayList<Bitmap> lstImageBitmap;

    public Place() {
    }

    public long getGiatoida() {
        return giatoida;
    }

    public void setGiatoida(long giatoida) {
        this.giatoida = giatoida;
    }

    public long getGiatoithieu() {
        return giatoithieu;
    }

    public void setGiatoithieu(long giatoithieu) {
        this.giatoithieu = giatoithieu;
    }

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
        dest.writeByte(this.giaohang ? (byte) 1 : (byte) 0);
        dest.writeString(this.maquanan);
        dest.writeString(this.giomocua);
        dest.writeString(this.giodongcua);
        dest.writeString(this.tenquanan);
        dest.writeString(this.videogioithieu);
        dest.writeLong(this.giatoida);
        dest.writeLong(this.giatoithieu);
        dest.writeStringList(this.tienich);
        dest.writeStringList(this.hinhanhquanan);
        dest.writeTypedList(this.binhluanList);
        dest.writeLong(this.luotthich);
        dest.writeTypedList(this.lstBranch);
        dest.writeTypedList(this.lstImageBitmap);
    }

    protected Place(Parcel in) {
        this.giaohang = in.readByte() != 0;
        this.maquanan = in.readString();
        this.giomocua = in.readString();
        this.giodongcua = in.readString();
        this.tenquanan = in.readString();
        this.videogioithieu = in.readString();
        this.giatoida = in.readLong();
        this.giatoithieu = in.readLong();
        this.tienich = in.createStringArrayList();
        this.hinhanhquanan = in.createStringArrayList();
        this.binhluanList = in.createTypedArrayList(Comment.CREATOR);
        this.luotthich = in.readLong();
        this.lstBranch = in.createTypedArrayList(Branch.CREATOR);
        this.lstImageBitmap = in.createTypedArrayList(Bitmap.CREATOR);
    }

    public static final Creator<Place> CREATOR = new Creator<Place>() {
        @Override
        public Place createFromParcel(Parcel source) {
            return new Place(source);
        }

        @Override
        public Place[] newArray(int size) {
            return new Place[size];
        }
    };
}
