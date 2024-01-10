/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * frmDangKyKH.java
 *
 * Created on 08-06-2011, 05:31:45
 */
package Presentation;

import RSCall.DuongRSc;
import RSCall.KhachHangRSc;
import RSCall.PhuongRSc;
import RSCall.QuanRSc;
import RSCall.ThanhPhoRSc;
import Entities.DUONG;
import Entities.DIACHI;
import Entities.KHACHHANG;
import Entities.PHUONG;
import Entities.QUAN;
import Entities.THANHPHO;
import java.awt.Font;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Nixforest
 */
public class frmDangKyKH extends javax.swing.JInternalFrame {

    /** Creates new form frmDangKyKH */
    public frmDangKyKH() {
        initComponents();
        Creation();
        LoadData();
        
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
        tbxHoTen = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        tbxDiaChiCTKH = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        tbxSoDT = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        tbxEmail = new javax.swing.JTextField();
        chbxKhachHangCu = new javax.swing.JCheckBox();
        jLabel33 = new javax.swing.JLabel();
        cbxMaKH = new javax.swing.JComboBox();
        jLabel5 = new javax.swing.JLabel();
        cbxThanhPho = new javax.swing.JComboBox();
        jLabel6 = new javax.swing.JLabel();
        cbxQuan = new javax.swing.JComboBox();
        jLabel7 = new javax.swing.JLabel();
        cbxPhuong = new javax.swing.JComboBox();
        jLabel8 = new javax.swing.JLabel();
        cbxDuong = new javax.swing.JComboBox();
        jLabel9 = new javax.swing.JLabel();
        btnTiep = new javax.swing.JButton();
        btnThoat = new javax.swing.JButton();

        setClosable(true);
        setTitle(java.util.ResourceBundle.getBundle("Presentation/" + frmMain.resource).getString("titlefrmDangKyKH"));
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

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(java.util.ResourceBundle.getBundle("Presentation/" + frmMain.resource).getString("chudiaoc")));

        jLabel1.setText(java.util.ResourceBundle.getBundle("Presentation/" + frmMain.resource).getString("hoten"));

        jLabel2.setText(java.util.ResourceBundle.getBundle("Presentation/" + frmMain.resource).getString("address"));

        jLabel3.setText(java.util.ResourceBundle.getBundle("Presentation/" + frmMain.resource).getString("soDT"));

        tbxSoDT.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                tbxSoDTKeyTyped(evt);
            }
        });

        jLabel4.setText(java.util.ResourceBundle.getBundle("Presentation/" + frmMain.resource).getString("email"));

        chbxKhachHangCu.setText(java.util.ResourceBundle.getBundle("Presentation/" + frmMain.resource).getString("oldcustomer"));
        chbxKhachHangCu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chbxKhachHangCuActionPerformed(evt);
            }
        });

        jLabel33.setText(java.util.ResourceBundle.getBundle("Presentation/" + frmMain.resource).getString("customerid"));

        cbxMaKH.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel5.setText(java.util.ResourceBundle.getBundle("Presentation/" + frmMain.resource).getString("city"));

        jLabel6.setText(java.util.ResourceBundle.getBundle("Presentation/" + frmMain.resource).getString("district"));

        jLabel7.setText(java.util.ResourceBundle.getBundle("Presentation/" + frmMain.resource).getString("precinct"));

        jLabel8.setText(java.util.ResourceBundle.getBundle("Presentation/" + frmMain.resource).getString("street"));

        jLabel9.setText(java.util.ResourceBundle.getBundle("Presentation/" + frmMain.resource).getString("detail"));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(18, 18, 18)
                        .addComponent(tbxHoTen, javax.swing.GroupLayout.DEFAULT_SIZE, 258, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6)
                            .addComponent(jLabel5)
                            .addComponent(jLabel7)
                            .addComponent(jLabel8)
                            .addComponent(jLabel9))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cbxDuong, 0, 188, Short.MAX_VALUE)
                            .addComponent(tbxDiaChiCTKH, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 188, Short.MAX_VALUE)
                            .addComponent(cbxPhuong, javax.swing.GroupLayout.Alignment.TRAILING, 0, 188, Short.MAX_VALUE)
                            .addComponent(cbxThanhPho, 0, 188, Short.MAX_VALUE)
                            .addComponent(cbxQuan, 0, 188, Short.MAX_VALUE)))
                    .addComponent(chbxKhachHangCu)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addGap(18, 18, 18)
                        .addComponent(tbxEmail, javax.swing.GroupLayout.DEFAULT_SIZE, 258, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addGap(18, 18, 18)
                        .addComponent(tbxSoDT, javax.swing.GroupLayout.DEFAULT_SIZE, 258, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel33)
                        .addGap(18, 18, 18)
                        .addComponent(cbxMaKH, 0, 258, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(tbxHoTen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel5)
                    .addComponent(cbxThanhPho, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(cbxQuan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7)
                    .addComponent(cbxPhuong, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(cbxDuong, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel9)
                    .addComponent(tbxDiaChiCTKH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(tbxSoDT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(tbxEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(chbxKhachHangCu)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel33)
                    .addComponent(cbxMaKH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        btnTiep.setText(java.util.ResourceBundle.getBundle("Presentation/" + frmMain.resource).getString("continue"));
        btnTiep.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTiepActionPerformed(evt);
            }
        });

        btnThoat.setText(java.util.ResourceBundle.getBundle("Presentation/" + frmMain.resource).getString("close"));
        btnThoat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThoatActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(46, 46, 46)
                .addComponent(btnTiep)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 80, Short.MAX_VALUE)
                .addComponent(btnThoat)
                .addGap(70, 70, 70))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 60, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnTiep)
                    .addComponent(btnThoat))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tbxSoDTKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbxSoDTKeyTyped
        Utilities.Utilities.tbxKeyTyped(evt);
}//GEN-LAST:event_tbxSoDTKeyTyped

    private void chbxKhachHangCuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chbxKhachHangCuActionPerformed
        cbxMaKH.setEnabled(chbxKhachHangCu.isSelected());
}//GEN-LAST:event_chbxKhachHangCuActionPerformed

    private void btnTiepActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTiepActionPerformed
        try {
            if (chbxKhachHangCu.isSelected()) {
                kh = dbKhachHang.GetRowByID(cbxMaKH.getSelectedItem().toString());                    
            }else{
                String hoTen = tbxHoTen.getText().trim();
                String maThanhPho = ((THANHPHO)cbxThanhPho.getSelectedItem()).getMaThanhPho();
                String maQuan = ((QUAN)cbxQuan.getSelectedItem()).getMaQuan();
                String maPhuong = ((PHUONG)cbxPhuong.getSelectedItem()).getMaPhuong();
                String maDuong = ((DUONG)cbxDuong.getSelectedItem()).getMaDuong();
                String diaChiCT = tbxDiaChiCTKH.getText().trim();
                String soDT = tbxSoDT.getText().trim();
                String email = tbxEmail.getText().trim();
                Utilities.Utilities.EmailValidator em = new Utilities.Utilities.EmailValidator();
                if (hoTen.isEmpty()) {
                    JOptionPane.showMessageDialog(rootPane, java.util.ResourceBundle.getBundle("Presentation/" + frmMain.resource).getString("mustenterhoten"));
                }else if(soDT.isEmpty()){
                    JOptionPane.showMessageDialog(rootPane, java.util.ResourceBundle.getBundle("Presentation/" + frmMain.resource).getString("mustentersodt"));
                }else if(email.isEmpty()){
                    JOptionPane.showMessageDialog(rootPane, java.util.ResourceBundle.getBundle("Presentation/" + frmMain.resource).getString("mustenteremail"));
                }else if(!em.validate(email)){
                    JOptionPane.showMessageDialog(rootPane, java.util.ResourceBundle.getBundle("Presentation/" + frmMain.resource).getString("mustentervalidemail"));
                }else{
                    String maDC = "";
                    DIACHI dc = new DIACHI(maDC, maThanhPho, maQuan, maPhuong, maDuong, diaChiCT);
                    String maKH = "";
                    kh = new KHACHHANG(maKH, hoTen, dc, soDT, email);
                }
            }
            
            frmDangKy f = new frmDangKy(kh);
            Utilities.Utilities.setFontForAll(f, new Font(java.util.ResourceBundle.getBundle("Presentation/" + frmMain.resource).getString("CALIBRI"), 0, 14));
            f.setClosable(true);
            f.setLocation(155, 0);
            f.show();
            this.getParent().add(f, 0);
            this.dispose();
        } catch (Exception ex) {
            Logger.getLogger(frmDangKyKH.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnTiepActionPerformed

    private void btnThoatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThoatActionPerformed
        int i = JOptionPane.showConfirmDialog(rootPane, java.util.ResourceBundle.getBundle("Presentation/" + frmMain.resource).getString("really want to close"), java.util.ResourceBundle.getBundle("Presentation/vn").getString("warn"), JOptionPane.YES_NO_OPTION);
        if(i == 0){
            this.dispose();
        }else {
        }
    }//GEN-LAST:event_btnThoatActionPerformed

    private void formInternalFrameOpened(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameOpened
        this.getRootPane().setDefaultButton(btnTiep);
    }//GEN-LAST:event_formInternalFrameOpened

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnThoat;
    private javax.swing.JButton btnTiep;
    private javax.swing.JComboBox cbxDuong;
    private javax.swing.JComboBox cbxMaKH;
    private javax.swing.JComboBox cbxPhuong;
    private javax.swing.JComboBox cbxQuan;
    private javax.swing.JComboBox cbxThanhPho;
    private javax.swing.JCheckBox chbxKhachHangCu;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JTextField tbxDiaChiCTKH;
    private javax.swing.JTextField tbxEmail;
    private javax.swing.JTextField tbxHoTen;
    private javax.swing.JTextField tbxSoDT;
    // End of variables declaration//GEN-END:variables
    private RSCall.KhachHangRSc dbKhachHang;
    private RSCall.ThanhPhoRSc dbThanhPho;
    private RSCall.QuanRSc dbQuan;
    private RSCall.PhuongRSc dbPhuong;
    private RSCall.DuongRSc dbDuong;
    private Entities.KHACHHANG kh;
    private ArrayList<String> listThanhPho;
    private ArrayList<String> listQuan;
    private ArrayList<String> listPhuong;
    private ArrayList<String> listDuong;
    
    private void Creation(){
        try {
            dbKhachHang = new KhachHangRSc();
            dbThanhPho = new ThanhPhoRSc();
            dbQuan = new QuanRSc();
            dbPhuong = new PhuongRSc();
            dbDuong = new DuongRSc();
                    
            kh = new KHACHHANG();
            listThanhPho = new ArrayList<String> ();
            listQuan = new ArrayList<String> ();
            listPhuong = new ArrayList<String> ();
            listDuong = new ArrayList<String> ();
            
            // Chưa check vào ô là khách hàng cũ thì không được sử dụng Checkbox Mã KH
            cbxMaKH.setEnabled(false);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(rootPane, ex.toString());
            Logger.getLogger(frmDangKyKH.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    private void LoadData(){
        for(THANHPHO t : dbThanhPho.mainList){
            cbxThanhPho.addItem(t);
            listThanhPho.add(t.getMaThanhPho());
        }
        for(QUAN q : dbQuan.mainList){
            cbxQuan.addItem(q);
            listQuan.add(q.getMaQuan());
        }
        
        for(PHUONG p : dbPhuong.mainList){
            cbxPhuong.addItem(p);
            listPhuong.add(p.getMaPhuong());
        }
        for(DUONG d : dbDuong.mainList){
            cbxDuong.addItem(d);
            listDuong.add(d.getMaDuong());
        }
        
        cbxMaKH.removeAllItems();
        if (dbKhachHang.mainList.size() > 0) {
            cbxMaKH.setEnabled(chbxKhachHangCu.isSelected());
            cbxMaKH.addItemListener(new ItemListener() {
                @Override
                public void itemStateChanged(ItemEvent e) {
                    String maKH = cbxMaKH.getItemAt(cbxMaKH.getSelectedIndex()).toString();
                    KHACHHANG k = new KHACHHANG();
                    try{
                        k = dbKhachHang.GetRowByID(maKH);
                        tbxHoTen.setText(k.getHoTen().trim());
                        
                        cbxThanhPho.setSelectedIndex(listThanhPho.indexOf(k.getDiaChiKH().getMaThanhPho()));
                        cbxQuan.setSelectedIndex(listQuan.indexOf(k.getDiaChiKH().getMaQuan()));
                        cbxPhuong.setSelectedIndex(listPhuong.indexOf(k.getDiaChiKH().getMaPhuong())) ;
                        cbxDuong.setSelectedIndex(listDuong.indexOf(k.getDiaChiKH().getMaDuong()));
                        
                        tbxDiaChiCTKH.setText(k.getDiaChiKH().getDiaChiCT().trim());
                        tbxSoDT.setText(k.getSoDT().trim());
                        tbxEmail.setText(k.getEmail().trim());
                    }catch(Exception ev){
                        JOptionPane.showMessageDialog(rootPane, ev.toString());
                    }
                }
            });
            for(KHACHHANG k : dbKhachHang.mainList){
                cbxMaKH.addItem(k.getMaKH());
            }
        }else cbxMaKH.setEnabled(false);
    }
}