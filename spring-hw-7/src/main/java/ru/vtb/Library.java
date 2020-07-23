package ru.vtb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Library {

    /**
     * Starting point for application
     *
     * @param args input arguments
     * @throws Exception raised exceptions
     */
    public static void main(String[] args) throws Exception {
        SpringApplication.run(Library.class, args);
    }
}
