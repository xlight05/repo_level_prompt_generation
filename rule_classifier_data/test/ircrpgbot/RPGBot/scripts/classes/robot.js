importPackage(Packages.classes);
importPackage(Packages.shared);
importPackage(Packages.entities);
importPackage(Packages.spells);
importPackage(Packages.bot);
importClass(java.util.ArrayList);

/*
This was made by KR. Just saying.
*/

function init() {
	return [
		new CharacterClass.Restriction("Mage",2)
	];
}

function setStats() {
	Class.setHp(new RestrictedNumber("Health", 0, 185, 185));
	Class.setOther(new RestrictedNumber("Charge", 0, 0, 100));
	Class.setLevel(new RestrictedNumber("Level", 0, 1, 999));
	Class.setExp(new RestrictedNumber("Exp", 0, 0, 100));
	Class.setPower(new RestrictedNumber("Power", 0, 4, 999));
	Class.setCon(new RestrictedNumber("Constitution", 0, 0, 999));
	Class.setMagic(new RestrictedNumber("ChargeUpPower", 0, 17, 999));
	Class.setSpeed(new RestrictedNumber("Speed", 0, 7, 99));
	Class.setLuck(new RestrictedNumber("Luck", 0, 1, 999));
	

	Class.setLuckInterval(9);
	Class.setHpGain(50);
	Class.setPowerGain(4);
	Class.setSpeedGain(2);
	Class.setMagicGain(1);
	Class.setOtherGain(20);
	Class.setConGain(2);
	
	Class.setName("Robot");
}

function heal() {
	Class.getOther().toMinimum();
}

function calcSpellReductions(dmg,spell) {
	var bot = Class.getMyMob().getTeam().getInvolvement().bot;
	if((spell.getElement() & Elements.WATER) > 0) {
		bot.sendMessage(Bot.channel, "Oh no, water! " + Class.getMyMob() + "'s only weakness!");
		return -dmg;
	}
	if((spell.getElement() & Elements.ELEC) > 0) {
		bot.sendMessage(Bot.channel, Class.getMyMob() + " gets charged up " + dmg + " units by the electricity!");
		Class.getOther().increase(dmg);
		if(Class.getOther().atMax()) {
			bot.sendMessage(Bot.channel, Class.getMyMob() + " is fully charged up!");
		}
		return dmg;
	}
	return 0;
}

function onRecieveSkillUse(user,me,skill) {
	var bot = me.getTeam().getInvolvement().bot;
	var spells = me.getSpellsByElement(Elements.ELEC|Elements.DEBUFF);
	for(var i = 0; i<spells.length; i++) {
		if((spells[i].getElement() & (Elements.ELEC|Elements.DEBUFF)) == (Elements.ELEC|Elements.DEBUFF)) {
			spells[i].cleanUp();
		}
	}
	if(spells.length > 0) {
		bot.sendMessage(Bot.channel, "All Electricity-elemented debuffs dissipates from " + me + "...");
	}
}