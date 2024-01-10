package br.com.dyad.infrastructure.navigation.admin.security;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.hibernate.FetchMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import br.com.dyad.client.DyadBaseModel;
import br.com.dyad.commons.reflect.ReflectUtil;
import br.com.dyad.infrastructure.aspect.UserInfoAspect;
import br.com.dyad.infrastructure.entity.BaseEntity;
import br.com.dyad.infrastructure.entity.GroupAuthorization;
import br.com.dyad.infrastructure.entity.ProductLicense;
import br.com.dyad.infrastructure.entity.Reference;
import br.com.dyad.infrastructure.entity.User;
import br.com.dyad.infrastructure.entity.UserGroup;
import br.com.dyad.infrastructure.persistence.PersistenceUtil;

public class SecurityUtil {
	
	public static Long administrator = -89999999999993L;	
	
	public static boolean isUserAdministrator( User user ){		
		//TODO verificar se o usuario é do grupo administrador
		return true;		
	}
	
	@SuppressWarnings("unchecked")
	public static ArrayList<DyadBaseModel> getLicensesByUser( Long userId, String database ) throws Exception{
		ArrayList<DyadBaseModel> licenses = new ArrayList<DyadBaseModel>();
		
		List<Reference> list = (List<Reference>)PersistenceUtil.executeHql(database, 
				"from Reference where classId = '" + BaseEntity.getClassIdentifier(ProductLicense.class) + "'" + 
				" and canGenerate = true"); 
		
		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			Reference reference = (Reference) iterator.next();
			if ( SecurityUtil.userHasPermission(ProductLicense.class, reference.getName()) ){			
				DyadBaseModel baseModel1 = new DyadBaseModel();
				baseModel1.set("id", reference.getId());
				baseModel1.set("name", reference.getName());
				licenses.add(baseModel1);
			}
		}
		
		DyadBaseModel noLicense = new DyadBaseModel();
		noLicense.set("id", null);
		noLicense.set("name", "No License");
		licenses.add(noLicense);
		
		return licenses;
	}
	
	@SuppressWarnings("unchecked")
	public static ArrayList<DyadBaseModel> getLicensesByUserToCreateWindow( Long userId, String database ) throws Exception{
		ArrayList<DyadBaseModel> licenses = new ArrayList<DyadBaseModel>();
		
		List<Reference> list = (List<Reference>)PersistenceUtil.executeHql(database, 
				"from Reference where classId = '" + BaseEntity.getClassIdentifier(ProductLicense.class) + "'" + 
				" and canGenerate = true"); 
		
		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			Reference reference = (Reference) iterator.next();
			if ( SecurityUtil.userHasPermission(ProductLicense.class, reference.getName(), database, userId ) ){			
				DyadBaseModel baseModel1 = new DyadBaseModel();
				baseModel1.set("id", reference.getId());
				baseModel1.set("name", reference.getName());
				licenses.add(baseModel1);
			}
		}
		
		DyadBaseModel noLicense = new DyadBaseModel();
		noLicense.set("id", null);
		noLicense.set("name", "No License");
		licenses.add(noLicense);
		
		return licenses;
	}
	
	private HashMap<Long, Object> permissions = new HashMap<Long, Object>();
	public static String ITEM_SEPARATOR = ",";
	public static String PERMISSION_SEPARATOR = "$##_@@$";
	
	public static Boolean groupHasPermission(String database, Long groupKey, String classKey, String permission) throws Exception {
		permission = PERMISSION_SEPARATOR + permission + PERMISSION_SEPARATOR;
		
		Session sess = PersistenceUtil.getSession(database);
		sess.beginTransaction();
		
		try {			
			GroupAuthorization auth = (GroupAuthorization)sess.createCriteria(GroupAuthorization.class)
										.createAlias("userGroup", "group")
										.add(Restrictions.eq("classId", BaseEntity.getClassIdentifier(GroupAuthorization.class)))
										.add(Restrictions.eq("classKey", classKey))
										.add(Restrictions.eq("userGroup.id", groupKey))
										.add(Restrictions.or(Restrictions.like("permissionString", permission, MatchMode.ANYWHERE), Restrictions.eq("group.admin", true)) )			
										.uniqueResult();
			
			return auth != null;
		} finally {
			sess.getTransaction().rollback();
		}
		
		
	}
	
	private static boolean userIsAdmin(String database, Long userKey) {
		
		StringBuffer hql = new StringBuffer();
		hql.append(" select gau.id                ");
		hql.append(" from GroupAndUser as gau     ");  
		hql.append(" where                        ");
		hql.append("  gau.user.id = :userKey      ");
		hql.append("  and gau.group.admin = true  ");
		
		Session sess = PersistenceUtil.getSession(database);
		sess.beginTransaction();
		
		try {
			Query query = sess.createQuery(hql.toString());
			query.setParameter("userKey", userKey);
			
			List list = query.list();
			return list != null && list.size() > 0;
		} finally {
			sess.getTransaction().rollback();
		}
	}
	
	public static Boolean userHasPermission(Class entity, String permission) throws Exception {		
		Long classKey = Long.parseLong(BaseEntity.getClassIdentifier(entity));
		return userHasPermission(UserInfoAspect.getDatabase(), UserInfoAspect.getUserKey(), classKey, permission);
	}
	
	public static Boolean userHasPermission(Class entity, String permission, String database, Long userKey ) throws Exception {		
		Long classKey = Long.parseLong(BaseEntity.getClassIdentifier(entity));
		return userHasPermission(database, userKey, classKey, permission);
	}
	
	public static Boolean userHasPermission(String database, Long userKey, Long classKey, String permission, boolean checkAdmin) throws Exception {
		if (checkAdmin && userIsAdmin(database, userKey)){
			return true;
		} else {			
			permission = PERMISSION_SEPARATOR + permission + PERMISSION_SEPARATOR;
			
			StringBuffer hql = new StringBuffer();
			/*hql.append(" select gau.id                                           ");
			hql.append(" from GroupAndUser as gau                                ");
			hql.append(" ,GroupAuthorization as auth                             ");  
			hql.append(" where                                                   ");
			hql.append("  gau.user.id = :userKey                                 ");
			hql.append("  and auth.userGroup = gau.group                         ");
			hql.append("  and (                                                  "); 		  
			hql.append("       (                                                 ");
			hql.append("        auth.classKey = :classKey                        ");
			hql.append("        and auth.permissionString like :permissionString ");
			hql.append("       )                                                 ");
			hql.append("       or gau.group.admin = true                         ");
			hql.append("      )                                                  ");*/
			
			hql.append(" select gau.id                                     ");
			hql.append(" from GroupAndUser as gau                          ");
			hql.append(" ,GroupAuthorization as auth                       ");  
			hql.append(" where                                             ");
			hql.append("  gau.user.id = :userKey                           ");
			hql.append("  and auth.userGroup = gau.group                   ");
			hql.append("  and auth.classKey = :classKey                    ");
			hql.append("  and auth.permissionString like :permissionString ");
			
			Session sess = PersistenceUtil.getSession(database);
			sess.beginTransaction();
			
			try {
				Query query = sess.createQuery(hql.toString());
				query.setParameter("userKey", userKey);
				query.setParameter("classKey", classKey);
				query.setParameter("permissionString", "%" + permission + "%");
				
				List list = query.list();
				return list != null && list.size() > 0;
			} finally {
				sess.getTransaction().rollback();
			}
		}		
	}
	
	public static Boolean userHasPermission(String database, Long userKey, Long classKey, String permission) throws Exception {
		return userHasPermission(database, userKey, classKey, permission, true);		
	}
	
	public static Object convertPermission(String database, GroupAuthorization groupAuthorization) throws Exception{
		Class permissionClass = CreatePermissionBean.getPermissionClass(database);
		Object result = permissionClass.newInstance();
		
		ReflectUtil.setFieldValue(result, "userGroup", groupAuthorization.getUserGroup());
		ReflectUtil.setFieldValue(result, "classKey", groupAuthorization.getClassKey());
		
		String permissionString = groupAuthorization.getPermissionString();
		if (permissionString != null && !permissionString.equals("")){
			String[] items = StringUtils.split(permissionString, ITEM_SEPARATOR);
			for (String string : items) {
				if (!string.equals("")){
					string = StringUtils.replace(string, PERMISSION_SEPARATOR, "");
					string = StringUtils.uncapitalize(string);
					ReflectUtil.setFieldValue(result, string, true);
				}
			}
		}
				
		return result;
	}
	
	public static void createPermissionBean(BaseEntity object, String database) {
		UserGroup userGroup = (UserGroup)ReflectUtil.getFieldValue(object, "userGroup");;
		Long classKey = (Long)ReflectUtil.getFieldValue(object, "classKey");
		Session sess = PersistenceUtil.getSession(database);  
		
		sess.beginTransaction();
		try{			
			Query query = sess.createQuery("from GroupAuthorization where userGroup.id = " + userGroup.getId() + " and classKey = " + classKey);
			GroupAuthorization data = (GroupAuthorization)query.uniqueResult();
			
			if (data == null){				
				UserGroup oldUserGroup = (UserGroup)object.getOldValue("userGroup");
				Long groupId = oldUserGroup == null ? userGroup.getId() : oldUserGroup.getId();
				Long oldClassKey = object.getOldValue("classKey") == null ? classKey : (Long)object.getOldValue("classKey"); 
				query = sess.createQuery("from GroupAuthorization where userGroup.id = " + groupId + " and classKey = " + oldClassKey);
				data = (GroupAuthorization)query.uniqueResult();				
			}			
			
			if (data == null){
				data = new GroupAuthorization();
				data.createId(database);
			}
			
			data.setClassId(BaseEntity.getClassIdentifier(GroupAuthorization.class));
			data.setClassKey(classKey);
			data.setUserGroup(userGroup);
			
			String permissionString = "";
			for(Field field : ReflectUtil.getClassFields(object.getClass(), false)){
				if (ReflectUtil.inheritsFrom(field.getType(), Boolean.class)){
					Boolean fieldValue = (Boolean)ReflectUtil.getFieldValue(object, field.getName());
					if (fieldValue != null && fieldValue == true){
						permissionString += ITEM_SEPARATOR + PERMISSION_SEPARATOR + field.getName() + PERMISSION_SEPARATOR;
					}
				}
			}
			
			data.setPermissionString(permissionString.length() > 0 ? permissionString.substring(1) : "");
			
			sess.save(data);
			sess.getTransaction().commit();
		} catch(Exception e) {
			sess.getTransaction().rollback();
			throw new RuntimeException(e);
		}
	}
	
	public static List loadPermissions(String database) {
		List permissions = new ArrayList();
		
		Session sess = PersistenceUtil.getSession(database);
		sess.beginTransaction();
		try {
			List<GroupAuthorization> list = sess.createCriteria(GroupAuthorization.class)
												.setFetchMode("userGroup", FetchMode.JOIN)
												.add(Restrictions.eq("classId", BaseEntity.getClassIdentifier(GroupAuthorization.class)))
												.list();
			
			for (GroupAuthorization groupAuthorization : list) {
				permissions.add(convertPermission(database, groupAuthorization));
			}
			
			sess.getTransaction().commit();
		} catch (Exception e) {
			sess.getTransaction().rollback();
			throw new RuntimeException(e);
		}
		
		return permissions;
	}
	
	public static boolean userCancustomizeLicense(String database, Long userKey, Long licenseId) {
		Session sess = PersistenceUtil.getSession(database);
		sess.beginTransaction();
		
		try {
			sess.beginTransaction();
			ProductLicense license = (ProductLicense)sess.get(ProductLicense.class, licenseId);
			Long classKey = Long.parseLong(BaseEntity.getClassIdentifier(ProductLicense.class));
			
			return userHasPermission(database, userKey, classKey, license.getName(), false); 
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			sess.getTransaction().rollback();
		}
	}
}
