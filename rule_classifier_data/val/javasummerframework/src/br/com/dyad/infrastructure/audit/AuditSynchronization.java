package br.com.dyad.infrastructure.audit;

import javax.transaction.Synchronization;

import org.hibernate.Session;
import org.hibernate.event.EventListeners;
import org.hibernate.event.PreDeleteEventListener;
import org.hibernate.event.PreInsertEventListener;
import org.hibernate.event.PreUpdateEventListener;

import br.com.dyad.infrastructure.persistence.PersistenceUtil;

public class AuditSynchronization implements Synchronization{
	
	private Session session;
	private Session auditedSession;

	public AuditSynchronization(Session auditedSession,Session session) {
		this.session = session;
		this.auditedSession = auditedSession;
	}
			
	public Session getSession() {
		return session;
	}

	public void setSession(Session session) {
		this.session = session;
	}

	//TODO Caso volte a usar o esquema de log antigo, isto deve ser descomentado 
	/*public void afterCompletion(int arg0) {
		session.getTransaction().commit();
		EventListeners listeners = PersistenceUtil.getDatabaseConfig(session.getSessionFactory()).getConf().getEventListeners();
		
		for (PreInsertEventListener evt : listeners.getPreInsertEventListeners()){
			HibernateAuditLogListener log = (HibernateAuditLogListener)evt;
			
			synchronized (log.getSessionList()) {
				log.getSessionList().remove(auditedSession);			
			}
		}		
		
		for (PreUpdateEventListener evt : listeners.getPreUpdateEventListeners()){
			HibernateAuditLogListener log = (HibernateAuditLogListener)evt;			
			synchronized (log.getSessionList()) {
				log.getSessionList().remove(auditedSession);			
			}
		}
		
		for (PreDeleteEventListener evt : listeners.getPreDeleteEventListeners()){
			HibernateAuditLogListener log = (HibernateAuditLogListener)evt;			
			synchronized (log.getSessionList()) {
				log.getSessionList().remove(auditedSession);			
			}				
		}		
	}*/

	public void beforeCompletion() {
		
	}

	@Override
	public void afterCompletion(int arg0) {
		// TODO Auto-generated method stub
		
	}		
}
