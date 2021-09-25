package de.dtonal.payroll.rest.controller;

public class OrderNotFoundException extends RuntimeException {
	OrderNotFoundException(Long id) {
		super("Could not find order " + id);
	}
}
