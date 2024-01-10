importPackage(Packages.classes);
importPackage(Packages.shared);
importPackage(Packages.entities);
importPackage(Packages.spells);
importPackage(Packages.bot);

function init() { 
	spell.setName("Drain");
	spell.setDuration(0);
	spell.setType("direct");
	spell.setBaseDamage(5);
	spell.setDescription("Drain health from an enemy!");
	spell.setElement(Elements.EARTH);

	return [
			new Spell.Restriction("Druid", 15, 4),
	];
}

function cast(caster, target) {
	target.takeDamage(calcDamage(target), caster, spell);
	spell.setElement(Elements.HEAL);
	caster.takeDamage(calcDamage(target) * 0.75, caster, spell);
	bot.sendMessage(Bot.channel, target+ " had their health drained!");
}

function calcDamage(target) {
	var magic = spell.getCastedClass().getMagic().getTotal();
	return spell.getBaseDamage()+spell.rand(magic, magic*3);
}