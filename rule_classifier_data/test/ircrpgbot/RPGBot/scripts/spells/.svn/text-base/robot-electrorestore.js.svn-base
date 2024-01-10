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
	spell.setName("ElectroRestore");
	spell.setDuration(0);
	spell.setType("direct");
	spell.setBaseDamage(20);
	spell.setDescription("Regain energy by using your charge.");
	spell.setElement(Elements.HEAL|Elements.BUFF);
	return [
		new Spell.Restriction("Robot", 1, 1)
	];
}

function cast(caster, target) {
	var charge_percent = (caster.getCharacterClass().getOther().getTotal() / caster.getCharacterClass().getOther().getMaximum());
	var heal = spell.getBaseDamage() + (caster.getCharacterClass().getHp().getTotal() / (caster.getCharacterClass().getPower().getTotal() - 2)) * charge_percent;
	bot.sendMessage(Bot.channel,caster + " restores their health using their charge...");
	caster.takeDamage(heal,caster,spell);
	caster.getCharacterClass().getOther().toMinimum();
}