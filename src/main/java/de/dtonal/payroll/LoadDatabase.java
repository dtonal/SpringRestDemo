package de.dtonal.payroll;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import de.dtonal.payroll.model.Employee;
import de.dtonal.payroll.repository.EmployeeRepository;

@Configuration
public class LoadDatabase {

	private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

	@Bean
	CommandLineRunner initDatabase(EmployeeRepository employeeRepository) {
		return args -> {
			log.info("Preloading: " + employeeRepository.save(new Employee("dtonal", "admin")));
			log.info("Preloading: " + employeeRepository.save(new Employee("maria", "user")));
		};
	}
}
