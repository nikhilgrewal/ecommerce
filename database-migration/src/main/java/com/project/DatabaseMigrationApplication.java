package com.project;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication()
public class DatabaseMigrationApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(DatabaseMigrationApplication.class, args);
    }

    @Override
    public void run(String... args) {
    }
}