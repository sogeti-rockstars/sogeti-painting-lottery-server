package com.sogetirockstars.sogetipaintinglotteryserver.config;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
public class HibernateConf {
    @Value("${mockdata.create}")
    private boolean createMockdata;

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
        var jpaProps = new Properties();
        jpaProps.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect");
        jpaProps.setProperty("hibernate.format_sql", "true");

        if (createMockdata) {
            jpaProps.setProperty("hibernate.hbm2ddl.auto", "create-drop");
            vendorAdapter.setGenerateDdl(true);
        }

        factory.setJpaVendorAdapter(vendorAdapter);
        factory.setPackagesToScan("com.sogetirockstars.sogetipaintinglotteryserver.model");
        factory.setJpaProperties(jpaProps);
        factory.setDataSource(dataSource());
        return factory;
    }

    private DriverManagerDataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost:3306/hello_spring");
        dataSource.setUsername("root");
        dataSource.setPassword("root");
        return dataSource;
    }
}
