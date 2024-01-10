/*
 * @author Kyle Kemp
 */
package commands;

import battle.Battle;
import battle.Team;
import bot.Bot;
import entities.Mob;

public class StartCommand extends Command {

	public StartCommand(Bot b) {
		super(b, "start");
		setMaster(true);
		setGeneral(true);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see commands.Command#execute(entities.Player, java.lang.String,
	 * java.lang.String, java.lang.String)
	 */
	@Override
	public boolean execute(Mob who, String channel, String sender, String args) {

		int count = 0;

		for (Team t : bot.getTeams().values()) {
			if (t != null && t.getInvolvement() == null) {
				count++;
			}
		}

		if (count > 1) {
			Battle b = new Battle(bot, channel);
			for (Team t : bot.getTeams().values()) {
				if (t != null && t.getInvolvement() == null) {
					b.addTeam(t);
					t.setInvolvement(b);
				}
			}
			bot.getBattles().add(b);
			b.initialize();
			return true;

		} else {
			bot.sendMessage(channel,
					"Not enough teams available to start a match.");
		}
		return false;

	}

}
