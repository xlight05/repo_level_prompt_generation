/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sunspotworld;
 
//import javax.bluetooth.RemoteDevice;
import java.io.*;
import javax.microedition.io.*;
import javax.bluetooth.*;
//import javax.microedition.io.StreamConnection;
//import javax.microedition.io.*;

import bt.javax.microedition.io.Connection;
import java.util.StringTokenizer;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jose Luis
 */
public class BTConnection extends Thread implements SenderConnection{
    private bt.javax.microedition.io.StreamConnection connectionAndroid = null;
    private BTListener btlistener;
    //private SunSpotHostApplication listener;
    private Boolean end;
    private String agentName;
    InputStream inStream;
    private OutputStream outStream;
    private String btAddress;
    
    public BTConnection(String name, BTListener btl){
        this.btlistener=btl;
        end = false;
    }

    public BTConnection(bt.javax.microedition.io.StreamConnection connectionAndroid, BTListener btl) {
        this.connectionAndroid = connectionAndroid;
        this.btlistener = btl;
        end = false;
    }

    @Override
    public void run(){
        try{
            RemoteDevice devAndroid = RemoteDevice.getRemoteDevice((Connection) connectionAndroid);

            btAddress = devAndroid.getBluetoothAddress();

            System.out.println("[SOL] Listening messages to send in: "+btAddress);
            System.out.println("[SOL] Remote Android device name: "+devAndroid.getFriendlyName(true));
            
            //read string from spp client
            inStream = connectionAndroid.openInputStream();
            outStream = connectionAndroid.openOutputStream();
            //os = connectionAndroid.openDataOutputStream();
            
            //BufferedReader bReader=new BufferedReader(new InputStreamReader(inStream));
            //String lineRead=bReader.readLine();
            StringBuffer result;
            
            while(!end){
                result = new StringBuffer();
                byte[] inbuf = new byte[512];      
                do
                {
                	try{
	                  int nCount = inStream.read(inbuf);
	                  result.append(new String(inbuf, 0, nCount));
                	}catch(Exception e){
                		result = null;
                		end=true;
                	}
                }
                while (inStream.available() > 0);

                if(result!=null){
                    AuroraMessage msg = new AuroraMessage(result.toString());
                    
                    if(msg.getOntology().equals("REG_ONTOLOGY")){
                        // Registrar agente en la plataforma
                        if(msg.getProtocol().equals("RegisterAgent")){
                            TreeMap<String,String> cont = ClassifyConnection.classifyContent(msg.getContent());
                            this.setAgentName(cont.get("agent"));                          
                            this.btlistener.registerConnection(this.getAgentName(), this, cont.get("mas"),cont.get("cat"));
                            // Se envía la confirmación al agente
                            AuroraMessage reply=msg.createReply();
                            if(this.btlistener.isConnectionRegistered(this.getAgentName(),this)){
                                reply.setContent("La conexion ha sido registrada correctamente.");
                                reply.setPerformative(AuroraMessage.CONFIRM);
                            }else{
                                reply.setContent("La conexion no pudo ser registrada correctamente.");
                                reply.setPerformative(AuroraMessage.FAILURE);
                            }
                            reply.setSender("HOST");
                            this.sendMessage(reply);
                            //end=true;
                        }else if(msg.getProtocol().equals("RegisterGroup")){
                            String groupName=msg.getContent();
                            //String ip=tokenizer.nextToken();
                            //Integer portInteger=new Integer(tokenizer.nextToken());
                            //int port=portInteger.intValue();                     
                            
                            GroupConnection gc = null;
                            // Comprobamos si el grupo no ha sido registrado ya.
                            if(!this.btlistener.isConnectionRegistered(groupName)){
                                // Se crea el objeto sender connection.
                                gc = new GroupConnection(groupName);
                                gc.generateBTMulticastConnection();
                                gc.addAgentConnection(this,msg.getSender(),TypeConnection.TYPE_BTMC);
                                System.out.println("Se ha registrado el grupo con id "+groupName);
                            }else{
                                gc = (GroupConnection) this.btlistener.getConnection(groupName);
                                gc.getIPPort(TypeConnection.TYPE_BTMC);
                                gc.addAgentConnection(this,msg.getSender(),TypeConnection.TYPE_BTMC);
                            }

                            this.btlistener.registerConnection(groupName, gc);
                            // Se envía el mensaje de replica.
                            AuroraMessage reply=msg.createReply();
                            reply.setContent(groupName);
                            reply.setPerformative(AuroraMessage.CONFIRM);
                            reply.setSender("HOST");
                            this.sendMessage(reply);
                        }else if(msg.getProtocol().equals("LeaveGroup")){
                            String groupName=msg.getContent();                
                            String agent = msg.getSender();
                            
                            AuroraMessage reply=msg.createReply();
                            reply.setContent(groupName);
                            reply.setSender("HOST");
                            
                            //GroupConnection gc = null;
                            // Comprobamos si el grupo ha sido registrado ya.
                            if(this.btlistener.isConnectionRegistered(groupName)){
                                //gc = (GroupConnection) this.btlistener.getConnection(groupName);
                                //boolean res = gc.unregisterBTConnection(this);
                                //if(res){
                                    //gc.unregisterAgent(agent);
                                   // System.out.println("El agente Bluetooth "+agent+" ha dejado el grupo con id "+groupName);
                                    reply.setPerformative(AuroraMessage.CONFIRM);
                                    this.btlistener.unregisterAgentFromGroup(groupName,agent);
                                /*}else{
                                    reply.setPerformative(AuroraMessage.FAILURE);
                                }*/
                            }else{
                                reply.setPerformative(AuroraMessage.FAILURE);
                            }
                            // Se envía el mensaje de replica.
                            this.sendMessage(reply);
                        }else if(msg.getProtocol().equals("UnregisterAgent")){
                            String agentName = msg.getSender();
                            AuroraMessage reply=msg.createReply();
                            if(this.btlistener.isConnectionRegistered(agentName,this)){
                                this.btlistener.unregisterConnection(agentName);
                                reply.setContent("La conexion Bluetooth ha sido desregistrada correctamente.");
                                reply.setPerformative(AuroraMessage.CONFIRM);

                            }else{
                                reply.setContent("La conexion Bluetooth no pudo ser desregistrada correctamente.");
                                reply.setPerformative(AuroraMessage.FAILURE);
                            }
                            // Se envía la confirmación al agente
                            reply.setSender("HOST");
                            this.sendMessage(reply);
                            end=true;
                         // Registrar un grupo   
                        }
                    } else if(msg.getOntology().equals("DF_ONTOLOGY")){
                        AuroraMessage reply=msg.createReply();
                        reply.setSender("HOST");
                        if(msg.getProtocol().equals("RegisterService")){
                            this.processService(msg);
                            // Se envía la confirmación al agente                            
                            reply.setContent("El servicio ha sido registrado correctamente.");
                            reply.setPerformative(AuroraMessage.CONFIRM);  
                            this.sendMessage(reply);
                        }else if(msg.getProtocol().equals("UnregisterService")){
                            this.processUnregisterService(msg);
                            // Se envía la confirmación al agente                            
                            reply.setContent("El servicio ha sido desregistrado correctamente.");
                            reply.setPerformative(AuroraMessage.CONFIRM);  
                            this.sendMessage(reply);
                        }else if(msg.getProtocol().equals("QueryService")){
                            //if(d)System.out.println(id+" ha hecho una consulta sobre un servicio.");
                            reply.setContent(this.btlistener.queryService(msg.getContent()));
                            reply.setPerformative(AuroraMessage.INFORM_REF);
                            this.sendMessage(reply);
                        }
                    // Se envía el mensaje al destinatario.
                    }else{
                        this.btlistener.sendMessage(msg);
                    }
                }
            }
        } catch (Exception e) {               
                //System.out.print("Ha habido un problema en ciclo de listener: ");
                e.printStackTrace();
        }finally{
            //this.btlistener.stopListener();
            try {
                inStream.close();
                outStream.close();
                connectionAndroid.close();
            } catch (IOException ex) {
                Logger.getLogger(BTConnection.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void sendMessage(AuroraMessage msg) {
                //OutputStream outStream = null;
        try {
                outStream.write(msg.toString().getBytes());
                /*OutputStreamWriter osw = new OutputStreamWriter(outStream);
                //osw.write(msg.toString());
                //DataOutputStream os = connectionAndroid.openDataOutputStream();
	        PrintWriter pWriter=new PrintWriter(osw);
	        pWriter.write(msg.toString()+"\r\n");
	        pWriter.flush();
	        pWriter.close();*/
	        //streamConnNotifierAndroid.close();
        } catch (Exception ex) {
            Logger.getLogger(BTListener.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
                /*try {
                    //outStream.flush();
                    //connectionAndroid.close();
                } catch (IOException ex) {
                    Logger.getLogger(BTListener.class.getName()).log(Level.SEVERE, null, ex);
                }*/
        }
    }

    public String getAgentName() {
        return agentName;
    }

    private void setAgentName(String agentName) {
        this.agentName = agentName;
    }
    
    public String getBTAddress(){
        return btAddress;
    }
    
    private void processService(AuroraMessage msg){
        String name,type,aux;

        Service ser=new Service();
        ser.setOwner(this.getAgentName());
         String content=msg.getContent();
        StringTokenizer token=new StringTokenizer(content);
        // Se extrae "(Servicio"
        token.nextToken();
        // Se extrae "(Nombre"
        token.nextToken();
        // Se extre el nombre del servicio y se quita el ")" final.
        aux=token.nextToken();
        name=aux.substring(0, aux.length()-1);
        // Se extrae el (Type
        aux=token.nextToken();
        // Se extrae el tipo de servicio
        aux=token.nextToken();
        type=aux.substring(0, aux.length()-2);
        //System.out.append("El tipo del servicio es "+type);
        ser.setName(name);
        //System.out.println("[DEBUG] Se ha procesado como name="+ser.getName()+" owner="+ser.getOwner());
        this.btlistener.registerService(type, ser);
    }
    
    private void processUnregisterService(AuroraMessage msg) {
        String nameService,type,aux;

        //Service ser=new Service();
        String content=msg.getContent();
        StringTokenizer token=new StringTokenizer(content);
        // Se extrae "(Servicio"
        token.nextToken();
        // Se extrae "(Nombre"
        token.nextToken();
        // Se extre el nombre del servicio y se quita el ")" final.
        aux=token.nextToken();
        nameService=aux.substring(0, aux.length()-1);
        // Se extrae el (Type
        aux=token.nextToken();
        // Se extrae el tipo de servicio
        aux=token.nextToken();
        type=aux.substring(0, aux.length()-2);
        //System.out.println("[DEBUG] Se ha procesado como name="+ser.getName()+" owner="+ser.getOwner());
        this.btlistener.unregisterService(type, nameService);
    }

    @Override
    public String getIP() {
        return btAddress;
    }

    @Override
    public String getConnectionProtocol() {
        return "Bluetooth";
    }

    @Override
    public int getTypeConnection() {
        return TypeConnection.TYPE_BTMC;
    }
}