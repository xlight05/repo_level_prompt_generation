package br.com.dyad.infrastructure.widget;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Vector;

import javax.servlet.http.HttpSession;
import javax.swing.JOptionPane;

import br.com.dyad.client.GenericService;
import br.com.dyad.commons.reflect.ReflectUtil;
import br.com.dyad.infrastructure.annotations.SendToClient;
import br.com.dyad.infrastructure.entity.Language;
import br.com.dyad.infrastructure.i18n.Translation;

/**
 * @enterprise Dyad & Associados (http://www.dyad.com.br)
 * @author Helton Gonçalves (helton@dyad.com.br;heltongoncalves@gmail.com)
 */
public abstract class BaseServerWidget {
	public static long objectIdGenerator = 0;
	
	protected HttpSession httpSession;
	protected String objectId;
	protected List<String> propsToSynchronize = new ArrayList<String>();
	@SuppressWarnings("unchecked")
	protected HashMap clientProps = new HashMap();
	@SendToClient
	protected String help;
	private Hashtable<DyadEvents, Vector<WidgetListener>> widgetListeners;
	protected List<BaseAction> actions;
	protected BaseServerWidget parent;	
	
	public synchronized static long generateObjectId() {
		objectIdGenerator++;
		return objectIdGenerator;
	}
	
	public BaseServerWidget getParent() {
		return parent;
	}

	public void setParent(BaseServerWidget parent) {
		this.parent = parent;
	}

	public boolean findAction( BaseAction action ){    	
    	return (Action)this.getObjectByServerId((ArrayList)this.getActions(), action.getObjectId() ) != null;
    }
	
	public boolean add(BaseAction action){
    	if ( ! findAction(action) ){
    		//action.setProcess(this);
    		action.setHttpSession(this.getHttpSession());
    		action.hide();
    		action.disable();
    		this.actions.add(action);
    		return true;
    	}
    	return false;
    }
	
	public List<BaseAction> getActions() {
		if (actions == null) {
			actions = new ArrayList<BaseAction>(); 
		}
		return actions;
	}

	public void setActions(List<BaseAction> actions) {
		this.actions = actions;
	}

	public Hashtable<DyadEvents, Vector<WidgetListener>> getWidgetListeners() {
		return widgetListeners;
	}

	public void setWidgetListeners(Hashtable<DyadEvents, Vector<WidgetListener>> awidgetListeners) {
		widgetListeners = awidgetListeners;
	}
	
	public void addWidgetListener(DyadEvents methodName, WidgetListener listener) {
		addWidgetListener(methodName, listener, false);
	}

	public void addWidgetListener(DyadEvents methodName, WidgetListener listener, Boolean addFirst) {			
		if (widgetListeners == null){
			widgetListeners = new Hashtable<DyadEvents, Vector<WidgetListener>>();
		}
		
		if (listener == null){
			throw new RuntimeException("Listener cannot be null");
		}
		
		Vector<WidgetListener> list = widgetListeners.get(methodName);		
		if (list == null){
			list = new Vector<WidgetListener>();
			widgetListeners.put(methodName, list);
		}
		if (list.indexOf(listener) == -1){
			if (addFirst != null && addFirst){				
				list.add(0, listener);
			} else {
				list.add(listener);
			}
		}		
	}
	
	public void removeListener(WidgetListener listener){
		for (DyadEvents key : widgetListeners.keySet()){
			Vector<WidgetListener> list = widgetListeners.get(key);
			list.remove(listener);
		}
	}
	
	public void removeMethodListener(DyadEvents method){
		if (method == null){
			widgetListeners = new Hashtable<DyadEvents, Vector<WidgetListener>>();
		} else {			
			widgetListeners.remove(method);
		}
	}
		
	/*public HashMap<String, WidgetListener> getWidgetListeners() {
		return widgetListeners;
	}

	public void setWidgetListeners(HashMap<String, WidgetListener> widgetListeners) {
		this.widgetListeners = widgetListeners;
	}*/

	public BaseServerWidget() {
		this.getObjectId();
	}	
	
	public HttpSession getHttpSession() {
		return httpSession;
	}

	public void setHttpSession(HttpSession httpSession) {
		this.httpSession = httpSession;
	}

	public String getObjectId(){
		if (objectId == null){
			//objectId = "$" + this.hashCode() + "$" + (new Date()).getTime();
			objectId = "" + generateObjectId();			
		}
		return objectId;
	}
	
	public String getDatabase() {
		return (String)httpSession.getAttribute(GenericService.GET_DATABASE_FILE);
	}

	public boolean addPropNameToPropsToSincronize( String propName, Object oldValue, Object newValue ){
		/*if (newValue != null) {
			JOptionPane.showMessageDialog(null, "Adicionou na classe " + this.getClass().getName() + " newValue: " + newValue.getClass().getName());
		}
		
		if (oldValue != null) {
			JOptionPane.showMessageDialog(null, "Adicionou na classe " + this.getClass().getName() + " oldValue: " + oldValue.getClass().getName());
		}*/
		
		if ( this.propsToSynchronize.indexOf(propName) == -1 ){
			if ( oldValue != null && newValue != null && ! oldValue.equals(newValue) ){			
				this.propsToSynchronize.add(propName);
			} else if ( oldValue == null && newValue != null ){
				this.propsToSynchronize.add(propName);
			} else if ( oldValue != null && newValue == null  ){
				this.propsToSynchronize.add(propName);
			}
		}
		return false;
	}

	protected boolean propMustGoToClient( String propName ){
		if ( this.propsToSynchronize.indexOf(propName) != -1 ){
			return true;
		}
		return false;
	}
	
	@SuppressWarnings("unchecked")
	protected boolean addPropToClient( String propName, Object value ){
		if ( this.propMustGoToClient(propName) ){
			try {
				clientProps.put(propName, value );
				return true;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}		
		return false;
	}
	
	@SuppressWarnings("unchecked")
	public HashMap toClientSynchronizer() throws Exception{
		clientProps.clear();

		this.addPropsToClientSincronizer();

		this.addObjecIdToClientSincronizer();
		
		propsToSynchronize.clear();

		return clientProps;
	}

	@SuppressWarnings("unchecked")
	protected void addObjecIdToClientSincronizer() {
		if ( ! clientProps.isEmpty() ){
			String objectId = this.getObjectId();
			clientProps.put("objectId", objectId );
		}
	}

	protected abstract void addPropsToClientSincronizer() throws Exception;

	public String getHelp() {
		return help;
	}

	public void setHelp(String help) {
		this.help = help;
	}

	public List<String> getPropsToSincronize() {
		return propsToSynchronize;
	}

	@SuppressWarnings("unchecked")
	public HashMap getClientProps() {
		return clientProps;
	}

	public BaseServerWidget getObjectByServerId( ArrayList<BaseServerWidget> list, String objectId ){
		for (BaseServerWidget baseServerWidget : list ) {
			if ( baseServerWidget.equals( objectId )){
				return baseServerWidget;
			}
		}					
		return null;
	}    

	@Override
	public boolean equals(Object obj) {
		if ( obj instanceof String ){
			boolean result = ((String) obj).equalsIgnoreCase((this.getObjectId()));
			return result;
		} 		
		return this.toString().equalsIgnoreCase( obj.toString() );
	}
	
	public String translate(String code){
		if (code == null){
			return null;
		}
		
		Language language = (Language)getHttpSession().getAttribute(GenericService.USER_LANGUAGE);
		
		if (getHttpSession() != null && language != null){
			return Translation.get(getDatabase(), language, code);
		} else {
			return code;
		}
		
	}
	
	public void resetSynchronizer(BaseServerWidget widget) {
		try {
			if (widget == null) {
				widget = this;
			}
			
			List<Field> fields = ReflectUtil.getClassFields(widget.getClass(), true);
			
			for (Field field : fields) {
				if (ReflectUtil.inheritsFrom(field.getType(), BaseServerWidget.class)) {				
					BaseServerWidget propValue = (BaseServerWidget)ReflectUtil.getFieldValue(widget, field.getName());
					
					if (propValue != null) {						
						resetSynchronizer(widget);
					}
				} else if (widget.propMustGoToClient(field.getName())) {
					this.propsToSynchronize.add(field.getName());
				}
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}