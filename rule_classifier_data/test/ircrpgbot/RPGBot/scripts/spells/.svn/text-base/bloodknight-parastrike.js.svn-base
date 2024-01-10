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
			new Spell.Restriction("Blood Knight", 35, 2)
	];
}

function cast(caster, target) {
	bot.sendMessage(Bot.channel, caster + " strikes at "+target+" with paralyzing force!");
	caster.attack(target);
	if(spell.prob(75)){
		bot.sendMessage(Bot.channel, target+ " was paralyzed!");
		spell.setDuration(spell.rand(1,6));
	}
}

function calcDamage(target) {
	return 0;
}

function postTick() {}

function onSelfAttack(hitter, hit) {
	if(spell.prob(25) && spell.duration>0){
		bot.sendMessage(Bot.channel, spell.getAffecting() + " is afflicted with t-paralysis!");
		spell.getAffecting().getTeam().getInvolvement().changeTurn();
	}
}