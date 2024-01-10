importPackage(Packages.classes);
importPackage(Packages.shared);
importPackage(Packages.entities);
importPackage(Packages.spells);
importPackage(Packages.bot);

function init() { 
	spell.setName("Fire");
	spell.setDuration(0);
	spell.setType("direct");
	spell.setBaseDamage(40);
	spell.setDescription("Set your opponent ablaze, potentially leaving him/her burned.");
	spell.setElement(Elements.FIRE|Elements.DEBUFF);

	return [
			new Spell.Restriction("Mage", 17, 1),
			new Spell.Restriction("Blood Knight", 25, 1),
	];
}

function cast(caster, target) {
	target.takeDamage(calcDamage(target), caster, spell);
	if(spell.prob(20)){
		bot.sendMessage(Bot.channel, target+ " was burned!");
		spell.setDuration(spell.rand(2,4));
		spell.setBaseDamage((spell.getBaseDamage()/10));
	}
}

function calcDamage(target) {
	var magic = spell.getCastedClass().getMagic().getTotal();
	return spell.getBaseDamage()+spell.rand(magic, magic*2);
}

function postTick() {
	if(spell.duration>0){
		bot.sendMessage(Bot.channel, spell.getAffecting()+ " took burn damage!");
		spell.getAffecting().takeDamage(spell.getBaseDamage(), spell.getCastedClass().getMyMob(), spell);
	}
}