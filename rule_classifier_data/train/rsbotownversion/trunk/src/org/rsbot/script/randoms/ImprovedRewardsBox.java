package org.rsbot.script.randoms;

import java.awt.Rectangle;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import org.rsbot.script.Random;
import org.rsbot.script.ScriptManifest;
import org.rsbot.script.wrappers.RSInterfaceComponent;

@ScriptManifest(authors = {"Fred"}, name = "Improved Rewards Box", version = 1.1)
public class ImprovedRewardsBox extends Random {

	Rectangle temp;

	String[] choices = {"Cash", "Runes", "Coal", "Essence", "Ore", "Bars", "Gems", "Herbs", "Seeds", "Charms", "XP item", "Surprise", "Emote", "Costume"};
	String XPChoice = "Attack";

	final int bookKnowledgeID = 11640;
	final int LampID = 2528;
	final int mysteryBoxID = 6199;
	final int boxID = 14664;
	final int boxIF = 202;
	final int boxConfirmIF = 28;
	final int boxSelectionIF = 15;
	final int boxScrollbarIF = 24;
	int optionSelected = 999;

	final int XPIF = 134;
	final int ATT_ID = 4;
	final int AGILITY_ID = 5;
	final int HERBLORE_ID = 6;
	final int FISHING_ID = 7;
	final int THIEVING_ID = 8;
	final int RUNECRAFTING_ID = 9;
	final int SLAYER_ID = 10;
	final int FARMING_ID = 11;
	final int MINING_ID = 12;
	final int SMITHING_ID = 13;
	final int HUNTER_ID = 14;
	final int COOKING_ID = 15;
	final int FIREMAKING_ID = 16;
	final int WOODCUTTING_ID = 17;
	final int FLETCHING_ID = 18;
	final int CONSTRUCTION_ID = 19;
	final int SUMMONING_ID = 20;
	final int STRENGTH_ID = 21;
	final int RANGED_ID = 22;
	final int MAGIC_ID = 23;
	final int DEFENCE_ID = 24;
	final int HITPOINTS_ID = 25;
	final int CRAFTING_ID = 26;
	final int PRAYER_ID = 27;
	final int CONFIRM_ID = 2;

	int scrollbarBottomLength;
	int scrollbarTopLength;
	int hiddenScreenHeight;
	double difference;
	int endofselection = 0;

	public ImprovedRewardsBox() {
		setEnforceTabFocus(false);
	}

	public boolean activateCondition() {
		return isLoggedIn() && !getMyPlayer().isInCombat() && (inventoryContains(boxID) || inventoryContains(bookKnowledgeID) || inventoryContains(LampID) || inventoryContains(mysteryBoxID));
	}


	public int getActualY(RSInterfaceComponent Component) {
		int boxYPos;
		RSInterfaceComponent[] selection = getInterface(202).getChild(15).getComponents();
		RSInterfaceComponent[] scrollbar = getInterface(202).getChild(24).getComponents();
		for (int end = 0; end < selection.length; end++) {
			if (selection[end].containsText(":")) {
				endofselection = (end - 6);
			}
			if (selection[end].containsText("emote")) {
				endofselection = (end - 6);
			}
			if (selection[end].containsText("costume")) {
				endofselection = (end - 6);
			}
		}
		int viewableScreenHeight = (getInterface(202).getChild(15).getHeight() - 11);
		int totalScreenHeight = (selection[endofselection].getAbsoluteY() + selection[endofselection].getHeight() - selection[0].getAbsoluteY());
		hiddenScreenHeight = (totalScreenHeight - viewableScreenHeight);
		if (hiddenScreenHeight > 0) {
			scrollbarTopLength = (scrollbar[1].getAbsoluteY() - scrollbar[0].getAbsoluteY());
			difference = (Double.parseDouble(Integer.toString(scrollbarTopLength)) / Double.parseDouble(Integer.toString(scrollbarBottomLength)) * Double.parseDouble(Integer.toString(hiddenScreenHeight)));
			boxYPos = (Component.getAbsoluteY() - (int) difference);
		} else {
			boxYPos = Component.getAbsoluteY();
		}
		return boxYPos;
	}

	public Rectangle getBoxArea(RSInterfaceComponent Component) {
		return new Rectangle(Component.getAbsoluteX(), getActualY(Component), Component.getWidth(), Component.getHeight());
	}

	public int loop() {
		if (getInterface(boxIF).isValid()) {
			String os = System.getProperty("os.name");
			String filename;
			if (os.contains("Windows")) {
				filename = System.getenv("APPDATA") + File.separator + "RewardsChoices.ini";
			} else {
				String home = System.getProperty("user.home");
				filename = (home == null ? "~" : home) + File.separator + ".rewardschoices";
			}
			File RewardsChoiceFile = new File(filename);
			if (RewardsChoiceFile.exists()) {
				try {
					BufferedReader in = new BufferedReader(new FileReader(filename));
					String inputLine = "";
					int choicenumber = 0;
					while ((inputLine = in.readLine()) != null) {
						if (choicenumber > (choices.length - 1)) {
							XPChoice = inputLine;
							break;
						}
						choices[choicenumber] = inputLine;
						choicenumber++;
					}
				} catch (final Exception e) {
					log("Error opening");
				}
			}
			RSInterfaceComponent[] selection = getInterface(boxIF).getChild(boxSelectionIF).getComponents();
			for (String choice : choices) {
				for (int i = 0; i < selection.length; i++) {
					if (selection[i].getText().toLowerCase().contains(choice.toLowerCase())) {
						optionSelected = i - 6;
						break;
					}
				}
				if (optionSelected != 999) {
					break;
				}
			}
			if (optionSelected == 999) {
				optionSelected = 0;
			}
			temp = getBoxArea(selection[optionSelected]);
			if (getBoxArea(selection[optionSelected]).y > 278) {
				RSInterfaceComponent[] scrollbar = getInterface(boxIF).getChild(boxScrollbarIF).getComponents();
				scrollbarBottomLength = (scrollbar[5].getAbsoluteY() - scrollbar[3].getAbsoluteY() + scrollbar[3].getHeight() - 6);
				moveMouse(scrollbar[1].getPoint().x + random(-4, 4), scrollbar[1].getPoint().y + random(-15, 15));
				int toDragtoY = (int) getMouseLocation().getY() + (getBoxArea(selection[optionSelected]).y - selection[0].getAbsoluteY());
				if ((toDragtoY - (int) getMouseLocation().getY()) > (scrollbar[5].getAbsoluteY() - scrollbar[3].getAbsoluteY() + scrollbar[3].getHeight() - 6)) {
					toDragtoY = (int) getMouseLocation().getY() + (scrollbar[5].getAbsoluteY() - scrollbar[3].getAbsoluteY() + scrollbar[3].getHeight() - 6);
				}
				dragMouse((int) getMouseLocation().getX(), toDragtoY);
			}

			/*
			 * if (getBoxArea(selection[optionSelected]).y +
			 * getBoxArea(selection[optionSelected]).height < 135) {
			 * RSInterfaceComponent[] scrollbar =
			 * getInterface(boxIF).getChild(boxScrollbarIF).getComponents();
			 * moveMouse(scrollbar[1].getPoint().x + random(-4, 4),
			 * scrollbar[1].getPoint().y + random(-15, 15)); int toDragtoY =
			 * (int) getMouseLocation().getY() +
			 * (getBoxArea(selection[optionSelected]).y -
			 * selection[0].getAbsoluteY()); if (((int)
			 * getMouseLocation().getY() - toDragtoY) >
			 * (scrollbar[1].getAbsoluteY() - scrollbar[0].getAbsoluteY())) {
			 * toDragtoY = (int) getMouseLocation().getY() -
			 * (scrollbar[1].getAbsoluteY() - scrollbar[0].getAbsoluteY()); }
			 * dragMouse((int) getMouseLocation().getX(), toDragtoY); }
			 */
			wait(random(3000, 4000));
			selection = getInterface(boxIF).getChild(boxSelectionIF).getComponents();
			int boxX = getBoxArea(selection[optionSelected]).x + 15;
			int boxY = getBoxArea(selection[optionSelected]).y + 15;
			int boxWidth = getBoxArea(selection[optionSelected]).width - 30;
			int boxHeight = getBoxArea(selection[optionSelected]).height - 30;
			temp = getBoxArea(selection[optionSelected]);
			moveMouse(random(boxX, boxX + boxWidth), random(boxY, boxY + boxHeight));
			clickMouse(true);
			atInterface(boxIF, boxConfirmIF);
			wait(random(3000, 4000));
		}
		if (getInterface(XPIF).isValid()) {
			String os = System.getProperty("os.name");
			String filename;
			if (os.contains("Windows")) {
				filename = System.getenv("APPDATA") + File.separator + "RewardsChoices.ini";
			} else {
				String home = System.getProperty("user.home");
				filename = (home == null ? "~" : home) + File.separator + ".rewardschoices";
			}
			File RewardsChoiceFile = new File(filename);
			if (RewardsChoiceFile.exists()) {
				try {
					BufferedReader in = new BufferedReader(new FileReader(filename));
					String inputLine = "";
					int choicenumber = 0;
					while ((inputLine = in.readLine()) != null) {
						if (choicenumber > (choices.length - 1)) {
							XPChoice = inputLine;
							break;
						}
						choices[choicenumber] = inputLine;
						choicenumber++;
					}
				} catch (final Exception e) {
					log("Error opening");
				}
			}
			int XPSelection = 0;
			if (XPChoice.contains("Attack")) {
				XPSelection = ATT_ID;
			}
			if (XPChoice.contains("Strength")) {
				XPSelection = STRENGTH_ID;
			}
			if (XPChoice.contains("Defence")) {
				XPSelection = DEFENCE_ID;
			}
			if (XPChoice.contains("Ranged")) {
				XPSelection = RANGED_ID;
			}
			if (XPChoice.contains("Prayer")) {
				XPSelection = PRAYER_ID;
			}
			if (XPChoice.contains("Magic")) {
				XPSelection = MAGIC_ID;
			}
			if (XPChoice.contains("Runecrafting")) {
				XPSelection = RUNECRAFTING_ID;
			}
			if (XPChoice.contains("Construction")) {
				XPSelection = CONSTRUCTION_ID;
			}
			if (XPChoice.contains("Hitpoints")) {
				XPSelection = HITPOINTS_ID;
			}
			if (XPChoice.contains("Agility")) {
				XPSelection = AGILITY_ID;
			}
			if (XPChoice.contains("Herblore")) {
				XPSelection = HERBLORE_ID;
			}
			if (XPChoice.contains("Thieving")) {
				XPSelection = THIEVING_ID;
			}
			if (XPChoice.contains("Crafting")) {
				XPSelection = CRAFTING_ID;
			}
			if (XPChoice.contains("Fletching")) {
				XPSelection = FLETCHING_ID;
			}
			if (XPChoice.contains("Slayer")) {
				XPSelection = SLAYER_ID;
			}
			if (XPChoice.contains("Hunter")) {
				XPSelection = HUNTER_ID;
			}
			if (XPChoice.contains("Mining")) {
				XPSelection = MINING_ID;
			}
			if (XPChoice.contains("Smithing")) {
				XPSelection = SMITHING_ID;
			}
			if (XPChoice.contains("Fishing")) {
				XPSelection = FISHING_ID;
			}
			if (XPChoice.contains("Cooking")) {
				XPSelection = COOKING_ID;
			}
			if (XPChoice.contains("Firemaking")) {
				XPSelection = FIREMAKING_ID;
			}
			if (XPChoice.contains("Woodcutting")) {
				XPSelection = WOODCUTTING_ID;
			}
			if (XPChoice.contains("Farming")) {
				XPSelection = FARMING_ID;
			}
			if (XPChoice.contains("Summoning")) {
				XPSelection = SUMMONING_ID;
			}
			atInterface(XPIF, XPSelection);
			atInterface(XPIF, CONFIRM_ID);
			wait(random(3000, 4000));
		}
		if (inventoryContains(boxID)) {
			atInventoryItem(boxID, "Drop");
			return random(3000, 4000);
		}
		if (inventoryContains(bookKnowledgeID)) {
			atInventoryItem(bookKnowledgeID, "Read");
			return random(3000, 4000);
		}
		if (inventoryContains(LampID)) {
			atInventoryItem(LampID, "Rub");
			return random(3000, 4000);
		}
		if (inventoryContains(mysteryBoxID)) {
			atInventoryItem(mysteryBoxID, "Open");
			return random(3000, 4000);
		}
		return -1;
	}

}