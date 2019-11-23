package com.amar.natural;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
public class DatasourceConfig {

//	@Bean(name="firstDataSource")
//	@Primary
//    @ConfigurationProperties(prefix = "spring.datasource")
//    public DataSource firstDataSource() {
//        return DataSourceBuilder.create().build();
//    }
//	
//	@Bean(name="secondDataSource")
//    @ConfigurationProperties(prefix = "spring.second.datasource")
//    public DataSource secondDataSource() {
//        return DataSourceBuilder.create().build();
//    }
//	
//	@Bean
//    public JdbcTemplate jdbcTemplateOne(@Qualifier("firstDataSource") DataSource ds) {
//        return new JdbcTemplate(ds);
//    }
//
//    @Bean
//    public JdbcTemplate jdbcTemplateTwo(@Qualifier("secondDataSource") DataSource ds) {
//        return new JdbcTemplate(ds);
//    }
	
}
