package com.hit.jpa.config;

import com.zaxxer.hikari.HikariDataSource;
import jakarta.persistence.EntityManagerFactory;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Log4j2
@Configuration
@ConditionalOnProperty(
        value = {"app.datasource.default.enable"},
        havingValue = "true"
)
@EnableTransactionManagement
@EnableJpaRepositories(
        entityManagerFactoryRef = "defaultEntityManagerFactory",
        transactionManagerRef = "defaultTransactionManager",
        basePackages = {"com.hit"}
)
public class DataSourceDefaultConfig {

    @Value("${app.datasource.default.url:#{null}}")
    private String urlForLog;

    @Primary
    @Bean(name = {"defaultDataSourceProperties"})
    @ConfigurationProperties("app.datasource.default")
    public DataSourceProperties dataSourceProperties() {
        return new DataSourceProperties();
    }

    @Primary
    @Bean(name = {"defaultDataSource"})
    @ConfigurationProperties(prefix = "app.datasource.default.configuration")
    public DataSource dataSource() {
        return this.dataSourceProperties().initializeDataSourceBuilder().type(HikariDataSource.class).build();
    }

    @Bean(name = {"defaultPropertiesHibernate"})
    @ConfigurationProperties(prefix = "app.datasource.default.properties")
    public Map<String, String> dataProperties() {
        return new HashMap<>();
    }

    @Primary
    @Bean(name = {"defaultEntityManagerFactory"})
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(EntityManagerFactoryBuilder builder) {
        log.info("DB config defaultDataSource: {}", this.urlForLog);
        return builder.dataSource(this.dataSource())
                .properties(this.dataProperties())
                .packages("com.hit")
                .build();
    }

    @Primary
    @Bean(name = {"defaultTransactionManager"})
    public PlatformTransactionManager transactionManager(@Qualifier("defaultEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }
}