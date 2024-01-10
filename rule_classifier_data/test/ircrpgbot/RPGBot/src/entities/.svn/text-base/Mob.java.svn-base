/*
 * @author Kyle Kemp
 */
package entities;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

import org.jibble.pircbot.Colors;

import shared.Constants;
import spells.Spell;
import battle.Team;
import bot.Bot;
import classes.CharacterClass;

import commands.Command;

public abstract class Mob extends Entity implements Constants {

	private Random gen = new Random();

	private CharacterClass characterClass;

	private int expGain = 0;

	private Team team;

	private CopyOnWriteArrayList<Spell> spells = new CopyOnWriteArrayList<Spell>();

	public int modDamage(Mob m, int i){
		return getCharacterClass().modDamage(m, i);
	}
	
	public int calcDamage(Mob m) {
		return getCharacterClass().calcDamage(m);
	}

	protected final boolean prob(int x) {
		return !(gen.nextInt(100) + 1 > x);
	}

	public void attack(Mob m) {
		Mob who = this;
		//TODO: perhaps add a block event
		who.sendState(ATTACK, who, m);
		int damage = who.calcDamage(m);
		damage = who.modDamage(m, damage);
		damage -= m.getCharacterClass().getCon().getTotal();
		//TODO: fix this -- dodging is absurdly incorrect.
		if (damage > 0) {
			if(prob(((who.getCharacterClass().getSpeed()
						.getTotal()) / (who.getCharacterClass().getSpeed().getMaximum()))
						) 
						|| m.canDodge()){

				getTeam().getInvolvement().bot.sendMessage(Bot.channel,  Colors.BOLD+m+" dodged the attack.");
				who.sendState(MISS, who, m);
				
			} else {
				if (gen.nextInt(who.getCharacterClass().getLuck().getMaximum()/10)
						< who.getCharacterClass()
						.getLuck().getTotal()/10) {
					damage *= 3;
					who.sendState(CRITICAL_HIT, who, m, damage);
				} else {
					who.sendState(HIT, who, m, damage);
					who.sendState(GIVE_DAMAGE, who, m, damage);
				}
			}

		} else {
			getTeam().getInvolvement().bot.sendMessage(Bot.channel,  Colors.BOLD+"The attack was blocked.");
			who.sendState(MISS, who, m);
		}
	}
	
	public boolean canDodge(){
		return getCharacterClass().canDodge();
	}

	public boolean canEventHappen(int state, Mob origin) {
		CharacterClass my = getCharacterClass();
		switch (state) {
		case ATTACK:
			if (my.getHp().atMin()) {
				return false;
			}
			break;
		}
		return true;
	}

	public final boolean canExecute(Command c) {
		return getCharacterClass() != null ? getCharacterClass().canExecute(c)
				: true;
	}

	public CharacterClass getCharacterClass() {
		return characterClass;
	}

	public ArrayList<String> getCommands() {
		ArrayList<String> rv = new ArrayList<String>();
		for (Command c : Bot.getCommands()) {
			if (c.isBattle() && canExecute(c)) {
				rv.add(c.toString());
			}
		}
		return rv;
	}

	public int getExpGain() {
		return expGain;
	}

	public CopyOnWriteArrayList<Spell> getSpells() {
		return spells;
	}

	public String getStats() {
		CharacterClass c = this.getCharacterClass();
		return c.getStats();
	}

	public Team getTeam() {
		return team;
	}

	public Spell[] getSpellsByElement(long element){
		ArrayList<Spell> rv = new ArrayList<Spell>();
		for(Spell s : spells){
			if((s.getElement() & element)>0){
				rv.add(s);
			}
		}
		return (Spell[]) rv.toArray();
	}
	
	public boolean affectedBySpell(String spellname) {
		for (Spell s : spells) {
			if (s.getName().equals(spellname)) {
				return true;
			}
		}
		return false;
	}

	public void setCharacterClass(CharacterClass characterClass) {
		this.characterClass = characterClass;
	}

	public void setExpGain(int expGain) {
		this.expGain = expGain;
	}

	public void setTeam(Team team) {
		if (team == null) {
			if (this.team != null && this.team.getLeader() != null
					&& this.team.getLeader().equals(this)) {
				this.team.setLeader(null);

			}
		} else {
			this.addObserver(team);
		}
		this.team = team;
	}

	public void takeDamage(int i, Mob hitter, Spell source) {
		getCharacterClass().takeDamage(i, hitter, source);
	}

	public String toBattleString() {
		return getName();
	}

	@Override
	public String toString() {
		return getName();
	}

}
