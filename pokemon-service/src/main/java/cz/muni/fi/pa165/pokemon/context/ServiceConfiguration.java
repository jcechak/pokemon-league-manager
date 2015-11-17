package cz.muni.fi.pa165.pokemon.context;

import org.dozer.spring.DozerBeanMapperFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * This class cofigures all the beans necessary to provide service layer with
 * spring context
 *
 * @author Jaroslav Cechak
 */
@Configuration
@Import(value = cz.muni.fi.pa165.pokemon.context.PersistenceConfiguration.class)
@EnableTransactionManagement // same as <tx:annotation-driven/> in XML config
@ComponentScan(basePackages = {"cz.muni.fi.pa165.pokemon.service"})
public class ServiceConfiguration {

    /**
     * Bean factory for dozer mappers with already predefined custom mappings.
     *
     * @return dozer mapper factory
     */
    @Bean
    public DozerBeanMapperFactoryBean dozerFactory() {
        DozerBeanMapperFactoryBean dmf = new DozerBeanMapperFactoryBean();

        Resource[] resourceArray
                = {new ClassPathResource("dozerMappings/pokemonMapping.xml")};
        dmf.setMappingFiles(resourceArray);

        return dmf;
    }
}
