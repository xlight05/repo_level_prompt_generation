/*
 * @author Kyle Kemp
 */
package commands;

import java.io.File;
import java.lang.reflect.Constructor;

import org.jibble.pircbot.Colors;

import classes.CharacterClass;

import spells.ScriptSpell;
import spells.Spell;
import spells.Spell.Restriction;
import battle.Team;
import bot.Bot;
import entities.Mob;

public class CastCommand extends Command {

	public CastCommand(Bot b) {
		super(b, "cast");
		setBattle(true);
	}

	@Override
	public boolean execute(Mob who, String channel, String sender,
			String argmess) {
try {
		if (checkArgs(argmess, 2)) {
			String[] args = getArgs(argmess, 2);
			Spell s;
			try {
				s = findSpell(who.getCharacterClass(), args[0]);
				if (s == null) {
					bot.sendMessage(Bot.channel,
							"Not a valid spell for you, broski.");
					return false;
				}
				if (s instanceof ScriptSpell) {
					Constructor<?> constructor = s.getClass()
							.getDeclaredConstructor(
									new Class[] { Bot.class, File.class });
					s = (ScriptSpell) constructor.newInstance(bot,
							((ScriptSpell) s).getMyFile());
					s.setCastedClass(who.getCharacterClass());

				} else {
					Constructor<?> constructor = s.getClass()
							.getDeclaredConstructor(new Class[] { Bot.class });
					s = (Spell) constructor.newInstance(bot);
					s.setCastedClass(who.getCharacterClass());
				}
			} catch (Exception e) {
				e.printStackTrace();
				bot.sendMessage(channel, "Something went wrong.. :(");
				return false;
			}
			Restriction r = s
					.getRestriction(who.getCharacterClass().getName());
			if (r == null) {
				bot.sendMessage(channel,
						"You are not eligible to cast this spell.");
				return false;
			}
			if (r.getLevelReq() > who.getCharacterClass().getLevel()
					.getCurrent()) {
				bot.sendMessage(Bot.channel,
						"You are not high enough level to cast this spell!");
				return false;
			}

			CharacterClass c = who.getCharacterClass();
			if(r.getMyType().equals("hp")){
				if(r.getCost() > c.getHp().getCurrent()){
					bot.sendMessage(Bot.channel, "You do not have enough hp to cast this spell!");
					return false;
				}
			} else if (r.getCost() > c.getOther().getCurrent()) {
				bot.sendMessage(Bot.channel, "You do not have enough "
						+ who.getCharacterClass().getOther().getName()
						+ " to cast this spell!");
				return false;
			}
			Mob m = findMob(args[1]);
			//TODO: make casting at self actually work.
			if (m != null) {
				bot.sendMessage(channel, Colors.BOLD+"Casting " + s + " at " + args[1]
						+ "..");
				s.cast(who, m);
				who.sendState(SKILL_USE, who, m);
				return true;
			} else {
				Team t = findTeam(args[1]);
				if(t!=null){
					bot.sendMessage(channel, Colors.BOLD+"Casting " + s + " at team " + args[1]
					                                						+ "..");
					s.cast(who, t);
					return true;
				} else {
					bot.sendMessage(channel, Colors.BOLD+"Casting " + s + " at self..");
					s.cast(who, who);
					return true;
				}
			}

		} else if (checkArgs(argmess, 1)) {
			String[] args = getArgs(argmess, 1);
			if (args[0].equals("help")) {
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
											+ who.getCharacterClass()
													.getOther().getName() + ".");
						}
					}
				}
			}

		} else {
			bot.sendMessage(Bot.channel,
					"You must put it in the format !cast [spell] [target]");
		}
}catch(Exception e){
	e.printStackTrace();
}
		return false;
	}

}
