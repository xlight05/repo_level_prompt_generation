package br.com.dyad.infrastructure.navigation.tests.entity;

import br.com.dyad.infrastructure.annotations.Test;
import br.com.dyad.infrastructure.aspect.UserInfoAspect;
import br.com.dyad.infrastructure.entity.User;
import br.com.dyad.infrastructure.unit.TestCase;
	

public class UserTest extends TestCase {
	
	@Test
	public void TesteCreateId() throws Exception{
		User u = new User();
		u.createId(UserInfoAspect.getDatabase());	
		assertTrue( u.getId() != null );
	}
	
}
