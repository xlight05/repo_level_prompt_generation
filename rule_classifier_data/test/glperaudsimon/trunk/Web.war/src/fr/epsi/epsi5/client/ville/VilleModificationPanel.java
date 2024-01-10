package fr.epsi.epsi5.client.ville;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.gwtext.client.core.EventObject;
import com.gwtext.client.core.Position;
import com.gwtext.client.dd.DD;
import com.gwtext.client.widgets.Button;
import com.gwtext.client.widgets.Panel;
import com.gwtext.client.widgets.Window;
import com.gwtext.client.widgets.event.ButtonListenerAdapter;
import com.gwtext.client.widgets.form.FormPanel;
import com.gwtext.client.widgets.form.TextField;
import com.gwtext.client.widgets.layout.AnchorLayoutData;
import com.gwtext.client.widgets.layout.FitLayout;

import fr.epsi.epsi5.client.CustomToastWindow;
import fr.epsi.epsi5.client.IHMRegistry;
import fr.epsi.epsi5.client.services.AsyncServiceRegistry;
import fr.epsi.epsi5.client.services.DTOException;
import fr.epsi.epsi5.dto.VilleDTO;


/**
 * VilleModificationPanel.
 * @author Wilhelm Peraud-Loïc Simon
 */
public class VilleModificationPanel extends Panel{
	private Window fenetreModifier;
	private TextField textModif = null;
	
	/**
	 * Constructeur de la classe VilleWidget.
	 */
	public VilleModificationPanel(){
		super();
		gererPanelModif();
		
	}
		
	/**
	 * Méthode pour gérer le panel de modification d'une ville.
	 */
	private void gererPanelModif(){
		
		this.setTitle("Modification d'une ville");  
		this.setBorder(true);  
		this.setWidth(200);
		this.setHeight(200);
		this.setCollapsible(true); 
		this.setButtonAlign(Position.CENTER);
		
		Button button = new Button("Modifier une ville", new ButtonListenerAdapter() {  
		    public void onClick(Button button, EventObject e) {
		    	creerFenetreModif(); 
		    	fenetreModifier.show();
		    }  
		});
		
		this.add(button);
		
		new DD(this);
	}

	/**
	 * Méthode pour gérer la fenetre qui permet la modification d'une ville.
	 */
	private void creerFenetreModif(){
		 
		fenetreModifier = new Window();
		fenetreModifier.setModal(true);
		fenetreModifier.setTitle("Modifier une ville");  
		fenetreModifier.setWidth(600);  
		fenetreModifier.setHeight(150);  
		fenetreModifier.setMinWidth(600);  
		fenetreModifier.setMinHeight(250);  
		fenetreModifier.setLayout(new FitLayout());  
		fenetreModifier.setPaddings(5);  
		fenetreModifier.setButtonAlign(Position.CENTER);  
		fenetreModifier.setCloseAction(Window.CLOSE);  
		fenetreModifier.setPlain(true);
		
		//Ajout des boutons et des listeners
		final Button bValider = new Button("Valider", new ButtonListenerAdapter() {  
		    public void onClick(Button button, EventObject e) {  
		    	if(!textModif.getText().equals("")) {
		    		modifierVille(textModif.getText(),IHMRegistry.getVillePanel().getIdVille(), 
		    		IHMRegistry.getPaysPanel().getIdPays());
		    		fenetreModifier.close();
		    	}
		    }  
		}); 
		
		final Button bAnnuler = new Button("Annuler", new ButtonListenerAdapter() {  
		    public void onClick(Button button, EventObject e) {  
		        fenetreModifier.close();	
		    }  
		}); 
		fenetreModifier.addButton(bValider);  
		fenetreModifier.addButton(bAnnuler);
		
		//Définition de la fenetre de modification 
		FormPanel formPanel = new FormPanel();  
		//strips all Ext styling for the component  
		formPanel.setBaseCls("x-plain");  
		formPanel.setLabelWidth(75);  
		  
		formPanel.setWidth(500);  
		formPanel.setHeight(140);  
		  
		// anchor width by percentage
		textModif = new TextField("Ville à modifier", "ville");
		textModif.setWidth(70);
		textModif.setValue(IHMRegistry.getVillePanel().getNomVille());
		formPanel.add(textModif, new AnchorLayoutData("100%"));  
		  
		fenetreModifier.add(formPanel); 
	}
	
	/**
	 * Méthode permettant de réaliser la modification d'une Ville.
	 * @param nomVille Nom de la ville à modifier
	 * @param idVille Id de la ville à modifier
	 * @param idPays Pays auquel est lié la ville
	 */
	private void modifierVille(final String nomVille, final int idVille, int idPays) {
		VilleDTO ville = new VilleDTO();
		ville.setNom(nomVille);
		ville.setId(idVille);
		ville.setIdPays(idPays);
		AsyncServiceRegistry.getServiceVilleAsync().updateVille(ville, new AsyncCallback<DTOException>()
		{
            // Called if successful
			public void onSuccess(DTOException exception) {
				if(exception == null) {
					new CustomToastWindow("Votre ville a bien été modifiée.");
					IHMRegistry.getVillePanel().setNomVille(nomVille);
					IHMRegistry.getWestWidget().updateVille(idVille, nomVille);
				}else {
					new CustomToastWindow(exception.getMessage(),false);
				}
			}

			// Called if unsuccessful
			public void onFailure(Throwable caught) {
				new CustomToastWindow("Un problème a eu lieu lors de la modification de votre ville.",false);
            }
        });
	}
}
