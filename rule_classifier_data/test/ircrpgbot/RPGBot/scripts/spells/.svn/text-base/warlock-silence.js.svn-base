importPackage(Packages.classes);
importPackage(Packages.shared);
importPackage(Packages.entities);
importPackage(Packages.spells);
importPackage(Packages.bot);

function init() { 
	spell.setName("ParaStrike");
	spell.setType("direct");
	spell.setBaseDamage(30);
	spell.setDuration(0);
	spell.setDescription("Strike with paralyzing force.");
	spell.setElement(Elements.ELEC|Elements.DEBUFF);

	return [
			new Spell.Restriction("Warlock", 70, 3)
	];
}

function cast(caster, target) {
	bot.sendMessage(Bot.channel, caster + " sings an unholy song!");
	if(spell.prob(100)){
		bot.sendMessage(Bot.channel, target+ " was silenced!");
		spell.setDuration(spell.rand(1,3));
	}
}

function calcDamage(target) {
	return 0;
}

function postTick() {}

function onPreCast() {
	bot.sendMessage(Bot.channel, spell.getAffecting()+ " is silent!");
	spell.getAffecting().getTeam().getInvolvement().changeTurn();
}