package br.pivetta.krash;

import br.pivetta.krash.config.FileStorageProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.web.config.EnableSpringDataWebSupport;

@SpringBootApplication
@EnableSpringDataWebSupport
@EnableCaching
@EnableConfigurationProperties({
		FileStorageProperties.class
})
public class KrashApplication {
	public static void main(String[] args) {
		SpringApplication.run(KrashApplication.class, args);
	}
}
