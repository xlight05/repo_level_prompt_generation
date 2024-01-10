package Entities;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class PHUONG {
    private String MaPhuong = "";
    private String TenPhuong = "";
    public PHUONG(){}
    public PHUONG(String maPhuong, String tenPhuong){
        MaPhuong = maPhuong;
        TenPhuong = tenPhuong;
    }

    @XmlElement
    public String getMaPhuong() {
        return MaPhuong;
    }


    public void setMaPhuong(String MaPhuong) {
        this.MaPhuong = MaPhuong;
    }

    @XmlElement
    public String getTenPhuong() {
        return TenPhuong;
    }

    public void setTenPhuong(String TenPhuong) {
        this.TenPhuong = TenPhuong;
    }
    
    @Override
    public String toString(){
        return this.TenPhuong;
    }
}
