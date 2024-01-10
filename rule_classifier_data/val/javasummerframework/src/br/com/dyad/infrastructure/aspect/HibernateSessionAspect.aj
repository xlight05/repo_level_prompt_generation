package br.com.dyad.infrastructure.aspect;

import java.util.Hashtable;
import org.hibernate.Session;
import org.hibernate.Transaction;
import br.com.dyad.client.AppException;
import br.com.dyad.infrastructure.entity.BaseEntity;
import br.com.dyad.infrastructure.entity.KeyGenerator;
import br.com.dyad.infrastructure.persistence.DatabaseConfig;
import br.com.dyad.infrastructure.persistence.PersistenceUtil;

public aspect HibernateSessionAspect issingleton() {	
    declare precedence : HibernateSessionAspect, OverrideAspect;
	
	private Hashtable<Transaction, Long> sessions = new Hashtable<Transaction, Long>(); 	
	public pointcut callCommit() : call(* org.hibernate.Transaction+.commit(..)) && !within(HibernateSessionAspect+) && !within(KeyGenerator+);
	
	public void saveTransaction(Session session){
		try {
			if (session.getTransaction() != null && sessions.get(session.getTransaction()) == null){				
				DatabaseConfig dbConfig = PersistenceUtil.getDatabaseConfig(session.getSessionFactory());		
				Long transactionId = KeyGenerator.getInstance(dbConfig.getConfigName()).generateKey(null);
				
				sessions.put(session.getTransaction(), transactionId);
			}			
		} catch (Exception e) {
			e.printStackTrace();
			throw new AppException(e.getMessage());
		}	
	}
	
	after() returning : callCommit(){
		Transaction transaction = (Transaction)thisJoinPoint.getTarget();
		sessions.remove(transaction);
	}
	
	pointcut saveMethod() : (call(* org.hibernate.Session+.save(..)) ||
			call(* org.hibernate.Session+.persist(..)) ||
			call(* org.hibernate.Session+.update(..)) ||
			call(* org.hibernate.Session+.saveOrUpdate(..)) ||
			call(* org.hibernate.Session+.merge(..)))		
		 && !within(HibernateSessionAspect+) && !within(KeyGenerator+);
	
	before() : saveMethod(){
		Session session = (Session)thisJoinPoint.getTarget();
		saveTransaction(session);
		Object args[] = thisJoinPoint.getArgs();
		
		if (args != null) {
			if (args.length == 1){				
				fillTransactionId(session, args[0]);
			} else if (args.length == 2){				
				fillTransactionId(session, args[1]);
			}
		}
	}
	
	public void fillTransactionId(Session session, Object object){
		if (object instanceof BaseEntity){
			if (session != null && session.getTransaction() != null){
				((BaseEntity)object).setLastTransaction(sessions.get(session.getTransaction()));
			}
		}
	}

}
