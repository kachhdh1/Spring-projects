package com.dk.utilities.db;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

/**
 * This class basically creates the connection for the MySQL database.
 */
@Configuration
@EnableTransactionManagement
@EnableEncryptableProperties
@EnableJpaRepositories(entityManagerFactoryRef = "auditEntityManagerFactory",
        transactionManagerRef = "auditTransactionManager",
        basePackages = {"com.dk.utilities.db.audit.repo"})
public class AuditDatasourceConfig {

    @Autowired
    private Environment environment;

    /**
     * The datasource for the MySQL connection which is created by looking up the details fron the configuration file.
     * The password is Jasypt encrypted using shared key which is stored inside JCEKS file.
     *
     * @return the DataSource for the MySQL db.
     */
    @Primary
    @Bean(name = "auditDataSource")
    public DataSource dataSource() {
        DriverManagerDataSource ds = new DriverManagerDataSource();
        ds.setDriverClassName(environment.getRequiredProperty("audit.datasource.driver-class-name"));
        ds.setUrl(environment.getRequiredProperty("audit.datasource.url"));
        ds.setUsername(environment.getRequiredProperty("audit.datasource.username"));
        ds.setPassword(environment.getRequiredProperty("audit.datasource.password"));

        System.out.println("audit.datasource.driver-class-name - " + environment.getRequiredProperty("audit" +
                ".datasource" +
                ".driver-class-name") + " audit.datasource.url - " + environment.getRequiredProperty("audit" +
                ".datasource.url") + " username - " + environment.getRequiredProperty("audit.datasource.username") +
                " " +
                "password - " + environment.getRequiredProperty("audit.datasource.password"));

        return ds;
    }

    /**
     * Create EntityManager for the datasource.
     *
     * @param builder    the EntityManagerFactoryBuilder
     * @param dataSource the DataSource
     * @return the EntityManager
     */
    @Bean(name = "auditEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean auditEntityManagerFactory(
            EntityManagerFactoryBuilder builder,
            @Qualifier("auditDataSource") DataSource dataSource) {
        return builder.dataSource(dataSource)
                .packages("com.dk.utilities.db.audit.domain")
                .persistenceUnit("audit").build();
    }

    /**
     * Returns the Transaction manager for the datasource.
     *
     * @param auditEntityManagerFactory the EntityManager
     * @return the transaction manager for the datasource
     */
    @Bean(name = "auditTransactionManager")
    public PlatformTransactionManager auditTransactionManager(
            @Qualifier("auditEntityManagerFactory") EntityManagerFactory auditEntityManagerFactory) {
        return new JpaTransactionManager(auditEntityManagerFactory);
    }

}
