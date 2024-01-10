importPackage(Packages.classes);
importPackage(Packages.shared);
importPackage(Packages.entities);
importPackage(Packages.spells);
importPackage(Packages.bot);

function init() { 
	spell.setName("Meditate");
	spell.setDuration(0);
	spell.setType("direct");
	spell.setBaseDamage(10);
	spell.setDescription("Recover some of your Spirit!");
	spell.setElement(Elements.HEAL);

	return [
			new Spell.Restriction("Druid", 0, 6),
	];
}

function cast(caster, target) {
	var recover = caster.getCharacterClass().getOther().getMaximum() * spell.rand(5, 50)/100;
	bot.sendMessage(Bot.channel, caster+ " recovered " +Math.floor(recover)+" Spirit upon meditating!");
	caster.getCharacterClass().getOther().increase(recover);
}

function calcDamage(target) {
	var magic = spell.getCastedClass().getMagic().getTotal();
	return spell.getBaseDamage()+spell.rand(magic*1.5, magic*2.5);
}