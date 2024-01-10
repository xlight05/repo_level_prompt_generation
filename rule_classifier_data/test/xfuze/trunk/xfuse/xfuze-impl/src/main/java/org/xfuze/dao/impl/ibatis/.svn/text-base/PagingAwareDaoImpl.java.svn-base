/*
 * Copyright (c) Ringus Solution Enterprise Limited. All rights reserved.
 */
package org.xfuze.dao.impl.ibatis;

import java.util.List;

import org.xfuze.dao.DaoException;
import org.xfuze.dao.PagingAwareDao;
import org.xfuze.dao.helper.ibatis.PagingHelper;
import org.xfuze.model.PagingAwareModel;
import org.xfuze.util.Match;
import org.xfuze.util.Restriction;

/**
 * @author Jason Chan
 *
 */
public class PagingAwareDaoImpl<M extends PagingAwareModel> extends CountAwareDaoImpl<M> implements PagingAwareDao<M> {
	protected PagingHelper pagingHelper;

	public PagingAwareDaoImpl(final Class<M> persistentClass) {
		super(persistentClass);
	}

	public void setPagingHelper(PagingHelper pagingHelper) {
		this.pagingHelper = pagingHelper;
	}

	@SuppressWarnings("unchecked")
	public List<M> getPaginatedList(int pageNumber, int pageSize) {
		return (List<M>) pagingHelper.getPaginatedList((Class<PagingAwareModel>) persistentClass, modelName,
				pageNumber, pageSize);
	}

	@SuppressWarnings("unchecked")
	public List<M> getPaginatedList(Restriction restriction, Match match, M example, int pageNumber, int pageSize)
			throws DaoException {
		return (List<M>) pagingHelper.getPaginatedList(restriction, match, (Class<PagingAwareModel>) persistentClass,
				modelName, example, pageNumber, pageSize);
	}
}
