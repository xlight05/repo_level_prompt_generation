package br.com.dyad.infrastructure.entity;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.swing.JOptionPane;

import org.hibernate.Query;
import org.hibernate.Session;

import br.com.dyad.client.AppException;
import br.com.dyad.infrastructure.persistence.PersistenceUtil;

/**
 * This class implements politic of key generating 
 * @author Danilo
 * 
 */
public class KeyGenerator {
	
	private Session session;
	private String database;

	protected KeyGenerator(String database){
		this.database = database;
		session = PersistenceUtil.getSession(database);
		keyRanges = getKeyRanges();
	}
	/**
	 * Singleton
	 */
	private static HashMap<String,KeyGenerator> keyGenerator = new HashMap<String,KeyGenerator>();
		
	/**
	 * Return a unique instance	
	 * @return
	 */
	public static KeyGenerator getInstance(String database){
		if (!keyGenerator.containsKey(database)){
			keyGenerator.put(database, new KeyGenerator(database));
		}
		
		return keyGenerator.get(database);
	}
	
	private List<KeyRange> keyRanges;
	
	public static Long KEY_RANGE_NO_LICENSE  = -89999999999997L /* No licensed key range */; 
	public static Long KEY_RANGE_PRODUCT_KEY = -89999999999996L /* Product licensed key range */;
	public static Long KEY_RANGE_CUSTOM_KEY  = -89999999999995L /* Customization licensed key range */;
	
	public static Long DYAD_INFRASTRUCTURE_LICENSE = -90000000000000L;
	public static Long CUSTOM_LICENSE              = -89999999999999L;
	public static Long DYAD_PRODUCTS_LICENSE       = -89999999999998L;
	
	/**
	 * 
	 * @return Long key
	 * @throws Exception 
	 * @throws InterruptedException 
	 */
	public Long generateKey( Long rangeId ) throws Exception{
		KeyRange currentLicense = getKeyRangeById( rangeId != null ? rangeId : KEY_RANGE_NO_LICENSE );
		Long key = null;
		
		if ( currentLicense != null ){
			if ( ! currentLicense.getKeyCacheReady() ){
				//System.out.println("Prepara cache de chaves");
				currentLicense.setCacheLastGeneretedKey( currentLicense.getLastGeneratedKey());
				currentLicense.setLastGeneratedKey( currentLicense.getLastGeneratedKey() + currentLicense.getRangeToKey() );
				session.beginTransaction();
				try{
					session.save(currentLicense);
					session.getTransaction().commit();
				}catch(Exception e){
					session.getTransaction().rollback();
					throw e;
				}
				currentLicense.setKeyCacheReady(true);
			}
			
			currentLicense.setCacheLastGeneretedKey(currentLicense.getCacheLastGeneretedKey() + 1);
			
			if ( currentLicense.getCacheLastGeneretedKey() <= currentLicense.getLastGeneratedKey() ){				
				key = currentLicense.getCacheLastGeneretedKey();
				//System.out.println("Usa cache de chaves");
			} else {
				//System.out.println("Pega novas chaves");
				currentLicense.setCacheLastGeneretedKey( currentLicense.getLastGeneratedKey());
				currentLicense.setLastGeneratedKey( currentLicense.getLastGeneratedKey() + currentLicense.getRangeToKey() );				
				
				session.beginTransaction();
				try{
					session.save(currentLicense);
					session.getTransaction().commit();
				}catch(Exception e){
					session.getTransaction().rollback();
					throw e;
				}
				
				currentLicense.setKeyCacheReady(true);
				
				currentLicense.setCacheLastGeneretedKey(currentLicense.getCacheLastGeneretedKey() + 1);
				key = currentLicense.getCacheLastGeneretedKey();
			}			
		}
		return key;
	}
	
	/**
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private List<KeyRange> getKeyRanges(){	
		//System.out.println("Deu o select...");				
		Query query = session.createQuery("from KeyRange");
		try{
			
			List<KeyRange> keyRanges = query.list();
			return keyRanges;		
		} catch(Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
		return null;
	}
	
	/**
	 * 
	 * @param key
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private KeyRange getKeyRangeById( Long key ){
		for (Iterator iterator = keyRanges.iterator(); iterator.hasNext();) {
			KeyRange keyRange = (KeyRange) iterator.next();
			if ( keyRange.getId().equals(key) ){
				return keyRange; 
			}
		}
		return null;
	}	
}