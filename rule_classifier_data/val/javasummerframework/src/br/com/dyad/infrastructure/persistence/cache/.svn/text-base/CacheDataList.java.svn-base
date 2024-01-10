package br.com.dyad.infrastructure.persistence.cache;

import java.util.List;

import br.com.dyad.commons.data.AppEntity;
import br.com.dyad.commons.data.DataList;
import br.com.dyad.infrastructure.entity.BaseEntity;

public class CacheDataList extends DataList {
	
	public CacheDataList() {
		setCommitOnSave(false);
	}

	private Long lastTransaction;

	public Long getLastTransaction() {
		if (lastTransaction == null) {
			setLastTransaction(0L);
		}
		
		return lastTransaction;
	}

	public void setLastTransaction(Long lastTransaction) {
		this.lastTransaction = lastTransaction;
	}
	
	@Override
	public List executeQuery(String hquery) throws RuntimeException {
		List tempList = super.executeQuery(hquery);
		
		if (tempList.size() == 0) {
			lastTransaction = 0L;
		} else {
			BaseEntity entity = (BaseEntity)tempList.get(tempList.size() - 1);
			lastTransaction = entity.getLastTransaction();
		}
		
		return tempList;
	}
	
	@Override
	public void add(AppEntity appEntity) throws Exception {
		super.add(appEntity);
		
		BaseEntity entity = (BaseEntity)appEntity;
		updateLastTransaction(entity);
	}
	
	public void updateLastTransaction(BaseEntity entity) {
		if (entity.getLastTransaction().longValue() > getLastTransaction().longValue()) {
			setLastTransaction(entity.getLastTransaction());
		}
	}
	
	@Override
	public Long commit() throws Exception {
		// não faz nada
		return 0L;
	}
	
}
