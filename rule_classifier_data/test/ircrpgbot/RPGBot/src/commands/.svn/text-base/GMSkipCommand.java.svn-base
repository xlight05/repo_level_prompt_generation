/*
 * @author Kyle Kemp
 */
package commands;

import bot.Bot;
import entities.Mob;
import battle.Battle;

public class GMSkipCommand extends Command {

	public GMSkipCommand(Bot b) {
		super(b, "skip");
		setMaster(true);
	}

	@Override
	public boolean execute(Mob who, String channel, String sender,
			String argmess) {

		if(checkArgs(argmess, 1)) {
			String[] args = getArgs(argmess, 1);
			Mob m = findMob(args[0]);
			if(m!=null){
				Battle b = m.getTeam().getInvolvement();
				if(b.getCurTurn().equals(m)){
					b.changeTurn();
					return true;
				} else {
					bot.sendMessage(Bot.channel, "It isn't "+m+"'s turn.");
				}
			} else {
				bot.sendMessage(Bot.channel, "I can't find "+m+".");
			}
		} else {
			bot.sendMessage(Bot.channel, "Who am I supposed to skip?");
		}
		return false;
	}

}
