package Entities;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement
public class KHACHHANG {
    private String MaKH = "";
    private String HoTen = "";
    private DIACHI DiaChiKH;
    private String SoDT = "";
    private String Email = "";
    
    public KHACHHANG(){}
    public KHACHHANG(String maKH, String hoTen, DIACHI diaChiKH, String soDT, String email){
        this.MaKH = maKH;
        this.HoTen = hoTen;
        this.DiaChiKH = diaChiKH;
        this.SoDT = soDT;
        this.Email = email;
    }

    @XmlElement
    public String getMaKH() {
        return MaKH;
    }

    public void setMaKH(String MaKH) {
        this.MaKH = MaKH;
    }

    @XmlElement
    public String getHoTen() {
        return HoTen;
    }

    public void setHoTen(String HoTen) {
        this.HoTen = HoTen;
    }

    @XmlElement
    public String getSoDT() {
        return SoDT;
    }

    public void setSoDT(String SoDT) {
        this.SoDT = SoDT;
    }

    @XmlElement
    public String getEmail() {
        return Email;
    }

    public void setEmail(String Email) {
        this.Email = Email;
    }

    @XmlElement
    public DIACHI getDiaChiKH() {
        return DiaChiKH;
    }

    public void setDiaChiKH(DIACHI DiaChiKH) {
        this.DiaChiKH = DiaChiKH;
    }
}
