import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.Map;

import org.rsbot.event.events.ServerMessageEvent;
import org.rsbot.event.listeners.PaintListener;
import org.rsbot.event.listeners.ServerMessageListener;
import org.rsbot.script.Script;
import org.rsbot.script.ScriptManifest;
import org.rsbot.script.wrappers.RSItemTile;

@ScriptManifest(authors = { "Grapes" }, name = "Tar Collector", category = "Money-Making", version = 1.2, description = "<center><font size='6'>Tar Collector<font/>"
		+ "<br><font size='4'>By Grapes</font></center>")
public class GrapesSwampTar extends Script implements ServerMessageListener,
		PaintListener {
	private final ScriptManifest info = getClass().getAnnotation(
			ScriptManifest.class);
	public long startTime;
	public long runTime = System.currentTimeMillis() - startTime;
	public int tarID = 1939;
	public int tarPrice;
	public float startingTar;
	public float tarCount;
	public float tarPerHour;
	public float moneyMade;
	public float moneyPerHour;

	@Override
	protected int getMouseSpeed() {
		return random(2, 3);
	}

	public boolean onStart(Map<String, String> args) {
		startTime = System.currentTimeMillis();
		tarPrice = grandExchange.loadItemInfo(tarID).getMarketPrice();
		startingTar = getInventoryCount(true);
		log("Current price of Swamp tar: " + tarPrice);
		return true;
	}

	public void pickUp() {
		RSItemTile tarLoc = getNearestGroundItemByID(tarID);
		while (tarLoc == null) {
			wait(random(100, 300));
		}
		if (!tileOnScreen(tarLoc)) {
			walkTo(tarLoc);
			wait(random(100, 200));
		}
		if (tileOnScreen(tarLoc)) {
			atTile(tarLoc, "Take");
			wait(random(1000, 1100));
			while (getMyPlayer().isMoving()) {
				wait(random(400, 500));
			}
		}
	}

	public void antiban() {
		int lolwut = random(1, 11);
		if (lolwut == 1) {
			setCameraRotation(random(1, 359));
		}
		if (lolwut == 2) {
			setCameraAltitude(true);
		}
		if (lolwut == 3) {
			clickMouse(random(555, 577), random(470, 497), true);
			wait(random(1500, 2000));
			openTab(TAB_INVENTORY);
		}
		if (lolwut == 4) {
			moveMouse(random(0, 515), random(0, 335));
			wait(random(300, 700));
		}
	}

	@Override
	public int loop() {
		int lol = random(1, 5000);
		int ran = random(1, 10);
		getMouseSpeed();
		if (!getMyPlayer().isMoving()) {
			pickUp();
		}
		if (!isRunning() && getEnergy() > random(40, 60)) {
			clickMouse(random(715, 732), random(103, 115), true);
		}
		if (ran < 3) {
			getMouseSpeed();
		}
		if (lol < 50) {
			antiban();
		}
		return random(100, 200);
	}

	@Override
	public void serverMessageRecieved(ServerMessageEvent e) {
	}

	@Override
	public void onRepaint(Graphics g) {
		if (isLoggedIn()) {
			runTime = System.currentTimeMillis() - startTime;
			long hours = runTime / (1000 * 60 * 60);
			runTime -= hours * (1000 * 60 * 60);
			long minutes = runTime / (1000 * 60);
			runTime -= minutes * (1000 * 60);
			long seconds = runTime / 1000;
			float tarPerSec = 0;
			if ((minutes > 0 || hours > 0 || seconds > 0) && tarCount > 0) {
				tarPerSec = (float) tarCount
						/ (float) (seconds + minutes * 60 + hours * 60 * 60);
			}
			final float tarPerMin = tarPerSec * 60;
			final float tarPerHour = tarPerMin * 60;
			tarCount = getInventoryCount(true) - startingTar;
			moneyMade = tarCount * tarPrice;
			moneyPerHour = tarPerHour * tarPrice;
			g.setColor(Color.green);
			g.drawRoundRect(550, 408, 183, 47, 10, 10);
			g.setColor(new Color(0, 0, 0, 100));
			g.fillRoundRect(550, 408, 183, 47, 10, 10);
			g.setColor(Color.white);
			g.setFont(new Font("Tahoma", Font.PLAIN, 11));
			g.drawString("Time Running " + hours + ":" + minutes + ":"
					+ seconds, 555, 420);
			g.drawString("Tar Collected " + (int)tarCount + " || Tar/Hour "
					+ (int) tarPerHour, 555, 435);
			g.drawString("Profit " + (int)moneyMade + " || $/Hour "
					+ (int) moneyPerHour, 555, 450);
			g.setFont(new Font("Tahoma", Font.PLAIN, 14));
			g.setColor(Color.white);
			g.drawString("Swamp Tar Collector V" +info.version(), 345, 330);
		}
	}

	public void onFinish() {
		log("Thanks for using.");
	}
}