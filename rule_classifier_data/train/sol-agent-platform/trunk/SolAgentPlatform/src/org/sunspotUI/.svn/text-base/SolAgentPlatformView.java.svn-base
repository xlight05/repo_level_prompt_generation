/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * PortsView.java
 *
 * Created on 18-jun-2012, 12:12:00
 */
package org.sunspotUI;

//set CLASSPATH=%CLASSPATH%;activation.jar;mail.jar
import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.List;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
/*import javax.activation.*;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;*/
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;
import org.sunspotworld.Service;
import org.sunspotworld.SunSpotHostApplication;

/**
 *
 * @author Jose Luis
 */
public class SolAgentPlatformView extends javax.swing.JFrame {
    
    private SunSpotHostApplication sha;
    private List<Image> listImages;
    private List<String> popupMenuItems;
    private int id_group_selected = -1;
    private final String AGENT = "agent";
    private final String GROUP = "group";
    private int id_agent_selected = -1;
    private Hashtable<String, List<Service>> services;  

    
    /** Creates new form PortsView */
    public SolAgentPlatformView() {
        listImages = new LinkedList<Image>();
        listImages.add(new javax.swing.ImageIcon(getClass().getResource("/org/images/sol-icon.png")).getImage());
        listImages.add(new javax.swing.ImageIcon(getClass().getResource("/org/images/sol-icon.png")).getImage());
        initComponents();
    }

    public SolAgentPlatformView(SunSpotHostApplication aThis) {
        /*class ImagePanel extends JComponent {
            private Image image;
            public ImagePanel(Image image) {
                this.image = image;
            }
            @Override
            protected void paintComponent(Graphics g) {
                g.drawImage(image, 0, 0, null);
            }
        }
        
        BufferedImage myImage = null;
        try {
            myImage = ImageIO.read(getClass().getResource("/org/images/sol-icon.png"));
        } catch (IOException ex) {
            Logger.getLogger(SolAgentPlatformView.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.setContentPane(new ImagePanel(myImage));*/
        
        listImages = new LinkedList<Image>();
        listImages.add(new javax.swing.ImageIcon(getClass().getResource("/org/images/sol-icon.png")).getImage());
        listImages.add(new javax.swing.ImageIcon(getClass().getResource("/org/images/sol-icon.png")).getImage());
        initComponents();
        this.menuPopUp();
        sha = aThis;
    }

    
    private void menuPopUp() {        
        //Here we set the popup menu for the JTree "treeAgents"
        popupMenuItems = new LinkedList<String>();
        popupMenuItems.add("Add agent to group");
        popupMenuItems.add("Remove agent from group");
        JMenuItem item;
        
        ActionListener menuListener = new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                if(event.getActionCommand().equals(popupMenuItems.get(0))){
                    TreePath tp = treeAgents.getSelectionPath();
                    if(tp!=null){
                        DefaultMutableTreeNode node = (DefaultMutableTreeNode) tp.getLastPathComponent();
                        if(id_group_selected>-1 && !node.getAllowsChildren()){
                            if(!node.getAllowsChildren()){
                                getSha().registerAgentToGroupConnectionAux(listGroups.getItem(id_group_selected), (String)node.getUserObject());
                                statusGroups.setText("");
                            }
                        }else{
                            statusGroups.setText("You have to select an agent and group");
                        }
                    }
                    updateGroups();
                }else if(event.getActionCommand().equals(popupMenuItems.get(1))){
                    TreePath tp = treeAgents.getSelectionPath();
                    if(tp!=null){
                        DefaultMutableTreeNode node = (DefaultMutableTreeNode) tp.getLastPathComponent();
                        if(id_group_selected>-1 && !node.getAllowsChildren()){
                            if(!node.getAllowsChildren()){
                                getSha().unregisterAgentGroupAux(listGroups.getItem(id_group_selected), (String)node.getUserObject());
                                statusGroups.setText("");
                            }
                        }else{
                            statusGroups.setText("You have to select an agent and a group");
                        }
                    }
                    updateGroups();
                }
            }
        };
        
        for (Iterator<String> it = popupMenuItems.iterator(); it.hasNext();) {
            String s = it.next();
            item = new JMenuItem(s);
            popupMenuTree.add(item);
            item.addActionListener(menuListener);
        }
    }
    
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        popupMenuTree = new javax.swing.JPopupMenu();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        gmsPanel = new javax.swing.JPanel();
        jToolBar = new javax.swing.JToolBar();
        addGroupButton = new javax.swing.JButton();
        removeGroupButton = new javax.swing.JButton();
        addAgentButton = new javax.swing.JButton();
        removeAgentButton = new javax.swing.JButton();
        sendMessageButton = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        listGroups = new java.awt.List();
        PanelServices = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tableAgentsGroups = new javax.swing.JTable();
        statusGroups = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        listAgents = new java.awt.List();
        jToolBar2 = new javax.swing.JToolBar();
        addGroupButton1 = new javax.swing.JButton();
        removeGroupButton1 = new javax.swing.JButton();
        addAgentButton1 = new javax.swing.JButton();
        removeAgentButton1 = new javax.swing.JButton();
        sendMessageButton1 = new javax.swing.JButton();
        statusAgents = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tableAgentInGroups = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        agentProtocolLabel = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        agentIPLabel = new javax.swing.JLabel();
        imageProtocol = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        PanelServices1 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        tableServices = new javax.swing.JTable();
        jToolBar1 = new javax.swing.JToolBar();
        removeServiceButton = new javax.swing.JButton();
        removeAllServicesButton = new javax.swing.JButton();
        statusServices = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        PanelStatus = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        statusPane = new javax.swing.JTextPane();
        clearButton = new javax.swing.JButton();
        copyButton = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        treeAgents = new javax.swing.JTree();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        saveLogMenuItem = new javax.swing.JMenuItem();
        exitMenuItem = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        contactMenuItem = new javax.swing.JMenuItem();
        aboutMenuItem = new javax.swing.JMenuItem();

        popupMenuTree.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Sol Agent Platform");
        setIconImage(new javax.swing.ImageIcon(getClass().getResource("/org/images/sol-icon.png")).getImage());
        setIconImages(listImages);

        jTabbedPane1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Group Management", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 1, 12))); // NOI18N
        jTabbedPane1.setToolTipText("");
        jTabbedPane1.setAutoscrolls(true);
        jTabbedPane1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jTabbedPane1.setMaximumSize(new java.awt.Dimension(2147483647, 2147483647));
        jTabbedPane1.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jTabbedPane1StateChanged(evt);
            }
        });

        gmsPanel.setToolTipText("");

        jToolBar.setBackground(new java.awt.Color(255, 255, 255));
        jToolBar.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 255, 255), 1, true));
        jToolBar.setFloatable(false);
        jToolBar.setRollover(true);

        addGroupButton.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        addGroupButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/images/Add group.png"))); // NOI18N
        addGroupButton.setText("Add Group");
        addGroupButton.setToolTipText("Add Group");
        addGroupButton.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        addGroupButton.setFocusable(false);
        addGroupButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        addGroupButton.setMaximumSize(new java.awt.Dimension(90, 57));
        addGroupButton.setMinimumSize(new java.awt.Dimension(57, 57));
        addGroupButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        addGroupButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addGroupButtonActionPerformed(evt);
            }
        });
        jToolBar.add(addGroupButton);
        addGroupButton.getAccessibleContext().setAccessibleDescription("");

        removeGroupButton.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        removeGroupButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/images/Remove group.png"))); // NOI18N
        removeGroupButton.setText("Remove Group");
        removeGroupButton.setToolTipText("Remove Group");
        removeGroupButton.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        removeGroupButton.setFocusable(false);
        removeGroupButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        removeGroupButton.setMaximumSize(new java.awt.Dimension(90, 57));
        removeGroupButton.setMinimumSize(new java.awt.Dimension(57, 57));
        removeGroupButton.setPreferredSize(new java.awt.Dimension(57, 57));
        removeGroupButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        removeGroupButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeGroupButtonActionPerformed(evt);
            }
        });
        jToolBar.add(removeGroupButton);

        /*f = new File("");
        s = f.getAbsolutePath()+sep+"build"+sep+"org"+sep+"images"+sep+"Sniffer.png";*/
        addAgentButton.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        addAgentButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/images/Register agent.png"))); // NOI18N
        addAgentButton.setText("Add Agent");
        addAgentButton.setToolTipText("Add Agent To Group");
        addAgentButton.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        addAgentButton.setFocusable(false);
        addAgentButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        addAgentButton.setMaximumSize(new java.awt.Dimension(90, 57));
        addAgentButton.setMinimumSize(new java.awt.Dimension(57, 57));
        addAgentButton.setPreferredSize(new java.awt.Dimension(57, 57));
        addAgentButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        addAgentButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addAgentButton1ActionPerformed(evt);
            }
        });
        jToolBar.add(addAgentButton);

        removeAgentButton.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        removeAgentButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/images/Unregister agent.png"))); // NOI18N
        removeAgentButton.setText("Remove Agent");
        removeAgentButton.setToolTipText("Remove Agent From Group");
        removeAgentButton.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        removeAgentButton.setFocusable(false);
        removeAgentButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        removeAgentButton.setMaximumSize(new java.awt.Dimension(90, 57));
        removeAgentButton.setMinimumSize(new java.awt.Dimension(57, 57));
        removeAgentButton.setPreferredSize(new java.awt.Dimension(57, 57));
        removeAgentButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        removeAgentButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeAgentButton1ActionPerformed(evt);
            }
        });
        jToolBar.add(removeAgentButton);

        sendMessageButton.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        sendMessageButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/images/Send message group.png"))); // NOI18N
        sendMessageButton.setText("Send Message");
        sendMessageButton.setToolTipText("Send Message To Group");
        sendMessageButton.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        sendMessageButton.setFocusable(false);
        sendMessageButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        sendMessageButton.setMaximumSize(new java.awt.Dimension(90, 57));
        sendMessageButton.setMinimumSize(new java.awt.Dimension(57, 57));
        sendMessageButton.setPreferredSize(new java.awt.Dimension(57, 57));
        sendMessageButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        sendMessageButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sendMessageButton1ActionPerformed(evt);
            }
        });
        jToolBar.add(sendMessageButton);

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Groups", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 12))); // NOI18N
        jPanel5.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        listGroups.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                listGroupsItemStateChanged(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(listGroups, javax.swing.GroupLayout.DEFAULT_SIZE, 167, Short.MAX_VALUE)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(listGroups, javax.swing.GroupLayout.DEFAULT_SIZE, 439, Short.MAX_VALUE)
        );

        PanelServices.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Agents", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 12))); // NOI18N
        PanelServices.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        tableAgentsGroups.setAutoCreateRowSorter(true);
        tableAgentsGroups.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Agent", "Connection", "Multicast Adress"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            @Override
            public boolean isCellEditable(int row, int column) {
                //all cells false
                return false;
            }
        });
        tableAgentsGroups.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        tableAgentsGroups.setEnabled(true);
        tableAgentsGroups.setFocusable(false);
        jScrollPane3.setViewportView(tableAgentsGroups);

        javax.swing.GroupLayout PanelServicesLayout = new javax.swing.GroupLayout(PanelServices);
        PanelServices.setLayout(PanelServicesLayout);
        PanelServicesLayout.setHorizontalGroup(
            PanelServicesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 688, Short.MAX_VALUE)
        );
        PanelServicesLayout.setVerticalGroup(
            PanelServicesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
        );

        statusGroups.setForeground(new java.awt.Color(255, 0, 0));

        javax.swing.GroupLayout gmsPanelLayout = new javax.swing.GroupLayout(gmsPanel);
        gmsPanel.setLayout(gmsPanelLayout);
        gmsPanelLayout.setHorizontalGroup(
            gmsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(gmsPanelLayout.createSequentialGroup()
                .addComponent(jToolBar, javax.swing.GroupLayout.PREFERRED_SIZE, 454, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(statusGroups, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(gmsPanelLayout.createSequentialGroup()
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(PanelServices, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        gmsPanelLayout.setVerticalGroup(
            gmsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(gmsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(gmsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jToolBar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(statusGroups, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(gmsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(PanelServices, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        jTabbedPane1.addTab("  GMS  ", null, gmsPanel, "Group Management System");

        jPanel2.setToolTipText("");
        jPanel2.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jPanel2FocusGained(evt);
            }
        });

        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Agents", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 12))); // NOI18N
        jPanel6.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        listAgents.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                listAgentsItemStateChanged(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(listAgents, javax.swing.GroupLayout.DEFAULT_SIZE, 167, Short.MAX_VALUE)
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(listAgents, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jToolBar2.setBackground(new java.awt.Color(255, 255, 255));
        jToolBar2.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 255, 255), 1, true));
        jToolBar2.setFloatable(false);
        jToolBar2.setRollover(true);

        addGroupButton1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        addGroupButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/images/Add group.png"))); // NOI18N
        addGroupButton1.setText("Add Group");
        addGroupButton1.setToolTipText("Add Group");
        addGroupButton1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        addGroupButton1.setEnabled(false);
        addGroupButton1.setFocusable(false);
        addGroupButton1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        addGroupButton1.setMaximumSize(new java.awt.Dimension(90, 57));
        addGroupButton1.setMinimumSize(new java.awt.Dimension(57, 57));
        addGroupButton1.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar2.add(addGroupButton1);

        removeGroupButton1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        removeGroupButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/images/Remove group.png"))); // NOI18N
        removeGroupButton1.setText("Remove From Group");
        removeGroupButton1.setToolTipText("Remove From Group");
        removeGroupButton1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        removeGroupButton1.setFocusable(false);
        removeGroupButton1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        removeGroupButton1.setMaximumSize(new java.awt.Dimension(120, 57));
        removeGroupButton1.setMinimumSize(new java.awt.Dimension(57, 57));
        removeGroupButton1.setPreferredSize(new java.awt.Dimension(57, 57));
        removeGroupButton1.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        removeGroupButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeGroupButton1ActionPerformed(evt);
            }
        });
        jToolBar2.add(removeGroupButton1);

        /*f = new File("");
        s = f.getAbsolutePath()+sep+"build"+sep+"org"+sep+"images"+sep+"Sniffer.png";*/
        addAgentButton1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        addAgentButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/images/Register agent.png"))); // NOI18N
        addAgentButton1.setText("Add Agent");
        addAgentButton1.setToolTipText("Add Agent");
        addAgentButton1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        addAgentButton1.setEnabled(false);
        addAgentButton1.setFocusable(false);
        addAgentButton1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        addAgentButton1.setMaximumSize(new java.awt.Dimension(90, 57));
        addAgentButton1.setMinimumSize(new java.awt.Dimension(57, 57));
        addAgentButton1.setPreferredSize(new java.awt.Dimension(57, 57));
        addAgentButton1.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar2.add(addAgentButton1);

        removeAgentButton1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        removeAgentButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/images/Unregister agent.png"))); // NOI18N
        removeAgentButton1.setText("Remove Agent");
        removeAgentButton1.setToolTipText("Remove Agent");
        removeAgentButton1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        removeAgentButton1.setFocusable(false);
        removeAgentButton1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        removeAgentButton1.setMaximumSize(new java.awt.Dimension(90, 57));
        removeAgentButton1.setMinimumSize(new java.awt.Dimension(57, 57));
        removeAgentButton1.setPreferredSize(new java.awt.Dimension(57, 57));
        removeAgentButton1.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        removeAgentButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeAgentButton11ActionPerformed(evt);
            }
        });
        jToolBar2.add(removeAgentButton1);

        sendMessageButton1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        sendMessageButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/images/Send message user.png"))); // NOI18N
        sendMessageButton1.setText("Send Message");
        sendMessageButton1.setToolTipText("Send Message Group");
        sendMessageButton1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        sendMessageButton1.setFocusable(false);
        sendMessageButton1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        sendMessageButton1.setMaximumSize(new java.awt.Dimension(90, 57));
        sendMessageButton1.setMinimumSize(new java.awt.Dimension(57, 57));
        sendMessageButton1.setPreferredSize(new java.awt.Dimension(57, 57));
        sendMessageButton1.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        sendMessageButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sendMessageButton11ActionPerformed(evt);
            }
        });
        jToolBar2.add(sendMessageButton1);

        statusAgents.setForeground(new java.awt.Color(255, 0, 0));

        jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Information", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 12))); // NOI18N

        tableAgentInGroups.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Groups", "Multicast Address"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tableAgentInGroups.setToolTipText("Groups where the agent is registered");
        jScrollPane4.setViewportView(tableAgentInGroups);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel1.setText("Connection protocol:");

        agentProtocolLabel.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel2.setText("Agent IP:");

        agentIPLabel.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 658, Short.MAX_VALUE)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(imageProtocol, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(agentProtocolLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(agentIPLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(agentProtocolLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(imageProtocol, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel2)
                    .addComponent(agentIPLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 342, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jToolBar2, javax.swing.GroupLayout.PREFERRED_SIZE, 482, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(statusAgents, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(statusAgents, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jToolBar2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        jTabbedPane1.addTab("  AMS  ", null, jPanel2, "Agent Management System");

        PanelServices1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Services", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 12))); // NOI18N

        tableServices.setAutoCreateRowSorter(true);
        tableServices.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Type", "Name", "Owner"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            @Override
            public boolean isCellEditable(int row, int column) {
                //all cells false
                return false;
            }
        });
        tableServices.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        tableServices.setEnabled(true);
        tableServices.setFocusable(false);
        jScrollPane5.setViewportView(tableServices);

        javax.swing.GroupLayout PanelServices1Layout = new javax.swing.GroupLayout(PanelServices1);
        PanelServices1.setLayout(PanelServices1Layout);
        PanelServices1Layout.setHorizontalGroup(
            PanelServices1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 863, Short.MAX_VALUE)
        );
        PanelServices1Layout.setVerticalGroup(
            PanelServices1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 450, Short.MAX_VALUE)
        );

        jToolBar1.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 255, 255), 1, true));
        jToolBar1.setFloatable(false);
        jToolBar1.setRollover(true);
        jToolBar1.setPreferredSize(new java.awt.Dimension(293, 59));

        removeServiceButton.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        removeServiceButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/images/Remove one.png"))); // NOI18N
        removeServiceButton.setText("Remove Service");
        removeServiceButton.setToolTipText("Remove Service");
        removeServiceButton.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        removeServiceButton.setFocusable(false);
        removeServiceButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        removeServiceButton.setMaximumSize(new java.awt.Dimension(120, 57));
        removeServiceButton.setMinimumSize(new java.awt.Dimension(57, 57));
        removeServiceButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        removeServiceButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeServiceButtonActionPerformed(evt);
            }
        });
        jToolBar1.add(removeServiceButton);

        removeAllServicesButton.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        removeAllServicesButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/images/Remove all.png"))); // NOI18N
        removeAllServicesButton.setText("Remove All Services");
        removeAllServicesButton.setToolTipText("Remove All Services");
        removeAllServicesButton.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        removeAllServicesButton.setFocusable(false);
        removeAllServicesButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        removeAllServicesButton.setMaximumSize(new java.awt.Dimension(120, 57));
        removeAllServicesButton.setMinimumSize(new java.awt.Dimension(57, 57));
        removeAllServicesButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        removeAllServicesButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeAllServicesButtonActionPerformed(evt);
            }
        });
        jToolBar1.add(removeAllServicesButton);

        statusServices.setForeground(new java.awt.Color(255, 0, 0));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 242, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(statusServices, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(PanelServices1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(statusServices, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(PanelServices1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("  DF  ", null, jPanel3, "Directory Facilitator");

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/images/Under construction.png"))); // NOI18N
        jLabel3.setText("      Under Construction");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(344, 344, 344)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(337, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(251, 251, 251)
                .addComponent(jLabel3)
                .addContainerGap(267, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("  IPMT  ", null, jPanel4, "Platform Management");

        PanelStatus.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Status Messages", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 0, 14))); // NOI18N
        PanelStatus.setFocusable(false);
        PanelStatus.setPreferredSize(new java.awt.Dimension(390, 150));

        statusPane.setEditable(false);
        statusPane.setForeground(new java.awt.Color(4, 157, 250));
        jScrollPane2.setViewportView(statusPane);

        clearButton.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        clearButton.setText("Clear");
        clearButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clearButtonActionPerformed(evt);
            }
        });

        copyButton.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        copyButton.setText("Copy");
        copyButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                copyButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout PanelStatusLayout = new javax.swing.GroupLayout(PanelStatus);
        PanelStatus.setLayout(PanelStatusLayout);
        PanelStatusLayout.setHorizontalGroup(
            PanelStatusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelStatusLayout.createSequentialGroup()
                .addComponent(clearButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(copyButton)
                .addGap(454, 454, 454))
            .addComponent(jScrollPane2)
        );
        PanelStatusLayout.setVerticalGroup(
            PanelStatusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelStatusLayout.createSequentialGroup()
                .addGroup(PanelStatusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(clearButton, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(copyButton, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 49, Short.MAX_VALUE))
        );

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Multi-Agent System", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 1, 12))); // NOI18N

        jScrollPane1.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        javax.swing.tree.DefaultMutableTreeNode treeNode1 = new javax.swing.tree.DefaultMutableTreeNode("Sol");
        javax.swing.tree.DefaultMutableTreeNode treeNode2 = new javax.swing.tree.DefaultMutableTreeNode("iMuseumI");
        treeNode1.add(treeNode2);
        treeAgents.setModel(new javax.swing.tree.DefaultTreeModel(treeNode1));
        treeAgents.setAutoscrolls(true);
        treeAgents.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                treeAgentsMouseReleased(evt);
            }
        });
        jScrollPane1.setViewportView(treeAgents);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 181, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1)
        );

        jMenuBar1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jMenuBar1.setBorderPainted(false);

        jMenu1.setText("File");
        jMenu1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        saveLogMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.SHIFT_MASK));
        saveLogMenuItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/images/Save as.png"))); // NOI18N
        saveLogMenuItem.setText("Save log as");
        saveLogMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveLogMenuItemActionPerformed(evt);
            }
        });
        jMenu1.add(saveLogMenuItem);

        exitMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_E, java.awt.event.InputEvent.SHIFT_MASK));
        exitMenuItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/images/Exit.png"))); // NOI18N
        exitMenuItem.setText("Exit");
        exitMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitMenuItemActionPerformed(evt);
            }
        });
        jMenu1.add(exitMenuItem);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("About");
        jMenu2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        contactMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_C, java.awt.event.InputEvent.SHIFT_MASK));
        contactMenuItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/images/ContactUs.png"))); // NOI18N
        contactMenuItem.setText("Contact us");
        contactMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                contactMenuItemActionPerformed(evt);
            }
        });
        jMenu2.add(contactMenuItem);

        aboutMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_A, java.awt.event.InputEvent.SHIFT_MASK));
        aboutMenuItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/images/sol-icon_mini.png"))); // NOI18N
        aboutMenuItem.setText("About Sol Agent Platform");
        aboutMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                aboutMenuItemActionPerformed(evt);
            }
        });
        jMenu2.add(aboutMenuItem);

        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(PanelStatus, javax.swing.GroupLayout.DEFAULT_SIZE, 1101, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(PanelStatus, javax.swing.GroupLayout.DEFAULT_SIZE, 99, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane1.getAccessibleContext().setAccessibleName("tabs");

        getAccessibleContext().setAccessibleName("solap");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void clearButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearButtonActionPerformed
        // TODO add your handling code here:
        statusPane.setText("");
    }//GEN-LAST:event_clearButtonActionPerformed

    private void copyButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_copyButtonActionPerformed
        // TODO add your handling code here:
        StringSelection sers = new StringSelection(statusPane.getText());
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(sers, null);
    }//GEN-LAST:event_copyButtonActionPerformed

    private void listAgentsItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_listAgentsItemStateChanged
        // TODO add your handling code here:
        id_agent_selected = listAgents.getSelectedIndex();
        loadGroups();
    }//GEN-LAST:event_listAgentsItemStateChanged

    private void jPanel2FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jPanel2FocusGained
        // TODO add your handling code here:
        jTabbedPane1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Agent Management", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 1, 12)));
        pack();
    }//GEN-LAST:event_jPanel2FocusGained

    private void jTabbedPane1StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jTabbedPane1StateChanged
        // TODO add your handling code here:
        if(jTabbedPane1.getSelectedIndex()==0){
            jTabbedPane1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Group Management", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 1, 12)));
        }else if (jTabbedPane1.getSelectedIndex()==1){
            jTabbedPane1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Agent Management", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 1, 12)));
        }else if(jTabbedPane1.getSelectedIndex()==2){
            jTabbedPane1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Directory Facilitator", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 1, 12)));
        }else if(jTabbedPane1.getSelectedIndex()==3){
            jTabbedPane1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Platform Management", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 1, 12)));
        }
    }//GEN-LAST:event_jTabbedPane1StateChanged

    private void removeAgentButton11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removeAgentButton11ActionPerformed
        // TODO add your handling code here:
        if(listAgents.getSelectedIndex()>-1){
            getSha().unregisterConnectionAux(listAgents.getSelectedItem());
            statusAgents.setText("");
            if(listAgents.getItemCount()>0){
                id_agent_selected = listAgents.getItemCount()-1;
                listAgents.select(id_agent_selected);
            }else{
                id_agent_selected = -1;
            }
        }else{
            statusAgents.setText("You have to select an agent");
        }
    }//GEN-LAST:event_removeAgentButton11ActionPerformed

    private void sendMessageButton11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sendMessageButton11ActionPerformed
        // TODO add your handling code here:
        if (listAgents.getSelectedIndex()>-1) {
            String agent = listAgents.getSelectedItem();
            MessageView mv = new MessageView(this, rootPaneCheckingEnabled, this.AGENT);
            mv.setVisible(true);
            String message = mv.getMessage();
            if (mv.getReturnStatus() == 1) {
                if (message != null && !message.equals("")) {
                    getSha().sendMessageGroupAux(agent, message);
                    this.statusAgents.setText("");
                } else {
                    this.statusAgents.setText("Message empty");
                }
            } else {
                this.statusAgents.setText("");
            }
        } else {
            this.statusAgents.setText("First, select an agent to send the message");
        }
    }//GEN-LAST:event_sendMessageButton11ActionPerformed

    private void listGroupsItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_listGroupsItemStateChanged
        // TODO add your handling code here:
        id_group_selected = listGroups.getSelectedIndex();
        updateAgents();
    }//GEN-LAST:event_listGroupsItemStateChanged

    private void sendMessageButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sendMessageButton1ActionPerformed
        // TODO add your handling code here:
        if (listGroups.getSelectedIndex()>-1) {
            String group = listGroups.getSelectedItem();
            MessageView mv = new MessageView(this, rootPaneCheckingEnabled, this.GROUP);
            mv.setVisible(true);
            String message = mv.getMessage();
            if (mv.getReturnStatus() == 1) {
                if (message != null && !message.equals("")) {
                    getSha().sendMessageGroupAux(group, message);
                    this.statusGroups.setText("");
                } else {
                    this.statusGroups.setText("Message empty");
                }
            } else {
                this.statusGroups.setText("");
            }
        } else {
            this.statusGroups.setText("First, select a group to send the message");
        }
    }//GEN-LAST:event_sendMessageButton1ActionPerformed

    private void removeAgentButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removeAgentButton1ActionPerformed
        // TODO add your handling code here:
        if(id_group_selected>-1){
            int[] rows = tableAgentsGroups.getSelectedRows();
            String agent;

            for(int i=0;i<rows.length;i++){
                agent = (String) tableAgentsGroups.getValueAt(rows[i],0);
                getSha().unregisterAgentGroupAux(listGroups.getItem(id_group_selected), agent);
            }
            this.statusGroups.setText("");
        } else {
            this.statusGroups.setText("Select the agent that will be unregistered from the selected group");
        }
    }//GEN-LAST:event_removeAgentButton1ActionPerformed

    private void addAgentButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addAgentButton1ActionPerformed
        // TODO add your handling code here:
        TreePath tp = treeAgents.getSelectionPath();
        if(tp!=null){
            DefaultMutableTreeNode node = (DefaultMutableTreeNode) tp.getLastPathComponent();
            if(id_group_selected>-1 && !node.getAllowsChildren()){
                if(!node.getAllowsChildren()){
                    getSha().registerAgentToGroupConnectionAux(listGroups.getItem(id_group_selected), (String)node.getUserObject());
                    statusGroups.setText("");
                }
                else{
                    statusGroups.setText("You have to select an agent from the tree and a group");
                }
            }
        }
    }//GEN-LAST:event_addAgentButton1ActionPerformed

    private void removeGroupButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removeGroupButtonActionPerformed
        // TODO add your handling code here:
        if(listGroups.getSelectedIndex()>-1){
            String[] groupName = listGroups.getSelectedItems();
            int s = groupName.length;
            for (int i = 0; i < s; i++) {
                if (groupName[i] != null) {
                    getSha().unregisterGroupConnectionAux(groupName[i]);
                }
            }
            if(listGroups.getItemCount()>0){
                id_group_selected = listGroups.getItemCount()-1;
                listGroups.select(id_group_selected);
            }else{
                id_group_selected = -1;
            }
            this.statusGroups.setText("");
        }else{
            this.statusGroups.setText("You have to select a group");
        }
    }//GEN-LAST:event_removeGroupButtonActionPerformed

    private void addGroupButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addGroupButtonActionPerformed
        // TODO add your handling code here:
        GroupNameView gnv = new GroupNameView(this, rootPaneCheckingEnabled);
        gnv.setVisible(true);
        String groupName = gnv.getGroupName();
        if (gnv.getReturnStatus() == 1) {
            if (groupName != null && !groupName.equals("")) {
                getSha().registerGroupConnectionAux(groupName);
                this.statusGroups.setText("");
            } else {
                this.statusGroups.setText("Group name not valid");
            }
        } else {
            this.statusGroups.setText("");
        }
    }//GEN-LAST:event_addGroupButtonActionPerformed

    private void treeAgentsMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_treeAgentsMouseReleased
        // TODO add your handling code here:
        if (SwingUtilities.isRightMouseButton(evt)) {
            //int row = treeAgents.getClosestRowForLocation(evt.getX(), evt.getY());
            TreePath tp = treeAgents.getClosestPathForLocation(evt.getX(), evt.getY());
            if(tp!=null){
                DefaultMutableTreeNode node = (DefaultMutableTreeNode) tp.getLastPathComponent();
                if(!node.getAllowsChildren()){
                    treeAgents.setSelectionPath(tp);
                    popupMenuTree.setEnabled(true);
                    popupMenuTree.setFocusable(true);
                    popupMenuTree.show(evt.getComponent(), evt.getX(), evt.getY());
                }else{
                    treeAgents.setSelectionPath(tp);
                }
            }
        }
    }//GEN-LAST:event_treeAgentsMouseReleased

    private void removeGroupButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removeGroupButton1ActionPerformed
        // TODO add your handling code here:
        if(id_agent_selected>-1){
            String agent = listAgents.getItem(id_agent_selected);
            int[] rows = tableAgentInGroups.getSelectedRows();
            String group;
            for(int i=0;i<rows.length;i++){
                group = (String) tableAgentInGroups.getValueAt(rows[i],0);
                getSha().unregisterAgentGroupAux(group, agent);
            }
            this.statusAgents.setText("");
        } else {
            this.statusAgents.setText("Select a group where the selected agent will be unregistered");
        }
    }//GEN-LAST:event_removeGroupButton1ActionPerformed

    private void removeServiceButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removeServiceButtonActionPerformed
        // TODO add your handling code here:
        DefaultTableModel model = (DefaultTableModel) tableServices.getModel();
        int[] rows = tableServices.getSelectedRows();
        Map <String,Service> serviceSelected;
        this.statusServices("",null);
        
        for(int i=0;i<rows.length;i++){
            serviceSelected = this.getRowDataAsMap(rows[i]);
            removeService(serviceSelected);
        }
    }//GEN-LAST:event_removeServiceButtonActionPerformed

    private void removeAllServicesButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removeAllServicesButtonActionPerformed
        // TODO add your handling code here:
        this.statusServices("",null);
        if(services!=null && !services.isEmpty()){
            getSha().unregisterAllServicesAux();
        }
    }//GEN-LAST:event_removeAllServicesButtonActionPerformed

    private void saveLogMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveLogMenuItemActionPerformed
        // TODO add your handling code here:
        //Opens filechooser
	JFileChooser fc = new JFileChooser(JFileChooser.FILE_FILTER_CHANGED_PROPERTY); 
	fc.setDialogTitle("Save .txt File");
	
	if (fc.showSaveDialog(this) == JFileChooser.APPROVE_OPTION)
	{
		//If they clicked yes call fileSaver method
		fileSaver(fc);
	}
	else
	{
		//Show cancelled message
		//JOptionPane.showMessageDialog(this, "Save Cancelled.", "Word Replacer Application Save Cancelled", JOptionPane.WARNING_MESSAGE);
	}
    }//GEN-LAST:event_saveLogMenuItemActionPerformed

    private void fileSaver(JFileChooser fc)
    //Saves string to file, pass in FileChooser
    {
	File file = fc.getSelectedFile();
	String textToSave = statusPane.getText(); 
	BufferedWriter writer = null;

	//Check for legal file extension (.txt)	
	String fileExtension = file.getPath();
        	
	//Set extension to .txt if not already	
	if(!fileExtension.toLowerCase().endsWith(".txt"))
	{
		file = new File(file + ".txt");
	}
	
	try
	{
		writer = new BufferedWriter(new FileWriter(file));
		//writer.write(textToSave.replaceAll("\n", System.getProperty("line.seperator")));
		writer.write(textToSave);
                
		JOptionPane.showMessageDialog(this, "Log saved (" + file.getName() + ")", "File Saved Successfully", JOptionPane.INFORMATION_MESSAGE);
	}catch (IOException e){}

	//Close writer
	finally
	{
		try
		{
			if(writer != null)
			{
				writer.close();
			}
		}
		catch (IOException e)
		{ }
	}
    }
    
    private void exitMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitMenuItemActionPerformed
        // TODO add your handling code here:
        System.exit(0);
        return;
    }//GEN-LAST:event_exitMenuItemActionPerformed

    private void aboutMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_aboutMenuItemActionPerformed
        // TODO add your handling code here:
        AboutSolView auv = new AboutSolView(this, true);
        auv.setVisible(true);
    }//GEN-LAST:event_aboutMenuItemActionPerformed

    private void contactMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_contactMenuItemActionPerformed
        // TODO add your handling code here:
        ContactUsView cuv = new ContactUsView(this, true);
        cuv.setVisible(true);
        String from = cuv.getFrom();
        String subject = "Feedback Sol - "+cuv.getSubject();
        String message = cuv.getMessage();
        if (cuv.getReturnStatus() == 1) {
            if (message != null && !message.equals("")) {
                if(this.sendEmail(from, subject, message)){
                    JOptionPane.showMessageDialog(this, "Feedback sent. We will contact you as soon as possible", "Feedback Sent", JOptionPane.INFORMATION_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Fill the message field", "Feedback Cancelled", JOptionPane.WARNING_MESSAGE);
            }
        } else {
            this.statusAgents.setText("");
        }
    }//GEN-LAST:event_contactMenuItemActionPerformed
   
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
            java.util.logging.Logger.getLogger(SolAgentPlatformView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(SolAgentPlatformView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(SolAgentPlatformView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(SolAgentPlatformView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                new SolAgentPlatformView().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel PanelServices;
    private javax.swing.JPanel PanelServices1;
    private javax.swing.JPanel PanelStatus;
    private javax.swing.JMenuItem aboutMenuItem;
    private javax.swing.JButton addAgentButton;
    private javax.swing.JButton addAgentButton1;
    private javax.swing.JButton addGroupButton;
    private javax.swing.JButton addGroupButton1;
    private javax.swing.JLabel agentIPLabel;
    private javax.swing.JLabel agentProtocolLabel;
    private javax.swing.JButton clearButton;
    private javax.swing.JMenuItem contactMenuItem;
    private javax.swing.JButton copyButton;
    private javax.swing.JMenuItem exitMenuItem;
    private javax.swing.JPanel gmsPanel;
    private javax.swing.JLabel imageProtocol;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JToolBar jToolBar;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JToolBar jToolBar2;
    private java.awt.List listAgents;
    private java.awt.List listGroups;
    private javax.swing.JPopupMenu popupMenuTree;
    private javax.swing.JButton removeAgentButton;
    private javax.swing.JButton removeAgentButton1;
    private javax.swing.JButton removeAllServicesButton;
    private javax.swing.JButton removeGroupButton;
    private javax.swing.JButton removeGroupButton1;
    private javax.swing.JButton removeServiceButton;
    private javax.swing.JMenuItem saveLogMenuItem;
    private javax.swing.JButton sendMessageButton;
    private javax.swing.JButton sendMessageButton1;
    private javax.swing.JLabel statusAgents;
    private javax.swing.JLabel statusGroups;
    private javax.swing.JTextPane statusPane;
    private javax.swing.JLabel statusServices;
    private javax.swing.JTable tableAgentInGroups;
    private javax.swing.JTable tableAgentsGroups;
    private javax.swing.JTable tableServices;
    private javax.swing.JTree treeAgents;
    // End of variables declaration//GEN-END:variables

    public void updateGroups() {
        listGroups.removeAll();
        try{
            List<String> groups = getSha().getIdGroups();
            Collections.sort(groups);
            for (int i = 0; i < groups.size(); i++) {
                listGroups.add(groups.get(i));
            }
        }catch(Exception e){
            System.out.println(e.getLocalizedMessage());
        }
        clearStatusLabel();
        updateAgents();
        
        /*try{
            groupCombo.setSelectedIndex(groupCombo.getItemCount()-1);
        }catch(Exception e){
            System.out.println(e.getLocalizedMessage());
        }*/
    }
    
    public void updateGroups(String id) {
        listGroups.removeAll();
        try{
            List<String> groups = getSha().getIdGroups();
            Collections.sort(groups);
            for (int i = 0; i < groups.size(); i++) {
                listGroups.add(groups.get(i));
                if(groups.get(i).equals(id)) id_group_selected = i;
            }
        }catch(Exception e){
            System.out.println(e.getLocalizedMessage());
        }
        clearStatusLabel();
        updateAgents();
        
        /*try{
            groupCombo.setSelectedIndex(groupCombo.getItemCount()-1);
        }catch(Exception e){
            System.out.println(e.getLocalizedMessage());
        }*/
    }

    protected void updateAgents() {
        DefaultTableModel model = (DefaultTableModel) tableAgentsGroups.getModel();
        while (model.getRowCount()>0){
            model.removeRow(0);
        }
        
        if(listGroups.getItemCount()>0){
            /*id_group_selected = listGroups.getSelectedIndex();
            if(id_group_selected<0){
                id_group_selected = 0;
            }*/
            while(id_group_selected >= listGroups.getItemCount()){
                id_group_selected--; //Aqui antes estaba id_agent_selected y generaba bucle sin fin al eliminar el ultimo grupo.
            }
            if(id_group_selected<0){
                id_group_selected = 0;
            }
            String group = listGroups.getItem(id_group_selected);
            listGroups.select(id_group_selected);
            
            TreeMap<String,String> info_group = getSha().getAllInfoAgentsGroup(group);

            String agent;
            String type_add;
            String type;
            String address;
            StringTokenizer st;

            for (Entry<String, String> entry : info_group.entrySet()) {
                agent = entry.getKey();
                type_add = entry.getValue();
                st = new StringTokenizer(type_add,"##");
                type = st.nextToken();
                address = st.nextToken();

                model.addRow(new Object[]{agent,type,address});
            }
        }
        loadGroups();
    }
    
    public void addStatusMessage(String message){
        Date date = new Date(); 
        appendTextStatus("["+date+"] "+message+"\n", Color.black);
    }

    private void appendTextStatus(String message, Color c) {
        if (c==null) c=Color.black;
        StyledDocument doc = statusPane.getStyledDocument();

        Style style = statusPane.addStyle(null, null);
        StyleConstants.setForeground(style, c);

        try { doc.insertString(doc.getLength(),message,style);}
        catch (BadLocationException e){}
    }

    public void notifyServicesRemoved() {
        Date date = new Date();
        String message = "Service(s) did remove correctly";
        appendTextStatus("["+date+"] "+message+"\n", Color.blue);
        //sssv.statusServices(message, Color.blue);
    }
    
    
    public void notifyServicesRemoved(String type, String nameService, String owner) {
        Date date = new Date();
        String message;
        if (type.equals("") && nameService.equals("")) {
            message = "All services from \""+owner+"\" of every type were removed correctly";
        }else{
            message = "Service \""+nameService+"\" of type \""+type+"\" and owner \""+owner+"\" were removed correctly";
        }
        appendTextStatus("["+date+"] "+message+"\n", Color.blue);
        //sssv.statusServices(message, Color.blue);
    }
    
    public void notifyServiceAdded(String type, Service ser){
        Date date = new Date();
        String message = "Service \""+ser.getName()+"\", which type is \""+type+"\" and owner is \""+ser.getOwner()+"\", were added correctly";
        appendTextStatus("["+date+"] "+message+"\n", Color.blue);
        //sssv.statusServices(message, Color.blue);
    }
    
    public void notifyGroupAdded(String groupName) {
        Date date = new Date();
        String message = "Group connection \""+groupName+"\" registered correctly";
        appendTextStatus("["+date+"] "+message+"\n", Color.blue);
    }
    
    public void notifyGroupRemoved(String groupName) {
        Date date = new Date();
        String message = "Group connection \""+groupName+"\" unregistered correctly";
        appendTextStatus("["+date+"] "+message+"\n", Color.blue);
    }
    
    public void notifyAgentGroupRemoved(String groupName, String agent, boolean registered) {
        Date date = new Date();
        String message;
        if(registered){
            message = "Agent \""+agent+"\" from group connection \""+groupName+"\" unregistered correctly";
            appendTextStatus("["+date+"] "+message+"\n", Color.blue);
        }else{
            //message = "Agent \""+agent+"\" doesn't exist in group connection \""+groupName+"\"";
            //appendTextStatus("["+date+"] "+message+"\n", Color.red);
        }
    }

    public void notifyAgentAdded(boolean registered, String agent) {
        Date date = new Date();        
        if(!registered){
            listAgents.add(agent);
            //To select the id of the agent we do the following things:
            for (int i = 0; i < listAgents.getItemCount(); i++) {
                if(listAgents.getItem(i).equals(agent)){
                    id_agent_selected = i;
                    break;
                }
            }
            //End to retrieve id of agent selected
            if(id_agent_selected>-1) listAgents.select(id_agent_selected);
            loadGroups();
            String message = "["+date+"] The agent \""+agent+"\" was correctly registered\n";
            this.appendTextStatus(message,Color.blue);
            //ssfv.addAgent(id);
        }
        else{
            String message = "["+date+"] Error found: The agent \""+agent+"\" is already registered\n";
            this.appendTextStatus(message,Color.red);
        }
    }
    
    public void notifyAgentRemoved(boolean registered, String agent) {
        Date date = new Date();
        String message;
        if(registered){
            listAgents.remove(agent);
            message = "["+date+"] The agent \""+agent+"\" was correctly unregistered\n";
            this.appendTextStatus(message,Color.blue);
            //ssfv.removeAgent(id);
        }else{
            message = "["+date+"] Error found: The agent \""+agent+"\" could not unregister\n";
            this.appendTextStatus(message,Color.red);
        }
    }
    
    public void updateTreeAgentsAdd(boolean registered, String id_mas, String agent, String category){
        if(!registered){
            //Hemos considerado 3 categorias y serás para iMuseumI, no valdran para otro MAS.
            String id_cat = category;

            DefaultTreeModel model = (DefaultTreeModel)treeAgents.getModel();
            DefaultMutableTreeNode root = (DefaultMutableTreeNode)model.getRoot();
            DefaultMutableTreeNode mas = null;
            DefaultMutableTreeNode cat = null;
            DefaultMutableTreeNode agt = new DefaultMutableTreeNode(agent);
            agt.setAllowsChildren(false);

            
            boolean found_mas = false;
            boolean found_cat = false;
            
            int num_mas = 0;
            int num_cat = 0;

            Enumeration<DefaultMutableTreeNode> chld = root.children();
            while (!found_mas && chld.hasMoreElements()) {
                mas = chld.nextElement();
                if(mas != null){
                    String aux_id_mas = (String) mas.getUserObject();
                    if(id_mas.equalsIgnoreCase(aux_id_mas)){
                    found_mas = true;
                    }
                }
            }
            
            if(found_mas){
                chld = mas.children();
                while (!found_cat && chld.hasMoreElements()) {
                    cat = chld.nextElement();
                    if(cat != null){
                        String aux_id_cat = (String) cat.getUserObject();
                        if(id_cat.equalsIgnoreCase(aux_id_cat)){
                            found_cat = true;
                            cat.add(agt);
                        }
                    }
                }
            }else{
                mas = new DefaultMutableTreeNode(id_mas);
            }

            if(!found_cat){
                cat = new DefaultMutableTreeNode(id_cat);
                cat.add(agt);
                mas.add(cat);
                root.add(mas);
            }

            model.reload(root);
            
            treeAgents.scrollPathToVisible(new TreePath(agt.getPath()));
        }
    }

    public void updateTreeAgentsRemove(String id) {
            DefaultTreeModel model = (DefaultTreeModel)treeAgents.getModel();
            DefaultMutableTreeNode root = (DefaultMutableTreeNode)model.getRoot();
            DefaultMutableTreeNode mas = null;
            DefaultMutableTreeNode cat = null;
            DefaultMutableTreeNode agent = null;
            boolean found_mas = false;
            boolean found_cat = false;

            Enumeration<DefaultMutableTreeNode> chld_mas = root.children();
            Enumeration<DefaultMutableTreeNode> chld_cat;
            Enumeration<DefaultMutableTreeNode> chld_agent;
            
            int row = 0;
            
            while (chld_mas.hasMoreElements()) {
                mas = chld_mas.nextElement();
                if(mas != null){
                    chld_cat = mas.children();
                    while (chld_cat.hasMoreElements()) {
                        cat = chld_cat.nextElement();
                        if(cat != null){
                            chld_agent = cat.children();
                            while (chld_agent.hasMoreElements()) {
                                agent = chld_agent.nextElement();
                                if(agent != null){
                                    if(id.equalsIgnoreCase((String)agent.getUserObject())){
                                        cat.remove(agent);
                                }
                            }
                        }
                    }
                }
            }

            model.reload(root);
            
            treeAgents.scrollPathToVisible(new TreePath(mas.getPath()));
            treeAgents.scrollPathToVisible(new TreePath(cat.getPath()));
        }
    }

    /*private boolean checkIsLeaf(int row) {
        boolean res = false;
        DefaultTreeModel model = (DefaultTreeModel) treeAgents.getModel();
        model.isLeaf(new DefaultMutableTreeNode());
        return res;
    }

    private boolean checkIsLeaf(TreePath tp) {
        boolean res = false;
        DefaultTreeModel model = (DefaultTreeModel) treeAgents.getModel();
        res = model.isLeaf(tp);
        return res;
    }*/
    
    private Map <String,Service> getRowDataAsMap(int rowIndex)
    {
        Map <String,Service>  retVal = new HashMap <String, Service>();
        List<String> colData = this.getColumnNames();
        String type, name, owner;
        
        type = getValueforCell(rowIndex, colData.indexOf("Type"));
        name = getValueforCell(rowIndex, colData.indexOf("Name"));
        owner = getValueforCell(rowIndex, colData.indexOf("Owner"));
        retVal.put(type, new Service(name,owner));
        
        return retVal;
    }
    
    private String[] getRowData(int rowIndex)
    {
        if ((rowIndex >  tableServices.getRowCount()) || (rowIndex  <  0))
        {
            return null;
        }
        ArrayList <String>  data = new ArrayList <String>();
        for (int c = 0; c  <  tableServices.getColumnCount(); c++)
        {
            data.add((String) this.getValueforCell(rowIndex, c));
        }
        String[] retVal = new String[data.size()];
        for (int i = 0; i  <  retVal.length; i++)
        {
            retVal[i] = data.get(i);
        }

        return retVal;
    }
    
    private String getValueforCell(int row, int col)
    {
        return (String) tableServices.getModel().getValueAt(row, col).toString();
    }
    
    private List<String> getColumnNames()
    {
        List<String> colNames = new LinkedList<String>();
        for (int i = 0; i  <  tableServices.getColumnCount(); i++)
        {
            colNames.add(tableServices.getColumnName(i));
        }
        return colNames;
    }
        
    public void updateServices(Hashtable<String, List<Service>> services) {
        this.services = services;
        loadTable();
    }

    private void loadTable() {
        Enumeration<String> keys=services.keys();
        Boolean end=false;
        String key;
        int i = 0;
        List<Service> ser;
        String type="";
        String name="";
        String owner="";
        
        //this.initComponents();
        DefaultTableModel model = (DefaultTableModel) tableServices.getModel();
        while (model.getRowCount()>0){
            model.removeRow(0);
        }
        //model.getDataVector().removeAllElements();
        
        while(!end && keys.hasMoreElements()){
            key=keys.nextElement();
            type = key;
            ser=this.services.get(key);
            i=0;
            while(!end && i<ser.size()){
                name = ser.get(i).getName();
                owner = ser.get(i).getOwner();
                model.addRow(new Object[]{type,name,owner});
                i++;
            }
        }
    }

    private void removeService(Map<String, Service> serviceSelected) {
        Enumeration<String> keys=services.keys();
        Boolean end = false;
        String key = null;
        List<Service> ser;
        Service service = null;
        
        while(!end && keys.hasMoreElements()){
            key=keys.nextElement();
            ser=this.services.get(key);
            if(serviceSelected.containsKey(key)){
                Iterator it = ser.iterator();
                while (!end && it.hasNext()){
                    Service aux = (Service) it.next();
                    end = serviceSelected.get(key).equals(aux);
                    service = aux;
                }
            }  
        }
        
        if(end) getSha().unregisterServiceAux(key, service.getName());
        if(tableServices.getRowCount()>0){
            tableServices.changeSelection(0, -1, false, false);
            tableServices.requestFocus();
        }
    }

    protected void statusServices(String message, Color c) {
        //statusServices.setForeground(c);
        statusServices.setText(message);
    }

    private void loadGroups() {
        DefaultTableModel model = (DefaultTableModel) tableAgentInGroups.getModel();
        while (model.getRowCount()>0){
            model.removeRow(0);
        }
        
        String type ="";
        String ip = "";
        if(listAgents.getItemCount()>0){
            while(id_agent_selected>=listAgents.getItemCount()){
                id_agent_selected--;
            }
            String agent = listAgents.getItem(id_agent_selected);
            TreeMap<String,String> info_agent = getSha().getAllInfoAgent(agent);
            String group;
            String type_address;
            String address;
            StringTokenizer st;
            type = getSha().getConnProtocol(agent);
            ip = getSha().getIPAgent(agent);

            for (Entry<String, String> entry : info_agent.entrySet()) {
                group = entry.getKey();
                type_address = entry.getValue();
                st = new StringTokenizer(type_address,"##");
                type = st.nextToken();
                address = st.nextToken();
                model.addRow(new Object[]{group,address});
            }
        }
        
        agentProtocolLabel.setText(type);
        
        if(listAgents.getItemCount()>0){
            if(type.equalsIgnoreCase("Android")){
                imageProtocol.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/images/Android.png")));
            }else if(type.equalsIgnoreCase("Bluetooth")){
                imageProtocol.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/images/Bluetooth.png")));
            }else if(type.equalsIgnoreCase("Sun Spot")){
                imageProtocol.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/images/SunSpot.png")));
            }else if(type.equalsIgnoreCase("ZigBee")){
                imageProtocol.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/images/ZigBee.png")));
            }else{
                imageProtocol.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/images/Unknown.png")));
            }
        }else{
            imageProtocol.setIcon(null);
        }
        
        agentIPLabel.setText(ip);
    }

    private void clearStatusLabel() {
        statusGroups.setText("");
        statusAgents.setText("");
    }

    private boolean sendEmail(String from, String subject, String msg) {
        // Recipient's email ID needs to be mentioned.
      /*String to = "jlbb@lcc.uma.es";

      // Sender's email ID needs to be mentioned
      //String from = "web@gmail.com";

      // Assuming you are sending email from localhost
      String host = "localhost";

      // Get system properties
      Properties properties = System.getProperties();

      // Setup mail server
      properties.setProperty("mail.smtp.host", host);

      // Get the default Session object.
      Session session = Session.getDefaultInstance(properties);

      try{
         // Create a default MimeMessage object.
         MimeMessage message = new MimeMessage(session);

         // Set From: header field of the header.
         message.setFrom(new InternetAddress(from));

         // Set To: header field of the header.
         message.addRecipient(Message.RecipientType.TO,
                                  new InternetAddress(to));

         // Set Subject: header field
         message.setSubject(subject);

         // Now set the actual message
         message.setText(msg);

         // Send message
         Transport.send(message);
         //System.out.println("Sent message successfully....");
         return true;
      }catch (MessagingException mex) {
         mex.printStackTrace();
         return false;
      }*/
        return false;
    }

    /**
     * @return the sha
     */
    private SunSpotHostApplication getSha() {
        return sha;
    }
}
