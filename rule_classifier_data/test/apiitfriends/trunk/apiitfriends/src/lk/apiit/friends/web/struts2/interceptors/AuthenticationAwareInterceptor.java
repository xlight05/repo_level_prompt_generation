package lk.apiit.friends.web.struts2.interceptors;

import java.lang.reflect.Method;

import lk.apiit.friends.model.User;
import lk.apiit.friends.service.auth.AuthenticatedUser;
import lk.apiit.friends.web.support.annotations.AuthenticationAware;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.Authentication;
import org.springframework.security.context.SecurityContextHolder;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

/**
 * Struts2 Intercepter which injects Authenticated User reference to the action
 * if authentication details are available and the action denotes the injection
 * point using {@link AuthenticationAware} annotation on a method.
 * 
 * @author Yohan Liyanage
 * @version 15-Sep-2008
 * @since 15-Sep-2008
 */
public class AuthenticationAwareInterceptor extends
		AbstractInterceptor {

	private static final long serialVersionUID = 6972704127031552607L;
	private static Log log = LogFactory.getLog(AuthenticationAwareInterceptor.class);
	
	/**
	 * Intercepts the Struts2 Action Invocation and injects Authenticated User
	 * if indicated by {@link AuthenticationAware} annotation on method of the action.
	 * 
	 * @param invocation ActionInvocation
	 * @return invocation result
	 */
	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		
		// Get Struts2 Action
		Object action = invocation.getAction();
		log.debug("AuthenticationAwareIntercepter intercepting Action Invocation on ..." + action.getClass().getName());
		
		// Get Authentication Details
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		log.debug("auth : " + auth);
		User user = null;
		
		
		if (auth.getPrincipal() instanceof AuthenticatedUser) {
			user = ((AuthenticatedUser) auth.getPrincipal()).getUser();
		}
		
		log.debug("user : " + user);
		
		if (user!=null) {
			
			// Check Methods
			for (Method m : action.getClass().getMethods()) {
				log.debug("method : " + m.getName());
				if (m.getAnnotation(AuthenticationAware.class) != null) {
					m.invoke(action, user);
					log.debug("AuthenticationAware Annotation Processed on method : " + m.getName());
					return invocation.invoke();
				}
			}
		}
		
		log.debug("AuthenticationAwareIntercepter complete, no operations.");
		
		return invocation.invoke();
		
	}

}
