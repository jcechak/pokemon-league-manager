package cz.muni.fi.pa165.pokemon.security;

import cz.muni.fi.pa165.pokemon.controllers.PokemonController;
import cz.muni.fi.pa165.pokemon.controllers.WebApiUris;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 *
 * @author Milos Bartak
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    public void configureGlobalSecurity(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication().passwordEncoder(passwordEncoder()).withUser("admin")
                .password("$2a$10$op4BpvQGIwznk6m.Iy2XJ.WjT2B6vLB3gfRTctwwXg9M5Ev051FUy").roles("ADMIN");

        auth.inMemoryAuthentication().passwordEncoder(passwordEncoder()).withUser("user")
                .password("$2a$10$Rx88swAmqtrJ0glEdiulLuLESZE3UmP2OZZXvbfxMA.Eq6Fjxny7K").roles("USER");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/resources/**").permitAll()
                .antMatchers(PokemonController.DELETE_URI + "/**",
                        PokemonController.CHANGE_SKILL_URI + "/**",
                        PokemonController.CHANGE_TRAINER_URI + "/**",
                        PokemonController.CREATE_URI + "/**", 
                        PokemonController.TRADE_URI+ "/**").hasRole("ADMIN") // only admin can change pokemons
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .permitAll();

        http
                .logout()
                .logoutSuccessUrl("/logout")
                .invalidateHttpSession(true);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
