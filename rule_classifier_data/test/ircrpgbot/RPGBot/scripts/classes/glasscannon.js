importPackage(Packages.classes);
importPackage(Packages.shared);
importPackage(Packages.entities);
importPackage(Packages.spells);
importPackage(Packages.bot);
importClass(java.util.ArrayList);

function init() { 
	return [			
		new CharacterClass.Restriction("Mage", 5),
	];
}

function setStats() {

	Class.setHp(new RestrictedNumber("Health", 0, 10, 10));
	Class.setOther(new RestrictedNumber("Mana", 0, 450, 450));
	Class.setLevel(new RestrictedNumber("Level", 0, 1, 999));
	Class.setSpeed(new RestrictedNumber("Speed", 0, 1, 999));
	Class.setMagic(new RestrictedNumber("Magic", 0, 40, 999));
	Class.setPower(new RestrictedNumber("Power", 0, 1, 999));
	Class.setLuck(new RestrictedNumber("Luck", 0, 1, 999));
	Class.setExp(new RestrictedNumber("Exp", 0, 0, 100));
	Class.setCon(new RestrictedNumber("Constitution", 0, 1, 999));

	Class.setLuckInterval(2);
	Class.setHpGain(10);
	Class.setPowerGain(1);
	Class.setSpeedGain(12);
	Class.setMagicGain(10);
	Class.setConGain(1);
	Class.setOtherGain(41);
	
	Class.setName("Glass Cannon");

}

function heal() {
	Class.getOther().toMaximum();
}

function onSelfTurnStart(turn) {
	Class.getOther().increase((Class.getMagic().getTotal()));
}

function canDodge(){
//prob(50) of dodging attacks
}