/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sunspotworld;

import com.sun.spot.io.j2me.udp.UDPConnection;
import com.sun.spot.io.j2me.udp.UDPDatagram;
import com.sun.spot.util.Utils;

/**
 *
 * @author Inma
 */
public class UDPBroadcaster extends Thread{
    
    private Boolean _end;
    private int _PORT;
    private long _time;
    
    public UDPBroadcaster(String  name,int port){
        super(name);
        this._end=false;
        this._PORT=port;
        //this._listener=l;
    }
    
    @Override
    public void run(){
        
        UDPConnection sendConn/*,recConn*/;
        //Byte[] address;
        UDPDatagram dg = null;
        String url="udp://broadcast:"+this._PORT;
        Utils.sleep(2000);  // Let IPv6 startup under us
        
        while(!_end){
            try {
                // Localización de los agentes
                sendConn = (UDPConnection) javax.microedition.io.Connector.open(url);
                dg = (UDPDatagram) sendConn.newDatagram(sendConn.getMaximumLength());
                // Write the command into the datagram
                dg.writeUTF(System.currentTimeMillis()+"");
                //System.out.println("[SOL] Dando la dirección a: "+dg.getAddress());
                //dg.writeUTF("AP");
                // Broadcast to other agents
                sendConn.send(dg);
                sendConn.close();
            }catch(Exception e){
                e.printStackTrace();
            }
            Utils.sleep(500);
        }
    }
    
    @Override
    public void destroy(){
        this._end=true;
    }
}
