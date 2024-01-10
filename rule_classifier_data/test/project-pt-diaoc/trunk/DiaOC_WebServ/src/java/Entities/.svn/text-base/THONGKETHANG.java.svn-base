package Entities;

import java.sql.Date;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlRootElement
public class THONGKETHANG {
    private String MaKH = "";
    private String HoTen = "";
    private DIACHI DiaChiKhachHang;
    private String SoDienThoai = "";
    private String Email = "";
    private Date TuNgay;

    public THONGKETHANG(){}
    public THONGKETHANG(String maKH, String hoTen, DIACHI diaChiKhachHang, String soDT, String email, Date tuNgay){
        this.MaKH = maKH;
        this.HoTen = hoTen;
        this.DiaChiKhachHang = diaChiKhachHang;
        this.SoDienThoai = soDT;
        this.Email = email;
        this.TuNgay = tuNgay;
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
    public String getSoDienThoai() {
        return SoDienThoai;
    }

    public void setSoDienThoai(String SoDienThoai) {
        this.SoDienThoai = SoDienThoai;
    }

    @XmlElement
    public String getEmail() {
        return Email;
    }

    public void setEmail(String Email) {
        this.Email = Email;
    }

    @XmlJavaTypeAdapter(SqlDateAdapter.class)
    public Date getTuNgay() {
        return TuNgay;
    }

    public void setTuNgay(Date tuNgay) {
        this.TuNgay = tuNgay;
    }

    @XmlElement
    public DIACHI getDiaChiKhachHang() {
        return DiaChiKhachHang;
    }

    public void setDiaChiKhachHang(DIACHI DiaChiKhachHang) {
        this.DiaChiKhachHang = DiaChiKhachHang;
    }

}
