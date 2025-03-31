package com.secretaria_api;

import com.secretaria_api.service.MinioService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

@SpringBootApplication
public class SecretariaApiApplication {

	@Autowired
	MinioService minioService;

	public static void main(String[] args) {
		SpringApplication.run(SecretariaApiApplication.class, args);
	}

	@PostConstruct
	public void criarBucket() throws IOException {
		minioService.criarBucketSeNaoExistir();
	}
}
