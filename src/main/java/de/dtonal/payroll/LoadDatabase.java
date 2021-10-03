package de.dtonal.payroll;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import de.dtonal.payroll.model.Employee;
import de.dtonal.payroll.model.Order;
import de.dtonal.payroll.model.Status;
import de.dtonal.payroll.model.auth.Role;
import de.dtonal.payroll.model.auth.RoleName;
import de.dtonal.payroll.model.auth.User;
import de.dtonal.payroll.repository.EmployeeRepository;
import de.dtonal.payroll.repository.OrderRepository;
import de.dtonal.payroll.repository.RoleRepository;
import de.dtonal.payroll.repository.UserRepository;

@Configuration
public class LoadDatabase {

	private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

	@Value("${spring.mail.username}")
	private String gmail;

	@Bean
	CommandLineRunner initDatabase(EmployeeRepository employeeRepository, OrderRepository orderRepository,
			RoleRepository roleRepository, UserRepository userRepository) {
		return args -> {
			log.info("Preloading: " + employeeRepository.save(new Employee("dtonal muu", "admin")));
			log.info("Preloading: " + employeeRepository.save(new Employee("maria maa", "user")));
			log.info("Preloading: " + orderRepository.save(new Order("Make me a coffee", Status.IN_PROGRESS)));
			log.info("Preloading: " + orderRepository.save(new Order("Wash my hair", Status.IN_PROGRESS)));

			Role adminRole = roleRepository.save(new Role(RoleName.ADMIN));
			log.info("Preloading Roles: " + adminRole);
			log.info("Preloading Roles: " + roleRepository.save(new Role(RoleName.USER)));

		};
	}
}
