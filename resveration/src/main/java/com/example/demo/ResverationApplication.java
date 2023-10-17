package com.example.demo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@MapperScan("com.example.demo.mapper")
@ComponentScan(basePackages = "com.example.demo") /* 만약 패키지를 못찾으면 직접 명시 해줄 것! */
public class ResverationApplication {

	public static void main(String[] args) {
		SpringApplication.run(ResverationApplication.class, args);
	}

}