//package com.example.teamviewr.config;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import javax.sql.DataSource;
//
//@Configuration
//public class DataSourceConfig {
//
//    private static final Logger logger = LoggerFactory.getLogger(DataSourceConfig.class);
//
//    @Value("${spring.datasource.url}")
//    private String url;
//
//    @Bean
//    public DataSource dataSource(DataSourceProperties properties) {
//
//        logger.info("Datasource URL: {}", properties.getUrl());
//
//        return properties.initializeDataSourceBuilder().build();
//    }
//
//}