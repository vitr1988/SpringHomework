package ru.vtb.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;

@ComponentScan("ru.vtb")
@PropertySource("classpath:poll.properties")
public class ApplicationConfiguration {
}
