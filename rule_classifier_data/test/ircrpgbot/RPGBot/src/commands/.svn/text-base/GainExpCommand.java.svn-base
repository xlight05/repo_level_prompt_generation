/*
 * @author Kyle Kemp
 */
package commands;

import entities.Mob;
import bot.Bot;

public class GainExpCommand extends Command {

	public GainExpCommand(Bot b) {
		super(b, "gainexp", "giveexp");
		setMaster(true);
		setGeneral(true);
	}

	@Override
	public boolean execute(Mob who, String channel, String sender,
			String argmess) {
		if(checkArgs(argmess, 2)){
			String[] args = getArgs(argmess, 2);
			Mob m = findMob(args[0]);
			if(m == null){
				bot.sendMessage(channel, "Who is "+args[0]+"?");
				return false;
			}
			int i = Integer.parseInt(args[1]);
			m.getCharacterClass().gainExp(i);
			bot.sendMessage(sender, "Successfully gave "+m+" "+i+" exp.");
		} else {
			bot.sendMessage(channel, "Format is: !gainexp [name] [amount]");
		}
		return false;
	}

}
