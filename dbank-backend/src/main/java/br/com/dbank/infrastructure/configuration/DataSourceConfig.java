package br.com.dbank.infrastructure.configuration;

import javax.sql.DataSource;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.flyway.autoconfigure.FlywayDataSource;
import org.springframework.boot.jdbc.autoconfigure.DataSourceProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class DataSourceConfig {

	@Bean
	@Primary
	@ConfigurationProperties("spring.datasource.postgres")
	public DataSourceProperties primaryDataSourcePropeties() {
		return new DataSourceProperties();
	}

	@Bean
	@Primary
	@FlywayDataSource
	public DataSource primaryDataSource() {
		return primaryDataSourcePropeties()
				.initializeDataSourceBuilder()
				.build();
	}
}
