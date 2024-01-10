/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * SunSpotSnifferView.java
 *
 * Created on 29-may-2012, 18:23:51
 */
package org.sunspotUI;

//import com.sl.app.Sniffer_GUI.Canvas;
import java.awt.*;
import java.io.File;
import org.sunspotUI.graphicLibraries.ConnectLine;
import java.util.HashMap;
import java.util.LinkedList;
import org.sunspotUI.graphicLibraries.ConnectorContainer;
import org.sunspotUI.graphicLibraries.JConnector;
import org.sunspotworld.SunSpotHostApplication;

/**
 *
 * @author Migue
 */
public class SunSpotSnifferView extends javax.swing.JFrame {
    Canvas c = new Canvas();
    //ConnectorPropertiesPanel props;
    private HashMap<String,LinkedList<JConnector>> agents_connections = new HashMap<String,LinkedList<JConnector>>();
    private LinkedList<JConnector> agents;
    private LinkedList<JConnector> connections;
    private ConnectorContainer cc;
    private LinkedList<String> listAgents;
    private LinkedList<String> listGroups;

    //private SunSpotHostApplication sha;
    
    public SunSpotSnifferView() {
        initComponents();
        listAgents = new LinkedList<String>();
        listGroups = new LinkedList<String>();
    }

    /*SunSpotSnifferView(SunSpotHostApplication sha) {
        this.sha = sha;
        initComponents();
        listAgents = new LinkedList<String>();
    }*/
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        addAgentSnifferButton = new javax.swing.JButton();
        removeAgentSnifferButton = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        list1 = new java.awt.List();
        jPanel5 = new javax.swing.JPanel();
        list2 = new java.awt.List();

        setTitle("SolAgent Sniffer");
        setBackground(new java.awt.Color(204, 255, 255));

        initializeConnections();
        jPanel2 = cc;
        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jPanel2.setBackground(new java.awt.Color(231, 231, 255));
        jPanel2.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 255, 255), 1, true));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 585, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 557, Short.MAX_VALUE)
        );

        jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, Short.MAX_VALUE, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, Short.MAX_VALUE, Short.MAX_VALUE)
        );

        jScrollPane1.setViewportView(jPanel2);

        jPanel3.setBackground(new java.awt.Color(255, 255, 204));

        File f = new File("");
        String sep = System.getProperty("file.separator");
        String s = f.getAbsolutePath()+sep+"build"+sep+"org"+sep+"images"+sep+"Add agent sniffer.png";
        addAgentSnifferButton.setIcon(new javax.swing.ImageIcon(s));
        addAgentSnifferButton.setToolTipText("Add agent sniffing");
        addAgentSnifferButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addAgentSnifferButtonActionPerformed(evt);
            }
        });

        f = new File("");
        s = f.getAbsolutePath()+sep+"build"+sep+"org"+sep+"images"+sep+"Remove agent sniffer.png";
        removeAgentSnifferButton.setIcon(new javax.swing.ImageIcon(s));
        removeAgentSnifferButton.setToolTipText("Remove agent sniffing");
        removeAgentSnifferButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeAgentSnifferButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(removeAgentSnifferButton, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(addAgentSnifferButton, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(addAgentSnifferButton, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(removeAgentSnifferButton, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder("Agents"));

        list1.setBackground(new java.awt.Color(255, 255, 204));
        list1.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(list1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 140, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(list1, javax.swing.GroupLayout.DEFAULT_SIZE, 263, Short.MAX_VALUE)
        );

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder("Groups"));

        list2.setBackground(new java.awt.Color(255, 255, 204));
        list2.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(list2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 140, Short.MAX_VALUE)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(list2, javax.swing.GroupLayout.DEFAULT_SIZE, 263, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(637, 637, 637))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void addAgentSnifferButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addAgentSnifferButtonActionPerformed
        // TODO add your handling code here:
        int i = list1.getSelectedIndex();
        if(i>=0){
            addAgent(list1.getItem(i));
            jPanel2.updateUI();
        }
        list1.select(-1);
        
        int j = list2.getSelectedIndex();
        if(j>=0){
            addAgent(list2.getItem(j));
            jPanel2.updateUI();
        }
        list2.select(-1);
    }//GEN-LAST:event_addAgentSnifferButtonActionPerformed

    private void removeAgentSnifferButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removeAgentSnifferButtonActionPerformed
        // TODO add your handling code here:
        int i = list1.getSelectedIndex();
        if(i>=0){
            removeAgent(list1.getItem(i));
            jPanel2.updateUI();
        }
        list1.select(-1);
        
        int j = list2.getSelectedIndex();
        if(j>=0){
            removeAgent(list2.getItem(j));
            jPanel2.updateUI();
        }
        list2.select(-1);
        
    }//GEN-LAST:event_removeAgentSnifferButtonActionPerformed

    private void AgentsItemStateChanged(java.awt.event.ItemEvent evt) {
        // TODO add your handling code here:
        /*if(Agent1.isSelected()){
            addAgent("AgentX");
        }else{
            removeAgent("AgentX");
        }
        jPanel1.updateUI();*/
    }
        
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(SunSpotSnifferView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(SunSpotSnifferView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(SunSpotSnifferView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(SunSpotSnifferView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                new SunSpotSnifferView().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addAgentSnifferButton;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private java.awt.List list1;
    private java.awt.List list2;
    private javax.swing.JButton removeAgentSnifferButton;
    // End of variables declaration//GEN-END:variables
    
    public void initializeConnections(){
        cc = new ConnectorContainer();
        cc.setLayout(null);
        cc.setAutoscrolls(true);
        connections = new LinkedList<JConnector>();
        agents = new LinkedList<JConnector>();
    }
    
    public void addAgent(String name_agent){
        if(!agents_connections.containsKey(name_agent)){
            String message = "";
            int offset, offset_fix;
            if(agents_connections.isEmpty()) offset = 10;
            else offset = agents_connections.size()*160+agents_connections.size()*10;
            agents_connections.put(name_agent,new LinkedList<JConnector>());

            JConnector agent = new JConnector(name_agent, message, offset, Color.red);
            agents.add(agent);
            connections.add(agent);
            JConnector[] conn = new JConnector[connections.size()];
            for (int i = 0; i < connections.size(); i++) {
                conn[i] = connections.get(i);
            }
            cc.setConnectors(conn);
            cc.add(agent.getAgentBox());
        }
        jPanel2.updateUI();
    }
    
    public void removeAgent(String name_agent){
        if(agents_connections.containsKey(name_agent)){
            JConnector agent = getAgent(name_agent);

            removeConnections(name_agent);
            agents.remove(agent);
            agents_connections.remove(name_agent);
            JConnector[] conn = new JConnector[connections.size()];
           
            for (int i = 0; i < connections.size(); i++) {
                conn[i] = connections.get(i);
            }
            cc.setConnectors(conn);
            cc.remove(agent.getAgentBox());
            
            LinkedList<String> agents_name = new LinkedList<String>();
            HashMap<String,LinkedList<JConnector>> ag_conn = new HashMap<String,LinkedList<JConnector>>();
            for (int i = 0; i < agents.size(); i++) {
                agents_name.add(agents.get(i).getAgentName());
                ag_conn.put(agents.get(i).getAgentName(), agents_connections.get(agents.get(i).getAgentName()));
            }
            agents.clear();
            agents_connections.clear();
            connections.clear();
            cc.removeAll();
            cc.setConnectors(conn);
            
            String name_aux;
            JConnector c_aux;
            LinkedList<JConnector> conn_aux = new LinkedList<JConnector>();
            for (int i = 0; i < agents_name.size(); i++) {
                name_aux = agents_name.get(i);
                addAgent(name_aux);
                conn_aux = ag_conn.get(name_aux);
                for (int j = 0; j < conn_aux.size(); j++) {
                    c_aux = conn_aux.get(j);
                    addConnection(c_aux.getSourceConnectionName(), c_aux.getDestConnectionName(),c_aux.getMessage());
                }
            }
            
        }
        jPanel2.updateUI();
    }
    
    public void addConnection(String agent_source, String agent_dest, String message){
        if(agents_connections.containsKey(agent_source) && agents_connections.containsKey(agent_dest)){
            JConnector agent1 = getAgent(agent_source);
            JConnector agent2 = getAgent(agent_dest);
            JConnector connection = new JConnector(agent1, agent2, agents, message, Color.blue);
            connection.setLineArrow(ConnectLine.LINE_ARROW_DEST);
            if(agent_source.equals(agent_dest)) connection.setLineArrow(ConnectLine.LINE_ARROW_NONE);
            if(connection!=null){
                connections.add(connection);
                
                LinkedList<JConnector> c = agents_connections.get(agent_source);
                c.add(connection);
                agents_connections.put(agent_source, c);
                
                if(!agent_source.equals(agent_dest)){
                    c = agents_connections.get(agent_dest);
                    c.add(connection);
                    agents_connections.put(agent_dest, c);
                }
            }
            
            JConnector[] conn = new JConnector[connections.size()];
            for (int i = 0; i < connections.size(); i++) {
                conn[i] = connections.get(i);
            }
            cc.setConnectors(conn);
        }
        jPanel2.updateUI();
    }
    
    //Auxiliar methods for the previous ones       
    private void removeConnections(String name_agent) {
        JConnector c;
        int i = 0;
        while (i < connections.size()) {
            c = connections.get(i);
            if(c.isConnection()){
                if(c.getSourceConnectionName().equals(name_agent) || c.getDestConnectionName().equals(name_agent)){
                    connections.remove(i);
                    i=0;
                }
                i++;
            }else{
                if(c.getAgentName().equals(name_agent)){
                    connections.remove(i);
                    i=0;
                }
                i++;
            }
        }
    }

    private JConnector getAgent(String agent_name) {
        JConnector ag = null;
        JConnector agent = null;
        for (int i = 0; i < agents.size(); i++) {
            ag = agents.get(i);
            if(ag.getAgentName().equals(agent_name)){
                agent = ag;
                break;
            }
        }
        return agent;
    }

    void updateteAgentsSnifferList(String[] items) {
        list1.removeAll();
        listAgents.clear();
        int length = items.length;
        String s;
        for (int i = 0; i < length; i++) {
            s = items[i];
            listAgents.add(s);
            list1.add(s);
        }
    }

    void updateGroups(java.util.List<String> groups) {
        list2.removeAll();
        listGroups.clear();
        int length = groups.size();
        String s;
        for (int i = 0; i < length; i++) {
            s = groups.get(i);
            listGroups.add(s);
            list2.add(s);
        }
    }

    public void checkAgents(String agent) {
        if(!agents_connections.containsKey(agent) && listAgents.contains(agent)){
            addAgent(agent);
        }
        if(!agents_connections.containsKey(agent) && listGroups.contains(agent)){
            addAgent(agent);
        }
    }
}
