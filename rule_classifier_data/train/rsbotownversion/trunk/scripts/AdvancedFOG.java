import java.awt.Graphics;
import java.awt.Point;

import org.rsbot.bot.Bot;
import org.rsbot.event.events.ServerMessageEvent;
import org.rsbot.event.listeners.PaintListener;
import org.rsbot.event.listeners.ServerMessageListener;
import org.rsbot.script.Calculations;
import org.rsbot.script.Script;
import org.rsbot.script.ScriptManifest;
import org.rsbot.script.wrappers.RSInterface;
import org.rsbot.script.wrappers.RSObject;
import org.rsbot.script.wrappers.RSPlayer;
import org.rsbot.script.wrappers.RSTile;

@ScriptManifest(authors = "Matt123337", name = "Advanced Fist of Guthix", version = 1.00, category = "Mini-Game", description = "Script is under construction")
public class AdvancedFOG extends Script implements PaintListener,
		ServerMessageListener {

	public Boolean hunted = false;
	public Boolean hasStone = false;
	public Boolean wielding = false;

	public int[] equipment;

	public RSTile GetplayerPos() {
		try {
			return getplayer().getLocation();
		} catch (NullPointerException e) {
		}
		return new RSTile(0, 0);
	}

	public RSPlayer getplayer() {
		String name = RSInterface.getInterface(730).getChild(18).getText();
		org.rsbot.accessors.RSPlayer[] array = Bot.getClient()
				.getRSPlayerArray();
		int i = 0;
		while (i < array.length) {
			if (array[i] != null)
				if ((array[i].getName()).equals(name))
					return new RSPlayer(array[i]);
			i++;
		}
		return null;
	}

	public void Wieldequipment() {
try{
		if (equipment == getEquipmentArray())
			return;
		else
			for (int i = 0; i < equipment.length; i++) {
				if (inventoryContains(equipment[i])) {
					atInventoryItem(equipment[i], "Wield");
				}
			}
}catch(NullPointerException e){}
	}

	public Boolean inLobby() {
		RSTile loc = getMyPlayer().getLocation();
		int x = loc.getX();
		int y = loc.getY();
		if (x <= 1725 && x >= 1670) {
			if (y <= 5615 && y >= 5585)
				return true;
		}

		return false;
	}

	public Boolean inGame() {
		RSTile loc = getMyPlayer().getLocation();
		int x = loc.getX();
		int y = loc.getY();
		if (x <= 1710 && x >= 1615) {
			if (y <= 5735 && y >= 5650)
				return true;
		}

		return false;
	}

	public Boolean inWaitingRoom() {
		RSTile loc = getMyPlayer().getLocation();
		int x = loc.getX();
		int y = loc.getY();
		if (x <= 1660 && x >= 1610) {
			if (y <= 5620 && y >= 5600)
				return true;
		}

		return false;
	}

	public int getCharges() {
		String str = RSInterface.getInterface(730).getChild(17).getText();
		String[] array = str.split(": ");
		return Integer.parseInt(array[1]);
	}

	public void JoinGame() {

		RSTile[] path = generateFixedPath(new RSTile(1718, 5599));
		while (distanceTo(new RSTile(1718, 5599)) > 5) {
			walkPathMM(path);
			wait(antiban(750, 1000));
		}
		if (distanceTo(new RSTile(1718, 5599)) <= 5) {
			while (!inWaitingRoom()) {
				try {
					atObject(getNearestObjectByID(30224), "Go-through");
				} catch (NullPointerException e) {
				}
				wait(antiban(750, 1250));
			}
		}

	}

	public void findPlayer() {
		RSTile[] center_path = generateFixedPath(new RSTile(1663, 5696));
		Path(center_path);
		if (GetplayerPos().getX() != 0 && GetplayerPos().getY() != 0)
			while (distanceTo(GetplayerPos()) < 10 && inGame())
				walkPathMM(generateFixedPath(GetplayerPos()));

	}

	public void Attack() {
		//Wieldequipment();
		if (GetplayerPos().getX() == 0 && GetplayerPos().getY() == 0)
			findPlayer();
antiban(0,1);
		if (distanceTo(GetplayerPos()) < 10) {
			atPlayer(getplayer(), "Attack "
					+ RSInterface.getInterface(730).getChild(18).getText());
			while (getMyPlayer().getInteracting() instanceof RSPlayer
					&& inGame())
				wait(antiban(25, 100));
		}
		// TODO run around elsewhere looking for the opponent
	}

	Boolean hasStone() {
		int[] inventory = getInventoryArray();
		int[] StoneIDs = { 12845, 12846, 12847, 12848, 12849 };
		for (int i = 0; i < StoneIDs.length; i++) {
			for (int a = 0; a < inventory.length; a++)
				if (inventory[a] == StoneIDs[i])
					return true;
		}

		return false;
	}

	public void Hide() {
		RSTile[] center_path = generateFixedPath(new RSTile(1663, 5696));
		while (!hasStone && inGame() && hunted) {
			if (getStone())
				hasStone = true;
		}

		wait(antiban(250, 1000));
		WieldStone();
		Path(center_path);
		// TODO Rest of the hunted method
	}

	private void WieldStone() {
		int[] inventory = getInventoryArray();
		int[] StoneIDs = { 12845, 12846, 12847, 12848, 12849 };
		for (int i = 0; i < StoneIDs.length; i++) {
			for (int a = 0; a < inventory.length; a++)
				if (inventory[a] == StoneIDs[i]) {
					atInventoryItem(inventory[a], "Wield");
					wielding = true;
					return;
				}
		}

	}

	public Boolean getStone() {
		try {
			RSObject object = getNearestObjectByID(30143);
			RSTile Loc = object.getLocation();
			Point ScreenLoc = Calculations.tileToScreen(Loc);
			while (!tileOnScreen(Loc) && inGame() && hunted) {
				RSTile[] Path = generateFixedPath(Loc);
				Path(Path);
			}
			moveMouse(ScreenLoc);
			clickMouse(true);
			wait(antiban(500, 750));
		} catch (NullPointerException ignored) {
		}
		if (!hunted)
			return true;
		return hasStone();
	}

	public void runGame() {
		if (!hunted)
			Attack();
		if (hunted)
			Hide();

	}

	private void Path(RSTile[] tiles) {

		while (distanceTo(tiles[tiles.length - 1]) > 5 && inGame()) {
			walkPathMM(tiles);
			while (getMyPlayer().isMoving() && inGame())
				wait(antiban(25, 30));
		}

	}

public int antiban(int min, int max){

try{
				int random = random(1, 2);
				char key = 0;
				if (random(1, 100) <= 3) {
if (random == 2) key = 37;
else key = 39;
			Bot.getInputManager().pressKey(key);
			wait(random(750, 2500));
			Bot.getInputManager().releaseKey(key);
				}
}catch(NullPointerException e){}

return random(min,max);
}

	public boolean onStart() {


equipment = getEquipmentArray();
		return true;
	}

	public void onFinish() {
	}

	public int loop() {
		if (inLobby()) {
			hasStone = false;
			wielding = false;
			hunted = false;
			//Wieldequipment();
			JoinGame();
			return antiban(100, 1000);
		}

		if (inWaitingRoom())
			return antiban(25, 100);

		if (inGame()) {
			runGame();
			return antiban(100, 1000);
		}
		return 100;
	}

	@Override
	public void onRepaint(Graphics g) {
		int X = 15;
		int Y = 50;
		g.drawString("In lobby:" + inLobby(), X, Y);
		Y += 20;
		g.drawString("Partner location:" + GetplayerPos().getX() + ","
				+ GetplayerPos().getY(), X, Y);
		Y += 20;
		g.drawString("Being hunted:" + hunted, X, Y);
		Y += 20;
	}

	public void serverMessageRecieved(ServerMessageEvent e) {
		String m = e.getMessage();
		if (m.contains("round"))
			hunted = false;
		if (m.contains("effect"))
			hunted = true;
	}

}