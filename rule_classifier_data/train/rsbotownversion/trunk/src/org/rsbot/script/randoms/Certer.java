package org.rsbot.script.randoms;

import org.rsbot.script.Random;
import org.rsbot.script.ScriptManifest;
import org.rsbot.script.wrappers.RSInterfaceChild;
import org.rsbot.script.wrappers.RSNPC;
import org.rsbot.script.wrappers.RSObject;
import org.rsbot.script.wrappers.RSTile;

/*
 * Certer Random event solver
 * Coding by joku.rules, Fixed by FuglyNerd
 * Interface data taken from Certer solver by Nightmares18
 *
 */

@ScriptManifest(authors = { "joku.rules" }, name = "Certer Solver", version = 1.0)
public class Certer extends Random {
	private final int[] MODEL_IDS = { 2807, 8828, 8829, 8832, 8833, 8834, 8835, 8836, 8837 };
	private final int[] bookPiles = { 42352, 42354 };
	/*
	 * private final RSTile certerLocation() { int myX =
	 * getMyPlayer().getLocation().getX(); int centY = 9494; RSTile theLoc = new
	 * RSTile(myX, centY); return theLoc; }
	 */
	private final String[] ITEM_NAMES = { "bowl", "battleaxe", "fish", "shield", "helmet", "ring", "shears", "sword", "spade" };
	private final int PORTAL_ID = 11368;

	private boolean readyToLeave = false;
	private int failCount = 0;

	@Override
	public boolean activateCondition() {
		return isLoggedIn() && (getNearestObjectByID(bookPiles) != null);
	}

	@Override
	public int loop() {
		if (!activateCondition() && readyToLeave) {
			readyToLeave = false;
			failCount = 0;
			log("I think we've solved the certer");
			return -1;
		}

		if (getInterface(241, 4).containsText("Ahem, ")) {
			readyToLeave = false;
		}

		if (getInterface(241, 4).containsText("Correct.") || getInterface(241, 4).containsText("You can go now.")) {
			readyToLeave = true;
		}

		if (readyToLeave) {
			final RSObject portal = getNearestObjectByID(PORTAL_ID);
			if (portal != null) {
				final RSTile portalLocation = portal.getLocation();
				if (distanceTo(portal) < 4) {
					atObject(portal, "Enter");
					return random(3000, 4000);
				} else {
					walkTileMM(randomizeTile(new RSTile(portalLocation.getX() - 1, portalLocation.getY()), 1, 1));
					return random(6000, 8000);
				}
			}
		}

		if (getInterface(184, 0).isValid()) {
			final int modelID = getInterface(184, 8).getComponents()[3].getModelID();
			String itemName = null;
			for (int i = 0; i < MODEL_IDS.length; i++) {
				if (MODEL_IDS[i] == modelID) {
					itemName = ITEM_NAMES[i];
				}
			}

			if (itemName == null) {
				log("The object couldn't be identified! ID: " + modelID);
				failCount++;
				if (failCount > 10) {
					stopScript();
					return -1;
				}
				return random(1000, 2000);
			}

			for (int j = 0; j < 3; j++) {
				final RSInterfaceChild iface = getInterface(184, 8).getComponents()[j];
				if (iface.containsText(itemName)) {
					atInterface(iface);
					return random(3000, 5000);
				}
			}
		}

		if (canContinue()) {
			clickContinue();
			return random(3000, 4000);
		}

		final RSNPC certer = getNearestNPCByName("Niles", "Miles", "Giles");
		if (certer != null) {
			if (distanceTo(certer) < 4) {
				clickRSNPC(certer, "Talk-to");
				return random(4000, 5000);
			} else {
				final RSTile certerLocation = certer.getLocation();
				walkTileMM(randomizeTile(new RSTile(certerLocation.getX() + 2, certerLocation.getY()), 1, 1));
				return random(6000, 8000);
			}
		}

		failCount++;
		if (failCount > 10) {
			stopScript();
			return -1;
		}
		return random(1000, 2000);
	}
}