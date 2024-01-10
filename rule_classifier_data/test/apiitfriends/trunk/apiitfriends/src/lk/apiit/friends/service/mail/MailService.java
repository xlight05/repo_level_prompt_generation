package lk.apiit.friends.service.mail;

import java.util.Map;

/**
 * Mail Delivery Service Interface.
 * 
 * @author Yohan Liyanage
 * @version 14-Sep-2008
 * @since 14-Sep-2008
 */
public interface MailService {

	/**
	 * Sends an email using the specified template and set of properties.
	 * @param template MailTemplate
	 * @param params Parameters for Template
	 * @param html whether the mail is html or text only
	 */
	public void send(MailTemplate template, Map<String, Object> params, boolean html);
	
}
