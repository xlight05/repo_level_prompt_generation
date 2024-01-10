package br.com.dyad.client.gxt.widget.customized;

import com.extjs.gxt.ui.client.Style.Scroll;
import com.extjs.gxt.ui.client.widget.Dialog;

public class DialogCustomized extends Dialog{	
	public DialogCustomized( String heading ){
		this.setHeading( heading );  
		//this.setBodyStyleName("pad-text");  
		this.setScrollMode(Scroll.AUTO);  
		this.setHideOnButtonClick(true);
		this.setIconStyle("startmenu-help");
	}
}
