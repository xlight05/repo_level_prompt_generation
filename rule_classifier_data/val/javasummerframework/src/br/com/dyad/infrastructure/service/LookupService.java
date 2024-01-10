package br.com.dyad.infrastructure.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.persistence.DiscriminatorValue;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.hibernate.Query;
import org.hibernate.Session;

import br.com.dyad.client.DataClass;
import br.com.dyad.client.GenericService;
import br.com.dyad.commons.data.DataList;
import br.com.dyad.commons.reflect.ReflectUtil;
import br.com.dyad.infrastructure.annotations.CacheClass;
import br.com.dyad.infrastructure.entity.BaseEntity;
import br.com.dyad.infrastructure.navigation.persistence.HibernateUtil;
import br.com.dyad.infrastructure.persistence.PersistenceUtil;
import br.com.dyad.infrastructure.persistence.cache.Cache;
import br.com.dyad.infrastructure.reflect.ObjectConverter;

public class LookupService extends DyadService{
	private Class clazz;
	public static String[] especialChars = {
		"á", "é", "í", "ó", "ú",
		"ã", "õ", "â", "ê", "ô",  
		"ç"
	};
	public static String[] standarChars = {
		"a", "e", "i", "o", "u",
		"a", "o", "a", "e", "o",  
		"c"
	};
	
	@SuppressWarnings("unchecked")
	@Override
	public HashMap getServiceValue(HashMap params) throws Exception {
		//try {
			String dbName = (String)params.get(GenericService.GET_DATABASE_FILE);
			String searchToken = (String)params.get("searchToken");
			String findExp = (String)params.get("findExpress");			
		
			HashMap ret = new HashMap();
			ret.put("searchToken", searchToken);
			Boolean isClass = (Boolean)params.get("isClassLookup");
			if ( isClass != null && isClass ){
				String dataClassName = (String)params.get("bean");
				HibernateUtil util = new HibernateUtil();						
				util.getDataClasses(getDatabase(), Class.forName(dataClassName));
				DataClass root = util.findClassParent(util.getClassTree(), Class.forName(dataClassName));
				
				if ( searchToken == null || searchToken.trim().length() == 0 ){
					ret.put("classes", root);
				} else {
					ArrayList<DataClass> allClasses = new ArrayList<DataClass>();
					allClasses.add(root);
					this.addChildrens(root, allClasses);
					
					ArrayList<DataClass> list = new ArrayList<DataClass>();
					for (Iterator iterator = allClasses.iterator(); iterator.hasNext();) {
						DataClass bean = (DataClass) iterator.next();
						Class beanName = Class.forName((String) bean.get("className"));
						String className = org.apache.commons.lang.ClassUtils.getShortCanonicalName(beanName);
						if ( searchToken == null || searchToken.trim().length() == 0 || org.apache.commons.lang.StringUtils.containsIgnoreCase(className, searchToken) ){						
							DataClass model = new DataClass();						
							DiscriminatorValue idValue = (DiscriminatorValue)beanName.getAnnotation(DiscriminatorValue.class);
							if ( idValue != null ){
								model.set("id", new Long(idValue.value()));
								model.set("lookupTemplate", className);
								model.setInheritLevel(bean.getInheritLevel());
								list.add(model);
							}
						}	
					}
					ret.put("classes", list);					
				}							
			} else {
				clazz = Class.forName((String)params.get("bean"));																					
				List beans = getBeans( dbName, searchToken, findExp );
				ret.put("bean", beans );						
			}									
			return ret;
		//} catch (Exception e) {
		//	throw ClientServerException.createException(e);
		//}
	}
	
	@SuppressWarnings("unchecked")
	private List getBeans( String database, String searchToken, String findExpressFromLookup ) throws Exception {
		//try{			
			List list = new ArrayList();
			
			if ( clazz.isAnnotationPresent( CacheClass.class ) ){
				DataList dataList = Cache.getInstance().getDataList(database, clazz);
				ObjectConverter objConverter = ObjectConverter.getInstance();
				List tempList = null;
				if ( searchToken == null || searchToken.length() == 0 || searchToken.trim().length() == 0 ){
					tempList = dataList.getList();
				} else {
					tempList = (List)CollectionUtils.select(dataList.getList(), new LookupServicePredicate(searchToken));					
				}				
				for (Iterator iterator = tempList.iterator(); iterator.hasNext();) {
					BaseEntity object = (BaseEntity) iterator.next();
					list.add(objConverter.getLookupModel(object));
				}
			} else {			
				Session session = PersistenceUtil.getSession(database);
				String findExpress = findExpressFromLookup != null ? findExpressFromLookup : 
					                 ( searchToken == null ? " " : (String)ReflectUtil.getMethodReturn(clazz, "getFindExpress", new Class[]{ String.class }, new Object[]{ searchToken }) );
				String strQuery = "from " + clazz.getName() + " where classId = '" + 
							   BaseEntity.getClassIdentifier(clazz) + "'" + 
							   findExpress; 
				Query query = session.createQuery(strQuery);
				query.setMaxResults(500);
				
				for(Object object : query.list()){
					ObjectConverter objConverter = ObjectConverter.getInstance();
					list.add(objConverter.getLookupModel(object));
				}
			}			
			return list;
		//} catch(Exception e) {
		//	throw ClientServerException.createException(e);
		//}			
	}
	
	private void addChildrens( DataClass parent, ArrayList list ){
		if ( parent != null ){
			ArrayList<DataClass> childrens = parent.getChildren();
			if ( childrens.size() > 0 ){
				list.addAll(childrens);
			}
			for (Iterator iterator = childrens.iterator(); iterator.hasNext();) {
				DataClass child = (DataClass) iterator.next();
				addChildrens(child, list);
			}
		}	
	}
	
	class LookupServicePredicate implements Predicate {
		String searchToken;
		
		public LookupServicePredicate( String searchToken ){
			this.searchToken = searchToken;
			this.searchToken = this.searchToken.toLowerCase();
			for (int i = 0; i < especialChars.length; i++) {
				this.searchToken = this.searchToken.replaceAll(especialChars[i], standarChars[i]);
			}
		}
		
		@Override
		public boolean evaluate(Object arg0) {
			String token2 = ((BaseEntity)arg0).toString();
			if ( token2 == null && this.searchToken == null ){
				return true;
			}
			if ( token2 == null || this.searchToken == null ){
				return false;
			}
			token2 = token2.toLowerCase();
			for (int i = 0; i < especialChars.length; i++) {
				token2 = token2.replaceAll(especialChars[i], standarChars[i]);
			}		
			return token2.indexOf(this.searchToken) != -1;
		}
	}
}