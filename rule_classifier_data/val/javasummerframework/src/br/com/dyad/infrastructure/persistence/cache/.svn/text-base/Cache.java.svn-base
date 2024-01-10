package br.com.dyad.infrastructure.persistence.cache;

import java.util.Hashtable;
import java.util.List;
import org.hibernate.Session;
import br.com.dyad.commons.data.AppTransaction;
import br.com.dyad.commons.data.DataList;
import br.com.dyad.infrastructure.entity.BaseEntity;
import br.com.dyad.infrastructure.persistence.PersistenceUtil;

public class Cache {		
	private static Cache instance;
	
	public static Cache getInstance() {
		if (instance == null) {
			instance = new Cache();
		}
		
		return instance;
	}
	
	public Cache() {}
	
	public Hashtable<String, Hashtable<Class,CacheDataList>> cache;

	public Hashtable<Class,CacheDataList> getCache(String database) {
		if (cache == null) {
			cache = new Hashtable<String, Hashtable<Class,CacheDataList>>();
		}
		
		if (cache.get(database) == null) {
			cache.put(database, new Hashtable<Class,CacheDataList>());
		}
		
		return cache.get(database);
	}

	private HibernateAppTransaction getDatabaseSession(String database) {
		Hashtable<Class,CacheDataList> hash = getCache(database);
		
		if (hash.size() > 0) {
			for (CacheDataList dataList : hash.values()) {
				return (HibernateAppTransaction)dataList.getTransactionalSession();
			}
		}
		Session session = PersistenceUtil.getSession(database);
		return HibernateAppTransaction.getNewSession(session);
	}
	
	//pega o número da última transação de uma classe			
	public Long getMaxTransactionId(String database, Class clazz) throws Exception {
		String classId = BaseEntity.getClassIdentifier(clazz);
		Long maxTransaction = null;
		AppTransaction session = getDatabaseSession(database);
		
		List result = null;
		
		//se o classId não for informado pega a tabela toda
		if (classId == null || classId.equals("")){			
			result = session.executeQuery("select max(lastTransaction) as lastTransaction from " + clazz.getName() + " where classId = '" + classId + "'");
			
		} else {
			result = session.executeQuery("select max(lastTransaction) as lastTransaction from " + clazz.getName());
		}
		
		maxTransaction = result.size() > 0 ? (Long)result.get(0) : 0L;
		return maxTransaction;
	}
	
	//sincroniza o datalist com o banco de dados
	public void synchronizeDataList(String database, Class clazz) throws Exception{		
		if (getCache(database).get(clazz) == null) {
			//Session session = PersistenceUtil.getSession(database);
			//AppTransaction transaction = DataListFactory.getNewAppSession(session);
			HibernateAppTransaction transaction = getDatabaseSession(database);
			CacheDataList dataList = new CacheDataList();
			dataList.setTransactionalSession(transaction);
			dataList.setCommitOnSave(false);
			String classId = BaseEntity.getClassIdentifier(clazz);
			
			//se o classId não for informado pega a tabela toda
			if (classId == null || classId.equals("")){
				//dataList.executeQuery("from " + clazz.getName() + " order by lastTransaction");
			} else {
				//dataList.executeQuery("from " + clazz.getName() + " where classId = '" + classId + "' order by lastTransaction");
			}
			
			getCache(database).put(clazz, dataList);
		} else {			
			CacheDataList dataList = (CacheDataList)getCache(database).get(clazz);
			Long lastTransactionDb = getMaxTransactionId(database, clazz);
			Long lastTransactionList = ((CacheDataList)getCache(database).get(clazz)).getLastTransaction();
			
			if (lastTransactionDb.compareTo(lastTransactionList) != 0) {				
				String classId = BaseEntity.getClassIdentifier(clazz);
				AppTransaction session = dataList.getTransactionalSession();					
				List list = null;				
				
				Session session2 = PersistenceUtil.getSession(database);
				
				//se o classId não for informado pega a tabela toda
				if (classId == null || classId.equals("")){			
					/*list = session.executeQuery("from " + clazz.getName() + 
							" where classId = '" + classId + "'" +
							" and lastTransaction > " + lastTransactionList +
							" order by lastTransaction");*/
					
					list = session2.createQuery("from " + clazz.getName() + 
							" where classId = '" + classId + "'" +
							" and lastTransaction > " + lastTransactionList +
							" order by lastTransaction").list(); 
				} else {
					/*list = session.executeQuery("from " + clazz.getName() + 
							" where lastTransaction > " + lastTransactionList +
							" order by lastTransaction");*/
					
					list = session2.createQuery("from " + clazz.getName() + 
							" where lastTransaction > " + lastTransactionList +
							" order by lastTransaction").list();
				}
				
				for (Object object : list) {
					BaseEntity entity = (BaseEntity)object;
					synchronizeEntity(database, entity);
				}
			}
		}		
	}
	
	public void synchronizeEntity(String database, BaseEntity entity) throws Exception {
		CacheDataList dataList = (CacheDataList)getCache(database).get(entity.getClass());
		int i = dataList.getList().indexOf(entity);
		
		if (i == -1) {
			dataList.add(entity);
		} else {
			dataList.delete(entity);
			dataList.add(entity);
		}
	}
	
	public DataList getDataList(String database, Class clazz) {
		Hashtable<Class,CacheDataList> hash = getCache(database);
		return hash.get(clazz);
	}
		
}
