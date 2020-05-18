package com.code2bits.maven.examples;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class SimpleConsoleApplication implements CommandLineRunner {


	private static Logger LOG = LoggerFactory.getLogger(SimpleConsoleApplication.class);


	public static void main(String[] args) {
		LOG.info("Spring Application Start");
		SpringApplication.run(SimpleConsoleApplication.class, args);
		LOG.info("Spring Application Complete");
	}


	@Override
	public void run(String... args) {
		LOG.info("Printing Command Line Arguments: ");

		for (int i = 0; i < args.length; ++i) {
			LOG.info("args[{}]: {}", i, args[i]);
		}
	}


}
