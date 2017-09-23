package com.tide;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import org.flywaydb.core.Flyway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.List;
import java.util.Properties;
import java.util.TimeZone;

import javax.sql.DataSource;

@EnableWebMvc
@SpringBootApplication
public class FeatureSwitchApplication extends WebMvcConfigurerAdapter {

	@Value("${database.host}")
	private String databaseHost;

	@Value("${database.schema}")
	private String databaseSchema;

	private static final Logger LOGGER = LoggerFactory.getLogger(FeatureSwitchApplication.class);

	public static void main(String[] args) {
		SpringApplication springApplication = new SpringApplication(FeatureSwitchApplication.class);
		springApplication.run(args);
	}

	@Bean
	public DataSource dataSource() {
		LOGGER.info("Creating datasource using: " + databaseHost);

		HikariConfig hikariConfig = new HikariConfig();
		String timezone = TimeZone.getDefault().getID();
		hikariConfig.setDriverClassName("com.mysql.cj.jdbc.Driver");
		hikariConfig.setJdbcUrl("jdbc:mysql://" + databaseHost + "/" + databaseSchema +
				"?useUnicode=yes&characterEncoding=UTF-8&connectionCollation=utf8_general_ci" +
				"&useLegacyDatetimeCode=false&serverTimezone=" + timezone + "&useSSL=false");
		hikariConfig.setUsername("tide");
		hikariConfig.setPassword("feature");

		hikariConfig.setPoolName("database-connection-pool");
		hikariConfig.setMaxLifetime(600000);
		hikariConfig.setMaximumPoolSize(20);
		hikariConfig.setConnectionTimeout(10000);
		hikariConfig.setAutoCommit(false);
		hikariConfig.setLeakDetectionThreshold(360000);
		return new HikariDataSource(hikariConfig);
	}

	@Bean
	public Flyway flyway(DataSource dataSource) {
		Flyway flyway = new Flyway();
		flyway.setDataSource(dataSource);
		flyway.setLocations("classpath:database");
		flyway.migrate();
		return flyway;
	}

	@Bean
	public LocalSessionFactoryBean sessionFactory() {
		LocalSessionFactoryBean sessionFactoryBean = new LocalSessionFactoryBean();
		sessionFactoryBean.setDataSource(dataSource());
		sessionFactoryBean.setPackagesToScan("com.tide.domain");
		Properties hibernateProperties = new Properties();
		hibernateProperties.put("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect");
		hibernateProperties.put("hibernate.show_sql", true);
		sessionFactoryBean.setHibernateProperties(hibernateProperties);
		return sessionFactoryBean;
	}

	@Bean
	public ObjectMapper objectMapper() {
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.registerModule(new Jdk8Module());
		objectMapper.registerModule(new JavaTimeModule());
		objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
		objectMapper.enable(JsonGenerator.Feature.WRITE_BIGDECIMAL_AS_PLAIN);
		objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
		return objectMapper;
	}

	@Override
	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
		// Useful for tests to explicitly configure object mapper so we get consistent results
		converters.add(new MappingJackson2HttpMessageConverter(objectMapper()));
	}

}
