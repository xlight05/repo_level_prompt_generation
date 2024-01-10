importPackage(Packages.classes);
importPackage(Packages.shared);
importPackage(Packages.entities);
importPackage(Packages.spells);
importPackage(Packages.bot);
importClass(java.util.ArrayList);

function init() { 
	return [
			new CharacterClass.Restriction("Fighter", 2)
	];
}
//TODO: gain rage on ally/self kill, lose on enemy kill (figure out how to intercept death?)
function setStats() {

	Class.setHp(new RestrictedNumber("Health", 0, 225, 225));
	Class.setOther(new RestrictedNumber("Rage", 0, 0, 100));
	Class.setLevel(new RestrictedNumber("Level", 0, 1, 999));
	Class.setSpeed(new RestrictedNumber("Speed", 0, 1, 999));
	Class.setMagic(new RestrictedNumber("Magic", 0, 0, 999));
	Class.setPower(new RestrictedNumber("Power", 0, 6, 999));
	Class.setLuck(new RestrictedNumber("Luck", 0, 1, 999));
	Class.setExp(new RestrictedNumber("Exp", 0, 0, 100));
	Class.setCon(new RestrictedNumber("Constitution", 0, 5, 999));

	Class.setLuckInterval(8);
	Class.setHpGain(50);
	Class.setPowerGain(6);
	Class.setSpeedGain(3);
	Class.setMagicGain(0);
	Class.setConGain(4);
	Class.setOtherGain(3);
	
	Class.setName("Barbarian");

}

function onAllyCriticalHit( hitter,  target,  damage) {
	Class.getOther().increase(15);
}

function onRecieveCriticalHit( hitter,  target,  damage) {
	Class.getOther().increase(30);
}

function onRecieveDamage( tookDamage,  hitter,  damage) {
	Class.getOther().increase(10);
}

function onSelfDamaged( tookDamage, hitter, damage) {
	Class.getOther().increase(10);
}

function onSelfDamage( tookDamage, hitter, damage) {
	Class.getOther().increase(10);
}

function onRecieveHealed(user, target, healed) {
	Class.getOther().decrease(healed);
}

function onRecieveMiss( hitter,  target) {
	Class.getOther().increase(15);
}

function onSelfMiss( hitter,  target) {
	Class.getOther().decrease(25);
}

function onSelfTurnEnd() {
	Class.getOther().decrease(5);
}

function calcReductions(damage) {
	return damage*0.05;
}

function heal() {
	Class.getOther().toMinimum();
}

function modDamage(mob, i){
	return i*1.5;
}