importPackage(Packages.classes);
importPackage(Packages.shared);
importPackage(Packages.entities);
importPackage(Packages.spells);
importPackage(Packages.bot);

function init() { 
	spell.setName("FlameStrike");
	spell.setType("direct");
	spell.setBaseDamage(40);
	spell.setDuration(0);
	spell.setDescription("Strike with flaming force.");
	spell.setElement(Elements.FIRE|Elements.DEBUFF);

	return [
			new Spell.Restriction("Blood Knight", 45, 3)
	];
}

function cast(caster, target) {
	bot.sendMessage(Bot.channel, caster + " strikes at "+target+" with flaming force!");
	caster.attack(target);
	if(spell.prob(50)){
		bot.sendMessage(Bot.channel, target+ " was burned!");
		spell.setDuration(spell.rand(3,6));
		spell.setBaseDamage((spell.getBaseDamage()/5));
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