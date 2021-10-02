package de.dtonal.payroll.mail;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import de.dtonal.payroll.model.auth.User;

@Component
public class MailContentCreator {

	private static final String WELCOME_MAIL_TEMPLATE = "auth/welcome-mail.html";

	private static final Logger LOG = LoggerFactory.getLogger(MailContentCreator.class);

	@Autowired
	private SpringTemplateEngine thymeleafTemplateEngine;

	public String createWelcomeMailBody(User user) {
		LOG.info("Create welcome mail body for user: " + user);
		Context thymeleafContext = new Context();
		thymeleafContext.setVariable("username", user.getUsername());
		thymeleafContext.setVariable("contactMail", "dtonal@posteo.de");

		String htmlBody = thymeleafTemplateEngine.process(WELCOME_MAIL_TEMPLATE, thymeleafContext);
		LOG.debug("HtmlBody is: " + htmlBody);
		return htmlBody;
	}

}
