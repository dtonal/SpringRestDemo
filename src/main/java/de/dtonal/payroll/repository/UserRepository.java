package de.dtonal.payroll.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import de.dtonal.payroll.model.auth.User;

public interface UserRepository extends JpaRepository<User, Long> {
	public Optional<User> findByUsername(String username);
}
