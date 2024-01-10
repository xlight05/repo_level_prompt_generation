package br.com.dyad.infrastructure.audit;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import org.apache.commons.lang.ArrayUtils;
import org.hibernate.EntityMode;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.cfg.Configuration;
import org.hibernate.event.Initializable;
import org.hibernate.event.PostInsertEvent;
import org.hibernate.event.PostLoadEvent;
import org.hibernate.event.PostUpdateEvent;
import org.hibernate.event.PreDeleteEvent;
import org.hibernate.event.PreInsertEvent;
import org.hibernate.event.PreInsertEventListener;
import org.hibernate.event.PreLoadEvent;
import org.hibernate.event.PreUpdateEvent;
import org.hibernate.event.PreUpdateEventListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import br.com.dyad.commons.reflect.ReflectUtil;
import br.com.dyad.infrastructure.entity.BaseEntity;
import br.com.dyad.infrastructure.persistence.DatabaseConfig;
import br.com.dyad.infrastructure.persistence.PersistenceUtil;

/**
 * Audit Log Listener is used to log insert, update, delete, and load operations. Complete list of listeners/events can be obtained at <tt>org.hibernate.event.EventListeners</tt>.
 */
public final class HibernateAuditLogListener implements PreInsertEventListener, PreUpdateEventListener,
/*PreDeleteEventListener, PreLoadEventListener, PostLoadEventListener, 
 PostUpdateEventListener, PostInsertEventListener,*/ Initializable {

    private static final long serialVersionUID = 1L;
    private static final Logger LOG = LoggerFactory.getLogger(HibernateAuditLogListener.class);
    /*private static final String[] FIELDS_NOT_AUDITABLE = {"LASTTRANSACTION"}; 
    private Hashtable<Object,Session> sessionList = new Hashtable<Object,Session>();
    
    public HibernateAuditLogListener() {
		
	}
        
    public Hashtable<Object, Session> getSessionList() {
		return sessionList;
	}

	public void setSessionList(Hashtable<Object, Session> sessionList) {
		this.sessionList = sessionList;
	}

	public Session getSession(Session auditedSession) {    	
    	Session ret = sessionList.get(auditedSession);     	
    	if (ret == null){
    		DatabaseConfig dbConf = PersistenceUtil.getDatabaseConfig(auditedSession.getSessionFactory());
    		ret = dbConf.getSessionFactory().openSession();			
    		sessionList.put(auditedSession, ret);
    	}
    	
    	if (ret.getTransaction() == null || !ret.getTransaction().isActive()){
    		ret.beginTransaction();
    		auditedSession.getTransaction().registerSynchronization(new AuditSynchronization(auditedSession, ret));
    	}
    	
		return ret;
	}

    */
    public final void initialize(final Configuration cfg) {
        
    }

    /**
     * Log deletions made to the current model in the the Audit Trail.
     * 
     * @param event
     *            the pre-deletion event
     */
    /*public final boolean onPreDelete(final PreDeleteEvent event) {
    	try {
    		if ( ! ( event.getEntity() instanceof NotAuditable ) ) {            		    		
    			final Long actorId = 0L;
	            final Serializable entityId = event.getPersister().hasIdentifierProperty() ? event.getPersister().getIdentifier(event.getEntity(), event.getPersister().guessEntityMode(event.getEntity())) : null;
	            final Class clazz = event.getEntity().getClass();
	            final String entityName = clazz.getName();
	            final Date transTime = new Date(); // new Date(event.getSource().getTimestamp());
	            final EntityMode entityMode = event.getPersister().guessEntityMode(event.getEntity());
	            Object newPropValue = null;	           	            
	
	            if (LOG.isDebugEnabled()) {
	                LOG.debug("{} for: {}, ID: {}, actor: {}, date: {}", new Object[] { entityName, entityId, actorId, transTime });
	            }
	            
	            String propertyNames = new String();
	            String oldValues = new String();
	            String userLogin = "anonymous";
	            for (String propertyName : event.getPersister().getPropertyNames()) {	                
	            	if ( ArrayUtils.indexOf( FIELDS_NOT_AUDITABLE, propertyName.toUpperCase() ) == -1 ) {
		            	newPropValue = event.getPersister().getPropertyValue(event.getEntity(), propertyName, entityMode);
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
	            		BaseEntity baseEntity = (BaseEntity) event.getEntity();
	            		id = baseEntity.getId();
	            		transactionId = baseEntity.getLastTransaction();
	            		classId = baseEntity.getClassIdentifier(); 
	            	} catch( Exception e ){
	            		e.printStackTrace();
	            	}
	            	DatabaseConfig dbConfig = PersistenceUtil.getDatabaseConfig(event.getSession().getSessionFactory());
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
	                
	                getSession(event.getSession()).save(auditTrail);
	            }
    		}
    	} catch (HibernateException e) {
            LOG.error("Unable to process audit log for DELETE operation", e);
        }
        return false;
    }*/        
    
    /**
     * Log insertions made to the current model in the the Audit Trail.
     * 
     * @param event
     *            the pre-insertion event
     */
    public final boolean onPreInsert(final PreInsertEvent event) {
        try {
    		if ( ! ( event.getEntity() instanceof NotAuditable ) && ReflectUtil.inheritsFrom(event.getEntity().getClass(), BaseEntity.class) ) {
    			((BaseEntity)event.getEntity()).setOriginalValues(new HashMap<String, Object>());
    			((BaseEntity)event.getEntity()).setNewRecord(false);
    		}
    	} catch (HibernateException e) {
            LOG.error("Unable to process audit log for INSERT operation", e);
        }
        return false;
    }
    /*public final boolean onPreInsert(final PreInsertEvent event) {
        try {
    		if ( ! ( event.getEntity() instanceof NotAuditable ) ) {            	

	        	// TODO : need to get the actor ID somehow
	            final Long actorId = 0L;
	            //final String entityId = event.getPersister().hasIdentifierProperty() ? event.getPersister().getIdentifier(event.getEntity(), event.getPersister().guessEntityMode(event.getEntity())).toString() : "";
	            final String entityId = event.getPersister().hasIdentifierProperty() ? event.getPersister().ENTITY_ID: "";
	            final Class clazz = event.getEntity().getClass();
	            final String entityName = clazz.getName();
	            final Date transTime = new Date(); // new Date(event.getSource().getTimestamp());
	            final EntityMode entityMode = event.getPersister().guessEntityMode(event.getEntity());
	            Object newPropValue = null;
	
	            // need to have a separate session for audit save	             
	
	            String propertyNames = new String();
	            String newValues = new String();
	            String userLogin = "anonymous";
	            for (String propertyName : event.getPersister().getPropertyNames()) {
	            	if ( ArrayUtils.indexOf( FIELDS_NOT_AUDITABLE, propertyName.toUpperCase() ) == -1 ) {
		                newPropValue = event.getPersister().getPropertyValue(event.getEntity(), propertyName, entityMode);
		                // because we are performing an insert we only need to be concerned will non-null values
		                if (newPropValue != null) {
		                    // collections will fire their own events
		                    if (!(newPropValue instanceof Collection)) {
		                    	String newStrPropValue = newPropValue != null ? newPropValue.toString() : "";
		                    	if (LOG.isDebugEnabled()) {
		                            LOG.debug("{} for: {}, ID: {}, property: {}, value: {}, actor: {}, date: {}", new Object[] { AuditTrail.OPERATION_TYPE_INSERT, entityName, entityId, propertyName, newPropValue, actorId, transTime });
		                        }
		                        propertyNames = propertyNames + propertyName.length() + ":" + propertyName + ";";
		                        newValues = newValues + newStrPropValue.length() + ":" + newStrPropValue + ";"; 
		                    }
		                }
	            	}   
	            }
	            
	            if ( propertyNames != "" ){
	            	//System.out.println("Insert");
	            	Long id = null;
	            	Long transactionId = null;
	            	String classId = null;
	            	try {
	            		BaseEntity baseEntity = (BaseEntity) event.getEntity();
	            		id = baseEntity.getId();
	            		//System.out.println("id entidade: " + id);
	            		transactionId = baseEntity.getLastTransaction();
	            		classId = baseEntity.getClassIdentifier();
	            	} catch( Exception e ){
	            		e.printStackTrace();
	            	}
	            	DatabaseConfig dbConfig = PersistenceUtil.getDatabaseConfig(event.getSession().getSessionFactory());
	            	String tableName = dbConfig.getConf().getClassMapping(clazz.getName()).getTable().getName();
	            	
	            	AuditTrail auditTrail = new AuditTrail();
	                //Faltando implementar a setagem das duas propriedades abaixo.
	                auditTrail.setTransactionId( transactionId );
	                auditTrail.setRecordId( id );
	                auditTrail.setClassId(classId);
	                auditTrail.setEntityName(entityName);
	            	auditTrail.setTableName(tableName);
	                auditTrail.setFieldNames(propertyNames);
	                auditTrail.setOldValues("");
	                auditTrail.setNewValues(newValues);
	                auditTrail.setType(AuditTrail.OPERATION_TYPE_INSERT);
	                auditTrail.setUserLogin(userLogin);
	                auditTrail.setDate(transTime);
	                auditTrail.setSignature(auditTrail.getHashCodeSignature());
	                
	                //Edu
	                //session.insert(auditTrail);	                
	                getSession(event.getSession()).save(auditTrail);
	            }
    		}
    	} catch (HibernateException e) {
            LOG.error("Unable to process audit log for INSERT operation", e);
        }
        return false;
    }*/

    /**
     * Log updates made to the current model in the the Audit Trail.
     * 
     * @param event
     *            the pre-update event
     */
    /*public final boolean onPreUpdate(PreUpdateEvent event) {
        try {
    		if ( ! ( event.getEntity() instanceof NotAuditable ) ) {            	
	        	// TODO : need to get the actor ID somehow
	            final Long actorId = 0L;
	            final Serializable entityId = event.getPersister().hasIdentifierProperty() ? event.getPersister().getIdentifier(event.getEntity(), event.getPersister().guessEntityMode(event.getEntity())) : null;
	            final Class clazz = event.getEntity().getClass();
	            final String entityName = clazz.getName();
	            final Date transTime = new Date(); // new Date(event.getSource().getTimestamp());
	            final EntityMode entityMode = event.getPersister().guessEntityMode(event.getEntity());
	            Object oldPropValue = null;
	            Object newPropValue = null;
	
	            // get the existing entity from session so that we can extract existing property values	            
	            Object existingEntity = null;
	            if (event.getEntity() instanceof BaseEntity){
	            	existingEntity = event.getEntity().getClass().newInstance();
	            	
	            	List<String> props = ReflectUtil.getClassProperties(existingEntity.getClass(), true);
	            	BaseEntity be = (BaseEntity)event.getEntity();
	            	
	            	for (String propName : props) {
	            		try{	            			
	            			ReflectUtil.setFieldValue(existingEntity, propName, be.getOriginalValue(propName));
	            		}catch(Exception e){	            			
	            		}
					}
	            	
	            	be.setOriginalValues(new HashMap<String, Object>());
		            //ReflectUtil.populateBean(existingEntity, ((BaseEntity)existingEntity).getOldValues());
	            }else{
	            	existingEntity = getSession(event.getSession()).get(event.getEntity().getClass(), entityId);
	            }	             	            
	            //
	
	            String propertyNames = new String();
	            String newValues = new String();
	            String oldValues = new String();
	            String userLogin = "anonymous";
	            // cycle through property names, extract corresponding property values and insert new entry in audit trail
	            for (String propertyName : event.getPersister().getPropertyNames()) {
	            	if ( ArrayUtils.indexOf( FIELDS_NOT_AUDITABLE, propertyName.toUpperCase() ) == -1 ) {
		            	newPropValue = event.getPersister().getPropertyValue(event.getEntity(), propertyName, entityMode);
		                // because we are performing an insert we only need to be concerned will non-null values
		                if (newPropValue != null) {
		                    // collections will fire their own events
		                    if (!(newPropValue instanceof Collection)) {
		                    	oldPropValue = event.getPersister().getPropertyValue(existingEntity, propertyName, entityMode);
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
	            	DatabaseConfig dbConfig = PersistenceUtil.getDatabaseConfig(event.getSession().getSessionFactory());
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
	                //session.insert(new AuditTrail(entityId.toString(), entityName, propertyName, oldPropValue != null ? oldPropValue.toString() : null, newPropValue != null ? newPropValue.toString() : null, OPERATION_TYPE_UPDATE, actorId, transTime));
	                getSession(event.getSession()).save(auditTrail);
	            }
    		}   
    	} catch (Exception e) {
            LOG.error("Unable to process audit log for UPDATE operation", e);
        }
        return false;
    }*/

    /**
     * Log the loading of the current model in the the Audit Trail.
     * 
     * @param event
     *            the pre-load event
     */
    /*public final void onPreLoad(final PreLoadEvent event) {
    	
    }

	public void onPostLoad(PostLoadEvent event) {
		if (event.getEntity() != null && event.getEntity() instanceof BaseEntity){
			try {
				PersistenceUtil.setOldValues((BaseEntity)event.getEntity());
			} catch (Exception e) {
				e.printStackTrace();
				LOG.error("Unable to process audit log for ONPOSTLOAD operation", e);
			}
		}
	}

	public void onPostUpdate(PostUpdateEvent event) {
		if (event.getEntity() != null && event.getEntity() instanceof BaseEntity){
			try {
				PersistenceUtil.setOldValues((BaseEntity)event.getEntity());
			} catch (Exception e) {
				e.printStackTrace();
				LOG.error("Unable to process audit log for ONPOSTUPDATE operation", e);
			}
		}		
	}

	public void onPostInsert(PostInsertEvent event) {
		if (event.getEntity() != null && event.getEntity() instanceof BaseEntity){
			try {
				PersistenceUtil.setOldValues((BaseEntity)event.getEntity());
			} catch (Exception e) {
				e.printStackTrace();
				LOG.error("Unable to process audit log for ONPOSTINSERT operation", e);
			}
		}		
	}*/

	@Override
	public boolean onPreUpdate(PreUpdateEvent event) {
		try {
    		if ( ! ( event.getEntity() instanceof NotAuditable ) && ReflectUtil.inheritsFrom(event.getEntity().getClass(), BaseEntity.class) ) {
    			((BaseEntity)event.getEntity()).setOriginalValues(new HashMap<String, Object>());
    			((BaseEntity)event.getEntity()).setNewRecord(false);
    		}
    	} catch (HibernateException e) {
            LOG.error("Unable to process audit log for INSERT operation", e);
        }
        return false;
	}	
	
}