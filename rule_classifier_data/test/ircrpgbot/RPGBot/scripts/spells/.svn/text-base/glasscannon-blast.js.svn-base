importPackage(Packages.classes);
importPackage(Packages.shared);
importPackage(Packages.entities);
importPackage(Packages.spells);
importPackage(Packages.bot);

function init() { 
	spell.setName("Blast");
	spell.setDuration(0);
	spell.setType("direct");
	spell.setBaseDamage(100);
	spell.setDescription("Blast your foe with a pure shot of energy.");
	spell.setElement(Elements.FORCE);

	return [
			new Spell.Restriction("Glass Cannon", 120, 1),
	];
}

function cast(caster, target) {
	target.takeDamage(calcDamage(target), caster, spell);
}

function calcDamage(target) {
	var magic = spell.getCastedClass().getMagic().getTotal();
	return spell.getBaseDamage()+spell.rand(magic, magic*2);
}