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

	Class.setHp(new RestrictedNumber("Health", 0, 60, 60));
	Class.setOther(new RestrictedNumber("Mana", 0, 200, 200));
	Class.setLevel(new RestrictedNumber("Level", 0, 1, 999));
	Class.setSpeed(new RestrictedNumber("Speed", 0, 2, 999));
	Class.setMagic(new RestrictedNumber("Magic", 0, 11, 999));
	Class.setPower(new RestrictedNumber("Power", 0, 1, 999));
	Class.setLuck(new RestrictedNumber("Luck", 0, 4, 999));
	Class.setExp(new RestrictedNumber("Exp", 0, 0, 100));
	Class.setCon(new RestrictedNumber("Constitution", 0, 3, 999));

	Class.setLuckInterval(5);
	Class.setHpGain(14);
	Class.setPowerGain(2);
	Class.setSpeedGain(2);
	Class.setMagicGain(5);
	Class.setOtherGain(44);
	Class.setConGain(2);
	
	Class.setName("Warlock");

}

function heal() {
	Class.getOther().toMaximum();
}

function onSelfTurnStart(turn) {
	Class.getOther().increase(Class.getMagic().getTotal()*1.5);
}