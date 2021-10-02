package de.dtonal.payroll.mail;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import de.dtonal.payroll.security.User;

@Component
public class AuthMailSender {

	@Autowired
	private JavaMailSender javaMailSender;

	private static final Logger LOG = LoggerFactory.getLogger(AuthMailSender.class);

	public void sendWelcomeMessage(User user) {
		LOG.info("Send Welcome Message to user " + user);

		SimpleMailMessage msg = new SimpleMailMessage();
		msg.setTo(user.getEmail());

		msg.setSubject("Testing from Spring Boot");
		msg.setText("Hello HAHA World \n Spring Boot Email");

		javaMailSender.send(msg);
	}
}
