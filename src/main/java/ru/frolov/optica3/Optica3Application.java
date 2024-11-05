package ru.frolov.optica3;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.frolov.optica3.cache.Cache;

@SpringBootApplication
public class Optica3Application {

    public static void main(String[] args) {
        SpringApplication.run(Optica3Application.class, args);
    }


}
