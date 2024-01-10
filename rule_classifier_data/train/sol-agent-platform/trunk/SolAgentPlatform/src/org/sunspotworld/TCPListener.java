/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sunspotworld;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.TreeMap;

/**
 *
 * @author Inma
 */
public class TCPListener extends Thread{
    
    private int PORT;
    private Boolean fin;
    //private Hashtable<String,AndroidTCPConnection> connections;
    private SunSpotHostApplication launcher;
    
    public TCPListener(int p,SunSpotHostApplication l){
        this.PORT=p;
        fin=false;
        //this.connections=new Hashtable<String,AndroidTCPConnection>();
        this.launcher=l;
    }
    
    public void stopListener(){
        fin=true;
    }
    
    @Override
    public void run(){
        
        TCPConnection conn;    
        String id;
        
        try{
            
            //System.out.println("[SOL] SolAgentPlatform is running and waiting for requests.");
            ServerSocket listener=new ServerSocket(PORT);
            //System.out.println("A");
            Socket server;
            while(!fin){
                //System.out.println("B");
                server=listener.accept();
                //System.out.println("Se ha registrado un nuevo agente.");
                conn=new TCPConnection(server,this);
                
                conn.start();
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
    
    protected Boolean isConnectionRegistered(String id){
        return launcher.isConnectionRegistered(id);
    }
    
    protected void registerConnection(String id,SenderConnection c){   
        /*System.out.println("[SOL] Se ha registrado la conexión con el id: "+id);
        this.connections.put(id, c);*/
        this.launcher.registerConnection(id, c);
    }
    
    protected void registerConnection(String id,SenderConnection c, String mas, String cat){   
        /*System.out.println("[SOL] Se ha registrado la conexión con el id: "+id);
        this.connections.put(id, c);*/
        this.launcher.registerConnection(id, c, mas, cat);
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
    
    protected void sendMessage(AuroraMessage msg){
        this.launcher.sendMessage(msg);
    }

    void unregisterAgentFromGroup(String groupName, String agent) {
        this.launcher.unregisterAgentFromGroup(groupName, agent);
    }

    boolean isConnectionRegistered(String agentName, TCPConnection aThis) {
        return this.launcher.isConnectionRegistered(agentName, aThis);
    }

    List<String> getMembersGroup(String groupName) {
        return this.launcher.getAgentsGroup(groupName);
    }

    List<String> getGroups() {
        return this.launcher.getIdGroups();
    }

    String getGroupsAgent(String agent) {
        TreeMap<String,String> map = this.launcher.getAllInfoAgent(agent);
        return map.keySet().toString();
    }
}
