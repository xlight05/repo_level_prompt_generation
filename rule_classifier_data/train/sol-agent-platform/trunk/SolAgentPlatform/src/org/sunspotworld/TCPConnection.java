/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sunspotworld;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Inma
 */
public class TCPConnection extends Thread implements SenderConnection {

    private Socket server;
    private String line, input;
    private Boolean end;
    private TCPListener listener;
    private BufferedReader reader;
    private PrintStream printer;
    private String id;
    private Boolean d = true;
    String ip;
    String aid;

    public TCPConnection(Socket s, TCPListener list) {
        server = s;
        //id=server.getInetAddress().getHostAddress();
        this.listener = list;
        end = false;
        ip = server.getInetAddress().getHostAddress();
    }

    @Override
    public void run() {
        input = "";
        AuroraMessage msg;

        try {
            reader = new BufferedReader(new InputStreamReader(server.getInputStream()));
            printer = new PrintStream(server.getOutputStream());
            while (!end) {
                // lectura de mensaje.
                input = reader.readLine();
                /*
                 * Alternativa a esperar lectura de nuevas lineas, cuando se
                 * acabe de recibir el ultimo mensaje desde el agente se cerrará
                 * la conexión con ese agente y se volvera a esperar a uno
                 * nuevo.
                 */
                /*
                 * if(reader.ready()){ input = reader.readLine(); }else{
                 * input=null; end=true; }
                 */

                //Mesh WIFI test
                //if(input!=null && input.equals("Ola k ase")) this.printer.println("Aki toy o k ase");

                if (input != null && input.startsWith("*HELLO*")) {
                    input = input.substring(7);
                    if (input.startsWith("@")) {
                        input = AuroraMessage.createAuroraMessage(input.substring(1));
                    }
                }
                if (input != null && input != "") { //&& !input.contains("HELLO")){
                    Long initial = System.currentTimeMillis();
                    msg = new AuroraMessage(input);
                    //System.out.println("[SOL] Se ha recibido un mensaje con el id "+msg.getConverstionId());
                    // 
                    if (msg.getOntology().equals("REG_ONTOLOGY")) {
                        // Registrar agente en la plataforma
                        if (msg.getProtocol().equals("RegisterAgent")) {
                            TreeMap<String, String> cont = ClassifyConnection.classifyContent(msg.getContent());
                            this.setAgentName(cont.get("agent"));
                            StringTokenizer st = new StringTokenizer(this.getAgentName(), "$$");
                            String ag1 = null, ag = null;
                            if (st.countTokens() == 2) {
                                ag1 = st.nextToken();
                                ag = st.nextToken(); //desired name
                                this.listener.registerConnection(this.getAgentName(), this, cont.get("mas"), cont.get("cat"));
                            } else {
                                ag = this.getAgentName();
                            }
                            this.listener.registerConnection(ag, this, cont.get("mas"), cont.get("cat"));
                            // Se envía la confirmación al agente
                            AuroraMessage reply = msg.createReply();
                            if (this.listener.isConnectionRegistered(ag, this)) {
                                reply.setContent("La conexion ha sido registrada correctamente.");
                                reply.setPerformative(AuroraMessage.CONFIRM);
                            } else {
                                reply.setContent("La conexion no pudo ser registrada correctamente.");
                                reply.setPerformative(AuroraMessage.FAILURE);
                            }
                            reply.setSender("HOST");
                            //this.sendMessage(reply);
                            this.listener.sendMessage(reply);
                            if (ag1 != null) {
                                this.listener.unregisterConnection(id);
                            }
                            this.setAgentName(ag);
                            // Registrar un grupo   
                        } else if (msg.getProtocol().equals("RegisterGroup")) {
                            String ipport = null;
                            String group = msg.getContent();
                            String groupName;
                            String category = "";
                            String pass = "";
                            boolean unicast = false;
                            boolean success = true;
                            String guide = getAgentName();

                            StringTokenizer st = new StringTokenizer(group, "#");
                            if (st.countTokens() > 3) {
                                groupName = st.nextToken();
                                if (st.nextToken().equalsIgnoreCase("UNICAST")) {
                                    unicast = true;
                                }
                                category = st.nextToken();
                                pass = st.nextToken();
                            } else if (st.countTokens() > 2) {
                                groupName = st.nextToken();
                                if (st.nextToken().equalsIgnoreCase("UNICAST")) {
                                    unicast = true;
                                }
                                category = st.nextToken();
                            } else if (st.countTokens() > 1) {
                                groupName = st.nextToken();
                                if (st.nextToken().equalsIgnoreCase("UNICAST")) {
                                    unicast = true;
                                }
                            } else {
                                groupName = st.nextToken();
                            }

                            //String ip=tokenizer.nextToken();
                            //Integer portInteger=new Integer(tokenizer.nextToken());
                            //int port=portInteger.intValue();                     

                            GroupConnection gc = null;
                            // Comprobamos si el grupo no ha sido registrado ya.
                            if (!this.listener.isConnectionRegistered(groupName)) {
                                // Se crea el objeto sender connection.
                                //mc=new MulticastConnection("231.15.20.1",5000);
                                //this.listener.registerConnection(groupName, mc); 
                                gc = new GroupConnection(groupName, pass);
                                if (category.equals("guide")) {
                                    gc.setGuide(guide);
                                }

                                ipport = gc.generateMulticastConnection();
                                if (unicast) {
                                    gc.addConnection(this); //For simulate Multicast, GroupConnection.sendMessage also modified.
                                }

                                gc.addAgentConnection(null, msg.getSender(), TypeConnection.TYPE_TCPMC);
                                //System.out.println("Se ha registrado el grupo con id "+groupName+" IP "+ipport);
                                this.listener.registerConnection(groupName, gc);
                            } else {
                                gc = (GroupConnection) this.listener.getConnection(groupName);
                                if (gc.getPass().equalsIgnoreCase(pass)) {
                                    ipport = gc.getIPPort(TypeConnection.TYPE_TCPMC);
                                    if (category.equals("guide")) {
                                        String g = gc.getGuide();
                                        if (!this.listener.getMembersGroup(groupName).contains(g)) {
                                            gc.setGuide(guide);
                                        }
                                    }
                                    if (unicast) {
                                        gc.addConnection(this); //For simulate Multicast, GroupConnection.sendMessage also modified.
                                    }
                                    gc.addAgentConnection(null, msg.getSender(), TypeConnection.TYPE_TCPMC);
                                    this.listener.registerConnection(groupName, gc);

                                } else {
                                    success = false;
                                }
                            }

                            if (unicast) {
                                ipport = "-"; //In order not to create a Multicast socket in the agent.
                            }
                            // Se envía el mensaje de replica.
                            AuroraMessage reply = msg.createReply();
                            //reply.setContent(groupName + ":" + ipport);
                            if (success) {
                                if (category.equals("visitor")) {
                                    reply.setContent(groupName + "#" + ipport + "#OK#" + gc.getGuide());
                                } else {
                                    reply.setContent(groupName + "#" + ipport + "#OK");
                                }
                            } else {
                                reply.setContent(groupName + "#" + ipport + "#FAIL");
                                //No se ha contemplado aqui la posibilidad de error por existir ya el grupo.
                            }
                            reply.setPerformative(AuroraMessage.CONFIRM);
                            reply.setSender("HOST");
                            this.sendMessage(reply);
                        } else if (msg.getProtocol().equals("LeaveGroup")) {
                            String groupName = msg.getContent();
                            String agent = msg.getSender();

                            AuroraMessage reply = msg.createReply();
                            reply.setContent(groupName);
                            reply.setSender("HOST");

                            // Comprobamos si el grupo ha sido registrado ya.
                            if (this.listener.isConnectionRegistered(groupName)) {
                                reply.setPerformative(AuroraMessage.CONFIRM);
                                this.listener.unregisterAgentFromGroup(groupName, agent);
                                GroupConnection gc = (GroupConnection) this.listener.getConnection(groupName);
                                if (gc != null && gc.getGuide().equals(agent)) {
                                    gc.setGuide("");
                                }
                            } else {
                                reply.setPerformative(AuroraMessage.FAILURE);
                            }
                            // Se envía el mensaje de replica.
                            this.sendMessage(reply);
                        } else if (msg.getProtocol().equals("UnregisterAgent")) {
                            String agentName = msg.getSender();
                            AuroraMessage reply = msg.createReply();
                            if (this.listener.isConnectionRegistered(agentName, this)) {
                                this.listener.unregisterConnection(agentName);
                                reply.setContent("La conexion TCP ha sido desregistrada correctamente.");
                                reply.setPerformative(AuroraMessage.CONFIRM);
                            } else {
                                reply.setContent("La conexion TCP no pudo ser desregistrada correctamente.");
                                reply.setPerformative(AuroraMessage.FAILURE);
                            }
                            // Se envía la confirmación al agente
                            reply.setSender("HOST");
                            this.sendMessage(reply);
                            end = true;
                            // Registrar un grupo   
                        }
                    } else if (msg.getOntology().equals("DF_ONTOLOGY")) {
                        AuroraMessage reply = msg.createReply();
                        reply.setSender("HOST");
                        if (msg.getProtocol().equals("RegisterService")) {
                            this.processService(msg);
                            // Se envía la confirmación al agente                            
                            reply.setContent("El servicio ha sido registrado correctamente.");
                            reply.setPerformative(AuroraMessage.CONFIRM);
                            this.sendMessage(reply);
                        } else if (msg.getProtocol().equals("UnregisterService")) {
                            //Se desregistra el servicio del cual el propietario es el emisor del AuroraMessage
                            this.processUnregisterService(msg.getSender());
                            // Se envía la confirmación al agente                            
                            reply.setContent("El servicio ha sido desregistrado correctamente.");
                            reply.setPerformative(AuroraMessage.CONFIRM);
                            this.sendMessage(reply);
                        } else if (msg.getProtocol().equals("QueryService")) {
                            //if(d)System.out.println(id+" ha hecho una consulta sobre un servicio.");
                            reply.setContent(this.listener.queryService(msg.getContent()));
                            reply.setPerformative(AuroraMessage.INFORM_REF);
                            this.sendMessage(reply);
                        }
                        // Se envía el mensaje al destinatario.
                    } else if (msg.getOntology().equals("OTHER_ONTOLOGY")) {
                        AuroraMessage reply = msg.createReply();
                        reply.setSender("HOST");
                        if (msg.getProtocol().equals("GetMembersProtocol")) {
                            String groupName = msg.getContent();
                            String agent = msg.getSender();

                            List<String> members = new LinkedList<String>();
                            if (groupName != null) {
                                members = this.listener.getMembersGroup(groupName);
                                if (members != null) {
                                    members.remove(agent);
                                }
                            }
                            reply.setContent(members.toString());
                            reply.setSender("HOST");
                            reply.setPerformative(AuroraMessage.CONFIRM);
                            this.sendMessage(reply);

                            /*String group_guide = msg.getContent();
                             StringTokenizer st = new StringTokenizer(group_guide, "#");
                             String group = st.nextToken();
                             String guide = "";
                             if (st.hasMoreTokens()) {
                             guide = st.nextToken();
                             }

                             String agent = msg.getSender();

                             List<String> members = new LinkedList<String>();
                             if (group != null) {
                             members = this.listener.getMembersGroup(group);
                             if (members != null) {
                             members.remove(guide);
                             }
                             }
                             reply.setContent(members.toString());
                             Vector<String> rec = new Vector<String>();
                             rec.add(guide);
                             reply.setReceivers(rec);
                             reply.setSender("HOST");
                             reply.setPerformative(AuroraMessage.CONFIRM);
                             this.sendMessage(reply);*/
                        } else if (msg.getProtocol().equals("GetGroupsProtocol")) {
                            String agent = msg.getSender();

                            List<String> groups = new LinkedList<String>();
                            groups = this.listener.getGroups();
                            List<String> aux = groups;
                            if (!msg.getContent().equalsIgnoreCase("security")) {
                                aux = removeSpecialGroups(groups);
                            }
                            reply.setContent(aux.toString());
                            reply.setSender("HOST");
                            reply.setPerformative(AuroraMessage.CONFIRM);
                            this.sendMessage(reply);
                        } else if (msg.getProtocol().equals("UpdateProtocol")) {
                            String group = msg.getContent();
                            GroupConnection gc = null;

                            if (this.listener.isConnectionRegistered(group)) {
                                gc = (GroupConnection) this.listener.getConnection(group);
                                reply.setContent(gc.getGuide());
                            } else {
                                reply.setContent("");
                            }
                            reply.setSender("HOST");
                            reply.setPerformative(AuroraMessage.CONFIRM);
                            this.sendMessage(reply);
                        } else if (msg.getProtocol().equals("AboutAgentProtocol")) {
                            String agent_group = msg.getContent();
                            StringTokenizer st = new StringTokenizer(agent_group, "#");
                            String agent = st.nextToken();
                            String group = null;
                            String group_guide = null;

                            if (st.hasMoreTokens()) {
                                group = st.nextToken();
                            }
                            String groups = this.listener.getGroupsAgent(agent);
                            if (group != null && this.listener.isConnectionRegistered(group)) {
                                GroupConnection gc = (GroupConnection) this.listener.getConnection(group);
                                group_guide = gc.getGuide();
                            }

                            if (group_guide == null) {
                                reply.setContent(groups);
                            } else {
                                reply.setContent(groups + "#" + group_guide);
                            }
                            reply.setSender("HOST");
                            reply.setPerformative(AuroraMessage.CONFIRM);
                            this.sendMessage(reply);
                        }
                    } else {
                        this.listener.sendMessage(msg);
                        Long end = System.currentTimeMillis();
                        Long result = end - initial;
                        //System.out.println("El tiempo desde que llega a TCP connection hasta que envía es: "+result);
                        //reader = new BufferedReader( new InputStreamReader ( server.getInputStream() ) );
                    }
                } else {
                    end = true;
                }
            }
        } catch (Exception ioe) {
            ioe.printStackTrace();
        } finally {
            //Unregister the connection
/*
             * En principio comentaremos estos 2 desregistros hasta estudiar si
             * realmente son necesarios
             */
            //this.listener.unregisterConnection(this.getAgentName());
            //this.listener.unregisterService(this.getAgentName());
            try {
                reader.close();
                printer.close();
                server.close();
            } catch (IOException ex) {
                Logger.getLogger(TCPConnection.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    synchronized public void sendMessage(AuroraMessage msg) {
        //if(d)System.out.println("Se ha enviado un mensaje para "+id);
        this.printer.println(msg.toString());
        //this.listener.sendMessage(msg);
    }

    public void stopConnection() {
        this.end = true;
    }

    private void processService(AuroraMessage msg) {
        String name, type, aux;

        Service ser = new Service();
        ser.setOwner(this.getAgentName());
        String content = msg.getContent();
        StringTokenizer token = new StringTokenizer(content);
        // Se extrae "(Servicio"
        token.nextToken();
        // Se extrae "(Nombre"
        token.nextToken();
        // Se extre el nombre del servicio y se quita el ")" final.
        aux = token.nextToken();
        name = aux.substring(0, aux.length() - 1);
        // Se extrae el (Type
        aux = token.nextToken();
        // Se extrae el tipo de servicio
        aux = token.nextToken();
        type = aux.substring(0, aux.length() - 2);
        //System.out.append("El tipo del servicio es "+type);
        ser.setName(name);
        //System.out.println("[DEBUG] Se ha procesado como name="+ser.getName()+" owner="+ser.getOwner());
        this.listener.registerService(type, ser);
    }

    private void processUnregisterService(String owner) {
        this.listener.unregisterService(owner);
    }

    private void processUnregisterServiceByTypeName(AuroraMessage msg) {
        String nameService, type, aux;

        //Service ser=new Service();
        String content = msg.getContent();
        StringTokenizer token = new StringTokenizer(content);
        // Se extrae "(Servicio"
        token.nextToken();
        // Se extrae "(Nombre"
        token.nextToken();
        // Se extre el nombre del servicio y se quita el ")" final.
        aux = token.nextToken();
        nameService = aux.substring(0, aux.length() - 1);
        // Se extrae el (Type
        aux = token.nextToken();
        // Se extrae el tipo de servicio
        aux = token.nextToken();
        type = aux.substring(0, aux.length() - 2);
        //System.out.println("[DEBUG] Se ha procesado como name="+ser.getName()+" owner="+ser.getOwner());
        this.listener.unregisterService(type, nameService);
    }

    private void setAgentName(String n) {
        this.id = n;
    }

    public String getAgentName() {
        return this.id;
    }

    @Override
    public String getIP() {
        return ip;
    }

    @Override
    public String getConnectionProtocol() {
        return "Android";
    }

    @Override
    public int getTypeConnection() {
        return TypeConnection.TYPE_TCPMC;
    }

    private List<String> removeSpecialGroups(List<String> groups) {
        List<String> aux = new LinkedList<String>();
        for (String g : groups) {
            if (!g.equalsIgnoreCase("museum") && !g.equalsIgnoreCase("guides") && !g.equalsIgnoreCase("security") && !g.equalsIgnoreCase("sensors")) {
                aux.add(g);
            }
        }

        return aux;
    }
}
