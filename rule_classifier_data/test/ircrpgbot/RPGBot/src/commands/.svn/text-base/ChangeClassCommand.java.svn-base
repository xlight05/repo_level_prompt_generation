/*
 * @author Kyle Kemp
 */
package commands;

import java.lang.reflect.InvocationTargetException;

import xml.ClassCreator;
import bot.Bot;
import classes.CharacterClass;
import classes.CharacterClass.Restriction;
import entities.Mob;
import entities.Player;

public class ChangeClassCommand extends Command {

	public ChangeClassCommand(Bot b) {
		super(b, "change", "class", "cc");
		setPM(true);
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
			if (who.getTeam() != null && who.getTeam().getInvolvement() != null) {
				bot.sendMessage(channel,
						"You can't change classes in a battle, newb.");
				return false;
			}
			CharacterClass newclass = null;
			for (String s : bot.getClasses().keySet()) {
				if (s.equals(args[0])) {
					newclass = bot.getClasses().get(s);
					break;
				}
			}
			if (newclass == null) {
				bot.sendMessage(channel, "There is no such class, newb.");
				return false;
			}
			if (who.getCharacterClass() != null
					&& newclass.getName().equals(
							who.getCharacterClass().getName())) {
				bot.sendMessage(channel, who + ", you are already a "
						+ newclass + ".");
				return false;
			}
			try {
				Bot.xml.savePlayer((Player) who);
				if(newclass.getClassRestrictions().size()!=0){
					for(Restriction s : newclass.getClassRestrictions()){
						CharacterClass c = Bot.xml.loadClass(s.getMyClass(),
								who.getName(), bot);

						if(c!=null){
							if(c.getLevel().getCurrent() < s.getLevelReq()){
								bot.sendMessage(sender, "You are not a powerful enough "+c.getName()+"!");
								return false;
							}
						} else {
							bot.sendMessage(sender, "You are not a powerful enough "+s.getMyClass()+"!");
							return false;
						}
					}
					checkAndSetCharacter(who, args, newclass);
				} else {
					checkAndSetCharacter(who, args, newclass);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			bot.sendMessage(channel, "Successfully changed classes to: "
					+ args[0]);
			bot.sendMessage(channel, who.getStats());
			Bot.xml.savePlayer((Player) who);
		}
		return true;
	}

	private void checkAndSetCharacter(Mob who, String[] args,
			CharacterClass newclass) throws InstantiationException,
			IllegalAccessException, NoSuchMethodException,
			InvocationTargetException {
		CharacterClass c = Bot.xml.loadClass(args[0],
				who.getName(), bot);
		if (c != null) {
			c.setMyMob(who);
			who.setCharacterClass(c);
		} else {
			who.setCharacterClass(ClassCreator.createNewClass(newclass));
			who.getCharacterClass().setMyMob(who);
		}
	}
}
