package cz.muni.fi.pa165.pokemon.config;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import cz.muni.fi.pa165.pokemon.loader.DataLoader;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;
import javax.annotation.PostConstruct;
import javax.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.JstlView;
import org.springframework.web.servlet.view.UrlBasedViewResolver;

/**
 *
 * @author Milos Bartak
 */
@Configuration
@Import(value = cz.muni.fi.pa165.pokemon.context.ServiceConfiguration.class)
@ComponentScan("cz.muni.fi.pa165.pokemon")
@EnableWebMvc
public class Config extends WebMvcConfigurerAdapter {

    @Autowired
    private DataLoader dataLoader;

    @Bean
    public UrlBasedViewResolver setupViewResolver() {
        UrlBasedViewResolver resolver = new UrlBasedViewResolver();
        resolver.setPrefix("/WEB-INF/jsp/");
        resolver.setSuffix(".jsp");
        resolver.setViewClass(JstlView.class);
        return resolver;
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/resources/**").addResourceLocations("/WEB-INF/resources/");
    }

    /**
     * Bean with JSR-303 validator for validation of DTOs.
     *
     * @return validator bean factory
     */
    @Bean
    public Validator validator() {
        return new LocalValidatorFactoryBean();
    }

    @PostConstruct
    public void load() {
        dataLoader.loadData();
    }
}
