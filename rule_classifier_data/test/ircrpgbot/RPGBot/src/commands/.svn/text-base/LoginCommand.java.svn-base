/*
 * @author Kyle Kemp
 */
package commands;

import bot.Bot;
import entities.Mob;
import entities.Player;

public class LoginCommand extends Command {

	public LoginCommand(Bot b) {
		super(b, "login", "l", "log");
		isGeneral = true;
		isPM = true;
	}

	@Override
	public boolean execute(Mob who, String channel, String sender,
			String argmess) {

		if (who != null) {
			bot.sendMessage(sender, "You're already logged in.");
			return false;
		}
		if (checkArgs(argmess, 2)) {
			try {
				String[] args = getArgs(argmess.trim(), 2);
				for (Player p : bot.getPlayers().values()) {
					if (p.getName().equals(args[0])) {
						bot.sendMessage(sender, args[0]
								+ " is already logged in.");
						return false;
					}
				}
				Player p = Bot.xml.loadPlayer(args[0], args[1], bot);
				if (p != null) {
					bot.sendMessage(sender,
							"You've successfully logged in as \"" + args[0]
									+ "\"");
					bot.getPlayers().put(sender, p);
					bot.sendMessage(Bot.channel, sender + " has logged in as "
							+ p + ".");
					if (p.getCharacterClass() != null) {
						bot.sendMessage(
								sender,
								"Your current class is: "
										+ p.getCharacterClass());
						bot.sendMessage(sender,
								"Your current stats are: " + p.getStats());
						return true;
					}
				} else {
					bot.sendMessage(sender,
							"Invalid login info, or nonexistant character.");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

		} else {
			bot.sendMessage(sender,
					"To login, you have to tell me: !login name password");
		}
		return false;

	}
}
