/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sunspotworld;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Migue
 */
public class BluetoothMulticast implements SenderConnection{
    List<BTConnection> bt;
    
    public BluetoothMulticast(){
        bt = new LinkedList<BTConnection>();
    }
    
    void addBTConnection(BTConnection btc){
        bt.add(btc);
    }
    
    boolean removeAgent(BTConnection btc) {
        return bt.remove(btc);
    }
    
    public void sendMessage(AuroraMessage msg) {
        for (Iterator<BTConnection> it = bt.iterator(); it.hasNext();) {
            BTConnection bTConnection = it.next();
            bTConnection.sendMessage(msg);
        }
    }

    public int sizeBT() {
        return bt.size();
    }
    
    public List<BTConnection> getBTDevices(){
        return bt;
    }

    boolean isAgentAdded(String sender) {
        boolean res = false;
        for (Iterator<BTConnection> it = bt.iterator(); it.hasNext();) {
            BTConnection bTConnection = it.next();
            if(bTConnection.getAgentName().equals(sender)){
                res = true;
                break;
            }
        }
        return res;
    }
    
    public BTConnection getBTDevice(String agent){
        BTConnection btconn = null;
        for (Iterator<BTConnection> it = bt.iterator(); it.hasNext();) {
            BTConnection btc = it.next();
            if(btc.getAgentName().equals(agent)){
                btconn = btc;
            }
        }
        return btconn;
    }

    @Override
    public String getIP() {
        return "BTMulticast";
    }

    @Override
    public String getConnectionProtocol() {
        return "BTMulticast";
    }

    @Override
    public int getTypeConnection() {
        return -1;
    }
    
}
