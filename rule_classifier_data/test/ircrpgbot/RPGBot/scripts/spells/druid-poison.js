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
	spell.setDescription("Poison an enemy, with slight damage!");
	spell.setElement(Elements.EARTH | Elements.POISON);

	return [
			new Spell.Restriction("Druid", 10, 3),
	];
}

function cast(caster, target) {
	target.takeDamage(calcDamage(target), caster, spell);
	bot.sendMessage(Bot.channel, target+ " was poisoned!");
	spell.setDuration(spell.rand(2,5));
}

function calcDamage(target) {
	var magic = spell.getCastedClass().getMagic().getTotal();
	return spell.getBaseDamage()+spell.rand(magic/3, magic/2);
}

function postTick() {
	if(spell.duration>0){
		bot.sendMessage(Bot.channel, spell.getAffecting()+ " was hurt by poison!");
		spell.getAffecting().takeDamage(spell.rand(5, 15), spell.getCastedClass().getMyMob(), spell);
	}
	else if(spell.duration<=0){
		bot.sendMessage(Bot.channel, spell.getAffecting()+ " recovered from poisoning.");
	}
}