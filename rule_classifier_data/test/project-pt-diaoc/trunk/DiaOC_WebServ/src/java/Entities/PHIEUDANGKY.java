package Entities;

import java.sql.Date;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlRootElement
public class PHIEUDANGKY {
    private String MaPhieuDangKy = "";
    private String MaDiaOc = "";
    private Date TuNgay;
    private Date DenNgay;
    private Float SoTien;
    private Date ThoiGianHenChupAnh;
    private String MaKH = "";


    public PHIEUDANGKY(){}
    public PHIEUDANGKY(String maPhieuDangKy, String maDiaOc, Date tuNgay,
            Date denNgay, Float soTien, Date thoiGianHenChupAnh, String maKH){
        this.MaPhieuDangKy = maPhieuDangKy;
        this.MaDiaOc = maDiaOc;
        this.TuNgay = tuNgay;
        this.DenNgay = denNgay;
        this.SoTien = soTien;
        this.ThoiGianHenChupAnh = thoiGianHenChupAnh;
        this.MaKH = maKH;
    }
    
    @XmlElement
    public String getMaPhieuDangKy() {
        return MaPhieuDangKy;
    }

    public void setMaPhieuDangKy(String MaPhieuDangKy) {
        this.MaPhieuDangKy = MaPhieuDangKy;
    }

    @XmlElement
    public String getMaDiaOc() {
        return MaDiaOc;
    }

    public void setMaDiaOc(String MaDiaOc) {
        this.MaDiaOc = MaDiaOc;
    }

    @XmlJavaTypeAdapter(SqlDateAdapter.class)
    public Date getTuNgay() {
        return TuNgay;
    }

    public void setTuNgay(Date TuNgay) {
        this.TuNgay = TuNgay;
    }

    @XmlJavaTypeAdapter(SqlDateAdapter.class)
    public Date getDenNgay() {
        return DenNgay;
    }

    public void setDenNgay(Date DenNgay) {
        this.DenNgay = DenNgay;
    }

    @XmlElement
    public Float getSoTien() {
        return SoTien;
    }

    public void setSoTien(Float SoTien) {
        this.SoTien = SoTien;
    }

    @XmlElement
    public String getMaKH() {
        return MaKH;
    }

    public void setMaKH(String MaKH) {
        this.MaKH = MaKH;
    }

    @XmlJavaTypeAdapter(SqlDateAdapter.class)
    public Date getThoiGianHenChupAnh() {
        return ThoiGianHenChupAnh;
    }

    public void setThoiGianHenChupAnh(Date ThoiGianHenChupAnh) {
        this.ThoiGianHenChupAnh = ThoiGianHenChupAnh;
    }
}
