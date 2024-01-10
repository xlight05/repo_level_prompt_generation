import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.Map;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.rsbot.event.listeners.PaintListener;
import org.rsbot.script.Calculations;
import org.rsbot.script.Constants;
import org.rsbot.script.Script;
import org.rsbot.script.ScriptManifest;
import org.rsbot.script.wrappers.RSItemTile;
import org.rsbot.script.wrappers.RSObject;
import org.rsbot.script.wrappers.RSTile;

@ScriptManifest(authors = { "Tad_" }, category = "Hunter", name = "FSHunt (AIO Hunter)", version = 1.12, description = "<html><font color = Red><center><h2> FSHunt by Tad_</h2></center>"
		+ "<center>Settings in GUI.</center>"
		+ "<center><font size=2>Credits to: RSHelper, Method, Jacmob, Tekk, and anyone else who's helped me out over irc</center></font>"
		+ "\n"
		+ "<center>Please note that this script will create .txt files in your RSBot folder to save settings if you choose to do so</center>")
public class FSHunt extends Script implements PaintListener {
	final ScriptManifest properties = getClass().getAnnotation(
			ScriptManifest.class);

	// GUI vars
	String WhatToHunt;
	boolean TakeBreaks, BuryBones;
	int BEvery = 0, BFor = 0, RandomBE = 0, RandomBF = 0, AntibanLevel = 1;
	public Point[] Spots = null;

	// Script Info Vars
	RSTile[] IsTrap = new RSTile[5], Possible = new RSTile[81];
	RSTile OriginalPosition;
	int BreakEvery = 0, BreakFor = 0, loopcheck, TrapsSet = 0,
			TrapsSuccess = 0, TrapsFail = 0;
	long StartEXP, StartLVL, TimeAtBreak, StartTime = System
			.currentTimeMillis();
	String status = "", TimeTimer = "";
	boolean KeepGUI = true, KeepOGUI = true, GUISuccess = false,
			UseTimer = false;
	FSHuntGUI gui;

	FSHuntSelector ogui = null;

	// Hunting Vars
	int TrapInvID, TrapReadyID, BonesID = 526, ProfitID, AmountProfit,
			TrapSuccessID, TrapFailID;

	long LastDropCheck = 0, LastBoneCheck = 0;
	int[] TrapFinishingID = null, DropIDs = null, DropSpots = null,
			BoneSpots = null;

	public long[] millisToHMS(long millis) {
		long H = 0, M = 0, S = 0;
		H = millis / (1000 * 60 * 60);
		M = millis / (1000 * 60) - (60 * H);
		S = millis / 1000 - ((60 * M) + (3600 * H));
		long[] HMSArray = { H, M, S };
		return HMSArray;
	}

	public void onRepaint(Graphics g) {
		int CurEXP = skills.getCurrentSkillExp(STAT_HUNTER);
		int CurLVL = skills.getCurrentSkillLevel(STAT_HUNTER);
		long EXPHour = 0, TimeToLVL, EXPToLVL;
		g.setColor(Color.red);
		g.setFont(new Font("Segoe UI", Font.BOLD, 15));
		g.drawString("" + status, 300, 335);
		if (isLoggedIn()) {
			long millis = System.currentTimeMillis() - StartTime;
			long HMS[] = millisToHMS(millis);
			long hours = HMS[0];
			long minutes = HMS[1];
			long seconds = HMS[2];
			g.setColor(new Color(20, 200, 20, 150));
			g.fillRoundRect(554, 211, 180, 247, 20, 20);
			g.fillRoundRect(295, 320, 215, 19, 10, 10);
			g.setColor(Color.red);

			g.setFont(new Font("Segoe UI", Font.BOLD, 12));
			g.drawString("Time running: " + hours + ":" + minutes + ":"
					+ seconds + ".", 560, 225);

			g.drawString("Traps set: " + TrapsSet, 560, 240);
			g.drawString("Traps successful: " + TrapsSuccess, 560, 255);
			g.drawString("Traps failed/fallen: " + TrapsFail, 560, 270);
			if ((TrapsSuccess + TrapsFail) != 0)
				g.drawString("% Success: "
						+ (TrapsSuccess * 100 / (TrapsSuccess + TrapsFail))
						+ "%", 560, 285);

			g.drawString("EXP gained: " + (CurEXP - StartEXP), 560, 305);
			EXPHour = (int) (3600000.0 / millis * (CurEXP - StartEXP));
			g.drawString("EXP/Hour: " + EXPHour, 560, 320);

			g.drawString("Current Level: " + CurLVL, 560, 340);
			g.drawString("Levels Gained: " + (CurLVL - StartLVL), 560, 355);
			EXPToLVL = skills.getXPToNextLevel(STAT_HUNTER);
			g.drawString("EXP to next level: " + EXPToLVL, 560, 370);
			g.drawString("% to next level: "
					+ skills.getPercentToNextLevel(STAT_HUNTER), 560, 385);
			if (EXPHour != 0) {
				TimeToLVL = EXPToLVL * 60 * 60 * 1000 / EXPHour;
				long HMS2[] = millisToHMS(TimeToLVL);
				g.drawString("Time to next level: " + HMS2[0] + ":" + HMS2[1]
						+ ":" + HMS2[2] + ".", 560, 400);
			}
		}
	}

	public void initTrapLocs() {
		int i, j = 0;

		if (Spots == null || Spots.length <= 0 || Spots[0] == null) {
			log("ERROR. Make sure you set up your trap locations. If you did and this error still occurs, alert Tad_");
			stopScript();
		}
		Point[] XYs = Spots;

		status = "Initializing Trap Locations";
		OriginalPosition = getMyPlayer().getLocation();
		for (i = 0; i < XYs.length; i++) {
			Possible[j] = new RSTile(OriginalPosition.getX() + XYs[i].x,
					OriginalPosition.getY() + XYs[i].y);
			j = j + 1;
		}
	}

	public boolean onStart(Map<String, String> args) {
		gui = new FSHuntGUI();
		gui.setVisible(true);
		gui.setAlwaysOnTop(true);
		while (KeepGUI)
			wait(50);
		if (!GUISuccess)
			return false;
		status = "Starting up";
		if (!isLoggedIn())
			login();
		StartEXP = skills.getCurrentSkillExp(STAT_HUNTER);
		StartLVL = skills.getCurrentSkillLevel(STAT_HUNTER); // Script by Tad_
		initTrapLocs();
		initHuntVars();
		initBreaks();
		return true;
	}

	public void initHuntVars() {

		status = "Initializing Hunt Vars";
		if (WhatToHunt.toLowerCase().equals("ferret")) {
			TrapInvID = 10008;
			TrapReadyID = 19187;
			ProfitID = -1;
			TrapFinishingID = new int[] { 19201, 19202, 19203, 19204, 19188 };
			TrapSuccessID = 19191;
			TrapFailID = 19192;
			if (BuryBones) {
				DropIDs = new int[1];
				DropIDs[0] = 10092;
			} else {
				DropIDs = new int[2];
				DropIDs[0] = 10092;
				DropIDs[1] = BonesID;
			}
		} else if (WhatToHunt.toLowerCase().equals("grey_chinchompa")) {
			TrapInvID = 10008;
			TrapReadyID = 19187;
			ProfitID = 10033;
			TrapFinishingID = new int[] { 19188, 19196, 19193, 19194, 19192,
					19195 };
			TrapSuccessID = 19189;
			TrapFailID = 19192;
		} else if (WhatToHunt.toLowerCase().equals("red_chinchompa")) {
			TrapInvID = 10008;
			TrapReadyID = 19187;
			ProfitID = 10034;
			TrapFinishingID = new int[] { 19188, 19199, 19197, 19198, 19200 };
			TrapSuccessID = 19190;
			TrapFailID = 19192;
		} else if (WhatToHunt.toLowerCase().equals("crimson_swift")) {
			TrapInvID = 10006;
			TrapReadyID = 19175;
			ProfitID = 10088;
			TrapFinishingID = new int[] { 19176, 19179 };
			TrapSuccessID = 19180;
			TrapFailID = 19174;
			if (BuryBones) {
				DropIDs = new int[1];
				DropIDs[0] = 9978;
			} else {
				DropIDs = new int[2];
				DropIDs[0] = 9978;
				DropIDs[1] = BonesID;
			}
		} else if (WhatToHunt.toLowerCase().equals("tropical_wagtail")) {
			TrapInvID = 10006;
			TrapReadyID = 19175;
			ProfitID = 10087;
			TrapFinishingID = new int[] { 19177, 19176 };
			TrapSuccessID = 19178;
			TrapFailID = 19174;
			if (BuryBones) {
				DropIDs = new int[1];
				DropIDs[0] = 9978;
			} else {
				DropIDs = new int[2];
				DropIDs[0] = 9978;
				DropIDs[1] = BonesID;
			}
		} else {
			log("Something went wrong (Error: initHuntVars fail). Alert Tad_. Terminating");
			stopScript();
		}
	}

	public void initBreaks() {
		if (!TakeBreaks)
			return;
		status = "Initializing Breaks";
		BreakEvery = (BEvery * 60000) + random(0, (RandomBE * 60000));
		BreakFor = (BFor * 60000) + random(0, (RandomBF * 60000));
		TimeAtBreak = System.currentTimeMillis();

	}

	public boolean needDrop(boolean InventoryFull) {
		long t;
		if (getEnergy() > 80)
			setRun(true);
		if (!InventoryFull) {
			if ((isIdle() || (getMyPlayer().getLocation().getX() != OriginalPosition
					.getX() || getMyPlayer().getLocation().getY() != OriginalPosition
					.getY())))
				if (getMyPlayer().getLocation().getX() == OriginalPosition
						.getX() + 1
						&& getMyPlayer().getLocation().getY() != OriginalPosition
								.getY())
					wait(random(300, 500));
			if (getMyPlayer().getLocation().getX() != OriginalPosition.getX()
					|| getMyPlayer().getLocation().getY() != OriginalPosition
							.getY()) {
				walkTileOnScreen(OriginalPosition);
				t = System.currentTimeMillis();
				while (!getMyPlayer().isMoving()
						&& (getMyPlayer().getLocation().getX() != OriginalPosition
								.getX() || getMyPlayer().getLocation().getY() != OriginalPosition
								.getY())) {
					if (System.currentTimeMillis() - t > 3000)
						break;
					wait(random(300, 500));
				}
				t = System.currentTimeMillis();
				while (getMyPlayer().isMoving()) {
					if (System.currentTimeMillis() - t > 4000)
						break;
					wait(random(300, 500));
				}
				wait(random(30, 90));
			}
		}
		if (DropIDs == null)
			return false;
		if (InventoryFull)
			return getInventoryCount() > 20;
		if (inventoryContainsOneOf(DropIDs) || inventoryContainsOneOf(BonesID))
			return true;
		return false;
	}

	public boolean needBreak() {
		if (!TakeBreaks)
			return false;
		if (((System.currentTimeMillis() - TimeAtBreak)) > BreakEvery)
			return true;
		return false;
	}

	public boolean isItemAt(int ID, RSTile Loc) {
		RSItemTile[] AllItems;
		int i;
		AllItems = getGroundItemsAt(Loc);
		if (AllItems != null)
			for (i = 0; i < AllItems.length; i++) {
				if (AllItems[i].getItem().getID() == ID)
					return true;
			}
		return false;
	}

	public boolean pickTrap() {
		int i;
		long t;
		status = "Checking fallen traps";
		if (IsTrap == null)
			return false;
		for (i = 0; i < IsTrap.length; i++) {
			if (IsTrap[i] == null)
				continue;
			if (isItemAt(TrapInvID, IsTrap[i])) {

				if (!tileOnScreen(IsTrap[i]))
					walkTo(IsTrap[i], 3, 3);
				if (WhatToHunt == "Crimson_Swift"
						|| WhatToHunt == "Tropical_Wagtail") {
					if (needPlaceTrap()) {
						if (!atTile(IsTrap[i], "Lay Bird snare"))
							return true;
					} else {
						if (!atTile(IsTrap[i], "Take Bird snare"))
							return true;
					}
				} else {
					if (needPlaceTrap()) {
						if (!atTile(IsTrap[i], "Lay Box trap"))
							return true;
					} else {
						if (!atTile(IsTrap[i], "Take Box trap"))
							return true;
					}
				}
				if (AntibanLevel != 0)
					if (random(0, 15) == 0)
						if (random(0, 1) == 0)
							setCameraRotation(random(0, 359));
						else
							setCameraAltitude(random(0, 100));
				t = System.currentTimeMillis();
				while (getObjectAt(IsTrap[i]) == null || !isIdle()) {
					if (System.currentTimeMillis() - t > 8000)
						break;
					wait(random(150, 250));
				}
				return true;
			}
		}
		return false;
	}

	public boolean needPlaceTrap() {
		int AvailTrapNum = 0, i, AmtTraps = 0;

		AvailTrapNum = (skills.getCurrentSkillLevel(STAT_HUNTER) / 20) + 1;
		for (i = 0; i < IsTrap.length; i++) {
			if (IsTrap[i] == null)
				continue;
			if (getObjectAt(IsTrap[i]) != null)
				AmtTraps = AmtTraps + 1;
			else if (!isItemAt(TrapInvID, IsTrap[i]))
				IsTrap[i] = null;
		}
		return (AvailTrapNum - AmtTraps) > 0;
	}

	public int findDoneTrap() { // Returns Index in IsTrap of nearest Trap
		// that is ready to be harvested. Prioritizes finished traps
		int i, TheTrap = -1;
		RSObject Temp;

		for (i = 0; i < IsTrap.length; i++) {
			if (IsTrap[i] == null)
				continue;
			Temp = getObjectAt(IsTrap[i]);
			if (Temp == null) {
				if (!isItemAt(TrapInvID, IsTrap[i]))
					IsTrap[i] = null;
				continue;
			}
			if (Temp.getID() == TrapSuccessID)
				if (TheTrap == -1)
					TheTrap = i;
				else if (distanceTo(IsTrap[i]) < distanceTo(Temp))
					TheTrap = i;
		}
		if (TheTrap != -1)
			return TheTrap;

		for (i = 0; i < IsTrap.length; i++) {
			if (IsTrap[i] == null)
				continue;
			Temp = getObjectAt(IsTrap[i]);
			if (Temp == null && !isItemAt(TrapInvID, IsTrap[i])) {
				IsTrap[i] = null;
				continue;
			} else if (Temp == null) {
				pickTrap();
				IsTrap[i] = null;
				continue;
			}
			if (Temp.getID() == TrapFailID)
				if (TheTrap == -1)
					TheTrap = i;
				else if (distanceTo(IsTrap[i]) < distanceTo(Temp))
					TheTrap = i;
		}
		return TheTrap;
	}

	public boolean checkTrap() {
		int Temp = -1;
		RSObject TheTrap = null;
		long t;
		boolean Success = false;

		status = "Reviewing Traps";
		Temp = findDoneTrap();
		if (Temp == -1)
			return false;
		TheTrap = getObjectAt(IsTrap[Temp]);
		if (TheTrap.getID() == TrapSuccessID) {
			if (!atObject(TheTrap, "Check"))
				return false;
			Success = true;
		} else {
			if (!atObject(TheTrap, "Dismantle"))
				return false;
			Success = false;
		}
		t = System.currentTimeMillis();
		while (getObjectAt(TheTrap.getLocation()) != null) {
			if (isIdle()) {
				wait(random(250, 450));
				if (isIdle())
					break;
			}
			if (System.currentTimeMillis() - t > 10000)
				break;
			wait(random(100, 200));
		}
		t = System.currentTimeMillis();
		if (IsTrap[Temp] != null) {
			while (getObjectAt(IsTrap[Temp]) != null) {
				if (isIdle()) {
					wait(random(250, 450));
					if (isIdle())
						break;
				}
				if (System.currentTimeMillis() - t > 10000)
					break;
				wait(random(150, 250));
			}
			if (getObjectAt(IsTrap[Temp]) == null && Success)
				TrapsSuccess++;
			else if (getObjectAt(IsTrap[Temp]) == null && !Success)
				TrapsFail++;
			if (getObjectAt(IsTrap[Temp]) == null)
				IsTrap[Temp] = null;
			wait(random(200, 450));
		}
		return true;
	}

	public boolean hoverTrap() {
		int i, ID, WhatTrap = -1, j;
		RSObject Temp;
		long t;
		boolean DidClick = false, IsFinishing = false;

		status = "Predicting trap";
		for (i = 0; i < IsTrap.length; i++) {
			if (IsTrap[i] == null)
				continue;
			Temp = getObjectAt(IsTrap[i]);
			if (Temp == null)
				continue;
			ID = Temp.getID();
			for (j = 0; j < TrapFinishingID.length; j++) {
				if (ID == TrapFinishingID[j]) {
					IsFinishing = true;
					break;
				}
			}
			if (!IsFinishing)
				return false;
			Point location = Calculations.tileToScreen(IsTrap[i]);
			if (location.x == -1 || location.y == -1)
				return false;
			moveMouse(location.x, location.y, 5, 5);
			Temp = getObjectAt(IsTrap[i]);
			t = System.currentTimeMillis();
			if (Temp != null) {
				while (Temp.getID() != TrapFailID
						&& Temp.getID() != TrapSuccessID) {
					Temp = getObjectAt(IsTrap[i]);
					if (Temp == null)
						break;
					if (System.currentTimeMillis() - t > 10000)
						break;
					wait(random(50, 100));

				}
			}
			Temp = getObjectAt(IsTrap[i]);
			if (Temp == null)
				return false;
			if (Temp.getID() == TrapFailID || Temp.getID() == TrapSuccessID) {
				wait(random(250, 400));
				clickMouse(true);
				WhatTrap = i;
				DidClick = true;
			}
			break;
		}
		if (DidClick) {
			t = System.currentTimeMillis();
			while (isIdle()) {
				if (System.currentTimeMillis() - t > 10000)
					break;
				wait(random(500, 650));
			}
			t = System.currentTimeMillis();
			if (IsTrap[WhatTrap] != null) {
				while (getObjectAt(IsTrap[WhatTrap]) != null && !isIdle()) {
					if (System.currentTimeMillis() - t > 10000)
						break;
					wait(random(150, 250));
				}
				if (getObjectAt(IsTrap[WhatTrap]) == null)
					IsTrap[WhatTrap] = null;
				wait(random(200, 450));
			}
			return true;
		}
		return false;
	}

	public int[] findItems(int[] IDs) {
		int[] AllItems = getInventoryArray();
		int i, j, LCount = 0;

		if (IDs == null)
			return null;
		for (j = 0; j < IDs.length; j++)
			if (AllItems != null)
				for (i = 0; i < AllItems.length; i++)
					if (AllItems[i] == IDs[j])
						LCount = LCount + 1;
		int[] Temp = new int[LCount];
		LCount = 0;
		if (AllItems != null) {
			for (j = 0; j < IDs.length; j++)
				for (i = 0; i < AllItems.length; i++)
					if (AllItems[i] == IDs[j]) {
						Temp[LCount] = i;
						LCount = LCount + 1;
					}
		}
		return Temp;
	}

	public void dropStuff(boolean CalcSpots) {
		Point ItemSpot = null;
		int i;
		long j;

		status = "Dropping";
		if (System.currentTimeMillis() - LastDropCheck > 5000 && CalcSpots) {
			DropSpots = findItems(DropIDs);
			LastDropCheck = System.currentTimeMillis();
		}
		if (BuryBones)
			if (System.currentTimeMillis() - LastBoneCheck > 5000 && CalcSpots) {
				BoneSpots = findItems(new int[] { BonesID });
				LastBoneCheck = System.currentTimeMillis();
			}
		if (DropSpots.length <= 0 && BoneSpots.length <= 0)
			return;
		for (i = 0; i < DropSpots.length; i++) {
			if (DropSpots[i] != -1) {
				ItemSpot = getInventoryItemPoint(DropSpots[i]);
				clickMouse(ItemSpot.x, ItemSpot.y, 35, 31, false);
				j = System.currentTimeMillis();
				while (System.currentTimeMillis() - j < 5000) {
					if (isMenuOpen())
						break;
					wait(random(50, 100));
				}
				if (!isMenuOpen())
					continue;
				wait(random(25, 100));

				if (getMenuIndex("Release") != -1) {
					if (!atMenu("Release"))
						continue;
				} else {
					if (!atMenu("Drop"))
						continue;
				}
				wait(random(50, 125));
				DropSpots[i] = -1;
				return;
			}
		}
		if (BuryBones)
			buryBone();
	}

	public void buryBone() {
		status = "Burying bones";
		Point ItemSpot = null;
		int i;
		long j;
		pickTrap();
		if (BoneSpots == null)
			return;
		if (BoneSpots.length <= 0)
			return;
		for (i = 0; i < BoneSpots.length; i++) {
			if (BoneSpots[i] != -1) {
				ItemSpot = getInventoryItemPoint(BoneSpots[i]);
				clickMouse(ItemSpot.x, ItemSpot.y, 35, 31, true);
				j = System.currentTimeMillis();
				while (System.currentTimeMillis() - j < 5000) {
					if (getMyPlayer().getAnimation() != -1)
						break;
					wait(random(50, 100));
				}
				wait(random(200, 500));
				BoneSpots[i] = -1;
				return;
			}
		}
	}

	public void dropAll() {
		DropSpots = findItems(DropIDs);
		while (findItems(DropIDs).length > 0)
			dropStuff(false);
		if (BuryBones) {
			BoneSpots = findItems(new int[] { BonesID });
			while (findItems(new int[] { BonesID }).length > 0)
				buryBone();
		}
	}

	public void pickAllTraps() {
		int i;
		long t;

		for (i = 0; i < IsTrap.length; i++) {
			if (IsTrap[i] != null) {
				if (getObjectAt(IsTrap[i]) == null) {
					pickTrap();
					IsTrap[i] = null;
				}
				if (getObjectAt(IsTrap[i]).getID() == TrapReadyID
						|| getObjectAt(IsTrap[i]).getID() == TrapFailID)
					if (!atObject(getObjectAt(IsTrap[i]), "Dismantle")) {
						i--;
						continue;
					}
				if (getObjectAt(IsTrap[i]).getID() == TrapSuccessID)
					if (!atObject(getObjectAt(IsTrap[i]), "Check")) {
						i--;
						continue;
					}
				t = System.currentTimeMillis();
				while (isIdle()) {
					if (System.currentTimeMillis() - t > 10000)
						break;
					wait(random(300, 500));
				}
				t = System.currentTimeMillis();
				while (getObjectAt(IsTrap[i]) != null && !isIdle()) {
					if (System.currentTimeMillis() - t > 10000)
						break;
					wait(random(150, 250));
				}
				if (getObjectAt(IsTrap[i]) == null)
					IsTrap[i] = null;
				wait(random(200, 450));
				IsTrap[i] = null;
			}
		}
	}

	public void takeBreak() {

		status = "Taking Break";
		log("Getting ready to take break");
		dropAll();
		pickAllTraps();
		logout();
		log("Currently taking break for " + BreakFor + "ms");
		wait(BreakFor);
		log("Done taking break!");
		login();
		TimeAtBreak = System.currentTimeMillis();
	}

	public void antiBan() {
		status = "Performing Antiban";
		if (random(0, 50) == 0)
			moveMouse(380, 250, 300, 200);
		if (random(0, 250) == 0) {
			openTab(random(0, 15));
			wait(random(350, 1000));
			openTab(Constants.TAB_INVENTORY);
		}
		if (random(0, 75) == 0)
			setCameraRotation(random(0, 359));
		if (random(0, 75) == 0)
			setCameraAltitude(random(0, 100));

	}

	public RSTile findGoodTile() {
		int i;
		if (Possible.equals(null))
			return null;
		for (i = 0; i < Possible.length; i++)
			if (Possible[i] != null)
				if (getObjectAt(Possible[i]) == null)
					if (!isItemAt(TrapInvID, Possible[i]))
						return Possible[i];
					else
						break;
				else if (getObjectAt(Possible[i]).getType() != 0
						&& getObjectAt(Possible[i]).getType() != 2)
					if (!isItemAt(TrapInvID, Possible[i]))
						return Possible[i];
					else
						break;
		return null;
	}

	public boolean placeTrap() {
		RSTile TileToUse = findGoodTile();
		long t;
		int Index;

		status = "Placing Trap";
		if (TileToUse == null)
			return false;
		if ((getMyPlayer().getLocation().getX() != TileToUse.getX() || getMyPlayer()
				.getLocation().getY() != TileToUse.getY())) {
			if (!tileOnScreen(TileToUse))
				walkTileMM(TileToUse, 3, 3);
			if (walkTileOnScreen(TileToUse)) {
				t = System.currentTimeMillis();
				while (!getMyPlayer().isMoving()) {
					if (System.currentTimeMillis() - t > 3000)
						break;
					wait(random(300, 500));
				}
				t = System.currentTimeMillis();
				while (getMyPlayer().isMoving()) {
					if (System.currentTimeMillis() - t > 4000)
						break;
				}
				if (getMyPlayer().getLocation().getX() != TileToUse.getX()
						|| getMyPlayer().getLocation().getY() != TileToUse
								.getY() || getMyPlayer().isMoving())
					return true;
			}
		}
		if (!atInventoryItem(TrapInvID, "Lay"))
			return true;
		TileToUse = getMyPlayer().getLocation();
		wait(random(30, 90));
		moveMouse(260, 175, 120, 75);
		if (AntibanLevel != 0)
			if (random(0, 15) == 0)
				if (random(0, 1) == 0)
					setCameraRotation(random(0, 359));
				else
					setCameraAltitude(random(0, 100));

		for (Index = 0; Index < IsTrap.length; Index++)
			if (IsTrap[Index] == null) {
				IsTrap[Index] = TileToUse;
				break;
			}
		t = System.currentTimeMillis();
		if (IsTrap[Index] == null) {
			while (!isIdle() && System.currentTimeMillis() - t < 10000)
				wait(random(500, 650));
			return true;
		}
		while (getObjectAt(IsTrap[Index]) == null) {
			if (System.currentTimeMillis() - t > 15000)
				break;
			wait(random(50, 100));
		}
		if (getObjectAt(TileToUse) == null)
			IsTrap[Index] = null;
		else
			TrapsSet++;

		wait(random(800, 1200));
		return true;
	}

	public void needQuit() {
		if (!UseTimer)
			return;
		String[] Temp = new String[3];
		int[] Units = new int[3];
		int i, MSTime;
		Temp = TimeTimer.split(":");
		for (i = 0; i < Temp.length; i++)
			Units[i] = Integer.parseInt(Temp[i]);
		MSTime = (Units[0] * 360000) + (Units[1] * 60000) + (Units[2] * 1000)
				+ random(2000, 5000);
		if (System.currentTimeMillis() - StartTime < MSTime)
			return;
		log("End time reached! Terminating script...");
		dropAll();
		pickAllTraps();
		logout();
		stopScript();
	}

	@Override
	protected int getMouseSpeed() {
		return random(7,9);
	}

	@Override
	public int loop() {
		if (needDrop(true))
			dropAll();
		if (pickTrap())
			if (inventoryContainsOneOf(TrapInvID))
				if (needPlaceTrap())
					if (placeTrap())
						return 0;
		needQuit();
		if (needBreak())
			takeBreak();
		if (inventoryContainsOneOf(TrapInvID))
			if (needPlaceTrap())
				if (placeTrap())
					return 0;
		if (checkTrap())
			return 0;
		if (needDrop(false)) {
			dropStuff(true);
			return 0;
		}
		if (hoverTrap())
			return 0;
		if (!inventoryContainsOneOf(TrapInvID)) {
			for (loopcheck = 0; loopcheck < IsTrap.length; loopcheck++)
				if (IsTrap == null && loopcheck == IsTrap.length - 1) {
					log("No more traps? Terminating.");
					logout();
					stopScript();
				}
		}
		if (AntibanLevel != 0)
			antiBan();

		return 0;
	}

	@SuppressWarnings("serial")
	public class FSHuntGUI extends JFrame {
		public FSHuntGUI() {
			initComponents();
			CBStateChanged();
			UTStateChanged();
		}

		private void OpenTrapSetupActionPerformed()
				throws IllegalMonitorStateException, InterruptedException {
			if (ogui != null)
				return;
			ogui = new FSHuntSelector();
			ogui.setVisible(true);
			gui.setAlwaysOnTop(false);
			ogui.setAlwaysOnTop(true);
			KeepOGUI = true;
			log("IGNORE THIS ERROR ^ AND THE ONE BELOW v");
			while (KeepOGUI) {
				wait(50);
			}
			KeepOGUI = true;
			ogui = null;
			gui.setAlwaysOnTop(true);
		}

		private void StartButtonActionPerformed(ActionEvent e) {
			if (AB.getText() != null)
				AntibanLevel = Integer.parseInt(AB.getText());
			BuryBones = BB.isSelected();
			TakeBreaks = CB.isSelected();
			WhatToHunt = WTH.getSelectedItem().toString();
			if (TakeBreaks) {
				if (BE.getText() != null)
					BEvery = Integer.parseInt(BE.getText());

				if (BF.getText() != null)
					BFor = Integer.parseInt(BF.getText());
				if (RBE.getText() != null)
					RandomBE = Integer.parseInt(RBE.getText());
				if (RBF.getText() != null)
					RandomBF = Integer.parseInt(RBF.getText());
			}
			KeepGUI = false;
			GUISuccess = true;
			UseTimer = UT.isSelected();
			TimeTimer = TT.getText();
			dispose();
		}

		/*
		 * private String getNumbers(final String toSearch) { //By RSHelper
		 * final char[] searchChars = toSearch.toCharArray(); String numbers =
		 * ""; for(final char curChar : searchChars)
		 * if(Character.isDigit(curChar)) numbers += curChar; return numbers; }
		 */

		private void SaveSettingsActionPerformed(ActionEvent e)
				throws IOException {
			FileOutputStream MySettings;
			try {
				MySettings = new FileOutputStream("FSHunt Settings.txt");
			} catch (FileNotFoundException e1) {
				log("Failed saving Settings!");
				return;
			}
			new PrintStream(MySettings).println(WTH.getSelectedIndex() + ","
					+ AB.getText() + "," + BB.isSelected() + ","
					+ CB.isSelected() + "," + BE.getText() + "," + BF.getText()
					+ "," + RBE.getText() + "," + RBF.getText() + ","
					+ UT.isSelected() + "," + TT.getText());
			/*
			 * 1: WTH 2: AB 3: BB 4: CB 5: BE 6: BF 7: RBE 8: RBF 9: UT 10: TT
			 */
			MySettings.close();
			log("Settings Saved");
		}

		private void LoadSettingsActionPerformed(ActionEvent e)
				throws IOException {
			FileInputStream MySettings;
			String[] Values = new String[10];
			try {
				MySettings = new FileInputStream("FSHunt Settings.txt");
			} catch (FileNotFoundException e1) {
				log("Failed loading Settings!");
				return;
			}

			BufferedReader d = new BufferedReader(new InputStreamReader(
					MySettings));
			Values = d.readLine().split(",");
			WTH.setSelectedIndex(Integer.parseInt(Values[0]));
			AB.setText(Values[1]);
			BB.setSelected(Boolean.parseBoolean(Values[2]));
			CB.setSelected(Boolean.parseBoolean(Values[3]));
			BE.setText(Values[4]);
			BF.setText(Values[5]);
			RBE.setText(Values[6]);
			RBF.setText(Values[7]);
			UT.setSelected(Boolean.parseBoolean(Values[8]));
			TT.setText(Values[9]);
			MySettings.close();
			FileInputStream MyLocs;
			String myValue;
			int ArrayLength = 0;

			try {
				MyLocs = new FileInputStream("FSHunt Locations.txt");
			} catch (FileNotFoundException e1) {
				log("Failed loading Locations!");
				return;
			}

			d = new BufferedReader(new InputStreamReader(MyLocs));
			while ((myValue = d.readLine()) != null)
				ArrayLength++;
			Values = new String[ArrayLength];
			d.close();
			MyLocs.close();

			try {
				MyLocs = new FileInputStream("FSHunt Locations.txt");
			} catch (FileNotFoundException e1) {
				log("Failed loading Locations!");
				return;
			}
			d = new BufferedReader(new InputStreamReader(MyLocs));
			ArrayLength = 0;

			while ((myValue = d.readLine()) != null) {

				Values[ArrayLength] = myValue;
				ArrayLength++;
			}
			String[] XandY = new String[2];
			int Temp = 0;
			Spots = new Point[ArrayLength];
			for (String item : Values) {
				XandY = item.split(",");
				Spots[Temp] = new Point(new Float(XandY[0]).intValue(),
						new Float(XandY[1]).intValue());
				Temp++;
				if (Temp >= Spots.length)
					break;
			}
			MyLocs.close();
			log("Successfully loaded locations");
			log("Successfully loaded Settings");
		}

		private void CBStateChanged() {
			boolean Enable = CB.isSelected();
			label5.setVisible(Enable);
			label6.setVisible(Enable);
			label7.setVisible(Enable);
			label8.setVisible(Enable);
			label10.setVisible(Enable);
			label11.setVisible(Enable);
			BE.setVisible(Enable);
			BF.setVisible(Enable);
			RBE.setVisible(Enable);
			RBF.setVisible(Enable);
		}

		private void UTStateChanged() {
			boolean Enable = UT.isSelected();
			label4.setVisible(Enable);
			TT.setVisible(Enable);
		}

		private void initComponents() {
			// JFormDesigner - Component initialization - DO NOT MODIFY
			// //GEN-BEGIN:initComponents
			label1 = new JLabel();
			label2 = new JLabel();
			WTH = new JComboBox();
			BB = new JCheckBox();
			CB = new JCheckBox();
			label5 = new JLabel();
			label6 = new JLabel();
			label7 = new JLabel();
			label8 = new JLabel();
			label9 = new JLabel();
			scrollPane1 = new JScrollPane();
			textArea1 = new JTextArea();
			label10 = new JLabel();
			label11 = new JLabel();
			BE = new JTextField();
			RBE = new JTextField();
			RBF = new JTextField();
			LoadSettings = new JButton();
			SaveSettings = new JButton();
			StartButton = new JButton();
			BF = new JTextField();
			AB = new JTextField();
			tetx = new JLabel();
			label3 = new JLabel();
			UT = new JCheckBox();
			label4 = new JLabel();
			TT = new JTextField();
			OpenTrapSetup = new JButton();
			label12 = new JLabel();

			// ======== this ========
			setTitle("FSHunt Settings");
			setResizable(false);
			setForeground(Color.black);
			Container contentPane = getContentPane();
			contentPane.setLayout(null);

			// ---- label1 ----
			label1.setText("FS Hunt by Tad_");
			label1.setFont(new Font("Papyrus", Font.BOLD, 31));
			contentPane.add(label1);
			label1.setBounds(150, 10, 303, 45);

			// ---- label2 ----
			label2.setText("What to Hunt:");
			contentPane.add(label2);
			label2.setBounds(new Rectangle(new Point(25, 75), label2
					.getPreferredSize()));

			// ---- WTH ----
			WTH.setModel(new DefaultComboBoxModel(new String[] {
					"Crimson_Swift", "Tropical_Wagtail", "Ferret",
					"Grey_Chinchompa", "Red_Chinchompa" }));
			contentPane.add(WTH);
			WTH.setBounds(new Rectangle(new Point(105, 70), WTH
					.getPreferredSize()));

			// ---- BB ----
			BB.setText("Bury Bones?");
			contentPane.add(BB);
			BB.setBounds(new Rectangle(new Point(25, 100), BB
					.getPreferredSize()));

			// ---- CB ----
			CB.setText("Use Custom Break Handler?");
			CB.setSelected(true);
			CB.addChangeListener(new ChangeListener() {
				public void stateChanged(ChangeEvent e) {
					CBStateChanged();
				}
			});
			contentPane.add(CB);
			CB.setBounds(new Rectangle(new Point(300, 70), CB
					.getPreferredSize()));

			// ---- label5 ----
			label5.setText("Break every");
			contentPane.add(label5);
			label5.setBounds(new Rectangle(new Point(305, 105), label5
					.getPreferredSize()));

			// ---- label6 ----
			label6.setText("minutes");
			contentPane.add(label6);
			label6.setBounds(new Rectangle(new Point(410, 105), label6
					.getPreferredSize()));

			// ---- label7 ----
			label7.setText("for");
			contentPane.add(label7);
			label7.setBounds(new Rectangle(new Point(305, 125), label7
					.getPreferredSize()));

			// ---- label8 ----
			label8.setText("minutes");
			contentPane.add(label8);
			label8.setBounds(355, 125, label8.getPreferredSize().width, 15);

			// ---- label9 ----
			label9.setText("Other info:");
			label9.setFont(new Font("Papyrus", Font.ITALIC, 15));
			contentPane.add(label9);
			label9.setBounds(220, 355, 85, label9.getPreferredSize().height);

			// ======== scrollPane1 ========
			{

				// ---- textArea1 ----
				textArea1.setRows(10);
				textArea1.setWrapStyleWord(true);
				textArea1.setTabSize(2);
				textArea1
						.setText("Information on various non self-explanatory components...\n\nCustom Break Handler: Self explanatory except for the\n'random'. BreakHandler will activate after the minutes\nyou set PLUS a random amount ranging from 0 to the\nnumber you put in RandomBE. It will stay logged out for\nthe minutes you set PLUS the amount in RandomBF. \n\nAntiban Level: Set to 0 to disable Antiban. After that,\nthe higher you set it, the less antiban it will perform.\nI personally recommend using 1, but some people claim\nthat that is unhumanlike. Expirement and find your\npreference.\n\nUse Timer & Time To Run: If Use Timer is enabled, the\nscript will run for the time specified in Time To Run\nthen stop, pick up traps, and logout. Time To Run should\nbe in this format: h:m:s (put a 0 if zero of that\nparticular unit).\n\nOpen Trap Setup: It is MANDATORY that you set this up. \nHowever, after you have saved the locations on the\nSetting up Locations dialog once, they will be loaded\nif you simply click Load Settings on the main GUI\n(FSHunt Settings dialog). ");
				scrollPane1.setViewportView(textArea1);
			}
			contentPane.add(scrollPane1);
			scrollPane1.setBounds(30, 390, 475, 110);

			// ---- label10 ----
			label10.setText("RandomBE: ");
			contentPane.add(label10);
			label10.setBounds(new Rectangle(new Point(305, 155), label10
					.getPreferredSize()));

			// ---- label11 ----
			label11.setText("RandomBF:");
			contentPane.add(label11);
			label11.setBounds(new Rectangle(new Point(305, 175), label11
					.getPreferredSize()));

			// ---- BE ----
			BE.setText("20");
			contentPane.add(BE);
			BE.setBounds(370, 100, 30, BE.getPreferredSize().height);

			// ---- RBE ----
			RBE.setText("10");
			contentPane.add(RBE);
			RBE.setBounds(new Rectangle(new Point(365, 150), RBE
					.getPreferredSize()));

			// ---- RBF ----
			RBF.setText("10");
			contentPane.add(RBF);
			RBF.setBounds(new Rectangle(new Point(365, 170), RBF
					.getPreferredSize()));

			// ---- LoadSettings ----
			LoadSettings.setText("Load Settings");
			LoadSettings.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						LoadSettingsActionPerformed(e);
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
			});
			contentPane.add(LoadSettings);
			LoadSettings.setBounds(30, 320, 105, LoadSettings
					.getPreferredSize().height);

			// ---- SaveSettings ----
			SaveSettings.setText("Save Settings");
			SaveSettings.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						SaveSettingsActionPerformed(e);
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
			});
			contentPane.add(SaveSettings);
			SaveSettings.setBounds(30, 290, 105, SaveSettings
					.getPreferredSize().height);

			// ---- StartButton ----
			StartButton.setText("Start!");
			StartButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					StartButtonActionPerformed(e);
				}
			});
			contentPane.add(StartButton);
			StartButton.setBounds(365, 290, 130, 50);

			// ---- BF ----
			BF.setText("10");
			contentPane.add(BF);
			BF.setBounds(325, 125, 25, BF.getPreferredSize().height);

			// ---- AB ----
			AB.setText("1");
			contentPane.add(AB);
			AB.setBounds(105, 125, 35, AB.getPreferredSize().height);

			// ---- tetx ----
			tetx.setText("Antiban Level:");
			contentPane.add(tetx);
			tetx.setBounds(new Rectangle(new Point(30, 130), tetx
					.getPreferredSize()));

			// ---- label3 ----
			label3
					.setText("(See bottom for \ninstructions on \nhow to set up)");
			contentPane.add(label3);
			label3.setBounds(new Rectangle(new Point(180, 50), label3
					.getPreferredSize()));

			// ---- UT ----
			UT.setText("Use Timer?");
			UT.setSelected(true);
			UT.addChangeListener(new ChangeListener() {
				public void stateChanged(ChangeEvent e) {
					UTStateChanged();
				}
			});
			contentPane.add(UT);
			UT.setBounds(new Rectangle(new Point(25, 150), UT
					.getPreferredSize()));

			// ---- label4 ----
			label4.setText("Time To Run: ");
			contentPane.add(label4);
			label4.setBounds(new Rectangle(new Point(30, 175), label4
					.getPreferredSize()));

			// ---- TT ----
			TT.setFont(TT.getFont().deriveFont(
					TT.getFont().getStyle() & ~Font.BOLD));
			TT.setText("1:0:0");
			contentPane.add(TT);
			TT.setBounds(100, 170, 55, TT.getPreferredSize().height);

			// ---- OpenTrapSetup ----
			OpenTrapSetup.setText("Open Trap Setup");
			OpenTrapSetup.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						OpenTrapSetupActionPerformed();
					} catch (IllegalMonitorStateException e1) {
						e1.printStackTrace();
					} catch (InterruptedException e1) {
						e1.printStackTrace();
					}
				}
			});
			contentPane.add(OpenTrapSetup);
			OpenTrapSetup.setBounds(new Rectangle(new Point(30, 205),
					OpenTrapSetup.getPreferredSize()));

			// ---- label12 ----
			label12.setText("(MANDATORY)");
			label12.setFont(label12.getFont().deriveFont(
					label12.getFont().getStyle() | Font.BOLD));
			contentPane.add(label12);
			label12.setBounds(new Rectangle(new Point(45, 230), label12
					.getPreferredSize()));

			{ // compute preferred size
				Dimension preferredSize = new Dimension();
				for (int i = 0; i < contentPane.getComponentCount(); i++) {
					Rectangle bounds = contentPane.getComponent(i).getBounds();
					preferredSize.width = Math.max(bounds.x + bounds.width,
							preferredSize.width);
					preferredSize.height = Math.max(bounds.y + bounds.height,
							preferredSize.height);
				}
				Insets insets = contentPane.getInsets();
				preferredSize.width += insets.right;
				preferredSize.height += insets.bottom;
				contentPane.setMinimumSize(preferredSize);
				contentPane.setPreferredSize(preferredSize);
			}
			setSize(550, 555);
			setLocationRelativeTo(getOwner());
			// JFormDesigner - End of component initialization
			// //GEN-END:initComponents
		}

		// JFormDesigner - Variables declaration - DO NOT MODIFY
		// //GEN-BEGIN:variables
		private JLabel label1;
		private JLabel label2;
		private JComboBox WTH;
		private JCheckBox BB;
		private JCheckBox CB;
		private JLabel label5;
		private JLabel label6;
		private JLabel label7;
		private JLabel label8;
		private JLabel label9;
		private JScrollPane scrollPane1;
		private JTextArea textArea1;
		private JLabel label10;
		private JLabel label11;
		private JTextField BE;
		private JTextField RBE;
		private JTextField RBF;
		private JButton LoadSettings;
		private JButton SaveSettings;
		private JButton StartButton;
		private JTextField BF;
		private JTextField AB;
		private JLabel tetx;
		private JLabel label3;
		private JCheckBox UT;
		private JLabel label4;
		private JTextField TT;
		private JButton OpenTrapSetup;
		private JLabel label12;
		// JFormDesigner - End of variables declaration //GEN-END:variables
	}

	@SuppressWarnings("serial")
	public class FSHuntSelector extends JFrame {

		public FSHuntSelector() {
			initComponents();
		}

		private boolean[] getBoxes() {
			boolean[] Boxes = { checkBox1.isSelected(), checkBox2.isSelected(),
					checkBox3.isSelected(), checkBox4.isSelected(),
					checkBox5.isSelected(), checkBox6.isSelected(),
					checkBox7.isSelected(), checkBox8.isSelected(),
					checkBox9.isSelected(), checkBox10.isSelected(),
					checkBox11.isSelected(), checkBox12.isSelected(),
					checkBox13.isSelected(), checkBox14.isSelected(),
					checkBox15.isSelected(), checkBox16.isSelected(),
					checkBox17.isSelected(), checkBox18.isSelected(),
					checkBox19.isSelected(), checkBox20.isSelected(),
					checkBox21.isSelected(), checkBox22.isSelected(),
					checkBox23.isSelected(), checkBox24.isSelected(),
					checkBox25.isSelected() };
			return Boxes;
		}

		private int pointToBox(Point WhichPoint) {
			switch (WhichPoint.y) {
			case 0:
				switch (WhichPoint.x) {
				case 0:
					return 13;
				case 1:
					return 14;
				case 2:
					return 15;
				case -1:
					return 12;
				case -2:
					return 11;
				}
			case 1:
				switch (WhichPoint.x) {
				case 0:
					return 8;
				case 1:
					return 9;
				case 2:
					return 10;
				case -1:
					return 7;
				case -2:
					return 5;
				}
			case 2:
				switch (WhichPoint.x) {
				case 0:
					return 6;
				case 1:
					return 4;
				case 2:
					return 3;
				case -1:
					return 1;
				case -2:
					return 2;
				}
			case -1:
				switch (WhichPoint.x) {
				case 0:
					return 18;
				case 1:
					return 19;
				case 2:
					return 10;
				case -1:
					return 17;
				case -2:
					return 16;
				}
			case -2:
				switch (WhichPoint.x) {
				case 0:
					return 23;
				case 1:
					return 24;
				case 2:
					return 25;
				case -1:
					return 22;
				case -2:
					return 21;
				}
			default:
				log("Error occured: Error PTBX. Report to Tad_");
			}
			return -1;
		}

		private Point boxToPoint(int WhichBox) {
			switch (WhichBox) {
			case 0:
				return new Point(-1, 2);
			case 1:
				return new Point(-2, 2);
			case 2:
				return new Point(2, 2);
			case 3:
				return new Point(1, 2);
			case 4:
				return new Point(-2, 1);
			case 5:
				return new Point(0, 2);
			case 6:
				return new Point(-1, 1);
			case 7:
				return new Point(0, 1);
			case 8:
				return new Point(1, 1);
			case 9:
				return new Point(2, 1);
			case 10:
				return new Point(-2, 0);
			case 11:
				return new Point(-1, 0);
			case 12:
				return new Point(0, 0);
			case 13:
				return new Point(1, 0);
			case 14:
				return new Point(2, 0);
			case 15:
				return new Point(-2, -1);
			case 16:
				return new Point(-1, -1);
			case 17:
				return new Point(0, -1);
			case 18:
				return new Point(1, -1);
			case 19:
				return new Point(2, -1);
			case 20:
				return new Point(-2, -2);
			case 21:
				return new Point(-1, -2);
			case 22:
				return new Point(0, -2);
			case 23:
				return new Point(1, -2);
			case 24:
				return new Point(2, -2);
			default:
				return null;
			}
		}

		public boolean arrayContains(Point[] MyArray, Point LookFor) {
			int i;
			for (i = 0; i < MyArray.length; i++)
				if (MyArray[i].getX() == LookFor.getX()
						&& MyArray[i].getY() == LookFor.getY())
					return true;
			return false;
		}

		private Point[] addToSpotArray(boolean Boxes[]) {
			int i, j = 0, k = 0, l, ArrLength = 0;
			Point[] SpotsCopy = null;
			if (Spots != null)
				if (Spots.length == 0)
					Spots = null;
			if (Spots != null) {
				SpotsCopy = new Point[Spots.length];
				System.arraycopy(Spots, 0, SpotsCopy, 0, Spots.length);
			} else
				SpotsCopy = null;

			for (i = 0; i < Boxes.length; i++)
				if (Boxes[i])
					ArrLength++;
			Spots = new Point[ArrLength];
			if (SpotsCopy != null && ArrLength == SpotsCopy.length)
				return SpotsCopy;
			for (l = 0; l < Boxes.length; l++) {
				for (i = 0; i < Boxes.length; i++) {
					if (Boxes[i]) {
						if (SpotsCopy != null && SpotsCopy.length > 0) {
							if (j >= Spots.length) {
								break;
							}
							if (k == SpotsCopy.length) {
								if (!arrayContains(SpotsCopy, boxToPoint(i))) {
									Spots[j] = boxToPoint(i);
									j++;
								}
							} else if (SpotsCopy[k] != null) {
								if (SpotsCopy[k].getX() == boxToPoint(i).getX()
										&& SpotsCopy[k].getY() == boxToPoint(i)
												.getY()) {
									Spots[j] = SpotsCopy[k];
									j++;
									k++;
								}
							}
						}
						if (SpotsCopy == null) {
							Spots[j] = boxToPoint(i);
							j++;
							if (j >= Spots.length)
								break;
						}
					}
				}

				if (SpotsCopy != null)
					if (k >= SpotsCopy.length && j >= Spots.length)
						break;
				if (j >= Spots.length)
					break;
			}
			return Spots;
		}

		private Point[] boxesToPoints(boolean[] Boxes) {
			int i, ArrLength = 0;

			for (i = 0; i < Boxes.length; i++)
				if (Boxes[i])
					ArrLength++;
			if (Spots == null || ArrLength != Spots.length)
				Spots = addToSpotArray(Boxes);
			return Spots;
		}

		private void checkBoxesStateChanged() {
			Spots = boxesToPoints(getBoxes());
		}

		private void resetSpots(boolean BoxesToo) {
			Spots = null;
			if (BoxesToo)
				for (int i = 0; i <= 24; i++)
					setCheckBox(boxToPoint(i), false);
		}

		private void LoadButtonActionPerformed() throws IOException {
			FileInputStream MyLocs;
			String myValue;
			int ArrayLength = 0;

			try {
				MyLocs = new FileInputStream("FSHunt Locations.txt");
			} catch (FileNotFoundException e1) {
				log("Failed loading Locations!");
				return;
			}

			BufferedReader d = new BufferedReader(new InputStreamReader(MyLocs));
			while ((myValue = d.readLine()) != null)
				ArrayLength++;
			String[] Values = new String[ArrayLength];
			d.close();
			MyLocs.close();

			try {
				MyLocs = new FileInputStream("FSHunt Locations.txt");
			} catch (FileNotFoundException e1) {
				log("Failed loading Locations!");
				return;
			}
			d = new BufferedReader(new InputStreamReader(MyLocs));
			ArrayLength = 0;

			while ((myValue = d.readLine()) != null) {
				Values[ArrayLength] = myValue;
				ArrayLength++;
			}
			String[] XandY = new String[2];
			int Temp = 0;
			resetSpots(true);
			for (String item : Values) {
				XandY = item.split(",");
				setCheckBox(new Point(new Float(XandY[0]).intValue(),
						new Float(XandY[1]).intValue()), true);
			}
			resetSpots(false);
			Spots = new Point[ArrayLength];
			for (String item : Values) {
				XandY = item.split(",");
				Spots[Temp] = new Point(new Float(XandY[0]).intValue(),
						new Float(XandY[1]).intValue());
				Temp++;
				if (Temp >= Spots.length)
					break;
			}
			MyLocs.close();
			log("Successfully loaded locations");
		}

		private void setCheckBox(Point myPoint, boolean setTrue) {
			int WhichBox = pointToBox(myPoint);
			switch (WhichBox) {
			case 1:
				checkBox1.setSelected(setTrue);
				break;
			case 2:
				checkBox2.setSelected(setTrue);
				break;
			case 3:
				checkBox3.setSelected(setTrue);
				break;
			case 4:
				checkBox4.setSelected(setTrue);
				break;
			case 5:
				checkBox5.setSelected(setTrue);
				break;
			case 6:
				checkBox6.setSelected(setTrue);
				break;
			case 7:
				checkBox7.setSelected(setTrue);
				break;
			case 8:
				checkBox8.setSelected(setTrue);
				break;
			case 9:
				checkBox9.setSelected(setTrue);
				break;
			case 10:
				checkBox10.setSelected(setTrue);
				break;
			case 11:
				checkBox11.setSelected(setTrue);
				break;
			case 12:
				checkBox12.setSelected(setTrue);
				break;
			case 13:
				checkBox13.setSelected(setTrue);
				break;
			case 14:
				checkBox14.setSelected(setTrue);
				break;
			case 15:
				checkBox15.setSelected(setTrue);
				break;
			case 16:
				checkBox16.setSelected(setTrue);
				break;
			case 17:
				checkBox17.setSelected(setTrue);
				break;
			case 18:
				checkBox18.setSelected(setTrue);
				break;
			case 19:
				checkBox19.setSelected(setTrue);
				break;
			case 20:
				checkBox20.setSelected(setTrue);
				break;
			case 21:
				checkBox21.setSelected(setTrue);
				break;
			case 22:
				checkBox22.setSelected(setTrue);
				break;
			case 23:
				checkBox23.setSelected(setTrue);
				break;
			case 24:
				checkBox24.setSelected(setTrue);
				break;
			case 25:
				checkBox25.setSelected(setTrue);
				break;
			default:
				log("Something went wrong. Error CBSX. Alert Tad_");
			}

		}

		private void SaveButtonActionPerformed() throws IOException {
			FileOutputStream MyLocs;
			int i;
			try {
				MyLocs = new FileOutputStream("FSHunt Locations.txt");
			} catch (FileNotFoundException e1) {
				log("Failed saving Locations!");
				return;
			}
			for (i = 0; i < Spots.length; i++)
				new PrintStream(MyLocs).println(Spots[i].getX() + ","
						+ Spots[i].getY());
			MyLocs.close();
			log("Locations Saved");
		}

		private void StartButtonActionPerformed() {
			KeepOGUI = false;
			dispose();
		}

		private void ResetActionPerformed() {
			resetSpots(true);
		}

		private void initComponents() {
			// JFormDesigner - Component initialization - DO NOT MODIFY
			// //GEN-BEGIN:initComponents
			checkBox1 = new JCheckBox();
			checkBox2 = new JCheckBox();
			checkBox3 = new JCheckBox();
			checkBox4 = new JCheckBox();
			checkBox5 = new JCheckBox();
			checkBox6 = new JCheckBox();
			checkBox7 = new JCheckBox();
			checkBox8 = new JCheckBox();
			checkBox9 = new JCheckBox();
			checkBox10 = new JCheckBox();
			checkBox11 = new JCheckBox();
			checkBox12 = new JCheckBox();
			checkBox13 = new JCheckBox();
			checkBox14 = new JCheckBox();
			checkBox15 = new JCheckBox();
			checkBox16 = new JCheckBox();
			checkBox17 = new JCheckBox();
			checkBox18 = new JCheckBox();
			checkBox19 = new JCheckBox();
			checkBox20 = new JCheckBox();
			checkBox21 = new JCheckBox();
			checkBox22 = new JCheckBox();
			checkBox23 = new JCheckBox();
			checkBox24 = new JCheckBox();
			checkBox25 = new JCheckBox();
			label1 = new JLabel();
			scrollPane1 = new JScrollPane();
			textArea1 = new JTextArea();
			LoadButton = new JButton();
			SaveButton = new JButton();
			StartButton = new JButton();
			label2 = new JLabel();
			Reset = new JButton();

			// ======== this ========
			setAlwaysOnTop(true);
			setTitle("FSHunt Placement Selector");
			Container contentPane = getContentPane();
			contentPane.setLayout(null);

			// ---- checkBox1 ----
			checkBox1.addChangeListener(new ChangeListener() {
				public void stateChanged(ChangeEvent e) {
					checkBoxesStateChanged();
				}
			});
			contentPane.add(checkBox1);
			checkBox1.setBounds(new Rectangle(new Point(65, 85), checkBox1
					.getPreferredSize()));

			// ---- checkBox2 ----
			checkBox2.addChangeListener(new ChangeListener() {
				public void stateChanged(ChangeEvent e) {
					checkBoxesStateChanged();
				}
			});
			contentPane.add(checkBox2);
			checkBox2.setBounds(45, 85, 21, 21);

			// ---- checkBox3 ----
			checkBox3.addChangeListener(new ChangeListener() {
				public void stateChanged(ChangeEvent e) {
					checkBoxesStateChanged();
				}
			});
			contentPane.add(checkBox3);
			checkBox3.setBounds(125, 85, 21, 21);

			// ---- checkBox4 ----
			checkBox4.addChangeListener(new ChangeListener() {
				public void stateChanged(ChangeEvent e) {
					checkBoxesStateChanged();
				}
			});
			contentPane.add(checkBox4);
			checkBox4.setBounds(105, 85, 21, 21);

			// ---- checkBox5 ----
			checkBox5.addChangeListener(new ChangeListener() {
				public void stateChanged(ChangeEvent e) {
					checkBoxesStateChanged();
				}
			});
			contentPane.add(checkBox5);
			checkBox5.setBounds(45, 105, 21, 21);

			// ---- checkBox6 ----
			checkBox6.addChangeListener(new ChangeListener() {
				public void stateChanged(ChangeEvent e) {
					checkBoxesStateChanged();
				}
			});
			contentPane.add(checkBox6);
			checkBox6.setBounds(85, 85, 21, 21);

			// ---- checkBox7 ----
			checkBox7.addChangeListener(new ChangeListener() {
				public void stateChanged(ChangeEvent e) {
					checkBoxesStateChanged();
				}
			});
			contentPane.add(checkBox7);
			checkBox7.setBounds(65, 105, 21, 21);

			// ---- checkBox8 ----
			checkBox8.addChangeListener(new ChangeListener() {
				public void stateChanged(ChangeEvent e) {
					checkBoxesStateChanged();
				}
			});
			contentPane.add(checkBox8);
			checkBox8.setBounds(85, 105, 21, 21);

			// ---- checkBox9 ----
			checkBox9.addChangeListener(new ChangeListener() {
				public void stateChanged(ChangeEvent e) {
					checkBoxesStateChanged();
				}
			});
			contentPane.add(checkBox9);
			checkBox9.setBounds(105, 105, 21, 21);

			// ---- checkBox10 ----
			checkBox10.addChangeListener(new ChangeListener() {
				public void stateChanged(ChangeEvent e) {
					checkBoxesStateChanged();
				}
			});
			contentPane.add(checkBox10);
			checkBox10.setBounds(125, 105, 21, 21);

			// ---- checkBox11 ----
			checkBox11.addChangeListener(new ChangeListener() {
				public void stateChanged(ChangeEvent e) {
					checkBoxesStateChanged();
				}
			});
			contentPane.add(checkBox11);
			checkBox11.setBounds(45, 125, 21, 21);

			// ---- checkBox12 ----
			checkBox12.addChangeListener(new ChangeListener() {
				public void stateChanged(ChangeEvent e) {
					checkBoxesStateChanged();
				}
			});
			contentPane.add(checkBox12);
			checkBox12.setBounds(65, 125, 21, 21);

			// ---- checkBox13 ----
			checkBox13.addChangeListener(new ChangeListener() {
				public void stateChanged(ChangeEvent e) {
					checkBoxesStateChanged();
				}
			});
			contentPane.add(checkBox13);
			checkBox13.setBounds(85, 125, 21, 21);

			// ---- checkBox14 ----
			checkBox14.addChangeListener(new ChangeListener() {
				public void stateChanged(ChangeEvent e) {
					checkBoxesStateChanged();
				}
			});
			contentPane.add(checkBox14);
			checkBox14.setBounds(105, 125, 21, 21);

			// ---- checkBox15 ----
			checkBox15.addChangeListener(new ChangeListener() {
				public void stateChanged(ChangeEvent e) {
					checkBoxesStateChanged();
				}
			});
			contentPane.add(checkBox15);
			checkBox15.setBounds(125, 125, 21, 21);

			// ---- checkBox16 ----
			checkBox16.addChangeListener(new ChangeListener() {
				public void stateChanged(ChangeEvent e) {
					checkBoxesStateChanged();
				}
			});
			contentPane.add(checkBox16);
			checkBox16.setBounds(45, 145, 21, 21);

			// ---- checkBox17 ----
			checkBox17.addChangeListener(new ChangeListener() {
				public void stateChanged(ChangeEvent e) {
					checkBoxesStateChanged();
				}
			});
			contentPane.add(checkBox17);
			checkBox17.setBounds(65, 145, 21, 21);

			// ---- checkBox18 ----
			checkBox18.addChangeListener(new ChangeListener() {
				public void stateChanged(ChangeEvent e) {
					checkBoxesStateChanged();
				}
			});
			contentPane.add(checkBox18);
			checkBox18.setBounds(85, 145, 21, 21);

			// ---- checkBox19 ----
			checkBox19.addChangeListener(new ChangeListener() {
				public void stateChanged(ChangeEvent e) {
					checkBoxesStateChanged();
				}
			});
			contentPane.add(checkBox19);
			checkBox19.setBounds(105, 145, 21, 21);

			// ---- checkBox20 ----
			checkBox20.addChangeListener(new ChangeListener() {
				public void stateChanged(ChangeEvent e) {
					checkBoxesStateChanged();
				}
			});
			contentPane.add(checkBox20);
			checkBox20.setBounds(125, 145, 21, 21);

			// ---- checkBox21 ----
			checkBox21.addChangeListener(new ChangeListener() {
				public void stateChanged(ChangeEvent e) {
					checkBoxesStateChanged();
				}
			});
			contentPane.add(checkBox21);
			checkBox21.setBounds(45, 165, 21, 21);

			// ---- checkBox22 ----
			checkBox22.addChangeListener(new ChangeListener() {
				public void stateChanged(ChangeEvent e) {
					checkBoxesStateChanged();
				}
			});
			contentPane.add(checkBox22);
			checkBox22.setBounds(65, 165, 21, 21);

			// ---- checkBox23 ----
			checkBox23.addChangeListener(new ChangeListener() {
				public void stateChanged(ChangeEvent e) {
					checkBoxesStateChanged();
				}
			});
			contentPane.add(checkBox23);
			checkBox23.setBounds(85, 165, 21, 21);

			// ---- checkBox24 ----
			checkBox24.addChangeListener(new ChangeListener() {
				public void stateChanged(ChangeEvent e) {
					checkBoxesStateChanged();
				}
			});
			contentPane.add(checkBox24);
			checkBox24.setBounds(105, 165, 21, 21);

			// ---- checkBox25 ----
			checkBox25.addChangeListener(new ChangeListener() {
				public void stateChanged(ChangeEvent e) {
					checkBoxesStateChanged();
				}
			});
			contentPane.add(checkBox25);
			checkBox25.setBounds(125, 165, 21, 21);

			// ---- label1 ----
			label1.setText("Placement Selector");
			label1.setFont(new Font("Segoe Print", Font.BOLD, 18));
			contentPane.add(label1);
			label1.setBounds(new Rectangle(new Point(225, 15), label1
					.getPreferredSize()));

			// ======== scrollPane1 ========
			{

				// ---- textArea1 ----
				textArea1
						.setText("Here you can select how you wish the bot to set the\ntraps.\n\nThe center check box is your original position (when you\nstart the script). Based on that, check, IN ORDER, how\nyou wish to prioritize your trap placing. For best\nresults, check all of the boxes (again, in the order you\nwish to prioritize)");
				scrollPane1.setViewportView(textArea1);
			}
			contentPane.add(scrollPane1);
			scrollPane1.setBounds(new Rectangle(new Point(185, 70), scrollPane1
					.getPreferredSize()));

			// ---- LoadButton ----
			LoadButton.setText("Load Settings");
			LoadButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						LoadButtonActionPerformed();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
			});
			contentPane.add(LoadButton);
			LoadButton.setBounds(new Rectangle(new Point(45, 205), LoadButton
					.getPreferredSize()));

			// ---- SaveButton ----
			SaveButton.setText("Save Settings");
			SaveButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						SaveButtonActionPerformed();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
			});
			contentPane.add(SaveButton);
			SaveButton.setBounds(new Rectangle(new Point(45, 230), SaveButton
					.getPreferredSize()));

			// ---- StartButton ----
			StartButton.setText("Continue");
			StartButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					StartButtonActionPerformed();
				}
			});
			contentPane.add(StartButton);
			StartButton.setBounds(515, 230, 100, 40);

			// ---- label2 ----
			label2.setText("Don't close with the X ^. Use the button below.");
			contentPane.add(label2);
			label2.setBounds(new Rectangle(new Point(425, 5), label2
					.getPreferredSize()));

			// ---- Reset ----
			Reset.setText("Reset");
			Reset.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					ResetActionPerformed();
				}
			});
			contentPane.add(Reset);
			Reset.setBounds(new Rectangle(new Point(65, 60), Reset
					.getPreferredSize()));

			{ // compute preferred size
				Dimension preferredSize = new Dimension();
				for (int i = 0; i < contentPane.getComponentCount(); i++) {
					Rectangle bounds = contentPane.getComponent(i).getBounds();
					preferredSize.width = Math.max(bounds.x + bounds.width,
							preferredSize.width);
					preferredSize.height = Math.max(bounds.y + bounds.height,
							preferredSize.height);
				}
				Insets insets = contentPane.getInsets();
				preferredSize.width += insets.right;
				preferredSize.height += insets.bottom;
				contentPane.setMinimumSize(preferredSize);
				contentPane.setPreferredSize(preferredSize);
			}
			pack();
			setLocationRelativeTo(getOwner());
			// JFormDesigner - End of component initialization
			// //GEN-END:initComponents
		}

		// JFormDesigner - Variables declaration - DO NOT MODIFY
		// //GEN-BEGIN:variables
		private JCheckBox checkBox1;
		private JCheckBox checkBox2;
		private JCheckBox checkBox3;
		private JCheckBox checkBox4;
		private JCheckBox checkBox5;
		private JCheckBox checkBox6;
		private JCheckBox checkBox7;
		private JCheckBox checkBox8;
		private JCheckBox checkBox9;
		private JCheckBox checkBox10;
		private JCheckBox checkBox11;
		private JCheckBox checkBox12;
		private JCheckBox checkBox13;
		private JCheckBox checkBox14;
		private JCheckBox checkBox15;
		private JCheckBox checkBox16;
		private JCheckBox checkBox17;
		private JCheckBox checkBox18;
		private JCheckBox checkBox19;
		private JCheckBox checkBox20;
		private JCheckBox checkBox21;
		private JCheckBox checkBox22;
		private JCheckBox checkBox23;
		private JCheckBox checkBox24;
		private JCheckBox checkBox25;
		private JLabel label1;
		private JScrollPane scrollPane1;
		private JTextArea textArea1;
		private JButton LoadButton;
		private JButton SaveButton;
		private JButton StartButton;
		private JLabel label2;
		private JButton Reset;
		// JFormDesigner - End of variables declaration //GEN-END:variables
	}

}