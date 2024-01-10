package Entities;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class THAMSO {
    private String TenThamSo = "";
    private Float GiaTri;
    private String GiaiThich = "";

    public THAMSO(){}
    public THAMSO(String tenThamSo, Float giaTri, String giaiThich){
        this.TenThamSo = tenThamSo;
        this.GiaTri = giaTri;
        this.GiaiThich = giaiThich;
    }
    
    @XmlElement
    public String getTenThamSo() {
        return TenThamSo;
    }

    public void setTenThamSo(String TenThamSo) {
        this.TenThamSo = TenThamSo;
    }

    @XmlElement
    public Float getGiaTri() {
        return GiaTri;
    }

    public void setGiaTri(Float GiaTri) {
        this.GiaTri = GiaTri;
    }

    @XmlElement
    public String getGiaiThich() {
        return GiaiThich;
    }

    public void setGiaiThich(String GiaiThich) {
        this.GiaiThich = GiaiThich;
    }

}
