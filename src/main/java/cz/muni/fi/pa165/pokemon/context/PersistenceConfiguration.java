package cz.muni.fi.pa165.pokemon.context;

import javax.sql.DataSource;
import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * This class cofigures all the beans necessary to provide persistance context
 *
 * @author Jaroslav Cechak
 */
@Configuration
@EnableTransactionManagement // same as <tx:annotation-driven/> in XML config
@EnableJpaRepositories
@ComponentScan(basePackages = {"cz.muni.fi.pa165.pokemon.dao"})
public class PersistenceConfiguration {

    /**
     * Creates {@link javax.sql.DataSource DataSource} object for an embedded
     * database (DERBY)
     *
     * @return datasource for an embedded database
     */
    @Bean
    public DataSource embeddedDataSource() {
        EmbeddedDatabase edb = new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.DERBY).build();
        return edb;
    }

    /**
     * Creates entity manager factory so that it can be injected and used to
     * create entity managers in DAO classes
     *
     * @return entity manager factory
     */
    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();

        emf.setDataSource(embeddedDataSource());
        emf.setPersistenceProviderClass(HibernatePersistenceProvider.class);

        return emf;
    }

    /**
     * Creates a transaction manager that is used by spring in classes/methods
     * annotated as @Transactional which
     * does not specify any transaction manager.
     *
     * @return transactio manager
     */
    @Bean
    public JpaTransactionManager transactionManager() {
        return new JpaTransactionManager(entityManagerFactory().getObject());
    }
}
