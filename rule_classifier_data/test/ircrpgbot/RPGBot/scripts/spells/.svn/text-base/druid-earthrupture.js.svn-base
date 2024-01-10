importPackage(Packages.classes);
importPackage(Packages.shared);
importPackage(Packages.entities);
importPackage(Packages.spells);
importPackage(Packages.bot);

function init() { 
	spell.setName("EarthRupture");
	spell.setDuration(0);
	spell.setType("direct");
	spell.setBaseDamage(45);
	spell.setDescription("Rupture the very earth below your foe!");
	spell.setElement(Elements.EARTH);

	return [
			new Spell.Restriction("Druid", 40, 10),
	];
}

function cast(caster, target) {
	target.takeDamage(calcDamage(target), caster, spell);
}

function calcDamage(target) {
	var magic = spell.getCastedClass().getMagic().getTotal();
	return spell.getBaseDamage()+spell.rand(magic* 3, magic*9);
}

