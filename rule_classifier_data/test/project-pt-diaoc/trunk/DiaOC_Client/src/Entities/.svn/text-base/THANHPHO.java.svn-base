package Entities;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class THANHPHO {
    private String MaThanhPho = "";
    private String TenThanhPho = "";
    
    public THANHPHO(){}
    public THANHPHO(String maThanhPho, String tenThanhPho){
        MaThanhPho = maThanhPho;
        TenThanhPho = tenThanhPho;
    }

    @XmlElement
    public String getMaThanhPho() {
        return MaThanhPho;
    }

    public void setMaThanhPho(String MaThanhPho) {
        this.MaThanhPho = MaThanhPho;
    }

    @XmlElement
    public String getTenThanhPho() {
        return TenThanhPho;
    }

    public void setTenThanhPho(String TenThanhPho) {
        this.TenThanhPho = TenThanhPho;
    }
    
    @Override
    public String toString(){
        return this.TenThanhPho;
    }
    
}
