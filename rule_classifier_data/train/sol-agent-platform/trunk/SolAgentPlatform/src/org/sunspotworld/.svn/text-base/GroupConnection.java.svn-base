/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sunspotworld;

import java.io.*;
import java.net.URL;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import bt.javax.microedition.io.Connection;
import java.net.Inet4Address;

/**
 *
 * @author Jose Luis
 */
public class GroupConnection implements SenderConnection {

    private String groupName;
    private String guide = "";
    private String pass;
    private Hashtable<Integer, SenderConnection> connections;
    private Hashtable<String, Integer> agentConnection;

    GroupConnection(String groupName) {
        this.groupName = groupName;
        pass = "";
        connections = new Hashtable<Integer, SenderConnection>();
        agentConnection = new Hashtable<String, Integer>();
    }

    GroupConnection(String groupName, String pass) {
        this.groupName = groupName;
        this.pass = pass;
        connections = new Hashtable<Integer, SenderConnection>();
        agentConnection = new Hashtable<String, Integer>();
    }

    /**
     * @return the groupName
     */
    public String getGroupName() {
        return groupName;
    }

    /**
     * @return the connections
     */
    public Hashtable<Integer, SenderConnection> getConnections() {
        /*
         * List<SenderConnection> list = new LinkedList<SenderConnection>(); for
         * (int i = 0; i < connections.size(); i++) {
         * list.add(connections.get(i));
         }
         */

        return connections;
    }

    public List<String> getAgents() {
        List<String> agents = new LinkedList<String>();
        for (Iterator<String> it = agentConnection.keySet().iterator(); it.hasNext();) {
            agents.add(it.next());
        }
        return agents;
    }

    //Generate MulticastConnections IPv4
    protected String generateMulticastConnection() {
        Random r = new Random();
        List<String> ips = new LinkedList<String>();
        String path = this.loadPath();

        if (path != null) {
            try {
                //FileReader fr = new FileReader("C:\\Users\\Migue\\Documents\\NetBeansProjects\\SolAgentPlatform\\src\\org\\sunspotworld\\IPPort");
                FileReader fr;
                fr = new FileReader(path);
                BufferedReader in = new BufferedReader(fr);
                String str;
                while ((str = in.readLine()) != null) {
                    ips.add(str);
                }
                in.close();
            } catch (FileNotFoundException ex) {
                Logger.getLogger(GroupConnection.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(GroupConnection.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        int q = (r.nextInt(16) + 224);
        while (q > 239) {
            q = (r.nextInt(16) + 224);
        }
        String ip = q + "." + r.nextInt(256) + "." + r.nextInt(256) + "." + (r.nextInt(256));
        //String ip = "226.1.0.20";
        int port = r.nextInt(65535) + 1;
        //int port = r.nextInt(65535)+1;
        String ipport = ip + ":" + port;

        while (ips.contains(ipport)) {
            q = (r.nextInt(16) + 224);
            while (q > 239) {
                q = (r.nextInt(16) + 224);
            }
            ip = q + "." + r.nextInt(256) + "." + r.nextInt(256) + "." + (r.nextInt(255));
            port = r.nextInt(65535) + 1;
            //port = 12345;
            ipport = ip + ":" + port;
        }

        /*
         * try { InetAddress addresses = InetAddress.getByName(ip); //for (int i
         * = 0; i < addresses.length; i++) { System.out.println(addresses); //}
         * } catch (UnknownHostException ex) {
         * Logger.getLogger(GroupConnection.class.getName()).log(Level.SEVERE,
         * null, ex);
         }
         */

        MulticastConnection mc = new MulticastConnection(ip, port);
        connections.put(TypeConnection.TYPE_TCPMC, mc);

        try {
            //BufferedWriter bw = new BufferedWriter(new FileWriter("C:\\Users\\Migue\\Documents\\NetBeansProjects\\SolAgentPlatform\\src\\org\\sunspotworld\\IPPort"));
            FileWriter fw;
            if (path == null) {
                File f = new File("IPPort");
                fw = new FileWriter(f);
            } else {
                fw = new FileWriter(path);
            }
            BufferedWriter bw = new BufferedWriter(fw);
            bw.append(ipport + "\n");
            bw.close();
        } catch (IOException ex) {
            Logger.getLogger(GroupConnection.class.getName()).log(Level.SEVERE, null, ex);
        }

        return ipport;
    }

    //Generate SunSpot MulticastConnections IPv6
    protected String generateSunSpotMulticastConnection() {
        Random r = new Random();
        List<String> ips = new LinkedList<String>();
        String path = this.loadPath();

        if (path != null) {
            try {
                //BufferedReader in = new BufferedReader(new FileReader("C:\\Users\\Migue\\Documents\\NetBeansProjects\\SolAgentPlatform\\src\\org\\sunspotworld\\IPPort"));
                BufferedReader in = new BufferedReader(new FileReader(path));
                String str;
                while ((str = in.readLine()) != null) {
                    ips.add(str);
                }
                in.close();
            } catch (FileNotFoundException ex) {
                Logger.getLogger(GroupConnection.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(GroupConnection.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        String ip = this.generateIPv6();
        int port = r.nextInt(65535) + 1;
        String ipport = ip + ":" + port;

        while (ips.contains(ipport)) {
            ip = this.generateIPv6();
            port = r.nextInt(65535) + 1;
            ipport = ip + ":" + port;
        }

        //MulticastConnection mc = new MulticastConnection(ip, port);
        SunSpotConnection ssp = new SunSpotConnection(ip, port);
        connections.put(TypeConnection.TYPE_SSMC, ssp);


        try {
            //BufferedWriter bw = new BufferedWriter(new FileWriter("C:\\Users\\Migue\\Documents\\NetBeansProjects\\SolAgentPlatform\\src\\org\\sunspotworld\\IPPort"));
            FileWriter fw;
            if (path == null) {
                File f = new File("IPPort");
                fw = new FileWriter(f);
            } else {
                fw = new FileWriter(path);
            }
            BufferedWriter bw = new BufferedWriter(fw);
            bw.append(ipport + "\n");
            bw.close();
        } catch (IOException ex) {
            Logger.getLogger(GroupConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ipport;
    }

    //Generate Bluetooth MulticastConnections
    protected void generateBTMulticastConnection() {
        /*
         * Random r = new Random(); List<String> ips = new LinkedList<String>();
         * String path = this.loadPath();
         *
         * if(path!=null){ try { //BufferedReader in = new BufferedReader(new
         * FileReader("C:\\Users\\Migue\\Documents\\NetBeansProjects\\SolAgentPlatform\\src\\org\\sunspotworld\\IPPort"));
         * BufferedReader in = new BufferedReader(new FileReader(path)); String
         * str; while ((str = in.readLine()) != null) { ips.add(str); }
         * in.close(); } catch (FileNotFoundException ex) {
         * Logger.getLogger(GroupConnection.class.getName()).log(Level.SEVERE,
         * null, ex); } catch (IOException ex) {
         * Logger.getLogger(GroupConnection.class.getName()).log(Level.SEVERE,
         * null, ex); }
         }
         */

        BluetoothMulticast btm = new BluetoothMulticast();
        connections.put(TypeConnection.TYPE_BTMC, btm);

        /*
         * btm.addBTConnection(btc);
         *
         * if(!ips.contains(address)){ connections.put(TYPE_BTMC,btc);
         *
         * try { //BufferedWriter bw = new BufferedWriter(new
         * FileWriter("C:\\Users\\Migue\\Documents\\NetBeansProjects\\SolAgentPlatform\\src\\org\\sunspotworld\\IPPort"));
         * FileWriter fw; if(path == null){ File f = new File("IPPort"); fw =
         * new FileWriter(f); }else{ fw = new FileWriter(path); } BufferedWriter
         * bw = new BufferedWriter(fw); bw.append(ipport+"\n"); bw.close(); }
         * catch (IOException ex) {
         * Logger.getLogger(GroupConnection.class.getName()).log(Level.SEVERE,
         * null, ex); } return ipport;
         */
    }

    /*
     * public void addBTAgentConnection(BTConnection btc, String sender, int
     * type) { BluetoothMulticast btm = (BluetoothMulticast)
     * connections.get(TYPE_BTMC); if(!btm.isAgentAdded(sender)){
     * agentConnection.put(sender, type); btm.addBTConnection(btc); }
     }
     */
    public void addAgentConnection(BTConnection btc, String sender, int type) {
        if (btc == null) {
            agentConnection.put(sender, type);
        } else {
            BluetoothMulticast btm = (BluetoothMulticast) connections.get(TypeConnection.TYPE_BTMC);
            if (!btm.isAgentAdded(sender)) {
                agentConnection.put(sender, type);
                btm.addBTConnection(btc);
            }
        }
    }

    void unregisterAgent(String id) {
        if (agentConnection != null && !agentConnection.isEmpty()) {

            if (agentConnection.get(id).equals(TypeConnection.TYPE_BTMC)) {
                BluetoothMulticast btm = (BluetoothMulticast) connections.get(TypeConnection.TYPE_BTMC);
                List<BTConnection> btcl = btm.getBTDevices();
                for (Iterator<BTConnection> it = btcl.iterator(); it.hasNext();) {
                    BTConnection bt = it.next();
                    if (bt.getAgentName().equals(id)) {
                        this.unregisterBTConnection(bt);
                    }
                }
            }

            int type = agentConnection.get(id);
            agentConnection.remove(id);
            /*
             * if(agentConnection.isEmpty()){ connections.clear(); }else{
             * boolean more = false; Enumeration<String> k =
             * (Enumeration<String>) agentConnection.keys(); while(k!=null &&
             * k.hasMoreElements()){ Integer t =
             * agentConnection.get(k.nextElement()); if(t==type){ more = true; }
             * } if(!more){ connections.remove(type); }
             }
             */
        }
    }

    boolean unregisterBTConnection(BTConnection btc) {
        BluetoothMulticast btm = (BluetoothMulticast) connections.get(TypeConnection.TYPE_BTMC);
        boolean r = btm.removeAgent(btc);
        return r;
    }

    private String generateIPv6() {
        String alphabet = "0123456789abcdef";
        int character;
        String s;

        String res = "fe80";
        for (int i = 4; i < 8 * 4 + 7; i++) { //8 positions with 4 hex characters each one plus 7 ":" between them.
            if (i == 4 || i == 9 || i == 14 || i == 19 || i == 24 || i == 29 || i == 34) {
                res = res + ":";
            } else {
                character = (int) (Math.random() * 16) + 0;
                s = alphabet.substring(character, character + 1);
                res = res + s;
            }
        }

        res = res.replaceAll("0000", "0");

        return res;
    }

    public void sendMessage(AuroraMessage msg) {
        //for (int i = 0; i < connections.size(); i++) {
        Set<Integer> key = connections.keySet();
        for (Iterator<Integer> it = key.iterator(); it.hasNext();) {
            Integer i = it.next();
            SenderConnection sc;
            sc = connections.get(i);
            if (sc != null) {
                if (sc instanceof TCPConnection) {
                    TCPConnection tp = (TCPConnection) sc;
                    if (!tp.getAgentName().equals(msg.getSender())) {
                        sc.sendMessage(msg);
                    }
                } else {
                    sc.sendMessage(msg);
                }
            }
        }
        /*for (int i = 0; i < 3; i++) {
         SenderConnection sc;
         sc = connections.get(i);
         if (sc != null) {
         sc.sendMessage(msg);
         }
         }*/
    }

    private String loadPath() {
        String fileSep = "/"; //System.getProperty("file.separator"); 
        String p;
        URL res = this.getClass().getClassLoader().getResource("./.");
        //System.out.println(this.getClass().getClassLoader().getResource("./."));
        if (res != null) {
            p = res.toString();
            p = p.substring(0, p.lastIndexOf(fileSep));
            p = p.substring(0, p.lastIndexOf(fileSep));
            if (p.startsWith("file:/home")) {
                p = p.substring(p.indexOf(fileSep), p.length());
            } else {
                p = p.substring(p.indexOf(fileSep) + 1, p.length());
            }
            p = p.replaceAll("%20", " ");
            //return p+fileSep+"build"+fileSep+"org"+fileSep+"sunspotworld"+fileSep+"IPPort";
            return p + fileSep + "build" + fileSep + "IPPort";
        } else {
            return null;
        }
    }

    /*
     * private String loadPathSunSpot() { String fileSep = "/";
     * //System.getProperty("file.separator"); String p; URL res =
     * this.getClass().getClassLoader().getResource("./.");
     * //System.out.println(this.getClass().getClassLoader().getResource("./."));
     * if(res!=null){ p = res.toString(); p = p.substring(0,
     * p.lastIndexOf(fileSep)); p = p.substring(0, p.lastIndexOf(fileSep));
     * if(p.startsWith("file:/home")){ p = p.substring(p.indexOf(fileSep),
     * p.length()); }else{ p = p.substring(p.indexOf(fileSep)+1, p.length()); }
     * p = p.replaceAll("%20"," "); //return
     * p+fileSep+"build"+fileSep+"org"+fileSep+"sunspotworld"+fileSep+"IPPort";
     * return p+fileSep+"build"+fileSep+"IPPortSunSpot"; }else{ return null; }
     }
     */
    public String getIPPort(Integer type) {
        String ipport = null;
        if (type == TypeConnection.TYPE_TCPMC) {
            MulticastConnection mc = (MulticastConnection) connections.get(type);
            if (mc != null) {
                ipport = mc.getIP() + ":" + mc.getPort();
            } else {
                ipport = this.generateMulticastConnection();
            }
        } else if (type == TypeConnection.TYPE_SSMC) {
            SunSpotConnection ssc = (SunSpotConnection) connections.get(type);
            if (ssc != null) {
                ipport = ssc.getHOST() + ":" + ssc.getPORT();
            } else {
                ipport = this.generateSunSpotMulticastConnection();
            }
        } else if (type == TypeConnection.TYPE_BTMC) {
            BluetoothMulticast btm = (BluetoothMulticast) connections.get(type);
            if (btm == null) {
                this.generateBTMulticastConnection();
            }
        }
        return ipport;
    }

    private String getIPPortBT(String agent) {
        BluetoothMulticast btm = (BluetoothMulticast) connections.get(TypeConnection.TYPE_BTMC);
        BTConnection btc = btm.getBTDevice(agent);
        return btc.getBTAddress();
    }

    TreeMap<String, String> getInfoGroup() {
        TreeMap<String, String> info_group = new TreeMap<String, String>();
        SenderConnection sc;
        List<String> agents = this.getAgents();

        for (Iterator<String> it = agents.iterator(); it.hasNext();) {
            String agent = it.next();
            String info_agent = this.getInfoAgent(agent);
            info_group.put(agent, info_agent);
        }

        return info_group;
    }

    String getInfoAgent(String agent) {
        int type_conn = agentConnection.get(agent);
        String type;
        String address;
        switch (type_conn) {
            case TypeConnection.TYPE_TCPMC:
                type = "Android";
                address = getIPPort(TypeConnection.TYPE_TCPMC);
                break;
            case TypeConnection.TYPE_SSMC:
                type = "Sun Spot";
                address = getIPPort(TypeConnection.TYPE_SSMC);
                break;
            case TypeConnection.TYPE_BTMC:
                type = "Bluetooth";
                address = getIPPortBT(agent);
                break;
            default:
                type = "Other";
                address = "-";
        }

        return type + "##" + address;
    }

    @Override
    public String getIP() {
        SenderConnection sc;
        String ip = connections.get(0).getIP() + "##" + connections.get(1).getIP() + "##" + connections.get(2).getIP();
        //return ip;
        return "GroupConnection";
    }

    @Override
    public String getConnectionProtocol() {
        return "GroupConnection";
    }

    @Override
    public int getTypeConnection() {
        return -1;
    }

    public String registerAgent(String agent, SenderConnection sc) {
        int type = sc.getTypeConnection();
        String ipport = null;
        if (type != -1) {
            ipport = this.getIPPort(type);
            if (type == TypeConnection.TYPE_BTMC) {
                this.addAgentConnection((BTConnection) sc, agent, type);
            } else {
                this.addAgentConnection(null, agent, type);
            }
        }
        return ipport;
    }

    /**
     * @return the pass
     */
    public String getPass() {
        return pass;
    }

    void setGuide(String guide) {
        this.guide = guide;
    }

    /**
     * @return the guide
     */
    public String getGuide() {
        return guide;
    }

    void addConnection(TCPConnection aThis) {
        Set<Integer> keys = connections.keySet();
        Integer i = 0, aux;
        for (Iterator<Integer> it = keys.iterator(); it.hasNext();) {
            aux = it.next();
            if(aux > i) i = aux;
        }
        if (i <= 2) {
            i = 3; //Reservamos las 3 primeras posiciones para TCP, BT y SS.
        } else {
            i++;
        }
        connections.put(i, aThis);
    }
}
