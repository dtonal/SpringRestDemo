package de.dtonal.payroll.rest.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import de.dtonal.payroll.model.Employee;
import de.dtonal.payroll.repository.EmployeeRepository;

@RestController
public class EmployeeController {
	private final EmployeeRepository repository;

	public EmployeeController(EmployeeRepository repository) {
		super();
		this.repository = repository;
	}

	@GetMapping("/employees")
	public List<Employee> all() {
		return repository.findAll();
	}
}
