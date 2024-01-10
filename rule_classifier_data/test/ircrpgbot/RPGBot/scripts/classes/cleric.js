importPackage(Packages.classes);
importPackage(Packages.shared);
importPackage(Packages.entities);
importPackage(Packages.spells);
importPackage(Packages.bot);
importClass(java.util.ArrayList);

function init() { 
	return [			
		new CharacterClass.Restriction("Mage", 2),
		new CharacterClass.Restriction("Fighter", 2)
	];
}

function setStats() {

	Class.setHp(new RestrictedNumber("Health", 0, 150, 150));
	Class.setOther(new RestrictedNumber("Mana", 0, 45, 45));
	Class.setLevel(new RestrictedNumber("Level", 0, 1, 999));
	Class.setSpeed(new RestrictedNumber("Speed", 0, 3, 999));
	Class.setMagic(new RestrictedNumber("Magic", 0, 6, 999));
	Class.setPower(new RestrictedNumber("Power", 0, 6, 999));
	Class.setLuck(new RestrictedNumber("Luck", 0, 1, 999));
	Class.setExp(new RestrictedNumber("Exp", 0, 0, 100));
	Class.setCon(new RestrictedNumber("Constitution", 0, 1, 999));

	Class.setLuckInterval(5);
	Class.setHpGain(25);
	Class.setPowerGain(2);
	Class.setSpeedGain(1);
	Class.setMagicGain(2);
	Class.setConGain(2);
	Class.setOtherGain(20);
	
	Class.setName("Cleric");

}

function heal() {
	Class.getOther().toMaximum();
}

function onSelfTurnStart(turn) {
	Class.getOther().increase((Class.getMagic().getTotal()/1.5));
	Class.getHp().increase((Class.getMagic().getTotal()/4));
}