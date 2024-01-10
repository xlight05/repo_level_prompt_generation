/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package diaoc;

import Presentation.frmAddEditLoaiDiaOc;
import Presentation.frmBao;
import Presentation.frmGiaTienBang;
import Presentation.frmGiaTienBao;
import Presentation.frmGiaTienToBuom;
import Presentation.frmKhachHang;
import Presentation.frmLoaiBang;
import Presentation.frmMain;
import Presentation.frmQuanLyDangNhap;
import Presentation.frmThamSo;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTabbedPane;

/**
 *
 * @author Nixforest
 */
public class QuanLyDuLieu extends JInternalFrame
{
    private JTabbedPane tabPanel = new JTabbedPane(JTabbedPane.TOP,JTabbedPane.SCROLL_TAB_LAYOUT);
//    private CrossCloseButton button1 = new CrossCloseButton();
//    private RoundCloseButton button2 = new RoundCloseButton();

    private JPanel pnl;
    private String[] items = {"Item 1","Item 2","Item 3","Item 4","Item 5"};
    private JComboBox combobox = new JComboBox(items);
    private JRadioButton radioButton = new JRadioButton("Radio Button");
    private JCheckBox checkBox = new JCheckBox("Check Box");

    public QuanLyDuLieu()
    {
        
        super(java.util.ResourceBundle.getBundle("Presentation/" + frmMain.resource).getString("quanlydulieu"));
        this.setClosable(true);
        //setDefaultCloseOperation(JInternalFrame.EXIT_ON_CLOSE);
        int tabNumber = -1;

        // Khách hàng
        pnl = new JPanel();
        pnl.add(new JLabel(java.util.ResourceBundle.getBundle("Presentation/" + frmMain.resource).getString("customer")));
        pnl.setOpaque(false);
        frmKhachHang kh = new frmKhachHang();
        ((javax.swing.plaf.basic.BasicInternalFrameUI) kh.getUI()).setNorthPane(null);
        tabPanel.addTab("KhachHang", kh);
        tabPanel.setTabComponentAt(++tabNumber,pnl);
        
        // Loại Địa Ốc
        pnl = new JPanel();
        pnl.add(new JLabel(java.util.ResourceBundle.getBundle("Presentation/" + frmMain.resource).getString("property type")));
        pnl.setOpaque(false);
        frmAddEditLoaiDiaOc loaiDiaOc = new frmAddEditLoaiDiaOc();
        ((javax.swing.plaf.basic.BasicInternalFrameUI) loaiDiaOc.getUI()).setNorthPane(null);
        tabPanel.addTab("LoaiDiaOc", loaiDiaOc);
        tabPanel.setTabComponentAt(++tabNumber,pnl);
        
        // Địa ốc
//        pnl = new JPanel();
//        pnl.add(new JLabel("Địa Ốc"));
//        pnl.setOpaque(false);
//        tabPanel.addTab("DiaOc", new frmDiaOc());
//        tabPanel.setTabComponentAt(2, pnl);
        
        // Loại Báo
        pnl = new JPanel();
        pnl.add(new JLabel(java.util.ResourceBundle.getBundle("Presentation/" + frmMain.resource).getString("newspaper name")));
        pnl.setOpaque(false);
        frmBao bao = new frmBao();
        ((javax.swing.plaf.basic.BasicInternalFrameUI) bao.getUI()).setNorthPane(null);
        tabPanel.addTab("LoaiBao", bao);
        tabPanel.setTabComponentAt(++tabNumber, pnl);
        
        // Giá Tiền quảng cáo Báo
        pnl = new JPanel();
        pnl.add(new JLabel(java.util.ResourceBundle.getBundle("Presentation/" + frmMain.resource).getString("adnewspaper")));
        pnl.setOpaque(false);
        frmGiaTienBao gtBao = new frmGiaTienBao();
        ((javax.swing.plaf.basic.BasicInternalFrameUI) gtBao.getUI()).setNorthPane(null);
        tabPanel.addTab("QCBao", gtBao);
        tabPanel.setTabComponentAt(++tabNumber, pnl);
        
        // Loại Bảng
        pnl = new JPanel();
        pnl.add(new JLabel(java.util.ResourceBundle.getBundle("Presentation/" + frmMain.resource).getString("board")));
        pnl.setOpaque(false);
        frmLoaiBang bang = new frmLoaiBang();
        ((javax.swing.plaf.basic.BasicInternalFrameUI) bang.getUI()).setNorthPane(null);
        tabPanel.addTab("LoaiBang", bang);
        tabPanel.setTabComponentAt(++tabNumber, pnl);
        
        // Giá Tiền quảng cáo bảng
        pnl = new JPanel();
        pnl.add(new JLabel(java.util.ResourceBundle.getBundle("Presentation/" + frmMain.resource).getString("adboard")));
        pnl.setOpaque(false);
        frmGiaTienBang gtBang = new frmGiaTienBang();
        ((javax.swing.plaf.basic.BasicInternalFrameUI) gtBang.getUI()).setNorthPane(null);
        tabPanel.addTab("QCBang", gtBang);
        tabPanel.setTabComponentAt(++tabNumber, pnl);
        
        // Giá Tiền Tờ bướm
        pnl = new JPanel();
        pnl.add(new JLabel(java.util.ResourceBundle.getBundle("Presentation/" + frmMain.resource).getString("handbill")));
        pnl.setOpaque(false);
        frmGiaTienToBuom gtToBuom = new frmGiaTienToBuom();
        ((javax.swing.plaf.basic.BasicInternalFrameUI) gtToBuom.getUI()).setNorthPane(null);
        tabPanel.addTab("ToBuom", gtToBuom);
        tabPanel.setTabComponentAt(++tabNumber, pnl);
        
        
        // Tham số
        pnl = new JPanel();
        pnl.add(new JLabel(java.util.ResourceBundle.getBundle("Presentation/" + frmMain.resource).getString("quydinh")));
        pnl.setOpaque(false);
        frmThamSo thamso = new frmThamSo();
        ((javax.swing.plaf.basic.BasicInternalFrameUI) thamso.getUI()).setNorthPane(null);
        tabPanel.addTab("ThamSo", thamso);
        tabPanel.setTabComponentAt(++tabNumber, pnl);
        
        // Đăng Nhập
        pnl = new JPanel();
        pnl.add(new JLabel(java.util.ResourceBundle.getBundle("Presentation/" + frmMain.resource).getString("quanlytaikhoan")));
        pnl.setOpaque(false);
        frmQuanLyDangNhap dn = new frmQuanLyDangNhap();
        ((javax.swing.plaf.basic.BasicInternalFrameUI) dn.getUI()).setNorthPane(null);
        tabPanel.addTab("DangNhap", dn);
        tabPanel.setTabComponentAt(++tabNumber, pnl);
        
        getContentPane().add(tabPanel,null);
        setSize(999,650);
        setVisible(true);
    }

//    public void actionPerformed(ActionEvent e)
//    {
//        Object source = e.getSource();
//        if (source==button1){
//            tabPanel.removeTabAt(tabPanel.indexOfTabComponent( panel1));
//        }
//        else if (source==button2)
//        {
//            tabPanel.removeTabAt(tabPanel.indexOfTabComponent( panel2));
//        }
//    }
}

//class CrossCloseButton extends JButton
//{
//    private final int size = 10;
//    private final int border = 2;
//
//    public CrossCloseButton()
//    {
//        super();
//        setContentAreaFilled(false);
//        setFocusable(false);
//        setBorderPainted(false);
//        setToolTipText("Click here to close this tab");
//    }
//
//    public void paintComponent(Graphics g)
//    {
//        super.paintComponent(g);
//        g.setColor(Color.BLUE);
//        g.drawLine(border, border, getWidth() - border - 1, getHeight() - border - 1);
//        g.drawLine(getWidth() - border - 1, border, border, getHeight() - border - 1);
//    }
//
//    public Dimension getPreferredSize()
//    {
//        return new Dimension(size,size);
//    }
//}
//
//class RoundCloseButton extends JButton
//{
//    private int size = 10;
//    private int diameter = 9;
//
//    public RoundCloseButton()
//    {
//        super();
//        setContentAreaFilled(false);
//        setFocusable(false);
//        setBorderPainted(false);
//        setToolTipText("Click here to close this tab");
//    }
//
//    public void paintComponent(Graphics g)
//    {
//        super.paintComponent(g);
//        g.setColor(Color.RED);
//        g.drawOval(0,0,diameter,diameter);
//    }
//
//    public Dimension getPreferredSize()
//    {
//        return new Dimension(size,size);
//    }
//} 
