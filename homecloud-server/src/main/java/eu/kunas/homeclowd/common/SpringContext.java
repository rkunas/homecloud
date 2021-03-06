package eu.kunas.homeclowd.common;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Created by ramazan on 29.04.15.
 */
@Configuration
@EnableTransactionManagement
@ComponentScan(value = "eu.kunas.homeclowd")
@EnableAsync
@EnableScheduling
public class SpringContext {

    // @Bean(destroyMethod = "shutdownDb", initMethod = "startDb")
    // public StarterOrientDb starterOrientDb(){
    //     return new StarterOrientDb();
    // }

    @Bean
    public DriverManagerDataSource datasource() {

        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUrl("jdbc:postgresql://localhost:5432/db_homeclowd");
        dataSource.setUsername("homeclowd");
        dataSource.setPassword("homeclowd");

        return dataSource;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {

        HibernateJpaVendorAdapter hibernateJpaVendorAdapter = new HibernateJpaVendorAdapter();
        hibernateJpaVendorAdapter.setShowSql(true);
        hibernateJpaVendorAdapter.setGenerateDdl(true);
        hibernateJpaVendorAdapter.setDatabasePlatform("org.hibernate.dialect.PostgreSQL9Dialect");

        LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();

        entityManagerFactoryBean.getJpaPropertyMap().put("hibernate.enable_lazy_load_no_trans", true);
        entityManagerFactoryBean.setPackagesToScan("eu.kunas.homeclowd.common.model");
        entityManagerFactoryBean.setDataSource(datasource());
        entityManagerFactoryBean.setJpaVendorAdapter(hibernateJpaVendorAdapter);

        return entityManagerFactoryBean;
    }

    @Bean
    public JpaTransactionManager transactionManager() {
        JpaTransactionManager jpaTransactionManager = new JpaTransactionManager();
        jpaTransactionManager.setEntityManagerFactory(entityManagerFactory().getNativeEntityManagerFactory());

        return jpaTransactionManager;
    }
}
