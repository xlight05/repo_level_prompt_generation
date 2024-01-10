importPackage(Packages.classes);
importPackage(Packages.shared);
importPackage(Packages.entities);
importPackage(Packages.spells);
importPackage(Packages.bot);
importClass(java.util.ArrayList);

function init() { 
	return [
			new CharacterClass.Restriction("Bloodbarian", 1),
			new CharacterClass.Restriction("Fighter", 4)
	];
}

function setStats() {

	Class.setHp(new RestrictedNumber("Health", 0, 500, 50));
	Class.setOther(new RestrictedNumber("Mana", 0, 0, 0));
	Class.setLevel(new RestrictedNumber("Level", 0, 1, 999));
	Class.setSpeed(new RestrictedNumber("Speed", 0, 0, 999));
	Class.setMagic(new RestrictedNumber("Magic", 0, 0, 999));
	Class.setPower(new RestrictedNumber("Power", 0, 40, 999));
	Class.setLuck(new RestrictedNumber("Luck", 0, 0, 999));
	Class.setExp(new RestrictedNumber("Exp", 0, 0, 500));
	Class.setCon(new RestrictedNumber("Constitution", 0, 0, 999));

	Class.setLuckGain(0);
	Class.setHpGain(212);
	Class.setPowerGain(27);
	Class.setSpeedGain(0);
	Class.setMagicGain(0);
	Class.setConGain(0);
	Class.setOtherGain(0);
	
	Class.setName("Juggernaut");

}

function onSelfTurnEnd(){
	Class.takeDamage(Class.getHp().getMaximum()*0.215, Class.getMyMob(), null);
}

function modDamage(mob, i){
	return i*2.5;
}