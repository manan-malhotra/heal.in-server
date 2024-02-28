package in.app.heal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class HealApplication {

	public static void main(String[] args) {
		SpringApplication.run(HealApplication.class, args);
	}

}
