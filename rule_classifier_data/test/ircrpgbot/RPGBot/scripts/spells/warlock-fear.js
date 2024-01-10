importPackage(Packages.classes);
importPackage(Packages.shared);
importPackage(Packages.entities);
importPackage(Packages.spells);
importPackage(Packages.bot);

function init() { 
	spell.setName("Fear");
	spell.setDuration(0);
	spell.setType("direct");
	spell.setBaseDamage(100);
	spell.setDescription("Inflict fear into opponents.");
	spell.setElement(Elements.FORCE|Elements.DEBUFF);

	return [
			new Spell.Restriction("Warlock", 35, 2),
	];
}

function cast(caster, target) {
	target.takeDamage(calcDamage(target), caster, spell);
	if(spell.prob(spell.getBaseDamage())){
		bot.sendMessage(Bot.channel, target+ " was scared to death!");
		spell.setDuration(1);
	}
}

function calcDamage(target) {
	return 0;
}

function preTick() {
	if(spell.duration>0){
		bot.sendMessage(Bot.channel, spell.getAffecting() + " is frozen in fear!");
		spell.getAffecting().getTeam().getInvolvement().changeTurn();
	}
}