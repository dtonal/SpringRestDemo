package de.dtonal.payroll.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import de.dtonal.payroll.model.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {

}
