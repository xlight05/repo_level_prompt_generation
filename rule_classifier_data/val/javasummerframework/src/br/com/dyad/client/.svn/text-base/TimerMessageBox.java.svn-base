package br.com.dyad.client;

import java.util.ArrayList;

import com.extjs.gxt.ui.client.widget.MessageBox;
import com.google.gwt.user.client.Timer;

public class TimerMessageBox extends MessageBox {
	private static ArrayList<MessageBox> dialogs = new ArrayList<MessageBox>();
	
	private static TimerMessageBox box;
	private static int DELAY_TIME = 2000;	
	private boolean forceClose = false;
	 	
	
	public static MessageBox wait(String title, String msg, String progressText) {
		
		//if (box == null) {			
			box = new TimerMessageBox();
			box.setType(MessageBoxType.WAIT);
			box.setButtons("");
			box.setClosable(false);
		//}
	    	    				
		box.setTitle(title);
		box.setMessage(msg);
		box.setProgressText(progressText);
		box.updateText(msg);
		
		//box.getDialog().layout();
		//box.getDialog().repaint();
		
		box.forceClose = false;		
	    Timer timer = new Timer(){

			@Override
			public void run() {
				if (!box.forceClose) {
					box.setModal(true);
					box.show();
				}
			}
	    	
	    };
	    
	    timer.schedule(DELAY_TIME);
	    
	    dialogs.add(box);
	    return box;
	  }
	
	@Override
	public void close() {
		forceClose = true;
		dialogs.remove(this);
		if (this.isVisible()) {			
			super.close();
		}
	}
	
}
