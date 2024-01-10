importPackage(Packages.classes);
importPackage(Packages.shared);
importPackage(Packages.entities);
importPackage(Packages.spells);
importPackage(Packages.bot);

function init() { 
	spell.setName("NatureGuard");
	spell.setDuration(2);
	spell.setType("direct");
	spell.setDescription("Temporarily take damage from your spirit instead of your health for two rounds! NOTE: You can only cast this on yourself!");
	spell.setElement(Elements.HEAL);

	return [
			new Spell.Restriction("Druid", 0, 3)
	];
}

function cast(caster, target) {
	bot.sendMessage(Bot.channel, caster+ " becomes guarded by runic symbols!");
}

function calcDamage(target) {
	return spell.getBaseDamage();
}


function preTick() {
	if(spell.duration<=0){
		bot.sendMessage(Bot.channel, spell.getAffecting() + " stops glowing with mystical power.");
	}
}