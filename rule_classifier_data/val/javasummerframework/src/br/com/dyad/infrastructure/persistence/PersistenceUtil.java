package br.com.dyad.infrastructure.persistence;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

import javassist.Modifier;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.mapping.PersistentClass;

import br.com.dyad.commons.reflect.ReflectUtil;
import br.com.dyad.infrastructure.entity.BaseEntity;

public class PersistenceUtil {
	
	public static Hashtable<String, DatabaseConfig> databases = new Hashtable<String, DatabaseConfig>();
	
	public static DatabaseConfig getDatabaseConfig(String configName) {
		if (databases.get(configName) == null){
			databases.put(configName, DatabaseConnection.connect(configName, false));			
		}
		
		return (DatabaseConfig)databases.get(configName);
	}
	
	public static Session getSession(String database) throws HibernateException {	
		DatabaseConfig dbConfig = getDatabaseConfig(database);
		return dbConfig.getSessionFactory().openSession();
	}
	
	public static DatabaseConfig getDatabaseConfig(SessionFactory factory) {
		for (DatabaseConfig dbConf : databases.values()){
			if (dbConf.getSessionFactory().equals(factory)){
				return dbConf;
			}
		}
		
		return null;
	}

	@SuppressWarnings("unchecked")
	public static List<Class> getAnnotatedClasses(String database) throws Exception{
		DatabaseConfig dbConfig = getDatabaseConfig(database);
		List<Class> retorno = new ArrayList<Class>();
				
		//try {
			for (Iterator iterator = dbConfig.getConf().getClassMappings(); iterator.hasNext();) {
				PersistentClass classRef = (PersistentClass) iterator.next();
				String className = classRef.getEntityName();
				
				/*
				String[] items = StringUtils.split(className, ".");
				
				if (items != null && items.length > 0 ){
					className = items[items.length - 1];
				}
				
				retorno.add(className);
				*/
				
				retorno.add(Class.forName(className));
			}
		//} catch (Exception e) {
			//throw AppException.createException(e);
		//}		
		
		return retorno;
	}
	
	public static void setOldValues(BaseEntity entity) throws Exception {
		entity.getOldValues().clear();
		
		for(Field field : ReflectUtil.getClassFields(entity.getClass(), true)){
			if (! Modifier.isTransient(field.getModifiers())){
				Object value = ReflectUtil.getFieldValue(entity, field.getName());
				entity.getOldValues().put(field.getName(), value);
			}
		}
	}
	
	public static List executeHql(Session session, String hql) {
		boolean startedHere = session.getTransaction() == null || !session.getTransaction().isActive();
		if (startedHere){
			session.beginTransaction();
		}
		try {
			Query query = session.createQuery(hql);						
			List list = query.list();			
			if (startedHere){
				session.getTransaction().commit();
			}
			
			return list;
		} catch (Exception e) {
			if (startedHere){
				session.getTransaction().rollback();
			}
			throw new RuntimeException(e);
		}				
	}
	
	public static List executeHql(String database, String hql) {
		Session sess = getSession(database);
		return executeHql(sess, hql);
	}

	public static void executeNativeSql(String script, String database ) throws Exception{
		if (script != null) {
			Session s = PersistenceUtil.getSession(database);
			s.beginTransaction();
			try {
				Query qy = s.createSQLQuery(script);
				qy.executeUpdate();
				s.getTransaction().commit();
			} catch (Exception e) {
				s.getTransaction().rollback();
				throw e;
			}	
		}	
	}
	
	public static String getClassTableName(String database, Class clazz) {
		DatabaseConfig dbConfig = getDatabaseConfig(database);
		return getClassTableName(dbConfig.getConf(), clazz);
	}
	
	public static String getClassTableName(AnnotationConfiguration hibernateConf, Class clazz) {
		PersistentClass persistentClass = hibernateConf.getClassMapping(clazz.getName());
		return persistentClass.getTable().getName();
	}
	
	public static String getColumnName(String database, Class clazz, Field property) {
		DatabaseConfig dbConfig = getDatabaseConfig(database);
		return getColumnName(dbConfig.getConf(), clazz, property);
	}	
	
	public static String getColumnName(String database, Class clazz, String fieldName) {
		return getColumnName(database, clazz, ReflectUtil.getDeclaredField(clazz, fieldName));
	}
	
	public static String getColumnName(AnnotationConfiguration hibernateConf, Class clazz, String fieldName) {
		return getColumnName(hibernateConf, clazz, ReflectUtil.getDeclaredField(clazz, fieldName));
	}
	
	public static String getColumnName(AnnotationConfiguration hibernateConf, Class clazz, Field property) {
		PersistentClass persistentClass = hibernateConf.getClassMapping(clazz.getName());
		org.hibernate.mapping.Property prop = persistentClass.getProperty(property.getName());
		org.hibernate.mapping.Column column = (org.hibernate.mapping.Column)prop.getValue().getColumnIterator().next();
						
		return column.getName().toLowerCase();
	}
	
	@SuppressWarnings("unchecked")
	public static byte[] convertHashMapToByteArray( HashMap map ){
		try {
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
	        ObjectOutputStream oos = new ObjectOutputStream(bos);
	
	        oos.writeObject(map);
	        oos.flush();
	        oos.close();
	        bos.close();
	
	        byte[] data = bos.toByteArray();
	        return data;
		} catch ( Exception ex ){
			ex.printStackTrace();
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public static HashMap convertByteArrayToHashMap( byte[] blob){
		if (blob == null){
			return new HashMap();
		}
		
		try {
			ByteArrayInputStream bais = new ByteArrayInputStream(blob);
			ObjectInputStream ins = new ObjectInputStream(bais);
			HashMap map = (HashMap) ins.readObject();
			ins.close();
			return map;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static List loadAll(Class clazz, Session session) throws Exception {
		Query query = session.createQuery("from " + clazz.getName() + " where classId = :classId");
		query.setString("classId", BaseEntity.getClassIdentifier(clazz));
		return query.list();
	}
	
	public static List loadBean(Class clazz, Long id, Session session) throws Exception {
		Query query = session.createQuery("from " + clazz.getName() + " where classId = :classId" +
				" and id = :id");
		query.setString("classId", BaseEntity.getClassIdentifier(clazz));
		query.setLong("id", id);
		return query.list();
	}
}