package br.com.dyad.infrastructure.widget;

import br.com.dyad.infrastructure.annotations.SendToClient;

/**
 * @enterprise Dyad & Associados (http://www.dyad.com.br)
 * @author Helton GonÃƒÂ§alves (helton@dyad.com.br;heltongoncalves@gmail.com)
 */
public abstract class BaseAction extends BaseServerWidget{
	
	@SendToClient
	private String text;
	@SendToClient
	private String permissionKeyword;
	@SendToClient
	private String iconStyle;
	@SendToClient
	private Boolean enabled;
	@SendToClient
	private Boolean visible;
	@SendToClient
	private Boolean validateLastInteraction;
	
	public BaseAction(){
		super();
		this.initializeBaseAction();
	}

	public BaseAction(BaseServerWidget parent){
		this();
		parent.add(this);
		setParent(parent);
	}

	public BaseAction(BaseServerWidget parent, String text) {
		this();
		this.setText(text);
		parent.add(this);
		setParent(parent);
	}

	public BaseAction(Window window, String text, String permissionKeyword ) {
		this();
		this.setText(text);
		this.setPermissionKeyword(permissionKeyword);
		window.add(this);
	}
					
	public void initializeBaseAction(){
		this.setIconStyle("process-action");
		this.setValidateLastInteraction(true);
	}

	abstract void onClick() throws Exception;

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getIconStyle() {
		return iconStyle;
	}

	public void setIconStyle(String iconStyle) {
		this.iconStyle = iconStyle;
	}

	public String getPermissionKeyword() {
		return permissionKeyword;
	}

	public void setPermissionKeyword(String permissionKeyword) {
		this.permissionKeyword = permissionKeyword;
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public Boolean getVisible() {
		return visible;
	}

	private void setVisible(Boolean visible) {
		this.visible = visible;
	};	
	

	public Boolean getValidateLastInteraction() {
		return validateLastInteraction;
	}

	/**
	 * Default true.
	 * @param validateLastInteraction
	 */
	public void setValidateLastInteraction(Boolean validateLastInteraction) {
		this.validateLastInteraction = validateLastInteraction;
	}

	public void enable(){
		this.setEnabled(true);
	}

	public void disable(){
		this.setEnabled(false);
	}

	public void show(){
		this.setVisible(true);
	}

	public void hide(){
		this.setVisible(false);
	}
	
	@Override
	protected void addPropsToClientSincronizer() throws Exception {	
	}
	
}
