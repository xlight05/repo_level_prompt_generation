package fr.epsi.epsi5.client;

import com.google.gwt.core.client.EntryPoint;
import com.gwtext.client.core.Margins;
import com.gwtext.client.core.RegionPosition;
import com.gwtext.client.widgets.HTMLPanel;
import com.gwtext.client.widgets.Panel;
import com.gwtext.client.widgets.Viewport;
import com.gwtext.client.widgets.layout.BorderLayout;
import com.gwtext.client.widgets.layout.BorderLayoutData;
import com.gwtext.client.widgets.layout.FitLayout;
import com.gwtextux.client.widgets.image.Image;

/**
 * Web.
 * @author Wilhelm Peraud-Loïc Simon
 */
public class Web implements EntryPoint { 

	/**
	 * Methode définissant le point d'entrée de la page.
	 */
    public void onModuleLoad() {   
        Panel panel = new Panel();
        panel.setBorder(false);   
        panel.setPaddings(15);   
        panel.setLayout(new FitLayout());   
  
        Panel borderPanel = new Panel(); 
        borderPanel.setLayout(new BorderLayout()); 
  
        //Panel du Haut : NORTH   
        Panel northPanel = new HTMLPanel();
        Image logo = new Image("logo", "fr.epsi.epsi5.Web/img/logo.png");
        northPanel.add(logo); 
        northPanel.setHeight(140);
        borderPanel.add(northPanel, new BorderLayoutData(RegionPosition.NORTH));   
  
        //Panel du bas : SOUTH   
        Panel southPanel = new HTMLPanel("<p>Projet d'Architecture Logicielle 2009</p>" +
        		"<br />" +
        		"<p>Produit par Simon Loïc et Peraud Wilhelm.</p>");   
        southPanel.setHeight(100);   
        southPanel.setCollapsible(true);   
        southPanel.setTitle("Informations sur le projet");   
  
        BorderLayoutData southData = new BorderLayoutData(RegionPosition.SOUTH);   
        southData.setMinSize(100);   
        southData.setMaxSize(200);   
        southData.setMargins(new Margins(0, 5, 0, 0));   
        southData.setSplit(true);   
        borderPanel.add(southPanel, southData); 
        
        //Panel du centre : CENTER
        CenterWidget centerWidget = new CenterWidget();
        IHMRegistry.setCenterPanel(centerWidget);
        centerWidget.setWidth("100%");
        centerWidget.setHeight("100%");
        borderPanel.add(centerWidget, new BorderLayoutData(RegionPosition.CENTER));
  
        //Panel de gauche : WEST
                   
        Panel westPanel = new Panel();  
        westPanel.setTitle("Destinations");
        westPanel.setCollapsible(true);  
        westPanel.setWidth(200);    
        
        final WestWidget westWidget  = new WestWidget();
        IHMRegistry.setWestWidget(westWidget);
        westPanel.add(westWidget);
        BorderLayoutData westData = new BorderLayoutData(RegionPosition.WEST);  
        westData.setSplit(true);  
        westData.setMinSize(175);  
        westData.setMaxSize(600);  
        westData.setMargins(new Margins(0, 5, 0, 0));  
        
        borderPanel.add(westPanel, westData);      
  
        panel.add(borderPanel);   
  
        new Viewport(panel);   
    }   
    
      
}