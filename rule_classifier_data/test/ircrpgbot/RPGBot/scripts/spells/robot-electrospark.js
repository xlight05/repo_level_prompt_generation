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
	spell.setName("ElectroSpark");
	spell.setDuration(0);
	spell.setType("direct");
	spell.setBaseDamage(20);
	spell.setDescription("Fire a spark at the target. The more charged up, the more powerful.");
	spell.setElement(Elements.ELEC);
	return [
		new Spell.Restriction("Robot", 1, 1)
	];
}

function cast(caster, target) {
	var dmg = calcDamage(target);
	var charge_percent = (spell.getCastedClass().getOther().getTotal() / spell.getCastedClass().getOther().getMaximum()) * 100;
	target.takeDamage(dmg,caster,spell);
	caster.getCharacterClass().getOther().toMinimum();
}

function calcDamage(target) {
	var charge_percent = (spell.getCastedClass().getOther().getTotal() / spell.getCastedClass().getOther().getMaximum());
	return spell.getBaseDamage() + (charge_percent * (spell.getCastedClass().getPower().getTotal()+4) * 22);
}