/*
 * @author Kyle Kemp
 */
package commands;

import java.util.ArrayList;
import java.util.TreeSet;

import org.jibble.pircbot.IrcUser;

import spells.Spell;
import battle.Team;
import bot.Bot;
import classes.CharacterClass;
import entities.Mob;

public abstract class Command implements shared.Constants {

	private ArrayList<String> aliases = new ArrayList<String>();

	protected Bot bot;

	private TreeSet<String> classes = new TreeSet<String>();

	protected boolean isGeneral, isPM, isMaster, isBattle;

	public Command(Bot b, String... aliases) {
		this.bot = b;
		for (String s : aliases) {
			this.aliases.add(s);
		}
	}

	protected final boolean checkArgs(String s, int args) {
		String[] temp = s.split(" ");
		return temp.length >= args;
	}

	public abstract boolean execute(Mob who, String channel, String sender,
			String argmess);

	protected final Team findTeam(String s){
		for (Team t : bot.getTeams().values()) {
			if(t.getName().equals(s)){
				return t;
			}
		}
		return null;
	}
	
	protected final Mob findMob(String s) {
		for (Team t : bot.getTeams().values()) {
			for (Mob m : t.getMembers()) {
				if (m.getName().toLowerCase().equals(s.toLowerCase())) {
					return m;
				}
			}
		}
		for(Mob m : bot.getPlayers().values()){
			if(m.getName().equals(s)){
				return m;
			}
		}
		for (IrcUser i : bot.getUsers(Bot.channel)) {
			if (i.getNick().toLowerCase().equals(s.toLowerCase())) {
				return bot.getPlayers().get(i.getNick());
			}
		}
		return null;
	}

	protected final Spell findSpell(CharacterClass c, String name) {
		if (bot.getSpells().containsKey(c.getName())) {
			for (Spell s : bot.getSpells().get(c.getName())) {
				if (s.getName().toLowerCase().equals(name.toLowerCase())) {
					return s;
				}
			}
		}
		return null;
	}

	public ArrayList<String> getAliases() {
		return aliases;
	}

	protected String[] getArgs(String s, int args) {
		String[] temp = s.split(" ");
		String[] rVal = new String[args];

		for (int i = 0; i < args; i++) {
			rVal[i] = temp[i];
		}

		rVal[args - 1] = s.substring(s.lastIndexOf(temp[args - 1]));

		return rVal;
	}

	public TreeSet<String> getClasses() {
		return classes;
	}

	/**
	 * @return the isBattle
	 */
	public boolean isBattle() {
		return isBattle;
	}

	/**
	 * @return the isGeneral
	 */
	public boolean isGeneral() {
		return isGeneral;
	}

	/**
	 * @return the isMaster
	 */
	public boolean isMaster() {
		return isMaster;
	}

	/**
	 * @return the isPM
	 */
	public boolean isPM() {
		return isPM;
	}

	public void setAliases(ArrayList<String> aliases) {
		this.aliases = aliases;
	}

	/**
	 * @param isBattle
	 *            the isBattle to set
	 */
	public void setBattle(boolean isBattle) {
		this.isBattle = isBattle;
	}

	public void setClasses(TreeSet<String> classes) {
		this.classes = classes;
	}

	/**
	 * @param isGeneral
	 *            the isGeneral to set
	 */
	public void setGeneral(boolean isGeneral) {
		this.isGeneral = isGeneral;
	}

	/**
	 * @param isMaster
	 *            the isMaster to set
	 */
	public void setMaster(boolean isMaster) {
		this.isMaster = isMaster;
	}

	/**
	 * @param isPM
	 *            the isPM to set
	 */
	public void setPM(boolean isPM) {
		this.isPM = isPM;
	}

	@Override
	public String toString() {
		return aliases.toString();
	}

}
