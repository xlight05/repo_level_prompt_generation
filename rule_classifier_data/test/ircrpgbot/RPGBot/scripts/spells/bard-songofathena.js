importPackage(Packages.classes);
importPackage(Packages.shared);
importPackage(Packages.entities);
importPackage(Packages.spells);
importPackage(Packages.bot);

function init() { 
	spell.setName("SongOfAthena");
	spell.setType("direct");
	spell.setBaseDamage(7);
	spell.setDuration(5);
	spell.setDescription("Increase your targets magic.");
	spell.setElement(Elements.FORCE|Elements.BUFF);

	return [
			new Spell.Restriction("Bard", 45, 1),
	];
}

function cast(caster, target) {
	spell.getAffecting().getCharacterClass().getMagic().increaseBonus(spell.getBaseDamage());
}

function calcDamage(target) {
	return spell.getBaseDamage();
}

function postTick() {}

function onDurationOver(forced) {
	spell.getAffecting().getCharacterClass().getMagic().decreaseBonus(spell.getBaseDamage());
}