importPackage(Packages.classes);
importPackage(Packages.shared);
importPackage(Packages.entities);
importPackage(Packages.spells);
importPackage(Packages.bot);

function init() { 
	spell.setName("Ice");
	spell.setDuration(0);
	spell.setType("direct");
	spell.setBaseDamage(27);
	spell.setDescription("A spray of ice is used against your opponent, potentially freezing him/her solid.");
	spell.setElement(Elements.ICE|Elements.WATER|Elements.DEBUFF);

	return [
			new Spell.Restriction("Mage", 24, 2),
			new Spell.Restriction("Blood Knight", 25, 1),
	];
}

function cast(caster, target) {
	target.takeDamage(calcDamage(target), caster, spell);
	if(spell.prob(10)){
		bot.sendMessage(Bot.channel, target+ " was frozen!");
		spell.setDuration(spell.rand(1,3));
		spell.setBaseDamage(0);
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
