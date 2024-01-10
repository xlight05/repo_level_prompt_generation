package Entities;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement
public class BAO {
    private String MaBao = "";
    private String TenBao = "";
    private int ThuPhatHanh = 1;

    public BAO(String maBao, String tenBao, int thuPhatHanh){
        this.MaBao = maBao;
        this.TenBao = tenBao;
        this.ThuPhatHanh = thuPhatHanh;
    }
    public BAO(){}
    
    @XmlElement
    public String getMaBao() {
        return MaBao;
    }


    public void setMaBao(String MaBao) {
        this.MaBao = MaBao;
    }

    @XmlElement
    public String getTenBao() {
        return TenBao;
    }


    public void setTenBao(String TenBao) {
        this.TenBao = TenBao;
    }

    @XmlElement
    public int getThuPhatHanh() {
        return ThuPhatHanh;
    }

    
    public void setThuPhatHanh(int ThuPhatHanh) {
        this.ThuPhatHanh = ThuPhatHanh;
    }
    
    @Override
    public String toString(){
        return this.TenBao;
    }
}
