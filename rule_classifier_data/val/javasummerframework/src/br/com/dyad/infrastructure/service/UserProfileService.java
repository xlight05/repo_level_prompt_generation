package br.com.dyad.infrastructure.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import br.com.dyad.client.GenericService;
import br.com.dyad.client.Navigator;
import br.com.dyad.infrastructure.aspect.UserInfoAspect;
import br.com.dyad.infrastructure.entity.BaseEntity;
import br.com.dyad.infrastructure.entity.NavigatorEntity;
import br.com.dyad.infrastructure.entity.User;
import br.com.dyad.infrastructure.entity.UserProfile;
import br.com.dyad.infrastructure.entity.UserShortcut;
import br.com.dyad.infrastructure.persistence.PersistenceUtil;

public class UserProfileService extends DyadService{		
	private Long userKey = null;
	@SuppressWarnings("unchecked")
	@Override
	public HashMap getServiceValue(HashMap params) throws Exception {
		Boolean createUserShortcut = (Boolean)params.get("createUserShortcut");
		Boolean removeShortcut = (Boolean)params.get("removeShortcut");
		
		if ( createUserShortcut != null && createUserShortcut ){
			userKey = UserInfoAspect.getUserKey();//(Long)params.get("userKey");
			return addUserShortcut((String)params.get(GenericService.GET_DATABASE_FILE), params);
		} else if (removeShortcut != null && removeShortcut) { 
			Long windowId = (Long)params.get("windowId");
			removeShortcut(windowId);
			return null;
	    } else {
			HashMap ret = new HashMap();
			userKey = UserInfoAspect.getUserKey();//(Long)params.get("userKey");
			ret.put("shortcuts", getShortcuts( (String)params.get(GenericService.GET_DATABASE_FILE) ));			
			return ret;
		}		
	}
	
	@SuppressWarnings("unchecked")
	public List getShortcuts( String database ) throws Exception {			
		List<Navigator> list = new ArrayList<Navigator>();			
		Session session = PersistenceUtil.getSession(database);			
		String strQuery = "from UserShortcut where classId = '" + 
					   BaseEntity.getClassIdentifier(UserShortcut.class) + "'" +
					   		" and user = " + UserInfoAspect.getUserKey(); 
		Query query = session.createQuery(strQuery);
		query.setMaxResults(100);
		
		for(Object object : query.list()){
			UserShortcut userShortcut = (UserShortcut) object;
			NavigatorEntity ent = userShortcut.getNavigatorEntity();
			Navigator nav = new Navigator();
			nav.setCssName(ent == null ? null : ent.getCssName());
			nav.setId(ent.getId());
			nav.setName( this.translate( ent.getName() ) );
			nav.setProcess(ent.getWindow());			
			list.add(nav);				
		}
		
		return list;			
	}
	
	public HashMap addUserShortcut(String database, HashMap params) throws Exception{
		Long windowId = (Long)params.get("windowId");
		
		Session transactionalSession = PersistenceUtil.getSession(database);		
		transactionalSession.beginTransaction();

		UserShortcut ent = new UserShortcut();
		ent.createId(database);
		
		NavigatorEntity nav = null;
		List list = PersistenceUtil.executeHql(database, "from NavigatorEntity where id = " + windowId );		
		if ( list.size() > 0 ){
			nav = (NavigatorEntity)list.get(0);
		}
		ent.setNavigatorEntity(nav);
		
		User u = null;
		List list2 = PersistenceUtil.executeHql(database, "from User where id = " + userKey );		
		if ( list2.size() > 0 ){
			u = (User)list2.get(0);
		}
		ent.setUser(u);

		transactionalSession.save( ent );
		transactionalSession.getTransaction().commit();

		HashMap ret = new HashMap();
		ret.put("shortcutCreated", ent.getId());
		return ret;
	}
	
	/**
	 * Exclui o atalho de um usuário
	 * 
	 * @param windowId
	 */
	public void removeShortcut(Long windowId) {
		Session session = PersistenceUtil.getSession(getDatabase());
		session.beginTransaction();
		
		try {
			Query query = session.createQuery("from UserProfile where windowId = " + windowId + 
					" and user.id = " + UserInfoAspect.getUserKey());
			UserProfile profile = (UserProfile)query.uniqueResult();
			
			if (profile != null) {				
				session.delete(profile);
			}
			
			session.getTransaction().commit();
		} catch (Exception e) {
			session.getTransaction().rollback();
		}		
	}
}