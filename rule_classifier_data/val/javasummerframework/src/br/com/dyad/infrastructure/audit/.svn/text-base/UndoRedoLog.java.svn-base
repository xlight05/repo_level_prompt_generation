package br.com.dyad.infrastructure.audit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import br.com.dyad.commons.reflect.ReflectUtil;
import br.com.dyad.infrastructure.entity.BaseEntity;


public class UndoRedoLog {
	
	@SuppressWarnings("unchecked")
	public static UndoRedoLogResult redoLog( Long transactionID, String database ) throws Exception{
		UndoRedoLogResult result = new UndoRedoLogResult(database);
		Session s = result.transactionalSession;
		Query qry = s.createQuery("from AuditTrail where transactionId = " + transactionID );		
		List<AuditTrail> trails = qry.list();
		return redoLog(result, trails);
	}

	@SuppressWarnings("unchecked")
	public static UndoRedoLogResult undoLog( Long transactionID, String database ) throws Exception{
		UndoRedoLogResult result = new UndoRedoLogResult(database);
		Session s = result.transactionalSession;
		Query qry = s.createQuery("from AuditTrail where transactionId = " + transactionID );		
		List<AuditTrail> trails = qry.list();
		return undoLog(result, trails);
	}
	
	public static UndoRedoLogResult undoLog( UndoRedoLogResult result, List<AuditTrail> trails ) throws Exception{
		List<AuditTrail> deletedTrails = new ArrayList<AuditTrail>();
		List<AuditTrail> insertedTrails = new ArrayList<AuditTrail>();
		List<AuditTrail> updatedTrails = new ArrayList<AuditTrail>();
				
		result.transactionalSession.beginTransaction();
		
		separateEntityList( trails, deletedTrails, insertedTrails, updatedTrails);
		
		prepareUndoDeletedTrails( result, deletedTrails );

		prepareUndoInsertedTrails( result, insertedTrails );
		
		prepareUndoUpdatedTrails( result, updatedTrails );
		
		return result;
	}
	
	public static UndoRedoLogResult redoLog( UndoRedoLogResult result, List<AuditTrail> trails ) throws Exception{
		List<AuditTrail> deletedTrails = new ArrayList<AuditTrail>();
		List<AuditTrail> insertedTrails = new ArrayList<AuditTrail>();
		List<AuditTrail> updatedTrails = new ArrayList<AuditTrail>();
		
		result.transactionalSession.beginTransaction();
		
		separateEntityList( trails, deletedTrails, insertedTrails, updatedTrails);		
		
		prepareRedoDeletedTrails( result, deletedTrails );

		prepareRedoInsertTrails( result, insertedTrails );
		
		prepareRedoUpdatedTrails( result, updatedTrails );
		
		return result;
	}

	
	private static void separateEntityList( List<AuditTrail> trails, List<AuditTrail> deletedTrails, List<AuditTrail> insertedTrails, List<AuditTrail> updatedTrails ){
		//First, we should separate the trails by the type of trail.
		for (AuditTrail auditTrail : trails) {
			if ( auditTrail.getType().equals(AuditTrail.OPERATION_TYPE_DELETE)  ){
				deletedTrails.add(auditTrail);
			}
			
			if ( auditTrail.getType().equals(AuditTrail.OPERATION_TYPE_INSERT) ){
				insertedTrails.add(auditTrail);
			}

			if ( auditTrail.getType().equals(AuditTrail.OPERATION_TYPE_UPDATE) ){
				updatedTrails.add(auditTrail);
			}		
		}
	}

	/**
	 * @param result
	 * @param insertedTrails
	 */
	private static void prepareRedoInsertTrails( UndoRedoLogResult result, List<AuditTrail> insertedTrails ){
		treatDeletedTrailsOnUndoOrInsertedTrailsOnRedo( result, insertedTrails, "REDO" );
	}

	private static void prepareUndoDeletedTrails( UndoRedoLogResult result, List<AuditTrail> deletedTrails ){
		treatDeletedTrailsOnUndoOrInsertedTrailsOnRedo( result, deletedTrails, "UNDO" );
	}

	private static void treatDeletedTrailsOnUndoOrInsertedTrailsOnRedo( UndoRedoLogResult result, List<AuditTrail> deletedTrails, String type ){
		Session session = result.transactionalSession;
		for (AuditTrail deletedAuditTrail : deletedTrails) {
			try {
				Class clazz = Class.forName(deletedAuditTrail.getEntityName());
				BaseEntity entityToInsert = (BaseEntity) clazz.newInstance();			
				HashMap<String, String> hashValues = new HashMap<String, String>();
				String fields = new String( deletedAuditTrail.getFieldNames() );
				String values = "";
				if ( type.equalsIgnoreCase( "UNDO" ) ){
					values = new String( deletedAuditTrail.getOldValues() );
				} else {
					values = new String( deletedAuditTrail.getNewValues() );
				}
				setFieldNamesAndValuesToHashMap( hashValues, fields, values );								
				ReflectUtil.populateBean( entityToInsert, hashValues );
				entityToInsert.setId(deletedAuditTrail.getRecordId());
				session.save(entityToInsert);
				result.getInsertedBaseEntities().add(entityToInsert);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}								
		}
	}

	/**
	 * @param result
	 * @param insertedTrails
	 */
	private static void prepareRedoDeletedTrails( UndoRedoLogResult result, List<AuditTrail> deletedTrails ){
		prepareUndoInsertedTrails( result, deletedTrails );
	}
	
	private static void prepareUndoInsertedTrails( UndoRedoLogResult result, List<AuditTrail> insertedTrails ){
		// Version two: gets all record from the same entity before dispatch the delete.
		// First: prepares a hashMap with all entityNames and his respective ids.
		HashMap<String, String> hash = new HashMap<String, String>();
		for (AuditTrail insertedAuditTrail : insertedTrails) {
			String entityName = insertedAuditTrail.getEntityName();
			if ( ! hash.containsKey( entityName ) ){
				hash.put(entityName, "" );
			}
			hash.put( entityName, (String)hash.get(entityName) + insertedAuditTrail.getRecordId() + "," );
		}

		Session session = result.transactionalSession;		
		Object[] entityNames = hash.keySet().toArray();
		for (int i = 0; i < entityNames.length; i++) {
			String entityName = (String)entityNames[i];
			String ids = (String)hash.get(entityName);
			ids = ids.substring(0, ids.length()-1);
			//
			Query qry = session.createQuery("from " + entityName + " where id in ( " + ids + ")" );		
			List list = qry.list();
			List<BaseEntity> baseEntities = list;
			for (BaseEntity baseEntity : baseEntities) {
				session.delete(baseEntity);
				result.getDeletedBaseEntities().add(baseEntity);
			}
		}				
	}
	
	
	/**
	 * @param result
	 * @param updatedTrails
	 */
	private static void prepareUndoUpdatedTrails( UndoRedoLogResult result, List<AuditTrail> updatedTrails ){
		treatUpdatedTrails(result, updatedTrails, "UNDO" );
	}
	
	private static void prepareRedoUpdatedTrails( UndoRedoLogResult result, List<AuditTrail> updatedTrails ){
		treatUpdatedTrails(result, updatedTrails, "REDO" );
	}

	private static void treatUpdatedTrails( UndoRedoLogResult result, List<AuditTrail> updatedTrails, String type ){
		Session session = result.transactionalSession;
		for (AuditTrail updatedAuditTrail : updatedTrails ) {
			try {
				Class clazz = Class.forName(updatedAuditTrail.getEntityName());
				BaseEntity entityToUpdate = (BaseEntity) session.get(clazz, updatedAuditTrail.getRecordId());			
				HashMap<String, String> hashValues = new HashMap<String, String>();
				String fields = new String( updatedAuditTrail.getFieldNames() );
				String values = "";
				if ( type.equalsIgnoreCase("UNDO") ){
					values = new String( updatedAuditTrail.getOldValues() );
				} else {
					values = new String( updatedAuditTrail.getNewValues() );
				}
				setFieldNamesAndValuesToHashMap( hashValues, fields, values );								
				ReflectUtil.populateBean( entityToUpdate, hashValues );
				session.save(entityToUpdate);
				result.getUpdatedBaseEntities().add(entityToUpdate);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}								
		}
	}
	
	
	private static void setFieldNamesAndValuesToHashMap( HashMap<String, String> hashValues, String fields, String values ){
		int indexFieldNames = fields.indexOf(":");
		int indexValues = values.indexOf(":");
		while( indexFieldNames != -1 ){
			Integer lengthFieldNames = new Integer(fields.substring(0, indexFieldNames));
			Integer lengthValues = new Integer(values.substring(0, indexValues));
			String fieldName = fields.substring(indexFieldNames + 1, indexFieldNames + 1 + lengthFieldNames );
			String value = values.substring(indexValues + 1, indexValues + 1 + lengthValues );
			hashValues.put(fieldName, value);
			fields = fields.substring(indexFieldNames + 1 + lengthFieldNames + 1, fields.length());
			values = values.substring(indexValues + 1 + lengthValues + 1, values.length());
			indexFieldNames = fields.indexOf(":");
			indexValues = values.indexOf(":");
		}
	}
}