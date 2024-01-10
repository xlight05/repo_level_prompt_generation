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

	Class.setHp(new RestrictedNumber("Health", 0, 100, 100));
	Class.setOther(new RestrictedNumber("Energy", 0, 100, 100));
	Class.setLevel(new RestrictedNumber("Level", 0, 1, 999));
	Class.setSpeed(new RestrictedNumber("Speed", 0, 10, 999));
	Class.setMagic(new RestrictedNumber("Magic", 0, 2, 999));
	Class.setPower(new RestrictedNumber("Power", 0, 5, 999));
	Class.setLuck(new RestrictedNumber("Luck", 0, 5, 999));
	Class.setExp(new RestrictedNumber("Exp", 0, 0, 100));
	Class.setCon(new RestrictedNumber("Constitution", 0, 3, 999));

	Class.setLuckInterval(1);
	Class.setLuckGain(2);
	Class.setHpGain(28);
	Class.setPowerGain(4);
	Class.setSpeedGain(4);
	Class.setMagicGain(1);
	Class.setConGain(1);
	Class.setOtherGain(10);
	
	Class.setName("Rogue");

}

function heal() {
	Class.getOther().toMaximum();
}

function onSelfTurnStart(turn) {
	Class.getOther().increase(Class.getOther().getTotal()*0.15);
}

function canDodge() {
	//return prob(10)
}