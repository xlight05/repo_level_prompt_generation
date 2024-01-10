package fr.epsi.epsi5.client.pays;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Label;
import com.gwtext.client.core.EventObject;
import com.gwtext.client.core.Position;
import com.gwtext.client.dd.DD;
import com.gwtext.client.widgets.Button;
import com.gwtext.client.widgets.Panel;
import com.gwtext.client.widgets.Window;
import com.gwtext.client.widgets.event.ButtonListenerAdapter;
import com.gwtext.client.widgets.layout.FitLayout;

import fr.epsi.epsi5.client.CustomToastWindow;
import fr.epsi.epsi5.client.IHMRegistry;
import fr.epsi.epsi5.client.services.AsyncServiceRegistry;
import fr.epsi.epsi5.client.services.DTOException;


/**
 * Panel gérant la suppression des pays.
 * @author Wilhelm Peraud-Loïc Simon
 */
public class PaysSuppressionPanel extends Panel{
	private Window fenetreSupprimer;
	
	/**
	 * Constructeur de la classe PaysSuppressionPanel.
	 */
	public PaysSuppressionPanel(){
		super();
		this.setId("pays_suppr");
		gererPanelSuppression();
	}
	
	/**
	 * Méthode pour gérer le panel de suppression d'un pays.
	 */
	private void gererPanelSuppression(){
		
		this.setTitle("Suppression d'un pays");  
		this.setBorder(true);  
		this.setWidth(200);
		this.setHeight(200);
		this.setCollapsible(true); 
		this.setButtonAlign(Position.CENTER);
		
		//Ajout du bouton de suppression du Pays
		Button button = new Button("Supprimer ce pays", new ButtonListenerAdapter() {  
		    public void onClick(Button button, EventObject e) {
		    	creerFenetreSuppression(); 
		    	fenetreSupprimer.show();
		    }  
		});
		
		this.add(button);
		
		new DD(this);
	}

	/**
	 * Méthode pour gérer la fenetre qui permet l'ajout d'une ville.
	 */
	private void creerFenetreSuppression(){
		 
		fenetreSupprimer = new Window();
		fenetreSupprimer.setModal(true);
		fenetreSupprimer.setTitle("Supprimer un pays");  
		fenetreSupprimer.setWidth(600);  
		fenetreSupprimer.setHeight(150);  
		fenetreSupprimer.setMinWidth(600);  
		fenetreSupprimer.setMinHeight(250);  
		fenetreSupprimer.setLayout(new FitLayout());  
		fenetreSupprimer.setPaddings(5);  
		fenetreSupprimer.setButtonAlign(Position.CENTER);  
		fenetreSupprimer.setCloseAction(Window.CLOSE);  
		fenetreSupprimer.setPlain(true);
		
		//Ajout des boutons et des listeners
		//Bouton de validation de la suppression
		final Button bValider = new Button("Valider", new ButtonListenerAdapter() {  
		    public void onClick(Button button, EventObject e) {
		    	//Appel à la méthode effectuant la suppression du Pays
		    	supprimerPays(IHMRegistry.getPaysPanel().getIdPays());
		    	fenetreSupprimer.close();
		    }  
		}); 
		
		//Bouton d'annulation de la suppression
		final Button bAnnuler = new Button("Annuler", new ButtonListenerAdapter() {  
		    public void onClick(Button button, EventObject e) {  
		        fenetreSupprimer.close();	
		    }  
		}); 
				
		fenetreSupprimer.addButton(bValider);  
		fenetreSupprimer.addButton(bAnnuler);
		
		Label label = new Label();
		label.setWidth("100%");
		label.setHeight("100%");
		label.setText("Etes vous sûr de vouloir supprimer le pays nommé " + 
				IHMRegistry.getPaysPanel().getNomPays() + " ?");
		
		fenetreSupprimer.add(label);
	}
	
	/**
	 * Méthode appellant le service RPC pour effectuer la
	 * suppression du pays.
	 * @param idPays Id du pays à supprimer
	 */
	private void supprimerPays(final int idPays) {
		AsyncServiceRegistry.getServicePaysAsync().deletePays(idPays, new AsyncCallback<DTOException>()
		{
            // Called if successful
			public void onSuccess(DTOException exception) {
				if(exception == null) {
					new CustomToastWindow("Votre pays a bien été supprimé.");
					IHMRegistry.getWestWidget().deletePays(idPays);
					IHMRegistry.getPaysPanel().setEnable(false);
					IHMRegistry.getVillePanel().setEnable(false,false);
				}else {
					new CustomToastWindow(exception.getMessage(),false);
				}
			}

			// Called if unsuccessful
			public void onFailure(Throwable caught) {
				new CustomToastWindow("Un problème a eu lieu lors de la suppression de votre pays.",false);
            }
        });
	}
}
