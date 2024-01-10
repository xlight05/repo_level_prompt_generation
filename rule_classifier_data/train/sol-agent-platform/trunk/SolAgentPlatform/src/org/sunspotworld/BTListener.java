/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sunspotworld;

import bt.javax.microedition.io.StreamConnection;
import bt.javax.microedition.io.StreamConnectionNotifier;
import java.io.*;
import java.util.List;
import java.util.UUID;
//import java.util.UUID;
//import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
//import javax.microedition.io.*;

/*import javax.microedition.io.Connector;
import javax.microedition.io.StreamConnection;
import javax.microedition.io.StreamConnectionNotifier;*/

/**
 *
 * @author Jose Luis
 */
public class BTListener extends Thread{
    private int PORT;
    private Boolean fin;
    //private Hashtable<String,AndroidTCPConnection> connections;
    private SunSpotHostApplication launcher;
    
    private static final javax.bluetooth.UUID SERVICE_ID = new javax.bluetooth.UUID("1101", true);
    //private static final java.util.UUID SERVICE_ID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    //private static final javax.bluetooth.UUID SERVICE_ID = new javax.bluetooth.UUID("1101", true);
    private String SERVICE_NAME = "BTListener_Platform";	
    
    //Create the servicve url
    private String connectionString;
    StreamConnection connectionAndroid = null;
    StreamConnectionNotifier streamConnNotifierAndroid = null;

    public BTListener(SunSpotHostApplication l){
        fin=false;
        this.launcher=l;
        connectionString = "btspp://localhost:" + SERVICE_ID +";name="+SERVICE_NAME+";authenticate=false;authorize=false";
    }
    
    public void stopListener(){
        fin=true;
    }
    
    @Override
    public void run(){
        
        BTConnection conn;    
        String id;
        
        try{
            
            System.out.println("[SOL] SolAgentPlatform is running and waiting for BT requests.");
            
            streamConnNotifierAndroid = (StreamConnectionNotifier) bt.javax.microedition.io.Connector.open(connectionString);
            //connectionAndroid = streamConnNotifierAndroid.acceptAndOpen();
            
            while(!fin){
                connectionAndroid = streamConnNotifierAndroid.acceptAndOpen();

                System.out.println("Se ha registrado un nuevo agente.");
                conn=new BTConnection(connectionAndroid,this);
                
                conn.start();
                //conn.interrupt();
                // Se registra la conexión
                /*id=server.getInetAddress().getHostAddress();
                System.out.println("[SOL] Se ha registrado la conexión "+id);
                this.registerConnection(id, conn);
                conn.start();*/
            }
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public void sendMessage(AuroraMessage msg) {
        this.launcher.sendMessage(msg);
    }

    protected Boolean isConnectionRegistered(String id){
        return launcher.isConnectionRegistered(id);
    }
    
    protected void registerConnection(String id,SenderConnection c){   
        /*System.out.println("[SOL] Se ha registrado la conexión con el id: "+id);
        this.connections.put(id, c);*/
        this.launcher.registerConnection(id, c);
    }
    
    
    void registerConnection(String id, BTConnection btc, String mas, String cat) {
        this.launcher.registerConnection(id, btc, mas, cat);
    }
    
    protected void unregisterConnection(String id){
        /*System.out.println("[SOL] La conexión "+id+" se ha eliminado de la plataforma.");
        this.connections.remove(id);*/
        this.launcher.unregisterConnection(id);
    }
    
    protected SenderConnection getConnection(String id){
        /*System.out.println("[SOL] La conexión "+id+" se ha eliminado de la plataforma.");
        this.connections.remove(id);*/
        return this.launcher.getConnection(id);
    }
    
    protected void registerService(String type,Service service){
        this.launcher.registerService(type, service);
    }
    
    protected void unregisterService(String type,String nameService){
        this.launcher.unregisterService(type, nameService);
    }
    
    protected void unregisterService(String name){
        launcher.unregisterService(name);
    }
    
    protected String queryService(String type){
        String res=null;
        
        List<Service> ser=this.launcher.queryService(type);
        if(ser==null || ser.isEmpty()){
            //System.out.println("[SAP] NO hay servicios de ese tipo disponibles");
            res="(ANSWER NO_AVAILABLE)";
        }else{
            //System.out.println("[SAP] Hay "+ser.size()+" servicios de ese tipo disponibles");
            res="(ANSWER ";
            for(int i=0;i<ser.size();i++){
                res=res+ser.get(i).toString()+" ";
            }
            res=res.substring(0, res.length()-1);
            res=res+")";
        }
        return res;
    }

    void unregisterAgentFromGroup(String groupName, String agent) {
        this.launcher.unregisterAgentFromGroup(groupName, agent);
    }

    boolean isConnectionRegistered(String agentName, BTConnection aThis) {
        return this.launcher.isConnectionRegistered(agentName, aThis);
    }
}
