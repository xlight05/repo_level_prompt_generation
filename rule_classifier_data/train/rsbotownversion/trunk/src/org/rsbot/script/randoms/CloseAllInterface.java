package org.rsbot.script.randoms;

import java.util.LinkedList;
import java.util.List;

import org.rsbot.script.Random;
import org.rsbot.script.ScriptManifest;
import org.rsbot.script.wrappers.RSInterface;
import org.rsbot.script.wrappers.RSInterfaceChild;

/**
 * Closes interfaces that scripts may open by mistake.
 * 
 * Updated 29/06/10. Updated by Famous(Updated/Added new interfaces)
 * Last updated 29/08/10. Updated by Minardi(Updated/Added new interfaces)
 */

@ScriptManifest(authors = { "Jacmob", "HeyyamaN" }, name = "Interface Closer", version = 2.0)
public class CloseAllInterface extends Random {
    private List<RSInterfaceChild> components = new LinkedList<RSInterfaceChild>();
    {
        addChild(743, 20); // Audio
        addChild(767, 10); // Bank help
        addChild(499, 29); // Stats
        addChild(741, 9); // Stat Advance
        addChild(594, 48); // Report
        addChild(275, 8); // Completed Quest
        addChild(178, 17); // Undone Quest
        addChild(206, 16); // Price check
        addChild(266, 11); // Grove  ???
        addChild(102, 13); // Death items
        addChild(14, 3); // Pin settings
        addChild(157, 13); // Quick chat help
        addChild(764, 2); // Objectives
        addChild(895, 19); // Advisor
        addChild(109, 13); // Bank Collect
        addChild(590, 3); // Clan Setup
        addChild(398, 19); // House Options
        addChild(667, 11); // Equipment Stats
        addChild(335, 12); // Trade Window
    }
    private void addChild(int parent, int idx) {
        components.add(RSInterface.getInterface(parent).getChild(idx));
    }
    public boolean activateCondition() {
        if (isLoggedIn()) {
            if (RSInterface.getInterface(755).getChild(45).isValid()) { // World
                // map
                if (RSInterface.getChildInterface(755, 0).getComponents().length > 0) {
                    return true;
                }
            }
     if (RSInterface.getInterface(978).isValid()) { // Graphics Settings
      return true;
     }
            for (RSInterfaceChild c : components) {
                if (c.isValid()) {
                    return true;
                }
            }
        }
        return false;
    }
    public int loop() {
        wait(random(400, 1000));
        if (RSInterface.getInterface(755).isValid()
                && (RSInterface.getChildInterface(755, 0).getComponents().length > 0)) {
            atInterface(755, 44);
            return random(400, 1000);
        }
 if (RSInterface.getInterface(978).isValid()) {
            atInterface(742, 14);
            return random(400, 1000);
 }
        for (RSInterfaceChild c : components) {
            if (c.isValid()) {
                atInterface(c);
                wait(random(400, 1000));
                break;
            }
        }
        return -1;
    }
}