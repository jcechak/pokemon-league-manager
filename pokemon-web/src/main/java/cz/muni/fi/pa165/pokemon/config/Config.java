package cz.muni.fi.pa165.pokemon.config;

import cz.muni.fi.pa165.pokemon.loader.DataLoader;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;  
import org.springframework.context.annotation.ComponentScan;  
import org.springframework.context.annotation.Configuration;  
import org.springframework.context.annotation.Import;
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
    
    @PostConstruct
    public void load() {
        dataLoader.loadData();
    }
}  
