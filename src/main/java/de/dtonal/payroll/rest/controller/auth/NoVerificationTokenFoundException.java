package de.dtonal.payroll.rest.controller.auth;

public class NoVerificationTokenFoundException extends Exception {

    public NoVerificationTokenFoundException(String message) {
        super(message);
    }

}
