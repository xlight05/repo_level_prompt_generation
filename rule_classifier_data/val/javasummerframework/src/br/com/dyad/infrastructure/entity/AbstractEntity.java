package br.com.dyad.infrastructure.entity;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Vector;

import javassist.Modifier;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;

import org.apache.commons.lang.StringUtils;

import br.com.dyad.commons.data.AppEntity;
import br.com.dyad.commons.data.AppTempEntity;
import br.com.dyad.commons.reflect.ReflectUtil;
import br.com.dyad.infrastructure.annotations.Authorization;
import br.com.dyad.infrastructure.annotations.MetaField;
import br.com.dyad.infrastructure.customization.Customizable;
import br.com.dyad.infrastructure.navigation.admin.security.PermissionTypes;
import br.com.dyad.infrastructure.persistence.PersistenceUtil;
import br.com.dyad.infrastructure.widget.DyadEvents;
import br.com.dyad.infrastructure.widget.WidgetListener;

import com.google.gwt.user.client.rpc.IsSerializable;
/**
 * @author Helton Gonçalves. 
 */
@Authorization
@MappedSuperclass
@DiscriminatorValue(value="-89999999999758")
public abstract class AbstractEntity implements IsSerializable, AppEntity, Customizable {	

	protected static transient long numerator = 1;
	private transient HashMap<String, Object> oldValues = new HashMap<String, Object>();
	private transient HashMap<String, Object> originalValues = new HashMap<String, Object>();
	public static transient MetaField repositoryMetaField = new MetaField();
	public static transient ArrayList<AbstractEntity> licensesAvailable = null;
	//true caso o registro ainda não tenha sido persistido
	private transient boolean newRecord = false;		
	
	public AbstractEntity() {
		if ( isTempEntity() ){			
			try{			
				createId("");
			} catch(Exception e) {
				throw new RuntimeException(e);
			}		
		}	
	}
	
	@Version
	@Column(nullable=false)
	protected Long version;	
	
	public boolean isNewRecord() {
		return newRecord;
	}
	
	public boolean getNewRecord() {
		return newRecord;
	}
	
	public void setNewRecord(boolean newRecord){
		this.newRecord = newRecord;
	}

	private static Hashtable<Class, Hashtable<DyadEvents, Vector<WidgetListener>>> widgetListeners;	

	public static Hashtable<Class, Hashtable<DyadEvents, Vector<WidgetListener>>> getWidgetListeners() {
		return widgetListeners;
	}

	public static void setWidgetListeners(Hashtable<Class, Hashtable<DyadEvents, Vector<WidgetListener>>> widgetListeners) {
		widgetListeners = widgetListeners;
	}

	public static void addWidgetListener(Class clazz, DyadEvents methodName, WidgetListener listener) {			
		if (widgetListeners == null){
			widgetListeners = new Hashtable<Class, Hashtable<DyadEvents, Vector<WidgetListener>>>();
		}
		
		Hashtable<DyadEvents, Vector<WidgetListener>> map = widgetListeners.get(clazz);
		if (map == null){
			map = new Hashtable<DyadEvents, Vector<WidgetListener>>();
			widgetListeners.put(clazz, map);
		}
		
		Vector<WidgetListener> list = map.get(methodName);		
		
		if (list == null){
			list = new Vector<WidgetListener>();
			map.put(methodName, list);
		}
		
		if (list.indexOf(listener) == -1){
			list.add(listener);
		}		
	}
	
	public static void removeListener(Class clazz, WidgetListener listener){
		Hashtable<DyadEvents, Vector<WidgetListener>> map = widgetListeners.get(clazz);
		
		for (DyadEvents key : map.keySet()){
			Vector<WidgetListener> list = map.get(key);
			list.remove(listener);
		}
	}
	
	public static void removeMethodListener(Class clazz, String method){
		Hashtable<DyadEvents, Vector<WidgetListener>> map = widgetListeners.get(clazz);
		map.remove(method);
	}
	
	public static Hashtable<DyadEvents, Vector<WidgetListener>> getClassListeners(Class clazz, DyadEvents evt){
		Hashtable<DyadEvents, Vector<WidgetListener>> ret = new Hashtable<DyadEvents, Vector<WidgetListener>>();
		if (widgetListeners != null){			
			Hashtable<DyadEvents, Vector<WidgetListener>> map = widgetListeners.get(clazz);
			if (map != null){			
				if (evt == null){							
					for (DyadEvents temp : map.keySet()) {
						Vector<WidgetListener> list = ret.get(temp);
						if (ret.get(temp) == null){
							list = new Vector<WidgetListener>();
							ret.put(temp, list);
						}
						
						Vector<WidgetListener> tempList = map.get(temp);
						for (WidgetListener widgetListener : tempList) {
							list.add(0, widgetListener);
						}
					}
				} else {
					ret = map;
				}
			}
		}
		
		if (!clazz.equals(AbstractEntity.class)){
			Class superClazz = clazz.getSuperclass();
			Hashtable<DyadEvents, Vector<WidgetListener>> superListeners = getClassListeners(superClazz, evt);
			
			for (DyadEvents temp : superListeners.keySet()) {
				Vector<WidgetListener> list = ret.get(temp);
				if (ret.get(temp) == null){
					list = new Vector<WidgetListener>();
					ret.put(temp, list);
				}
				
				Vector<WidgetListener> tempList = superListeners.get(temp);
				for (WidgetListener widgetListener : tempList) {
					list.add(0, widgetListener);
				}
			}
		}
				
		return ret;
	}
		
	public HashMap<String, Object> getOriginalValues() {
		return originalValues;
	}

	public void setOriginalValues(HashMap<String, Object> originalValues) {
		this.originalValues = originalValues;
	}

	protected boolean isTempEntity() {
		return ReflectUtil.inheritsFrom(this.getClass(), AppTempEntity.class);
	}
	
	public HashMap<String, Object> getOldValues() {
		return oldValues;
	}

	public String toString(){
		return this.getId() == null ? "" : this.getId().toString();
	}
	
	public static String getFindExpress( String searchToken ){
		return "";
	}
	
	public boolean find( String token ){		
		return true;
	}
	
	public void setOldValues(HashMap<String, Object> oldValues) {
		this.oldValues = oldValues;
	}

	public abstract Long getId();

	public abstract void setId(Long id);

	/*public void commit() {
		Session s = PersistenceUtil.getSession();
		s.beginTransaction();
		s.save(this);
		s.getTransaction().commit();
	}*/

	public String getClassIdentifier() throws Exception{
		/*try{				
			DiscriminatorValue annotation = this.getClass().getAnnotation(DiscriminatorValue.class);
			return annotation.value();
		}catch(Exception e){
			throw DyadException.createException(e);
		}*/
		return AbstractEntity.getClassIdentifier(this.getClass());
	}
	
	@SuppressWarnings("unchecked")
	public static String getClassIdentifier(Class clazz) throws Exception{
		if ( clazz == null ){
			throw new Exception( "Clazz cant be null for funcion getClassIdentifier()" );
		}

		DiscriminatorValue annotation = (DiscriminatorValue)clazz.getAnnotation(DiscriminatorValue.class);
		if ( annotation == null ){
			return null;
			//throw new Exception( "Could not find annotation DiscriminatorValue in class " + clazz.getName() );
		}
		return annotation.value();
	}
	
	@SuppressWarnings("unchecked")
	public static String getClassIdentifierByClassName(String className) throws Exception{
		//try{				
			Class clazz = Class.forName( className );
			return AbstractEntity.getClassIdentifier( clazz );
		//}catch(Exception e){
			//throw AppException.createException(e);
		//}
	}		

	public void createId( String database, Long rangeId ) throws Exception{
		if (isTempEntity()){
			setId(numerator++);
		} else {		
			setNewRecord(true);
			setId( KeyGenerator.getInstance(database).generateKey(rangeId) );
		}
	}
	
	/**
	 * Esse métido deve ser usado para criar um registro de uma determinada licença. Foi adicionado
	 * o parâmetro rangeId apenas para diferenciar a assinatura do método createId( String database, Long rangeId )
	 * @param database
	 * @param licenseId
	 * @param rangeId
	 * @throws Exception
	 */
	public void createId( String database, Long licenseId, Long rangeId ) throws Exception{
		if (isTempEntity()){
			setId(numerator++);
		} else {			
			if ( licenseId != null ){
				List list = PersistenceUtil.executeHql(database, "from ProductLicense where id = "+ licenseId +
						"and classId = '" + AbstractEntity.getClassIdentifierByClassName( "br.com.dyad.infrastructure.entity.ProductLicense" ) + "'");
				ProductLicense license = ((ProductLicense)list.get(0));
				setId( KeyGenerator.getInstance(database).generateKey( license.getKeyRange().getId() ) );
			} else {
				setId( KeyGenerator.getInstance(database).generateKey( null ) );
			}
		}
	}
	
	public void createId(String database) throws Exception{
		if (isTempEntity()){
			setId(numerator++);
		} else {			
			createId(database, KeyGenerator.KEY_RANGE_NO_LICENSE);
		}
	}
	
	public Object getOriginalValue(String key) {
		return originalValues.containsKey(key) ? originalValues.get(key) : ReflectUtil.getFieldValue(this, key);
	}
	
	public Object getOldValue(String key) {
		return oldValues.containsKey(key) ? oldValues.get(key) : ReflectUtil.getFieldValue(this, key);
	}

	public static MetaField getMetaField() {
		return repositoryMetaField;
	}

	public static void setMetaField(MetaField metaField) {
		AbstractEntity.repositoryMetaField = metaField;
	}

	public void defineFields(){
		this.defineField( 
			"id", 
			MetaField.label, "Id", 
			MetaField.tableViewWidth, 50,
			MetaField.required, true, 
			MetaField.visible, false,  
			MetaField.order, 0, 
			MetaField.readOnly, true,
			MetaField.password, true);

		this.defineField(
			"version",
			MetaField.label, "Record Version", 
			MetaField.visible, false, 
			MetaField.order, 1, 
			MetaField.readOnly, true);
		
		this.defineField(
			"classId",
			MetaField.label, "Class Id", 
			MetaField.visible, false, 
			MetaField.order, 2, 
			MetaField.readOnly, true);
		
		this.defineField(
			"lastTransaction",
			MetaField.label, "Last Transaction", 
			MetaField.visible, false, 
			MetaField.order, 3, 
			MetaField.readOnly, true,
			MetaField.canCustomizeValue, false );
		
		this.defineField(
			"licenseId",
			MetaField.label, "Product License", 
			MetaField.visible, false, 
			MetaField.order, 4, 
			MetaField.readOnly, true);
	}

	public void defineField(String propName, Object...objects){
		AbstractEntity.repositoryMetaField.setProps(propName, objects);
	}
	
	public Object getAttributeValueForPropName( String propName, String attribute ){
		return AbstractEntity.repositoryMetaField.getAttributeValueForPropName(propName, attribute);
	}
	
	public Boolean containsAttributeValueForPropName( String propName, String attribute ){
		return AbstractEntity.repositoryMetaField.containsAttributeValueForPropName(propName, attribute);
	}
	
	@SuppressWarnings("unchecked")
	public static ArrayList getOptions( Object...objects ){
		ArrayList options = new ArrayList();
		
		for (int i = 0; i < objects.length; i++) {
			Object value = objects[i];
			i++;
			Object description = objects[i];			
			
			ArrayList option = new ArrayList();
			option.add( value );
			option.add( description );
			
			options.add( option );
		}
		
		return options;
	}
	
	@SuppressWarnings("unchecked")
	public static ArrayList getOptions(Class enumClass){
		ArrayList options = new ArrayList();		
		List<Field> fields = ReflectUtil.getClassFields(enumClass, false);
		
		for (Field field : fields) {			
			try {
				if (Modifier.isPublic(field.getModifiers()) && !StringUtils.startsWith(field.getName(), "$")){
					Object obj = field.get(null);	
					
					ArrayList option = new ArrayList();
					option.add(ReflectUtil.getMethodReturn(obj, "getCode"));
					option.add(field.getName().toString());
										
					options.add( option);
				}
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
		
		return options;
	}
	
	public void onBeforeChange(String property) {}	
	public void onAfterChange(String property) {}
	
	@Authorization
	public static List<String> getAuthItems() {
		List<String> items = new ArrayList<String>();
		items.add(PermissionTypes.Insert.toString());
		items.add(PermissionTypes.Update.toString());
		items.add(PermissionTypes.Delete.toString());
		return items;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof AbstractEntity && obj != null) {
			Long idObj = ((AbstractEntity)obj).getId();
			
			if (idObj != null && getId() != null) {
				return idObj.equals(getId());
			} else {
				return false;
			}
		} else {			
			return super.equals(obj);
		}
	}

	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}
}
