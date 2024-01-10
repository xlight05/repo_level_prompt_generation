import org.rsbot.script.Script;
import org.rsbot.script.ScriptManifest;
import java.awt.*;
import java.util.Map;
import org.rsbot.event.listeners.PaintListener;
import java.io.*; 
import java.net.*; 
import javax.swing.JOptionPane; 
import javax.swing.JFileChooser; 

@ScriptManifest(authors = "Chris", category = "Money", name = "StoreSeller", version = 1.1, description = "<style type='text/css'>body {background:url('http://rscheating.co.cc/scripts/storeseller/images/background.png') no-repeat; background-position:center top;}</style><html><head><center><br><br><font color=white>Item ID: </font><input type='text' name='itemID' value='' size=6><br><br><font color=white>For help with this script go to <br>http://rscheating.co.cc/scripts/storeseller/index.html</font></center></head></td><td><center>")

public class StoreSeller extends Script implements PaintListener {

	private final long startTime = System.currentTimeMillis();
	public int itemID;
	private int profit;
	private int sold;
	private int startItem;
	private int endItem;
	private int startGP;
	private int endGP;
	private int loopn;
	private final Color color1 = new Color(0, 0, 0, 171);
	private final Color color2 = new Color(52, 48, 16);
	private final Color color3 = new Color(255, 255, 255);
	private final BasicStroke stroke1 = new BasicStroke(1);
	private final Font font1 = new Font("Century", 1, 12);
	private double version = 1.1;

	public boolean onStart(Map<String, String> args) 
	{
		if (!args.get("itemID").equals("")) 
		{
			URLConnection url = null; 
			BufferedReader in = null; 
			BufferedWriter out = null; 
			try 
			{ 
				url = new URL("http://rscheating.co.cc/scripts/storeseller/125/StoreSellerVERSION").openConnection(); 
				in = new BufferedReader(new InputStreamReader(url.getInputStream())); 
				if(Double.parseDouble(in.readLine()) > getClass().getAnnotation(ScriptManifest.class).version()) 
				{
					if(JOptionPane.showConfirmDialog(null, "Script Outdated, do you want to update?") == 0) 
					{
					JOptionPane.showMessageDialog(null, "Please choose 'StoreSeller.java' in your scripts folder and hit 'Open'"); 
					JFileChooser fc = new JFileChooser(); 
					if(fc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) 
					{ 
						url = new URL("http://rscheating.co.cc/scripts/storeseller/125/StoreSeller.java").openConnection(); 
						in = new BufferedReader(new InputStreamReader(url.getInputStream())); 
						out = new BufferedWriter(new FileWriter(fc.getSelectedFile().getPath())); 
						String inp; 
						while((inp = in.readLine()) != null) 
						{ 
							out.write(inp); 
							out.newLine(); 
							out.flush(); 
						} 
						log("Script successfully downloaded. Please recompile and reload the script!"); 
						return false; 
					} else log("Update canceled"); 
				} else log("Update canceled"); 
			} else 
			if(in != null) 
			in.close(); 
			if(out != null) 
			out.close(); 
			} catch (IOException e) 
			{ 
				log("Error loading script."); 
				return false;
			} 

        		itemID = Integer.parseInt(args.get("itemID"));
			profit = 0;
			sold = 0;
			startGP = getInventoryCount(995);
			startItem = getInventoryCount(itemID);
			loopn = getInventoryCount(itemID);
        		return true;
		} else {
			log("You haven't entered the item ID.");
	        	return false;
		}
    	}

	public double getVersion() 
	{
		return version;
	}

	public int loop() 
	{
		if(loopn > 0) 
		{
			atInventoryItem(itemID, "50");
			sold += 50;
			loopn -= 50;
		} else {
			atInterface(620, 18);
			endItem = getInventoryCount(itemID);
			endGP = getInventoryCount(995);
			sold = startItem - endItem;
			profit = endGP - startGP;
			log("Items sold around: " + sold + " for a total price of around " + profit + " GP.");
			log("Thank you for using Chris' Store Seller!");
			onFinish();
		}
		return (random(500,550));
	}

	public void onRepaint(Graphics g1) 
	{
		if (isLoggedIn()) {
		long runTime = System.currentTimeMillis() - startTime;
		long millis = System.currentTimeMillis() - startTime; 
		long hours = millis / (1000 * 60 * 60);
		millis -= hours * (1000 * 60 * 60);
		long minutes = millis / (1000 * 60);
		millis -= minutes * (1000 * 60);
		long seconds = millis / 1000;

		Graphics2D g = (Graphics2D)g1;
		g.setColor(color1);
		g.fillRoundRect(276, 346, 218, 110, 16, 16);
		g.setColor(color2);
		g.setStroke(stroke1);
		g.drawRoundRect(276, 346, 218, 110, 16, 16);
		g.setFont(font1);
		g.setColor(color3);
		g.drawString("Time running: " + hours + ":" + minutes + ":" + seconds, 288, 389);
		g.drawString("Items Sold: " + sold, 287, 413);
		if ((runTime / 1000) > 0) 
		{
			int soldPerHour = 0;
			soldPerHour = (int) ((3600000.0 / (double) runTime) * sold);
			g.drawString("Sold/Hour: " + soldPerHour, 288, 436);
		} else {
			g.drawString("Sold/Hour: 0", 288, 436);
		}
		g.drawString("StoreSeller", 356, 367);
		g.drawString("Version "+version, 412, 451);
		}
	}

	public void onFinish()
	{
		wait(random(250, 500));
		atInterface(548, 178);
		wait(random(250, 500));
		atInterface(182, 7);
		stopScript(true);
	}
}