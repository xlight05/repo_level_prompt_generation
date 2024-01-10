/*
 * @author Kyle Kemp
 */
package commands;

import bot.Bot;
import entities.Mob;

public class LeaveCommand extends Command {

	public LeaveCommand(Bot b) {
		super(b, "leave");
	}

	@Override
	public boolean execute(Mob who, String channel, String sender,
			String argmess) {
		
		if(who.getTeam().getInvolvement()!=null){
			bot.sendMessage(channel, "You can't leave in the middle of a battle.");
			return false;
		}
		
		if (who.getTeam() != null) {
			if (who.getTeam().getLeader().equals(who)) {
				bot.getTeams().remove(who.getTeam().getName());
			}
			who.getTeam().getMembers().remove(who);
			who.removeObserver(who.getTeam());
			who.setTeam(null);
			bot.sendMessage(channel, who + ", leaving " + who.getTeam() + "...");
		}
		return false;
	}

}
