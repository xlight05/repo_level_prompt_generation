/*
 * SunSpotHostApplication.java
 *
 * Created on 29-ago-2011 13:28:11;
 */
package org.sunspotworld;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import org.sunspotUI.GroupConnectionsView;
import org.sunspotUI.PortsView;
import org.sunspotUI.SolAgentPlatformView;
import org.sunspotUI.SunSpotView;
//import java.a

/**
 * Sample Sun SPOT host application
 */
public class SunSpotHostApplication {

    // Agentes/Aplicaciones que se han registrado en la plataforma/aplicación
    // <Identificador de la aplicación,Socket de comunicaciones>
    private static Hashtable<String, SenderConnection> _connections;
    // Servicios que han registrado las aplicaciones en la plataforma
    private static Hashtable<String, List<Service>> _services;
    private static List<String> _idgroups;
    //private SunSpotView ssv = null;
    private SolAgentPlatformView ssv = null;
    private Integer BRD_PORT = null;
    private Integer LST_PORT = null;
    private Integer SND_PORT = null;
    private Integer TCP_PORT = null;

    /**
     * Print out our radio address.
     */
    public void run() {
        // Listener para Sockets Java normales.
        //TCPListener alistener=new TCPListener(8080,this);
        //alistener.start();
        //if(ssv == null) ssv = new SunSpotView(this);
        if (ssv == null) {
            ssv = new SolAgentPlatformView(this);
        }
        //ssv.setVisible(true);
        PortsView pv = new PortsView(this);
        pv.setVisible(true);

        try {
            while (pv.isVisible()) {
                Thread.sleep(1000);
            }
        } catch (InterruptedException ex) {
            Logger.getLogger(SunSpotHostApplication.class.getName()).log(Level.SEVERE, null, ex);
        }

        ssv.setVisible(true);
        // ....
        UDPBroadcaster ssbroadcaster = new UDPBroadcaster("BROADCAST", BRD_PORT);
        ssbroadcaster.start();
        // Listener para SunSPOT
        SunSpotListener sslistener = new SunSpotListener("listener", this, LST_PORT, SND_PORT);
        sslistener.start();
        //Listener para TCP
        TCPListener tcplistener = new TCPListener(TCP_PORT, this);
        tcplistener.start();
        if (pv.activatedBluetoothSupport()) {
            //Listener para Bluetooth
            BTListener btlistener = new BTListener(this);
            btlistener.start();
        }
    }

    public void setPorts(int brd, int lst, int snd, int tcp) {
        BRD_PORT = brd;
        LST_PORT = lst;
        SND_PORT = snd;
        TCP_PORT = tcp;
    }

//Methods about connections -agents and groups-    
    protected void registerConnection(String id, SenderConnection c) {
        //System.out.println("[SOL] Se ha registrado la conexión con el id: "+id);
        boolean registered = false;
        if (!isConnectionRegistered(id)) {
            _connections.put(id, c);
            registered = false;
        } else {
            registered = true;
        }
        try {
            GroupConnection gc = (GroupConnection) c;
            if (!_idgroups.contains(id)) {
                _idgroups.add(id);
                ssv.updateGroups();
                ssv.notifyGroupAdded(id);
            } else {
                _connections.put(id, c);
                ssv.updateGroups();
            }
        } catch (Exception e) {
            ssv.notifyAgentAdded(registered, id);
            ssv.updateTreeAgentsAdd(registered, "Others", id, "other_category");
        }
    }

    protected void registerConnection(String id, SenderConnection c, String mas, String cat) {
        //System.out.println("[SOL] Se ha registrado la conexión con el id: "+id);
        boolean registered = false;
        if (!isConnectionRegistered(id)) {
            _connections.put(id, c);
            registered = false;
        } else {
            registered = true;
        }
        try {
            GroupConnection gc = (GroupConnection) c;
            if (!_idgroups.contains(id)) {
                _idgroups.add(id);
                ssv.updateGroups();
                ssv.notifyGroupAdded(id);
            } else {
                ssv.updateGroups();
            }
        } catch (Exception e) {
            ssv.notifyAgentAdded(registered, id);
            ssv.updateTreeAgentsAdd(registered, mas, id, cat);
        }
    }

//Revisar la conexion de grupos de SunSpot
    protected void registerConnectionGroupSunSpot(String id, GroupConnection c) {
        //System.out.println("[SOL] Se ha registrado la conexión con el id: "+id);
        _connections.put(id, c);
        try {
            GroupConnection gc = (GroupConnection) c;
            _idgroups.add(id);
            //ssv.updateGroups(_idgroups);
            ssv.updateGroups();
        } catch (Exception e) {
        }
    }

    protected void registerConnectionSunSpot(String id, SunSpotConnection ss) {
        //System.out.println("[SOL] Se ha registrado la conexión con el id: "+id);
        _connections.put(id, ss);
        //ssv.addAgentList(id);
    }

    protected void unregisterConnection(String id) {
        //System.out.println("[SOL] La conexión "+id+" se ha eliminado de la plataforma.");
        //ssv.removeAgentList(id);
        unregisterAgentFromAllGroups(id);
        if (_connections.remove(id) != null) {
            ssv.notifyAgentRemoved(true, id);
            ssv.updateTreeAgentsRemove(id);
        } else {
            ssv.notifyAgentRemoved(false, id);
        }
        ssv.updateGroups();

    }

    private void unregisterAgentFromAllGroups(String id) {
        String groupName;
        for (int i = 0; i < _idgroups.size(); i++) {
            groupName = _idgroups.get(i);
            unregisterAgentFromGroup(groupName, id);
        }
    }

    protected void unregisterAgentFromGroup(String groupName, String id) {
        boolean found = false;
        if (isConnectionRegisteredInGroup(groupName, id)) {
            found = true;
            GroupConnection gc = (GroupConnection) _connections.get(groupName);
            gc.unregisterAgent(id);
            if (gc.getGuide().equals(id)) {
                gc.setGuide("");
            }
            _connections.put(groupName, gc);
            ssv.notifyAgentGroupRemoved(groupName, id, found);
            ssv.updateGroups();
        }
        if (!found) {
            ssv.notifyAgentGroupRemoved(groupName, id, found);
        }
    }

    protected void unregisterGroup(String groupName) {
        //System.out.println("[SOL] El grupo "+groupName+" se ha eliminado de la plataforma.");
        for (Map.Entry<String, SenderConnection> entry : _connections.entrySet()) {
            String id_conn = entry.getKey();
            //SenderConnection sc = entry.getValue();
            this.unregisterAgentFromGroup(groupName, id_conn);
        }
        _connections.remove(groupName);
        //ssv.removeGroupSniffer(groupName);
        _idgroups.remove(groupName);
        ssv.updateGroups();
        ssv.notifyGroupRemoved(groupName);
    }

    private boolean isConnectionRegisteredInGroup(String groupName, String id) {
        if (_idgroups.contains(groupName)) {
            GroupConnection gc = (GroupConnection) _connections.get(groupName);
            List<String> agents = gc.getAgents();
            return agents.contains(id);
        } else {
            return false;
        }
    }

    protected boolean isConnectionRegistered(String id) {
        return _connections.containsKey(id);
    }

    protected boolean isConnectionRegistered(String id, SenderConnection c) {
        SenderConnection sc = _connections.get(id);
        if (sc != null) {
            boolean eq = sc.equals(c);
            return eq;
        } else {
            return false;
        }
    }

    public List<String> getAgentsGroup(String groupName) {
        List<String> agents = null;
        try {
            GroupConnection gc = (GroupConnection) _connections.get(groupName);
            agents = gc.getAgents();
        } catch (Exception e) {
            //System.out.println(e.getLocalizedMessage());
        }
        return agents;
    }

    public SenderConnection getConnection(String conn) {
        return _connections.get(conn);
    }

    public List<String> getIdGroups() {
        return _idgroups;
    }

//Methods about services    
    protected void registerService(String type, Service ser) {
        List<Service> services;

        if (this._services.containsKey(type)) {
            services = this._services.get(type);
        } else {
            services = new ArrayList<Service>();
        }
        services.add(ser);
        this._services.put(type, services);
        if (ssv == null) {
            ssv = new SolAgentPlatformView(this);
        }
        ssv.updateServices(_services);

        //System.out.println("[SOL] Se registra el servicio cuyo nombre es "+ser.getName()+
        //        " del tipo "+type+" cuyo dueño es "+ser.getOwner());

        ssv.notifyServiceAdded(type, ser);
    }

    protected void unregisterService(String type, String nameService) {
        Enumeration<String> keys = this._services.keys();
        Boolean end = false;
        String owner = "";
        int i = 0;
        List<Service> ser;

        if (_services.containsKey(type)) {
            ser = _services.get(type);
            while (!end && i < ser.size()) {
                if (ser.get(i).getName().equals(nameService)) {
                    owner = ser.get(i).getOwner();
                    ser.remove(i);
                    this._services.put(type, ser);
                    end = true;
                    //System.out.println("[SOL] Se  ha eliminado el servicio "+nameService+" cuyo dueño es "+owner
                    //        +" y el tipo es "+type);
                }
                i++;
            }

            /*
             * NOTA Hemos añadido opcionalmente esto para eliminar el tipo, si
             * este se queda sin servicios.
             */
            if (!(ser.size() > 0)) {
                _services.remove(type);
            }

            ssv.updateServices(_services);
            ssv.notifyServicesRemoved(type, nameService, owner);
        }
    }

    // Se eliminan del registro todos los servicios asociados a un agente.
    protected void unregisterService(String owner) {
        Enumeration<String> keys = SunSpotHostApplication._services.keys();
        String key;
        List<Service> listSer;
        int i;
        Boolean end;
        Service ser;

        while (keys.hasMoreElements()) {
            key = keys.nextElement();
            listSer = _services.get(key);
            // Buscamos en la lista si algún nombre se corresponde con owner
            i = 0;
            end = false;
            while (!end && i < listSer.size()) {
                ser = listSer.get(i);
                if (ser.getOwner().equals(owner)) {
                    end = true;
                    listSer.remove(ser);
                    //System.out.println("[SOL] Se  ha eliminado el servicio "+ser.getName()+" de "+ser.getOwner()+", y cuyo tipo es "+key);
                }
                i++;
            }
        }

        ssv.updateServices(_services);
        ssv.notifyServicesRemoved("", "", owner);
    }

    protected void unregisterAllServices() {
        Enumeration<String> keys = SunSpotHostApplication._services.keys();
        String key;
        List<Service> listSer;
        int i;
        Boolean any;
        Service ser;

        //_services.clear();        
        while (keys.hasMoreElements()) {
            key = keys.nextElement();
            listSer = _services.get(key);
            // Buscamos en la lista si algún dueño se corresponde con name
            i = 0;
            any = false;
            while (i < listSer.size()) {
                ser = listSer.get(i);
                listSer.remove(ser);
                //System.out.println("[SOL] Se  ha eliminado el servicio "+ser.getName()+" cuyo dueño es "+ser.getOwner()
                //                +" y el tipo es "+key);
                i++;
                any = true;
            }
            _services.remove(key);
            if (any) {
                ssv.notifyServicesRemoved();
            }
            ssv.updateServices(_services);
        }
    }

    protected List<Service> queryService(String type) {
        //System.out.println("[SOL] Se ha hecho una consulta para el servicio "+type);
        List<Service> answer;
        answer = this._services.get(type);
        return answer;
    }

//Method to send messages    
    protected void sendMessage(AuroraMessage msg) {
        //System.out.println("[SOL] Se envía un mensaje a ");
        Long initial = System.currentTimeMillis();
        Vector<String> receivers = msg.getReceivers();
        SenderConnection sc;
        String agent;

        //Go through receiver to send the message to each one.
        for (int i = 0; i < receivers.size(); i++) {
            //System.out.println(receivers.get(i));
            /*
             * if(_idgroups.contains(receivers.get(i))){
             *
             * }
             */
            sc = _connections.get(receivers.get(i));
            try {
                if (sc != null) {
                    //Status.addStatus(msg);
                    sc.sendMessage(msg); //OJO: El receptor y emisor deben tener el mismo 'protocol'
                    //Long end=System.currentTimeMillis();
                    //Long result=end-initial;
                    //System.out.println("El tiempo en HostApplication es: "+result);
                    agent = receivers.get(i);
                    //System.out.println("[SOL] Se envía un mensaje a "+agent);
                    if (!msg.getSender().equals(agent)) {
                        ssv.addStatusMessage(msg.getSender() + " envia un mensaje a " + agent);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                // Si hay una excepción mandando el mensaje, eliminamos el servicio y el agente de la lista.
                //unregisterConnection(receivers.get(i));
                //unregisterService(receivers.get(i));
            }
        }
        //ssv.addConnectionToSniffer(msg.getSender(), receivers, "", msg.getContent());

    }

    /*
     * public void unregisterConnectionAux(String id){
     * this.unregisterConnection(id);    
     }
     */
//Auxiliar methods to be used by the view
    public void unregisterServiceAux(String type, String nameService) {
        this.unregisterService(type, nameService);
        ssv.updateServices(_services);
        ssv.notifyServicesRemoved();
    }

    public void unregisterAllServicesAux() {
        this.unregisterAllServices();
        ssv.updateServices(_services);
    }

    public TreeMap<String, String> getAllInfoAgentsGroup(String group) {
        TreeMap<String, String> info = new TreeMap<String, String>();
        GroupConnection gc = (GroupConnection) _connections.get(group);
        info = gc.getInfoGroup();
        return info;
    }

    public TreeMap<String, String> getAllInfoAgent(String agent) {
        TreeMap<String, String> info = new TreeMap<String, String>();
        String group;
        GroupConnection gc;
        String info_agent;
        for (int i = 0; i < _idgroups.size(); i++) {
            group = _idgroups.get(i);
            if (isConnectionRegisteredInGroup(group, agent)) {
                gc = (GroupConnection) _connections.get(group);
                info_agent = gc.getInfoAgent(agent);
                info.put(group, info_agent);
            }
        }
        return info;
    }

    public void registerAgentToGroupConnectionAux(String groupName, String agent) {
        GroupConnection gc = (GroupConnection) this.getConnection(groupName);
        SenderConnection sc = this.getConnection(agent);
        String ipport = gc.registerAgent(agent, sc);
        //this._connections.put(groupName, gc);
        this.registerConnection(groupName, gc);
        AuroraMessage msg = this.createMessage(agent, groupName + ":" + ipport);
        msg.setProtocol("RegisterGroup");
        this.sendMessage(msg);
    }

    public void registerGroupConnectionAux(String groupName) {
        this.registerConnection(groupName, new GroupConnection(groupName));
    }

    public void unregisterGroupConnectionAux(String groupName) {
        //this.unregisterAgentAllGroups(groupName);
        GroupConnection gc = (GroupConnection) this.getConnection(groupName);
        List<String> rec = gc.getAgents();

        AuroraMessage msg = this.createMessage(null, groupName);
        Vector v = new Vector(rec);
        msg.setReceivers(v);
        msg.setProtocol("LeaveGroup");
        this.sendMessage(msg);

        this.unregisterGroup(groupName);
    }

    public void unregisterAgentGroupAux(String groupName, String agent) {
        AuroraMessage msg = this.createMessage(agent, groupName);
        msg.setProtocol("LeaveGroup");
        this.sendMessage(msg);
        this.unregisterAgentFromGroup(groupName, agent);
    }

    public void unregisterConnectionAux(String agent) {
        AuroraMessage msg = this.createMessage(agent, null);

        msg.setProtocol("LeaveGroup");
        for (Iterator<String> it = _idgroups.iterator(); it.hasNext();) {
            String group = it.next();
            if (isConnectionRegisteredInGroup(group, agent)) {
                msg.setContent(group);
                this.sendMessage(msg);
            }
        }
        msg.setProtocol("UnregisterAgent");
        this.sendMessage(msg);

        this.unregisterConnection(agent);
    }

    public void sendMessageGroupAux(String receiver, String message) {
        AuroraMessage msg = this.createMessage(receiver, message);
        this.sendMessage(msg);
    }

    /**
     * Start up the host application.
     *
     * @param args any command line arguments
     */
    public static void main(String[] args) {
        _connections = new Hashtable<String, SenderConnection>();
        _services = new Hashtable<String, List<Service>>();
        _idgroups = new LinkedList<String>();
        SunSpotHostApplication app = new SunSpotHostApplication();
        // Registramos
        Service service = new Service();
        service.setOwner("handheld");
        service.setName("light");
        app.registerService("light", service);
        app.run();
    }

    public String getIPAgent(String agent) {
        SenderConnection sc = _connections.get(agent);
        return sc.getIP();
    }

    public String getConnProtocol(String agent) {
        SenderConnection sc = _connections.get(agent);
        return sc.getConnectionProtocol();
    }

    private AuroraMessage createMessage(String receiver, String m) {
        AuroraMessage msg = new AuroraMessage().createReply();
        msg.setSender("Manager");
        //set the receivers
        Vector v = new Vector();
        v.add(receiver);
        msg.setReceivers(v);
        //set the content
        msg.setConverstionId("15613");
        msg.setEnconding("UTF-8");
        msg.setLanguage("SPANISH");
        msg.setContent(m);
        msg.setOntology("GuideOntology");
        msg.setProtocol("HostProtocol");
        msg.setPerformative(AuroraMessage.CONFIRM);

        return msg;
    }
}