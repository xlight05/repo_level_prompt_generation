package fr.epsi.epsi5.client.pays;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;

import fr.epsi.epsi5.dto.PaysDTO;


/**
 * PaysWidget.
 * @author Wilhelm Peraud-Loïc Simon
 */
public class PaysWidget extends Composite{
	private HorizontalPanel paysPanel;
	private PaysAjoutPanel paysAjoutPanel = null;
	private PaysModificationPanel paysModifPanel = null;
	private PaysSuppressionPanel paysSupprPanel = null;
	
	private PaysDTO pays = null;
		
	/**
	 * Constructeur de la classe PaysWidget.
	 */
	public PaysWidget(){
		paysPanel = new HorizontalPanel();
		paysPanel.setSpacing(15);
		//NE SURTOUT PAS OUBLIER CETTE LIGNE
		initWidget(paysPanel);
		
		//Initialisation des sous panels
		paysAjoutPanel = new PaysAjoutPanel();
		paysModifPanel = new PaysModificationPanel();
		paysSupprPanel = new PaysSuppressionPanel();
		
		//Ajout des sous panels
		paysPanel.add(paysAjoutPanel);
		paysPanel.add(paysModifPanel);	
		paysPanel.add(paysSupprPanel);
		
		this.setEnable(false);
	}
	
	/**
	 * Setter de l'attribut Pays représentant le pays choisit par l'utilisateur.
	 * @param pays Pays choisit par l'utilisateur
	 */
	public void setPays(PaysDTO pays) {
		this.pays = pays;
	}
	
	/**
	 * Getter de l'attribut Id du pays.
	 * @return Id du pays
	 */
	public int getIdPays() {
		if(pays != null) {
			return pays.getId();
		} else {
			return 0;
		}	
	}
	
	/**
	 * Getter de l'attribut Nom du pays.
	 * @return Nom du pays
	 */
	public String getNomPays() {
		if(pays != null) {
			return pays.getNom();
		} else {
			return null;
		}
	}
	
	/**
	 * Setter du Nom du Pays.
	 * @param nomPays Nom du pays
	 */
	public void setNomPays(String nomPays) {
		pays.setNom(nomPays);
	}
		
	/**
	 * Méthode permettant d'activer ou non ce panel.
	 * @param isEnabled Si vrai, alors le panel est activé. Sinon, il est désactivé.
	 */
	public void setEnable(boolean isEnabled) {
		if(isEnabled) {
			paysModifPanel.enable();
			paysSupprPanel.enable();
		} else {
			paysModifPanel.disable();
			paysSupprPanel.disable();
		}
	}
}

