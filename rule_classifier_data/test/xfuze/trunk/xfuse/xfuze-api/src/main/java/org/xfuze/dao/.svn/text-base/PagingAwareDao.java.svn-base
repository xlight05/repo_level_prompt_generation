/*
 * Copyright (c) Ringus Solution Enterprise Limited. All rights reserved.
 */
package org.xfuze.dao;

import java.util.List;

import org.xfuze.model.PagingAwareModel;
import org.xfuze.util.Match;
import org.xfuze.util.Restriction;

/**
 * @author Jason Chan
 *
 */
public interface PagingAwareDao<M extends PagingAwareModel> extends CountAwareDao<M> {
	/**
	 * Retrieve list of paginated model in the given page number and page size.
	 *
	 * @param pageNumber
	 *            the page number, start from 0.
	 * @param pageSize
	 *            the page size (must > 0).
	 * @return list of model or empty list if not found.
	 * @throws DaoException
	 *             if failed to retrieve.
	 */
	public List<M> getPaginatedList(int pageNumber, int pageSize) throws DaoException;

	/**
	 * Retrieve list of paginated model in the given restriction, match mode, example, page number and page size.
	 *
	 * @param restriction
	 *            the restriction.
	 * @param match
	 *            the match mode.
	 * @param example
	 *            the example
	 * @param pageNumber
	 *            the page number, start from 0.
	 * @param pageSize
	 *            the page size (must > 0).
	 * @return list of model or empty list if not found.
	 * @throws DaoException
	 *             if failed to retrieve.
	 */
	public List<M> getPaginatedList(Restriction restriction, Match match, M example, int pageNumber, int pageSize)
			throws DaoException;
}
