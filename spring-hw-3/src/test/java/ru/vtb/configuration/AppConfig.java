package ru.vtb.configuration;

import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.io.InputStream;
import java.io.PrintStream;

@Configuration
@ComponentScan("ru.vtb.service")
public class AppConfig {

    @Bean
    public InputStream inputStream() {
        return Mockito.mock(InputStream.class);
    }

    @Bean
    public PrintStream printStream() {
        return Mockito.mock(PrintStream.class);
    }
}
