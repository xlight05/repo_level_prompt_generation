/*
 * @author Kyle Kemp
 */
package entities;

import org.jibble.pircbot.PircBot;

public class Monster extends Mob {
	/*
	 * Monsters will be connected to the game via a pircbot instance representing them.
	 * 
	 * Monsters will have access to all of the information the bot does, so it can make relatively
	 * informed decisions. A good way to figure out a lot of choices are to take every spell, and weight
	 * them with a comparator, and just pick a spell to cast based on an ai setting.
	 * In addition, players can be weighed the same way: sort them, sift through all of them until you
	 * come across one not on your team, and then attack it, or cast at it. This means AI will have two
	 * possible setting: one for spell casting (or attacking) and one for player picking.
	 * Since spells do not fail gracefully (ending turn), monsters will also have to parse output on occasion
	 * and, if a spell fails, then it will have to attack someone.
	 * 
	 * Monsters AI settings:
	 * 	BIG_BURST_MP			use the most mp-consuming spells first
	 *  SMALL_BURST_MP			use the smallest mp-consuming spells first
	 *  CONSERVE_MP				use as little mp as possible (probably cast a spell every few turns)
	 *  USE_NO_MP				only attack
	 *  SUPPORT					only use spells that follow Element.HEAL & Element.BUFF
	 *  ANYTHING				do whatever is wanted at the time (failed casts still must attack after)
	 *  
	 *  HIT_STRONGEST			hit the person with the highest power
	 *  HIT_WEAKEST				hit the person with the least power
	 *  HIT_MEDIAN				hit someone in the middle of the power debate (iffy)
	 *  HIT_MOST_INTELLIGENT	hit the person with the most magic (iffy)
	 *  HIT_LEAST_INTELLIGENT	hit the person with the least magic (iffy)
	 *  HIT_HIGHEST_LEVEL		hit the highest level person first
	 *  HIT_LOWEST_LEVEL		hit the lowest level person first
	 *  HIT_MOST_THREATENING	hit the person with the most hp first
	 *  HIT_LEAST_THREATENING	hit the person with the least hp first
	 */
	PircBot myBot;
}
