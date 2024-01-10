/*
 * @author Kyle Kemp
 */
package battle;

import java.util.ArrayList;

import org.jibble.pircbot.Colors;

import shared.Constants;
import shared.Event;
import shared.Observer;
import shared.Subject;
import spells.Spell;
import bot.Bot;
import entities.Mob;

public class Team implements Observer, Constants {

	private Mob leader;

	private int limit = 5;
	private ArrayList<Mob> members = new ArrayList<Mob>();
	private String name;
	private ArrayList<Team> allies;
	private Battle involvement;

	public Team(String s, Mob leader) {
		setName(s);
		setLeader(leader);
		if (addMember(leader)) {
		}
	}

	public boolean addMember(Mob m) {
		if (!members.contains(m) && members.add(m)) {
			m.setTeam(this);
			if (getLeader() == null) {
				setLeader(m);
			}
			return true;
		}
		return false;
	}

	public void endFight() {
		try {
			for (Mob m : getMembers()) {
				m.getCharacterClass().gainExp(
						getInvolvement().getConsolationExp());
				for(Spell s : m.getSpells()){
					s.onDurationOver(true);
				}
				m.getSpells().clear();
				m.getCharacterClass().heal();
				m.clearObservers();
				m.setTeam(null);
			}
			setLeader(null);
			getInvolvement().bot.getTeams().remove(getName());
			setInvolvement(null);
			members.clear();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void finalize() throws Throwable {
		System.out.println("Team finalized broski" + this);
		super.finalize();
	}

	public ArrayList<Team> getAllies() {
		return allies;
	}

	public Battle getInvolvement() {
		return involvement;
	}

	public Mob getLeader() {
		return leader;
	}

	/**
	 * @return the limit
	 */
	public int getLimit() {
		return limit;
	}

	/**
	 * @return the members
	 */
	public ArrayList<Mob> getMembers() {
		return members;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	public void setAllies(ArrayList<Team> allies) {
		this.allies = allies;
	}

	public void setInvolvement(Battle involvement) {
		this.involvement = involvement;
	}

	public void setLeader(Mob leader) {
		this.leader = leader;
	}

	/**
	 * @param limit
	 *            the limit to set
	 */
	public void setLimit(int limit) {
		this.limit = limit;
	}

	/**
	 * @param members
	 *            the members to set
	 */
	public void setMembers(ArrayList<Mob> members) {
		this.members = members;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		if (name.length() > 20) {
			name = name.substring(0, 20);
		}
		this.name = name;
	}

	@Override
	public String toString() {
		return name;
	}

	// from entity
	/* (non-Javadoc)
	 * @see shared.Observer#update(int, entities.Mob, entities.Mob, spells.Spell, entities.Item, int)
	 */
	@Override
	public void update(int state, Mob source, Mob target, Spell spell,
			int damage) {

		if (getInvolvement() != null) {
			Event.doEvent(state, source, target, spell, damage);
		}

		switch (state) {
		case FIGHT_END:
			endFight();
			break;
		case DEATH:
			getInvolvement().bot
					.sendMessage(Bot.channel, Colors.BOLD + source + " has died!");
			break;
		}
	}

	// from battle
	@Override
	public void update(Subject o, int state) {
		update(state, null, null, null, 0);
	}

}
