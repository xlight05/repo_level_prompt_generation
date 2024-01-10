importPackage(Packages.classes);
importPackage(Packages.shared);
importPackage(Packages.entities);
importPackage(Packages.spells);
importPackage(Packages.bot);
importClass(java.util.ArrayList);

/*
This was made by KR. Just saying.
*/

function init() { 
	spell.setName("ChargeUp");
	spell.setDuration(0);
	spell.setType("direct");
	spell.setBaseDamage(0);
	spell.setDescription("Charge up to use special skills.");
	spell.setElement(0);
	return [
		new Spell.Restriction("Robot", 0, 1)
	];
}

function cast(caster, target) {
	var chargeup = spell.rand(caster.getCharacterClass().getMagic().getTotal()*0.8,caster.getCharacterClass().getMagic().getTotal()*1.3);
	bot.sendMessage(Bot.channel,caster + " charges up " + chargeup + " units...");
	caster.getCharacterClass().getOther().increase(chargeup);
	if(caster.getCharacterClass().getOther().atMax()) {
		bot.sendMessage(Bot.channel, caster + " is fully charged up!");
	}
}