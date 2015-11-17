package cz.muni.fi.pa165.pokemon.context;

import cz.muni.fi.pa165.pokemon.mapping.TrainerIdCustomConverter;
import java.util.HashMap;
import java.util.Map;
import org.dozer.CustomConverter;
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

        Map<String, CustomConverter> customConverters
                = new HashMap<>();
        customConverters.put("trainerIdCustomConverter", trainerIdCustomConverter());

        dmf.setCustomConvertersWithId(customConverters);

        return dmf;
    }

    /**
     * Bean with custom converter between Trainer and his/her id used by dozer
     * mapper.
     *
     * @return trainer-id converter
     */
    @Bean
    public CustomConverter trainerIdCustomConverter() {
        return new TrainerIdCustomConverter();
    }
}
