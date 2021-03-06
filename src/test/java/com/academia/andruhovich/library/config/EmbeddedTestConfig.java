package com.academia.andruhovich.library.config;

import org.springframework.boot.autoconfigure.liquibase.LiquibaseProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.sql.DataSource;
import java.io.IOException;

@Configuration
public class EmbeddedTestConfig {

	@Bean
	public DataSource dataSource() {
		EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
		return builder.setType(EmbeddedDatabaseType.H2).build();
	}

	@Bean
	@Primary
	public LiquibaseProperties liquibaseProperties() {
		LiquibaseProperties properties = new LiquibaseProperties();
		properties.setChangeLog("classpath:changelog-master.xml");
		return properties;
	}
}
