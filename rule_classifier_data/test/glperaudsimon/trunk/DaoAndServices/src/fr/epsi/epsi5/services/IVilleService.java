package fr.epsi.epsi5.services;

import fr.epsi.epsi5.dao.IVilleDao;

/**
 * Interface du service d'accès aux Villes.
 * @author Wilhelm Peraud - Loïc Simon
 */
public interface IVilleService extends IVilleDao {
	/**
	 * Setter du Dao d'accès aux Villes.
	 * @param dao Dao d'accès aux Villes
	 */
	void setDao(IVilleDao dao);
}
