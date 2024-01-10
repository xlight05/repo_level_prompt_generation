package br.com.dyad.infrastructure.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import br.com.dyad.client.GenericService;
import br.com.dyad.client.Navigator;
import br.com.dyad.infrastructure.entity.GroupAndUser;
import br.com.dyad.infrastructure.entity.GroupPermission;
import br.com.dyad.infrastructure.entity.NavigatorEntity;
import br.com.dyad.infrastructure.persistence.PersistenceUtil;

/**
 * @author DYAD
 * @see DyadService
 */
public class NavigatorService extends DyadService {

	public static Long administrator = -89999999999993L;
	private String database;
	
	/**
	 * @see br.com.dyad.infrastructure.service.DyadService#getServiceValue(java.util.HashMap)
	 */
	@Override
	public HashMap getServiceValue(HashMap params) throws Exception {
		//try {
			database = (String)params.get(GenericService.GET_DATABASE_FILE);  
			HashMap value = new HashMap();
			
			Object nav = GenericServiceImpl.servletContext.getAttribute("navigator");
			if (nav == null){
				nav = getMenu();
				GenericServiceImpl.servletContext.setAttribute("navigator", nav);
			}			
			
			value.put("navigator", (Navigator)nav);
			HashMap<Long, Long> groupPermissions = getGroupPermissions(params);
			value.put("groupPermission", groupPermissions);
			
			/*HashMap<Long, Long> groupPermissions = getGroupPermissions(params);
			Long admin = groupPermissions.get(NavigatorService.administrator);
			if ( admin != null && admin.equals(NavigatorService.administrator)){
				value.put("isAdmin", NavigatorService.administrator );
			} else {
				value.put("groupPermission", groupPermissions);
			}*/	
			Boolean getShortcuts = (Boolean)params.get("getShortcuts");
			
			if (getShortcuts != null && getShortcuts) {
				UserProfileService profileService = new UserProfileService();
				profileService.setHttpSession(getHttpSession());				
				List<Navigator> shortcuts = profileService.getShortcuts(getDatabase());
				
				Navigator shortcutRoot = new Navigator();
				shortcutRoot.setName("Atalhos do usuário");
				shortcutRoot.setId(0L);
				shortcutRoot.setSubmenu(shortcuts);
				
				List<Navigator> rootList = new ArrayList<Navigator>();
				rootList.add(shortcutRoot);
				
				Navigator dummyRoot = new Navigator();
				dummyRoot.setName("Atalhos do usuário");
				dummyRoot.setSubmenu(rootList);
				
				value.put("shortcuts", dummyRoot);
			}
			
			return value;
		//} catch (Exception e ){
		//	throw ClientServerException.createException(e);
		//}
	}

	/**
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public Navigator getMenu() throws Exception{
		Navigator ret = new Navigator();
		
		Session s = PersistenceUtil.getSession(database);
		Query qry = s.createQuery("from NavigatorEntity where id = -89999999999989"); /* Navigation */		
		List<NavigatorEntity> navigator = qry.list();
		
		if (navigator != null && navigator.size() > 0){			
			ret.setName( this.translate( navigator.get(0).getName() ) );
			ret.setProcess(navigator.get(0).getWindow());
			ret.setCssName(navigator.get(0).getCssName());
			ret.setId(navigator.get(0).getId());
			ret.setLicenseId(navigator.get(0).getLicenseId());
			addItems(navigator.get(0), ret);
			
			GenericServiceImpl.servletContext.setAttribute("navigator", navigator.get(0));
		}
		
		return ret;
	}
	
	/**
	 * @param source
	 * @param destiny
	 */
	private void addItems(NavigatorEntity source, Navigator destiny) {
		for (NavigatorEntity item : source.getSubmenu()) {
			Navigator temp = new Navigator();
			temp.setName( this.translate( item.getName() ) );		
			temp.setProcess(item.getWindow());
			temp.setCssName(item.getCssName());
			temp.setId(item.getId());
			temp.setLicenseId(item.getLicenseId());
			destiny.getSubmenu().add(temp);			
			addItems(item, temp);
		}
	}
	
	/**
	 * @param params
	 * @return
	 */
	private HashMap<Long, Long> getGroupPermissions( HashMap params ){
		Session s = PersistenceUtil.getSession(database); 
		Query qry = s.createQuery("from GroupAndUser where classId = '-89999999999945' and user.id = "+ (Long)params.get("userKey"));
		List<GroupAndUser> groupsAndUsers = qry.list();
		String groups = "";
		HashMap<Long, Long> permissionsMap = new HashMap<Long, Long>();
		
		for (Iterator iterator = groupsAndUsers.iterator(); iterator.hasNext();) {
			GroupAndUser groupAndUser = (GroupAndUser) iterator.next();
			if ( groupAndUser.getGroup().getId().equals(NavigatorService.administrator)){
				permissionsMap.put(NavigatorService.administrator, NavigatorService.administrator);
				return permissionsMap;
			}
			groups += groupAndUser.getGroup().getId() + ",";			
		}
		if ( groups.length() > 0 ){
			groups = groups.substring(0, groups.length() - 1 );
		}
		
		Query qryPermission = s.createQuery("from GroupPermission where classId = '-89999999999944' and group in ("+ groups +")"); /* GroupPermission */		
		List<GroupPermission> permissions = qryPermission.list();
		
		for (Iterator iterator = permissions.iterator(); iterator.hasNext();) {
			GroupPermission groupPermission = (GroupPermission) iterator.next();
			permissionsMap.put(groupPermission.getNavigatorEntity().getId(), groupPermission.getGroup().getId());
		}
		return permissionsMap;
	}

}
