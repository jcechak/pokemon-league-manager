package cz.muni.fi.pa165.pokemon.security;

import cz.muni.fi.pa165.pokemon.controllers.PokemonController;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.channel.ChannelProcessingFilter;
import org.springframework.web.filter.CharacterEncodingFilter;

/**
 *
 * @author Milos Bartak
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private DataSource datasource;
    
    @Autowired
    public void configureGlobalSecurity(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication().dataSource(datasource).passwordEncoder(passwordEncoder())
                .usersByUsernameQuery("select username,password,enabled from AppUser where username=?")
                .authoritiesByUsernameQuery("select username,role from AppUser where username=?");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/resources/**").permitAll()
                .antMatchers(PokemonController.DELETE_URI + "/**",
                        PokemonController.CHANGE_SKILL_URI + "/**",
                        PokemonController.CHANGE_TRAINER_URI + "/**",
                        PokemonController.CREATE_URI + "/**",
                        PokemonController.TRADE_URI + "/**").hasRole("ADMIN") // only admin can change pokemons
                .antMatchers("/rest/**").hasRole("ADMIN") // allow only admin to use rest api
                .antMatchers("/menu/trainer/delete/**",
                        "/menu/trainer/edit/**",
                        "/menu/trainer/update/**",
                        "/menu/trainer/new/**",
                        "/menu/trainer/create/**").hasRole("ADMIN") // only admin can change trainers                
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .permitAll();

        http
                .logout()
                .logoutSuccessUrl("/logout")
                .invalidateHttpSession(true);
        http
                // Sets character encoding to POST parameters for correct conversion. This must be done as the first thing (before any security filters kick in) otherwise it has no effect at all.
                .addFilterBefore(new CharacterEncodingFilter("UTF-8"), ChannelProcessingFilter.class);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
