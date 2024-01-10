/*
 * @author Kyle Kemp
 */
package commands;

import bot.Bot;
import entities.Mob;
import entities.Player;

public class LogoutCommand extends Command {

	public LogoutCommand(Bot b) {
		super(b, "logout");
	}

	/*
	 * @Override public boolean execute(Mob who, String channel, String sender,
	 * String argmess) { if (who == null) { bot.sendMessage(sender,
	 * "You're not logged in."); return false; } if
	 * (bot.getPlayers().containsValue(who)) { for (String p :
	 * bot.getPlayers().keySet()) { if (p.equals(sender)) { if
	 * (who.getCharacterClass() != null) { if (Bot.xml.savePlayer(who)) {
	 * bot.sendMessage(sender, "Successfully saved \"" + who.getName() + "\"");
	 * return true; } else { bot.sendMessage(sender,
	 * "Unable to save your character. Report this?"); } }
	 * bot.getPlayers().remove(sender); bot.sendMessage(sender,
	 * "Successfully logged out."); bot.sendMessage(Bot.channel, who +
	 * " has logged out."); } } } else { bot.sendMessage(sender,
	 * "You still aren't logged in. Stop trying to get past this check."); }
	 * return false; }
	 */

	@Override
	public boolean execute(Mob who, String channel, String sender,
			String argmess) {
		
		if(who.getTeam()!=null){
			bot.sendMessage(channel, "You need to leave your teams before you can logout.");
			return false;
		}
		
		Bot.xml.savePlayer((Player)who);
		
		bot.getPlayers().remove(sender);
		bot.sendMessage(channel, "You have probably been successfully logged out.");
		
		return true;
	}
}
