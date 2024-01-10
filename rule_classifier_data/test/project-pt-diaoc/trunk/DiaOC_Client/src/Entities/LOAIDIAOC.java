package Entities;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement
public class LOAIDIAOC {
    private String MaLoaiDiaOc = "";
    private String TenLoaiDiaOc = "";
    private String KyHieu = "";


    public LOAIDIAOC(){}
    public LOAIDIAOC(String maLoaiDiaOc, String tenLoaiDiaOc, String kyHieu){
        this.MaLoaiDiaOc = maLoaiDiaOc;
        this.TenLoaiDiaOc = tenLoaiDiaOc;
        this.KyHieu = kyHieu;
    }
    
    @XmlElement
    public String getMaLoaiDiaOc() {
        return MaLoaiDiaOc;
    }

    public void setMaLoaiDiaOc(String MaLoaiDiaOc) {
        this.MaLoaiDiaOc = MaLoaiDiaOc;
    }

    @XmlElement
    public String getTenLoaiDiaOc() {
        return TenLoaiDiaOc;
    }

    public void setTenLoaiDiaOc(String TenLoaiDiaOc) {
        this.TenLoaiDiaOc = TenLoaiDiaOc;
    }

    @XmlElement
    public String getKyHieu() {
        return KyHieu;
    }

    public void setKyHieu(String KyHieu) {
        this.KyHieu = KyHieu;
    }
    
    @Override
    public String toString()
    {
        return TenLoaiDiaOc;
    }
}
