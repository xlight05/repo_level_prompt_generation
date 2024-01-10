package br.com.dyad.infrastructure.service;

import java.util.HashMap;

import br.com.dyad.infrastructure.email.Email;

public class SendMailService extends DyadService{
	@Override
	public HashMap getServiceValue(HashMap params) throws Exception {
		String emailContent = (String)params.get("emailContent");
		String subject = (String)params.get("subject");
		String[] recipients = (String[])params.get("recipients");
		
		Email email = new Email();
		email.setAuthenticate(true);
		email.setEmailContent(emailContent);
		email.setRecipients(recipients);
		email.setSmtpHost("smtp.gmail.com");
		email.setSubject(subject);
		
		//-- TODO a conta de usuário deve ser configurada na tabela de configurações do sistema.
		email.setEmailFrom("admin_dyadinfra@dyad.com.br");
		email.setUser("admin_dyadinfra@dyad.com.br");
		email.setPassword("dy4d1nfr41qa!QA");		
				
		email.send();
		
		return null;		
	}
}
