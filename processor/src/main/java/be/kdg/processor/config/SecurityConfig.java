package be.kdg.processor.config;

import be.kdg.processor.services.impl.modelservices.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserService userService;

    @Autowired
    public SecurityConfig(BCryptPasswordEncoder bCryptPasswordEncoder, UserService userService) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userService = userService;
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
        auth.setUserDetailsService(userService);
        auth.setPasswordEncoder(bCryptPasswordEncoder);
        return auth;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(authenticationProvider());
    }

    /**
     * Configure which controller methods are secured and how to login.
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.
                authorizeRequests()
                    .antMatchers("/").permitAll()
                    .antMatchers("/api/**").permitAll()
                    .antMatchers("/user/login").permitAll()
                    .antMatchers("/user/register").permitAll()
                    .antMatchers("/app/**", "/option/**", "/user/menu").hasAuthority("ADMIN").anyRequest().authenticated()
                    .and().csrf().disable()
                .formLogin().loginPage("/user/login")
                    .failureUrl("/user/login?error=true")
                    .defaultSuccessUrl("/user/menu", true)
                    .and()
                .logout()
                    .logoutSuccessUrl("/");
    }

    /**
     * Define which resources should not be secured.
     */
    @Override
    public void configure(WebSecurity web) {
        web
                .ignoring()
                .antMatchers("/resources/**", "/static/**", "/fonts/**", "/css/**", "/js/**", "/images/**", "/h2-console/**");
    }
}
