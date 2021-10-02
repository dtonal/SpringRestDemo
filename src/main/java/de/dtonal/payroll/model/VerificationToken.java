package de.dtonal.payroll.model;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

import de.dtonal.payroll.model.auth.User;

@Entity
public class VerificationToken {

    private static final int EXPIRATION = 60 * 24;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    private String token;

    @OneToOne
    @JoinColumn(nullable = false, name = "user_id")
    private User user;

    private LocalDateTime expiryDateTime;

    private LocalDateTime calculateExpiryDate(int expirationInMinutes) {
        LocalDateTime now = LocalDateTime.now();
        expiryDateTime = now.plus(expirationInMinutes, ChronoUnit.MINUTES);
        return expiryDateTime;
    }

    public VerificationToken() {
        // default constructor
    }

    public static VerificationToken forUser(User user) {
        VerificationToken token = new VerificationToken();
        token.expiryDateTime = token.calculateExpiryDate(EXPIRATION);
        token.user = user;
        return token;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDateTime getExpiryDateTime() {
        return expiryDateTime;
    }

    public void setExpiryDateTime(LocalDateTime expiryDateTime) {
        this.expiryDateTime = expiryDateTime;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
