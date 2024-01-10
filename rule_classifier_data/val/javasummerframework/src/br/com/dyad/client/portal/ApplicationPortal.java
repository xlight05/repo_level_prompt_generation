package br.com.dyad.client.portal;


import br.com.dyad.client.DyadInfrastructure;

import com.extjs.gxt.ui.client.Style.LayoutRegion;
import com.extjs.gxt.ui.client.Style.Scroll;
import com.extjs.gxt.ui.client.event.IconButtonEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.util.Margins;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.Html;
import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.extjs.gxt.ui.client.widget.TabPanel;
import com.extjs.gxt.ui.client.widget.Text;
import com.extjs.gxt.ui.client.widget.button.ToolButton;
import com.extjs.gxt.ui.client.widget.custom.Portal;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.extjs.gxt.ui.client.widget.layout.AccordionLayout;
import com.extjs.gxt.ui.client.widget.layout.BorderLayout;
import com.extjs.gxt.ui.client.widget.layout.BorderLayoutData;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.extjs.gxt.ui.client.widget.layout.TableData;
import com.google.gwt.user.client.Element;

public class ApplicationPortal extends LayoutContainer {
	private TabPanel panel;
	private ContentPanel nav;
	private ContentPanel west;
	//private ContentPanel shortcuts;
	private WindowTreeMenu treeMenu;

	public TabPanel getPanel() {
		return panel;
	}

	public void setPanel(TabPanel panel) {
		this.panel = panel;
	}

	@Override
	protected void onRender(Element parent, int index) {
		super.onRender(parent, index);
		setLayout(new BorderLayout());

		LayoutContainer north = new LayoutContainer();
		BorderLayoutData northData = new BorderLayoutData(LayoutRegion.NORTH, 30);
		//align=\"left\" 
		//align=\"right\"
		String header = "<table width=\"100%\"><tr><td>" +
			DyadInfrastructure.translate("Database") + ":" + DyadInfrastructure.database + "</td>" + 
			"<td><img align=\"right\" src=\"resources/logo.gif\"  />" +
			"</td></tr></table>";
		north.add(new Html(header));
		west = new ContentPanel();
		west.setBodyBorder(false);
		west.setHeading("Iniciar");
		west.setLayout(new FitLayout());

		nav = new ContentPanel();
		nav.setHeading("Ir Para");
		nav.setBorders(false);
		nav.setBodyStyle("fontSizPanel e: 12px; padding: 6px");
		nav.setScrollMode(Scroll.AUTO);
		nav.setHeaderVisible(false);
		west.add(nav);

		/*shortcuts = new ContentPanel();
		shortcuts.setHeading("Atalhos");
		shortcuts.setBorders(false);
		shortcuts.setBodyStyle("fontSizPanel e: 12px; padding: 6px");
		west.add(shortcuts);*/
		
		//Cria o menuPanel 
		//new WindowTreeMenu(nav, shortcuts);
		treeMenu = new WindowTreeMenu(nav, nav);

		BorderLayoutData westData = new BorderLayoutData(LayoutRegion.WEST,
				200, 100, 300);
		westData.setMargins(new Margins(5, 0, 5, 5));
		westData.setCollapsible(true);
		
		panel = new TabPanel();
	    panel.setResizeTabs(true);
	    panel.setAutoSelect(true);

		BorderLayoutData centerData = new BorderLayoutData(LayoutRegion.CENTER);
		centerData.setMargins(new Margins(5));

		add(north, northData);
		add(west, westData);
		add(panel, centerData);
	}
	
}
