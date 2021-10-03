package de.dtonal.payroll.rest.controller.employee;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
import java.util.Optional;
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
import de.dtonal.payroll.model.auth.User;
import de.dtonal.payroll.repository.EmployeeRepository;
import de.dtonal.payroll.repository.UserRepository;
import de.dtonal.payroll.security.user.UserPrincipal;

@RestController
@RequestMapping("/protected/api")
public class EmployeeController {

	@Autowired
	private EmployeeModelAssembler employeeModelAssembler;
	@Autowired
	private UserRepository userRepository;

	private final EmployeeRepository repository;

	public EmployeeController(EmployeeRepository repository) {
		super();
		this.repository = repository;
	}

	@GetMapping("/employees")
	CollectionModel<EntityModel<Employee>> all() {
		User callingUser = getCallingUser(SecurityContextHolder.getContext().getAuthentication().getPrincipal());

		List<EntityModel<Employee>> employees = repository.findAll()//
				.stream()//
				.filter(employee -> employee.getOwner().equals(callingUser))//
				.map(employeeModelAssembler::toModel)//
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
		User callingUser = getCallingUser(SecurityContextHolder.getContext().getAuthentication().getPrincipal());

		Optional<Employee> foundById = repository.findById(id);

		if (foundById.isPresent()) {
			Employee employee = foundById.get();
			if (!employee.getOwner().equals(callingUser)) {
				// TODO handle with exception
				throw new RuntimeException("wrong");
			} else {
				employee.setName(newEmployee.getName());
				employee.setRole(newEmployee.getRole());
				return employeeModelAssembler.toModel(repository.save(employee));
			}
		} else {
			newEmployee.setId(id);
			newEmployee.setOwner(callingUser);
			return employeeModelAssembler.toModel(repository.save(newEmployee));
		}
	}

	private User getCallingUser(Object principal) {
		UserPrincipal userPrincipal = (UserPrincipal) principal;
		return userRepository.getById(userPrincipal.getUserId());
	}
}
