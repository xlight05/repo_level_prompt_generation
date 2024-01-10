package Entities;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement
public class THONGKEDIAOC {
    private String MaKH = "";
    private String HoTen = "";
    private String MaDiaOC = "";
    private String TrangThai = "";
    private Float GiaBan;
    private DIACHI DiaChiDiaOc;

    public THONGKEDIAOC(){}
    public THONGKEDIAOC(String maKH, String hoTen, String maDiaOc,
            String trangThai, Float giaBan, DIACHI diaChiDiaOc){
        this.MaKH = maKH;
        this.HoTen = hoTen;
        this.MaDiaOC = maDiaOc;
        this.TrangThai = trangThai;
        this.GiaBan = giaBan;
        this.DiaChiDiaOc = diaChiDiaOc;
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
    public String getMaDiaOC() {
        return MaDiaOC;
    }

    public void setMaDiaOC(String MaDiaOC) {
        this.MaDiaOC = MaDiaOC;
    }

    @XmlElement
    public String getTrangThai() {
        return TrangThai;
    }

    public void setTrangThai(String TrangThai) {
        this.TrangThai = TrangThai;
    }

    @XmlElement
    public Float getGiaBan() {
        return GiaBan;
    }
    
    public void setGiaBan(Float GiaBan) {
        this.GiaBan = GiaBan;
    }

    @XmlElement
    public DIACHI getDiaChiDiaOc() {
        return DiaChiDiaOc;
    }

    public void setDiaChiDiaOc(DIACHI DiaChiDiaOc) {
        this.DiaChiDiaOc = DiaChiDiaOc;
    }
}
