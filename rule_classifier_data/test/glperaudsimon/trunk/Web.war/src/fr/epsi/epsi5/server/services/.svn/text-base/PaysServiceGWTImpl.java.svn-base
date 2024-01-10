package fr.epsi.epsi5.server.services;

import java.util.List;

import org.gwtwidgets.server.spring.GWTSpringController;
import org.springframework.beans.BeanUtils;

import fr.epsi.epsi5.client.services.DTOException;
import fr.epsi.epsi5.client.services.PaysServiceGWT;
import fr.epsi.epsi5.dto.PaysDTO;
import fr.epsi.epsi5.entities.Pays;
import fr.epsi.epsi5.services.IPaysService;

/**
 * Implémentation du service RPC permettant l'accès aux Pays.
 * @author Wilhelm Peraud - Loïc Simon
 */
public class PaysServiceGWTImpl extends GWTSpringController implements
		PaysServiceGWT {
	
	private IPaysService paysService;
	
	private static final long serialVersionUID = -1792067294329596081L;
	
	/**
	 * Setter du Service d'accès aux Pays.
	 * @param paysService Service d'accès aux Pays
	 */
	public void setPaysService(IPaysService paysService) {
		this.paysService = paysService;
	}
	
	/**
	 * Méthode permettant de retourner les Pays disponibles dans la base.
	 * @return Tableau de Pays
	 */
	public PaysDTO[] getPays() {
		List<Pays> pays = paysService.getPays();
		
		PaysDTO[] result = new PaysDTO[pays.size()];
		try {
			for (int i=0;i<pays.size();i++) {
				Pays unPays = pays.get(i);
				PaysDTO paysDTO = new PaysDTO();
				BeanUtils.copyProperties(unPays, paysDTO);
				result[i] = paysDTO;
			}
		}catch(Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
				
		return result;
	}

	/**
	 * Méthode permettant d'ajouter un Pays.
	 * @param pays Pays à ajouter
	 * @return Exception pouvant avoir lieu lors de l'ajout
	 */
	public DTOException addPays(PaysDTO pays) {
		try {
			//On vérifie que le pays n'existe pas déjà.
			if(!paysService.findDuplicatePays(pays.getNom(), pays.getId())) {
				Pays unPays = new Pays();
				BeanUtils.copyProperties(pays, unPays);
				//On tente l'ajout du pays
				paysService.addPays(unPays);
				return null;
			} else {
				return new DTOException("Ce pays existe déjà.");
			}
		}catch (Exception e) {
			return new DTOException("Un problème a eu lieu lors de l'ajout de votre pays");
		}
	}

	/**
	 * Méthode permettant de mettre à jour un pays.
	 * @param pays Pays à mettre à jour
	 * @return Exception pouvant avoir lieu lors de la mise à jour
	 */
	public DTOException updatePays(PaysDTO pays) {
		try {
			//On vérifie que le pays n'existe pas déjà.
			if(!paysService.findDuplicatePays(pays.getNom(), pays.getId())) {
				Pays unPays = new Pays();
				BeanUtils.copyProperties(pays, unPays);
				//On tente l'ajout de la ville
				paysService.updatePays(unPays);
				return null;
			} else {
				return new DTOException("Un pays portant ce nom existe déjà.");
			}
		}catch (Exception e) {
			return new DTOException("Un problème a eu lieu lors de la mise à jour de votre pays");
		}
	}
	
	/**
	 * Méthode permettant de supprimer un pays.
	 * @param idPays Id du pays à supprimer
	 * @return Exception pouvant avoir lieu lors de la suppression
	 */
	public DTOException deletePays(int idPays) {
		try {
			paysService.deletePays(idPays);
			return null;
		} catch(Exception e) {
			return new DTOException("Une erreur a eu lieu lors de la suppression de votre pays"+e.getMessage());
		}
	}
	
	/**
	 * Méthode permettant de charger un pays.
	 * @param paysId Id du pays à charger
	 * @return Pays chargé
	 */
	public PaysDTO loadPays(int paysId) {
		try {
			//Chargement du pays depuis la base
			Pays pays = paysService.loadPays(paysId);
			//Si le pays existe, on l'envoie à la retourne
			if(pays != null) {
				PaysDTO monPays = new PaysDTO();
				BeanUtils.copyProperties(pays, monPays);
				return monPays;
			} else {
				return null;
			}
		}catch(Exception e) {
			return null;
		}
	}
}
