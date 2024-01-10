package fr.epsi.epsi5.server.services;

import java.util.List;

import org.gwtwidgets.server.spring.GWTSpringController;
import org.springframework.beans.BeanUtils;

import fr.epsi.epsi5.client.services.DTOException;
import fr.epsi.epsi5.client.services.VilleServiceGWT;
import fr.epsi.epsi5.dto.VilleDTO;
import fr.epsi.epsi5.entities.Pays;
import fr.epsi.epsi5.entities.Ville;
import fr.epsi.epsi5.services.IPaysService;
import fr.epsi.epsi5.services.IVilleService;


/**
 * Implémentation du service RPC permettant l'accès aux Villes.
 * @author Wilhelm Peraud - Loïc Simon
 */
public class VilleServiceGWTImpl extends GWTSpringController implements
		VilleServiceGWT {
	
	/**
	 * Serial ID.
	 */
	private static final long serialVersionUID = 6324297280050549025L;
	/**
	 * Service d'accès aux données des Villes.
	 */
	private IVilleService villeService;
	
	/**
	 * Service d'accès aux données des Pays.
	 */
	private IPaysService paysService;
		
	/**
	 * Setter du Service d'accès aux Villes.
	 * @param villeService Service d'accès aux Villes
	 */
	public void setVilleService(IVilleService villeService) {
		this.villeService = villeService;
	}
	
	/**
	 * Setter du Service d'accès aux Pays.
	 * @param paysService Service d'accès aux Pays
	 */
	public void setPaysService(IPaysService paysService) {
		this.paysService = paysService;
	}

	/**
	 * Méthode getVilles renvoyant la liste des villes liées à un Pays.
	 * @param paysId Id du pays dont on cherche les villes
	 * @return Les villes du pays
	 */
	public VilleDTO[] getVilles(int paysId) {
		//On récupère la liste des villes depuis l'id du Pays
		List<Ville> villes = villeService.getVilles(paysId);
		//Si la liste est remplie, on va renvoyer les villes
		if((villes != null) && (villes.size() > 0)) {
			//On copie les valeurs des entités des villes dans des DTO
			VilleDTO[] villesDTO = new VilleDTO[villes.size()];
			for(int i=0;i<villes.size();i++) {
				VilleDTO villeDTO = new VilleDTO();
				Ville ville = villes.get(i);
				BeanUtils.copyProperties(villes.get(i), villeDTO);
				//Si la ville est liée à un Pays
				if(ville.getPays() != null) {
					villeDTO.setIdPays(ville.getPays().getId());
				}
				villesDTO[i] = villeDTO;
			}
			//Renvoi de la liste des villes
			return villesDTO;
		} else {
			return null;
		}
	}

	/**
	 * Méthode permettant de charger une ville.
	 * @param villeId Id de la ville à charger
	 * @return Ville chargée
	 */
	public VilleDTO loadVille(int villeId) {
		try {
			//Chargement de la ville depuis la base
			Ville ville = villeService.loadVille(villeId);
			//Si la ville existe, on l'envoie à la retourne
			if(ville != null) {
				VilleDTO maVille = new VilleDTO();
				BeanUtils.copyProperties(ville, maVille);
				maVille.setIdPays(ville.getPays().getId());
				return maVille;
			} else {
				return null;
			}
		}catch(Exception e) {
			return null;
		}
	}

	/**
	 * Méthode permettant d'ajouter une ville.
	 * @param ville Ville à ajouter
	 * @return Exception pouvant avoir eu lieu lors de l'ajout
	 */
	public DTOException addVille(VilleDTO ville) {
		Pays unPays = paysService.loadPays(ville.getIdPays()); 
		//Si le pays auquel est censé appartenir la ville
		//existe bien
		if(unPays != null) {
			try {
				//On vérifie que la ville n'existe pas déjà.
				if(!villeService.findDuplicateVille(ville.getNom(), ville.getId())) {
					Ville uneVille = new Ville();
					BeanUtils.copyProperties(ville, uneVille);
					uneVille.setPays(unPays);
					//On tente l'ajout de la ville
					villeService.addVille(uneVille);
					return null;
				} else {
					return new DTOException("Cette ville existe déjà.");
				}
			}catch (Exception e) {
				return new DTOException("Un problème a eu lieu lors de l'ajout de votre ville");
			}
		} else {
			return new DTOException("Le pays lié à cette ville n'existe pas/plus");
		}
	}
	
	/**
	 * Méthode permettant de mettre à jour une ville.
	 * @param ville Ville à mettre à jour
	 * @return Exception pouvant avoir lieu lors de la mise à jour
	 */
	public DTOException updateVille(VilleDTO ville) {
		Pays unPays = paysService.loadPays(ville.getIdPays()); 
		//Si le pays auquel est censé appartenir la ville
		//existe bien
		if(unPays != null) {
			try {
				//On vérifie que la ville n'existe pas déjà.
				if(!villeService.findDuplicateVille(ville.getNom(), ville.getId())) {
					Ville uneVille = new Ville();
					BeanUtils.copyProperties(ville, uneVille);
					uneVille.setPays(unPays);
					//On tente la mise à jour de la ville
					villeService.updateVille(uneVille);
					return null;
				} else {
					return new DTOException("Une ville portant ce nom existe déjà.");
				}
			}catch (Exception e) {
				return new DTOException("Un problème a eu lieu lors de la mise à jour de votre ville");
			}
		} else {
			return new DTOException("Le pays lié à cette ville n'existe pas/plus");
		}
	}
	
	/**
	 * Méthode permettant de supprimer une ville.
	 * @param idVille Id de la ville à supprimer
	 * @return Exception pouvant avoir lieu lors de la mise à jour
	 */
	public DTOException deleteVille(int idVille) {
		try {
			villeService.deleteVille(idVille);
			return null;
		} catch(Exception e) {
			return new DTOException("Une erreur a eu lieu lors de la suppression de votre ville : "+e.getMessage());
		}
	}
}
