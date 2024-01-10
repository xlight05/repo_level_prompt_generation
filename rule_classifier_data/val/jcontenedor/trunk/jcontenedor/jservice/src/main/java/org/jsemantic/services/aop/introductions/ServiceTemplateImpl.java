/**
 * 
 */
package org.jsemantic.services.aop.introductions;

public class ServiceTemplateImpl implements ServiceTemplate {
	
	public ServiceTemplateImpl() {
		super();
	}
	
	public Object getTemplate(Class <?> service) {
		//return super.getContext().locateService(service);
		return null;
	}
	
	public Object getTemplate() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
