importPackage(Packages.classes);
importPackage(Packages.shared);
importPackage(Packages.entities);
importPackage(Packages.spells);
importPackage(Packages.bot);

function init() { 
	spell.setName("Recover");
	spell.setDuration(0);
	spell.setType("direct");
	spell.setDescription("Remove your target of any status effects and debuffs!");
	spell.setElement(Elements.HEAL);

	return [
			new Spell.Restriction("Druid", 20, 1)
	];
}

function cast(caster, target) {
	var spells = target.getSpellsByElement(Elements.DEBUFF);
	for(var x=0; x<spells.length; x++) {
		spells[x].cleanUp();
	}
	bot.sendMessage(Bot.channel, target+ " glows momentarily, restored of their debuffs!");
}

function calcDamage(target) {
	return spell.getBaseDamage();
}

function preTick() {}