importPackage(Packages.classes);
importPackage(Packages.shared);
importPackage(Packages.entities);
importPackage(Packages.spells);
importPackage(Packages.bot);
importClass(java.util.ArrayList);

function init() { 
	return [
			new CharacterClass.Restriction("Mage", 5)
	];
}

function setStats() {

	Class.setHp(new RestrictedNumber("Health", 0, 120, 120));
	Class.setOther(new RestrictedNumber("Spirit", 0, 50, 70));
	Class.setLevel(new RestrictedNumber("Level", 0, 1, 999));
	Class.setSpeed(new RestrictedNumber("Speed", 0, 2, 999));
	Class.setMagic(new RestrictedNumber("Magic", 0, 9, 999));
	Class.setPower(new RestrictedNumber("Power", 0, 3, 999));
	Class.setLuck(new RestrictedNumber("Luck", 0, 2, 999));
	Class.setExp(new RestrictedNumber("Exp", 0, 0, 100));
	Class.setCon(new RestrictedNumber("Constitution", 0, 1, 999));

	Class.setLuckInterval(6);
	Class.setHpGain(7);
	Class.setPowerGain(1);
	Class.setSpeedGain(4);
	Class.setMagicGain(3);
	Class.setOtherGain(30);
	Class.setConGain(1);
	
	Class.setName("Druid");

}

function heal() {
	Class.getOther().setCurrent(Class.getOther().getMaximum() / 2);
}

function onSelfTurnStart(turn) {
	var x = Class.getMagic().getTotal() * 2 * ( ( Class.getHp().getCurrent() / Class.getHp().getMaximum() ) + 1);
	Class.getOther().increase(x);
}
function onRecieveCriticalHit( hitter,  target,  damage) {
	var x = damage * 2 / Class.getMagic().getTotal()
	Class.getOther().decrease(x);
}

function onRecieveDamage( tookDamage,  hitter,  damage) {
	var x = damage * 2 / Class.getMagic().getTotal()
	Class.getOther().decrease(x);
}
function onRecieveHealed(user, target, healed) {
	Class.getOther().increase(healed / 2);
}
function calcReductions(damage) {
	var spirit = Class.getOther().getTotal();
	if(Class.affectedBySpell("NatureGuard")) {
		if(damage > spirit) { return damage-spirit; Class.getOther().decrease(damage-spirit);}
		else{ return damage; Class.getOther().decrease(damage);}
	}
}