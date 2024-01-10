/*
 * @author Kyle Kemp
 */
package classes;

import java.util.TreeSet;

import org.jibble.pircbot.Colors;

import shared.Constants;
import shared.Elements;
import shared.Eventful;
import shared.RestrictedNumber;
import spells.Spell;
import battle.Team;
import bot.Bot;

import commands.Command;

import entities.Entity;
import entities.Mob;
import entities.Monster;
import entities.Player;

public abstract class CharacterClass implements Eventful, Constants, Elements {
	
	public static class Restriction implements Comparable<Restriction> {
		
		private int levelReq;
		private String myClass;

		public Restriction(String clazz, int levelReq) {
			setMyClass(clazz);
			setLevelReq(levelReq);
		}

		@Override
		public int compareTo(Restriction o) {
			return o.getMyClass().equals(getMyClass()) ? 0 : 1;
		}

		public int getLevelReq() {
			return levelReq;
		}

		public String getMyClass() {
			return myClass;
		}

		public void setLevelReq(int levelReq) {
			this.levelReq = levelReq;
		}

		public void setMyClass(String clazz) {
			this.myClass = clazz;
		}
	}
	
	private TreeSet<Restriction> classRestrictions = new TreeSet<Restriction>();

	private long element=0;

	//TODO: add another other stat, and make it so stats are skipped on save if null
	//TODO: remove fighter and bloodbarian mana stat
	private RestrictedNumber level, power, magic, speed, hp, luck, other, exp, con;

	protected int luckInterval = 10, powerGain = 1, magicGain = 1, hpGain = 30,
			speedGain = 1, otherGain = 10, conGain=1, luckGain=1;

	private Mob myMob;

	private String name;


	public CharacterClass() {
		setName(getClass().getSimpleName());
		setStats();
	}

	public void addClasses(Restriction... classes) {
		for (Restriction c : classes) {
			classRestrictions.add(c);
		}
	}

	public final int calcBaseDamage() {
		return (int) (getPower().getTotal() * (Math
				.random() + .7));
	}

	public int calcDamage(Mob m) {
		int i = calcBaseDamage();
		i -= m.getCharacterClass().calcReductions(i);
		return i;
	}

	public int calcReductions(int i) {
		return 0;
	}
	
	public int calcReductions(int i, Spell s){
		return 0;
	}

	public boolean canDodge(){
		return false;
	}
	
	public boolean canExecute(Command c) {
		return c.getClasses().size() == 0
				|| c.getClasses().contains(getName());
	}
	
	public final void gainExp(int i) {
		while(getExp().getCurrent() + i > getExp().getMaximum()){
			int temp = i+getExp().getCurrent()-getExp().getMaximum();
			getExp().increase(i);
			i = temp;
			if (getExp().atMax()) {
				levelUp();
			}
		}
		getExp().increase(i);
		if (getExp().atMax()) {
			levelUp();
		}
		if(myMob instanceof Player){
			Bot.xml.savePlayer((Player)myMob);
		}
		if (myMob.getTeam() != null) {
			myMob.sendState(EXP_GAIN, myMob);
			if(myMob.getTeam()!=null && myMob.getTeam().getInvolvement()!=null){
				myMob.getTeam().getInvolvement().bot.sendMessage(Bot.channel, Colors.BOLD+myMob
						+ " has gained " + i + " exp!");
			}
		}
	}

	public TreeSet<Restriction> getClassRestrictions() {
		return classRestrictions;
	}

	public RestrictedNumber getCon() {
		return con;
	}

	/**
	 * @return the conGain
	 */
	public int getConGain() {
		return conGain;
	}

	public long getElement() {
		return element;
	}

	public RestrictedNumber getExp() {
		return exp;
	}

	public RestrictedNumber getHp() {
		return hp;
	}

	/**
	 * @return the hpGain
	 */
	public int getHpGain() {
		return hpGain;
	}

	public RestrictedNumber getLevel() {
		return level;
	}

	public RestrictedNumber getLuck() {
		return luck;
	}

	/**
	 * @return the luckGain
	 */
	public int getLuckGain() {
		return luckGain;
	}

	/**
	 * @return the luckInterval
	 */
	public int getLuckInterval() {
		return luckInterval;
	}

	public RestrictedNumber getMagic() {
		return magic;
	}

	/**
	 * @return the magicGain
	 */
	public int getMagicGain() {
		return magicGain;
	}

	/**
	 * @return the myMob
	 */
	public Mob getMyMob() {
		return myMob;
	}

	public String getName() {
		return name;
	}

	public RestrictedNumber getOther() {
		return other;
	}

	/**
	 * @return the otherGain
	 */
	public int getOtherGain() {
		return otherGain;
	}

	public RestrictedNumber getPower() {
		return power;
	}

	/**
	 * @return the powerGain
	 */
	public int getPowerGain() {
		return powerGain;
	}

	public RestrictedNumber getSpeed() {
		return speed;
	}

	/**
	 * @return the speedGain
	 */
	public int getSpeedGain() {
		return speedGain;
	}

	public String getStats(){
		return getHp() + " " + getOther() + " "
				+ getLevel().toHalfString() + " " + getExp() + " "
				+ getPower().toHalfString() + " "
				+ getCon().toHalfString() + " "
				+ getMagic().toHalfString() + " "
				+ getSpeed().toHalfString() + " "
				+ getLuck().toHalfString();
	}

	public void heal() {
		getHp().toMaximum();
	}

	protected final void levelUp() {
		getLevel().increase(1);
		getExp().setCurrent(0);
		getExp().setMaximum(getExp().getMaximum() + (getLevel().getCurrent()*75));
		if(myMob.getTeam()!=null){
			myMob.getTeam().getInvolvement().bot.sendMessage(Bot.channel, Colors.BOLD+myMob
					+ " has leveled up (" + getLevel().getCurrent() + ")!");
			myMob.sendState(LEVEL_UP, myMob);
		}

		getHp().increaseAndBound(getHpGain());
		getOther().increaseAndBound(getOtherGain());
		getPower().increase(getPowerGain());
		getSpeed().increase(getSpeedGain());
		getMagic().increase(getMagicGain());
		getCon().increase(getConGain());

		if (getLevel().getCurrent() % getLuckInterval() == 0) {
			this.getLuck().increase(getLuckGain());
		}
	}

	public int modDamage(Mob m, int i){
		return i;
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

	@Override
	public void onAllyDamaged(Entity hitter, int damage) {

	}

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

	@Override
	public void onEnemyDamaged(Entity hitter, int damage) {

	}

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
		takeDamage(damage, (Mob) hitter, null);
	}

	@Override
	public void onRecieveCriticalHit(Entity hitter, Entity hit, Integer damage) {
		myMob.getTeam().getInvolvement().bot.sendMessage(Bot.channel,
				"The attack was critical!");
		onRecieveCriticalHit(hitter, hit, damage.intValue());

	}

	@Override
	public void onRecieveDamage(Entity tookDamage, Entity hitter, int damage) {
		takeDamage(damage, (Mob) tookDamage, null);

	}

	@Override
	public void onRecieveDamage(Entity tookDamage, Entity hitter, Integer damage) {
		// myMob.getTeam().getInvolvement().bot.sendMessage(Bot.channel,
		// "The attack went through (" + damage + ")!");
		onRecieveDamage(tookDamage, hitter, damage.intValue());

	}

	@Override
	public void onRecieveDamaged(Entity hitter, int damage) {
		myMob.getTeam().getInvolvement().bot.sendMessage(Bot.channel,
				"The attack went through2 (" + damage + ")!");
		takeDamage(damage, (Mob) hitter, null);

	}

	@Override
	public void onRecieveDamaged(Entity hitter, Integer damage) {
		// onRecieveDamaged(hitter, damage);

	}

	@Override
	public void onRecieveDeath(Entity killer, Entity target) {
		Mob m = (Mob) target;
		Mob k = (Mob) killer;

		for (Mob x : m.getTeam().getMembers()) {
			if(x.getCharacterClass().getHp().atMin()){continue;}
			x.getCharacterClass().gainExp(
					k.getExpGain() == 0 ? k.getCharacterClass().getLevel()
							.getCurrent() * 50 : k.getExpGain());
		}
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
		takeDamage(damage, (Mob)hitter, null);
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
		myMob.getTeam().getInvolvement().bot.sendMessage(Bot.channel, "The attack missed!");
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

	@Override
	public void onSelfDamaged(Entity hitter, int damage) {

	}

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
	public void onSelfTurnEnd(Entity turn) {

	}

	@Override
	public void onSelfTurnStart(Entity turn) {

	}

	public void setClassRestrictions(TreeSet<Restriction> classRestrictions) {
		this.classRestrictions = classRestrictions;
	}

	public void setCon(RestrictedNumber con) {
		this.con = con;
	}

	/**
	 * @param conGain the conGain to set
	 */
	public void setConGain(int conGain) {
		this.conGain = conGain;
	}

	public void setElement(long element) {
		this.element = element;
	}

	public void setExp(RestrictedNumber exp) {
		this.exp = exp;
	}

	/**
	 * @param hp
	 *            the hp to set
	 */
	public void setHp(RestrictedNumber hp) {
		this.hp = hp;
	}

	/**
	 * @param hpGain
	 *            the hpGain to set
	 */
	public void setHpGain(int hpGain) {
		this.hpGain = hpGain;
	}

	/**
	 * @param level
	 *            the level to set
	 */
	public void setLevel(RestrictedNumber level) {
		this.level = level;
	}

	/**
	 * @param luck
	 *            the luck to set
	 */
	public void setLuck(RestrictedNumber luck) {
		this.luck = luck;
	}
	
	/**
	 * @param luckGain the luckGain to set
	 */
	public void setLuckGain(int luckGain) {
		this.luckGain = luckGain;
	}	
	
	/**
	 * @param luckInterval
	 *            the luckInterval to set
	 */
	public void setLuckInterval(int luckInterval) {
		this.luckInterval = luckInterval;
	}

	/**
	 * @param magic
	 *            the magic to set
	 */
	public void setMagic(RestrictedNumber magic) {
		this.magic = magic;
	}

	/**
	 * @param magicGain
	 *            the magicGain to set
	 */
	public void setMagicGain(int magicGain) {
		this.magicGain = magicGain;
	}

	/**
	 * @param myMob
	 *            the myMob to set
	 */
	public void setMyMob(Mob myMob) {
		this.myMob = myMob;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * @param other
	 *            the other to set
	 */
	public void setOther(RestrictedNumber other) {
		this.other = other;
	}

	/**
	 * @param otherGain
	 *            the otherGain to set
	 */
	public void setOtherGain(int otherGain) {
		this.otherGain = otherGain;
	}

	/**
	 * @param power
	 *            the power to set
	 */
	public void setPower(RestrictedNumber power) {
		this.power = power;
	}

	/**
	 * @param powerGain
	 *            the powerGain to set
	 */
	public void setPowerGain(int powerGain) {
		this.powerGain = powerGain;
	}

	/**
	 * @param speed
	 *            the speed to set
	 */
	public void setSpeed(RestrictedNumber speed) {
		this.speed = speed;
	}

	/**
	 * @param speedGain
	 *            the speedGain to set
	 */
	public void setSpeedGain(int speedGain) {
		this.speedGain = speedGain;
	}

	protected abstract void setStats();

	//TODO: Make it not display anything if damage is 0
	public void takeDamage(int i, Mob hitter, Spell source) {
		if(i == 0) return;
		
		i -= calcReductions(i,source);
		if (source != null && (source.getElement() & shared.Elements.HEAL) > 0) {
			hp.increase(i);
			myMob.getTeam().getInvolvement().bot.sendMessage(Bot.channel, Colors.BOLD+myMob
					+ " was healed for " + i + "!");
			myMob.sendState(TAKE_HEALS, myMob, hitter, i);
		} else {
			hp.decrease(i);
			myMob.getTeam().getInvolvement().bot.sendMessage(Bot.channel, Colors.BOLD+myMob
					+ " took " + i + " damage!");
			myMob.sendState(TAKE_DAMAGE, myMob, hitter, i);
		}
		
		if (hp.atMin()) {
			myMob.sendState(DEATH, myMob, hitter);
		} else if (myMob instanceof Monster) {
			if (getHp().lessThanPercent(50)) {
				myMob.getTeam().getInvolvement().bot.sendMessage(Bot.channel,
						myMob + " is bleeding!");
			} else if (getHp().lessThanPercent(10)) {
				myMob.getTeam().getInvolvement().bot.sendMessage(Bot.channel,
						myMob + " is in critical condition!");
			}
		}

	}
	

	@Override
	public String toString() {
		return getName();
	}
}
