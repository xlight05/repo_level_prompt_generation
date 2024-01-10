/*
 * @author Kyle Kemp
 */
package bot;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.TreeMap;

import org.jibble.pircbot.IrcUser;
import org.jibble.pircbot.PircBot;
import org.jibble.pircbot.TrustingSSLSocketFactory;

import spells.ScriptSpell;
import spells.Spell;
import spells.Spell.Restriction;
import xml.XMLManager;
import battle.Battle;
import battle.Team;
import classes.CharacterClass;
import classes.ScriptClass;

import commands.Command;

import entities.Mob;
import entities.Player;

public class Bot extends PircBot implements shared.Constants {

	public static final String channel = "#rpgbot";

	private static LinkedList<Command> commands = new LinkedList<Command>();

	public static LinkedList<Command> getCommands() {
		return commands;
	}

	private ArrayList<Battle> battles = new ArrayList<Battle>();

	private TreeMap<String, CharacterClass> classes = new TreeMap<String, CharacterClass>();

	private ArrayList<String> masters = new ArrayList<String>();

	private TreeMap<String, Player> players = new TreeMap<String, Player>();

	private HashMap<String, ArrayList<Spell>> spells = new HashMap<String, ArrayList<Spell>>();

	private TreeMap<String, Team> teams = new TreeMap<String, Team>();

	public static final XMLManager xml = new XMLManager();

	public Bot(String name, String server) {
		initialize();
		setAutoNickChange(true);
		setName(name);
		try {
			//this.connect(server, 6697, new TrustingSSLSocketFactory());
			this.connect(server, 6667);
			joinChannel("#rpgbot");
		} catch (Exception e) {
			e.printStackTrace();
		}
		masters.add("EllyMent");
		masters.add("Electric");
		masters.add("KR");
		masters.add("Chase");
		masters.add("Darkblizer");

		this.identify("seiyria");
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		attemptAutoRecognize();
	}

	public ArrayList<Battle> getBattles() {
		return battles;
	}

	/**
	 * @return the classes
	 */
	public TreeMap<String, CharacterClass> getClasses() {
		return classes;
	}

	public ArrayList<String> getMasters() {
		return masters;
	}

	public Mob getMob(String name) {
		return players.get(name);
	}

	/**
	 * @return the players
	 */
	public TreeMap<String, Player> getPlayers() {
		return players;
	}

	public HashMap<String, ArrayList<Spell>> getSpells() {
		return spells;
	}

	public TreeMap<String, Team> getTeams() {
		return teams;
	}

	public void initialize() {

		getSpells().clear();
		getClasses().clear();
		getCommands().clear();

		loadCommands();
		loadClasses();
		loadSpells();
	}

	private void loadClasses() {
		try {
			for (Class<?> c : shared.ClassFinder.getClasses("classes")) {
				try {
					Constructor<?> constructor = c.getDeclaredConstructor();
					CharacterClass com = (CharacterClass) constructor
							.newInstance();
					classes.put(com.getName(), com);
				} catch (NullPointerException e) {
				} catch (InstantiationException e) {
				} catch (IllegalAccessException e) {
				} catch (IllegalArgumentException e) {
				} catch (InvocationTargetException e) {
				} catch (NoSuchMethodException e) {
				} catch (SecurityException e) {
				}
			}
		} catch (ClassNotFoundException e) {
		} catch (IOException e) {
		}

		File dir = new File("./scripts/classes/");
		File[] files = dir.listFiles();
		for (File f : files) {

			// directories obviously don't have the necessary properties to be a
			// script
			if (f.isDirectory()) {
				continue;
			}

			// just in case there are "other" files in the folder
			if (f.toString().contains(".js")) {
				ScriptClass newClass = new ScriptClass(f);
				classes.put(newClass.getName(), newClass);
			}
		}
	}

	private void loadCommands() {
		try {
			for (Class<?> c : shared.ClassFinder.getClasses("commands")) {
				try {
					Constructor<?> constructor = c
							.getDeclaredConstructor(new Class[] { Bot.class });
					Command com = (Command) constructor.newInstance(this);
					commands.add(com);
				} catch (NullPointerException e) {
				} catch (InstantiationException e) {
				} catch (IllegalAccessException e) {
				} catch (IllegalArgumentException e) {
				} catch (InvocationTargetException e) {
				} catch (NoSuchMethodException e) {
				} catch (SecurityException e) {
				}
			}
		} catch (ClassNotFoundException e) {
		} catch (IOException e) {
		}
	}

	private void loadSpells() {

		ArrayList<Spell> spells = new ArrayList<Spell>();

		try {
			for (Class<?> c : shared.ClassFinder.getClasses("spells")) {
				try {
					Constructor<?> constructor = c
							.getDeclaredConstructor(new Class[] { Bot.class });
					Spell s = (Spell) constructor.newInstance(this);
					spells.add(s);
				} catch (NullPointerException e) {
				} catch (InstantiationException e) {
				} catch (IllegalAccessException e) {
				} catch (IllegalArgumentException e) {
				} catch (InvocationTargetException e) {
				} catch (NoSuchMethodException e) {
				} catch (SecurityException e) {
				}
			}
		} catch (ClassNotFoundException e) {
		} catch (IOException e) {
		}

		File dir = new File("./scripts/spells/");
		File[] files = dir.listFiles();
		for (File f : files) {

			// directories obviously don't have the necessary properties to be a
			// script
			if (f.isDirectory()) {
				continue;
			}

			// just in case there are "other" files in the folder
			if (f.toString().contains(".js")) {
				spells.add(new ScriptSpell(this, f));
			}
		}

		for (Spell s : spells) {
			for (Restriction r : s.getCanCast()) {
				String name = r.getMyClass();
				if (!this.spells.containsKey(name)) {
					this.spells.put(name, new ArrayList<Spell>());
				}
				this.spells.get(name).add(s);
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jibble.pircbot.PircBot#onDisconnect()
	 */
	@Override
	protected void onDisconnect() {
		boolean connected = false;
		while (!connected) {
			try {
				this.connect("irc.esper.net", 6697,
						new TrustingSSLSocketFactory());
				joinChannel("#rpgbot");
				connected = true;
			} catch (Exception e) {
				System.out.println("Attempting to reconnect in 5..");
				connected = false;
			}
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jibble.pircbot.PircBot#onMessage(java.lang.String,
	 * java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	protected void onMessage(String channel, String sender, String login,
			String hostname, String message) {

		if (message.startsWith("!")) {
			message = message.substring(1);
			String command = message.split(" ")[0].toLowerCase();
			for (Command c : commands) {
				if (c.isMaster() && !(masters.contains(sender))) {
					continue;
				}
				if (c.isPM()) {
					continue;
				}
				if (c.getAliases().contains(command)) {
					Player p = players.get(sender);
					if (c.isGeneral() || (p != null && p.canExecute(c))) {
						if (message.length() > message.indexOf(command)
								+ command.length() + 1) {
							message = message.substring(message
									.indexOf(command) + command.length() + 1);
						}
						if (c.isBattle()) {
							if (p != null && p.getTeam() != null
									&& p.getTeam().getInvolvement() != null) {
								Battle b = p.getTeam().getInvolvement();
								if (p.equals(b.getCurTurn())) {
									if (c.execute(p, channel, sender, message)) {
										b.changeTurn();
									}
								} else {
									sendMessage(channel, "It's not your turn!");
								}
							}
						} else {
							c.execute(p, channel, sender, message);
						}
					} else {
						sendMessage(
								channel,
								"You do not have permission to execute this command, perhaps you're not logged in?");
					}
				}
			}
		}

		super.onMessage(channel, sender, login, hostname, message);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jibble.pircbot.PircBot#onNickChange(java.lang.String,
	 * java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	protected void onNickChange(String oldNick, String login, String hostname,
			String newNick) {

		if (players.containsKey(oldNick)) {
			players.put(newNick, players.get(oldNick));
			players.remove(oldNick);
		}

		super.onNickChange(oldNick, login, hostname, newNick);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jibble.pircbot.PircBot#onPrivateMessage(java.lang.String,
	 * java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	protected void onPrivateMessage(String sender, String login,
			String hostname, String message) {

		if (message.startsWith("!")) {
			message = message.substring(1);
		}
		String command = message.split(" ")[0].toLowerCase();
		for (Command c : commands) {
			if (c.isMaster() && !(masters.contains(sender))) {
				continue;
			}
			if (c.getAliases().contains(command)) {
				Player p = players.get(sender);
				if (c.isGeneral() || (p != null && p.canExecute(c))) {
					if (message.length() > message.indexOf(command)
							+ command.length() + 1) {
						message = message.substring(message.indexOf(command)
								+ command.length() + 1);
					}
					c.execute(p, sender, sender, message);
				} else if (p == null) {
					sendMessage(sender, "You aren't logged in!");
				}
			}
		}

		super.onPrivateMessage(sender, login, hostname, message);
	}

	/* (non-Javadoc)
	 * @see org.jibble.pircbot.PircBot#onJoin(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	protected void onJoin(String channel, String sender, String login,
			String hostname) {
		if(!players.containsKey(sender) && xml.checkAutoLogin(sender) ){
			autoLogin(sender);
		}
		super.onJoin(channel, sender, login, hostname);
	}

	private void autoLogin(String sender) {
		String user = xml.retrieveName(sender);
		String password = xml.retrievePassword(xml.retrieveName(sender));
		Player p = Bot.xml.loadPlayer(user, password, this);
		getPlayers().put(sender, p);
		sendMessage(Bot.channel, sender + " has autologged in as "
				+ p + ".");
	}

	public String reverseMobFind(Mob sender) {
		for (String s : getPlayers().keySet()) {
			if (getPlayers().get(s).equals(sender)) {
				return s;
			}
		}
		return null;
	}

	public void attemptAutoRecognize() {

		for(IrcUser u : this.getUsers(channel)){
			if(xml.checkAutoLogin(u.getNick())){
				autoLogin(u.getNick());
			}
		}
	}

	public void save() {
		for (Player p : this.getPlayers().values()) {
			xml.savePlayer(p);
		}
	}

	public void setBattles(ArrayList<Battle> battles) {
		this.battles = battles;
	}

	public void setMasters(ArrayList<String> masters) {
		this.masters = masters;
	}

	/**
	 * @param players
	 *            the players to set
	 */
	public void setPlayers(TreeMap<String, Player> players) {
		this.players = players;
	}

	public void setSpells(HashMap<String, ArrayList<Spell>> spells) {
		this.spells = spells;
	}

	public void setTeams(TreeMap<String, Team> teams) {
		this.teams = teams;
	}
}
