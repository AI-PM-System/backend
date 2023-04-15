package project.mainframe.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * The main application.
 */
@SpringBootApplication
public class ApiApplication {

	/**
     * No-args constructor
     */
	public ApiApplication() {}

	/**
	 * The main method.
	 * @param args The arguments
	 */
	public static void main(String[] args) {
		SpringApplication.run(ApiApplication.class, args);
	}
}
