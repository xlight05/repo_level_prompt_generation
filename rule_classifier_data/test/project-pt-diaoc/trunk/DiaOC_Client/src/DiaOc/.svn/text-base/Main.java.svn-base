/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package diaoc;
import Presentation.frmLogin;
import Presentation.frmSWSetting;
import java.io.BufferedReader;
import java.io.FileReader;
import javax.swing.UIManager;
/**
 *
 * @author VooKa
 */
public class Main{
    public static void main(String[] args) throws Exception{

        FileReader fr = new FileReader("config.dat");
        
        BufferedReader input = new BufferedReader(fr);
        String ui = input.readLine();
        String lang = input.readLine();
        String mHost = input.readLine();
        String mPort = input.readLine();
        
        Utilities.Utilities.host = mHost;
        Utilities.Utilities.port = mPort;
        
        if(Utilities.Utilities.checkService(mHost, mPort) == false){
            frmSWSetting fs = new frmSWSetting();
            fs.setVisible(true);
        }else{     
            UIManager.setLookAndFeel(ui);
            frmLogin frm = new frmLogin();
            frm.setVisible(true);
        }
    }
}
