package net.caimito.tapestry.sesame.sample.pages;

import net.caimito.tapestry.sesame.Authentication;
import net.caimito.tapestry.sesame.AuthenticationException;
import net.caimito.tapestry.sesame.UsernamePasswordToken;

import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.corelib.components.BeanEditForm;
import org.apache.tapestry5.ioc.annotations.Inject;

public class Login {
	@Inject
	private Authentication authentication ;
	
	@Property
	private UsernamePasswordToken usernamePasswordToken ;
	
	@InjectComponent
	private BeanEditForm beaneditform ;
	
	public Object onSuccess() {
		try {
			return authentication.authenticate(usernamePasswordToken.getUserName(), usernamePasswordToken.getPassword()) ;
		} catch (AuthenticationException e) {
			beaneditform.recordError(e.getMessage()) ;
			return null ;
		}
	}
}
