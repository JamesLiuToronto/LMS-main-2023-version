package com.cognifia.lms.account.infrastructure;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableJpaRepositories(basePackages = {"com.cognifia.lms"})
@PropertySource("classpath:in-memory-db.properties")
@EnableTransactionManagement
public class TestDbConfig {

    @Value("${test.jdbc.driverClassName}")
    private String driverClassName;

    @Value("${test.jdbc.url}")
    private String url;

    @Value("${test.jpa.hibernate.hbm2ddl.auto}")
    private String hbm2ddl;

    @Value("${test.jpa.hibernate.dialect}")
    private String dialect;

    @Value("${test.jpa.hibernate.show_sql}")
    private String showSql;

    @Value("${test.jpa.hibernate.cache.use_second_level_cache}")
    private String useSecondLevelCache;

    @Value("${test.jpa.hibernate.cache.use_query_cache}")
    private String useQueryCache;

    @Value("${test.jpa.hibernate.globally_quoted_identifiers}")
    private String globallyQuoatedIdentifiers;

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(driverClassName);
        dataSource.setUrl(url);
        dataSource.setUsername("sa");
        dataSource.setPassword("");

        return dataSource;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource) {
        LocalContainerEntityManagerFactoryBean entityManager = new LocalContainerEntityManagerFactoryBean();
        entityManager.setDataSource(dataSource);
        entityManager.setPackagesToScan("com.cognifia.lms");

        JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        entityManager.setJpaVendorAdapter(vendorAdapter);
        entityManager.setJpaProperties(additionalProperties());

        return entityManager;
    }

    private Properties additionalProperties() {
        final Properties hibernateProperties = new Properties();

        hibernateProperties.setProperty("hibernate.hbm2ddl.auto", hbm2ddl);
        hibernateProperties.setProperty("hibernate.dialect", dialect);
        hibernateProperties.setProperty("hibernate.show_sql", showSql);
        hibernateProperties.setProperty("hibernate.cache.use_second_level_cache", useSecondLevelCache);
        hibernateProperties.setProperty("hibernate.cache.use_query_cache", useQueryCache);
        hibernateProperties.setProperty("hibernate.globally_quoted_identifiers", globallyQuoatedIdentifiers);

        return hibernateProperties;
    }

    @Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactory emf) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(emf);
        return transactionManager;
    }
}
