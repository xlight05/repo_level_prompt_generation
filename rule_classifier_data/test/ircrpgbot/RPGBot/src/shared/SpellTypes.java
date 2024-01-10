/*
 * @author Kyle Kemp
 */
package shared;

public interface SpellTypes extends Eventful {
	long INSTANT_DEATH = (0 << 1);
	long POISON = (1 << 1);
	long ACID = (2 << 1);
}
