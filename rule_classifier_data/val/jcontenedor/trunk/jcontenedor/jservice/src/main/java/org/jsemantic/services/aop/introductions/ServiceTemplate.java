/**
 * 
 */
package org.jsemantic.services.aop.introductions;


public interface ServiceTemplate {
	
	public Object getTemplate();
	
	public Object getTemplate(Class <?> service);
	
}
