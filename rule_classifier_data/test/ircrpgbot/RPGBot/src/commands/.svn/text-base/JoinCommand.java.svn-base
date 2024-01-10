/*
 * @author Kyle Kemp
 */
package commands;

import battle.Team;
import bot.Bot;
import entities.Mob;

public class JoinCommand extends Command {

	public JoinCommand(Bot b) {
		super(b, "join", "j");
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

		if (who.getCharacterClass() == null) {
			bot.sendMessage(channel,
					"You cannot join if you have no character class, " + sender);
			return false;
		}

		if (who.getTeam() != null && who.getTeam().getInvolvement() != null) {
			bot.sendMessage(channel,
					"You can't leave in the middle of a battle!");
			return false;
		}

		if (checkArgs(argmess, 1)) {
			String[] args = getArgs(argmess, 1);
			if (args[0].equals("join")) {
				args[0] = "Adventurers";
			}
			if (bot.getTeams().containsKey(args[0])) {
				Team t = bot.getTeams().get(args[0]);
				if (t == null) {
					bot.getTeams().remove(args[0]);
					bot.sendMessage(channel, "That team is broken!");
					return false;
				}
				try {
					if (t.getInvolvement() != null) {
						bot.sendMessage(channel,
								"That team is in the midst of combat!");
						return false;
					}
					if (t.getMembers().contains(who)) {
						bot.sendMessage(channel, "You're already in team " + t
								+ ".");
						return false;
					}

					if (who.getTeam() != null) {
						if (who.getTeam().getLeader().equals(who)) {
							bot.getTeams().remove(who.getTeam().getName());
						}
						who.getTeam().getMembers().remove(who);
						who.removeObserver(who.getTeam());
						bot.sendMessage(channel,
								who + ", leaving " + who.getTeam() + "...");
					}

					if (t.addMember(who)) {
						bot.sendMessage(channel, "Successfully joined " + t
								+ ".");
					} else {
						bot.sendMessage(channel, "Unable to join " + t + ".");
					}
				} catch (Exception e) {
					e.printStackTrace();
				}

			} else {
				if (who.getTeam() != null) {
					if (who.getTeam().getLeader().equals(who)) {
						bot.getTeams().remove(who.getTeam().getName());
					}
					who.getTeam().getMembers().remove(who);
					who.removeObserver(who.getTeam());
					bot.sendMessage(channel, who + ", leaving " + who.getTeam()
							+ "...");
				}
				Team t = new Team(args[0], who);
				bot.getTeams().put(args[0], t);
				bot.sendMessage(channel, "Successfully created team " + t
						+ " with " + t.getLeader() + " as the leader.");
			}
			return true;
		} else {
			bot.sendMessage(channel,
					"To join a team, you must tell me: join [teamname]");
		}
		return false;

	}

}
