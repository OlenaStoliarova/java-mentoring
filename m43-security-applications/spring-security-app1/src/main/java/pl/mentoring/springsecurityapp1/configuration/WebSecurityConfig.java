package pl.mentoring.springsecurityapp1.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
            .antMatchers("/about").not().fullyAuthenticated()
            .antMatchers("/admin").hasRole("ADMIN")
            .antMatchers("/info").hasRole("USER")
            .antMatchers("/", "/css/**").permitAll()
            .anyRequest().authenticated()
            .and()
                .formLogin().loginPage("/login")
                .permitAll()
                .defaultSuccessUrl("/main")
            .and()
                .logout()
                .permitAll()
                .logoutSuccessUrl("/");

        return http.build();
    }

    @Bean
    public UserDetailsService users(DataSource dataSource) {
        return new JdbcUserDetailsManager(dataSource);
    }

    @Bean
    public PasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
