package br.com.dyad.infrastructure.persistence.cache;

import java.util.List;

import org.hibernate.Session;

import br.com.dyad.commons.data.AppEntity;
import br.com.dyad.commons.data.AppTransaction;
import br.com.dyad.infrastructure.entity.BaseEntity;

public abstract class HibernateAppTransaction extends AppTransaction {

	protected Session session;
		
	public HibernateAppTransaction(Session session) {
		this.session = session;
	}
	
	public Session getSession() {
		return session;
	}

	public void setSession(Session session) {
		this.session = session;
	}

	public static HibernateAppTransaction getNewSession(final Session session){
		return new HibernateAppTransaction(session){

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
			
		};
	}

}
