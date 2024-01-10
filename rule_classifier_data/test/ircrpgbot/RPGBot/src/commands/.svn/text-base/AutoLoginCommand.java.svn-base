package commands;

import entities.Mob;
import bot.Bot;

public class AutoLoginCommand extends Command {

	public AutoLoginCommand(Bot b) {
		super(b, "auto");
		setPM(true);
	}

	@Override
	public boolean execute(Mob who, String channel, String sender,
			String argmess) {
		bot.sendMessage(sender, "Autologin: "+Bot.xml.setAutoLogin(who.getName()));
		return false;
	}

}
