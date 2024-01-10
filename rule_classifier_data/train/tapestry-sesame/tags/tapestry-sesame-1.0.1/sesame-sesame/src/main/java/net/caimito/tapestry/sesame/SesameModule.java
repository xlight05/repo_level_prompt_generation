package net.caimito.tapestry.sesame;

import java.io.IOException;
import java.util.Map;

import net.caimito.tapestry.sesame.providers.AuthenticationProvider;

import org.apache.tapestry5.ioc.MappedConfiguration;
import org.apache.tapestry5.ioc.OrderedConfiguration;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.ComponentClassResolver;
import org.apache.tapestry5.services.ComponentClassTransformWorker;
import org.apache.tapestry5.services.Cookies;
import org.apache.tapestry5.services.Request;
import org.apache.tapestry5.services.RequestExceptionHandler;
import org.apache.tapestry5.services.RequestGlobals;
import org.apache.tapestry5.services.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SesameModule {
	private static Logger logger = LoggerFactory.getLogger(SesameModule.class) ;
	
//	public static AuthenticationProvider buildAuthenticationProvider() {
//		return new FakeDatabaseAuthenticationProvider() ;
//	}
	
	public static RememberMe buildRememberMe(final Cookies cookies, AuthenticationProvider authenticationProvider) {
		return new RememberMe(cookies, authenticationProvider) ;
	}
	
	public static Authentication buildAuthentication(final RequestGlobals requestGlobals, final RememberMe rememberMe, final AuthenticationProvider authenticationProvider,
			final Map<String, String> configuration) {
		return new Authentication(requestGlobals, rememberMe, authenticationProvider, configuration) ;
	}
	
	public static SecurityChecker buildSecurityChecker(@Inject RequestGlobals requestGlobals) {
	  return new SecurityChecker(requestGlobals) ;
	}
	
	public static void contributeComponentClassTransformWorker(OrderedConfiguration<ComponentClassTransformWorker> configuration,
			@Inject SecurityChecker securityChecker) {
		configuration.add("net.caimito.tapestry.sesame", new SecurityTransformationWorker(securityChecker));
	}
	
	public static void contributeAuthentication(final MappedConfiguration<String, String> configuration) {
		configuration.add(Authentication.SESAME_LOGIN_PAGE_NAME, "login") ;
	}

	public static RequestExceptionHandler decorateRequestExceptionHandler(final Object delegate, final Response response,
			final Request request, final ComponentClassResolver resolver, final RememberMe rememberMe,
			final Authentication authentication) {
		return new RequestExceptionHandler() {
			public void handleRequestException(Throwable exception) throws IOException {
				Throwable cause = exception ;
				
				while (cause.getCause() != null && !(cause instanceof SecurityRedirectException)) {
					cause = cause.getCause() ;
				}
				
				if (cause instanceof SecurityRedirectException) {
					if (rememberMe.hasRememberMeCookie()){
						logger.debug("Remember-me cookie found") ;
						try {
							rememberMe.reauthenticate(request.getSession(true)) ;
							
							String path = cleanUri(request.getPath()) ;
							response.sendRedirect(path) ;
						} catch (AuthenticationException e) {
							logger.debug("Reauthentication failed due to " + e.getMessage()) ;
							askForAuthentication(response) ;
						}
					} else {
						askForAuthentication(response) ;
					}
				} else
					((RequestExceptionHandler) delegate).handleRequestException(exception);
			}

			private String cleanUri(String path) {
				if (path.indexOf("/") != -1)
					path = path.substring(1) ;
				
				return path ;
			}
			
			private void askForAuthentication(Response response) throws IOException {
				String path = cleanUri(request.getPath()) ;
				
				if (resolver.isPageName(path)) {
					logger.debug("Original URI requested was " + path) ;
					request.getSession(true).setAttribute(Authentication.SESAME_ORGINAL_PAGE_REQUESTED, path) ;
				}
					
				logger.debug("Redirecting to " + authentication.getLoginPageName()) ;
				response.sendRedirect(authentication.getLoginPageName());
			}
		};
	}

}
