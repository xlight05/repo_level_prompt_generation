package commands;

import entities.Mob;
import bot.Bot;

public class LevelupCommand extends Command{

	public LevelupCommand(Bot b) {
		super(b, "levelup", "gainlevel");
		setMaster(true);
	}

	@Override
	public boolean execute(Mob who, String channel, String sender,
			String argmess) {
		if(checkArgs(argmess, 1)){
			String[] args = getArgs(argmess, 1);
			Mob m = findMob(args[0]);
			if(m == null){
				bot.sendMessage(channel, "Who is "+args[0]+"?");
				return false;
			}
			try{
			m.getCharacterClass().getExp().toMaximum();
			m.getCharacterClass().gainExp(0);
			bot.sendMessage(sender, "Successfully gave "+m+" a levelup.");
			}catch(Exception e){
				e.printStackTrace();
			}
		} else {
			bot.sendMessage(channel, "Format is: !levelup [name]");
		}
		return false;
	}

}
