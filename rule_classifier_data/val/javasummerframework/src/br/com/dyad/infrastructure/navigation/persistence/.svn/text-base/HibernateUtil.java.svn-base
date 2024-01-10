package br.com.dyad.infrastructure.navigation.persistence;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.persistence.DiscriminatorValue;

import br.com.dyad.client.DataClass;
import br.com.dyad.client.Navigator;
import br.com.dyad.commons.reflect.ReflectUtil;
import br.com.dyad.infrastructure.entity.BaseEntity;
import br.com.dyad.infrastructure.persistence.PersistenceUtil;

public class HibernateUtil {
	//-- Ãrvore de produto
	private ArrayList<Navigator> tree = new ArrayList<Navigator>();
	@SuppressWarnings("unchecked")
	private ArrayList<Class> classes = new ArrayList<Class>(); 
	@SuppressWarnings("unchecked")
	private Class currentClass = Object.class;
	
	//-- Classes de dados
	private ArrayList<DataClass> classTree = new ArrayList<DataClass>();
	@SuppressWarnings("unchecked")
	private ArrayList<Class> dataClasses = new ArrayList<Class>(); 
	@SuppressWarnings("unchecked")
	private Class currentDataClass = Object.class;
	
	public ArrayList<Navigator> getTree() {
		return tree;
	}
	
	public ArrayList<DataClass> getClassTree() {
		return classTree;
	}

	@SuppressWarnings("unchecked")
	private void addClass(Class clazz) {
		if (clazz != Object.class &&  classes.indexOf(clazz) == -1){
			classes.add(clazz);
			addClass(clazz.getSuperclass());
		}
	}
	
	@SuppressWarnings("unchecked")
	private void addDataClass(Class clazz) {
		if (clazz != Object.class &&  dataClasses.indexOf(clazz) == -1){
			dataClasses.add(clazz);
			addDataClass(clazz.getSuperclass());
		}
	}
	
	@SuppressWarnings("unchecked")
	public Navigator getClasses( String database ) throws Exception {
		tree.add(getNewNavigator(Object.class));

		for (Class clazz : PersistenceUtil.getAnnotatedClasses(database)) {
			addClass(clazz);
		}

		ArrayList<Class> classesTemp = (ArrayList<Class>) classes.clone();

		do {
			for (Class clazz : classesTemp) {
				Navigator parent = findParent(tree, clazz.getSuperclass());

				if ( clazz.getSuperclass().equals(currentClass) || parent != null ) {
					classes.remove(clazz);
					Navigator item = getNewNavigator(clazz);
					if (parent != null) {
						if (parent.getSubmenu().indexOf(item) == -1) {
							parent.getSubmenu().add(item);
						}
					}
					currentClass = clazz;
				}
			}
		} while (classes.size() > 0);
		
		return tree.get(0);
	}
	
	@SuppressWarnings("unchecked")
	private Navigator getNewNavigator(Class clazz) {
		Navigator nav = new Navigator();
		nav.setName(clazz.getName());
		nav.setClassName(clazz.getName());
		return nav;
	}
	
	@SuppressWarnings("unchecked")
	public Navigator findParent(List<Navigator> tree, Class parent) {
		if (tree != null && tree.size() > 0){
			for (Navigator nav : tree) {
				if (nav.getClassName().equals(parent.getName())){
					return nav;
				}else{
					Navigator temp = findParent(nav.getSubmenu(), parent);
					
					if (temp != null){
						return temp;						
					}
				}
			}
		}
		
		
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public DataClass getDataClasses( String database, Class rootClass ) throws Exception {
		classTree.add(getNewDataClass(Object.class, null));

		for (Class clazz : PersistenceUtil.getAnnotatedClasses(database)) {
			addDataClass(clazz);
		}

		ArrayList<Class> classesTemp = (ArrayList<Class>) dataClasses.clone();

		do {
			for (Class clazz : classesTemp) {
				DataClass parent = findClassParent(classTree, clazz.getSuperclass());

				if ( clazz.getSuperclass().equals(currentDataClass) || parent != null ) {
					dataClasses.remove(clazz);
					DataClass item = getNewDataClass(clazz, rootClass);
					if (parent != null) {
						if (parent.getChildren().indexOf(item) == -1) {
							parent.getChildren().add(item);
						}
					}
					currentDataClass = clazz;
				}
			}
		} while (dataClasses.size() > 0);
		
		return classTree.get(0);
	}	
	
	@SuppressWarnings("unchecked")
	private DataClass getNewDataClass(Class clazz, Class rootClass) throws NumberFormatException, Exception {				
		DiscriminatorValue annotation = (DiscriminatorValue)clazz.getAnnotation(DiscriminatorValue.class);
				
		//if ( annotation != null && annotation.value() != null ){
			DataClass dataClass = new DataClass();
			//dataClass.setName(org.apache.commons.lang.ClassUtils.getShortCanonicalName(clazz.getName()));
			dataClass.setName(clazz.getSimpleName());
			dataClass.setClassName(clazz.getName());		
			if (annotation != null && annotation.value() != null ){
				dataClass.setClassId(new Long(annotation.value()));
			}
			if ( rootClass != null ){
				dataClass.setInheritLevel(this.getInheritLevel(clazz, rootClass));
			}
			return dataClass;
		//}
		//return null;
	}
	
	@SuppressWarnings("unchecked")
	public DataClass findClassParent(ArrayList<DataClass> clazzTree, Class parent) {
		if (clazzTree != null && clazzTree.size() > 0){
			for (DataClass dataClazz : clazzTree) {
				if ( dataClazz.getClassName().equals(parent.getName()) ){
					return dataClazz;
				} else {
					DataClass temp = findClassParent(dataClazz.getChildren(), parent);					
					if (temp != null){
						return temp;						
					}
				}
			}
		}		
		return null;
	}
	
	public DataClass findClassByIdentifier( ArrayList<DataClass> clazzTree, Long classId ){
		for (Iterator<DataClass> iterator = clazzTree.iterator(); iterator.hasNext();) {
			DataClass dataClass = (DataClass) iterator.next();
			if ( dataClass.getClassId() != null && dataClass.getClassId().equals(classId) ){
				return dataClass;
			}
			
			if ( dataClass.getChildren() != null && dataClass.getChildren().size() > 0 ){
				DataClass temp = findClassByIdentifier(dataClass.getChildren(), classId);
				if ( temp != null ){
					return temp;
				}			
			}
		}		
		return null;
	}
	
	public Integer getInheritLevel(Class baseClass, Class parent){
		if (parent == null){
			return 0;
		}
		if (parent.equals(baseClass)){
			return 0;
		}
		Class tempParent = baseClass.getSuperclass();
		if (tempParent == null ){
			return 0;
		}
		Integer level = 1;
		
		while( tempParent != null && !tempParent.equals(parent)){
			level++;
			tempParent = tempParent.getSuperclass(); 
		}
		return level;
	}
}