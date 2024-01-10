/*
 * @author Kyle Kemp
 */
package commands;

import bot.Bot;
import entities.Mob;

public class AttackCommand extends Command {
	
	public AttackCommand(Bot b) {
		super(b, "attack", "a");
		isBattle = true;
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
		if (checkArgs(argmess, 1)) {
			String[] args = getArgs(argmess, 1);
			Mob m = findMob(args[0]);
			if(m.equals(who)){
				bot.sendMessage(channel, "You can't attack yourself, silly.");
				return false;
			}
			if (m != null) {
				if (m.canEventHappen(ATTACK, who)) {
					who.attack(m);
				} else {
					bot.sendMessage(
							channel,
							"The attack was unable to be completed (try again, and stop hitting dead people!)!");
					// who.sendState(MISS, who, m);
					return false;
				}
				return true;
			}
		} else {
			bot.sendMessage(sender,
					"To attack, you have to tell me: !attack [target]");
		}
		return false;
	}

}
