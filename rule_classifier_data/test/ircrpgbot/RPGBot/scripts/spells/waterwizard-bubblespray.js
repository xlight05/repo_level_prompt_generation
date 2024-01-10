importPackage(Packages.classes);
importPackage(Packages.shared);
importPackage(Packages.entities);
importPackage(Packages.spells);
importPackage(Packages.bot);

function init() { 
        spell.setName("BubbleSpray");
        spell.setDuration(0);
        spell.setType("direct");
        spell.setBaseDamage(24);
        spell.setDescription("A torrent of bubbles is fired at the opponent, Low damage, but high chance of trapping the opponent");
        spell.setElement(Elements.WATER|Elements.DEBUFF);

        return [
                        new Spell.Restriction("Water Wizard", 15, 2),
        ];
}

function cast(caster, target) {
        target.takeDamage(calcDamage(target), caster, spell);
        if(spell.prob(60)){
                bot.sendMessage(Bot.channel, target+ " was trapped in a bubble!");
                spell.setDuration(spell.rand(1,2));
                spell.setBaseDamage(0);
        }
}

function calcDamage(target) {
        var magic = spell.getCastedClass().getMagic().getTotal();
        return spell.getBaseDamage()+spell.rand(magic/2, magic);
}
        
function preTick() {
        if(spell.duration>0){
			bot.sendMessage(Bot.channel, spell.getAffecting() + " is trapped in a bubble, and can't breathe!");
			spell.getAffecting().takeDamage(spell.rand(1,9), spell.getCastedClass().getMyMob(), spell);
			spell.getAffecting().getTeam().getInvolvement().changeTurn();
        }
}