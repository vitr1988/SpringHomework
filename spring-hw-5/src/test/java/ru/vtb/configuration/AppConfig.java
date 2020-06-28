package ru.vtb.configuration;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.ComponentScan;

@SpringBootConfiguration
@ComponentScan("ru.vtb.dao")
public class AppConfig {
}
