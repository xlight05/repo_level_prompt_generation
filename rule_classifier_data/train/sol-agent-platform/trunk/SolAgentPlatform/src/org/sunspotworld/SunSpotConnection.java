/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sunspotworld;

import com.sun.spot.io.j2me.udp.UDPConnection;
import com.sun.spot.io.j2me.udp.UDPDatagram;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.microedition.io.Connector;

/**
 *
 * @author Inma
 */
public class SunSpotConnection implements SenderConnection{
    
    private String HOST;
    private byte[] address;
    private int PORT;
    
    public SunSpotConnection(String ip,int port,byte[] a){
        this.HOST=ip;
        this.address=a;
        this.PORT=port;
    }
    
    public SunSpotConnection(String ip,int port){
        this.HOST=ip;
        this.PORT=port;
    }

    public String getHOST() {
        return HOST;
    }

    public void setHOST(String HOST) {
        this.HOST = HOST;
    }

    public int getPORT() {
        return PORT;
    }

    public void setPORT(int PORT) {
        this.PORT = PORT;
    }

    public byte[] getAddress() {
        return address;
    }

    public void setAddress(byte[] address) {
        this.address = address;
    }
    
    public void sendMessage(AuroraMessage msg) {
        try {
            UDPConnection conn;
            
            conn = (UDPConnection) Connector.open("udp://["+HOST+"]:"+this.PORT);
            //Utils.sleep(500);
            //System.out.println("[SSCL] Se abre la conexión "+"udp://["+msg.getReceivers().get(i)+"]:"+this._SND_PORT);
            UDPDatagram rdg=(UDPDatagram)conn.newDatagram(conn.getMaximumLength());
            rdg.setDstAddress(this.address);
            rdg.setDstPort(this.PORT);
            rdg.writeUTF(msg.toString());
            //System.out.println("[SSCL] Send message to SunSpot");
            conn.send(rdg);
            conn.close();
        } catch (Exception ex) {
            Logger.getLogger(SunSpotConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public String getIP(){
        return HOST;
    }

    @Override
    public String getConnectionProtocol() {
        return "Sun Spot";
    }

    @Override
    public int getTypeConnection() {
        return TypeConnection.TYPE_SSMC;
    }
}
