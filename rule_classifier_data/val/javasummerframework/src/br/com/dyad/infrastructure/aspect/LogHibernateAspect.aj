package br.com.dyad.infrastructure.aspect;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import org.apache.commons.lang.ArrayUtils;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import br.com.dyad.commons.reflect.ReflectUtil;
import br.com.dyad.infrastructure.audit.AuditTrail;
import br.com.dyad.infrastructure.audit.NotAuditable;
import br.com.dyad.infrastructure.entity.BaseEntity;
import br.com.dyad.infrastructure.persistence.DatabaseConfig;
import br.com.dyad.infrastructure.persistence.PersistenceUtil;

public aspect LogHibernateAspect {	
	private static final Logger LOG = LoggerFactory.getLogger("HibernateLog");
	private static final String[] FIELDS_NOT_AUDITABLE = {"LASTTRANSACTION"}; 

	
	public void executeLog(Session session, Object object){
		if (object instanceof BaseEntity){
			BaseEntity entity = (BaseEntity)object;
			if (entity.isNewRecord()){
				saveAudit(session, object);
			} else {
				updateAudit(session, object);
			}
		}
	}
	
	pointcut saveMethod() : (call(* org.hibernate.Session+.save(..)) ||
			call(* org.hibernate.Session+.persist(..)) ||
			call(* org.hibernate.Session+.update(..)) ||
			call(* org.hibernate.Session+.saveOrUpdate(..)) ||
			call(* org.hibernate.Session+.merge(..)))		
		&& !within(LogHibernateAspect);
	
	after() returning : saveMethod(){
		Session session = (Session)thisJoinPoint.getTarget();
		Object args[] = thisJoinPoint.getArgs();
		
		if (args != null) {
			if (args.length == 1){				
				executeLog(session, args[0]);
			} else if (args.length == 2){				
				executeLog(session, args[1]);
			}
		}
	}
	
	pointcut deleteMethod() : call(* org.hibernate.Session+.delete(..))		
		&& !within(LogHibernateAspect);
	
	after() returning : deleteMethod(){
		Session session = (Session)thisJoinPoint.getTarget();
		Object args[] = thisJoinPoint.getArgs();
		
		if (args != null) {
			if (args.length == 1){				
				deleteAudit(session, args[0]);
			} else if (args.length == 2){				
				deleteAudit(session, args[1]);
			}
		}
	}	
	
	public void saveAudit(Session session, Object object){
		try {
    		if ( ! ( object instanceof NotAuditable ) ) {            	
	        	// TODO : need to get the actor ID somehow
	            //final Long actorId = 0L;
	            //final Serializable entityId = event.getPersister().hasIdentifierProperty() ? event.getPersister().getIdentifier(event.getEntity(), event.getPersister().guessEntityMode(event.getEntity())) : null;
	            final Serializable entityId = session.getIdentifier(object);
	            final Class clazz = object.getClass();
	            final String entityName = clazz.getName();
	            final Date transTime = new Date(); // new Date(event.getSource().getTimestamp());
	            //final EntityMode entityMode = event.getPersister().guessEntityMode(event.getEntity());
	            Object oldPropValue = null;
	            Object newPropValue = null;
	
	            // get the existing entity from session so that we can extract existing property values	            
	            Object existingEntity = null;
	            if (object instanceof BaseEntity){
	            	existingEntity = object.getClass().newInstance();
	            	
	            	List<String> props = ReflectUtil.getPersistedProperties(existingEntity.getClass());
	            	BaseEntity be = (BaseEntity)object;
	            	
	            	for (String propName : props) {
	            		try{	            			
	            			ReflectUtil.setFieldValue(existingEntity, propName, be.getOriginalValue(propName));
	            		}catch(Exception e){	            			
	            		}
					}
	            	
	            	//Altera o originalValues e a propriedade newRecord
	            	//be.setOriginalValues(new HashMap<String, Object>());
	            	//be.setNewRecord(false);
	            }else{
	            	//nunca vai entrar aqui
	            	existingEntity = session.get(clazz, entityId);
	            }	             	            
	            //
	
	            String propertyNames = new String();
	            String newValues = new String();
	            String oldValues = new String();
	            String userLogin = UserInfoAspect.getUser();
	            // cycle through property names, extract corresponding property values and insert new entry in audit trail	            
	            for (String propertyName : ReflectUtil.getPersistedProperties(clazz)) {
	            	if ( ArrayUtils.indexOf( FIELDS_NOT_AUDITABLE, propertyName.toUpperCase() ) == -1 ) {
		            	newPropValue = ReflectUtil.getFieldValue(object, propertyName); 
		                // because we are performing an insert we only need to be concerned will non-null values
		                if (newPropValue != null) {
		                    // collections will fire their own events
		                    if (!(newPropValue instanceof Collection)) {
		                    	oldPropValue = ReflectUtil.getFieldValue(existingEntity, propertyName);//event.getPersister().getPropertyValue(existingEntity, propertyName, entityMode);
		                        /*
		                         * if (LOG.isDebugEnabled()) {
		                            LOG.debug("{} for: {}, ID: {}, property: {}, old value: {}, new value: {}, actor: {}, date: {}", new Object[] { AuditTrail.OPERATION_TYPE_UPDATE, entityName, entityId, propertyName, oldPropValue, newPropValue, actorId, transTime });
		                        }*/
		                        String newStrPropValue = newPropValue != null ? newPropValue.toString() : "";
		                    	String oldStrPropValue = oldPropValue != null ? oldPropValue.toString() : "";
		                        
		                        if ( ! newStrPropValue.equals( oldStrPropValue ) ){ 
		                        	propertyNames = propertyNames + propertyName.length() + ":" + propertyName + ";";
		                        	newValues = newValues + newStrPropValue.length() + ":" + newStrPropValue + ";"; 
		                        	oldValues = oldValues + oldStrPropValue.length() + ":" + oldStrPropValue + ";"; 
		                        }	
		                    }
		                }
	            	}    
	            }
	
	            if ( propertyNames != "" ){
	            	Long id = null;
	            	Long transactionId = null;
	            	String classId = null;
	            	try {
	            		BaseEntity baseEntity = (BaseEntity) existingEntity;
	            		id = baseEntity.getId();
	            		transactionId = baseEntity.getLastTransaction();
	            		classId = baseEntity.getClassIdentifier();
	            	} catch( Exception e ){
	            		e.printStackTrace();
	            	}
	            	
	            	DatabaseConfig dbConfig = PersistenceUtil.getDatabaseConfig(session.getSessionFactory());
	            	String tableName = dbConfig.getConf().getClassMapping(clazz.getName()).getTable().getName();
	            	
	            	AuditTrail auditTrail = new AuditTrail();
	                auditTrail.setTransactionId( transactionId );
	                auditTrail.setRecordId( id );
	                auditTrail.setClassId(classId);
	                auditTrail.setEntityName(entityName);
	            	auditTrail.setTableName(tableName);
	                auditTrail.setFieldNames(propertyNames);
	                auditTrail.setOldValues(oldValues);
	                auditTrail.setNewValues(newValues);
	                auditTrail.setType(AuditTrail.OPERATION_TYPE_UPDATE);
	                auditTrail.setUserLogin(userLogin);
	                auditTrail.setDate(transTime);
	                auditTrail.setSignature(auditTrail.getHashCodeSignature());
	                auditTrail.setNote(SysReferenceAspect.getReferenceName());
	                session.save(auditTrail);
	            }
    		}   
    	} catch (Exception e) {
            LOG.error("Unable to process audit log for UPDATE operation", e);
        }
	}
	
	public void updateAudit(Session session, Object object){
		try {
    		if ( ! ( object instanceof NotAuditable ) ) {            	
	        	// TODO : need to get the actor ID somehow
	            final Long actorId = 0L;
	            final Serializable entityId = session.getIdentifier(object);
	            final Class clazz = object.getClass();
	            final String entityName = clazz.getName();
	            final Date transTime = new Date(); 
	            Object oldPropValue = null;
	            Object newPropValue = null;
	
	            // get the existing entity from session so that we can extract existing property values	            
	            Object existingEntity = null;
	            if (object instanceof BaseEntity){
	            	existingEntity = object.getClass().newInstance();
	            	
	            	List<String> props = ReflectUtil.getPersistedProperties(existingEntity.getClass());
	            	BaseEntity be = (BaseEntity)object;
	            	
	            	for (String propName : props) {
	            		try{	            			
	            			ReflectUtil.setFieldValue(existingEntity, propName, be.getOriginalValue(propName));
	            		}catch(Exception e){	            			
	            		}
					}
	            	
	            	//Altera o originalValues e a propriedade newRecord
	            	//be.setOriginalValues(new HashMap<String, Object>());
	            	//be.setNewRecord(false);
	            }else{
	            	//nunca vai entrar aqui
	            	existingEntity = session.get(clazz, entityId);
	            }	             	            
	            //
	
	            String propertyNames = new String();
	            String newValues = new String();
	            String oldValues = new String();
	            String userLogin = UserInfoAspect.getUser();
	            // cycle through property names, extract corresponding property values and insert new entry in audit trail
	            for (String propertyName : ReflectUtil.getPersistedProperties(clazz)) {
	            	if ( ArrayUtils.indexOf( FIELDS_NOT_AUDITABLE, propertyName.toUpperCase() ) == -1 ) {
		            	newPropValue = ReflectUtil.getFieldValue(object, propertyName);
		                // because we are performing an insert we only need to be concerned will non-null values
		                if (newPropValue != null) {
		                    // collections will fire their own events
		                    if (!(newPropValue instanceof Collection)) {
		                    	oldPropValue = ReflectUtil.getFieldValue(existingEntity, propertyName);
		                        if (LOG.isDebugEnabled()) {
		                            LOG.debug("{} for: {}, ID: {}, property: {}, old value: {}, new value: {}, actor: {}, date: {}", new Object[] { AuditTrail.OPERATION_TYPE_UPDATE, entityName, entityId, propertyName, oldPropValue, newPropValue, actorId, transTime });
		                        }
		                        String newStrPropValue = newPropValue != null ? newPropValue.toString() : "";
		                    	String oldStrPropValue = oldPropValue != null ? oldPropValue.toString() : "";
		                        
		                        if ( ! newStrPropValue.equals( oldStrPropValue ) ){ 
		                        	propertyNames = propertyNames + propertyName.length() + ":" + propertyName + ";";
		                        	newValues = newValues + newStrPropValue.length() + ":" + newStrPropValue + ";"; 
		                        	oldValues = oldValues + oldStrPropValue.length() + ":" + oldStrPropValue + ";"; 
		                        }	
		                    }
		                }
	            	}    
	            }
	
	            if ( propertyNames != "" ){
	            	Long id = null;
	            	Long transactionId = null;
	            	String classId = null;
	            	try {
	            		BaseEntity baseEntity = (BaseEntity) existingEntity;
	            		id = baseEntity.getId();
	            		transactionId = baseEntity.getLastTransaction();
	            		classId = baseEntity.getClassIdentifier();
	            	} catch( Exception e ){
	            		e.printStackTrace();
	            	}
	            	//System.out.println("update");
	            	DatabaseConfig dbConfig = PersistenceUtil.getDatabaseConfig(session.getSessionFactory());
	            	String tableName = dbConfig.getConf().getClassMapping(clazz.getName()).getTable().getName();
	            	
	            	AuditTrail auditTrail = new AuditTrail();
	                //Faltando implementar a setagem das duas propriedades abaixo.
	                auditTrail.setTransactionId( transactionId );
	                auditTrail.setRecordId( id );
	                auditTrail.setClassId(classId);
	                auditTrail.setEntityName(entityName);
	            	auditTrail.setTableName(tableName);
	                auditTrail.setFieldNames(propertyNames);
	                auditTrail.setOldValues(oldValues);
	                auditTrail.setNewValues(newValues);
	                auditTrail.setType(AuditTrail.OPERATION_TYPE_UPDATE);
	                auditTrail.setUserLogin(userLogin);
	                auditTrail.setDate(transTime);
	                auditTrail.setSignature(auditTrail.getHashCodeSignature());
	                auditTrail.setNote(SysReferenceAspect.getReferenceName());
	                session.save(auditTrail);
	            }
    		}   
    	} catch (Exception e) {
            LOG.error("Unable to process audit log for UPDATE operation", e);
        }
	}
	
	public void deleteAudit(Session session, Object object){
		try {
    		if ( ! ( object instanceof NotAuditable ) ) {            		    		
    			final Long actorId = 0L;
    			final Serializable entityId = session.getIdentifier(object);
	            final Class clazz = object.getClass();
	            final String entityName = clazz.getName();
	            final Date transTime = new Date(); 
	            Object newPropValue = null;	           	            
	
	            if (LOG.isDebugEnabled()) {
	                LOG.debug("{} for: {}, ID: {}, actor: {}, date: {}", new Object[] { entityName, entityId, actorId, transTime });
	            }
	            
	            String propertyNames = new String();
	            String oldValues = new String();
	            String userLogin = UserInfoAspect.getUser();
	            for (String propertyName : ReflectUtil.getPersistedProperties(clazz)) {	                
	            	if ( ArrayUtils.indexOf( FIELDS_NOT_AUDITABLE, propertyName.toUpperCase() ) == -1 ) {
		            	newPropValue = ReflectUtil.getFieldValue(object, propertyName);
		                // because we are performing an insert we only need to be concerned will non-null values
		                String newStrPropValue = newPropValue != null ? newPropValue.toString() : "";
		                if (newPropValue != null) {
		                    // collections will fire their own events
		                    if (!(newPropValue instanceof Collection)) {                    	
		                    	if (LOG.isDebugEnabled()) {
		                            LOG.debug("{} for: {}, ID: {}, property: {}, value: {}, actor: {}, date: {}", new Object[] { AuditTrail.OPERATION_TYPE_INSERT, entityName, entityId, propertyName, newPropValue, actorId, transTime });
		                        }
		                        propertyNames = propertyNames + propertyName.length() + ":" + propertyName + ";";
		                        oldValues = oldValues + newStrPropValue.length() + ":" + newStrPropValue + ";"; 
		                    }
		                }
	            	}    
	            }
	            
	            if ( propertyNames != "" ){
	            	//System.out.println("Delete");
	            	Long id = null;
	            	Long transactionId = null;	            	
	            	String classId = null;
	            	try {
	            		BaseEntity baseEntity = (BaseEntity)object;
	            		id = baseEntity.getId();
	            		transactionId = baseEntity.getLastTransaction();
	            		classId = baseEntity.getClassIdentifier(); 
	            	} catch( Exception e ){
	            		e.printStackTrace();
	            	}
	            	DatabaseConfig dbConfig = PersistenceUtil.getDatabaseConfig(session.getSessionFactory());
	            	String tableName = dbConfig.getConf().getClassMapping(clazz.getName()).getTable().getName();
	            	AuditTrail auditTrail = new AuditTrail();
	                auditTrail.setTransactionId( transactionId );
	                auditTrail.setRecordId( id );
	                auditTrail.setClassId(classId);
	                auditTrail.setEntityName(entityName);
	                auditTrail.setTableName(tableName);
	                auditTrail.setFieldNames(propertyNames);
	                auditTrail.setOldValues(oldValues);
	                auditTrail.setNewValues("");
	                auditTrail.setType(AuditTrail.OPERATION_TYPE_DELETE);
	                auditTrail.setUserLogin(userLogin);
	                auditTrail.setDate(transTime);
	                auditTrail.setSignature(auditTrail.getHashCodeSignature());
	                auditTrail.setNote(SysReferenceAspect.getReferenceName());
	                session.save(auditTrail);
	            }
    		}
    	} catch (HibernateException e) {
            LOG.error("Unable to process audit log for DELETE operation", e);
        }
	}

}
