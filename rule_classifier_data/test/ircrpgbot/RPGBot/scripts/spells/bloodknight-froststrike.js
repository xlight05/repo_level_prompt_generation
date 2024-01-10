importPackage(Packages.classes);
importPackage(Packages.shared);
importPackage(Packages.entities);
importPackage(Packages.spells);
importPackage(Packages.bot);

function init() { 
	spell.setName("FrostStrike");
	spell.setType("direct");
	spell.setBaseDamage(30);
	spell.setDuration(0);
	spell.setDescription("Strike with freezing force.");
	spell.setElement(Elements.ICE|Elements.WATER|Elements.DEBUFF);

	return [
			new Spell.Restriction("Blood Knight", 35, 4)
	];
}

function cast(caster, target) {
	bot.sendMessage(Bot.channel, caster + " strikes at "+target+" with freezing force!");
	caster.attack(target);
	if(spell.prob(60)){
		bot.sendMessage(Bot.channel, target+ " was frozen!");
		spell.setDuration(spell.rand(1,3));
	}
}

function calcDamage(target) {
	var magic = spell.getCastedClass().getMagic().getTotal();
	return spell.getBaseDamage()+spell.rand(magic/2, magic);
}

function preTick() {
	if(spell.duration>0){
		bot.sendMessage(Bot.channel, spell.getAffecting() + " is frozen!");
		spell.getAffecting().getTeam().getInvolvement().changeTurn();
	}
}