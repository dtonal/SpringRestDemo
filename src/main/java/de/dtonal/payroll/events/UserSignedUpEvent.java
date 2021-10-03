package de.dtonal.payroll.events;

import org.springframework.context.ApplicationEvent;

import de.dtonal.payroll.model.auth.User;

public class UserSignedUpEvent extends ApplicationEvent {
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public UserSignedUpEvent(User user) {
        super(user);
        this.user = user;
    }

}
