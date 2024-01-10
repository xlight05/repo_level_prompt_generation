package lk.apiit.friends.service.mail;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MailServiceTest {

	private MailService service;
	
	@Before
	public void setUp() throws Exception {
		if (service==null) {
			ApplicationContext ctx = new ClassPathXmlApplicationContext("services-context.xml");
			service = (MailService) ctx.getBean("mailService");
		}
	}

	@Test
	public void testSend() {
		Map<String, Object> params = new HashMap<String, Object>();
		
		params.put("from", "APIIT Friends <noreply@apiitfriends.org>");
		params.put("to", "test@apiitfriends.org");
		params.put("subject", "Welcome to APIIT Friends - Please Confirm Account");
		params.put("siteurl", "http://localhost:8080/APIITFriends/");
		params.put("firstname", "Test");
		params.put("lastname", "User");
		params.put("username", "test");
		params.put("type", "Student");
		params.put("support_address", "support@apiitfriends.org");
		params.put("confirm_url", "http://localhost:8080/APIITFriends/reg/374894798/confirm");
		params.put("modify_url", "http://localhost:8080/APIITFriends/reg/374894798/modify");
		params.put("del_url", "http://localhost:8080/APIITFriends/reg/374894798/del");
		service.send(MailTemplate.REGISTRATION_CONFIRM, params, true);
	}

}
