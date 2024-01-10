package br.com.dyad.infrastructure.persistence;

import java.util.List;

import org.hibernate.Session;

import br.com.dyad.commons.data.AppEntity;
import br.com.dyad.commons.data.AppTransaction;
import br.com.dyad.commons.data.DataList;
import br.com.dyad.infrastructure.entity.BaseEntity;

public class DataListFactory {
		
	public static DataList newDataList(String database){
		final Session session = PersistenceUtil.getSession(database);
		AppTransaction appTransaction = getNewAppSession(session);
		appTransaction.setDatabase(database);
		DataList dataList = new DataList( appTransaction );
		
		return dataList;
	}

	public static DataList newDataList( AppTransaction appTransaction ){
		DataList dataList = new DataList( appTransaction );		
		return dataList;
	}
	
	public static AppTransaction getNewAppSession(final Session session){
		return new AppTransaction(){

			@Override
			public void beginTransaction() throws Exception {
				session.beginTransaction();
				
			}

			@Override
			public Long commit() {
				session.getTransaction().commit();
				return getTransactionNumber();
			}

			@Override
			public void delete(AppEntity appEntity) {
				session.delete(appEntity);				
			}

			@Override
			public List executeQuery(String query) {
				return session.createQuery(query).list();
			}

			@Override
			public void rollback() {
				if (session.getTransaction() != null){
					session.getTransaction().rollback();
				}
			}

			@Override
			public void save(AppEntity appEntity) {
				if (appEntity instanceof BaseEntity){
					setTransactionNumber(((BaseEntity)appEntity).getLastTransaction());
				}
				session.save(appEntity);
			}

			@Override
			public Object getSession() {
				return session;
			}
			
		};
	}

}
