package kuit.springbasic;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

// spring security 비활성화
@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class SpringBasicApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBasicApplication.class, args);
	}

}
