package com.example.myanmarfontconverter.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
public class DataSourceConfig {

	@Value("${database.driver}")
	String driver;

	@Value("${database.user}")
	String username;

	@Value("${database.password}")
	String password;

	@Value("${database.name}")
	String databaseName;

	@Bean(name = "dsCustom")
	public DataSource dataSource() {
		return DataSourceBuilder.create().username(username).password(password)
				.url("jdbc:mysql://localhost:3306/" + databaseName
						+ "?useSSL=false&useUnicode=true&characterEncoding=UTF-8&serverTimezone=UTC")
				.driverClassName(driver).build();
	}

	@Bean
	@Autowired
	public JdbcTemplate jdbcTemplate(@Qualifier("dsCustom") DataSource dsCustom) {
		return new JdbcTemplate(dsCustom);
	}
}
