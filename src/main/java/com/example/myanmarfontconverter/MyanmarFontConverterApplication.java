package com.example.myanmarfontconverter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin(origins = "http://localhost:8096")
@SpringBootApplication
public class MyanmarFontConverterApplication {

	public static void main(String[] args) {
		SpringApplication.run(MyanmarFontConverterApplication.class, args);
	}

}
