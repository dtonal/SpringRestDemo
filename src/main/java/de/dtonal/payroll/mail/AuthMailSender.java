package de.dtonal.payroll.mail;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import de.dtonal.payroll.security.User;

@Component
public class AuthMailSender {

	@Autowired
	private JavaMailSender javaMailSender;

	@Autowired
	private MailContentCreator mailContentCreator;

	private static final Logger LOG = LoggerFactory.getLogger(AuthMailSender.class);

	public void sendWelcomeMessage(User user) {
		LOG.info("Send Welcome Message to user " + user);

		try {
			sendHtmlMessage(user.getEmail(), "Welcome to MYAPP", mailContentCreator.createWelcomeMailBody(user));
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void sendHtmlMessage(String to, String subject, String htmlBody) throws MessagingException {
		MimeMessage message = javaMailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
		helper.setTo(to);
		helper.setSubject(subject);
		helper.setText(htmlBody, true);
		javaMailSender.send(message);
	}
}
