package Entities;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class DUONG {
    private String MaDuong = "";
    private String TenDuong = "";
    public DUONG(){}
    public DUONG(String maDuong, String tenDuong){
        MaDuong = maDuong;
        TenDuong = tenDuong;
    }

    @XmlElement
    public String getMaDuong() {
        return MaDuong;
    }

    public void setMaDuong(String MaDuong) {
        this.MaDuong = MaDuong;
    }

    @XmlElement
    public String getTenDuong() {
        return TenDuong;
    }

    public void setTenDuong(String TenDuong) {
        this.TenDuong = TenDuong;
    }
    
    @Override
    public String toString(){
        return this.TenDuong;
    }
}
