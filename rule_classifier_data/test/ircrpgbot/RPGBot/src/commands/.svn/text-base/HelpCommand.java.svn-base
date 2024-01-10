/*
 * @author Kyle Kemp
 */
package commands;

import spells.Spell;
import spells.Spell.Restriction;
import bot.Bot;
import entities.Mob;

public class HelpCommand extends Command {

	public HelpCommand(Bot b) {
		super(b, "help", "?");
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

		if(checkArgs(argmess, 1)){
			String[] args = getArgs(argmess, 1);
			if(args[0].equals("?")){
				showStats(who, sender);
				return true;
			}
			Mob m = findMob(args[0]);
			if(m==null){
				bot.sendMessage(channel, "Could not find "+args[0]);
				return false;
			}
			showStats(m, sender);
			return true;
		} 


		return true;
	}

	private void showStats(Mob who, String sender) {

		if (who == null || who.getCharacterClass() == null) {
			bot.sendMessage(sender, "There is no character class present!");
			return;
		}
		
		bot.sendMessage(sender, "["+who+"] Current Class: " + who.getCharacterClass());
		bot.sendMessage(sender, "["+who+"] Current Stats: " + who.getStats());
		bot.sendMessage(sender, "["+who+"] Available Spells: ");
		for (Spell s : bot.getSpells().get(
				who.getCharacterClass().getName())) {
			for (Restriction r : s.getCanCast()) {
				if (r.getMyClass().equals(who.getCharacterClass().getName()) && 
						r.getLevelReq() <= who.getCharacterClass()
						.getLevel().getTotal()) {
					bot.sendMessage(sender,
							""
									+ s
									+ " - "
									+ s.getDescription()
									+ " "
									+ r.getCost()
									+ " "
									+ (r.getMyType().equals("hp") ? "hp" : who.getCharacterClass()
											.getOther().getName()) + ".");
				}
			}
		}
	}

}
