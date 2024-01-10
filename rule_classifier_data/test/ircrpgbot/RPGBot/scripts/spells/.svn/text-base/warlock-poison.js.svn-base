importPackage(Packages.classes);
importPackage(Packages.shared);
importPackage(Packages.entities);
importPackage(Packages.spells);
importPackage(Packages.bot);

function init() { 
	spell.setName("Poison");
	spell.setDuration(0);
	spell.setType("direct");
	spell.setBaseDamage(5);
	spell.setDescription("Hit your opponent with deadly poison.");
	spell.setElement(Elements.POISON|Elements.DEBUFF);

	return [
			new Spell.Restriction("Warlock", 46, 1),
	];
}

function cast(caster, target) {
	target.takeDamage(calcDamage(target), caster, spell);
	if(spell.prob(95)){
		bot.sendMessage(Bot.channel, target+ " was inflicted with poison!");
		spell.setDuration(spell.rand(9,15));
	} else {
		bot.sendMessage(Bot.channel, target+ " narrowly avoided the poison blast!");
	}
}

function calcDamage(target) {
	var magic = spell.getCastedClass().getMagic().getTotal();
	return spell.rand(magic*2, magic*3.5);
}

function postTick() {
	if(spell.duration>0){
		bot.sendMessage(Bot.channel, spell.getAffecting()+ " took poison damage!");
		spell.getAffecting().takeDamage(spell.getBaseDamage(), spell.getCastedClass().getMyMob(), spell);
	}
}