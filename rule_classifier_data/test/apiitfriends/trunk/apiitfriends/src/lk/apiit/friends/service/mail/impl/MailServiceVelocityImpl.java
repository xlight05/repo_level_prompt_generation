package lk.apiit.friends.service.mail.impl;

import java.util.Map;

import javax.mail.internet.MimeMessage;

import lk.apiit.friends.service.mail.MailService;
import lk.apiit.friends.service.mail.MailTemplate;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.ui.velocity.VelocityEngineUtils;

public class MailServiceVelocityImpl implements MailService {

	private static Log log = LogFactory.getLog(MailServiceVelocityImpl.class);

	private VelocityEngine velocityEngine;
	private JavaMailSender mailSender;
	private String templatePath;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void send(MailTemplate template,	final Map<String, Object> params, final boolean html) throws MailException {

		// Derive Template file location
		String templateFile = 	templatePath 
							+ 	"/" + template.getTemplateFile() 
							+ 	"_" + (html ? "html" : "text") + ".vm";
		
		// Apply template and retrieve text
		final String message = VelocityEngineUtils.mergeTemplateIntoString(	velocityEngine, 
																			templateFile, 
																			params);

		// Create a Spring MimeMessagePreparator to handle mail
		MimeMessagePreparator preparator = new MimeMessagePreparator() {

			@Override
			public void prepare(MimeMessage mimeMessage) throws Exception {
				MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
				helper.setTo(params.get("to").toString());
				helper.setFrom(params.get("from").toString());
				helper.setSubject(params.get("subject").toString());
				helper.setText(message, html);
			}

		};

		
		try {
			// Send Message
			mailSender.send(preparator);
			log.debug("Sent Mail [" + template + "] to " + params.get("to").toString());
		} catch (MailException e) {
			log.warn("Unable to send Mail", e);
			throw e;
		}

	}

	/**
	 * Sets the path of Velocity Template Files. This should be the relative
	 * path of the location with respect to the resource loader.
	 * 
	 * @param templatePath
	 *            template path
	 */
	public void setTemplatePath(String templatePath) {
		this.templatePath = templatePath;
	}

	/**
	 * Sets the Velocity Engine used for templating email messages.
	 * 
	 * @param velocityEngine
	 *            VelocityEngine implementation
	 */
	public void setVelocityEngine(VelocityEngine velocityEngine) {
		this.velocityEngine = velocityEngine;
	}

	/**
	 * Sets the Spring JavaMail Sender implementation used for sending emails.
	 * 
	 * @param mailSender
	 *            JavaMailSender
	 */
	public void setMailSender(JavaMailSender mailSender) {
		this.mailSender = mailSender;
	}

}
