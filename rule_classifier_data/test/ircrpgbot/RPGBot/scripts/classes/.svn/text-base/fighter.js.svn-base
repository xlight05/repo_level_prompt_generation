importPackage(Packages.classes);
importPackage(Packages.shared);
importPackage(Packages.entities);
importPackage(Packages.spells);
importPackage(Packages.bot);
importClass(java.util.ArrayList);

function init() { 
	return [
	];
}

function setStats() {
		
	Class.setHp(new RestrictedNumber("Health", 0, 200, 200));
	Class.setOther(new RestrictedNumber("Mana", 0, 0, 0));
	Class.setLevel(new RestrictedNumber("Level", 0, 1, 999));
	Class.setSpeed(new RestrictedNumber("Speed", 0, 5, 999));
	Class.setMagic(new RestrictedNumber("Magic", 0, 3, 999));
	Class.setPower(new RestrictedNumber("Power", 0, 10, 999));
	Class.setLuck(new RestrictedNumber("Luck", 0, 1, 999));
	Class.setExp(new RestrictedNumber("Exp", 0, 0, 100));
	Class.setCon(new RestrictedNumber("Constitution", 0, 7, 999));

	Class.setLuckInterval(3);
	Class.setHpGain(75);
	Class.setPowerGain(7);
	Class.setSpeedGain(2);
	Class.setMagicGain(1);
	Class.setOtherGain(0);
	Class.setConGain(6);
	
	Class.setName("Fighter");

}

function heal() {
	Class.getOther().toMaximum();
}

function onSelfTurnStart(turn) {
	Class.getHp().increase(Class.getMagic().getTotal());
}

function modDamage(mob, i){
	return i*2.5;
}