/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.sunspotworld;

import com.sun.spot.io.j2me.udp.UDPConnection;
import com.sun.spot.io.j2me.udp.UDPDatagram;
import com.sun.spot.util.Utils;
import java.util.List;
import java.util.StringTokenizer;
import java.util.TreeMap;
import javax.microedition.io.Connector;

/**
 *
 * @author Inma
 */
public class SunSpotListener extends Thread{
    
    private Boolean end;
    private int LSN_PORT,SND_PORT;
    private SunSpotHostApplication listener;
    
    
    public SunSpotListener(String name, SunSpotHostApplication ap, int lPort,int sPort){
        super(name);
        this.LSN_PORT=lPort;
        this.SND_PORT=sPort;
        this.end=false;
        this.listener=ap;
    }
    
    @Override
    public void run(){
        
        UDPConnection recConn;
        String address;
        UDPDatagram dg = null;
        String url="udp://:"+this.LSN_PORT;
        //System.out.println("[SOL] Listening messages to send in: "+url);
        Utils.sleep(1000);  // Let IPv6 startup under us
        
        while(!end){
            try {
                //System.out.println("[APP] Se espera a recibir un mensaje en la url: "+url);
                recConn=(UDPConnection)Connector.open(url);
                dg=(UDPDatagram)recConn.newDatagram(recConn.getMaximumLength());
                recConn.receive(dg);

                AuroraMessage msg=new AuroraMessage(dg.readUTF());
                //System.out.println("[SOL] Se ha recibido un mensaje: "+msg.getContent());
                //System.out.println("Vamos a asignar al mensaje "+msg.getConverstionId()+" el sender "+sender);
                //msg.setSender(sender);
                //System.out.println("[APP] Se ha recibido un mensaje.");
                // Mensaje para solicitar un servicio a la platform -> Reformar este código.
                if(msg.getOntology().equals("REG_ONTOLOGY")){
                    if(msg.getProtocol().equals("RegisterAgent")){
                        TreeMap<String,String> cont = ClassifyConnection.classifyContent(msg.getContent());
                        String agentName = (cont.get("agent"));                          
                        // Se crea la sender connection.
/*Mirar el tema de las direcciones getAdress y getSrcAddres si se tuviera problemas de comunicación*/                        
                        address=dg.getAddress();
                        byte[] dstAddres=dg.getSrcAddress();
                        SunSpotConnection ss=new SunSpotConnection(address,this.SND_PORT,dstAddres);                     
                        // Se registra en la plataforma.
                        this.listener.registerConnection(agentName, ss, cont.get("mas"), cont.get("cat"));
                        // Se envía el mensaje notificando el regsitro.
                        AuroraMessage reply=msg.createReply(); 
                        reply.setContent("La conexion ha sido registrada correctamente.");
                        reply.setPerformative(AuroraMessage.CONFIRM);
                        reply.setSender("HOST");
                        this.sendMessage(recConn, dg, msg);
                        //this.sendMessage(reply);
                    }else if(msg.getProtocol().equals("RegisterGroup")){
                            String ipport = null;
                            String groupName=msg.getContent();
                            GroupConnection gc = null;
                            // Comprobamos si el servicio no ha sido registrado ya.
                            if(!this.listener.isConnectionRegistered(groupName)){
                                gc = new GroupConnection(groupName);
                                ipport = gc.generateSunSpotMulticastConnection();
                                gc.addAgentConnection(null, msg.getSender(),TypeConnection.TYPE_SSMC);
                                //System.out.println("Se ha registrado el grupo con id "+groupName+" IP "+ipport);
                            }else{
                                gc = (GroupConnection) this.listener.getConnection(groupName);
                                ipport = gc.getIPPort(TypeConnection.TYPE_SSMC);
                                gc.addAgentConnection(null, msg.getSender(),TypeConnection.TYPE_SSMC);
                            }
                            
                            this.listener.registerConnection(groupName, gc);
                            // Se envía el mensaje de replica.
                            AuroraMessage reply=msg.createReply();
                            reply.setContent(groupName+":"+ipport);
                            reply.setPerformative(AuroraMessage.CONFIRM);
                            reply.setSender("HOST");
                            this.sendMessage(recConn, dg, reply);
                    }else if(msg.getProtocol().equals("LeaveGroup")){
                            String groupName=msg.getContent();                
                            String agent = msg.getSender();
                            
                            AuroraMessage reply=msg.createReply();
                            reply.setContent(groupName);
                            reply.setSender("HOST");
                            
                            GroupConnection gc = null;
                            // Comprobamos si el servicio no ha sido registrado ya.
                            if(this.listener.isConnectionRegistered(groupName)){
                                gc = (GroupConnection) this.listener.getConnection(groupName);
                                gc.unregisterAgent(agent);
                                //System.out.println("El agente Sun Spot "+agent+" ha dejado el grupo con id "+groupName);
                                reply.setPerformative(AuroraMessage.CONFIRM);
                            }else{
                                reply.setPerformative(AuroraMessage.FAILURE);
                            }
                            this.listener.unregisterConnection(agent);
                            // Se envía el mensaje de replica.
                            this.sendMessage(recConn, dg, msg);
                    }else if(msg.getProtocol().equals("UnregisterAgent")){
                            String agentName=msg.getSender();
                            this.listener.unregisterConnection(agentName);
                            AuroraMessage reply=msg.createReply(); 
                            reply.setContent("La conexion Sun Spot ha sido desregistrada correctamente.");
                            reply.setPerformative(AuroraMessage.CONFIRM);
                            reply.setSender("HOST");
                            this.sendMessage(recConn, dg, msg);
                    }
                }else if(msg.getOntology().equals("DF_ONTOLOGY")){
                    AuroraMessage reply=msg.createReply();
                    reply.setSender("HOST");
                    if(msg.getProtocol().equals("RegisterService")){
                        this.processService(msg);
                        // Se envía la confirmación al agente                            
                        reply.setContent("El servicio ha sido registrado correctamente.");
                        reply.setPerformative(AuroraMessage.CONFIRM);  
                        this.sendMessage(recConn, dg, reply);
                    }else if(msg.getProtocol().equals("UnregisterService")){
                        this.processUnregisterService(msg);
                        // Se envía la confirmación al agente                            
                        reply.setContent("El servicio ha sido desregistrado correctamente.");
                        reply.setPerformative(AuroraMessage.CONFIRM);  
                        this.sendMessage(recConn, dg, reply);
                    }else if(msg.getProtocol().equals("QueryService")){
                        List<Service> ans=this.listener.queryService(msg.getContent());
                        String content=this.processAnswer(ans);
                        reply.setContent(content);
                        reply.setPerformative(AuroraMessage.INFORM_REF);
                        this.sendMessage(recConn, dg, reply);
                    }
                }else{
                    this.listener.sendMessage(msg);
                }
                recConn.close();
            } catch (Exception e) {               
                //System.out.print("Ha habido un problema en ciclo de listener: ");
                e.printStackTrace();
                end=true;
            }
        }
    } 
    
    protected void sendMessage(AuroraMessage msg){
        this.listener.sendMessage(msg);
    }
    
    private boolean sendMessage(UDPConnection conn,UDPDatagram dg,AuroraMessage msg){
        UDPDatagram sendDg;
        boolean res=false;
        
        try{
            msg.setSender("HOST");
            //System.out.println("[SSCL] Se ha enviado mensaje a "+conn.getRemoteAddress().toString()+":"+conn.getRemotePort());
            // Se rellenan los campos del datagram y se envía.
            sendDg=(UDPDatagram)conn.newDatagram(conn.getMaximumLength());
/*Mirar el tema de las direcciones Src y Dst del datagrama dg*/            
            sendDg.setDstAddress(dg.getSrcAddress());
            sendDg.setDstPort(dg.getSrcPort());
            sendDg.writeUTF(msg.toString());
            conn.send(sendDg);
            res=true;
        }catch(Exception e){
            e.printStackTrace();
        }
      
        return res;
    }
    
    private String processAnswer(List<Service> services){
        String res;
        
        if(services==null || services.isEmpty()){
            //System.out.println("[SAP] NO hay servicios de ese tipo disponibles");
            res="(ANSWER NO_AVAILABLE)";
        }else{
            //System.out.println("[SAP] Hay "+ser.size()+" servicios de ese tipo disponibles");
            res="(ANSWER ";
            for(int i=0;i<services.size();i++){
                res=res+services.get(i).toString()+" ";
            }
            //res=res.substring(0, res.length()-1);
            res=res+")";
        }
        return res;
    }
    
    /*protected void sendMessageToAgent(AuroraMessage msg){
        UDPConnection conn;
        String aid;
        RealLocation loc;
        
        try{
            // Se manda un mensaje a cada uno de los agentes.
            for (int i=0;i<msg.getReceivers().size();i++){
                aid=(String) msg.getReceivers().get(i);
                loc=_ap.getAgent(aid);
                if(loc!=null){
                    switch (loc.getDevice()){
                        case SUNSPOT:
                            conn = (UDPConnection) Connector.open("udp://["+msg.getReceivers().get(i)+"]:"+this._SND_PORT);
                            Utils.sleep(1000);
                            System.out.println("[SSCL] Se abre la conexión "+"udp://["+msg.getReceivers().get(i)+"]:"+this._SND_PORT);
                            UDPDatagram rdg=(UDPDatagram)conn.newDatagram(conn.getMaximumLength());
                            rdg.setDstAddress(loc.getAddress());
                            rdg.setDstPort(this._SND_PORT);
                            rdg.writeUTF(msg.toString());
                            System.out.println("[SSCL] Send message to SunSpot");
                            conn.send(rdg);
                            conn.close();
                            break;
                        case ANDROID:
                            // Enviar mensajes usando datagramas a teléfonos Android.
                            //System.out.println("[SSCL] Send message to Android.");
                            this._ap.sendMessageToAndroid(msg);
                            break;
                    }
               }else{
                    System.out.println("[SSCL] No hay agentes con el identificador "+aid+".");
               }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }*/
    
    
    @Override
    public void destroy(){
        this.end=true;
    }

    private void processService(AuroraMessage msg) {
        String name,type,aux;

        Service ser=new Service();
        ser.setOwner(msg.getSender());
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
        this.listener.registerService(type, ser);
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
        this.listener.unregisterService(type, nameService);
    }
    
}
