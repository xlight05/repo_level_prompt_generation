/*
 * @author Kyle Kemp
 */
package spells;

import bot.Bot;
import entities.Entity;
import entities.Mob;

public class DefendSpell extends Spell {

	public DefendSpell(Bot b) {
		super(b);
		setDuration(1);
		setType("percent");
		setBaseDamage(50);
		setBuff(true);
		setName("Defend");
	}

	@Override
	protected int calcDamage(Mob target) {
		return getBaseDamage();

	}

	@Override
	public void onRecieveDamage(Entity tookDamage, Entity hitter, int damage) {
		bot.sendMessage(Bot.channel, hitter
				+ " was defending, and only took half damage!");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see spells.Spell#preTick()
	 */
	@Override
	public void preTick() {
		decreaseDuration();
	}
}
