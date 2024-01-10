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

	Class.setHp(new RestrictedNumber("Health", 0, 120, 120));
	Class.setOther(new RestrictedNumber("Mana", 0, 70, 70));
	Class.setLevel(new RestrictedNumber("Level", 0, 1, 999));
	Class.setSpeed(new RestrictedNumber("Speed", 0, 2, 999));
	Class.setMagic(new RestrictedNumber("Magic", 0, 9, 999));
	Class.setPower(new RestrictedNumber("Power", 0, 3, 999));
	Class.setLuck(new RestrictedNumber("Luck", 0, 2, 999));
	Class.setExp(new RestrictedNumber("Exp", 0, 0, 100));
	Class.setCon(new RestrictedNumber("Constitution", 0, 1, 999));

	Class.setLuckInterval(6);
	Class.setHpGain(10);
	Class.setPowerGain(1);
	Class.setSpeedGain(1);
	Class.setMagicGain(5);
	Class.setOtherGain(30);
	Class.setConGain(1);
	
	Class.setName("Mage");

}

function heal() {
	Class.getOther().toMaximum();
}

function onSelfTurnStart(turn) {
	Class.getOther().increase(Class.getMagic().getTotal());
}