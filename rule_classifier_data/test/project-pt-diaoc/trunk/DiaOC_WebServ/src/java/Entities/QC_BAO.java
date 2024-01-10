package Entities;

import java.sql.Date;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlRootElement
public class QC_BAO {
    private String MaQCBao;
    private Date NgayBatDauPhatHanh;
    private Boolean CoHinhAnh;
    private String MaND;
    private String MaPhieuDangKy;
    private String MaGiaTien;

    public QC_BAO(){}
    public QC_BAO(String maQCBao, Date ngayBatDauPhatHanh, Boolean coHinhAnh,
            String maND, String maPhieuDK, String maGiaTien){
        this.MaQCBao = maQCBao;
        this.NgayBatDauPhatHanh = ngayBatDauPhatHanh;
        this.CoHinhAnh = coHinhAnh;
        this.MaND = maND;
        this.MaPhieuDangKy = maPhieuDK;
        this.MaGiaTien = maGiaTien;
    }
    
    @XmlElement
    public String getMaQCBao() {
        return MaQCBao;
    }

    public void setMaQCBao(String MaQCBao) {
        this.MaQCBao = MaQCBao;
    }

    @XmlJavaTypeAdapter(SqlDateAdapter.class)
    public Date getNgayBatDauPhatHanh() {
        return NgayBatDauPhatHanh;
    }
    
    public void setNgayBatDauPhatHanh(Date NgayBatDauPhatHanh) {
        this.NgayBatDauPhatHanh = NgayBatDauPhatHanh;
    }

    @XmlElement
    public Boolean getCoHinhAnh() {
        return CoHinhAnh;
    }

    public void setCoHinhAnh(Boolean CoHinhAnh) {
        this.CoHinhAnh = CoHinhAnh;
    }

    @XmlElement
    public String getMaND() {
        return MaND;
    }

    public void setMaND(String MaND) {
        this.MaND = MaND;
    }

    @XmlElement
    public String getMaPhieuDangKy() {
        return MaPhieuDangKy;
    }

    public void setMaPhieuDangKy(String MaPhieuDangKy) {
        this.MaPhieuDangKy = MaPhieuDangKy;
    }

    @XmlElement
    public String getMaGiaTien() {
        return MaGiaTien;
    }

    public void setMaGiaTien(String MaGiaTien) {
        this.MaGiaTien = MaGiaTien;
    }

}
