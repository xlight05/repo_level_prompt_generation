package Entities;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement
public class DANGNHAP {
    private int ID = 1;
    private String UserName = "";
    private String PassWord = "";
    public DANGNHAP(){};
    public DANGNHAP(int id,String username, String password){
        this.ID = id;
        this.UserName = username;
        this.PassWord = password;
    }

    @XmlElement
    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    @XmlElement
    public String getUserName() {
        return UserName;
    }

    public void setUserName(String UserName) {
        this.UserName = UserName;
    }

    @XmlElement
    public String getPassWord() {
        return PassWord;
    }

     public void setPassWord(String PassWord) {
        this.PassWord = PassWord;
    }

}
