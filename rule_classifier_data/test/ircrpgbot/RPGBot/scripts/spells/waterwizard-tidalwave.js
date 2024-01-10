importPackage(Packages.classes);
importPackage(Packages.shared);
importPackage(Packages.entities);
importPackage(Packages.spells);
importPackage(Packages.bot);

function init() { 
        spell.setName("TidalWave");
        spell.setDuration(0);
        spell.setType("direct");
        spell.setBaseDamage(5);
        spell.setDescription("Send a wall of water rushing at your enemy.");
        spell.setElement(Elements.WATER);

        return [
                        new Spell.Restriction("Water Wizard", 5, 1),
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
