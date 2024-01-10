import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.util.Map;
import javax.swing.UIManager;

import org.rsbot.event.listeners.PaintListener;
import org.rsbot.script.Script;
import org.rsbot.script.ScriptManifest;
import org.rsbot.script.wrappers.RSNPC;
import org.rsbot.script.wrappers.RSTile;

@ScriptManifest(authors = { "Hatred" }, name = "Hatred's AIO Stunner", category = "Magic", description = "<html><body>All settings are in the GUI.</body></html>")
public class HatredsAIOStunner extends Script implements PaintListener {
	
	private enum State {
		CLICKSPELL, CLICKNPC
	}
	
	private State state = State.CLICKSPELL;
	
	private boolean scriptStarted = false;
	private int npcID = -1;
	private int spell = -1;
	private RSNPC[] npcArray = getNPCArray(false);
	private int[] id = new int[npcArray.length];	
	private RSTile startTile = getMyPlayer().getLocation();
	private long startExp = skills.getCurrentSkillExp(STAT_MAGIC);
	private long startTime = 0;
	private long casts = 0;

	protected int getMouseSpeed() {
	return random(6, 8);
	}
	
	@Override
	public boolean onStart(Map<String, String> args) {
		log("Thank you for using my script.");
		log("Please post any feedback/suggestions/bugs on my thread.");
		for (int i = 0; i < id.length; i++) {
			id[i] = i;
		}
		StunnerGUI gui = new StunnerGUI();
		gui.main(new String[] { });
		do {
			wait(random(200, 400));
		} while (!scriptStarted);
		startTime = System.currentTimeMillis();
		return true;
	}
	
	@Override
	public void onFinish() {
		log("Thanks for using my script!");
	}
	
	private void handleState() {
		switch (state) {
		case CLICKSPELL:
			if (distanceTo(startTile) < 50) {
				if (getCurrentTab() != TAB_MAGIC) {
					openTab(TAB_MAGIC);
					wait(random(1000, 1200));
				}
				switch (spell) {
				case 0:
					atInterface(192, 26);
				break;
				case 1:
					atInterface(192, 31);
				break;
				case 2:
					atInterface(192, 35);
				break;
				case 3:
					atInterface(192, 75);
				break;
				case 4:
					atInterface(192, 78);
				break;
				case 5:
					atInterface(192, 82);
				break;
				}
				wait(random(200, 400));
				state = State.CLICKNPC;
			} else {
				log("Probably in a random event. Waiting 10 seconds.");
				wait(10000);
			}
		break;
		case CLICKNPC:
			if (distanceTo(startTile) < 50) {
				RSNPC npc = null;
				int failsafe = 0;
				do {
					npc = npcArray[npcID];
					failsafe++;
					wait(random(500, 1000));
				} while (npc == null && failsafe != 10);
				if (atNPC(npc, "Cast"))	casts++;				
				wait(random(400, 600));
				state = State.CLICKSPELL;
			} else {
				log("Probably in a random event. Waiting 10 seconds.");
				wait(10000);
			}
		break;
		}
	}
	
	private void antiban() {
		switch(random(0, 2000)) {
		case 0:
		case 1:
		case 2:
		case 3:
		case 4:
		case 5:
			moveMouse(new Point(random(555, 579), random(170, 200)));
			wait(random(400, 700));
			clickMouse(true);
			wait(random(500, 700));
			moveMouse(new Point(random(552, 609), random(352, 376)));
			wait(random(1500, 2000));
			moveMouse(new Point(random(734, 757), random(170, 200)));
			wait(random(400, 700));
			clickMouse(true);
			wait(random(500, 900));
		break;
		case 6:
		case 7:
		case 8:
		case 9:
		case 10:
			moveMouse(new Point(random(523, 548), random(470, 500)));
			wait(random(500, 600));
			clickMouse(true);
			wait(random(600, 700));
			moveMouse(new Point(random(594, 699), random(399, 401)));
			wait(random(1600, 2100));
			moveMouse(new Point(random(734, 757), random(170, 200)));
			wait(random(400, 700));
			clickMouse(true);
			wait(random(500, 900));
		break;
		case 11:
		case 12:
		case 13:
		case 14:
		case 15:
		case 16:
			setCameraRotation(getCameraAngle()+random(-50, 80));
		break;
		case 17:
		case 18:
		case 19:
		case 20:
		case 21:
			moveMouseSlightly();
		break;
		default:
		break;
		}
	}
	
	@Override
	public int loop() {
		antiban();
		handleState();
		return random(100, 200);
	}
	
	private void drawNPCArray(Graphics g) {
		int x, y;
		for (int ids : id) {
			RSNPC npc = npcArray[ids];
			x = npc.getScreenLocation().x-14;
			y = npc.getScreenLocation().y-30;
			g.setColor(new Color(51, 102, 255, 120));
			g.drawRect(x, y, 28, 60);
			g.setColor(new Color(51, 102, 255, 120));
			g.fillRect(x, y, 28, 60);
			g.setColor(new Color(0, 0, 0));
			g.setFont(new Font("Arial", Font.BOLD, 12));
			g.drawString(""+ids, x+3, y+5);
		}
	}

	private final RenderingHints rh = new RenderingHints(
		RenderingHints.KEY_TEXT_ANTIALIASING,
		RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

	public void onRepaint(Graphics g) {
		if (!scriptStarted) {
			drawNPCArray(g);
		} else {
			long exp = skills.getCurrentSkillExp(STAT_MAGIC)-startExp;
			long timeRunning = (System.currentTimeMillis()-startTime)+1000;
			long castsPH = (casts*3600)/(timeRunning/1000);
			long expPH = (exp*3600)/(timeRunning/1000);
			long hours, minutes, seconds;
			hours = timeRunning / (1000 * 60 * 60);
			timeRunning -= hours * (1000 * 60 * 60);
			minutes = timeRunning / (1000 * 60);
			timeRunning -= minutes * (1000 * 60);
			seconds = timeRunning / 1000;
			((Graphics2D)g).setRenderingHints(rh);
			g.setColor(new Color(0, 0, 0, 100));
			g.fillRect(348, 260, 162, 57);
			g.setColor(new Color(204, 0, 0, 155));
			g.fillRect(348, 234, 162, 96);
			g.setColor(new Color(204, 0, 0));
			g.drawRect(348, 234, 162, 96);
			g.setFont(new Font("Arial", 0, 19));
			g.setColor(new Color(0, 0, 0));
			g.drawString("AIO Stunner", 357, 198+57);
			g.setFont(new Font("Arial", 0, 13));
			g.setColor(new Color(0, 0, 0));
			g.drawString("by Hatred", 450, 216+57);
			g.setFont(new Font("Arial", 0, 12));
			g.setColor(new Color(0, 0, 0));
			g.drawString("Magic Exp:", 354, 237+57);
			g.setFont(new Font("Arial", 0, 12));
			g.setColor(new Color(0, 0, 0));
			g.drawString(exp+" ("+expPH+" p/h)", 417, 237+57);
			g.setFont(new Font("Arial", 0, 12));
			g.setColor(new Color(0, 0, 0));
			g.drawString("Spells Cast:", 354, 252+57);
			g.setFont(new Font("Arial", 0, 12));
			g.setColor(new Color(0, 0, 0));
			g.drawString(casts+" ("+castsPH+" p/h)", 426, 252+57);
			g.setFont(new Font("Arial", 0, 12));
			g.setColor(new Color(0, 0, 0));
			g.drawString("Time Running:", 354, 267+57);
			g.setFont(new Font("Arial", 0, 12));
			g.setColor(new Color(0, 0, 0));
			g.drawString(hours+":"+minutes+":"+seconds, 441, 267+57);
		}
	}

	/**
	 *
	 * @author Brandon
	 */
	class StunnerGUI extends javax.swing.JFrame {

	    /**
		 * 
		 */
		private static final long serialVersionUID = 8735467489534176293L;
		/** Creates new form StunnerGUI */
	    public StunnerGUI() {
	        initComponents();
	    }

	    /** This method is called from within the constructor to
	     * initialize the form.
	     * WARNING: Do NOT modify this code. The content of this method is
	     * always regenerated by the Form Editor.
	     */
	    private void initComponents() {

	        jLabel1 = new javax.swing.JLabel();
	        jSeparator1 = new javax.swing.JSeparator();
	        jTextField1 = new javax.swing.JTextField();
	        jLabel2 = new javax.swing.JLabel();
	        jLabel4 = new javax.swing.JLabel();
	        jLabel5 = new javax.swing.JLabel();
	        jComboBox1 = new javax.swing.JComboBox();
	        jLabel3 = new javax.swing.JLabel();
	        jSeparator2 = new javax.swing.JSeparator();
	        jSeparator3 = new javax.swing.JSeparator();
	        jButton1 = new javax.swing.JButton();
	        jLabel6 = new javax.swing.JLabel();

	        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
	        setTitle("Hatred's AIO Stunner");
	        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

	        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
	        jLabel1.setText("Thanks for using Hatred's AIO Stunner!");

	        jLabel2.setText("NPC ID:");

	        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
	        jLabel4.setText("You can find the NPC ID above the NPC's");

	        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
	        jLabel5.setText("head, inside the blue rectangle.");

	        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Confuse", "Weaken", "Curse", "Vulnerability", "Enfeeble", "Stun" }));

	        jLabel3.setText("Spell:");

	        jButton1.setText("Start Script");
	        jButton1.addActionListener(new java.awt.event.ActionListener() {
	            public void actionPerformed(java.awt.event.ActionEvent evt) {
	                jButton1ActionPerformed(evt);
	            }
	        });

	        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
	        jLabel6.setText("Please donate if you like my scripts!");

	        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
	        getContentPane().setLayout(layout);
	        layout.setHorizontalGroup(
	            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
	            .addGroup(layout.createSequentialGroup()
	                .addContainerGap()
	                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 197, Short.MAX_VALUE)
	                .addContainerGap())
	            .addGroup(layout.createSequentialGroup()
	                .addGap(38, 38, 38)
	                .addComponent(jLabel2)
	                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
	                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
	                .addContainerGap(54, Short.MAX_VALUE))
	            .addComponent(jSeparator2, javax.swing.GroupLayout.DEFAULT_SIZE, 217, Short.MAX_VALUE)
	            .addGroup(layout.createSequentialGroup()
	                .addGap(39, 39, 39)
	                .addComponent(jLabel3)
	                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
	                .addComponent(jComboBox1, 0, 95, Short.MAX_VALUE)
	                .addGap(53, 53, 53))
	            .addComponent(jSeparator3, javax.swing.GroupLayout.DEFAULT_SIZE, 217, Short.MAX_VALUE)
	            .addGroup(layout.createSequentialGroup()
	                .addGap(60, 60, 60)
	                .addComponent(jButton1)
	                .addContainerGap(70, Short.MAX_VALUE))
	            .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 217, Short.MAX_VALUE)
	            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
	                .addGroup(layout.createSequentialGroup()
	                    .addContainerGap()
	                    .addComponent(jLabel4)
	                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
	            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
	                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
	                    .addContainerGap()
	                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, 197, Short.MAX_VALUE)
	                    .addContainerGap()))
	            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
	                .addGroup(layout.createSequentialGroup()
	                    .addContainerGap()
	                    .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, 197, Short.MAX_VALUE)
	                    .addContainerGap()))
	        );
	        layout.setVerticalGroup(
	            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
	            .addGroup(layout.createSequentialGroup()
	                .addContainerGap()
	                .addComponent(jLabel1)
	                .addGap(26, 26, 26)
	                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE)
	                .addGap(36, 36, 36)
	                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
	                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
	                    .addComponent(jLabel2))
	                .addGap(18, 18, 18)
	                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE)
	                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
	                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
	                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
	                    .addComponent(jLabel3))
	                .addGap(18, 18, 18)
	                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE)
	                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
	                .addComponent(jButton1)
	                .addContainerGap(24, Short.MAX_VALUE))
	            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
	                .addGroup(layout.createSequentialGroup()
	                    .addGap(65, 65, 65)
	                    .addComponent(jLabel4)
	                    .addContainerGap(182, Short.MAX_VALUE)))
	            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
	                .addGroup(layout.createSequentialGroup()
	                    .addGap(75, 75, 75)
	                    .addComponent(jLabel5)
	                    .addContainerGap(172, Short.MAX_VALUE)))
	            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
	                .addGroup(layout.createSequentialGroup()
	                    .addGap(21, 21, 21)
	                    .addComponent(jLabel6)
	                    .addContainerGap(226, Short.MAX_VALUE)))
	        );

	        pack();
	    }// </editor-fold>

	    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {
	        spell = jComboBox1.getSelectedIndex();
	        npcID = Integer.parseInt(jTextField1.getText());
	        scriptStarted = true;
	        dispose();
	    }

	    /**
	    * @param args the command line arguments
	    */
	    public void main(String args[]) {
	        try {
	            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
	        } catch (Exception e) {
	            
	        }
	        java.awt.EventQueue.invokeLater(new Runnable() {
	            public void run() {
	                new StunnerGUI().setVisible(true);
	            }
	        });
	    }

	    // Variables declaration - do not modify
	    private javax.swing.JButton jButton1;
	    private javax.swing.JComboBox jComboBox1;
	    private javax.swing.JLabel jLabel1;
	    private javax.swing.JLabel jLabel2;
	    private javax.swing.JLabel jLabel3;
	    private javax.swing.JLabel jLabel4;
	    private javax.swing.JLabel jLabel5;
	    private javax.swing.JLabel jLabel6;
	    private javax.swing.JSeparator jSeparator1;
	    private javax.swing.JSeparator jSeparator2;
	    private javax.swing.JSeparator jSeparator3;
	    private javax.swing.JTextField jTextField1;
	    // End of variables declaration

	}


}