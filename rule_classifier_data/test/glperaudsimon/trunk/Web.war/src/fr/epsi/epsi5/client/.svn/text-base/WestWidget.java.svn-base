package fr.epsi.epsi5.client;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Composite;
import com.gwtext.client.core.EventObject;
import com.gwtext.client.data.Node;
import com.gwtext.client.widgets.HTMLPanel;
import com.gwtext.client.widgets.Panel;
import com.gwtext.client.widgets.tree.TreeNode;
import com.gwtext.client.widgets.tree.TreePanel;
import com.gwtext.client.widgets.tree.event.TreeNodeListenerAdapter;

import fr.epsi.epsi5.client.services.AsyncServiceRegistry;
import fr.epsi.epsi5.dto.PaysDTO;
import fr.epsi.epsi5.dto.VilleDTO;

/**
 * WestWidget.
 * @author Wilhelm Peraud-Loïc Simon
 */
public class WestWidget extends Composite {
	
	private AbsolutePanel westAbsolutPanel = new AbsolutePanel();
	private TreePanel treePanel = null;
	private String villePrefix = "ville_";
	private String paysPrefix = "pays_";
	
	/**
	 * Constructeur de la class WestWidget.
	 */
	public WestWidget() {		
		//NE SURTOUT PAS OUBLIER CETTE LIGNE
		initWidget(westAbsolutPanel);
		westAbsolutPanel.setWidth("100%");
		loadCountries();
	}
	
	/**
	 * Méthode appellant le service RPC d'accès aux pays.
	 */
	public void loadCountries() {  
		// Call Service
        AsyncServiceRegistry.getServicePaysAsync().getPays(new AsyncCallback<PaysDTO[]>()
        {
            // Called if successful
			public void onSuccess(PaysDTO[] pays) {
				chargerPays(pays);
            }

            // Called if unsuccessful
            public void onFailure(Throwable caught) {
            	Panel listePaysVide = new HTMLPanel("<p>Il n'y a pas de pays dans la base.</p>");
    			westAbsolutPanel.add(listePaysVide);
            }
        });
    }

	/**
	 * Méthode chargeant les villes appartenant à un pays.
	 * @param idPays Id du pays dont les villes doivent être affichées.
	 */
	public void loadCities(int idPays) {
		Node node = treePanel.getNodeById(paysPrefix+idPays);
		if(node != null) {
			chargerVilles(node,true);
		}
	}
	
	/**
	 * Méthode permettant d'initialiser le TreePanel des pays et villes.
	 */
	private void initPaysTreePanel() {
		treePanel = new TreePanel(); 
		
		treePanel.setWidth("100%");  
		treePanel.setHeight(400);
		treePanel.setTitle("Liste des pays");  
		treePanel.setAnimate(true);  
		treePanel.setEnableDD(false);   
		treePanel.setRootVisible(true);
		treePanel.setRootNode(new TreeNode("Pays"));
		westAbsolutPanel.add(treePanel);
	}
	
	/**
	 * Méthode permettant de charger les Pays.
	 * @param pays Tableau de Pays
	 */
	private void chargerPays(PaysDTO[] pays) {
		if (pays.length > 0){
			if(treePanel == null) {
				initPaysTreePanel();
			}
			
			//On récupère la racine du TreePanel
			TreeNode root = treePanel.getRootNode();
			
			//On enlève les enfants de la node du Pays
			Node[] nodes = root.getChildNodes();
			for (Node node : nodes) {
				node.remove();
			}
			
			//On rajoute les nouveaux éléments
			for (final PaysDTO paysDTO : pays) {
				TreeNode paysNode = new TreeNode(paysDTO.getNom());
				paysNode.setId(paysPrefix+Integer.toString(paysDTO.getId()));
				paysNode.addListener(new TreeNodeListenerAdapter() {
					@Override
					public void onClick(Node node, EventObject e){
						onPaysClick(node,paysDTO);
					}
				});
				
				root.appendChild(paysNode);
			}
						
			//On affiche les enfants de la racine
			root.expand();
		} else {
			Panel listePaysVide = new HTMLPanel("<p>Il n'y a pas de pays dans la base.</p>");
			westAbsolutPanel.add(listePaysVide);
		}
	}
	
	/**
	 * Méthode permettant de charger les Villes.
	 * @param paysNode Node du Pays auquel il faut rajouter les villes
	 * @param expand Indique si les enfants de la node du Pays doivent être affichés.
	 */
	private void chargerVilles(Node paysNode, boolean expand){
		final TreeNode tNode = (TreeNode)paysNode;
		
		//On enlève les enfants de la node du Pays
		Node[] nodes = paysNode.getChildNodes();
		for (Node node : nodes) {
			node.remove();
		}
				
		//On appelle le service asynchrone pour récupérer les villes.
		AsyncServiceRegistry.getServiceVilleAsync().getVilles(extractPaysIdFromNode(paysNode), new AsyncCallback<VilleDTO[]>()
		{
			//Called if successful
			public void onSuccess(VilleDTO[] villes) {
				if((villes != null) && (villes.length > 0)) {
					//Pour toutes les villes récupérées, on va ajouter une node fille
					for (final VilleDTO villeDTO : villes) {
						final TreeNode villeNode = new TreeNode(villeDTO.getNom());
						villeNode.setId(villePrefix+Integer.toString(villeDTO.getId()));
						villeNode.addListener(new TreeNodeListenerAdapter() {
							//Evenement onClick pour chaque Node
							@Override
							public void onClick(Node node, EventObject e){
								onVilleClick(node, villeDTO);
							}
						});
							
						tNode.appendChild(villeNode);
					}
					tNode.expand();
				}
			}
			
			// Called if unsuccessful
			public void onFailure(Throwable caught) {
				new CustomToastWindow("Une erreur a eu lieu lors du chargement des villes.", false);	
			}
		});
	}
	
	/**
	 * Show the MapUpdateToastWindow if necessary.
	 */
	private void showToastWindow() {
		//On teste si on est sur un autre panel
		//que celui de la Map Google.
		//Si c'est le cas, on affiche une ToastWindow
		if(IHMRegistry.getCenterPanel().getActiveTab().getId() != IHMRegistry.getMapPanel().getId()) {
			new CustomToastWindow("Carte mise à jour.", true);
		}
	}
	
	/**
	 * Méthode extrayant l'idée d'un pays à partir de la node.
	 * @param node Node du pays cliqué
	 * @return Id du pays
	 */
	private int extractPaysIdFromNode(Node node) {
		try {
			return Integer.parseInt(node.getId().replace(paysPrefix, ""));
		} catch(Exception e) {
			return 0;
		}
	}
		
	/**
	 * Méthode mettant à jour le nom d'un Pays dans la node lui correspondant.
	 * @param idPays Id du pays à mettre à jour
	 * @param nouveauNom Nouveau nom du Pays
	 */
	public void updatePays(int idPays, String nouveauNom) {
		TreeNode node = treePanel.getNodeById(paysPrefix+idPays);
		if(node != null) {
			node.setText(nouveauNom);
		}
	}
	
	/**
	 * Méthode mettant à jour le nom d'une Ville dans la node lui correspondant.
	 * @param idVille Id de la ville à mettre à jour
	 * @param nouveauNom Nouveau nom de la ville
	 */
	public void updateVille(int idVille, String nouveauNom) {
		TreeNode node = treePanel.getNodeById(villePrefix+idVille);
		if(node != null) {
			node.setText(nouveauNom);
		}
	}
	
	/**
	 * Méthode supprimant la node correspondant à un pays.
	 * @param idPays Id du Pays à supprimer
	 */
	public void deletePays(int idPays) {
		Node node = treePanel.getNodeById(paysPrefix+idPays);
		if(node != null) {
			node.remove();
		}
	}
	
	/**
	 * Méthode supprimant la node correspondant à une ville.
	 * @param idVille Id de la Ville à supprimer
	 */
	public void deleteVille(int idVille) {
		Node node = treePanel.getNodeById(villePrefix+idVille);
		if(node != null) {
			node.remove();
		}
	}
	
	/**
	 * Méthode s'exécutant lors du click sur un Pays dans le TreePanel.
	 * @param node Node cliquée
	 * @param paysDTO Pays choisi par l'utilisateur
	 */
	private void onPaysClick(Node node, PaysDTO paysDTO) {
		IHMRegistry.getMapPanel().majCarte(paysDTO.getNom(), false);
		showToastWindow();
		IHMRegistry.getPaysPanel().setPays(paysDTO);
		IHMRegistry.getPaysPanel().setEnable(true);
		IHMRegistry.getVillePanel().setEnable(false,true);
		chargerVilles(node,false);
	}
	
	/**
	 * Méthode s'exécutant lors du click sur une Ville du TreePanel.
	 * @param node Node cliquée
	 * @param villeDTO Ville choisie par l'utilisateur
	 */
	private void onVilleClick(Node node, VilleDTO villeDTO) {
		IHMRegistry.getMapPanel().majCarte(villeDTO.getNom(), true);
		IHMRegistry.getVillePanel().setVille(villeDTO);
		showToastWindow();
		IHMRegistry.getVillePanel().setEnable(true);
	}
}
