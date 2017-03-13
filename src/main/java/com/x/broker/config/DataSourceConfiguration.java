package com.x.broker.config;

import java.util.Properties;
import javax.naming.NamingException;
import javax.sql.DataSource;
import org.apache.commons.dbcp2.BasicDataSource;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jndi.JndiTemplate;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBuilder;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * A transactional data source configuration for SQL relational databases.
 *
 * @author Akis Papadopoulos
 */
@Configuration
@EnableTransactionManagement
@ComponentScan("com.x.broker.data")
@PropertySource("classpath:config.properties")
public class DataSourceConfiguration {

    @Autowired
    private Environment environment;

    @Bean
    @Profile("development")
    public SessionFactory developmentSessionFactory() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName(environment.getProperty("development.db.driver.class.name"));
        dataSource.setUrl(environment.getProperty("development.db.url"));
        dataSource.setUsername(environment.getProperty("development.db.username"));
        dataSource.setPassword(environment.getProperty("development.db.password"));
        dataSource.setMinIdle(environment.getProperty("development.db.min.idle", Integer.class));
        dataSource.setMaxIdle(environment.getProperty("development.db.max.idle", Integer.class));
        dataSource.setMaxTotal(environment.getProperty("development.db.max.active", Integer.class));

        LocalSessionFactoryBuilder sessionBuilder = new LocalSessionFactoryBuilder(dataSource);

        Properties properties = new Properties();
        properties.put("hibernate.show_sql", "true");
        properties.put("hibernate.dialect", environment.getProperty("development.db.dialect"));

        sessionBuilder.addProperties(properties);
        sessionBuilder.scanPackages("com.x.broker.domain");

        return sessionBuilder.buildSessionFactory();
    }

    @Bean
    @Profile("production")
    public SessionFactory productionSessionFactory() throws NamingException {
        JndiTemplate jndiTemplate = new JndiTemplate();
        String jndiName = environment.getProperty("production.db.jndi.name");
        DataSource dataSource = (DataSource) jndiTemplate.lookup(jndiName);

        LocalSessionFactoryBuilder sessionBuilder = new LocalSessionFactoryBuilder(dataSource);

        Properties properties = new Properties();
        properties.put("hibernate.show_sql", "false");
        properties.put("hibernate.dialect", environment.getProperty("production.db.dialect"));

        sessionBuilder.addProperties(properties);
        sessionBuilder.scanPackages("com.x.broker.domain");

        return sessionBuilder.buildSessionFactory();
    }

    @Bean
    @Autowired
    public HibernateTransactionManager transactionManager(SessionFactory sessionFactory) {
        HibernateTransactionManager transactionManager = new HibernateTransactionManager(sessionFactory);

        return transactionManager;
    }
}
