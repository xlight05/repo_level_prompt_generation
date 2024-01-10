importPackage(Packages.classes);
importPackage(Packages.shared);
importPackage(Packages.entities);
importPackage(Packages.spells);
importPackage(Packages.bot);

function init() { 
	spell.setName("Stab");
	spell.setType("direct");
	spell.setBaseDamage(20);
	spell.setDuration(2);
	spell.setDescription("Stab a foe.");
	spell.setElement(Elements.FORCE);

	return [
			new Spell.Restriction("Rogue", 10, 1),
	];
}

function cast(caster, target) {
	bot.sendMessage(Bot.channel, caster + " stabs at "+target+"!");
	target.takeDamage(calcDamage(target), caster, spell);
	if(target.affectedBySpell("Jab")) {
		bot.sendMessage(Bot.channel, caster + " executed Stab & Jab!");
		target.takeDamage(calcDamage(target), caster, spell);
	}
}

function calcDamage(target) {
	return spell.getBaseDamage();
}

function postTick() {}