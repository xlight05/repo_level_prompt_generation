/*
 * @author Kyle Kemp
 */
package commands;

import java.util.Date;

import bot.Bot;
import entities.Mob;

public class RegisterCommand extends Command {

	public RegisterCommand(Bot b) {
		super(b, "register", "r", "reg");
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
			String[] args = getArgs(argmess.trim(), 2);
			if (args[0].length() < 1) {
				bot.sendMessage(sender,
						"Your character name needs to be longer than 1 character.");
				return false;
			}
			if (Bot.xml.createNewPlayer(args[0], args[1], sender,
					new Date().toString())) {
				bot.sendMessage(sender, "You've successfully registered \""
						+ args[0] + "\"");
				return true;
			} else {
				bot.sendMessage(sender,
						"An error occured -- that name is probably taken.");
			}

		} else {
			bot.sendMessage(sender,
					"To register, you have to tell me: !register name password");
		}
		return false;

	}

}
