package pl.sklepelektroniczny.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import pl.sklepelektroniczny.app.repositories.UzytkownikRepository;

@SpringBootApplication
@EnableJpaRepositories(basePackageClasses = UzytkownikRepository.class)
public class AppApplication {

	public static void main(String[] args) {
		SpringApplication.run(AppApplication.class, args);
	}

}
