package de.dtonal.payroll.rest.controller.employee;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import de.dtonal.payroll.model.Employee;

@Component
public class EmployeeModelAssembler implements RepresentationModelAssembler<Employee, EntityModel<Employee>> {

	@Override
	public EntityModel<Employee> toModel(Employee entity) {
		return EntityModel.of(entity, //
				employeeLink(entity.getId()), linkTo(methodOn(EmployeeController.class).all()).withRel("employees"));

	}

	private Link employeeLink(Long id) {
		return linkTo(methodOn(EmployeeController.class).one(id)).withSelfRel();
	}

}
