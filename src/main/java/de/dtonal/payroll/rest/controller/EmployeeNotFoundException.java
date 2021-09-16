package de.dtonal.payroll.rest.controller;

public class EmployeeNotFoundException extends RuntimeException {
	EmployeeNotFoundException(Long id) {
		super("Could not find employee " + id);
	}
}
