importPackage(Packages.classes);
importPackage(Packages.shared);
importPackage(Packages.entities);
importPackage(Packages.spells);
importPackage(Packages.bot);
importClass(java.util.ArrayList);

function init() { 
	return [
			new CharacterClass.Restriction("Fighter", 5)
	];
}

function setStats() {

	Class.setHp(new RestrictedNumber("Health", 0, 250, 250));
	Class.setOther(new RestrictedNumber("Mana", 0, 100, 100));
	Class.setLevel(new RestrictedNumber("Level", 0, 1, 999));
	Class.setSpeed(new RestrictedNumber("Speed", 0, 3, 999));
	Class.setMagic(new RestrictedNumber("Magic", 0, 5, 999));
	Class.setPower(new RestrictedNumber("Power", 0, 7, 999));
	Class.setLuck(new RestrictedNumber("Luck", 0, 2, 999));
	Class.setExp(new RestrictedNumber("Exp", 0, 0, 1000));
	Class.setCon(new RestrictedNumber("Constitution", 0, 3, 999));

	Class.setLuckInterval(2);
	Class.setHpGain(37);
	Class.setPowerGain(4);
	Class.setSpeedGain(1);
	Class.setMagicGain(3);
	Class.setOtherGain(27);
	Class.setConGain(2);
	
	Class.setName("Blood Knight");

}

function heal() {
	Class.getOther().toMaximum();
}

function onSelfTurnStart(turn) {
	Class.getOther().increase(Class.getMagic().getTotal()+Class.getCon().getTotal());
}