/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * frmMain.java
 *
 * Created on May 6, 2011, 9:25:29 PM
 */

package Presentation;

import Utilities.Utilities;
import diaoc.QuanLyBaoBieu;
import diaoc.QuanLyDuLieu;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Nixforest
 */
public class frmMain extends javax.swing.JFrame {

    public static final String ChuaBan = "Chưa bán";
    public static String resource = "";
    
    /** Creates new form frmMain */
    public frmMain() {
        try {
            FileReader fr = new FileReader("config.dat");
            BufferedReader input = new BufferedReader(fr);
            input.readLine();
            String ui = input.readLine();
            int n = Integer.parseInt(ui);
            if (n == 0) {
                resource = "vn";
            }else resource = "en";
        } catch (IOException ex) {
            Logger.getLogger(frmLogin.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try{
            initComponents();
            Creation();
        }catch(HeadlessException e){
            Logger.getLogger(frmLogin.class.getName()).log(Level.SEVERE, null, e);
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

        DesktopPanel = new javax.swing.JDesktopPane(){
            URL url = getClass().getResource("../Images/ui/background.jpg");
            Image img = Toolkit.getDefaultToolkit().getImage(url);

            Image newimage = img.getScaledInstance(1200, 700, Image.SCALE_SMOOTH);

            @Override
            protected void paintComponent(Graphics g)
            {
                super.paintComponent(g);
                g.drawImage(newimage, 0, 0, this.getWidth(), this.getHeight(), this);
            }
        };
        btnBaoBieu = new javax.swing.JButton();
        btnChupAnh = new javax.swing.JButton();
        btnQuanLyDL = new javax.swing.JButton();
        btnDangKy = new javax.swing.JButton();
        btnDiaOc = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        mIDiaOc = new javax.swing.JMenu();
        mIDangKy = new javax.swing.JMenuItem();
        miDiaOcDangBan = new javax.swing.JMenuItem();
        mIBaoBieu = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        mIKhachHang = new javax.swing.JMenuItem();
        mILoaiDiaOc = new javax.swing.JMenuItem();
        mILoaiBao = new javax.swing.JMenuItem();
        mILoaiBang = new javax.swing.JMenuItem();
        mIQCBao = new javax.swing.JMenuItem();
        mIQCBang = new javax.swing.JMenuItem();
        mIToBuom = new javax.swing.JMenuItem();
        mIQuyDinh = new javax.swing.JMenuItem();
        mILogin = new javax.swing.JMenuItem();
        jMenu3 = new javax.swing.JMenu();
        mITuyChinh = new javax.swing.JMenuItem();
        jMenu1 = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle(java.util.ResourceBundle.getBundle("Presentation/" + frmMain.resource).getString("titlefrmMain"));
        setPreferredSize(new java.awt.Dimension(1300, 700));
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        DesktopPanel.setBackground(new java.awt.Color(255, 255, 255));
        DesktopPanel.setPreferredSize(new java.awt.Dimension(1200, 700));

        btnBaoBieu.setText(java.util.ResourceBundle.getBundle("Presentation/" + frmMain.resource).getString("baobieu"));
        btnBaoBieu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBaoBieuActionPerformed(evt);
            }
        });
        DesktopPanel.add(btnBaoBieu);
        btnBaoBieu.setBounds(20, 350, 133, 74);

        btnChupAnh.setText(java.util.ResourceBundle.getBundle("Presentation/" + frmMain.resource).getString("chupanh"));
        btnChupAnh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnChupAnhActionPerformed(evt);
            }
        });
        DesktopPanel.add(btnChupAnh);
        btnChupAnh.setBounds(20, 270, 133, 69);

        btnQuanLyDL.setText(java.util.ResourceBundle.getBundle("Presentation/" + frmMain.resource).getString("quanlydulieu"));
        btnQuanLyDL.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnQuanLyDLActionPerformed(evt);
            }
        });
        DesktopPanel.add(btnQuanLyDL);
        btnQuanLyDL.setBounds(20, 190, 133, 68);

        btnDangKy.setText(java.util.ResourceBundle.getBundle("Presentation/" + frmMain.resource).getString("dangky"));
        btnDangKy.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDangKyActionPerformed(evt);
            }
        });
        DesktopPanel.add(btnDangKy);
        btnDangKy.setBounds(20, 110, 133, 68);

        btnDiaOc.setText(java.util.ResourceBundle.getBundle("Presentation/" + frmMain.resource).getString("diaoc"));
        btnDiaOc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDiaOcActionPerformed(evt);
            }
        });
        DesktopPanel.add(btnDiaOc);
        btnDiaOc.setBounds(20, 30, 133, 68);

        mIDiaOc.setText(java.util.ResourceBundle.getBundle("Presentation/" + frmMain.resource).getString("dangky"));

        mIDangKy.setText(java.util.ResourceBundle.getBundle("Presentation/" + frmMain.resource).getString("dangky"));
        mIDangKy.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mIDangKyActionPerformed(evt);
            }
        });
        mIDiaOc.add(mIDangKy);

        miDiaOcDangBan.setText(java.util.ResourceBundle.getBundle("Presentation/" + frmMain.resource).getString("diaoc"));
        miDiaOcDangBan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miDiaOcDangBanActionPerformed(evt);
            }
        });
        mIDiaOc.add(miDiaOcDangBan);

        mIBaoBieu.setText(java.util.ResourceBundle.getBundle("Presentation/" + frmMain.resource).getString("baobieu"));
        mIBaoBieu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mIBaoBieuActionPerformed(evt);
            }
        });
        mIDiaOc.add(mIBaoBieu);

        jMenuBar1.add(mIDiaOc);

        jMenu2.setText(java.util.ResourceBundle.getBundle("Presentation/" + frmMain.resource).getString("quanlydulieu"));

        mIKhachHang.setText(java.util.ResourceBundle.getBundle("Presentation/" + frmMain.resource).getString("customer"));
        mIKhachHang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mIKhachHangActionPerformed(evt);
            }
        });
        jMenu2.add(mIKhachHang);

        mILoaiDiaOc.setText(java.util.ResourceBundle.getBundle("Presentation/" + frmMain.resource).getString("property type"));
        mILoaiDiaOc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mILoaiDiaOcActionPerformed(evt);
            }
        });
        jMenu2.add(mILoaiDiaOc);

        mILoaiBao.setText(java.util.ResourceBundle.getBundle("Presentation/" + frmMain.resource).getString("newspaper name"));
        mILoaiBao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mILoaiBaoActionPerformed(evt);
            }
        });
        jMenu2.add(mILoaiBao);

        mILoaiBang.setText(java.util.ResourceBundle.getBundle("Presentation/" + frmMain.resource).getString("board"));
        mILoaiBang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mILoaiBangActionPerformed(evt);
            }
        });
        jMenu2.add(mILoaiBang);

        mIQCBao.setText(java.util.ResourceBundle.getBundle("Presentation/" + frmMain.resource).getString("adnewspaper"));
        mIQCBao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mIQCBaoActionPerformed(evt);
            }
        });
        jMenu2.add(mIQCBao);

        mIQCBang.setText(java.util.ResourceBundle.getBundle("Presentation/" + frmMain.resource).getString("adboard"));
        mIQCBang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mIQCBangActionPerformed(evt);
            }
        });
        jMenu2.add(mIQCBang);

        mIToBuom.setText(java.util.ResourceBundle.getBundle("Presentation/" + frmMain.resource).getString("handbill"));
        mIToBuom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mIToBuomActionPerformed(evt);
            }
        });
        jMenu2.add(mIToBuom);

        mIQuyDinh.setText(java.util.ResourceBundle.getBundle("Presentation/" + frmMain.resource).getString("quydinh"));
        mIQuyDinh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mIQuyDinhActionPerformed(evt);
            }
        });
        jMenu2.add(mIQuyDinh);

        mILogin.setText(java.util.ResourceBundle.getBundle("Presentation/" + frmMain.resource).getString("quanlytaikhoan"));
        mILogin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mILoginActionPerformed(evt);
            }
        });
        jMenu2.add(mILogin);

        jMenuBar1.add(jMenu2);

        jMenu3.setText(java.util.ResourceBundle.getBundle("Presentation/" + frmMain.resource).getString("option"));

        mITuyChinh.setText(java.util.ResourceBundle.getBundle("Presentation/" + frmMain.resource).getString("option"));
        mITuyChinh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mITuyChinhActionPerformed(evt);
            }
        });
        jMenu3.add(mITuyChinh);

        jMenuBar1.add(jMenu3);

        jMenu1.setText("About");
        jMenuBar1.add(jMenu1);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(DesktopPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 1234, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(DesktopPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 712, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // Show Địa ốc đang bán
    private void miDiaOcDangBanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miDiaOcDangBanActionPerformed
        frmDiaOc frm = new frmDiaOc();
        frm.setClosable(true);
        Utilities.setFontForAll(frm, new Font("Calibri", 0, 14));
        frm.setLocation(155, 0);
        frm.show();
        this.DesktopPanel.add(frm,0);
    }//GEN-LAST:event_miDiaOcDangBanActionPerformed

    // Show đăng ký
    private void mIDangKyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mIDangKyActionPerformed
        frmDangKyKH frm = new frmDangKyKH();
        frm.setClosable(true);
        Utilities.setFontForAll(frm, new Font("Calibri", 0, 14));
        frm.setLocation(155, 0);
        frm.show();
        this.DesktopPanel.add(frm,0);
    }//GEN-LAST:event_mIDangKyActionPerformed

    private void mIBaoBieuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mIBaoBieuActionPerformed
        QuanLyBaoBieu frm = new QuanLyBaoBieu();
        frm.setClosable(true);
        Utilities.setFontForAll(frm, new Font("Calibri", 0, 14));
        frm.setLocation(155, 0);
        frm.show();
        this.DesktopPanel.add(frm,0);
    }//GEN-LAST:event_mIBaoBieuActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        myMain = new frmMainContent();
        Utilities.setFontForAll(myMain, new Font("Calibri", 0, 14));
        myMain.setLocation(155, 0);
        myMain.show();
        this.DesktopPanel.add(myMain,0);
        
    }//GEN-LAST:event_formWindowOpened

    private void mIKhachHangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mIKhachHangActionPerformed
        frmKhachHang frm = new frmKhachHang();
        frm.setClosable(true);
        Utilities.setFontForAll(frm, new Font("Calibri", 0, 14));
        frm.setLocation(155, 0);
        frm.show();
        this.DesktopPanel.add(frm,0);
    }//GEN-LAST:event_mIKhachHangActionPerformed

    private void mILoaiBaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mILoaiBaoActionPerformed
        frmBao frm = new frmBao();
        frm.setClosable(true);
        Utilities.setFontForAll(frm, new Font("Calibri", 0, 14));
        frm.setLocation(155, 0);
        frm.show();
        this.DesktopPanel.add(frm,0);
    }//GEN-LAST:event_mILoaiBaoActionPerformed

    private void mILoaiBangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mILoaiBangActionPerformed
        frmLoaiBang frm = new frmLoaiBang();
        frm.setClosable(true);
        Utilities.setFontForAll(frm, new Font("Calibri", 0, 14));
        frm.setLocation(155, 0);
        frm.show();
        this.DesktopPanel.add(frm,0);
    }//GEN-LAST:event_mILoaiBangActionPerformed

    private void mIQCBaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mIQCBaoActionPerformed
        frmGiaTienBao frm = new frmGiaTienBao();
        frm.setClosable(true);
        Utilities.setFontForAll(frm, new Font("Calibri", 0, 14));
        frm.setLocation(155, 0);
        frm.show();
        this.DesktopPanel.add(frm,0);
    }//GEN-LAST:event_mIQCBaoActionPerformed

    private void mIQCBangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mIQCBangActionPerformed
        frmGiaTienBang frm = new frmGiaTienBang();
        frm.setClosable(true);
        Utilities.setFontForAll(frm, new Font("Calibri", 0, 14));
        frm.setLocation(155, 0);
        frm.show();
        this.DesktopPanel.add(frm,0);
    }//GEN-LAST:event_mIQCBangActionPerformed

    private void mILoaiDiaOcActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mILoaiDiaOcActionPerformed
        frmAddEditLoaiDiaOc frm = new frmAddEditLoaiDiaOc();
        frm.setClosable(true);
        Utilities.setFontForAll(frm, new Font("Calibri", 0, 14));
        frm.setLocation(155, 0);
        frm.show();
        this.DesktopPanel.add(frm,0);
    }//GEN-LAST:event_mILoaiDiaOcActionPerformed

    private void btnChupAnhActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnChupAnhActionPerformed
        frmThongBaoChupAnh f = new frmThongBaoChupAnh();
        Utilities.setFontForAll(f, new Font("Calibri", 0, 14));
        f.setLocation(155, 0);
        f.show();
        this.DesktopPanel.add(f, 0);
}//GEN-LAST:event_btnChupAnhActionPerformed

    private void btnBaoBieuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBaoBieuActionPerformed
        QuanLyBaoBieu f = new QuanLyBaoBieu();
        Utilities.setFontForAll(f, new Font("Calibri", 0, 14));
        f.setLocation(155, 0);
        f.show();
        this.DesktopPanel.add(f, 0);
}//GEN-LAST:event_btnBaoBieuActionPerformed

    private void btnQuanLyDLActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnQuanLyDLActionPerformed
        QuanLyDuLieu f = new QuanLyDuLieu();
        Utilities.setFontForAll(f, new Font("Calibri", 0, 14));
        f.setLocation(155, 0);
        f.show();
        this.DesktopPanel.add(f, 0);
}//GEN-LAST:event_btnQuanLyDLActionPerformed

    private void btnDangKyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDangKyActionPerformed
        frmDangKyKH f = new frmDangKyKH();
        Utilities.setFontForAll(f, new Font("Calibri", 0, 14));
        f.setClosable(true);
        f.setLocation(155, 0);
        f.show();
        this.DesktopPanel.add(f, 0);
        
}//GEN-LAST:event_btnDangKyActionPerformed

    private void mIToBuomActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mIToBuomActionPerformed
         // TODO add your handling code here:
        frmGiaTienToBuom frm = new frmGiaTienToBuom();
        frm.setClosable(true);
        Utilities.setFontForAll(frm, new Font("Calibri", 0, 14));
        frm.setLocation(155, 0);
        frm.show();
        this.DesktopPanel.add(frm,0);
    }//GEN-LAST:event_mIToBuomActionPerformed

    private void mIQuyDinhActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mIQuyDinhActionPerformed
        frmThamSo frm = new frmThamSo();        
        frm.setClosable(true);
        Utilities.setFontForAll(frm, new Font("Calibri", 0, 14));
        frm.setLocation(155, 0);
        frm.show();
        this.DesktopPanel.add(frm,0);
    }//GEN-LAST:event_mIQuyDinhActionPerformed

    private void mILoginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mILoginActionPerformed

        frmQuanLyDangNhap frm = new frmQuanLyDangNhap();
        frm.setClosable(true);
        Utilities.setFontForAll(frm, new Font("Calibri", 0, 14));
        frm.setLocation(155, 0);
        frm.show();
        this.DesktopPanel.add(frm,0);
    }//GEN-LAST:event_mILoginActionPerformed

    private void btnDiaOcActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDiaOcActionPerformed
        if (!myMain.isShowing()) {
            Utilities.setFontForAll(myMain, new Font("Calibri", 0, 14));
            myMain.show();
            myMain.setVisible(true);
            
            this.DesktopPanel.add(myMain,0);
        }
    }//GEN-LAST:event_btnDiaOcActionPerformed

    private void mITuyChinhActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mITuyChinhActionPerformed
        Option frm = new Option();
        frm.setClosable(true);
        Utilities.setFontForAll(frm, new Font("Calibri", 0, 14));
        frm.setLocation(155, 0);
        frm.show();
        this.DesktopPanel.add(frm,0);
    }//GEN-LAST:event_mITuyChinhActionPerformed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new frmMain().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JDesktopPane DesktopPanel;
    private javax.swing.JButton btnBaoBieu;
    private javax.swing.JButton btnChupAnh;
    private javax.swing.JButton btnDangKy;
    private javax.swing.JButton btnDiaOc;
    private javax.swing.JButton btnQuanLyDL;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem mIBaoBieu;
    private javax.swing.JMenuItem mIDangKy;
    private javax.swing.JMenu mIDiaOc;
    private javax.swing.JMenuItem mIKhachHang;
    private javax.swing.JMenuItem mILoaiBang;
    private javax.swing.JMenuItem mILoaiBao;
    private javax.swing.JMenuItem mILoaiDiaOc;
    private javax.swing.JMenuItem mILogin;
    private javax.swing.JMenuItem mIQCBang;
    private javax.swing.JMenuItem mIQCBao;
    private javax.swing.JMenuItem mIQuyDinh;
    private javax.swing.JMenuItem mIToBuom;
    private javax.swing.JMenuItem mITuyChinh;
    private javax.swing.JMenuItem miDiaOcDangBan;
    // End of variables declaration//GEN-END:variables

    private frmMainContent myMain;
    GridBagLayout gb = new GridBagLayout();
    GridBagConstraints gbc = new GridBagConstraints();
    
    private void Creation(){
        try{
            myMain = new frmMainContent();
        }catch(Exception e){
            JOptionPane.showMessageDialog(rootPane, e.toString());
            
            Logger.getLogger(frmMain.class.getName()).log(Level.SEVERE, null, e);
        }
    }
}

