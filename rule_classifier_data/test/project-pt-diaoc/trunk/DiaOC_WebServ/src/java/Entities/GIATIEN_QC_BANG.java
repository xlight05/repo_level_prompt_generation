package Entities;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement
public class GIATIEN_QC_BANG {
    private String MaGiaTien = "";
    private String MaLoaiBang = "";
    private String MoTa = "";
    private Float KichCo;
    private String DVT = "";
    private Float GiaTien;

    public GIATIEN_QC_BANG(){}
    public GIATIEN_QC_BANG(String maGiaTien, String maLoaiBang, String moTa, Float kichCo, String dvt, Float giaTien){
        this.MaGiaTien = maGiaTien;
        this.MaLoaiBang = maLoaiBang;
        this.MoTa = moTa;
        this.KichCo = kichCo;
        this.DVT = dvt;
        this.GiaTien = giaTien;
    }
    @XmlElement
    public String getMaGiaTien() {
        return MaGiaTien;
    }

    public void setMaGiaTien(String MaGiaTien) {
        this.MaGiaTien = MaGiaTien;
    }

    @XmlElement
    public String getMoTa() {
        return MoTa;
    }

    public void setMoTa(String MoTa) {
        this.MoTa = MoTa;
    }

    @XmlElement
    public Float getKichCo() {
        return KichCo;
    }

    public void setKichCo(Float KichCo) {
        this.KichCo = KichCo;
    }

    @XmlElement
    public String getDVT() {
        return DVT;
    }

    public void setDVT(String DVT) {
        this.DVT = DVT;
    }

    @XmlElement
    public Float getGiaTien() {
        return GiaTien;
    }

    public void setGiaTien(Float GiaTien) {
        this.GiaTien = GiaTien;
    }

    @XmlElement
    public String getMaLoaiBang() {
        return MaLoaiBang;
    }

    public void setMaLoaiBang(String MaLoaiBang) {
        this.MaLoaiBang = MaLoaiBang;
    }
    
    @Override
    public String toString(){
        return this.getMaGiaTien();
    }
}
