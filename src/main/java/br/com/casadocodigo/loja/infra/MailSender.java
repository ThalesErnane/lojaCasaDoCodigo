package br.com.casadocodigo.loja.infra;

import javax.annotation.Resource;
import javax.enterprise.context.ApplicationScoped;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

@ApplicationScoped // dura durante todo escopo da aplicação
public class MailSender {
	
	/*
	 * No meu caso, foi o Avast Antivirus interferindo na conexão. Ações para desabilitar este recurso: Avast -> Configurações-> Componentes -> Mail Shield (Personalizar) -> Verificação SSL -> desmarque "Verificar conexões SSL".
	 * 
	 * */

	@Resource(mappedName = "java:/jboss/mail/gmail") // recursos do standalone-full.xml, mail-session, outbound-socket-binding
	private Session session;

	public void send(String from, String to, String subject, String body) {
		// especificação javaMail
		MimeMessage message = new MimeMessage(session);
		try {
		    message.setRecipients(javax.mail.Message.RecipientType.TO, InternetAddress.parse(to));

		    message.setFrom(new InternetAddress(from));
		    message.setSubject(subject);
		    message.setContent(body, "text/html");

		    Transport.send(message);
		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}

	}

}
