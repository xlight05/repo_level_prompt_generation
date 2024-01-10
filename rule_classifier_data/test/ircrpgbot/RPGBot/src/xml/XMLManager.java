/*
 * @author Kyle Kemp
 */
package xml;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import shared.RestrictedNumber;
import bot.Bot;
import classes.CharacterClass;

import com.sun.org.apache.xerces.internal.dom.DocumentImpl;
import com.sun.org.apache.xml.internal.serialize.OutputFormat;
import com.sun.org.apache.xml.internal.serialize.XMLSerializer;

import entities.Player;

public class XMLManager {

	private static final String PLAYERS_XML = "players.xml";

	Document players;

	public XMLManager() {
		createFilesIfNotExistent();
		loadFiles();
	}

	private void createFile(String s) {
		File f = new File(s);
		if (!f.exists()) {
			try {
				f.createNewFile();
			} catch (IOException e) {
				System.out.println("Unable to write file " + s);
			}
		}
	}

	private void createFilesIfNotExistent() {
		if (players == null) {
			createFile(PLAYERS_XML);
			players = parseXmlFile(PLAYERS_XML);
		}
	}

	public boolean createNewPlayer(String name, String password, String regBy,
			String date) {
		NodeList nodes = players.getElementsByTagName("Player");
		for (int i = 0; i < nodes.getLength(); i++) {
			Node n = nodes.item(i);
			if (n.getAttributes().getNamedItem("name").getNodeValue()
					.equals(name)) {
				return false;
			}
		}

		try {
			Document d = players;
			Element root = d.getDocumentElement();

			Element player = d.createElement("Player");
			player.setAttribute("name", name);
			player.setAttribute("password", password);
			player.setAttribute("time", date);
			player.setAttribute("user", regBy);
			player.setAttribute("autologin", "false");
			root.appendChild(player);
			player.appendChild(d.createElement("LastClass"));
			player.appendChild(d.createElement("Classes"));

			saveFile(d, PLAYERS_XML);
			return true;

		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}
		return false;
	}

	public CharacterClass loadClass(NodeList nodes, String Class, Bot b) {
		CharacterClass rv = null;
		for (int i = 0; i < nodes.getLength(); i++) {
			if (nodes.item(i).getNodeName().equals("Classes")) {
				nodes = nodes.item(i).getChildNodes();
				for (i = 0; i < nodes.getLength(); i++) {
					Node cur = nodes.item(i);
					if (cur.hasAttributes()
							&& cur.getAttributes().getNamedItem("name")
									.getNodeValue().equals(Class)) {
						String name = cur.getAttributes().getNamedItem("name")
								.getNodeValue();

						try {
							// load new class
							CharacterClass c = b.getClasses().get(name);
							rv = ClassCreator.createNewClass(c);
						} catch (Exception e) {
							e.printStackTrace();
						}
						// load stats for class
						loadStats(rv, cur);
						break;
					}
				}
				break;
			}
		}
		return rv;
	}

	public CharacterClass loadClass(String Class, String name, Bot b) {
		NodeList nodes = players.getElementsByTagName("Player");
		for (int i = 0; i < nodes.getLength(); i++) {
			Node n = nodes.item(i);
			if (n.getAttributes().getNamedItem("name").getNodeValue()
					.equals(name)) {
				return loadClass(n.getChildNodes(), Class, b);
			}
		}
		return null;
	}

	private void loadFiles() {
		players = parseXmlFile(PLAYERS_XML);
	}

	public Player loadPlayer(String name, String password, Bot b) {
		NodeList nodes = players.getElementsByTagName("Player");
		for (int i = 0; i < nodes.getLength(); i++) {
			Node n = nodes.item(i);
			if (n.getAttributes().getNamedItem("name").getNodeValue()
					.equals(name)
					&& n.getAttributes().getNamedItem("password")
							.getNodeValue().equals(password)) {

				Player p = new Player();

				p.setName(n.getAttributes().getNamedItem("name").getNodeValue());

				Node charclass = null;
				for (int c = 0; c < n.getChildNodes().getLength(); c++) {
					Node x = n.getChildNodes().item(c);
					if (x.getNodeName().equals("LastClass")) {
						charclass = x;
						break;
					}
				}
				if (charclass != null && charclass.hasAttributes()) {
					p.setCharacterClass(loadClass(charclass.getParentNode()
							.getChildNodes(), charclass.getAttributes()
							.getNamedItem("name").getNodeValue(), b));
					if (p.getCharacterClass() != null) {
						p.getCharacterClass().setMyMob(p);
					} else {
						System.err
								.println(p + " is missing a character class.");
					}
				}
				return p;
			}
		}
		return null;
	}

	private void loadRestricted(CharacterClass Class, Node n, RestrictedNumber r) {
		if(r==null){return;}
		for(int x=0; x<n.getChildNodes().getLength(); x++){
			Node next = n.getChildNodes().item(x);
			if (next.hasAttributes()
					&& next.getAttributes().getNamedItem("name").getNodeValue()
							.equals(r.getName())) {
				try {
					for (int i = 0; i < next.getChildNodes().getLength(); i++) {
						Node stat = next.getChildNodes().item(i);
						if (stat.hasAttributes()) {
							loadStat(stat.getNodeName(), r, stat
									.getAttributes().getNamedItem("value"));
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
				
		}
	}

	private void loadStat(String field, RestrictedNumber r, Node n) {
		if (field.equals("initial")) {
			r.setInitial(Integer.parseInt(n.getNodeValue()));
		} else if (field.equals("minimum")) {
			r.setMinimum(Integer.parseInt(n.getNodeValue()));
		} else if (field.equals("current")) {
			r.setCurrent(Integer.parseInt(n.getNodeValue()));
		} else if (field.equals("maximum")) {
			r.setMaximum(Integer.parseInt(n.getNodeValue()));
		}
	}

	private void loadStats(CharacterClass Class, Node n) {
		loadRestricted(Class, n, Class.getLuck());
		loadRestricted(Class, n, Class.getLevel());
		loadRestricted(Class, n, Class.getExp());
		loadRestricted(Class, n, Class.getHp());
		loadRestricted(Class, n, Class.getMagic());
		loadRestricted(Class, n, Class.getOther());
		loadRestricted(Class, n, Class.getPower());
		loadRestricted(Class, n, Class.getSpeed());
		loadRestricted(Class, n, Class.getCon());
	}

	private Document parseXmlFile(String f) {
		File file = new File(f);
		if (!file.exists()) {
			return null;
		}
		if (file.length() == 0) {
			writeRoot(f.substring(0, f.indexOf('.')));
		}

		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

		try {

			DocumentBuilder db = dbf.newDocumentBuilder();
			return db.parse(f);

		} catch (ParserConfigurationException pce) {
			pce.printStackTrace();
		} catch (SAXException se) {
			se.printStackTrace();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
		return null;
	}

	private void saveFile(Document d, String s) {
		FileOutputStream output;
		OutputFormat out = new OutputFormat(d);
		XMLSerializer xSel;
		try {
			xSel = new XMLSerializer(
					output = new FileOutputStream(new File(s)), out);

			out.setIndenting(true);

			xSel.serialize(d);
			output.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public boolean savePlayer(Player p) {
		if (p.getCharacterClass() == null) {
			return false;
		}
		NodeList nodes = players.getElementsByTagName("Player");
		for (int i = 0; i < nodes.getLength(); i++) {
			Node n = nodes.item(i);
			if (n.hasAttributes()
					&& n.getAttributes().getNamedItem("name").getNodeValue()
							.equals(p.getName())) {
				NodeList nl = n.getChildNodes();
				for (int x = 0; x < nl.getLength(); x++) {
					try {
						Node cur = nl.item(x);
						if (cur.getNodeName().equals("Classes")) {
							writeClass(players, cur, p.getCharacterClass());
						} else if (cur.getNodeName().equals("LastClass")) {
							if (cur instanceof Element) {
								Element e = (Element) cur;
								e.setAttribute("name", p.getCharacterClass()
										.getName());
							}
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				saveFile(players, PLAYERS_XML);
				return true;
			}
		}
		return false;
	}

	public void savePlayers() {
		saveFile(players, PLAYERS_XML);
		System.out.println("Saved file.");
	}

	private void writeClass(Document d, Node e, CharacterClass c) {

		if (e.hasChildNodes()) {

			for (int i = 0; i < e.getChildNodes().getLength(); i++) {
				Node next = e.getChildNodes().item(i);
				if (next.hasAttributes()
						&& next.getAttributes().getNamedItem("name")
								.getNodeValue().equals(c.getName())) {
					e.removeChild(next);
				}
			}
		}

		Element Class = d.createElement("Class");
		Class.setAttribute("name", c.getName());
		writeRestricted(d, Class, c.getLevel(), false);
		writeRestricted(d, Class, c.getExp(), false);
		writeRestricted(d, Class, c.getHp(), false);
		writeRestricted(d, Class, c.getPower(), false);
		writeRestricted(d, Class, c.getMagic(), false);
		writeRestricted(d, Class, c.getSpeed(), false);
		writeRestricted(d, Class, c.getOther(), true);
		writeRestricted(d, Class, c.getLuck(), false);
		writeRestricted(d, Class, c.getCon(), false);
		e.appendChild(Class);
	}

	private void writeRestricted(Document d, Node Class, RestrictedNumber n,
			boolean override) {
		Element resn = d.createElement(override ? "Other" : n.getName());
		resn.setAttribute("name", n.getName());

		Element e = d.createElement("minimum");
		e.setAttribute("value", "" + n.getMinimum());
		resn.appendChild(e);

		e = d.createElement("current");
		e.setAttribute("value", "" + n.getCurrent());
		resn.appendChild(e);

		e = d.createElement("maximum");
		e.setAttribute("value", "" + n.getMaximum());
		resn.appendChild(e);

		e = d.createElement("initial");
		e.setAttribute("value", "" + n.getInitial());
		resn.appendChild(e);

		Class.appendChild(resn);
	}

	private void writeRoot(String s) {
		FileOutputStream output = null;
		try {
			Document d = new DocumentImpl();
			Element root = d.createElement(s);

			d.appendChild(root);
			d.setXmlVersion("1.0");

			OutputFormat out = new OutputFormat(d);
			XMLSerializer xSel = new XMLSerializer(
					output = new FileOutputStream(new File(s + ".xml")), out);

			out.setIndenting(true);
			out.setDoctype(s, s);

			xSel.serialize(d);
			output.close();

		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public boolean checkAutoLogin(String name){
		NodeList nodes = players.getElementsByTagName("Player");
		for(int i=0; i<nodes.getLength(); i++){
			Node n = nodes.item(i);
			if(n.getAttributes().getNamedItem("user").getNodeValue().equals(name)){
				if(!Boolean.parseBoolean(n.getAttributes().getNamedItem("autologin").getNodeValue())){
					continue;
				} 
				return true;
			}
		}
		return false;
	}
	
	public boolean setAutoLogin(String name){
		NodeList nodes = players.getElementsByTagName("Player");
		for(int i=0; i<nodes.getLength(); i++){
			Node n = nodes.item(i);
			if(n.getAttributes().getNamedItem("name").getNodeValue().equals(name)){
				Node bool = n.getAttributes().getNamedItem("autologin");
				boolean rv = !Boolean.parseBoolean(bool.getNodeValue());
				bool.setNodeValue(""+rv);
				return rv;
			}
		}
		return false;
	}
	
	public String retrieveName(String find){
		NodeList nodes = players.getElementsByTagName("Player");
		for(int i=0; i<nodes.getLength(); i++){
			Node n = nodes.item(i);
			if(n.getAttributes().getNamedItem("user").getNodeValue().equals(find)){
				if(!Boolean.parseBoolean(n.getAttributes().getNamedItem("autologin").getNodeValue())){
					continue;
				}
				return n.getAttributes().getNamedItem("name").getNodeValue();
			}
		}
		return "";
	}
	
	public String retrievePassword(String player){
		NodeList nodes = players.getElementsByTagName("Player");
		for(int i=0; i<nodes.getLength(); i++){
			Node n = nodes.item(i);
			if(n.getAttributes().getNamedItem("name").getNodeValue().equals(player)){
				if(!Boolean.parseBoolean(n.getAttributes().getNamedItem("autologin").getNodeValue())){
					continue;
				}
				return n.getAttributes().getNamedItem("password").getNodeValue();
			}
		}
		return "";
		
	}
}
