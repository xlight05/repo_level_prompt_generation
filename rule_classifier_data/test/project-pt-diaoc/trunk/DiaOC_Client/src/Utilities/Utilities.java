/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Utilities;

import java.awt.Component;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author Nixforest
 */
public class Utilities {
    static public String host = "localhost";
    static public String port = "8080";
    
    static public boolean checkService(String h, String p){
        Client client = ClientBuilder.newClient();
        String URI = "http://"+h+":"+p+"/DiaOC_WebServ/Resources/HOST/";
        WebTarget Host_Resource = client.target(URI);
        try {
            String s = Host_Resource.path("CheckHost").
                request(MediaType.TEXT_PLAIN).get(String.class);
            return s.equals("success");
        } catch (Exception e){
            return false;
        }
    }
    
    // Set thuộc tính Font cho Một JInternalFrame và toàn bộ các thành phần trong nó
    static public void setFontForAll(JInternalFrame f, java.awt.Font font){
        f.setFont(font);
        setFontRecursive(f.getContentPane().getComponents(), font);
    }

    static public void setFontRecursive(Component[] components, Font font) {
        for (Component c : components) {
            c.setFont(font);
            if (c instanceof java.awt.Container) {
                setFontRecursive(((java.awt.Container)c).getComponents(), font);
            }
        }
    }
    
    // Set thuộc tính Enabled cho Một JPanel và toàn bộ các thành phần trong nó
    static public void setEnabledForAll(JPanel f, boolean b){
        f.setEnabled(b);
        setEnabledRecursive(f.getComponents(), b);
    }

    public static void setEnabledRecursive(Component[] components, boolean b) {
        for (Component c : components) {
            c.setEnabled(b);
            if (c instanceof java.awt.Container) {
                setEnabledRecursive(((java.awt.Container)c).getComponents(), b);
            }
        }
    }
    
    static public void tbxKeyTyped(java.awt.event.KeyEvent evt) {                                  
        // TODO add your handling code here:
        String decimal = "1234567890.";
        int key = evt.getKeyChar();
        if(key!=KeyEvent.VK_BACK_SPACE&&key!=KeyEvent.VK_DELETE&&key!=KeyEvent.VK_ENTER&&key!=KeyEvent.VK_END&&key!=KeyEvent.VK_HOME&&key!=KeyEvent.VK_TAB)
        {
            int flag=0;
            if(decimal.indexOf(key)==-1)
            {
                flag++;
            }
            if(flag>0)
                evt.consume();
        }
    }
    
    // Trả về chuỗi Thứ từ một số nguyên từ 1 đến 7
    static public String GetThu(int t){
        String s = "";
        switch(t){
            case 1: s = "Chủ Nhật"; break;
            case 2: s = "Thứ 2"; break;
            case 3: s = "Thứ 3"; break;
            case 4: s = "Thứ 4"; break;
            case 5: s = "Thứ 5"; break;
            case 6: s = "Thứ 6"; break;
            default: s = "Thứ 7";
        }
        return s;
    }
    
    // Xử lý xác nhận email hợp lệ
    static public class EmailValidator{

          private Pattern pattern;
          private Matcher matcher;

          private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

          public EmailValidator(){
                  pattern = Pattern.compile(EMAIL_PATTERN);
          }

          /**
           * Validate hex with regular expression
           * @param hex hex for validation
           * @return true valid hex, false invalid hex
           */
          public boolean validate(final String hex){

                  matcher = pattern.matcher(hex);
                  return matcher.matches();

          }
    }
    
    // Xử lý bỏ dấu tiếng việt
    static public class StringUtil{
        private final String[] VietnameseSigns = new String[]{
            "aAeEoOuUiIdDyY",
            "áàạảãâấầậẩẫăắằặẳẵ",
            "ÁÀẠẢÃÂẤẦẬẨẪĂẮẰẶẲẴ",
            "éèẹẻẽêếềệểễ",
            "ÉÈẸẺẼÊẾỀỆỂỄ",
            "óòọỏõôốồộổỗơớờợởỡ",
            "ÓÒỌỎÕÔỐỒỘỔỖƠỚỜỢỞỠ",
            "úùụủũưứừựửữ",
            "ÚÙỤỦŨƯỨỪỰỬỮ",
            "íìịỉĩ",
            "ÍÌỊỈĨ",
            "đ",
            "Đ",
            "ýỳỵỷỹ",
            "ÝỲỴỶỸ"
        };
        public String RemoveSign4VietNameseString(String str){
            //Tiến hành thay thế , lọc bỏ dấu cho chuỗi
            for (int i = 1; i < VietnameseSigns.length; i++){
                for (int j = 0; j < VietnameseSigns[i].length(); j++)
                    str = str.replace(VietnameseSigns[i].charAt(j), VietnameseSigns[0].charAt(i-1));
                }
            return str;
        }
    }
}
