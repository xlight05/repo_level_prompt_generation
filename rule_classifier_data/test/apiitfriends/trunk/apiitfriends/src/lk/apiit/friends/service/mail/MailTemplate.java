package lk.apiit.friends.service.mail;

/**
 * Mail Template File Names.
 * 
 * @author Yohan Liyanage
 * @version 14-Sep-2008
 * @since 14-Sep-2008
 */
public enum MailTemplate {

	/**
	 * Registration Confirmation Email Template
	 */
	REGISTRATION_CONFIRM ("new_user");
	
	private String templateFile;

	/**
	 * Returns the template file name for the Mail Template.
	 * @return
	 */
	public String getTemplateFile() {
		return templateFile;
	}

	private MailTemplate(String templateFile) {
		this.templateFile = templateFile;
	}
	
	
}
