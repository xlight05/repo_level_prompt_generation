package swin.metrictool;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;

import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

class OperationHandler implements ListSelectionListener, ActionListener, Runnable
{

	JFrame newFrame;

	JTextField txtProjectName;

	JTextField txtDescription;

	JTextField txtDate;

	JButton BttnSaveAdded;

	// Vector<Project> v = new Vector<Project>(10, 3);

	int i = 0, k = 0;

	Toolkit kit = Toolkit.getDefaultToolkit();

	Dimension screenSize = this.kit.getScreenSize();

	int screenHeight = this.screenSize.height;

	int screenWidth = this.screenSize.width;

	Image img = this.kit.getImage("images/icon.JPG");

	FileReader fis;

	ObjectInputStream ois;

	JList list;

	DefaultListModel listModel;

	ListSelectionModel listSelectionModel;

	JRadioButton byfname, bylname;

	Thread t;

	Project project;

	ProjectList projectList;

	JTable searchTable;

	JTextField txtSearch;

	String columnNames[] = {"Project Name", "Date", "Description"};

	Object data[][] = new Object[5][8];
	public static String DIRTECTORY = "data\\data.dat";

	OperationHandler()
	{
		// ...checks on aFile are elided
		// StringBuffer contents = new StringBuffer();

		if(!FileSystemUtilities.exists("data"))
		{
			FileSystemUtilities.makeDirectory("data");
		}

		StringBuffer projectListBuffer = null;
		if((projectListBuffer =FileSystemUtilities.readFile(DIRTECTORY)) != null){
			projectList = XStreamUtility.getProjectList(projectListBuffer.toString());
		}
		

		// 
	}

	public void run()
	{
		Project proj = new Project();
		if (projectList == null) {
			projectList  = new ProjectList();
		}
			if (projectList.getProjects().contains(txtProjectName.getText())) {
				JOptionPane.showMessageDialog(this.newFrame,
						"Project already exists: Project already exists.",
						"Project already exists", JOptionPane.ERROR_MESSAGE);
			} else {
				proj.setDetails(txtProjectName.getText(), txtDescription
						.getText(), txtDate.getText());
				projectList.getProjects().put(txtProjectName.getText(), proj);
				boolean made = FileSystemUtilities.makeDirectory("data\\" + txtProjectName
						.getText());
				System.out.println("data\\" + txtProjectName
						.getText());
				if (!made) {
					JOptionPane
							.showMessageDialog(
									this.newFrame,
									"Error Making Directory: Could Not Making Directory.",
									"Error Making Directory",
									JOptionPane.ERROR_MESSAGE);
				}
			}
			
	}
	
	public void close()
	{
		StringBuffer xml = new StringBuffer().append(XStreamUtility.getXml(projectList));
		try {
			FileSystemUtilities.writeFile(DIRTECTORY, xml);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
	}

	public void AddNew()
	{
		this.newFrame = new JFrame("Add New");
		this.newFrame.setSize(220, 250);
		this.newFrame.setResizable(false);
		this.newFrame.setIconImage(this.img);

		JLabel lblFirstName = new JLabel("Project Name: ");
		JLabel lblLastName = new JLabel("Description: ");
		JLabel lblNickname = new JLabel("Date: ");

		this.txtProjectName = new JTextField(10);
		this.txtDescription = new JTextField(10);
		this.txtDate = new JTextField(10);

		JButton BttnAdd = new JButton("Add New!");
		this.BttnSaveAdded = new JButton("Save Added!");

		BttnAdd.addActionListener(this);
		this.BttnSaveAdded.addActionListener(this);
		this.BttnSaveAdded.setEnabled(false);

		JPanel centerPane = new JPanel();
		JPanel bottomPane = new JPanel();

		centerPane.add(lblFirstName);
		centerPane.add(this.txtProjectName);
		centerPane.add(lblLastName);
		centerPane.add(this.txtDescription);
		centerPane.add(lblNickname);
		centerPane.add(this.txtDate);
		// centerPane.add(lblEMail);
		// centerPane.add(txtEMail);
		// centerPane.add(lblAddress);
		// centerPane.add(txtAddress);
		// centerPane.add(lblPhoneNo);
		// centerPane.add(txtPhoneNo);
		// centerPane.add(lblWebpage);
		// centerPane.add(txtWebpage);
		// centerPane.add(lblBDay);
		// centerPane.add(txtBDay);
		bottomPane.add(BttnAdd);
		bottomPane.add(this.BttnSaveAdded);

		centerPane.setLayout(new GridLayout(0, 2));

		this.newFrame.getContentPane().add(centerPane, BorderLayout.CENTER);

		this.newFrame.getContentPane().add(bottomPane, BorderLayout.SOUTH);
		this.newFrame.setLocation(this.screenWidth / 4, this.screenHeight / 4);
		this.newFrame.setVisible(true);

	}

	public void SearchProjects()
	{
		this.newFrame = new JFrame("Search Projects");
		this.newFrame.setSize(700, 220);
		this.newFrame.setLocation(this.screenWidth / 4, this.screenHeight / 4);
		this.newFrame.setIconImage(this.img);
		this.newFrame.setResizable(false);

		JPanel topPane = new JPanel();
		JLabel label1 = new JLabel("Search Projects:");
		topPane.add(label1);

		JPanel centerPane = new JPanel();
		JLabel label2 = new JLabel("Search String:");
		this.txtSearch = new JTextField(20);
		JButton bttnSearch = new JButton("Search!");
		bttnSearch.addActionListener(this);
		JButton bttnCancel = new JButton("Cancel");
		bttnCancel.addActionListener(this);
		centerPane.add(label2);
		centerPane.add(this.txtSearch);
		centerPane.add(bttnSearch);
		centerPane.add(bttnCancel);

		this.searchTable = new JTable(this.data, this.columnNames);
		JScrollPane scrollPane = new JScrollPane(this.searchTable);
		this.searchTable.setPreferredScrollableViewportSize(new Dimension(500, 90));

		this.newFrame.getContentPane().add(scrollPane, BorderLayout.SOUTH);

		this.newFrame.getContentPane().add(topPane, BorderLayout.NORTH);
		this.newFrame.getContentPane().add(centerPane, BorderLayout.CENTER);
		this.newFrame.setVisible(true);
	}

	public void SortProjects()
	{
		this.newFrame = new JFrame("Sort Contacts");
		this.newFrame.setSize(250, 160);
		this.newFrame.setLocation(this.screenWidth / 4, this.screenHeight / 4);
		this.newFrame.setIconImage(this.img);
		this.newFrame.setResizable(false);

		this.byfname = new JRadioButton("By Name");
		this.byfname.setActionCommand("By Name");
		this.byfname.setSelected(true);

		this.bylname = new JRadioButton("By Description");
		this.bylname.setActionCommand("By Description");

		ButtonGroup group = new ButtonGroup();
		group.add(this.byfname);
		group.add(this.bylname);

		JPanel topPane = new JPanel();
		JLabel label = new JLabel("Sort Projects By:");
		topPane.add(label);

		JPanel pane = new JPanel(new GridLayout(0, 1));
		pane.add(this.byfname);
		pane.add(this.bylname);

		JPanel bottomPane = new JPanel();
		JButton sortBttn = new JButton("Sort Projects");
		JButton cancelBttn = new JButton("Cancel");
		bottomPane.add(sortBttn);
		bottomPane.add(cancelBttn);
		sortBttn.addActionListener(this);
		cancelBttn.addActionListener(this);

		this.newFrame.getContentPane().add(topPane, BorderLayout.NORTH);
		this.newFrame.getContentPane().add(pane, BorderLayout.CENTER);
		this.newFrame.getContentPane().add(bottomPane, BorderLayout.SOUTH);

		this.newFrame.setVisible(true);

	}

	public void DeleteProject()
	{
		/*
		 * String fname, lname; newFrame = new JFrame("Delete Project"); newFrame.setSize(300,
		 * 300); newFrame.setLocation(screenWidth / 4, screenHeight / 4);
		 * newFrame.setIconImage(img); // JPanel centerPane = new JPanel(); listModel = new
		 * DefaultListModel(); Project project = new Project(); for (int l = 0; l < v.size();
		 * l++) { project = (Project) v.elementAt(l); fname = project.getShortName(); lname =
		 * project.getDescription(); listModel.addElement(fname + " " + lname); } list = new
		 * JList(listModel); list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		 * listSelectionModel = list.getSelectionModel();
		 * listSelectionModel.addListSelectionListener(this); JScrollPane listScrollPane = new
		 * JScrollPane(list); JPanel topPane = new JPanel(); JLabel label = new JLabel("Current
		 * Projects:"); topPane.add(label); JPanel bottomPane = new JPanel(); JButton
		 * bttnDelete = new JButton("Delete Selected"); bottomPane.add(bttnDelete);
		 * bttnDelete.addActionListener(this); JButton bttnCancel = new JButton("Cancel");
		 * bottomPane.add(bttnCancel); bttnCancel.addActionListener(this);
		 * newFrame.getContentPane().add(topPane, BorderLayout.NORTH);
		 * newFrame.getContentPane().add(listScrollPane, BorderLayout.CENTER);
		 * newFrame.getContentPane().add(bottomPane, BorderLayout.SOUTH);
		 * newFrame.setVisible(true);
		 */

	}

	public void ViewAllProjects()
	{

		/*
		 * newFrame = new JFrame("All Projects In The Address Book"); newFrame.setSize(600,
		 * 300); newFrame.setIconImage(img); Project con = new Project(); // String
		 * columnNames[] = // { "First Name", "Last Name", "Nickname", "E Mail Address",
		 * "Address", // "Phone No.", "Webpage", "B'day" }; Object data[][] = new
		 * Object[v.size()][8]; for (int j = 0; j < v.size(); j++) { con = (Project)
		 * v.elementAt(k); data[j][0] = con.getShortName(); data[j][1] = con.getDescription();
		 * data[j][2] = con.getCreationDate(); k++; } k = 0; JTable abtable = new JTable(data,
		 * columnNames); JScrollPane scrollPane = new JScrollPane(abtable);
		 * abtable.setPreferredScrollableViewportSize(new Dimension(500, 370)); JPanel pane =
		 * new JPanel(); JLabel label = new JLabel("Projects Currently In The Address Book");
		 * pane.add(label); newFrame.getContentPane().add(pane, BorderLayout.SOUTH);
		 * newFrame.getContentPane().add(scrollPane, BorderLayout.CENTER);
		 * newFrame.setLocation(screenWidth / 4, screenHeight / 4); newFrame.setVisible(true);
		 */

	}

	public void showHelp() throws IOException
	{
		String title = "Help Contents";
		String data = "";
		FileInputStream fishelp = null;
		int i;

		this.newFrame = new JFrame(title);
		this.newFrame.setSize(401, 400);
		this.newFrame.setResizable(false);
		this.newFrame.setLocation(this.screenWidth / 4, this.screenHeight / 4);
		this.newFrame.setIconImage(this.img);

		JTextArea textArea = new JTextArea(5, 20);
		textArea.setLineWrap(true);
		textArea.setEditable(false);

		try
		{
			fishelp = new FileInputStream("help/help.txt");
		}
		catch (Exception e)
		{
			JOptionPane.showMessageDialog(this.newFrame, "Help File Not Found.",
					"Help File Not Found", JOptionPane.INFORMATION_MESSAGE);
		}

		do
		{
			i = fishelp.read();
			if (i != 1) data = data + (char) i;
		} while (i != -1);

		fishelp.close();

		textArea.setText(data);

		JPanel bottomPane = new JPanel();
		JButton button = new JButton("Ok");
		bottomPane.add(button);
		button.addActionListener(this);

		JPanel topPane = new JPanel();
		JLabel label = new JLabel(title);
		topPane.add(label);

		JScrollPane scrollPane = new JScrollPane(textArea);

		this.newFrame.getContentPane().add(topPane, BorderLayout.NORTH);
		this.newFrame.getContentPane().add(scrollPane);

		this.newFrame.getContentPane().add(bottomPane, BorderLayout.SOUTH);

		this.newFrame.setVisible(true);

	}

	public void actionPerformed(ActionEvent ae)
	{

		if (ae.getActionCommand() == "Add New!")
		{

			if (this.txtProjectName.getText().equals("") && this.txtDescription.getText().equals(""))
				JOptionPane.showMessageDialog(this.newFrame,
						"Entries Empty! Fill in the required entries to save Project.",
						"Entries Empty", JOptionPane.INFORMATION_MESSAGE);
			else
			{
				this.project = new Project();
				this.project.setDetails(this.txtProjectName.getText(), this.txtDescription.getText(),
						this.txtDate.getText());
//				this.txtProjectName.setText("");
//				this.txtDescription.setText("");
//				this.txtDate.setText("");
				this.newFrame.setVisible(false);
				this.saveXML();
			}

		}
		else if (ae.getActionCommand() == "Save Added!")
		{
			/*
			 * saveVector(); newFrame.setVisible(false);
			 */

		}
		else if (ae.getActionCommand() == "Ok")
			this.newFrame.setVisible(false);
		else if (ae.getActionCommand() == "Delete Selected")
		{

			/*
			 * int index; try { index = list.getSelectedIndex(); if (index == -1) {
			 * JOptionPane.showMessageDialog(newFrame, "Select a Project first to delete it.",
			 * "Select a Project First", JOptionPane.INFORMATION_MESSAGE); } else { int n =
			 * JOptionPane.showConfirmDialog(newFrame, "Are you sure you want to delete the
			 * selected Project?", "Are you sure?", JOptionPane.YES_NO_OPTION); if (n ==
			 * JOptionPane.YES_OPTION) { listModel.remove(index); v.removeElementAt(index);
			 * saveVector(); newFrame.setVisible(true); } else if (n == JOptionPane.NO_OPTION) { } } }
			 * catch (Exception e) { }
			 */
		}
		else if (ae.getActionCommand() == "Cancel")
			this.newFrame.setVisible(false);
		else if (ae.getActionCommand() == "Search!")
		{
			/*
			 * String SearchStr; SearchStr = txtSearch.getText(); boolean flag = false; Project
			 * con = new Project(); int c = 0; for (int t = 0; t < 5; t++) { data[t][0] = "";
			 * data[t][1] = ""; data[t][2] = ""; data[t][3] = ""; data[t][4] = ""; data[t][5] =
			 * ""; data[t][6] = ""; data[t][7] = ""; } for (int t = 0; t < v.size(); t++) { con =
			 * (Project) v.elementAt(t); if (SearchStr.equalsIgnoreCase(con.getShortName()) ||
			 * SearchStr.equalsIgnoreCase(con.getDescription())) { flag = true; data[c][0] =
			 * con.getShortName(); data[c][1] = con.getDescription(); data[c][2] =
			 * con.getCreationDate(); searchTable = new JTable(data, columnNames);
			 * newFrame.setSize(700, 221); newFrame.setSize(700, 220); if (c < 5) c++; } } if
			 * (flag) { JOptionPane.showMessageDialog(newFrame, "Project Found!", "Search
			 * Result!", JOptionPane.INFORMATION_MESSAGE); } else {
			 * JOptionPane.showMessageDialog(newFrame, "No Such Project Found!", "Search
			 * Result!", JOptionPane.INFORMATION_MESSAGE); }
			 */

		}
		else if (ae.getActionCommand() == "Sort Projects")
		{
			/*
			 * if (byfname.isSelected()) { Project project1 = new Project(); Project project2 =
			 * new Project(); Project temp = new Project(); int l, m; for (l = 0; l < v.size() -
			 * 1; l++) { for (m = l + 1; m < v.size(); m++) { project1 = (Project)
			 * v.elementAt(l); project2 = (Project) v.elementAt(m); if
			 * (project1.getShortName().compareTo(project2.getShortName()) > 0) { temp =
			 * (Project) v.elementAt(m); v.setElementAt(v.elementAt(l), m);
			 * v.setElementAt(temp, l); } } } saveVector();
			 */
		}
		else
		{

			/*
			 * Project project1 = new Project(); Project project2 = new Project(); Project temp =
			 * new Project(); int l, m; for (l = 0; l < v.size() - 1; l++) { for (m = l + 1; m <
			 * v.size(); m++) { project1 = (Project) v.elementAt(l); project2 = (Project)
			 * v.elementAt(m); if
			 * (project1.getDescription().compareTo(project2.getDescription()) > 0) { temp =
			 * (Project) v.elementAt(m); v.setElementAt(v.elementAt(l), m);
			 * v.setElementAt(temp, l); } } } saveVector(); } newFrame.setVisible(false);
			 */
		}

	}

	public void saveXML()
	{
		this.t = new Thread(this, "Save xml Thread");
		this.t.start();
	}

	public void valueChanged(ListSelectionEvent lse)
	{

	}

}
