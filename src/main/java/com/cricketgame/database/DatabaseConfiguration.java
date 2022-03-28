package com.cricketgame.database;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;


@Data
@ConfigurationProperties
@Configuration
@PropertySource("classpath:dbconfig.properties")
public class DatabaseConfiguration {
    private String url;
    private String username;
    private String password;
}
