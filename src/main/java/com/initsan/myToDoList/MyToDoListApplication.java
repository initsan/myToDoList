package com.initsan.myToDoList;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableAutoConfiguration
public class MyToDoListApplication {

	public static void main(String[] args) {
		SpringApplication.run(MyToDoListApplication.class, args);
	}

}
