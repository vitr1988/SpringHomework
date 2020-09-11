package ru.vtb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import ru.vtb.config.ApplicationProperties;

@SpringBootApplication
@EntityScan("ru.vtb.model.sql")
@EnableMongoRepositories
@EnableConfigurationProperties(ApplicationProperties.class)
public class Converter {

    public static void main(String[] args) {
        SpringApplication.run(Converter.class, args);
    }
}
