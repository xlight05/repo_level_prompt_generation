package br.com.dyad.infrastructure.navigation.admin.security;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.ClassUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Session;

import br.com.dyad.client.AppException;
import br.com.dyad.commons.data.AppTempEntity;
import br.com.dyad.commons.reflect.ReflectUtil;
import br.com.dyad.infrastructure.annotations.Authorization;
import br.com.dyad.infrastructure.annotations.MetaField;
import br.com.dyad.infrastructure.aspect.UserInfoAspect;
import br.com.dyad.infrastructure.customization.ClasspathIterator;
import br.com.dyad.infrastructure.entity.BaseEntity;
import br.com.dyad.infrastructure.entity.UserGroup;
import br.com.dyad.infrastructure.i18n.Translation;
import br.com.dyad.infrastructure.persistence.PersistenceUtil;
import br.com.dyad.infrastructure.widget.BaseAction;
import br.com.dyad.infrastructure.widget.WidgetListener;

import com.sun.tools.javac.Main;

public class CreatePermissionBean {
	
	private static HashMap<String, Class> permissionBeans = new HashMap<String, Class>();
	
	public static Class getPermissionClass(final String database) throws Exception{
		if (permissionBeans.get(database) == null){
			final CreatePermissionBean createPermissionBean = new CreatePermissionBean();
			final HashMap<String,String> props = new HashMap<String,String>();
			
			ClasspathIterator classpath = new ClasspathIterator();		
			classpath.addListener(new WidgetListener(){

				@Override
				public void handleEvent(Object sender) throws Exception {
					List<String[]> list = createPermissionBean.getAuthMethods(database, (String)sender);
					for (String[] string : list) {						
						if (!props.containsKey(string)){
							props.put(string[0], string.length > 1 ? string[1] : string[0]);
						}
					}
				}
				
			});
			
			classpath.listClasses();			
			String classPackage = CreatePermissionBean.class.getPackage().getName();
			String className = "PermissionBean" + database;
			String classString = "package " + classPackage + ";\n\n";
			classString += "import " + BaseEntity.class.getName() + ";\n";
			classString += "import " + AppTempEntity.class.getName() + ";\n";
			classString += "import " + UserGroup.class.getName() + ";\n";
			classString += "import " + MetaField.class.getName() + ";\n";
			classString += "\n\n";
			
			classString += "public class " + className + " extends BaseEntity implements AppTempEntity{ \n";
			
			classString += "   private UserGroup userGroup;\n";
			classString += "   public UserGroup getUserGroup(){\n";
			classString += "      return this.userGroup;\n";
			classString += "   }\n";
			classString += "   public void setUserGroup(UserGroup value){\n";
			classString += "      getOldValues().put(\"userGroup\", this.userGroup);";
			classString += "      this.userGroup = value;\n";
			classString += "   }\n";
			classString += "\n";
			
			classString += "   private Long classKey;\n";
			classString += "   public Long getClassKey(){\n";
			classString += "      return this.classKey;\n";
			classString += "   }\n";
			classString += "   public void setClassKey(Long value){\n";
			classString += "      getOldValues().put(\"classKey\", this.classKey);";
			classString += "      this.classKey = value;\n";
			classString += "   }\n";
			classString += "\n";
			
			for (String string : props.keySet()) {
				String temp = StringUtils.capitalize(string); 
				classString += "   private Boolean " + string + "; \n";
				classString += "   public Boolean get" + temp + "(){\n";				
				classString += "      return this." + string + ";\n";
				classString += "   }\n";
				classString += "   public void set" + temp + "(Boolean value){\n";
				classString += "      getOldValues().put(\"" + string + "\", this." + string + ");";
				classString += "      this." + string + " = value;\n";
				classString += "   }\n";
				classString += "\n";
			}
			
			classString += "   public static String ___getFieldLabel___(String fieldName){\n";
			for (String string : props.keySet()) {
				classString += "      if (fieldName.equals(\"" + string + "\")){\n";
				classString += "        return \"" + props.get(string) + "\";\n";
				classString += "      }\n";
			}
			classString += "      return null;\n";
			classString += "   }\n";
			
			classString += "   @Override";
			classString += "   public void defineFields() {\n";
			classString += "   	   super.defineFields();\n";
			classString += "   	   this.defineField(\n";
			classString += "   	   		\"userGroup\",\n";
			classString += "   	   		MetaField.order, -20,\n";
			classString += "   	   		MetaField.required, true,\n";
			classString += "   	   		MetaField.width, 200,\n";
			classString += "   	   		MetaField.tableViewWidth, 100,\n";
			classString += "   	   		MetaField.beanName," + UserGroup.class.getName() + ".class.getName()\n";
			classString += "   	   );\n";
			classString += "   	   this.defineField(\n";
			classString += "   	   		\"classKey\",\n";
			classString += "   	   		MetaField.order, -10,\n";
			classString += "   	   		MetaField.required, true,\n";
			classString += "   	   		MetaField.width, 200,\n";
			classString += "   	   		MetaField.tableViewWidth, 100\n";
			classString += "   	   );\n";
			classString += "   }\n";	   
			classString += "}";
			
			String classDir = classpath.getClassDir();
			File file = new File(classDir + File.separator + className + ".java");
			FileUtils.writeStringToFile(file, classString);
			
			String javaClassPath = System.getProperty("java.class.path", "");
			String[] options = new String[] {"-classpath", javaClassPath, "-d", classDir, classDir + "\\" + className + ".java"};
			Main.compile(options);
			permissionBeans.put(database, Class.forName(classPackage + "." + className));
			
		}
		
		return permissionBeans.get(database);
	}
	
	private List<String[]> getAuthMethods(String database, String className) throws Exception {
		List<String[]> list = new ArrayList<String[]>();
		
		//Não pode carregar nenhuma classe no pacote client
		if (!StringUtils.startsWith(className, "br.com.dyad.client")){
			//Class clazz = Class.forName(className);
			Class clazz = ClassUtils.getClass(className, false);
			Authorization an = (Authorization)clazz.getAnnotation(Authorization.class);	
			
			//Customização de classe
			if (an != null){
				if (an.data()){
					Session sess = PersistenceUtil.getSession(database);
					List beans = PersistenceUtil.loadAll(clazz, sess);
					
					for (Object obj : beans){						
						list.add( new String[]{ ReflectUtil.getFieldValue(obj, an.dataField()).toString() } );
					}
				}								
			}	
			
			//Verifica a lista as permissões dos métodos
			for (Method m : clazz.getMethods()){
				Authorization authMethod = (Authorization)m.getAnnotation(Authorization.class);
				if (authMethod != null){												
					try {
						Object obj = m.invoke(null);							
						if (ReflectUtil.inheritsFrom(obj.getClass(), List.class)){
							
							List<String> methodList = (List<String>)obj;
							
							for (String string : methodList) {	
								String label = Translation.get(UserInfoAspect.getDatabase(), UserInfoAspect.getUserLanguage(), string);
								list.add(new String[]{string, label});
							}
						}
					} catch (Exception e) {
						throw new AppException(e.getMessage());
					}
				}
			}
			
			//Verifica a lista as permissões das propriedades			
			for (Field field : ReflectUtil.getClassFields(clazz, false)) {
				if (ReflectUtil.inheritsFrom(field.getType(), BaseAction.class)){					
					Authorization authField = (Authorization)field.getAnnotation(Authorization.class);
					
					if (authField != null){						
						String permissionName = authField.permissionFieldName();
						
						if (!permissionName.equals("")){
							String label = authField.permissionLabel().equals("") ? authField.permissionFieldName() : authField.permissionLabel();
							label = Translation.get(UserInfoAspect.getDatabase(), UserInfoAspect.getUserLanguage(), label);
							list.add(new String[]{permissionName, label});
						}
					}
				}
			}
		}
		
		return list;
	}	

}
