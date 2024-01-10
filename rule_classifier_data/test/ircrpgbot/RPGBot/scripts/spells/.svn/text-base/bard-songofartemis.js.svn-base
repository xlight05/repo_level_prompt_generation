importPackage(Packages.classes);
importPackage(Packages.shared);
importPackage(Packages.entities);
importPackage(Packages.spells);
importPackage(Packages.bot);

function init() { 
	spell.setName("SongOfArtemis");
	spell.setType("direct");
	spell.setBaseDamage(7);
	spell.setDuration(5);
	spell.setDescription("Increase your targets speed.");
	spell.setElement(Elements.FORCE|Elements.BUFF);

	return [
			new Spell.Restriction("Bard", 75, 1),
	];
}

function cast(caster, target) {
	spell.getAffecting().getCharacterClass().getSpeed().increaseBonus(spell.getBaseDamage());
}

function calcDamage(target) {
	return spell.getBaseDamage();
}

function postTick() {}

function onDurationOver(forced) {
	spell.getAffecting().getCharacterClass().getSpeed().decreaseBonus(spell.getBaseDamage());
}