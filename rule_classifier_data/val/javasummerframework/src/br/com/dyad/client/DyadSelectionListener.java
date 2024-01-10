package br.com.dyad.client;

import com.extjs.gxt.ui.client.event.MenuEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;

public abstract class DyadSelectionListener extends SelectionListener <MenuEvent>{
	final String PROCESS = "PROCESS";
	final String PROCESSID = "PROCESSID";
	final String TITLE = "TITLE";
	final String TOOLTIP = "TOOLTIP";
	final String ICONSTYLE = "ICONSTYLE";
	
	public DyadSelectionListener(){
		super();
	}
}