importPackage(Packages.classes);
importPackage(Packages.shared);
importPackage(Packages.entities);
importPackage(Packages.spells);
importPackage(Packages.bot);

function init() { 
	spell.setName("Smite");
	spell.setDuration(0);
	spell.setType("direct");
	spell.setBaseDamage(35);
	spell.setDescription("Smite your foe with holy energy.");
	spell.setElement(Elements.LIGHT);

	return [
			new Spell.Restriction("Cleric", 18, 2),
	];
}

function cast(caster, target) {
	target.takeDamage(calcDamage(target), caster, spell);
}

function calcDamage(target) {
	var magic = spell.getCastedClass().getMagic().getTotal();
	return spell.getBaseDamage()+spell.rand(magic/3, magic*2);
}

function preTick() {}