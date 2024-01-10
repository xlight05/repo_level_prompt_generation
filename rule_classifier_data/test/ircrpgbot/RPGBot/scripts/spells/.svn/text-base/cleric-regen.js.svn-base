importPackage(Packages.classes);
importPackage(Packages.shared);
importPackage(Packages.entities);
importPackage(Packages.spells);
importPackage(Packages.bot);

function init() { 
	spell.setName("Regen");
	spell.setType("direct");
	spell.setBaseDamage(25);
	spell.setDescription("Cast a regenerative spell on your target.");
	spell.setElement(Elements.HEAL|Elements.BUFF);

	return [
			new Spell.Restriction("Cleric", 25, 3),
	];
}

function cast(caster, target) {
	bot.sendMessage(Bot.channel, target+ " is surrounded by a white light!");
	spell.setDuration(spell.rand(3,6));
}

function calcDamage(target) {
	var magic = spell.getCastedClass().getMagic().getTotal();
	return spell.getBaseDamage()+spell.rand(magic, magic*2);
}

function preTick() {
	if(spell.duration>0){
		bot.sendMessage(Bot.channel, spell.getAffecting()+ " was healed!");
		spell.getAffecting().takeDamage(spell.getBaseDamage(), spell.getCastedClass().getMyMob(), spell);
	}
}

function onDurationOver() {
	bot.sendMessage(Bot.channel, spell.getAffecting() + " is no longer glowing.");
}