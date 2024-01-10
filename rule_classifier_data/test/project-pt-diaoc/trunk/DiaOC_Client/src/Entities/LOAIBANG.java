package Entities;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class LOAIBANG {
    private String MaLoaiBang = "";
    private String LoaiBang = "";

    public LOAIBANG(){}
    public LOAIBANG(String maLoaiBang, String loaiBang){
        this.MaLoaiBang = maLoaiBang;
        this.LoaiBang = loaiBang;
    }
    
    @XmlElement
    public String getMaLoaiBang() {
        return MaLoaiBang;
    }

    public void setMaLoaiBang(String MaLoaiBang) {
        this.MaLoaiBang = MaLoaiBang;
    }

    @XmlElement
    public String getLoaiBang() {
        return LoaiBang;
    }

    public void setLoaiBang(String LoaiBang) {
        this.LoaiBang = LoaiBang;
    }

}
