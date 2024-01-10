importPackage(Packages.classes);
importPackage(Packages.shared);
importPackage(Packages.entities);
importPackage(Packages.spells);
importPackage(Packages.bot);

function init() { 
	spell.setName("Heal");
	spell.setDuration(0);
	spell.setType("direct");
	spell.setBaseDamage(10);
	spell.setDescription("Heal the target!");
	spell.setElement(Elements.HEAL);

	return [
			new Spell.Restriction("Druid", 25, 6),
	];
}

function cast(caster, target) {
	target.takeDamage(calcDamage(target), caster, spell);
}

function calcDamage(target) {
	var magic = spell.getCastedClass().getMagic().getTotal();
	return spell.getBaseDamage()+spell.rand(magic*1.5, magic*2.5);
}