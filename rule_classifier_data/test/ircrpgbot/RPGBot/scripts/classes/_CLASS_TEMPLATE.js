importPackage(Packages.classes);
importPackage(Packages.shared);
importPackage(Packages.entities);
importPackage(Packages.spells);
importPackage(Packages.bot);
importClass(java.util.ArrayList);

/*
READ THIS.
----------
Foreward
Some of these arguments may or may not be correct -- they are written to the best of my knowledge.
Some of these functions may or may not get called -- some might be obsoleted based on how they are called. The finest example of this is the Self and Recieve set of functions. You're best to verify which works before using either of them.
There are alternatives if one doesn't work.

In cases where there are arguments like 'tookDamage' and 'hitter,' you may want to verify both of them before doing anything. Please let me know if you find any misleading argument names so they can be changed.

SPECIAL REFERENCES
------------------
Class -- a reference to the class in question.
*/

function init() { 
	return [
			//new CharacterClass.Restriction("Fighter", 2);
	];
}
/**
  * This is called during the initialization process. It must be filled out.
  */
function setStats() {
	//Class.setHp(new RestrictedNumber("Health", 0, 225, 225));
	//Class.setOther(new RestrictedNumber("Rage", 0, 100, 100));
	//Class.setLevel(new RestrictedNumber("Level", 0, 1, 999));
	//Class.setSpeed(new RestrictedNumber("Speed", 0, 1, 99));
	//Class.setMagic(new RestrictedNumber("Magic", 0, 0, 999));
	//Class.setPower(new RestrictedNumber("Power", 0, 6, 999));
	//Class.setLuck(new RestrictedNumber("Luck", 0, 1, 999));
	//Class.setCon(new RestrictedNumber("Constitution", 0, 1, 999));
	//Class.setExp(new RestrictedNumber("Exp", 0, 0, 100));

	//Class.setLuckInterval(8);
	//Class.setHpGain(50);
	//Class.setPowerGain(6);
	//Class.setSpeedGain(3);
	//Class.setMagicGain(1);
	//Class.setOtherGain(3);
	//Class.setConGain(3);
	
	//Class.setName("Barbarian");
}

/**
  * This is called to allow classes to flat-out reduce damage (say a class has 5% damage reduction..)
  * @param damage the initial damage amount
  * @return a number
  */
function calcReductions(damage) {
	return 0;
}

/**
  * This is called at the start of every battle -- use it to manage your Other stat (HP is already taken care of for you)
  */
function heal() {

}

/**
  * This is called during !attack to see if you have any modifications to do to the damage based on the hitters statistics.
  * @param hitter the mob hitting you
  * @param damage the damage being done, sans modification
  * @return a number
  */
function calcDamage(hitter) {
	return 0;
}

/**
  * This is called during !attack to see if you are able to dodge the attack via skill means. (if(Class.getMyMob().hasSpell("Dodge")))
  * @return 1 if dodging is possible, 0 if not.
  */
function canDodge() {
	return 0;
}

/**
  * This is called during !attack to modify your own damage output.
  * @param hitting the mob you are hitting
  * @param damage the damage being done, sans modification
  * @return a number
  */
function modDamage(hitting, damage) {
	return 0;
}

/**
  * Called when an ally declares an attack on anyone else.
  * @param hitter the person declaring the attack
  * @param hit the person getting hit by the attack
  */
function onAllyAttack(hitter, hit) {

}

/**
  * Called when an ally gets a critical hit.
  * @param hitter the person declaring the attack
  * @param hit the person getting hit by the attack
  * @param damage how much damage was done via critical hit
  */
function onAllyCriticalHit( hitter,  target,  damage) {

}
	
/**
  * Called when an ally deals damage.
  * @param tookDamage the person declaring the attack
  * @param hitter the person getting hit by the attack
  * @param damage how much damage was done via hit
  */
function onAllyDamage( tookDamage,  hitter,  damage) {

}
	
/**
  * Called when an ally takes damage.
  * @param hitter the person declaring the attack
  * @param damage how much damage was done via hit
  */
function onAllyDamaged( hitter,  damage) {

}

/**
  * Called when an ally dies
  * @param killer the person who killed the ally
  * @param target the ally that was killed
  */	
function onAllyDeath( killer,  target) {

}

/**
  * Called when an ally flees from battle
  * @param flee the person who fled from battle
  */
function onAllyFlee( flee) {

}

/**
  * Called when an ally gains exp.
  * @param gain the person gaining exp
  */
function onAllyGainExp( gain) {

}

/**
  * Called when an ally is healed.
  * @param user the person healing someone
  * @param target the subject of the heal
  * @param healed how much damage was healed
  */
function onAllyHealed(user, target, healed) {

}

/**
  * Called when an ally lands a hit.
  * @param hitter the person declaring the attack
  * @param target the subject of the attack
  * @param damage how much damage was done via hit
  */
function onAllyHit( hitter,  target,  damage) {

}

/**
  * Called when an ally gains a level.
  * @param gain the person gaining a level
  */	
function onAllyLevelUp( gain) {

}

/**
  * Called when an ally misses a hit.
  * @param hitter the person who missed
  * @param target the subject of the attack
  */
function onAllyMiss( hitter,  target) {

}

/**
  * Called when an ally uses a skill.
  * @param user the person using the skill
  * @param target the subject of the skill
  * @param skill the skill being used
  */
function onAllySkillUse( user,  target,  skill) {

}

/**
  * Called when an ally's turn ends.
  * @param turn the ally whose turn just ended
  */
function onAllyTurnEnd( turn) {

}

/**
  * Called when an ally's turn starts.
  * @param turn the ally whose turn just started
  */
function onAllyTurnStart( turn) {

}
	
/**
  * Called when an Enemy declares an attack on anyone else.
  * @param hitter the person declaring the attack
  * @param hit the person getting hit by the attack
  */
function onEnemyAttack(hitter, hit) {

}

/**
  * Called when an Enemy gets a critical hit.
  * @param hitter the person declaring the attack
  * @param hit the person getting hit by the attack
  * @param damage how much damage was done via critical hit
  */
function onEnemyCriticalHit( hitter,  target,  damage) {

}
	
/**
  * Called when an Enemy deals damage.
  * @param tookDamage the person declaring the attack
  * @param hitter the person getting hit by the attack
  * @param damage how much damage was done via hit
  */
function onEnemyDamage( tookDamage,  hitter,  damage) {

}
	
/**
  * Called when an Enemy takes damage.
  * @param hitter the person declaring the attack
  * @param damage how much damage was done via hit
  */
function onEnemyDamaged( hitter,  damage) {

}

/**
  * Called when an Enemy dies
  * @param killer the person who killed the Enemy
  * @param target the Enemy that was killed
  */	
function onEnemyDeath( killer,  target) {

}

/**
  * Called when an Enemy flees from battle
  * @param flee the person who fled from battle
  */
function onEnemyFlee( flee) {

}

/**
  * Called when an Enemy gains exp.
  * @param gain the person gaining exp
  */
function onEnemyGainExp( gain) {

}

/**
  * Called when an enemy is healed.
  * @param user the person healing someone
  * @param target the subject of the heal
  * @param healed how much damage was healed
  */
function onEnemyHealed(user, target, healed) {

}

/**
  * Called when an Enemy lands a hit.
  * @param hitter the person declaring the attack
  * @param target the subject of the attack
  * @param damage how much damage was done via hit
  */
function onEnemyHit( hitter,  target,  damage) {

}

/**
  * Called when an Enemy gains a level.
  * @param gain the person gaining a level
  */	
function onEnemyLevelUp( gain) {

}

/**
  * Called when an Enemy misses a hit.
  * @param hitter the person who missed
  * @param target the subject of the attack
  */
function onEnemyMiss( hitter,  target) {

}

/**
  * Called when an Enemy uses a skill.
  * @param user the person using the skill
  * @param target the subject of the skill
  * @param skill the skill being used
  */
function onEnemySkillUse( user,  target,  skill) {

}

/**
  * Called when an Enemy's turn ends.
  * @param turn the Enemy whose turn just ended
  */
function onEnemyTurnEnd( turn) {

}

/**
  * Called when an Enemy's turn starts.
  * @param turn the Enemy whose turn just started
  */
function onEnemyTurnStart( turn) {

}


/**
  * Called when the fight ends.
  */
function onFightEnd() {

}


/**
  * Called when the fight begins.
  */
function onFightStart() {

}


/**
  * Called when a team is declared loser.
  * @param t the losing team
  * @deprecated use the respective gainexp functions for each type
  */
function onGainExp( gainer) {

}


/**
  * Called when a team is declared loser.
  * @param t the losing team
  * @deprecated use the respective levelup functions for each type
  */
function onLevelUp( awesome) {

}


/**
  * Called when a team is declared loser.
  * @param t the losing team
  */
function onPartyLose( t) {

}

/**
  * Called when a team is declared victorious.
  * @param t the victorious team
  */
function onPartyWin( t) {

}

/**
  * Called on the recipient of an attack declaration.
  * @param hitter the person declaring the attack
  * @param hit the person getting hit by the attack
  */
function onRecieveAttack(hitter, hit) {

}

/**
  * Called on the recipient of a critical hit.
  * @param hitter the person declaring the attack
  * @param hit the person getting hit by the attack
  * @param damage how much damage was done via critical hit
  */
function onRecieveCriticalHit( hitter,  target,  damage) {

}
	
/**
  * Called on the target that was dealt damage via ATTACK.
  * ***This function and the function below are confusing.
  * @param tookDamage the person declaring the attack
  * @param hitter the person getting hit by the attack
  * @param damage how much damage was done via hit
  */
function onRecieveDamage( tookDamage,  hitter,  damage) {

}
	
/**
  * Called on the target that was dealt damage.
  * ***Probably not called, use the above function.
  * @param hitter the person declaring the attack
  * @param damage how much damage was done via hit
  */
function onRecieveDamaged( hitter,  damage) {

}

/**
  * Called on the recipient of death.
  * @param killer the person who killed the Recieve
  * @param target the Recieve that was killed
  */	
function onRecieveDeath( killer,  target) {

}

/**
  * Probably not called at all.
  * @param flee the person who fled from battle
  */
function onRecieveFlee( flee) {

}

/**
  * Called on the person recieving exp.
  * @param gain the person gaining exp
  */
function onRecieveGainExp( gain) {

}

/**
  * Called when I recieve healing.
  * @param user the person healing someone
  * @param target the subject of the heal
  * @param healed how much damage was healed
  */
function onRecieveHealed(user, target, healed) {

}

/**
  * Called on the person who got hit.
  * @param hitter the person declaring the attack
  * @param target the subject of the attack
  * @param damage how much damage was done via hit
  */
function onRecieveHit( hitter,  target,  damage) {

}

/**
  * Called when a level is gained.
  * @param gain the person gaining a level
  */	
function onRecieveLevelUp( gain) {

}

/**
  * Called on the recipient of the missed attack.
  * @param hitter the person who missed
  * @param target the subject of the attack
  */
function onRecieveMiss( hitter,  target) {

}

/**
  * Called on the target of a skill.
  * @param user the person using the skill
  * @param target the subject of the skill
  * @param skill the skill being used
  */
function onRecieveSkillUse( user,  target,  skill) {

}

/**
  * Called when an Recieve's turn ends.
  * @param turn the Recieve whose turn just ended
  */
function onRecieveTurnEnd( turn) {

}

/**
  * Called when an Recieve's turn starts.
  * @param turn the Recieve whose turn just started
  */
function onRecieveTurnStart( turn) {

}

/**
  * Called when the current round ends.
  */
function onRoundEnd() {

}

/**
  * Called when a new round begins.
  */
function onRoundStart() {

}

/**
  * Called when I declare an attack on anyone else.
  * @param hitter the person declaring the attack
  * @param hit the person getting hit by the attack
  */
function onSelfAttack(hitter, hit) {

}

/**
  * Called when I land a critical hit.
  * @param hitter the person declaring the attack
  * @param hit the person getting hit by the attack
  * @param damage how much damage was done via critical hit
  */
function onSelfCriticalHit( hitter,  target,  damage) {

}
	
/**
  * Called when I deal damage.
  * @param tookDamage the person declaring the attack
  * @param hitter the person getting hit by the attack
  * @param damage how much damage was done via hit
  */
function onSelfDamage( tookDamage,  hitter,  damage) {

}
	
/**
  * Called when I take damage via SKILL.
  * @param hitter the person declaring the attack
  * @param damage how much damage was done via hit
  */
function onSelfDamaged( hitter,  damage) {

}

/**
  * Called when I die.
  * @param killer the person who killed the Self
  * @param target the Self that was killed
  */	
function onSelfDeath( killer,  target) {

}

/**
  * Called when I flee.
  * @param flee the person who fled from battle
  */
function onSelfFlee( flee) {

}

/**
  * Called when I gain exp.
  * @param gain the person gaining exp
  */
function onSelfGainExp( gain) {

}


/**
  * Called when I heal someone.
  * @param user the person healing someone
  * @param target the subject of the heal
  * @param healed how much damage was healed
  */
function onSelfHealed(user, target, healed) {

}

/**
  * Called when I land a hit.
  * @param hitter the person declaring the attack
  * @param target the subject of the attack
  * @param damage how much damage was done via hit
  */
function onSelfHit( hitter,  target,  damage) {

}

/**
  * Called when I gain a level.
  * @param gain the person gaining a level
  */	
function onSelfLevelUp( gain) {

}

/**
  * Called when I miss a hit.
  * @param hitter the person who missed
  * @param target the subject of the attack
  */
function onSelfMiss( hitter,  target) {

}

/**
  * Called when I use a skill.
  * @param user the person using the skill
  * @param target the subject of the skill
  * @param skill the skill being used
  */
function onSelfSkillUse( user,  target,  skill) {

}

/**
  * Called when my turn ends.
  */
function onSelfTurnEnd() {

}

/**
  * Called when my turn starts.
  */
function onSelfTurnStart() {

}