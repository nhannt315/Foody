package nhannt.foody.data.model;

import java.util.ArrayList;

/**
 * Created by nhannt on 12/11/2017.
 */
public class Comment {
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
}
