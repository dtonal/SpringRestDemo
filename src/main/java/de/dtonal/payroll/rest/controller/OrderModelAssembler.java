package de.dtonal.payroll.rest.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import de.dtonal.payroll.model.Order;

@Component
public class OrderModelAssembler implements RepresentationModelAssembler<Order, EntityModel<Order>> {

	@Override
	public EntityModel<Order> toModel(Order entity) {
		return EntityModel.of(entity, //
				employeeLink(entity.getId()), linkTo(methodOn(OrderController.class).all()).withRel("orders"));

	}

	private Link employeeLink(Long id) {
		return linkTo(methodOn(OrderController.class).one(id)).withSelfRel();
	}

}
