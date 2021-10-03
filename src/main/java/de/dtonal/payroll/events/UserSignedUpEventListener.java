package de.dtonal.payroll.events;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import de.dtonal.payroll.mail.AuthMailSender;
import de.dtonal.payroll.model.VerificationToken;
import de.dtonal.payroll.model.auth.User;
import de.dtonal.payroll.repository.auth.VerifcationTokenRepository;

@Component
public class UserSignedUpEventListener implements ApplicationListener<UserSignedUpEvent> {
    @Autowired
    private AuthMailSender mailSender;
    @Autowired
    private VerifcationTokenRepository verifcationTokenRepository;

    @Override
    public void onApplicationEvent(UserSignedUpEvent event) {

        User user = event.getUser();
        VerificationToken token = VerificationToken.forUser(user);
        token.setToken(UUID.randomUUID().toString());
        verifcationTokenRepository.save(token);

        mailSender.sendWelcomeMessage(user, token);

    }

}
