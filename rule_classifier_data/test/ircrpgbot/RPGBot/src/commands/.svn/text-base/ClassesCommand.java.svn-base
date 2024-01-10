/*
 * @author Kyle Kemp
 */
package commands;

import classes.CharacterClass;
import entities.Mob;
import bot.Bot;

public class ClassesCommand extends Command{

	public ClassesCommand(Bot b) {
		super(b, "classes");
		setPM(true);
	}

	@Override
	public boolean execute(Mob who, String channel, String sender,
			String argmess) {
		for(String s : bot.getClasses().keySet()){
			CharacterClass c = Bot.xml.loadClass(s,
					who.getName(), bot);

			if(c!=null){
				bot.sendMessage(sender, c + ": "+c.getStats());
			}
		}
		return false;
	}

}
