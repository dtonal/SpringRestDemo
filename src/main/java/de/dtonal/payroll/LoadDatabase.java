package de.dtonal.payroll;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import de.dtonal.payroll.model.Employee;
import de.dtonal.payroll.model.Order;
import de.dtonal.payroll.model.Status;
import de.dtonal.payroll.repository.EmployeeRepository;
import de.dtonal.payroll.repository.OrderRepository;

@Configuration
public class LoadDatabase {

	private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

	@Bean
	CommandLineRunner initDatabase(EmployeeRepository employeeRepository, OrderRepository orderRepository) {
		return args -> {
			log.info("Preloading: " + employeeRepository.save(new Employee("dtonal muu", "admin")));
			log.info("Preloading: " + employeeRepository.save(new Employee("maria maa", "user")));
			log.info("Preloading: " + orderRepository.save(new Order("Make me a coffee", Status.IN_PROGRESS)));
			log.info("Preloading: " + orderRepository.save(new Order("Wash my hair", Status.IN_PROGRESS)));

		};
	}
}
