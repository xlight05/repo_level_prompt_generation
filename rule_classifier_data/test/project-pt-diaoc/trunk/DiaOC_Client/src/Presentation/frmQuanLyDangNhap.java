/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * frmQuanLyDangNhap.java
 *
 * Created on May 28, 2011, 2:43:21 PM
 */

package Presentation;

import RSCall.DangNhapRSc;
import Entities.DANGNHAP;
import ExceptionMessage.UserNameTonTai;
import java.util.ArrayList;
import java.util.Vector;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author VooKa
 */
public class frmQuanLyDangNhap extends javax.swing.JInternalFrame {

    /** Creates new form frmQuanLyDANGNHAP */
    public frmQuanLyDangNhap() {
        initComponents();
    }

    private void clear() {
        tbxUsername.setText("");
        tbxPassword.setText("");
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        tbxUsername = new javax.swing.JTextField();
        tbxPassword = new javax.swing.JPasswordField();
        btnAdd = new javax.swing.JButton();
        btnUpdate = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();

        setTitle(java.util.ResourceBundle.getBundle("Presentation/" + frmMain.resource).getString("titlefrmQuanLyDangNhap"));
        addInternalFrameListener(new javax.swing.event.InternalFrameListener() {
            public void internalFrameActivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosed(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosing(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameDeactivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameDeiconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameIconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameOpened(javax.swing.event.InternalFrameEvent evt) {
                formInternalFrameOpened(evt);
            }
        });

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel2.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel2.setText(java.util.ResourceBundle.getBundle("Presentation/" + frmMain.resource).getString("PASSWORD"));

        jLabel1.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel1.setText("UserName");

        btnAdd.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        btnAdd.setText(java.util.ResourceBundle.getBundle("Presentation/" + frmMain.resource).getString("insert"));
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });

        btnUpdate.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        btnUpdate.setText(java.util.ResourceBundle.getBundle("Presentation/" + frmMain.resource).getString("update"));
        btnUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateActionPerformed(evt);
            }
        });

        btnDelete.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        btnDelete.setText(java.util.ResourceBundle.getBundle("Presentation/" + frmMain.resource).getString("delete"));
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel1))
                        .addGap(54, 54, 54)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(tbxPassword)
                            .addComponent(tbxUsername, javax.swing.GroupLayout.DEFAULT_SIZE, 167, Short.MAX_VALUE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(btnAdd)
                        .addGap(44, 44, 44)
                        .addComponent(btnUpdate)
                        .addGap(48, 48, 48)
                        .addComponent(btnDelete)))
                .addContainerGap(76, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(tbxUsername, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(tbxPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAdd)
                    .addComponent(btnUpdate)
                    .addComponent(btnDelete))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1))
                .addContainerGap(14, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formInternalFrameOpened(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameOpened
        // TODO add your handling code here:
        this.LoadData();
    }//GEN-LAST:event_formInternalFrameOpened

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        // TODO add your handling code here:
        if(tbxUsername.getText().isEmpty())
            JOptionPane.showMessageDialog(this, java.util.ResourceBundle.getBundle("Presentation/" + frmMain.resource).getString("not enter username"));
        else
            if(tbxPassword.getText().isEmpty())
                JOptionPane.showMessageDialog(this, java.util.ResourceBundle.getBundle("Presentation/" + frmMain.resource).getString("not enter pass"));            
            else
            {
                try
                {
                     DangNhapRSc _dangNhap = new DangNhapRSc();
                     _dangNhap.Insert(tbxUsername.getText().trim(), tbxPassword.getText().trim());
                     this.LoadData();
                     clear();
                     JOptionPane.showMessageDialog(this, java.util.ResourceBundle.getBundle("Presentation/" + frmMain.resource).getString("insertedaaccount"));
                }
                catch(UserNameTonTai e)
                {
                    JOptionPane.showMessageDialog(this, java.util.ResourceBundle.getBundle("Presentation/" + frmMain.resource).getString("userused"));
                }
                catch(Exception e)
                {
                    JOptionPane.showMessageDialog(this, e.toString());
                }
            }
    }//GEN-LAST:event_btnAddActionPerformed

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
        // TODO add your handling code here:
        if(this.jTable1.getSelectedRow()==-1)
            JOptionPane.showMessageDialog(this, java.util.ResourceBundle.getBundle("Presentation/" + frmMain.resource).getString("mustselectGiaTien"));
        else
            if(tbxUsername.getText().isEmpty())
                JOptionPane.showMessageDialog(this, java.util.ResourceBundle.getBundle("Presentation/" + frmMain.resource).getString("not enter username"));
            else
                if(tbxPassword.getText().isEmpty())
                    JOptionPane.showMessageDialog(this, java.util.ResourceBundle.getBundle("Presentation/" + frmMain.resource).getString("not enter pass"));                
                else
                {
                    try
                    {
                        DangNhapRSc _dangNhap = new DangNhapRSc();
                        _dangNhap.Update((Integer)this.jTable1.getValueAt(this.jTable1.getSelectedRow(), 0), tbxUsername.getText(), tbxPassword.getText());
                        this.LoadData();
                        this.clear();
                        JOptionPane.showMessageDialog(this, java.util.ResourceBundle.getBundle("Presentation/" + frmMain.resource).getString("updatedaacount"));
                    }
                    catch(Exception e)
                    {
                        JOptionPane.showMessageDialog(this, e.toString());
                    }
                }
    }//GEN-LAST:event_btnUpdateActionPerformed

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        // TODO add your handling code here:
        tbxUsername.setText((String)this.jTable1.getValueAt(this.jTable1.getSelectedRow(), 1));
        tbxPassword.setText((String)this.jTable1.getValueAt(this.jTable1.getSelectedRow(), 2));
    }//GEN-LAST:event_jTable1MouseClicked

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        if(this.jTable1.getSelectedRow()==-1)
        JOptionPane.showMessageDialog(this, java.util.ResourceBundle.getBundle("Presentation/" + frmMain.resource).getString("mustselectGiaTien"));
        else
        {
            int n = JOptionPane.showConfirmDialog(this,
                java.util.ResourceBundle.getBundle("Presentation/" + frmMain.resource).getString("really delete"),
                java.util.ResourceBundle.getBundle("Presentation/" + frmMain.resource).getString("warn"),JOptionPane.YES_NO_OPTION);
            if(n==0)
            {
                try
                {
                    DangNhapRSc _dangNhap = new DangNhapRSc();
                    _dangNhap.Delete((Integer)this.jTable1.getValueAt(this.jTable1.getSelectedRow(), 0));
                    this.LoadData();
                    this.clear();
                }
                catch(Exception e)
                {
                    JOptionPane.showMessageDialog(this, e.toString());
                }
            }
        }
    }//GEN-LAST:event_btnDeleteActionPerformed
    private void LoadData(){
        
        try
        {
            DangNhapRSc _dangNhap = new DangNhapRSc();
            ArrayList<DANGNHAP> lst = _dangNhap.GetAllRows();
            DefaultTableModel dtm = new DefaultTableModel(){
                @Override
                public boolean isCellEditable(int x, int y){
                    return false;
                }
            };
            dtm.addColumn("id");
            dtm.addColumn("UserName");
            dtm.addColumn(java.util.ResourceBundle.getBundle("Presentation/" + frmMain.resource).getString("PASSWORD"));
            for(int i=0;i<lst.size();i++)
            {
                DANGNHAP _d = lst.get(i);
                Vector _v = new Vector();
                _v.add(_d.getID());
                _v.add(_d.getUserName());
                _v.add(_d.getPassWord());
                dtm.addRow(_v);
            }
            this.jTable1.setModel(dtm);
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(this, e.toString());
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnUpdate;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JPasswordField tbxPassword;
    private javax.swing.JTextField tbxUsername;
    // End of variables declaration//GEN-END:variables

}
