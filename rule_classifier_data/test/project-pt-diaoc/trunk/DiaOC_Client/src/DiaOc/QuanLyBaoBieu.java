/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package diaoc;

import Presentation.*;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

/**
 *
 * @author Nixforest
 */
public class QuanLyBaoBieu extends JInternalFrame
{
    private JTabbedPane tabPanel = new JTabbedPane(JTabbedPane.TOP,JTabbedPane.SCROLL_TAB_LAYOUT);

    private JPanel pnl;

    public QuanLyBaoBieu()
    {
        super(java.util.ResourceBundle.getBundle("Presentation/" + frmMain.resource).getString("report"));
        this.setClosable(true);
        //setDefaultCloseOperation(JInternalFrame.EXIT_ON_CLOSE);
        int tabNumber = -1;

        // Khách hàng
        pnl = new JPanel();
        pnl.add(new JLabel(java.util.ResourceBundle.getBundle("Presentation/" + frmMain.resource).getString("customer")));
        pnl.setOpaque(false);
        JInternalFrame frm = new frmThongKeKhachHangTheoThang();        
        ((javax.swing.plaf.basic.BasicInternalFrameUI) frm.getUI()).setNorthPane(null);
        tabPanel.addTab("KhachHang", frm);
        tabPanel.setTabComponentAt(++tabNumber, pnl);
        
        // Loại Địa Ốc
        pnl = new JPanel();
        pnl.add(new JLabel(java.util.ResourceBundle.getBundle("Presentation/" + frmMain.resource).getString("diaoc")));
        pnl.setOpaque(false);
        tabPanel.addTab(java.util.ResourceBundle.getBundle("Presentation/" + frmMain.resource).getString("property type"),new frmThongKeDiaOc());
        tabPanel.setTabComponentAt(1,pnl);
        
        // Địa ốc
        pnl = new JPanel();
        pnl.add(new JLabel(java.util.ResourceBundle.getBundle("Presentation/" + frmMain.resource).getString("Service")));
        pnl.setOpaque(false);
        tabPanel.addTab("DiaOc", new frmThongKeSoLuongKhachHang());
        tabPanel.setTabComponentAt(2, pnl);
        
        // Loại Báo
        pnl = new JPanel();
        pnl.add(new JLabel(java.util.ResourceBundle.getBundle("Presentation/" + frmMain.resource).getString("sale")));
        pnl.setOpaque(false);
        tabPanel.addTab("LoaiBao", new frmThongKeDoanhThu());
        tabPanel.setTabComponentAt(3, pnl);
        
        getContentPane().add(tabPanel,null);
        setSize(999,650);
        setVisible(true);
    }
}