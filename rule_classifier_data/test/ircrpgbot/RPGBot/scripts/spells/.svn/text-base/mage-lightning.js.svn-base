importPackage(Packages.classes);
importPackage(Packages.shared);
importPackage(Packages.entities);
importPackage(Packages.spells);
importPackage(Packages.bot);

function init() { 
	spell.setName("Lightning");
	spell.setDuration(0);
	spell.setType("direct");
	spell.setBaseDamage(15);
	spell.setDescription("Strike your foe with a discharge of electricity, potentially leaving him/her paralyzed.");
	spell.setElement(Elements.ELEC|Elements.DEBUFF);

	return [
			new Spell.Restriction("Mage", 29, 3),
			new Spell.Restriction("Blood Knight", 25, 1),
	];
}

function cast(caster, target) {
	target.takeDamage(calcDamage(target), caster, spell);
	if(spell.prob(40)){
		bot.sendMessage(Bot.channel, target+ " was zapped!");
		spell.setDuration(spell.rand(5,6));
		spell.setBaseDamage(0);
	}
}

function calcDamage(target) {
	var magic = spell.getCastedClass().getMagic().getTotal();
	return spell.rand(1, spell.getBaseDamage()+magic*3);
}

//this is defined only so postTick gets called
function postTick() {}

function onSelfAttack(hitter, hit) {
	if(spell.prob(25) && spell.duration>0){
		bot.sendMessage(Bot.channel, spell.getAffecting() + " is afflicted with t-paralysis!");
		spell.getAffecting().getTeam().getInvolvement().changeTurn();
	}
}