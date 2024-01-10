/*
 * @author Kyle Kemp
 */
package commands;

import bot.Bot;
import entities.Mob;
import entities.Player;

public class PlayersCommand extends Command {

	public PlayersCommand(Bot b) {
		super(b, "players");
		setGeneral(true);
	}

	@Override
	public boolean execute(Mob who, String channel, String sender,
			String argmess) {
		bot.sendMessage(sender, "Current players online: ");
		for (Player p : bot.getPlayers().values()) {
			bot.sendMessage(sender, p.getName() + " - Level "
					+ p.getCharacterClass().getLevel().getCurrent() + " "
					+ p.getCharacterClass().getName());
		}
		return false;
	}

}
