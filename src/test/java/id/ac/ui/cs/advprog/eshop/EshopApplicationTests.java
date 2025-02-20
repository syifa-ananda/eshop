package id.ac.ui.cs.advprog.eshop;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@SpringBootTest
class EshopApplicationTests {

	@Test
	void contextLoads() {
		/*
		 * This test is intentionally left empty because it is used by the
		 * Spring Boot testing framework to verify that the application
		 * context loads successfully. No additional checks are needed.
		 */
	}

	@Test
	void mainMethodTest() {
		/*
		 * This test is intentionally minimal. It invokes the main method to
		 * ensure that the application starts up without throwing exceptions.
		 * The assertDoesNotThrow assertion verifies that no exception is
		 * thrown during the execution of the main method.
		 */
		assertDoesNotThrow(() -> EshopApplication.main(new String[]{}));
	}
}
