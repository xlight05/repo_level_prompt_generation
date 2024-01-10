package fr.epsi.epsi5.client;

import com.gwtext.client.widgets.Panel;
import com.gwtext.client.widgets.TabPanel;

import fr.epsi.epsi5.client.pays.PaysWidget;
import fr.epsi.epsi5.client.ville.VilleWidget;

/**
 * CenterWidget.
 * @author Wilhelm Peraud-Loïc Simon
 */
public class CenterWidget extends TabPanel {
		
	/**
	 * Construteur de la classe CenterWidget.
	 */
	public CenterWidget() {
				
		this.setDeferredRender(false);   
		this.setHeight("100%");
        
        //Onglet Pays
        PaysWidget paysWidget = new PaysWidget();
        paysWidget.setHeight("100%");
        IHMRegistry.setPaysPanel(paysWidget);
        Panel paysPanel = new Panel("Pays");
        paysPanel.add(paysWidget);
        this.add(paysPanel);
        
        //Onglet Ville
        VilleWidget villeWidget = new VilleWidget();
        villeWidget.setHeight("100%");
        IHMRegistry.setVillePanel(villeWidget);
        Panel villePanel = new Panel("Ville");
        villePanel.add(villeWidget);
        this.add(villePanel);

		//Onglet Google Map
        GoogleMapPanel mapPanel = new GoogleMapPanel();
		IHMRegistry.setMapPanel(mapPanel);
		this.add(mapPanel);
		
		this.setActiveTab(0);
	}        

}
