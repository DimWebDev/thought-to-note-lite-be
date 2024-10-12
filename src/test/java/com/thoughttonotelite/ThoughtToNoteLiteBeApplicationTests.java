package com.thoughttonotelite;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.DynamicPropertyRegistry;

/**
 * Integration tests for the ThoughtToNoteLiteBeApplication.
 * <p>
 * This class ensures that the Spring ApplicationContext loads correctly and that the application
 * can interact with the PostgreSQL database managed by Testcontainers.
 * </p>
 */
@SpringBootTest
@Testcontainers
public class ThoughtToNoteLiteBeApplicationTests {

	/**
	 * Defines a PostgreSQL container that will be shared across all tests in this class.
	 */
	@Container
	public static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:16")
			.withDatabaseName("thoughttnotelitedb")
			.withUsername("postgres")
			.withPassword("postgres");

	/**
	 * Dynamically sets the Spring datasource properties to match the Testcontainers PostgreSQL instance.
	 *
	 * @param registry the dynamic property registry
	 */
	@DynamicPropertySource
	static void configureProperties(DynamicPropertyRegistry registry) {
		registry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl);
		registry.add("spring.datasource.username", postgreSQLContainer::getUsername);
		registry.add("spring.datasource.password", postgreSQLContainer::getPassword);
		registry.add("spring.jpa.hibernate.ddl-auto", () -> "update"); // or your preferred setting
	}

	/**
	 * Verifies that the Spring ApplicationContext loads successfully.
	 */
	@Test
	void contextLoads() {
		// Test logic can be added here if needed
	}
}
