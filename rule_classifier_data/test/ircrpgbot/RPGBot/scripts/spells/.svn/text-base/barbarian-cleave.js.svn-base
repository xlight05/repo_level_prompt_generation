importPackage(Packages.classes);
importPackage(Packages.shared);
importPackage(Packages.entities);
importPackage(Packages.spells);
importPackage(Packages.bot);

function init() { 
	spell.setName("Cleave");
	spell.setType("direct");
	spell.setBaseDamage(0);
	spell.setDuration(0);
	spell.setDescription("Strike with great force, and negate defense.");
	spell.setElement(Elements.FORCE);

	return [
			new Spell.Restriction("Barbarian", 25, 2),
			new Spell.Restriction("Bloodbarian","hp", 100, 2)
	];
}

function cast(caster, target) {
	spell.setBaseDamage(caster.getCharacterClass().getPower().getTotal());
	bot.sendMessage(Bot.channel, caster + " strikes at "+target+" with great force!");
	target.takeDamage(calcDamage(target), caster, spell);
}

function calcDamage(target) {
	return spell.getBaseDamage()*3 + (target.getCharacterClass().getCon().getTotal());
}