importPackage(Packages.classes);
importPackage(Packages.shared);
importPackage(Packages.entities);
importPackage(Packages.spells);
importPackage(Packages.bot);

function init() { 
	spell.setName("VineWrap");
	spell.setBaseDamage(10);
	spell.setDuration(0);
	spell.setType("direct");
	spell.setDescription("Attack a target, with a chance of entangling them!");
	spell.setElement(Elements.EARTH);

	return [
			new Spell.Restriction("Druid", 20, 1)
	];
}

function cast(caster, target) {
	target.takeDamage(calcDamage(target), caster, spell);
	if(spell.prob(20)){
		bot.sendMessage(Bot.channel, target+ " was trapped in vines!");
		spell.setDuration(spell.rand(2,3));
		spell.setBaseDamage(0);
	}
}

function calcDamage(target) {
	var magic = spell.getCastedClass().getMagic().getTotal();
	return spell.rand(1, spell.getBaseDamage()+magic*1.5);
}

function preTick() {
	if(spell.duration>0){
		bot.sendMessage(Bot.channel, spell.getAffecting() + " is trapped in vines!");
		spell.getAffecting().getTeam().getInvolvement().changeTurn();
	}
}