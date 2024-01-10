package fr.epsi.epsi5.client.ville;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;

import fr.epsi.epsi5.dto.VilleDTO;

/**
 * VilleWidget.
 * @author Wilhelm Peraud-Loïc Simon
 */
public class VilleWidget extends Composite{
	private HorizontalPanel villePanel;
	private VilleAjoutPanel villeAjoutPanel = null;
	private VilleModificationPanel villeModifPanel = null;
	private VilleSuppressionPanel villeSupprPanel = null;
	
	private VilleDTO ville;
	
	/**
	 * Constructeur de la classe VilleWidget.
	 */
	public VilleWidget(){
		villePanel = new HorizontalPanel();
		villePanel.setSpacing(15);
		//NE SURTOUT PAS OUBLIER CETTE LIGNE
		initWidget(villePanel);
		
		//Initialisation des sous panels
		villeAjoutPanel = new VilleAjoutPanel();
		villeModifPanel = new VilleModificationPanel();
		villeSupprPanel = new VilleSuppressionPanel();
		
		//Ajout des sous panels
		villePanel.add(villeAjoutPanel);
		villePanel.add(villeModifPanel);	
		villePanel.add(villeSupprPanel);
		
		this.setEnable(false,false);
	}

	/**
	 * Setter de la ville sélectionnée par l'utilisateur.
	 * @param ville Ville sélectionnée par l'utilisateur
	 */
	public void setVille(VilleDTO ville) {
		this.ville = ville;
	}

	/**
	 * Getter de l'Id de la Ville.
	 * @return Id de la ville
	 */
	public int getIdVille() {
		if(ville != null) {
			return ville.getId();
		} else {
			return 0;
		}
	}
	
	/**
	 * Getter du Nom de la Ville.
	 * @return Nom de la ville
	 */
	public String getNomVille() {
		if(ville != null) {
			return ville.getNom();
		} else {
			return null;
		}
	}
	
	/**
	 * Setter du Nom de la Ville.
	 * @param nomVille Nom de la ville
	 */
	public void setNomVille(String nomVille) {
		ville.setNom(nomVille);
	}
	
	/**
	 * Méthode permettant d'activer ou non ce panel.
	 * @param isEnabled Si vrai, alors le panel est activé. Sinon, il est désactivé.
	 */
	public void setEnable(boolean isEnabled) {
		if(isEnabled) {
			villeModifPanel.enable();
			villeSupprPanel.enable();
		} else {
			villeModifPanel.disable();
			villeSupprPanel.disable();
		}
	}
	
	/**
	 * Méthode permettant d'activer ou non ce panel.
	 * @param isEnabled Si vrai, alors le panel est activé. Sinon, il est désactivé.
	 * @param addIsEnabled Indique si l'ajout doit être activé ou non
	 */
	public void setEnable(boolean isEnabled, boolean addIsEnabled) {
		setEnable(isEnabled);
		if(addIsEnabled) {
			villeAjoutPanel.enable();
		} else {
			villeAjoutPanel.disable();
		}
	}
}
