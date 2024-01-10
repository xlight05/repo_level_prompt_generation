package org.rsbot.script.randoms;

import org.rsbot.script.Random;
import org.rsbot.script.ScriptManifest;

/**
 * Advanced System Update script that will logout at a random time during a
 * system update.
 * 
 * @author Gnarly
 */
@ScriptManifest(authors = { "Gnarly" }, name = "System Update", version = 1.4)
public class SystemUpdate extends Random {

	private int logoutMinutes;
	private int logoutSeconds;

	@Override
	public boolean activateCondition() {
		if (isLoggedIn() && getInterface(754, 5).getText().startsWith("<col=ffff00>System update in") && !getMyPlayer().isInCombat()) {
			check();
		}
		return false;
	}

	@Override
	public int loop() {
		return -1;
	}
	
	private void check() {
		logoutMinutes = random(1, getMinutes());
		logoutSeconds = random(10, getSeconds());
		while (!checkForLogout()) {
			wait(1000);
		}
		log.info("System update will occur soon, we have logged out.");
	}

	private boolean checkForLogout() {
		if ((getMinutes() < logoutMinutes) && (getSeconds() < logoutSeconds)) {
			stopScript();
			return true;
		} else
			return false;
	}

	private int getMinutes() {
		return Integer.parseInt(getInterface(754, 5).getText().substring(29).trim().split(":")[0]);
	}

	private int getSeconds() {
		return Integer.parseInt(getInterface(754, 5).getText().substring(29).trim().split(":")[1]);
	}

}
