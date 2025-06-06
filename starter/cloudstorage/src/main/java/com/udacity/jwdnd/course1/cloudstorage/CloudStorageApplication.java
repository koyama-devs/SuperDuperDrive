package com.udacity.jwdnd.course1.cloudstorage;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.storage.StorageProperties;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableConfigurationProperties(StorageProperties.class)
public class CloudStorageApplication {

	public static void main(String[] args) {
		SpringApplication.run(CloudStorageApplication.class, args);
	}
	@Bean
	CommandLineRunner init(FileService fileService) {
		return (args) -> {
			fileService.deleteAll();
			fileService.init();
		};
	}
}
