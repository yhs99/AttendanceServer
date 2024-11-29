package com.attendance.attendanceserver.api.infra;

import javax.sql.DataSource;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class TestDataSourceConfig {
	@Bean
	public DataSource dataSource() {
		return DataSourceBuilder.create()
			.url("jdbc:h2:mem:testdb")
			.driverClassName("org.h2.Driver")
			.username("sa")
			.password("")
			.build();
	}
}
