package br.com.dyad.infrastructure.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import br.com.dyad.client.Navigator;
import br.com.dyad.infrastructure.persistence.PersistenceUtil;

public class HibernateTree extends DyadService {
	
	private ArrayList<Navigator> tree = new ArrayList<Navigator>();
	@SuppressWarnings("unchecked")
	private ArrayList<Class> classes = new ArrayList<Class>(); 
	@SuppressWarnings("unchecked")
	private Class currentClass = Object.class;
		
	@SuppressWarnings("unchecked")
	@Override
	public HashMap getServiceValue(HashMap params) throws Exception {
		HashMap ret = new HashMap();
		ret.put("root", getClasses());
		
		return ret;
	}
	
	public ArrayList<Navigator> getTree() {
		return tree;
	}

	@SuppressWarnings("unchecked")
	private void addClass(Class clazz) {
		if (clazz != Object.class &&  classes.indexOf(clazz) == -1){
			classes.add(clazz);
			addClass(clazz.getSuperclass());
		}
	}
	
	@SuppressWarnings("unchecked")
	public Navigator getClasses() throws Exception {
		tree.add(getNewNavigator(Object.class));

		for (Class clazz : PersistenceUtil.getAnnotatedClasses(getDatabase())) {
			addClass(clazz);
		}

		ArrayList<Class> classesTemp = (ArrayList<Class>) classes.clone();

		do {
			for (Class clazz : classesTemp) {
				Navigator parent = findParent(tree, clazz.getSuperclass());

				if (clazz.getSuperclass().equals(currentClass)
						|| parent != null) {
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
}