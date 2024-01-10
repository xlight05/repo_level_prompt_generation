/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * frmGiaTienBang.java
 *
 * Created on May 24, 2011, 2:53:41 PM
 */

package Presentation;

import RSCall.GiaTienQCBangRSc;
import RSCall.LoaiBangRSc;
import Entities.GIATIEN_QC_BANG;
import Entities.LOAIBANG;
import java.util.ArrayList;
import java.util.Vector;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author VooKa
 */
public class frmGiaTienBang extends javax.swing.JInternalFrame {

    /** Creates new form frmGiaTienBang */
    public frmGiaTienBang() {
        initComponents();
    }

    private void clear() {
        cbxLoaiBang.setSelectedIndex(0);
        tbxGiaTien.setText("");
        tbxMoTa.setText("");
        tbxKichCo.setText("");
        tbxDVT.setText("");
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        label = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        cbxLoaiBang = new javax.swing.JComboBox();
        tbxKichCo = new javax.swing.JTextField();
        tbxDVT = new javax.swing.JTextField();
        tbxGiaTien = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbxMoTa = new javax.swing.JTextArea();
        btnAdd = new javax.swing.JButton();
        btnUpdate = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblGiaTienBang = new javax.swing.JTable();

        setTitle(java.util.ResourceBundle.getBundle("Presentation/" + frmMain.resource).getString("titlefrmGiaTienBang"));
        setPreferredSize(new java.awt.Dimension(515, 500));
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

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel1.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel1.setText(java.util.ResourceBundle.getBundle("Presentation/" + frmMain.resource).getString("board"));

        jLabel2.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel2.setText(java.util.ResourceBundle.getBundle("Presentation/" + frmMain.resource).getString("size"));

        jLabel3.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel3.setText(java.util.ResourceBundle.getBundle("Presentation/" + frmMain.resource).getString("unit"));

        label.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        label.setText(java.util.ResourceBundle.getBundle("Presentation/" + frmMain.resource).getString("cost"));

        jLabel4.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel4.setText(java.util.ResourceBundle.getBundle("Presentation/" + frmMain.resource).getString("describe"));

        tbxKichCo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                tbxKichCoKeyTyped(evt);
            }
        });

        tbxGiaTien.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                tbxGiaTienKeyTyped(evt);
            }
        });

        tbxMoTa.setColumns(20);
        tbxMoTa.setLineWrap(true);
        tbxMoTa.setRows(3);
        tbxMoTa.setWrapStyleWord(true);
        tbxMoTa.setAutoscrolls(false);
        jScrollPane1.setViewportView(tbxMoTa);

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

        tblGiaTienBang.setModel(new javax.swing.table.DefaultTableModel(
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
        tblGiaTienBang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblGiaTienBangMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblGiaTienBang);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                    .addComponent(btnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(47, 47, 47)
                                    .addComponent(btnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 59, Short.MAX_VALUE)
                                    .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel2)
                                        .addComponent(jLabel3)
                                        .addComponent(label)
                                        .addComponent(jLabel4))
                                    .addGap(18, 18, 18)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(tbxDVT)
                                        .addComponent(tbxKichCo)
                                        .addComponent(tbxGiaTien)
                                        .addComponent(cbxLoaiBang, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 235, Short.MAX_VALUE)))))
                        .addGap(122, 122, 122))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 455, Short.MAX_VALUE)
                        .addContainerGap())))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(cbxLoaiBang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(tbxKichCo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(tbxDVT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(label)
                    .addComponent(tbxGiaTien, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addGap(79, 79, 79))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jScrollPane1)
                        .addGap(18, 18, 18)))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnUpdate)
                    .addComponent(btnAdd)
                    .addComponent(btnDelete))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 132, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap(20, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formInternalFrameOpened(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameOpened
        this.LoadData();
        this.LoadCombobox();
    }//GEN-LAST:event_formInternalFrameOpened

    // Xử lý thêm Giá Tiền quảng cáo Bảng
    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        String moTa = tbxMoTa.getText().trim();
        String kichCo = tbxKichCo.getText().trim();
        String  donViTinh = tbxDVT.getText().trim();
        String giaTien = tbxGiaTien.getText().trim();
        if(cbxLoaiBang.getSelectedItem().toString().isEmpty())
            JOptionPane.showMessageDialog(this,
                    java.util.ResourceBundle.getBundle("Presentation/" + frmMain.resource).getString("yeucauloaibang"));
        else
            if(kichCo.isEmpty())
                JOptionPane.showMessageDialog(this,
                        java.util.ResourceBundle.getBundle("Presentation/" + frmMain.resource).getString("chuanhapkichco"));
            else
                if(donViTinh.isEmpty())
                    JOptionPane.showMessageDialog(this,
                            java.util.ResourceBundle.getBundle("Presentation/" + frmMain.resource).getString("chuanhapdonvitinh"));
                else
                    if(giaTien.isEmpty())
                        JOptionPane.showMessageDialog(this,
                                java.util.ResourceBundle.getBundle("Presentation/" + frmMain.resource).getString("chuanhapgiatien"));
                    else
                        if(moTa.isEmpty())
                            JOptionPane.showMessageDialog(this,
                                    java.util.ResourceBundle.getBundle("Presentation/" + frmMain.resource).getString("chuanhapmota."));
                        else
                        {
                            try
                            {
                                GiaTienQCBangRSc dbGiaTienACBang = new GiaTienQCBangRSc();
                                String maLoaiBang = this.ReturnMaLoaiBang(cbxLoaiBang.getSelectedIndex());
                                Float kichCoBang = Float.MIN_VALUE;
                                Float giaTienBang = Float.MIN_VALUE;
                                try{
                                    kichCoBang = Float.parseFloat(kichCo);
                                    giaTienBang = Float.parseFloat(giaTien);
                                    dbGiaTienACBang.Insert(maLoaiBang, moTa, kichCoBang, donViTinh, giaTienBang);
                                    this.LoadData();
                                    clear();
                                    JOptionPane.showMessageDialog(this,
                                            java.util.ResourceBundle.getBundle("Presentation/" + frmMain.resource).getString("insertedGiaTienBang"));
                                }catch(NumberFormatException e){
                                    JOptionPane.showMessageDialog(this,
                                            java.util.ResourceBundle.getBundle("Presentation/" + frmMain.resource).getString("nhapso"));
                                }
                            }catch(Exception e){
                                JOptionPane.showMessageDialog(this, e.toString());
                            }
                        }
    }//GEN-LAST:event_btnAddActionPerformed

    private void tblGiaTienBangMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblGiaTienBangMouseClicked
        int row = this.tblGiaTienBang.getSelectedRow();
        cbxLoaiBang.setSelectedItem((String)this.tblGiaTienBang.getValueAt(row, 2));
        tbxKichCo.setText(String.valueOf(this.tblGiaTienBang.getValueAt(row, 3)));
        tbxDVT.setText((String)this.tblGiaTienBang.getValueAt(row, 4));
        tbxGiaTien.setText(String.valueOf(this.tblGiaTienBang.getValueAt(row, 5)));
        tbxMoTa.setText((String)this.tblGiaTienBang.getValueAt(row, 6));
    }//GEN-LAST:event_tblGiaTienBangMouseClicked

    // Xử lý cập nhập
    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
        String moTa = tbxMoTa.getText().trim();
        String kichCo = tbxKichCo.getText().trim();
        String  donViTinh = tbxDVT.getText().trim();
        String giaTien = tbxGiaTien.getText().trim();
        if(this.tblGiaTienBang.getSelectedRow()==-1)
            JOptionPane.showMessageDialog(this,
                    java.util.ResourceBundle.getBundle("Presentation/" + frmMain.resource).getString("mustselectGiaTien"));
        else
            if(cbxLoaiBang.getSelectedItem().toString().isEmpty())
                JOptionPane.showMessageDialog(this,
                        java.util.ResourceBundle.getBundle("Presentation/" + frmMain.resource).getString("yeucauloaibang"));
            else
                if(kichCo.isEmpty())
                    JOptionPane.showMessageDialog(this,
                            java.util.ResourceBundle.getBundle("Presentation/" + frmMain.resource).getString("chuanhapkichco"));
                else
                    if(donViTinh.isEmpty())
                        JOptionPane.showMessageDialog(this,
                                java.util.ResourceBundle.getBundle("Presentation/" + frmMain.resource).getString("chuanhapdonvitinh"));
                    else
                        if(giaTien.isEmpty())
                            JOptionPane.showMessageDialog(this,
                                    java.util.ResourceBundle.getBundle("Presentation/" + frmMain.resource).getString("chuanhapgiatien"));
                        else
                            if(moTa.isEmpty())
                                JOptionPane.showMessageDialog(this,
                                        java.util.ResourceBundle.getBundle("Presentation/" + frmMain.resource).getString("chuanhapmota"));
                            else
                            {
                                try
                                {
                                    GiaTienQCBangRSc dbGiaTienQCBang = new GiaTienQCBangRSc();
                                    String maGiaTien = this.tblGiaTienBang.getValueAt(tblGiaTienBang.getSelectedRow(), 1).toString();
                                    String maLoaiBang = this.ReturnMaLoaiBang(cbxLoaiBang.getSelectedIndex());
                                    Float kichCoBang = Float.MIN_VALUE;
                                    Float giaTienBang = Float.MIN_VALUE;
                                    try{
                                        kichCoBang = Float.parseFloat(kichCo);
                                        giaTienBang = Float.parseFloat(giaTien);
                                        dbGiaTienQCBang.Update(maGiaTien, maLoaiBang, moTa, kichCoBang, donViTinh, giaTienBang);
                                        this.LoadData();
                                        clear();
                                        JOptionPane.showMessageDialog(this,
                                                java.util.ResourceBundle.getBundle("Presentation/" + frmMain.resource).getString("updatedGiaTienBang"));
                                    }catch(NumberFormatException e){
                                        JOptionPane.showMessageDialog(this,
                                                java.util.ResourceBundle.getBundle("Presentation/" + frmMain.resource).getString("nhapso"));
                                    }
                                }catch(Exception e){
                                    JOptionPane.showMessageDialog(this, e.toString());
                                }
                            }
    }//GEN-LAST:event_btnUpdateActionPerformed

    // Xử lý xóa
    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        // TODO add your handling code here:
        if(this.tblGiaTienBang.getSelectedRow()==-1)
            JOptionPane.showMessageDialog(this,
                    java.util.ResourceBundle.getBundle("Presentation/" + frmMain.resource).getString("mustselectGiaTien"));
        else
        {
            int n = JOptionPane.showConfirmDialog(this,
                    java.util.ResourceBundle.getBundle("Presentation/" + frmMain.resource).getString("really delete"),
                    java.util.ResourceBundle.getBundle("Presentation/" + frmMain.resource).getString("warn"),JOptionPane.YES_NO_OPTION);
            if(n==0)
            {
                try
                {
                    GiaTienQCBangRSc _giaTien = new GiaTienQCBangRSc();
                    _giaTien.Delete((String)this.tblGiaTienBang.getValueAt(this.tblGiaTienBang.getSelectedRow(), 1));
                    this.LoadData();
                    this.clear();
                    JOptionPane.showMessageDialog(this,
                    java.util.ResourceBundle.getBundle("Presentation/" + frmMain.resource).getString("delete"));
                }catch(Exception e){
                    JOptionPane.showMessageDialog(this, e.toString());
                }
            }
        }
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void tbxGiaTienKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbxGiaTienKeyTyped
        Utilities.Utilities.tbxKeyTyped(evt);
    }//GEN-LAST:event_tbxGiaTienKeyTyped

    private void tbxKichCoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbxKichCoKeyTyped
        Utilities.Utilities.tbxKeyTyped(evt);
    }//GEN-LAST:event_tbxKichCoKeyTyped
    private String ReturnMaLoaiBang(int index){
        try
        {
            LoaiBangRSc _loai = new LoaiBangRSc();
            ArrayList<LOAIBANG> lst = _loai.GetAllRows();
            LOAIBANG _bang = lst.get(index);
            return _bang.getMaLoaiBang();
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(this, e.toString());
        }
        return null;
    }
    private void LoadData()
    {

        try
        {
            GiaTienQCBangRSc _giaTien = new GiaTienQCBangRSc();
            ArrayList<GIATIEN_QC_BANG> lst = _giaTien.GetAllRows();
            DefaultTableModel dtm = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int x,int y){
                return false;
            }
            };
            dtm.addColumn(java.util.ResourceBundle.getBundle("Presentation/" + frmMain.resource).getString("STT"));
            dtm.addColumn(java.util.ResourceBundle.getBundle("Presentation/" + frmMain.resource).getString("MÃ GIÁ TIỀN"));
            dtm.addColumn(java.util.ResourceBundle.getBundle("Presentation/" + frmMain.resource).getString("board"));
            dtm.addColumn(java.util.ResourceBundle.getBundle("Presentation/" + frmMain.resource).getString("size"));
            dtm.addColumn(java.util.ResourceBundle.getBundle("Presentation/" + frmMain.resource).getString("dvt"));
            dtm.addColumn(java.util.ResourceBundle.getBundle("Presentation/" + frmMain.resource).getString("cost"));
            dtm.addColumn(java.util.ResourceBundle.getBundle("Presentation/" + frmMain.resource).getString("describe"));
            LoaiBangRSc _loai = new LoaiBangRSc();
            for(int i=0;i<lst.size();i++)
            {
                Vector _v = new Vector();
                GIATIEN_QC_BANG _g = lst.get(i);
                _v.add(i+1);
                _v.add(_g.getMaGiaTien());
                LOAIBANG _l = _loai.GetRowByID(_g.getMaLoaiBang());
                _v.add(_l.getLoaiBang().trim());
                _v.add(_g.getKichCo());
                _v.add(_g.getDVT());
                _v.add(_g.getGiaTien());
                _v.add(_g.getMoTa().trim());
                dtm.addRow(_v);
            }
            this.tblGiaTienBang.setModel(dtm);
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(this, e.toString());
        }
    }
    private void LoadCombobox()
    {
        try
        {
            LoaiBangRSc _loai = new LoaiBangRSc();
            ArrayList<LOAIBANG> lst = _loai.GetAllRows();
            for(int i=0;i<lst.size();i++)
            {
                LOAIBANG _bang = lst.get(i);
                cbxLoaiBang.addItem(_bang.getLoaiBang());
            }
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
    private javax.swing.JComboBox cbxLoaiBang;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel label;
    private javax.swing.JTable tblGiaTienBang;
    private javax.swing.JTextField tbxDVT;
    private javax.swing.JTextField tbxGiaTien;
    private javax.swing.JTextField tbxKichCo;
    private javax.swing.JTextArea tbxMoTa;
    // End of variables declaration//GEN-END:variables

}