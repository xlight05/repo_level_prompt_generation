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

	Class.setHp(new RestrictedNumber("Health", 0, 250, 250));
	Class.setOther(new RestrictedNumber("Mana", 0, 245, 245));
	Class.setLevel(new RestrictedNumber("Level", 0, 1, 999));
	Class.setSpeed(new RestrictedNumber("Speed", 0, 6, 999));
	Class.setMagic(new RestrictedNumber("Magic", 0, 6, 999));
	Class.setPower(new RestrictedNumber("Power", 0, 6, 999));
	Class.setLuck(new RestrictedNumber("Luck", 0, 6, 999));
	Class.setExp(new RestrictedNumber("Exp", 0, 0, 100));
	Class.setCon(new RestrictedNumber("Constitution", 0, 6, 999));

	Class.setLuckInterval(1);
	Class.setHpGain(41);
	Class.setPowerGain(3);
	Class.setSpeedGain(3);
	Class.setMagicGain(3);
	Class.setConGain(3);
	Class.setOtherGain(41);
	
	Class.setName("Bard");

}

function heal() {
	Class.getOther().toMaximum();
}

function onSelfTurnStart(turn) {
	Class.getOther().increase((Class.getMagic().getTotal()/3));
	Class.getHp().increase((Class.getMagic().getTotal()/3));
}