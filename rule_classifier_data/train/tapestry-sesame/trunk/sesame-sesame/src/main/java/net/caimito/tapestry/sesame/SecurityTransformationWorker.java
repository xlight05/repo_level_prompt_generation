package net.caimito.tapestry.sesame;

import java.lang.reflect.Modifier;


import org.apache.tapestry5.model.MutableComponentModel;
import org.apache.tapestry5.services.ClassTransformation;
import org.apache.tapestry5.services.ComponentClassTransformWorker;
import org.apache.tapestry5.services.TransformMethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SecurityTransformationWorker implements ComponentClassTransformWorker {
	private static Logger logger = LoggerFactory.getLogger(SecurityTransformationWorker.class) ;
	private SecurityChecker securityChecker ;
	
	public SecurityTransformationWorker(SecurityChecker securityChecker) {
		this.securityChecker = securityChecker ;
	}

	public void transform(ClassTransformation transformation, MutableComponentModel model) {
		logger.debug("Searching for security annotations in " + transformation.getClassName()) ;
		AuthenticationRequired authenticationRequired = transformation.getAnnotation(AuthenticationRequired.class) ;
		if (authenticationRequired != null)
			addSecurityChecker(transformation) ;
		else
			logger.debug("Did not find any security annotations.") ;
	}

	private void addSecurityChecker(ClassTransformation transformation) {
		logger.debug("Adding security checker") ;
		
		final String securityCheckerField = transformation.addInjectedField(SecurityChecker.class, "_$checker", securityChecker);
		
		final String[] ON_ACTIVATE_METHOD_PARAMETERS = {  Object[].class.getName() } ;
		
		TransformMethodSignature signatureBeginRender = new TransformMethodSignature(Modifier.PUBLIC, "void", "onActivate", 
				ON_ACTIVATE_METHOD_PARAMETERS, null);
		
		transformation.addMethod(signatureBeginRender, "if (" + securityCheckerField + ".checkAccess() == false) throw new " + SecurityRedirectException.class.getName() + "() ;") ;
	}

}
