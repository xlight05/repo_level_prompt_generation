package Entities;

import java.sql.Date;
import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlRootElement
public class HINHANH {
    private int MaHinhAnh = 0;
    private String Anh = "";
    private String MoTa = "";
    private Date ThoiGianChupAnh;
    private String MaND = "";


    public HINHANH(){}
    public HINHANH(int maHinhAnh, String anh, String moTa, Date thoiGianChupAnh, String maND){
        this.MaHinhAnh = maHinhAnh;
        this.Anh = anh;
        this.MoTa = moTa;
        this.ThoiGianChupAnh = thoiGianChupAnh;
        this.MaND = maND;
    }
    
    @XmlElement
    public int getMaHinhAnh() {
        return MaHinhAnh;
    }

    public void setMaHinhAnh(int MaHinhAnh) {
        this.MaHinhAnh = MaHinhAnh;
    }

    @XmlElement
    public String getAnh() {
        return Anh;
    }

    public void setAnh(String Anh) {
        this.Anh = Anh;
    }

    @XmlElement
    public String getMoTa() {
        return MoTa;
    }

    public void setMoTa(String MoTa) {
        this.MoTa = MoTa;
    }

    @XmlElement
    public String getMaND() {
        return MaND;
    }

    public void setMaND(String MaND) {
        this.MaND = MaND;
    }

    @XmlJavaTypeAdapter(SqlDateAdapter.class)
    public Date getThoiGianChupAnh() {
        return ThoiGianChupAnh;
    }

    public void setThoiGianChupAnh(Date ThoiGianChupAnh) {
        this.ThoiGianChupAnh = ThoiGianChupAnh;
    }

}
