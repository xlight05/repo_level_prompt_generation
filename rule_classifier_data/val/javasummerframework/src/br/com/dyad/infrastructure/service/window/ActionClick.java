package br.com.dyad.infrastructure.service.window;

import java.util.HashMap;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import br.com.dyad.client.GenericService;
import br.com.dyad.commons.data.DataList;
import br.com.dyad.commons.reflect.ReflectUtil;
import br.com.dyad.infrastructure.annotations.Authorization;
import br.com.dyad.infrastructure.aspect.UserInfoAspect;
import br.com.dyad.infrastructure.entity.BaseEntity;
import br.com.dyad.infrastructure.entity.User;
import br.com.dyad.infrastructure.entity.UserFieldValue;
import br.com.dyad.infrastructure.navigation.admin.security.SecurityUtil;
import br.com.dyad.infrastructure.persistence.DataListFactory;
import br.com.dyad.infrastructure.persistence.PersistenceUtil;
import br.com.dyad.infrastructure.service.SynchronizerService;
import br.com.dyad.infrastructure.widget.Action;
import br.com.dyad.infrastructure.widget.BaseAction;
import br.com.dyad.infrastructure.widget.Window;

public class ActionClick extends SynchronizerService {

	@Override
	public HashMap getServiceValue(HashMap params) throws Exception {
		HashMap resultMap = new HashMap();
		
		String serverWindowId = (String)params.get("serverWindowId");
		String serverActionId = (String)params.get("serverActionId");
		HashMap fieldValues = (HashMap)params.get("fieldValuesToSave");

		if ( serverWindowId == null || serverWindowId.equals("") ){
			throw new Exception("Sincronizer Service - Set field value: serverWindowId is empty.");
		}
		if ( serverActionId == null || serverActionId.equals("") ){
			throw new Exception("Sincronizer Service - Action Click: serverActionId is empty.");
		}				
		
		Window window = getWindowMap().get(serverWindowId);
		if ( window != null ){
			Action action = window.getActionByServerObjectId(serverActionId);
			if ( action != null ){
				if ( ! action.getEnabled() ){
					throw new Exception("Action " + action.getText()  + " is disabled.");
				}
				
				Long windowId = Window.getWindowId(window.getClass());
				
				if (windowId != null){
					String permission = "";
					
					//Encontra qual propriedade da classe está referenciando esta action
					for (java.lang.reflect.Field field : ReflectUtil.getClassFields(window.getClass(), true)){
						if (ReflectUtil.inheritsFrom(field.getType(), BaseAction.class)){
							Object fieldValue = ReflectUtil.getFieldValue(window, field.getName());
							
							if (fieldValue == action) {
								Authorization auth = (Authorization)field.getAnnotation(Authorization.class);
								if (auth != null) {									
									permission = auth.permissionFieldName();
								}
							}
						}
					}
					
					if (!permission.equals("") && !SecurityUtil.userHasPermission(UserInfoAspect.getDatabase(), UserInfoAspect.getUserKey(), windowId, permission)){
						throw new RuntimeException(translate("Access denied") + "!");
					}
				}
				action.onClick();
			}
		}
		
		try {
			if ( fieldValues != null && fieldValues.containsKey(GenericService.USER_KEY ) && fieldValues.containsKey("completeGridName") ){					
				Long userKey = (Long)fieldValues.get(GenericService.USER_KEY);
				String gridName = (String)fieldValues.get("completeGridName");
				String database = (String)getHttpSession().getAttribute(GenericService.GET_DATABASE_FILE);					


				String strQuery = "from UserFieldValue where classId = '"
					+ BaseEntity.getClassIdentifier(UserFieldValue.class)
					+ "'" + " and user = " + userKey
					+ " and completeFieldName = '" + gridName + "' ";

				/*Session session = PersistenceUtil.getPersistenceSession(database);					
			Query query = session.createQuery(strQuery);
			query.setMaxResults(100);*/

				DataList conf = DataListFactory.newDataList(database);
				conf.executeQuery( strQuery );
				conf.setCommitOnSave(true);

				Session sess = PersistenceUtil.getSession(getDatabase());
				Query qry = sess.createQuery(strQuery);											
				UserFieldValue userFieldValue = (UserFieldValue)qry.uniqueResult();

				//if ( ! conf.getList().isEmpty() ){
				if (userFieldValue != null){
					/*UserFieldValue userFieldValue = (UserFieldValue) conf.getList().get(0);
				userFieldValue.setFieldValues(PersistenceUtil.convertHashMapToByteArray(fieldValues));
				conf.save( userFieldValue );*/
					sess.beginTransaction();
					try{							
						userFieldValue.setFieldValues(PersistenceUtil.convertHashMapToByteArray(fieldValues));
						sess.merge( userFieldValue );
						sess.getTransaction().commit();
					} catch(Exception e) {
						sess.getTransaction().rollback();
						e.printStackTrace();
					}
				} else {
					UserFieldValue ent = (UserFieldValue) conf.newEntity(database, UserFieldValue.class.getName(), null);
					ent.createId(database);

					//TODO guardar o usuario logado de alguma forma
					User u = null;
					List list = PersistenceUtil.executeHql(database, "from User where id = " + userKey );		
					if ( list.size() > 0 ){
						u = (User)list.get(0);
					}

					ent.setUser(u);

					ent.setCompleteFieldName(gridName);
					ent.setFieldValues(PersistenceUtil.convertHashMapToByteArray(fieldValues));

					conf.save( ent );
				}
			}
		} catch( Exception e ){
			e.printStackTrace();
			//TODO O Schema Update dá erro quando não existe a tabela SysUserProfile.
		}
		
		System.out.println("Executou o click da action no servidor!");
		resultMap.put("serverWindowId", serverWindowId );
		resultMap.put("clientSincronizerMap", window.toClientSynchronizer() );
		
		return resultMap;
	}
	
}
