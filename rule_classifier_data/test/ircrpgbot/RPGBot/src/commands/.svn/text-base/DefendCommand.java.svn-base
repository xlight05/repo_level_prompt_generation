/*
 * @author Kyle Kemp
 */
package commands;

import org.jibble.pircbot.Colors;

import spells.DefendSpell;
import spells.Spell;
import bot.Bot;
import entities.Mob;

public class DefendCommand extends Command {

	public DefendCommand(Bot b) {
		super(b, "defend", "guard");
		setBattle(true);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see commands.Command#execute(entities.Player, java.lang.String,
	 * java.lang.String, java.lang.String)
	 */
	@Override
	public boolean execute(Mob who, String channel, String sender,
			String argmess) {
		Spell s = new DefendSpell(bot);
		s.cast(who, who);
		bot.sendMessage(channel, Colors.BOLD+who + " is defending!");
		return true;
	}
}
