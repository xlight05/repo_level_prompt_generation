importPackage(Packages.classes);
importPackage(Packages.shared);
importPackage(Packages.entities);
importPackage(Packages.spells);
importPackage(Packages.bot);

function init() { 
	spell.setName("DoubleTap");
	spell.setType("direct");
	spell.setBaseDamage(0);
	spell.setDuration(0);
	spell.setDescription("Strike at an opponent twice.");
	spell.setElement(Elements.FORCE);

	return [
			new Spell.Restriction("Barbarian", 30, 3),
			new Spell.Restriction("Bloodbarian", "hp",150, 3)
	];
}

function cast(caster, target) {
	bot.sendMessage(Bot.channel, caster + " strikes at "+target+" twice!");
	caster.attack(target);
	caster.attack(target);
}

function calcDamage(target) {
	return spell.getBaseDamage();
}