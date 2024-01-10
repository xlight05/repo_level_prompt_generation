package lk.apiit.friends.persistence;

import org.springframework.dao.DataAccessException;

/**
 * Data Access Exception for situations where the expected object
 * is not found
 * 
 * @author Yohan Liyanage
 * @version 12-Sep-2008
 * @since 12-Sep-2008
 */
public class ObjectNotFoundException extends DataAccessException {

	private static final long serialVersionUID = 6103950125831266706L;

	/**
	 * {@inheritDoc}
	 */
	public ObjectNotFoundException(String msg, Throwable cause) {
		super(msg, cause);
	}

	/**
	 * {@inheritDoc}
	 */
	public ObjectNotFoundException(String msg) {
		super(msg);
	}

}
