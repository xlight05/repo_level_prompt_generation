importPackage(Packages.classes);
importPackage(Packages.shared);
importPackage(Packages.entities);
importPackage(Packages.spells);
importPackage(Packages.bot);

function init() { 
	spell.setName("Heal");
	spell.setDuration(0);
	spell.setType("direct");
	spell.setBaseDamage(37);
	spell.setDescription("Heal your target.");
	spell.setElement(Elements.HEAL);

	return [
			new Spell.Restriction("Cleric", 12, 1)
	];
}

function cast(caster, target) {
	target.takeDamage(calcDamage(target), caster, spell);
}

function calcDamage(target) {
	var magic = spell.getCastedClass().getMagic().getTotal();
	return spell.getBaseDamage()+spell.rand(magic, magic*2);
}

function preTick() {}