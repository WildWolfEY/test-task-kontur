package ru.home.textRedactor.spring;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.home.textRedactor.core.*;

import java.io.FileNotFoundException;

@Configuration
public class Config {

    @Value("${file.path}")
    private String filePath;
    @Value("${file.out.path}")
    private String outFilePath;

    @Bean(initMethod = "read")
    public Reader getReader() throws FileNotFoundException {
        try {
            return new Reader(filePath);
        } catch (Exception ex) {
            return new Reader(filePath);
        }
    }

    @Bean

    public InitialData getInitialData() {
        return new InitialData();
    }

    @Bean
    public Sieve getSieve() {
        return new Sieve();
    }

    @Bean
    public Searcher getSearcher() {
        return new Searcher();
    }
}
