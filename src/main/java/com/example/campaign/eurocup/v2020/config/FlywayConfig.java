package com.example.campaign.eurocup.v2020.config;

import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.configuration.FluentConfiguration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
public class FlywayConfig {

	@Value("${spring.datasource.username}")
	private String username;
	@Value("${spring.datasource.password}")
	private String password;
	@Value("${spring.datasource.url}")
	private String url;

	@Profile(value = "!dbClean")
	@Bean(name = "flyway", initMethod = "migrate")
	public Flyway flywayNotADestroyer() {
		FluentConfiguration config = Flyway.configure();
		config.dataSource(url, username, password);
		config.baselineOnMigrate(true);
		config.cleanDisabled(true);
		return new Flyway(config);
	}

	@Profile(value = "dbClean")
	@Bean(name = "flyway", initMethod = "migrate")
	public Flyway flywayTheDestroyer() {
		FluentConfiguration config = Flyway.configure();
		config.dataSource(url, username, password);
		config.baselineOnMigrate(true);
		config.cleanOnValidationError(true);
		Flyway res = new Flyway(config);
		res.clean();
		return res;
	}
}
