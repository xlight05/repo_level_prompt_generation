package br.com.dyad.infrastructure.aspect;

import java.util.Date;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import br.com.dyad.commons.reflect.ReflectUtil;
import br.com.dyad.infrastructure.audit.AuditTrail;
import br.com.dyad.infrastructure.persistence.PersistenceUtil;

public aspect LogExceptionAspect {
	private Logger log = Logger.getLogger(LogExceptionAspect.class);
	public pointcut exceptionHandlerPointcut(Exception exception) : 
	      handler(Exception+) && args(exception) && !within(LogExceptionAspect+) && !cflow(adviceexecution( ));

	before(Exception exception) : exceptionHandlerPointcut(exception) {
		//if (!ReflectUtil.inheritsFrom(exception.getClass(), HibernateException.class)){
			try {
				String database = UserInfoAspect.getDatabase();
				if (database != null){				
					Session session = PersistenceUtil.getSession(database);
					session.beginTransaction();
					try{					
						AuditTrail auditTrail = new AuditTrail();
						auditTrail.setEntityName(exception.getClass().getName());
						auditTrail.setNewValues(exception.getMessage());
						auditTrail.setType(AuditTrail.OPERATION_TYPE_ERROR);
						auditTrail.setUserLogin(UserInfoAspect.getUser());
						auditTrail.setDate(new Date());
						auditTrail.setSignature(auditTrail.getHashCodeSignature());
						
						session.save(auditTrail);
						session.getTransaction().commit();
					} catch(Exception e3) {
						session.getTransaction().rollback();
						throw e3;
					}
				}                        
			} catch (Exception e2) {
				log.error(e2);
			}
		//}
	}
}
