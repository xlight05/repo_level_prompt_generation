importPackage(Packages.classes);
importPackage(Packages.shared);
importPackage(Packages.entities);
importPackage(Packages.spells);
importPackage(Packages.bot);
importClass(java.util.ArrayList);

function init() {
        return [
        ];
}

function setStats() {

        Class.setHp(new RestrictedNumber("Health", 0, 100, 100));
        Class.setOther(new RestrictedNumber("Mana", 0, 170, 170));
        Class.setLevel(new RestrictedNumber("Level", 0, 1, 999));
        Class.setSpeed(new RestrictedNumber("Speed", 0, 3, 999));
        Class.setMagic(new RestrictedNumber("Magic", 0, 6, 999));
        Class.setPower(new RestrictedNumber("Power", 0, 1, 999));
        Class.setLuck(new RestrictedNumber("Luck", 0, 2, 999));
        Class.setExp(new RestrictedNumber("Exp", 0, 0, 100));
        Class.setCon(new RestrictedNumber("Constitution", 0, 1, 999));

        Class.setLuckInterval(6);
        Class.setHpGain(10);
        Class.setPowerGain(1);
        Class.setSpeedGain(1);
        Class.setMagicGain(7);
        Class.setOtherGain(15);
        Class.setConGain(1);
       
        Class.setName("Water Wizard");

}

function heal() {
        Class.getOther().toMaximum();
}

function onSelfTurnStart(turn) {
        Class.getOther().increase(Class.getMagic().getTotal());
}
