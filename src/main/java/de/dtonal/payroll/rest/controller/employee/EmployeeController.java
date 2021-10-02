package de.dtonal.payroll.rest.controller.employee;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import de.dtonal.payroll.model.Employee;
import de.dtonal.payroll.repository.EmployeeRepository;

@RestController
@RequestMapping("/protected/api")
public class EmployeeController {

	@Autowired
	private EmployeeModelAssembler employeeModelAssembler;

	private final EmployeeRepository repository;

	public EmployeeController(EmployeeRepository repository) {
		super();
		this.repository = repository;
	}

	@GetMapping("/employees")
	CollectionModel<EntityModel<Employee>> all() {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		List<EntityModel<Employee>> employees = repository.findAll().stream().map(employeeModelAssembler::toModel)
				.collect(Collectors.toList());

		return CollectionModel.of(employees, linkTo(methodOn(EmployeeController.class).all()).withSelfRel());
	}

	@GetMapping("/employees/{id}")
	public EntityModel<Employee> one(@PathVariable Long id) {

		Employee employee = repository.findById(id) //
				.orElseThrow(() -> new EmployeeNotFoundException(id));

		return employeeModelAssembler.toModel(employee);
	}

	@DeleteMapping("/employees/{id}")
	public ResponseEntity<?> deleteEmployee(@PathVariable Long id) {

		repository.deleteById(id);

		return ResponseEntity.noContent().build();
	}

	@PutMapping("/employees/{id}")
	EntityModel<Employee> replaceEmployee(@RequestBody Employee newEmployee, @PathVariable Long id) {

		return repository.findById(id).map(employee -> {
			employee.setName(newEmployee.getName());
			employee.setRole(newEmployee.getRole());
			return employeeModelAssembler.toModel(repository.save(employee));
		}).orElseGet(() -> {
			newEmployee.setId(id);
			return employeeModelAssembler.toModel(repository.save(newEmployee));
		});
	}
}
