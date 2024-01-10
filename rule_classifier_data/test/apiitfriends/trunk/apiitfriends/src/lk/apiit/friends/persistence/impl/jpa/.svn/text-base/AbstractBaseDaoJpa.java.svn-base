package lk.apiit.friends.persistence.impl.jpa;

import lk.apiit.friends.persistence.BaseDao;

import org.springframework.dao.DataAccessException;
import org.springframework.orm.jpa.support.JpaDaoSupport;

/**
 * Base Data Access Object for all DAOs.
 * 
 * @author Yohan Liyanage
 * @version 12-Sep-2008
 * @since 12-Sep-2008
 * 
 * @param <T> Type of model object handled by the DAO
 */
public abstract class AbstractBaseDaoJpa<T> extends JpaDaoSupport implements BaseDao<T> {

	/**
	 * Class of the Model Object handled by the DAO.
	 */
	protected Class<T> clazz;
	
	/**
	 * Constructs an Abstract DAO which manages the objects
	 * of the given type by Class reference.
	 * 
	 * @param clazz type of objects
	 */
	public AbstractBaseDaoJpa(Class<T> clazz) {
		super();
		this.clazz = clazz;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public T findById(Long id) throws DataAccessException  {
		return getJpaTemplate().find(clazz, id);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void persist(T obj) throws DataAccessException  {
		getJpaTemplate().persist(obj);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void remove(T obj) throws DataAccessException  {
		T instance = getJpaTemplate().merge(obj);
		getJpaTemplate().remove(instance);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void update(T obj) throws DataAccessException  {
		getJpaTemplate().merge(obj);
	}

}
