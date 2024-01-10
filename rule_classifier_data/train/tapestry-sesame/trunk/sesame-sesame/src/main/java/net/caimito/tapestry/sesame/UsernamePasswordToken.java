package net.caimito.tapestry.sesame;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root
public class UsernamePasswordToken implements AuthenticationToken {

	@Element
	private String userName ;
	
	@Element
	private String password;

	public UsernamePasswordToken() {
	}

	public UsernamePasswordToken(String username, String password) {
		this.userName = username ;
		this.password = password ;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String username) {
		this.userName = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
