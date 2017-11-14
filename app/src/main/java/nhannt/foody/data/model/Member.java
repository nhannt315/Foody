package nhannt.foody.data.model;

/**
 * Created by nhannt on 09/11/2017.
 */
public class Member {
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
}
