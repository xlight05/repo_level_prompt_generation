//package swin.metrictool;
//
//import java.awt.BorderLayout;
//import java.awt.Dimension;
//import java.awt.Image;
//import java.awt.Toolkit;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//import java.io.File;
//import java.io.IOException;
//
//import javax.swing.JButton;
//import javax.swing.JFileChooser;
//import javax.swing.JFrame;
//import javax.swing.JLabel;
//import javax.swing.JMenu;
//import javax.swing.JMenuBar;
//import javax.swing.JMenuItem;
//import javax.swing.JOptionPane;
//import javax.swing.JPanel;
//import javax.swing.JScrollPane;
//
//class ProjectCreation implements ActionListener
//{
//
//	JPanel topPanel, bottomPanel;
//
//	JScrollPane scrollPane;
//
//	JFrame frame;
//
//	JMenuBar menubar = new JMenuBar();
//
//	JMenu menu = new JMenu();
//
//	JMenuItem menuItem;
//
//	Toolkit kit = Toolkit.getDefaultToolkit();
//
//	Dimension screenSize = this.kit.getScreenSize();
//
//	int screenHeight = this.screenSize.height;
//
//	int screenWidth = this.screenSize.width;
//
//	Image img = this.kit.getImage("images/icon.JPG");
//
//	public ProjectCreation()
//	{
//		this.frame = new JFrame("Project Creation");
//		this.frame.setSize(600, 200);
//
//		this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		this.frame.setLocation(this.screenWidth / 4, this.screenHeight / 4);
//		this.frame.setIconImage(this.img);
//		this.addWidgets();
//		this.frame.setVisible(true);
//
//	}
//
//	public void addWidgets()
//	{
//		this.menubar.add(this.menu);
//
//		this.menu = new JMenu("Options");
//		this.menuItem = new JMenuItem("New Project...");
//		this.menu.add(this.menuItem);
//		this.menuItem.addActionListener(this);
//
//		this.menuItem = new JMenuItem("Delete Project");
//		this.menu.add(this.menuItem);
//		this.menuItem.addActionListener(this);
//
//		this.menuItem = new JMenuItem("Search Projects");
//		this.menu.add(this.menuItem);
//		this.menuItem.addActionListener(this);
//
//		this.menuItem = new JMenuItem("Sort Projects");
//		this.menu.add(this.menuItem);
//		this.menuItem.addActionListener(this);
//
//		this.menuItem = new JMenuItem("View All Projects");
//		this.menu.add(this.menuItem);
//		this.menuItem.addActionListener(this);
//
//		this.menuItem = new JMenuItem("Backup Projects");
//		this.menu.add(this.menuItem);
//		this.menuItem.addActionListener(this);
//
//		this.menubar.add(this.menu);
//
//		this.menu = new JMenu("Help");
//
//		this.menuItem = new JMenuItem("Help Contents");
//		this.menu.add(this.menuItem);
//		this.menuItem.addActionListener(this);
//
//		this.menuItem = new JMenuItem("About");
//		this.menu.add(this.menuItem);
//		this.menuItem.addActionListener(this);
//
//		this.menubar.add(this.menu);
//
//		this.frame.setJMenuBar(this.menubar);
//
//		JPanel topPanel = new JPanel();
//		JPanel bottomPanel = new JPanel();
//
//		// Add Buttons To Bottom Panel
//		JButton AddNew = new JButton("New Project...");
//		JButton DeleteContact = new JButton("Delete Project");
//		JButton SearchContacts = new JButton("Search  Projects");
//		JButton SortContacts = new JButton("Sort Projects");
//		JButton ViewContactList = new JButton("View Projects");
//
//		JLabel label = new JLabel(
//				"<HTML><FONT FACE = ARIAL SIZE = 2><B>Use The options below and In The Menu To Manage Projects");
//
//		// Add Action Listeners
//		AddNew.addActionListener(this);
//		DeleteContact.addActionListener(this);
//		SearchContacts.addActionListener(this);
//		SortContacts.addActionListener(this);
//		ViewContactList.addActionListener(this);
//
//		topPanel.add(label);
//
//		bottomPanel.add(AddNew);
//		bottomPanel.add(DeleteContact);
//		bottomPanel.add(SearchContacts);
//		bottomPanel.add(SortContacts);
//		bottomPanel.add(ViewContactList);
//
//		this.frame.getContentPane().add(topPanel, BorderLayout.NORTH);
//		this.frame.getContentPane().add(bottomPanel, BorderLayout.SOUTH);
//		this.frame.setResizable(false);
//	}
//
//	public static void main(String args[])
//	{
//		//ProjectCreation projectCreation = new ProjectCreation();
//		//projectCreation.close();
//	}
//	
//	//public void close(){}
//
//	private OperationHandler oh = new OperationHandler();
//
//	public void actionPerformed(ActionEvent ae)
//	{
//		if (ae.getActionCommand() == "New Project...")
//			this.oh.AddNew();
//		else if (ae.getActionCommand() == "Search Projects")
//			this.oh.SearchProjects();
//		else if (ae.getActionCommand() == "Sort Projects")
//			this.oh.SortProjects();
//		else if (ae.getActionCommand() == "Delete Project")
//			this.oh.DeleteProject();
//		else if (ae.getActionCommand() == "View All Projects")
//			this.oh.ViewAllProjects();
//		else if (ae.getActionCommand() == "About")
//			JOptionPane.showMessageDialog(this.frame,
//					"About Project Creation:\nCreated By AgileTeam",
//					"About Project Creation", JOptionPane.INFORMATION_MESSAGE);
//		else if (ae.getActionCommand() == "Help Contents")
//			try
//			{
//				this.oh.showHelp();
//			}
//			catch (IOException e)
//			{
//				e.printStackTrace();
//			}
//		else if (ae.getActionCommand() == "Backup Projects")
//		{
//			JFileChooser chooser = new JFileChooser();
//			chooser.setCurrentDirectory(new File("."));
//			chooser.setMultiSelectionEnabled(false);
//
//			chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
//			chooser.showSaveDialog(this.frame);
//			String filename = null;
//			int p;
//
//			try
//			{
//				filename = chooser.getSelectedFile().getPath();
//			}
//			catch (Exception e)
//			{
//			}
//			//need to back up here
//			
//
//		}
//
//	}
//
//}
