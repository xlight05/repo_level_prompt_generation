/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sunspotworld;

import java.io.IOException;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Inma
 */
public class MulticastConnection implements SenderConnection{
    //private MulticastSocket multicastSocket;
    private String ip;
    private int port;
    private DatagramSocket multicastSocket;
    
    
    public MulticastConnection(String i,int p){
        ip=i;
        port=p;
        /*try {
            multicastSocket = new MulticastSocket();
        } catch (IOException ex) {
            Logger.getLogger(MulticastConnection.class.getName()).log(Level.SEVERE, null, ex);
        }*/

    }

    
    
    public void sendMessage(AuroraMessage msg) {
        try {        
            // Se envía el mensaje con el datagrama!!
            //byte buf[] = new byte[10];
            //for (int i=0; i<buf.length; i++) buf[i] = (byte)i;
            multicastSocket = new MulticastSocket();
            byte buf[] = new byte[1024];
            buf = msg.toString().getBytes();
            //int ttl=1;
            InetAddress group = InetAddress.getByName(ip);
            //System.out.println(group);
            boolean t = group.isMulticastAddress();
            //multicastSocket.joinGroup(group);
            // Create a DatagramPacket 
            DatagramPacket pack = new DatagramPacket(buf, buf.length, group, port);
            //System.out.println("Se ha enviado un mensaje usando el multicast");
            
            // Do a send. Note that send takes a byte for the ttl and not an int.
            multicastSocket.send(pack);
            // And when we have finished sending data close the socket
            multicastSocket.close();
        } catch (IOException ex) {
            Logger.getLogger(MulticastConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        /*InetAddress ia = null;
        byte[] buffer = msg.toString().getBytes();
        DatagramPacket dp = new DatagramPacket(buffer, buffer.length);
        int port = 0;


        //read the address from the command line
        try{
            try {
                ia = InetAddress.getByName(getIp());
            }
            catch (UnknownHostException e) {
                System.err.println(e);
            }
            port = getPort();
        }// end try
        catch (Exception e) {
            System.err.println(e);
            System.err.println
            ("Usage: java MulticastSniffer MulticastAddress port");
            System.exit(1);
        }

        try{
            MulticastSocket ms = new MulticastSocket(port);
            ms.joinGroup(ia);
            while (true) {
                //ms.receive(dp);
                ms.send(dp);
                //String s = new String(dp.getData(),0,dp.getLength());
                //System.out.println(s);
            }
        }
        catch (SocketException se) {
            System.err.println(se);
        }
        catch (IOException ie) {
            System.err.println(ie);
        }*/
    }

    /**
     * @return the ip
     */
    public String getIP() {
        return ip;
    }

    /**
     * @return the port
     */
    public int getPort() {
        return port;
    }

    @Override
    public String getConnectionProtocol() {
        return "MulticastConnection";
    }

    @Override
    public int getTypeConnection() {
        return -1;
    }
}
