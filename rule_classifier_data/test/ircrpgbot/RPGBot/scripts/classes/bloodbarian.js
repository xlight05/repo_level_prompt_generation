importPackage(Packages.classes);
importPackage(Packages.shared);
importPackage(Packages.entities);
importPackage(Packages.spells);
importPackage(Packages.bot);
importClass(java.util.ArrayList);

function init() { 
	return [
			new CharacterClass.Restriction("Barbarian", 5)
	];
}

function setStats() {

	Class.setHp(new RestrictedNumber("Health", 0, 414, 414));
	Class.setOther(new RestrictedNumber("Mana", 0, 0, 0));
	Class.setLevel(new RestrictedNumber("Level", 0, 1, 999));
	Class.setSpeed(new RestrictedNumber("Speed", 0, 0, 999));
	Class.setMagic(new RestrictedNumber("Magic", 0, 0, 999));
	Class.setPower(new RestrictedNumber("Power", 0, 25, 999));
	Class.setLuck(new RestrictedNumber("Luck", 0, 0, 999));
	Class.setExp(new RestrictedNumber("Exp", 0, 0, 300));
	Class.setCon(new RestrictedNumber("Constitution", 0, 5, 999));

	Class.setLuckGain(0);
	Class.setHpGain(157);
	Class.setPowerGain(18);
	Class.setSpeedGain(0);
	Class.setMagicGain(0);
	Class.setConGain(4);
	Class.setOtherGain(0);
	
	Class.setName("Bloodbarian");

}

function onRecieveHealed(user, target, healed) {
	Class.getHp().decrease(healed);
}

function onSelfTurnEnd(){
	Class.takeDamage(Class.getHp().getTotal()*0.1, Class.getMyMob(), null);
}

function onSelfDamage(target, hitter, damage){
	Class.getHp().increase(damage*0.75);
}

function modDamage(mob, i){
	return i*1.5;
}