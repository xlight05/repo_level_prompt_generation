package br.com.dyad.infrastructure.navigation.admin.security;

import java.util.List;
import javax.servlet.http.HttpSession;
import org.hibernate.Session;

import br.com.dyad.commons.reflect.ReflectUtil;
import br.com.dyad.infrastructure.entity.AbstractEntity;
import br.com.dyad.infrastructure.entity.BaseEntity;
import br.com.dyad.infrastructure.navigation.GenericEntityBeanWindow;
import br.com.dyad.infrastructure.persistence.PersistenceUtil;
import br.com.dyad.infrastructure.widget.Action;
import br.com.dyad.infrastructure.widget.DyadEvents;
import br.com.dyad.infrastructure.widget.WidgetListener;
import br.com.dyad.infrastructure.widget.field.Field;
import br.com.dyad.infrastructure.widget.field.FieldBoolean;
import br.com.dyad.infrastructure.widget.grid.DataGrid;

public class PermissionWindow extends GenericEntityBeanWindow {	
	
	public PermissionWindow(HttpSession httpSession) {
		super(httpSession);
	}
	
	Action replicateAction = new Action(this, "Replicate on Children"){
		@Override
		public void onClick() throws Exception {
			replicateOnChildren();
		}		
	};
	
	public void replicateOnChildren() {
		DataGrid grid = (DataGrid)this.getGrids().get(0);
		AbstractEntity entity = grid.getCurrentEntity();
		
	}

	@Override
	public void defineWindow() throws Exception {
		Class permissionClass = CreatePermissionBean.getPermissionClass(getDatabase());
		setBeanName(permissionClass.getName());
		List temp = SecurityUtil.loadPermissions(getDatabase());		
		
		this.dataList.setList(temp);			
		super.defineWindow();
		
		this.grid.addWidgetListener(DyadEvents.onBeforePost, new WidgetListener(){

			@Override
			public void handleEvent(Object sender) throws Exception {
				DataGrid dataGrid = (DataGrid)sender;
				AbstractEntity entity = dataGrid.getCurrentEntity();
				SecurityUtil.createPermissionBean((BaseEntity)entity, getDatabase());
			}
			
		}, false);
		
		this.grid.addWidgetListener(DyadEvents.onBeforeDelete, new WidgetListener(){

			@Override
			public void handleEvent(Object sender) throws Exception {
				DataGrid dataGrid = (DataGrid)sender;
				AbstractEntity entity = dataGrid.getCurrentEntity();
				deletePermissionBean(entity);
			}			
			
		}, false);		
	}	
	
	private void deletePermissionBean(AbstractEntity entity) {
		Session sess = PersistenceUtil.getSession(getDatabase());  		
		sess.beginTransaction();
		try{									
			sess.delete(entity);
			sess.getTransaction().commit();
		} catch(Exception e) {
			sess.getTransaction().rollback();
			throw new RuntimeException(e);
		}
	}	
	
	public void onAfterDefineWindow() throws Exception{
		super.onAfterDefineWindow();
		
		Class permissionClass = CreatePermissionBean.getPermissionClass(getDatabase());
		
		for (Field field : this.grid.getFields()){
			if (field instanceof FieldBoolean){
				String label = (String)ReflectUtil.getMethodReturn(permissionClass, "___getFieldLabel___", new Object[]{field.getName()});
				field.setLabel(label);
			}
		}
	}

}