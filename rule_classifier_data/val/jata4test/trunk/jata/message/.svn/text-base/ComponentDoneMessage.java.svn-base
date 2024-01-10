package jata.message;

import jata.Component.*;


public class ComponentDoneMessage extends NoCDMessage{

	public ComponentDoneMessage(Message Father) {
		super(Father);
	}
	
	public ComponentDoneMessage() {
		this(null);
	}
	
	protected String componentDoneMessage;

	public String getComponentDoneMessage() {
		return componentDoneMessage;
	}

	public void setComponentDoneMessage(TestComponent t) {
		this.componentDoneMessage = t.getName();
	}
	
	public void setComponentDoneMessage(TestCase<?,?> t) {
		this.componentDoneMessage = t.getName();
	}
}
