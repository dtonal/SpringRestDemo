package de.dtonal.payroll.rest.controller.order;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import de.dtonal.payroll.model.Order;
import de.dtonal.payroll.model.Status;

@Component
public class OrderModelAssembler implements RepresentationModelAssembler<Order, EntityModel<Order>> {

	@Override
	public EntityModel<Order> toModel(Order entity) {
		EntityModel<Order> entityModel = EntityModel.of(entity, //
				employeeLink(entity.getId()), linkTo(methodOn(OrderController.class).all()).withRel("orders"));

		if (Status.IN_PROGRESS == entity.getStatus()) {
			entityModel.add(linkTo(methodOn(OrderController.class).cancel(entity.getId())).withRel("Cancel"));
			entityModel.add(linkTo(methodOn(OrderController.class).complete(entity.getId())).withRel("Complete"));
		}

		return entityModel;

	}

	private Link employeeLink(Long id) {
		return linkTo(methodOn(OrderController.class).one(id)).withSelfRel();
	}

}
