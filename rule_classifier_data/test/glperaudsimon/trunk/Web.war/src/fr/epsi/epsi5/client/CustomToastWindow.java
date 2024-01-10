package fr.epsi.epsi5.client;

import java.util.Date;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.gwtext.client.core.EventObject;
import com.gwtext.client.widgets.Button;
import com.gwtext.client.widgets.event.ButtonListenerAdapter;
import com.gwtextux.client.widgets.window.ToastWindow;

/**
 * Classe personnalisée.
 * @author Wilhelm Peraud-Loïc Simon
 *
 */
public class CustomToastWindow{
	
	/**
	 * Constructeur avec un seul paramètre.
	 * @param message Message à émettre
	 */
	public CustomToastWindow(String message) {
		showToastWindow(message,true,false);
	}
	
	/**
	 * Constructeur avec deux paramètres.
	 * @param message à emettre
	 * @param information defaut true, sinon message exclamation
	 */
	public CustomToastWindow(String message, boolean information){
		showToastWindow(message,information,false);
	}
	
	/**
	 * Constructeur avec deux paramètres.
	 * @param message à emettre
	 * @param information defaut true, sinon message exclamation
	 * @param carte precise si on veut ajouter un bouton pour aller à la carte
	 */
	public CustomToastWindow(String message, boolean information, boolean carte){
		showToastWindow(message,information,carte);
	}
	
	
	/**
	 * Méhode affichant la ToastWindow.
	 * @param message Message à afficher
	 * @param information Par défaut true, sinon message d'exclamation
	 * @param carte precise si on veut ajouter un bouton pour aller à la carte
	 */
	private void showToastWindow(String message, boolean information, boolean carte) {
		ToastWindow tw = new ToastWindow();
		//Formatage de la date à la française
		DateTimeFormat dateStandard = DateTimeFormat.getFormat("kk:mm dd/mm/yyyy");
		//Dans le cas où il s'agit d'une information
        if (information) {
        	tw.setTitle("Info : " + dateStandard.format(new Date()));
        	tw.setIconCls("information");
        //Dans le cas où il s'agit d'une erreur
        } else {
        	tw.setTitle("Erreur : " + dateStandard.format(new Date()));
        	tw.setIconCls("exclamation");
        }
        //On met le message
        tw.setMessage(message); 
        
        if (carte){
        	final Button bCarte = new Button("Aller à la carte", new ButtonListenerAdapter() {  
    		    public void onClick(Button button, EventObject e) {  
    		        //Ajout de la ville
    		    	IHMRegistry.getCenterPanel().activate("mapPanel");
    		    }  
    		});
            
            tw.add(bCarte);
        }
        
        //On affiche la ToastWindow
        tw.show();
	}

}
