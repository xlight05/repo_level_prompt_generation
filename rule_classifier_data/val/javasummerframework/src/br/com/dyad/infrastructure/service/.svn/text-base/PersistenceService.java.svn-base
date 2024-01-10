package br.com.dyad.infrastructure.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.hibernate.Session;

import br.com.dyad.client.DyadBaseModel;
import br.com.dyad.client.GenericService;
import br.com.dyad.commons.reflect.ReflectUtil;
import br.com.dyad.infrastructure.persistence.PersistenceUtil;
import br.com.dyad.infrastructure.reflect.ObjectConverter;

public class PersistenceService extends DyadService {

	@SuppressWarnings("unused")
	private String className;
	private String database;
	
	@SuppressWarnings("unchecked")
	@Override
	public HashMap getServiceValue(HashMap params) throws Exception {
		//try{						
			this.database = (String)params.get(GenericService.GET_DATABASE_FILE);
			this.className = (String)params.get("className");	
			List<DyadBaseModel> beans = (List<DyadBaseModel>)params.get("beans");
			List<DyadBaseModel> savedBeans = new ArrayList<DyadBaseModel>();
			HashMap map = new HashMap();			

			for (DyadBaseModel model : beans) {
				savedBeans.add(saveBean(model));
			}
			
			map.put("beans", savedBeans);
			return map;
		//} catch(Exception e){
		//	throw ClientServerException.createException(e);
		//}
	}
	
	protected DyadBaseModel saveBean(DyadBaseModel model) throws Exception {
		//try{			
			Class clazz = Class.forName(this.className);		
			Object obj = null;//clazz.newInstance();
			
			for (String name : model.getPropertyNames()){				
				if (model.get(name) instanceof com.extjs.gxt.ui.client.widget.form.Time){
					com.extjs.gxt.ui.client.widget.form.Time prop = (com.extjs.gxt.ui.client.widget.form.Time)model.get(name);
					Calendar date = Calendar.getInstance();
					date.setTime(new Date());
					date.set(Calendar.HOUR, (Integer)prop.get("hour"));
					date.set(Calendar.MINUTE, (Integer)prop.get("minutes"));
					
					model.set(name, date.getTime());
				}
			}
			
			obj = ObjectConverter.getInstance().getBeanFromModel(model);
			//ReflectUtil.populateBean(obj, model.getBeanProperties(false));
			
			Session s = PersistenceUtil.getSession(database);
			s.beginTransaction();
			try{				
				if (ReflectUtil.getFieldValue(obj, "id") == null){
					ReflectUtil.getMethodReturn(obj, "createId");
					obj = s.save(obj);
				}else{					
					obj = s.merge(obj);				
				}
				s.getTransaction().commit();
				return ObjectConverter.getInstance().getModel(obj);
			}catch(Exception e){
				s.getTransaction().rollback();
				throw e;
			}
		//} catch(Exception e){
		//	throw ClientServerException.createException(e);
		//}
	}
	
}