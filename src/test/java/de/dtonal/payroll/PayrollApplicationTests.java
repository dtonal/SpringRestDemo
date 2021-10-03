package de.dtonal.payroll;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class PayrollApplicationTests {
	static {
		System.setProperty("GMAIL_USERNAME", "foo");
		System.setProperty("GMAIL_PASSWORD", "foo");
	}

	@Test
	void contextLoads() {
	}

}
