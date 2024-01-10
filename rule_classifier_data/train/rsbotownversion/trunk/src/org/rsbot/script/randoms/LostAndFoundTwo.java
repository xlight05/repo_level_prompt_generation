package org.rsbot.script.randoms;

import org.rsbot.script.Random;
import org.rsbot.script.ScriptManifest;

@ScriptManifest(name = "LostAndFoundTwo", authors = "Garrett", version = 1.0)
public class LostAndFoundTwo extends Random {

	@Override
	public boolean activateCondition() {
		return (getInterface(210, 1).containsText("The Abyssal Services Department apologises for the inconvenience.") && (getInterface(210, 1).getAbsoluteY() > 380) && (getInterface(210, 1).getAbsoluteY() < 410));
	}

	@Override
	public int loop() {
		if (canContinue()) {
			clickContinue();
			wait(random(1000, 1200));
		}
		atInterface(210, 1);
		return -1;
	}

}
