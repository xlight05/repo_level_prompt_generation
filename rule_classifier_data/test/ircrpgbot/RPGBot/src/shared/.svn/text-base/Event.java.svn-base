/*
 * @author Kyle Kemp
 */
package shared;

import java.lang.reflect.Method;

import javax.script.ScriptException;

import classes.ScriptClass;

import spells.ScriptSpell;
import spells.Spell;
import battle.Team;
import entities.Entity;
import entities.Mob;

public class Event implements Constants {

	private static class Reduction {
		public static int percVal = 0;
		public static int defVal = 0;

		public static int modDamage(Integer cur) {
			double d = cur * (percVal / 100.0);
			Double i = (cur - defVal - d);
			defVal = 0;
			percVal = 0;
			return i.intValue();
		}
	}

	private static Object[] determineArgs(int state, Mob source, Mob target,
			Spell spell, int damage) {
		switch (state) {
		case ATTACK:
		case DEATH:
		case MISS:
			return new Object[] { source, target };
		case HIT:
		case CRITICAL_HIT:
		case TAKE_HEALS:
		case GIVE_DAMAGE:
			return new Object[] { source, target, damage };
		case TAKE_DAMAGE:
			return new Object[] { source, damage };
		case FLEE:
		case TURN_START:
		case TURN_END:
		case EXP_GAIN:
		case LEVEL_UP:
			return new Object[] { source };
		case SKILL_USE:
			return new Object[] { source, target, spell };
		case FIGHT_END:
		case FIGHT_START:
		case ROUND_END:
		case ROUND_START:
			return new Object[] {};
		case PARTY_LOSE:
		case PARTY_WIN:
			return new Object[] { source.getTeam() };
		}
		return null;
	}

	@SuppressWarnings("rawtypes")
	private static Class[] determineArgtypes(int state) {
		switch (state) {
		case ATTACK:
		case DEATH:
		case MISS:
			return new Class[] { Entity.class, Entity.class };
		case HIT:
		case CRITICAL_HIT:
		case GIVE_DAMAGE:
		case TAKE_HEALS:
			return new Class[] { Entity.class, Entity.class, Integer.class };
		case TAKE_DAMAGE:
			return new Class[] { Entity.class, Integer.class };
		case FLEE:
		case TURN_START:
		case TURN_END:
		case EXP_GAIN:
		case LEVEL_UP:
			return new Class[] { Entity.class };
		case SKILL_USE:
			return new Class[] { Entity.class, Entity.class, Spell.class };
		case FIGHT_END:
		case FIGHT_START:
		case ROUND_END:
		case ROUND_START:
			return new Class[] {};
		case PARTY_LOSE:
		case PARTY_WIN:
			return new Class[] { Team.class };
		}
		return null;
	}

	// pass in null (source, target) for battle events
	@SuppressWarnings("rawtypes")
	public static void doEvent(int state, Mob source, Mob target, Spell spell,
			 int damage) {

		if (source != null) {
			Team steam = source.getTeam();

			for (Team t : source.getTeam().getInvolvement().getTeams()) {
				for (Mob m : t.getMembers()) {

					String method = "on";
					Object[] args = null;
					Class[] argtypes = null;

					if (source.equals(m)) {
						method += "Self";
					} else if (target != null && target.equals(m)) {
						method += "Recieve";
					} else if (t.equals(steam)) {
						method += "Ally";
					} else {
						method += "Enemy";
					}

					method += state2String(state);
					args = determineArgs(state, source, target, spell, 
							damage);
					argtypes = determineArgtypes(state);

					try {
						for (Spell s : m.getSpells()) {
							Method mar = s.getClass().getSuperclass()
									.getMethod(method, argtypes);
							int intArg = 0;
							for (int intArgt = 0; intArgt < args.length; intArgt++) {
								if (args[intArgt] instanceof Integer) {
									intArg = intArgt;
									break;
								}
							}
							if (intArg != 0 && s.isBuff()) {
								switch (s.getType().charAt(0)) {
								case 'p':
									Reduction.percVal += s.getBaseDamage();
									break;
								case 'd':
									Reduction.defVal += s.getBaseDamage();
									break;
								}
								args[intArg] = Reduction
										.modDamage((Integer) args[intArg]);
							}
							if (s instanceof ScriptSpell && state != TURN_END
									&& state != TURN_START) {
								ScriptSpell script = (ScriptSpell) s;
								if (((ScriptSpell) s).getMyScript().contains(
										method)) {
									script.getiEngine().invokeFunction(method,
											args);
								}
							} else {
								mar.invoke(s, args);
							}
						}

						Method me = m.getCharacterClass().getClass()
								.getSuperclass().getMethod(method, argtypes);

						if(m.getCharacterClass() instanceof ScriptClass){
							//System.out.println(method);
							ScriptClass s = (ScriptClass)m.getCharacterClass();
							switch(state){
							case HIT:
							case CRITICAL_HIT:
							case DEATH:
								me.invoke(m.getCharacterClass(), args);
								break;
							default:
								try{
								if(s.getMyScript().contains(method+"(")){
									s.getiEngine().invokeFunction(method, args);
								} 
								}catch(ScriptException e){
									e.printStackTrace();
									System.err.println(s.getMyFile().getName());
								}
							}
						} else {
							me.invoke(m.getCharacterClass(), args);
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

	private static String state2String(int state) {
		switch (state) {
		case ATTACK:
			return "Attack";
		case HIT:
			return "Hit";
		case CRITICAL_HIT:
			return "CriticalHit";
		case DEATH:
			return "Death";
		case FLEE:
			return "Flee";
		case MISS:
			return "Miss";
		case SKILL_USE:
			return "SkillUse";
		case TURN_START:
			return "TurnStart";
		case TURN_END:
			return "TurnEnd";
		case FIGHT_END:
			return "FightEnd";
		case FIGHT_START:
			return "FightStart";
		case PARTY_LOSE:
			return "PartyLose";
		case PARTY_WIN:
			return "PartyWin";
		case EXP_GAIN:
			return "GainExp";
		case LEVEL_UP:
			return "LevelUp";
		case ROUND_START:
			return "RoundStart";
		case ROUND_END:
			return "RoundEnd";
		case TAKE_DAMAGE:
			return "Damaged";
		case GIVE_DAMAGE:
			return "Damage";
		case TAKE_HEALS:
			return "Healed";
		}
		return null;
	}
}
