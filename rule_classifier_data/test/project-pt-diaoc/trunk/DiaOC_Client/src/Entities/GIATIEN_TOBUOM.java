package Entities;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class GIATIEN_TOBUOM {
    private String MaGiaTien = "";
    private int SoLuong = 0;
    private Float GiaTien;


    public GIATIEN_TOBUOM(){}
    public GIATIEN_TOBUOM(String maGiaTien, int soLuong, Float giaTien){
        this.MaGiaTien = maGiaTien;
        this.SoLuong = soLuong;
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
    public int getSoLuong() {
        return SoLuong;
    }

    public void setSoLuong(int SoLuong) {
        this.SoLuong = SoLuong;
    }

    @XmlElement
    public Float getGiaTien() {
        return GiaTien;
    }

    public void setGiaTien(Float GiaTien) {
        this.GiaTien = GiaTien;
    }

    @Override
    public String toString(){
        return Integer.toString(SoLuong);
    }
}
