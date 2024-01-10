package Entities;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class QUAN {
    private String MaQuan = "";
    private String TenQuan = "";
    public QUAN(){}
    public QUAN(String maQuan, String tenQuan){
        MaQuan = maQuan;
        TenQuan = tenQuan;
    }

    @XmlElement
    public String getMaQuan() {
        return MaQuan;
    }

    public void setMaQuan(String MaQuan) {
        this.MaQuan = MaQuan;
    }

    @XmlElement
    public String getTenQuan() {
        return TenQuan;
    }

    public void setTenQuan(String TenQuan) {
        this.TenQuan = TenQuan;
    }
    
    @Override
    public String toString(){
        return this.TenQuan;
    }
}
