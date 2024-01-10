package br.com.dyad.infrastructure.widget.grid;

import java.lang.reflect.Modifier;

import br.com.dyad.client.AppException;
import br.com.dyad.commons.data.DataList;
import br.com.dyad.commons.reflect.ReflectUtil;
import br.com.dyad.infrastructure.annotations.SendToClient;
import br.com.dyad.infrastructure.entity.AbstractEntity;
import br.com.dyad.infrastructure.widget.Window;

public abstract class DataContainer extends Grid {

	protected DataList dataList;
	@SendToClient
	protected String beanName;
	private Class classReference;	

	public DataContainer(Window window, String name) throws Exception {
		super(window, name);
	}
		
	public Class getClassReference() {
		return classReference;
	}

	public void setClassReference(Class classReference) {
		this.classReference = classReference;
	}

	public String getBeanName() {
		return beanName;
	}
	public void setBeanName(String beanName) {
		this.beanName = beanName;
		try {
			this.classReference = Class.forName(beanName);
		} catch (ClassNotFoundException e) {
			throw new AppException(e.getMessage());
		}
	}
	
	public DataList getDataList() {
		return dataList;
	}
	public void setDataList(DataList dataList) {		
		this.dataList = dataList;
	}

	@SuppressWarnings("unchecked")
	private void defineFieldsBasedInBean() throws Exception {
		if ( this.getBeanName().equals("")){
			throw new Exception( "This grid cannot be its own detail." );
		}
		
		Class clazz = Class.forName(this.getBeanName());

		if ( Modifier.isAbstract( clazz.getModifiers() ) ){
			throw new Exception( "Bean name cannot be a abstract bean. BeanName used " + this.getBeanName() );
		}
		
		Object object = clazz.newInstance();
		ReflectUtil.getMethodReturn(object, "defineFields");	
	}	
	
	public void defineFieldsAndDefineGrid() throws Exception {		
		this.defineFieldsBasedInBean();
		this.defineGrid();
	}	
	
	public void defineField(String propName, Object...objects){
		AbstractEntity.repositoryMetaField.setProps(propName, objects);
	}
}
