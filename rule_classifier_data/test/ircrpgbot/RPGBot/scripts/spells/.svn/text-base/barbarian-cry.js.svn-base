importPackage(Packages.classes);
importPackage(Packages.shared);
importPackage(Packages.entities);
importPackage(Packages.spells);
importPackage(Packages.bot);

function init() { 
	spell.setName("Cry");
	spell.setType("direct");
	spell.setBaseDamage(5);
	spell.setDuration(5);
	spell.setDescription("Increase your con/power, or decrease an opponent's con/power.");
	spell.setElement(Elements.FORCE);

	return [
			new Spell.Restriction("Barbarian", 15, 1),
			new Spell.Restriction("Bloodbarian","hp", 50, 1),
	];
}

function cast(caster, target) {
	if(caster.getTeam().equals(target.getTeam())){
		spell.setElement(spell.getElement()|Elements.BUFF);
		spell.getAffecting().getCharacterClass().getCon().increaseBonus(spell.getBaseDamage());
		spell.getAffecting().getCharacterClass().getPower().increaseBonus(spell.getBaseDamage());
	} else if(!caster.equals(target)){
		spell.setBaseDamage(-spell.getBaseDamage());
		spell.setElement(spell.getElement()|Elements.DEBUFF);
		spell.getAffecting().getCharacterClass().getCon().increaseBonus(spell.getBaseDamage());
		spell.getAffecting().getCharacterClass().getPower().increaseBonus(spell.getBaseDamage());
	}
	bot.sendMessage(Bot.channel, "The barbarian lets out a shrill cry!");
}

function calcDamage(target) {
	return spell.getBaseDamage();
}

function postTick() {}

function onDurationOver(forced) {
	spell.getAffecting().getCharacterClass().getCon().decreaseBonus(spell.getBaseDamage());
	spell.getAffecting().getCharacterClass().getPower().decreaseBonus(spell.getBaseDamage());
	if(!forced){
		bot.sendMessage(Bot.channel, spell.getAffecting()+ " no longer hears the barbaric cries!");
	}
}