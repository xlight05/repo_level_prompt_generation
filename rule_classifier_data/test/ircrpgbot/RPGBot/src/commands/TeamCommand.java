/*
 * @author Kyle Kemp
 */
package commands;

import battle.Team;
import bot.Bot;
import entities.Mob;

public class TeamCommand extends Command {

	public TeamCommand(Bot b) {
		super(b, "teams");
		setGeneral(true);
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

		if (bot.getTeams().values().size() == 0) {
			bot.sendMessage(sender, "There are currently no teams available.");
			return false;
		}

		bot.sendMessage(sender, "Current teams registered: ");

		for (Team t : bot.getTeams().values()) {
			bot.sendMessage(sender, "" + t + ", led by " + t.getLeader() + ": "
					+ t.getMembers());
		}
		return true;
	}

}
