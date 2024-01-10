/*
 * @author Kyle Kemp
 */
package commands;

import classes.ScriptClass;
import bot.Bot;
import entities.Mob;
import entities.Player;

public class ReloadCommand extends Command {

	public ReloadCommand(Bot b) {
		super(b, "reload");
		setMaster(true);
		setGeneral(true);
	}

	@Override
	public boolean execute(Mob who, String channel, String sender,
			String argmess) {

		bot.initialize();
		bot.sendMessage(Bot.channel, "Reloaded myself.");
		for(Player p : bot.getPlayers().values()){
			if(p.getCharacterClass() instanceof ScriptClass){
				ScriptClass sc = (ScriptClass) p.getCharacterClass();
				sc.setMyScript(((ScriptClass)bot.getClasses().get(sc.getName())).getMyScript());
				sc.initialize();
			}
		}
		return false;
	}

}
