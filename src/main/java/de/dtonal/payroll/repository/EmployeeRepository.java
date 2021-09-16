package de.dtonal.payroll.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import de.dtonal.payroll.model.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

}
