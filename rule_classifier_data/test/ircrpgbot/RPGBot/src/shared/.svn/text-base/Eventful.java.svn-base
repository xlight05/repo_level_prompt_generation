/*
 * @author Kyle Kemp
 */
package shared;

import spells.Spell;
import battle.Team;
import entities.Entity;

public interface Eventful extends Constants {

	// declared attack
	void onAllyAttack(Entity hitter, Entity hit);

	void onAllyCriticalHit(Entity hitter, Entity target, int damage);

	void onAllyCriticalHit(Entity hitter, Entity hit, Integer damage);

	void onAllyDamage(Entity tookDamage, Entity hitter, int damage);

	// take damage
	void onAllyDamage(Entity tookDamage, Entity hitter, Integer damage);

	void onAllyDamaged(Entity hitter, int damage);

	void onAllyDamaged(Entity hitter, Integer damage);

	void onAllyDeath(Entity killer, Entity target);

	void onAllyFlee(Entity flee);

	void onAllyGainExp(Entity gain);

	void onAllyHealed(Entity user, Entity target, int healed);
	
	void onAllyHealed(Entity user, Entity target, Integer healed);

	void onAllyHit(Entity hitter, Entity target, int damage);

	// attack connected
	void onAllyHit(Entity hitter, Entity target, Integer damage);

	void onAllyLevelUp(Entity gain);

	void onAllyMiss(Entity hitter, Entity target);

	void onAllySkillUse(Entity user, Entity target, Spell skill);

	void onAllyTurnEnd(Entity turn);

	void onAllyTurnStart(Entity turn);

	void onEnemyAttack(Entity hitter, Entity hit);

	void onEnemyCriticalHit(Entity hitter, Entity target, int damage);

	void onEnemyCriticalHit(Entity hitter, Entity hit, Integer damage);

	void onEnemyDamage(Entity tookDamage, Entity hitter, int damage);

	void onEnemyDamage(Entity tookDamage, Entity hitter, Integer damage);

	void onEnemyDamaged(Entity hitter, int damage);

	void onEnemyDamaged(Entity hitter, Integer damage);

	void onEnemyDeath(Entity killer, Entity target);

	void onEnemyFlee(Entity flee);

	void onEnemyGainExp(Entity gain);

	void onEnemyHealed(Entity user, Entity target, int healed);
	
	void onEnemyHealed(Entity user, Entity target, Integer healed);

	void onEnemyHit(Entity hitter, Entity target, int damage);

	void onEnemyHit(Entity hitter, Entity target, Integer damage);

	void onEnemyLevelUp(Entity gain);

	void onEnemyMiss(Entity hitter, Entity target);

	void onEnemySkillUse(Entity user, Entity target, Spell skill);

	void onEnemyTurnEnd(Entity turn);

	void onEnemyTurnStart(Entity turn);

	void onFightEnd();

	void onFightStart();

	void onGainExp(Entity gainer);

	void onLevelUp(Entity awesome);

	void onPartyLose(Team t);

	void onPartyWin(Team t);

	void onRecieveAttack(Entity hitter, Entity hit);

	void onRecieveCriticalHit(Entity hitter, Entity target, int damage);

	void onRecieveCriticalHit(Entity hitter, Entity hit, Integer damage);

	void onRecieveDamage(Entity tookDamage, Entity hitter, int damage);

	void onRecieveDamage(Entity tookDamage, Entity hitter, Integer damage);

	void onRecieveDamaged(Entity hitter, int damage);

	void onRecieveDamaged(Entity hitter, Integer damage);

	void onRecieveDeath(Entity killer, Entity target);

	void onRecieveFlee(Entity flee);

	void onRecieveGainExp(Entity gain);

	void onRecieveHealed(Entity user, Entity target, int healed);
	
	void onRecieveHealed(Entity user, Entity target, Integer healed);

	void onRecieveHit(Entity hitter, Entity target, int damage);

	void onRecieveHit(Entity target, Entity hitter, Integer damage);

	void onRecieveLevelUp(Entity gain);

	void onRecieveMiss(Entity hitter, Entity target);

	void onRecieveSkillUse(Entity user, Entity target, Spell skill);

	void onRecieveTurnEnd(Entity turn);

	void onRecieveTurnStart(Entity turn);

	void onRoundEnd();

	void onRoundStart();

	void onSelfAttack(Entity hitter, Entity hit);

	void onSelfCriticalHit(Entity hitter, Entity target, int damage);

	void onSelfCriticalHit(Entity hitter, Entity hit, Integer damage);

	void onSelfDamage(Entity tookDamage, Entity hitter, int damage);

	void onSelfDamage(Entity tookDamage, Entity hitter, Integer damage);

	void onSelfDamaged(Entity hitter, int damage);

	void onSelfDamaged(Entity hitter, Integer damage);

	void onSelfDeath(Entity killer, Entity target);

	void onSelfFlee(Entity flee);

	void onSelfGainExp(Entity gain);

	void onSelfHealed(Entity user, Entity target, int healed);
	
	void onSelfHealed(Entity user, Entity target, Integer healed);

	void onSelfHit(Entity hitter, Entity target, int damage);

	void onSelfHit(Entity hitter, Entity target, Integer damage);

	void onSelfLevelUp(Entity gain);

	void onSelfMiss(Entity hitter, Entity target);

	void onSelfSkillUse(Entity user, Entity target, Spell skill);

	void onSelfTurnEnd(Entity turn);

	void onSelfTurnStart(Entity turn);
}
