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
 * VilleWidget.
 * @author Wilhelm Peraud-Loïc Simon
 */
public class VilleAjoutPanel extends Panel{
	private Window fenetreAjouter;
	private TextField textAjout;
	
	/**
	 * Constructeur de la classe VilleWidget.
	 */
	public VilleAjoutPanel(){
		super();	
		gererPanelAjout();
	}
	
	/**
	 * Méthode pour gérer le panel d'ajout d'une ville.
	 */
	private void gererPanelAjout(){
		
		this.setTitle("Ajout d'une ville");  
		this.setBorder(true);  
		this.setWidth(200);
		this.setHeight(200);
		this.setCollapsible(true); 
		this.setButtonAlign(Position.CENTER);

		Button button = new Button("Ajouter une ville", new ButtonListenerAdapter() {  
		    public void onClick(Button button, EventObject e) {
		    	creerFenetreAjout(); 
		    	fenetreAjouter.show();
		    }  
		});
		
		this.add(button);
		
		new DD(this);
	}

	/**
	 * Méthode pour gérer la fenetre qui permet l'ajout d'une ville.
	 */
	private void creerFenetreAjout(){
		 
		fenetreAjouter = new Window();
		fenetreAjouter.setModal(true);
		fenetreAjouter.setTitle("Ajouter une ville");  
		fenetreAjouter.setWidth(600);  
		fenetreAjouter.setHeight(150);  
		fenetreAjouter.setMinWidth(600);  
		fenetreAjouter.setMinHeight(250);  
		fenetreAjouter.setLayout(new FitLayout());  
		fenetreAjouter.setPaddings(5);  
		fenetreAjouter.setButtonAlign(Position.CENTER);  
		fenetreAjouter.setCloseAction(Window.CLOSE);  
		fenetreAjouter.setPlain(true);
		
		//Ajout des boutons et des listeners
		final Button bValider = new Button("Valider", new ButtonListenerAdapter() {  
		    public void onClick(Button button, EventObject e) {  
		        //Ajout de la ville
		    	if(!textAjout.getText().equals("")) {
		    		ajoutVille(textAjout.getText());
		    		fenetreAjouter.close();
		    	}
		    }  
		}); 
		
		final Button bAnnuler = new Button("Annuler", new ButtonListenerAdapter() {  
		    public void onClick(Button button, EventObject e) {  
		        fenetreAjouter.close();	
		    }  
		}); 
		fenetreAjouter.addButton(bValider);  
		fenetreAjouter.addButton(bAnnuler);
		
		//Définition de la fenetre d'ajout  
		FormPanel formPanel = new FormPanel();  
		//strips all Ext styling for the component  
		formPanel.setBaseCls("x-plain");  
		formPanel.setLabelWidth(75);   
		  
		formPanel.setWidth(500);  
		formPanel.setHeight(140);  
		  
		// anchor width by percentage
		textAjout = new TextField("Ville à ajouter", "ville");
		textAjout.setWidth(70);
		formPanel.add(textAjout, new AnchorLayoutData("100%"));  
		  
		fenetreAjouter.add(formPanel); 
	}	
	
	/**
	 * Méthode permettant de réaliser l'ajout d'une Ville.
	 * @param nomVille Nom de la ville à rajouter
	 */
	private void ajoutVille(String nomVille) {
		VilleDTO ville = new VilleDTO();
		ville.setNom(nomVille);
		ville.setIdPays(IHMRegistry.getPaysPanel().getIdPays());
		AsyncServiceRegistry.getServiceVilleAsync().addVille(ville, new AsyncCallback<DTOException>()
		{
            // Called if successful
			public void onSuccess(DTOException exception) {
				if(exception == null) {
					new CustomToastWindow("Votre ville a bien été ajoutée.");
					IHMRegistry.getWestWidget().loadCities(IHMRegistry.getPaysPanel().getIdPays());
				}else {
					new CustomToastWindow(exception.getMessage(),false);
				}
			}

			// Called if unsuccessful
			public void onFailure(Throwable caught) {
				new CustomToastWindow("Un problème a eu lieu lors de l'ajout de votre ville.",false);
            }
        });
	}
}
