
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sunspotworld;

/**
 *
 * @author Inma
 */
public interface SenderConnection {
    
    //public static boolean groupConnection = false;
    public void sendMessage(AuroraMessage msg);

    public String getIP();

    public String getConnectionProtocol();

    public int getTypeConnection();
    
}
