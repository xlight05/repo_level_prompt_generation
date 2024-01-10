/*
 * Copyright (c) Ringus Solution Enterprise Limited. All rights reserved.
 */
package org.xfuze.dao.impl.ibatis;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.dao.DataAccessException;
import org.xfuze.dao.DaoException;
import org.xfuze.dao.PagingAwareDao;
import org.xfuze.model.PagingAwareModel;
import org.xfuze.util.Match;
import org.xfuze.util.Restriction;

import com.ibatis.sqlmap.client.SqlMapException;

/**
 * @author Jason Chan
 *
 */
public class PagingAwareDaoImpl<M extends PagingAwareModel> extends CountAwareDaoImpl<M> implements PagingAwareDao<M> {
	/**
	 * Read page by criteria query name (Conjunction, Anywhere).
	 *
	 * Query: {0}.selectPageConjunctionAnywhere<br>
	 * Notes: 0 - model name<br>
	 * Example: User.selectPageConjunctionAnywhere
	 */
	protected static final String QUERY_GET_PAGE_CONJUNCTION_ANYWHERE = "{0}.selectPageConjunctionAnywhere";

	/**
	 * Read page by criteria query name (Conjunction, Exact).
	 *
	 * Query: {0}.selectPageConjunctionExact<br>
	 * Notes: 0 - model name<br>
	 * Example: User.selectPageConjunctionExact
	 */
	protected static final String QUERY_GET_PAGE_CONJUNCTION_EXACT = "{0}.selectPageConjunctionExact";

	/**
	 * Read page by criteria query name (Disjunction, Anywhere).
	 *
	 * Query: {0}.selectPageDisjunctionAnywhere<br>
	 * Notes: 0 - model name<br>
	 * Example: User.selectPageDisjunctionAnywhere
	 */
	protected static final String QUERY_GET_PAGE_DISJUNCTION_ANYWHERE = "{0}.selectPageDisjunctionAnywhere";

	/**
	 * Read page by criteria query name (Disjunction, Exact).
	 *
	 * Query: {0}.selectPageDisjunctionExact<br>
	 * Notes: 0 - model name<br>
	 * Example: User.selectPageDisjunctionExact
	 */
	protected static final String QUERY_GET_PAGE_DISJUNCTION_EXACT = "{0}.selectPageDisjunctionExact";

	public PagingAwareDaoImpl(final Class<M> persistentClass) {
		super(persistentClass);
	}

	public List<M> getPaginatedList(int pageNumber, int pageSize) {
		return getPaginatedList(Restriction.AND, Match.EXACT, null, pageNumber, pageSize);
	}

	@SuppressWarnings("unchecked")
	public List<M> getPaginatedList(Restriction restriction, Match match, M example, int pageNumber, int pageSize)
			throws DaoException {
		if (logger.isTraceEnabled()) {
			logger.trace(">>>>>-------------------------------------------------");
			logger.trace("Received restriction: {}", restriction);
			logger.trace("Received match      : {}", match);
			logger.trace("Received example    : {}", example);
			logger.trace("Received pageNumber : {}", pageNumber);
			logger.trace("Received pageSize   : {}", pageSize);
			logger.trace("-------------------------------------------------<<<<<");
		}

		String query = null;
		if (Restriction.AND == restriction) {
			if (Match.ANYWHERE == match) {
				query = getQueryName(QUERY_GET_PAGE_CONJUNCTION_ANYWHERE, modelName);
			} else if (Match.EXACT == match) {
				query = getQueryName(QUERY_GET_PAGE_CONJUNCTION_EXACT, modelName);
			} else {
				logger.error("Unknown match mode [" + match + "].");
			}
		} else if (Restriction.OR == restriction) {
			if (Match.ANYWHERE == match) {
				query = getQueryName(QUERY_GET_PAGE_DISJUNCTION_ANYWHERE, modelName);
			} else if (Match.EXACT == match) {
				query = getQueryName(QUERY_GET_PAGE_DISJUNCTION_EXACT, modelName);
			} else {
				logger.error("Unknown match mode [" + match + "].");
			}
		} else {
			logger.error("Unknown restriction [" + restriction + "].");
		}

		List<M> list = null;
		if (StringUtils.isNotBlank(query)) {
			if (example == null) {
				try {
					int count = getCount();
					int quotient = count / pageSize;
					int remainder = count % pageSize;

					M eg = persistentClass.newInstance();
					eg.setPage((1 + pageNumber) * pageSize);
					if (pageNumber < quotient) {
						eg.setPageSize(pageSize);
					} else {
						eg.setPageSize(remainder);
					}

					list = (List<M>) getSqlMapClientTemplate().queryForList(query, eg);
				} catch (InstantiationException e) {
					logger.error(e.getMessage(), e);
				} catch (IllegalAccessException e) {
					logger.error(e.getMessage(), e);
				} catch (DataAccessException e) {
					logger.error(e.getMessage(), e);
					throw e;
				} catch (SqlMapException e) {
					logger.error(e.getMessage(), e);
					throw e;
				}
			} else {
				int count = getCount(restriction, match, example);
				int quotient = count / pageSize;
				int remainder = count % pageSize;

				example.setPage((1 + pageNumber) * pageSize);
				if (pageNumber < quotient) {
					example.setPageSize(pageSize);
				} else {
					example.setPageSize(remainder);
				}

				try {
					list = (List<M>) getSqlMapClientTemplate().queryForList(query, example);
				} catch (DataAccessException e) {
					logger.error(e.getMessage(), e);
					throw e;
				} catch (SqlMapException e) {
					logger.error(e.getMessage(), e);
					throw e;
				}
			}
		}

		if (list == null) {
			list = new ArrayList<M>();
		}

		return list;
	}
}
