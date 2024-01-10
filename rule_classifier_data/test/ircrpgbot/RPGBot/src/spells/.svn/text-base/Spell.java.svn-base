/*
 * @author Kyle Kemp
 */
package spells;

import java.io.File;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Random;
import java.util.TreeSet;

import shared.Elements;
import shared.Eventful;
import battle.Team;
import bot.Bot;
import classes.CharacterClass;
import entities.Entity;
import entities.Mob;

public abstract class Spell extends Entity implements Eventful, Elements {

	public static class Restriction implements Comparable<Restriction> {
		private int cost, levelReq;
		private String myClass, myType;

		public Restriction(String clazz, int cost, int levelReq) {
			this(clazz, "Other", cost, levelReq);
		}

		public Restriction(String clazz, String type, int cost, int levelReq) {
			setMyClass(clazz);
			setCost(cost);
			setLevelReq(levelReq);
			setMyType(type.toLowerCase().contains("hp") ? "hp" : "other");
		}

		@Override
		public int compareTo(Restriction o) {
			return o.getMyClass().equals(getMyClass()) ? 0 : 1;
		}

		public int getCost() {
			return cost;
		}

		public int getLevelReq() {
			return levelReq;
		}

		public String getMyClass() {
			return myClass;
		}

		public String getMyType() {
			return myType;
		}

		public void setCost(int cost) {
			this.cost = cost;
		}

		public void setLevelReq(int levelReq) {
			this.levelReq = levelReq;
		}

		public void setMyClass(String myClass) {
			this.myClass = myClass;
		}

		public void setMyType(String myType) {
			this.myType = myType;
		}
	}

	protected Mob affecting;

	protected int baseDamage = 0;

	protected Bot bot;

	protected TreeSet<Restriction> canCast = new TreeSet<Restriction>();

	protected CharacterClass castedClass;

	protected String description;

	protected int duration = 0;

	protected long element = 0;

	private Random gen = new Random();

	protected boolean isBuff = false;

	protected String type;

	public Spell(Bot b, Restriction... classes) {
		this.bot = b;
		addClasses(classes);
	}

	public void addClasses(Restriction... classes) {
		for (Restriction c : classes) {
			canCast.add(c);
		}
	}

	protected abstract int calcDamage(Mob target);
	
	public void cast(Mob caster, ArrayList<Mob> targets) {
		for (Mob m : targets) {
			caster.sendState(SKILL_USE, caster, m);
			Spell s = null;
			try {
				Constructor<?> constructor=null;
				if(this instanceof ScriptSpell){
					constructor = getClass()
					.getDeclaredConstructor(new Class[] { Bot.class, File.class });
					s = (Spell) constructor.newInstance(bot, ((ScriptSpell)this).getMyFile());
				} else {
					constructor = getClass()
					.getDeclaredConstructor(new Class[] { Bot.class });
					s = (Spell) constructor.newInstance(bot);
				}
				
				s.setCastedClass(caster.getCharacterClass());
				
				s.setBaseDamage(s.getBaseDamage()/targets.size());
				s.cast(caster, m);
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public void cast(Mob caster, Mob target) {
		//TODO: make this not take away extra mp when casting at a teams
		castedClass = caster.getCharacterClass();
		setAffecting(target);

		Restriction cast = getRestriction(caster.getCharacterClass().getName());
		
		if (cast != null) {
			if(cast.getMyType().equals("hp")){
				caster.takeDamage(cast.getCost(), caster, this);
			} else {
			caster.getCharacterClass()
					.getOther()
					.decrease(cast.getCost());
			}
		}
	}

	public void cast(Mob caster, Team t){
		cast(caster, t.getMembers());
	}

	protected final void decreaseDuration() {
		setDuration(--duration);
		if (getDuration() <= 0) {
			cleanUp(false);
		}
	}

	private void cleanUp(boolean force) {
		affecting.getSpells().remove(this);
		onDurationOver(force);
	}

	/**
	 * @return the affecting
	 */
	public Mob getAffecting() {
		return affecting;
	}

	/**
	 * @return the baseDamage
	 */
	public int getBaseDamage() {
		return baseDamage;
	}

	/**
	 * @return the canCast
	 */
	public TreeSet<Restriction> getCanCast() {
		return canCast;
	}

	/**
	 * @return the castedClass
	 */
	public CharacterClass getCastedClass() {
		return castedClass;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	public int getDuration() {
		return duration;
	}

	/**
	 * @return the element
	 */
	public long getElement() {
		return element;
	}

	public final Restriction getRestriction(String c) {
		for (Restriction r : getCanCast()) {
			if (r.getMyClass().equals(c)) {
				return r;
			}
		}
		return null;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @return the isBuff
	 */
	public boolean isBuff() {
		return isBuff;
	}

	@Override
	public void onAllyAttack(Entity hitter, Entity hit) {

	}

	@Override
	public void onAllyCriticalHit(Entity hitter, Entity target, int damage) {

	}

	@Override
	public void onAllyCriticalHit(Entity hitter, Entity hit, Integer damage) {
		onAllyCriticalHit(hitter, hit, damage.intValue());

	}

	@Override
	public void onAllyDamage(Entity tookDamage, Entity hitter, int damage) {

	}

	@Override
	public void onAllyDamage(Entity tookDamage, Entity hitter, Integer damage) {
		onAllyDamage(tookDamage, hitter, damage.intValue());

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see shared.Eventful#onAllyDamaged(entities.Entity, int)
	 */
	@Override
	public void onAllyDamaged(Entity hitter, int damage) {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see shared.Eventful#onAllyDamaged(entities.Entity, java.lang.Integer)
	 */
	@Override
	public void onAllyDamaged(Entity hitter, Integer damage) {

	}

	@Override
	public void onAllyDeath(Entity killer, Entity target) {

	}
	
	@Override
	public void onAllyFlee(Entity flee) {

	}

	@Override
	public void onAllyGainExp(Entity gain) {

	}

	@Override
	public void onAllyHealed(Entity user, Entity target, int healed) {

	}

	@Override
	public void onAllyHealed(Entity user, Entity target, Integer healed) {
		
	}

	@Override
	public void onAllyHit(Entity hitter, Entity target, int damage) {

	}

	@Override
	public void onAllyHit(Entity hitter, Entity target, Integer damage) {
		onAllyHit(hitter, target, damage.intValue());

	}



	@Override
	public void onAllyLevelUp(Entity gain) {

	}

	@Override
	public void onAllyMiss(Entity hitter, Entity target) {

	}

	@Override
	public void onAllySkillUse(Entity user, Entity target, Spell skill) {

	}

	@Override
	public void onAllyTurnEnd(Entity turn) {

	}

	@Override
	public void onAllyTurnStart(Entity turn) {

	}

	public void onDurationOver(boolean forced){
		
	}

	@Override
	public void onEnemyAttack(Entity hitter, Entity hit) {

	}

	@Override
	public void onEnemyCriticalHit(Entity hitter, Entity target, int damage) {

	}

	@Override
	public void onEnemyCriticalHit(Entity hitter, Entity hit, Integer damage) {
		onEnemyCriticalHit(hitter, hit, damage.intValue());

	}

	@Override
	public void onEnemyDamage(Entity tookDamage, Entity hitter, int damage) {

	}

	@Override
	public void onEnemyDamage(Entity tookDamage, Entity hitter, Integer damage) {
		onEnemyDamage(tookDamage, hitter, damage.intValue());

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see shared.Eventful#onEnemyDamaged(entities.Entity, int)
	 */
	@Override
	public void onEnemyDamaged(Entity hitter, int damage) {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see shared.Eventful#onEnemyDamaged(entities.Entity, java.lang.Integer)
	 */
	@Override
	public void onEnemyDamaged(Entity hitter, Integer damage) {

	}
	@Override
	public void onEnemyDeath(Entity killer, Entity target) {

	}

	@Override
	public void onEnemyFlee(Entity flee) {

	}

	@Override
	public void onEnemyGainExp(Entity gain) {

	}

	@Override
	public void onEnemyHealed(Entity user, Entity target, int healed) {

	}

	@Override
	public void onEnemyHealed(Entity user, Entity target, Integer healed) {
		
	}

	@Override
	public void onEnemyHit(Entity hitter, Entity target, int damage) {

	}

	@Override
	public void onEnemyHit(Entity hitter, Entity target, Integer damage) {
		onEnemyHit(hitter, target, damage.intValue());

	}



	@Override
	public void onEnemyLevelUp(Entity gain) {

	}

	@Override
	public void onEnemyMiss(Entity hitter, Entity target) {

	}

	@Override
	public void onEnemySkillUse(Entity user, Entity target, Spell skill) {

	}

	@Override
	public void onEnemyTurnEnd(Entity turn) {

	}

	@Override
	public void onEnemyTurnStart(Entity turn) {

	}

	@Override
	public void onFightEnd() {

	}

	
	@Override
	public void onFightStart() {

	}
	

	@Override
	public void onGainExp(Entity gainer) {

	}

	@Override
	public void onLevelUp(Entity awesome) {

	}

	@Override
	public void onPartyLose(Team t) {

	}

	@Override
	public void onPartyWin(Team t) {

	}

	@Override
	public void onRecieveAttack(Entity hitter, Entity hit) {

	}

	@Override
	public void onRecieveCriticalHit(Entity hitter, Entity target, int damage) {

	}

	@Override
	public void onRecieveCriticalHit(Entity hitter, Entity hit, Integer damage) {
		onRecieveCriticalHit(hitter, hit, damage.intValue());

	}

	@Override
	public void onRecieveDamage(Entity tookDamage, Entity hitter, int damage) {

	}

	@Override
	public void onRecieveDamage(Entity tookDamage, Entity hitter, Integer damage) {
		onRecieveDamage(tookDamage, hitter, damage.intValue());

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see shared.Eventful#onRecieveDamaged(entities.Entity, int)
	 */
	@Override
	public void onRecieveDamaged(Entity hitter, int damage) {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see shared.Eventful#onRecieveDamaged(entities.Entity, java.lang.Integer)
	 */
	@Override
	public void onRecieveDamaged(Entity hitter, Integer damage) {

	}
	@Override
	public void onRecieveDeath(Entity killer, Entity target) {

	}

	@Override
	public void onRecieveFlee(Entity flee) {

	}

	@Override
	public void onRecieveGainExp(Entity gain) {

	}

	@Override
	public void onRecieveHealed(Entity user, Entity target, int healed) {

	}

	@Override
	public void onRecieveHealed(Entity user, Entity target, Integer healed) {
		
	}

	@Override
	public void onRecieveHit(Entity hitter, Entity target, int damage) {

	}

	@Override
	public void onRecieveHit(Entity hitter, Entity target, Integer damage) {
		onRecieveHit(hitter, target, damage.intValue());

	}



	@Override
	public void onRecieveLevelUp(Entity gain) {

	}

	@Override
	public void onRecieveMiss(Entity hitter, Entity target) {

	}

	@Override
	public void onRecieveSkillUse(Entity user, Entity target, Spell skill) {

	}

	@Override
	public void onRecieveTurnEnd(Entity turn) {

	}

	@Override
	public void onRecieveTurnStart(Entity turn) {

	}

	@Override
	public void onRoundEnd() {

	}

	@Override
	public void onRoundStart() {

	}

	
	@Override
	public void onSelfAttack(Entity hitter, Entity hit) {

	}

	@Override
	public void onSelfCriticalHit(Entity hitter, Entity target, int damage) {

	}

	@Override
	public void onSelfCriticalHit(Entity hitter, Entity hit, Integer damage) {
		onSelfCriticalHit(hitter, hit, damage.intValue());

	}

	@Override
	public void onSelfDamage(Entity tookDamage, Entity hitter, int damage) {

	}

	@Override
	public void onSelfDamage(Entity tookDamage, Entity hitter, Integer damage) {
		onSelfDamage(tookDamage, hitter, damage.intValue());

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see shared.Eventful#onSelfDamaged(entities.Entity, int)
	 */
	@Override
	public void onSelfDamaged(Entity hitter, int damage) {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see shared.Eventful#onSelfDamaged(entities.Entity, java.lang.Integer)
	 */
	@Override
	public void onSelfDamaged(Entity hitter, Integer damage) {

	}

	@Override
	public void onSelfDeath(Entity killer, Entity target) {

	}

	@Override
	public void onSelfFlee(Entity flee) {

	}

	@Override
	public void onSelfGainExp(Entity gain) {

	}

	@Override
	public void onSelfHealed(Entity user, Entity target, int healed) {

	}

	@Override
	public void onSelfHealed(Entity user, Entity target, Integer healed) {
		
	}

	@Override
	public void onSelfHit(Entity hitter, Entity target, int damage) {

	}

	@Override
	public void onSelfHit(Entity hitter, Entity target, Integer damage) {
		onSelfHit(hitter, target, damage.intValue());

	}



	@Override
	public void onSelfLevelUp(Entity gain) {

	}

	@Override
	public void onSelfMiss(Entity hitter, Entity target) {

	}

	@Override
	public void onSelfSkillUse(Entity user, Entity target, Spell skill) {

	}

	@Override
	public final void onSelfTurnEnd(Entity turn) {
		postTick();
	}

	@Override
	public final void onSelfTurnStart(Entity turn) {
		preTick();

	}

	public void postTick() {
	}

	public void preTick() {
	}

	public final boolean prob(int x) {
		return !(gen.nextInt(100) + 1 > x);
	}

	public final int rand(int i) {
		return gen.nextInt(i);
	}

	public final int rand(int x, int y) {
		return gen.nextInt(y - x) + x;
	}

	private void setAffecting(Mob target) {
		this.affecting=target;

		for (Spell s : affecting.getSpells()) {
			if(s.getName()==null){
				System.out.println(s.getDescription()+" is null!");
				continue;}
			if (s.getName().equals(getName()) && s.getDescription().equals(getDescription())) {
				s.cleanUp(true);
			}
		}
		
		affecting.getSpells().add(this);
		
	}

	/**
	 * @param baseDamage
	 *            the baseDamage to set
	 */
	public void setBaseDamage(int baseDamage) {
		this.baseDamage = baseDamage;
	}

	/**
	 * @param isBuff
	 *            the isBuff to set
	 */
	public void setBuff(boolean isBuff) {
		this.isBuff = isBuff;
	}

	/**
	 * @param castedClass
	 *            the castedClass to set
	 */
	public void setCastedClass(CharacterClass castedClass) {
		this.castedClass = castedClass;
	}

	
	/**
	 * @param description
	 *            the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	/**
	 * @param element
	 *            the element to set
	 */
	public void setElement(long element) {
		this.element = element;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return getName();
	}
}
