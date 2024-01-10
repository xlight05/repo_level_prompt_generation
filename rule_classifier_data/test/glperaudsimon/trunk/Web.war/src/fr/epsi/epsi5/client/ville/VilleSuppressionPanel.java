package fr.epsi.epsi5.client.ville;

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
 * VilleSuppressionPanel.
 * @author Wilhelm Peraud-Loïc Simon
 */
public class VilleSuppressionPanel extends Panel{
	private Window fenetreSupprimer;
	
	/**
	 * Constructeur de la classe VilleSuppressionPanel.
	 */
	public VilleSuppressionPanel(){
		super();
		gererPanelSuppression();
	}
		
	/**
	 * Méthode pour gérer le panel de suppression d'une ville.
	 */
	private void gererPanelSuppression(){
		
		this.setTitle("Suppression d'une ville");  
		this.setBorder(true);  
		this.setWidth(200);
		this.setHeight(200);
		this.setCollapsible(true); 
		this.setButtonAlign(Position.CENTER);
		
		Button button = new Button("Supprimer une ville", new ButtonListenerAdapter() {  
		    public void onClick(Button button, EventObject e) {
		    	creerFenetreSuppression(); 
		    	fenetreSupprimer.show();
		    }  
		});
		
		this.add(button);
		
		new DD(this);
	}

	/**
	 * Méthode pour gérer la fenetre qui permet la suppression d'une ville.
	 */
	private void creerFenetreSuppression(){
		 
		fenetreSupprimer = new Window();
		fenetreSupprimer.setModal(true);
		fenetreSupprimer.setTitle("Supprimer une ville");  
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
		//Bouton validant la suppression de la ville
		final Button bValider = new Button("Valider", new ButtonListenerAdapter() {  
		    public void onClick(Button button, EventObject e) {  
		    	//Appel à la méthode effectuant la suppression
		    	supprimerVille(IHMRegistry.getVillePanel().getIdVille());
		    	fenetreSupprimer.close();
		    }  
		}); 
		
		//Bouton annulant la suppression de la ville
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
		label.setText("Etes vous sûr de vouloir supprimer la ville nommée " + 
				IHMRegistry.getVillePanel().getNomVille() + " ?");
		
		fenetreSupprimer.add(label);
	}
	
	/**
	 * Méthode appellant le service RPC pour effectuer
	 * la suppression de la ville.
	 * @param idVille Id de la ville à supprimer
	 */
	private void supprimerVille(final int idVille) {
		AsyncServiceRegistry.getServiceVilleAsync().deleteVille(idVille, new AsyncCallback<DTOException>()
		{
            // Called if successful
			public void onSuccess(DTOException exception) {
				if(exception == null) {
					new CustomToastWindow("Votre ville a bien été supprimée.");
					IHMRegistry.getWestWidget().deleteVille(idVille);
					IHMRegistry.getVillePanel().setEnable(false);
				}else {
					new CustomToastWindow(exception.getMessage(),false);
				}
			}

			// Called if unsuccessful
			public void onFailure(Throwable caught) {
				new CustomToastWindow("Un problème a eu lieu lors de la suppression de votre ville.",false);
            }
        });
	}
}
