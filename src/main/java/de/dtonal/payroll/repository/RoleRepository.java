package de.dtonal.payroll.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import de.dtonal.payroll.security.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {

}
