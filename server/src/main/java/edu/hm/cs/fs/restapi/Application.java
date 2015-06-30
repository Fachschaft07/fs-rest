package edu.hm.cs.fs.restapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


/**
 * This is the entry point class for the application.
 *
 * @author Fabio
 */
@SpringBootApplication
public class Application {
    /**
     * The main class is called to start the application.
     *
     * @param args
     *         could be filled with parameters for spring.
     */
    public static void main(final String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
