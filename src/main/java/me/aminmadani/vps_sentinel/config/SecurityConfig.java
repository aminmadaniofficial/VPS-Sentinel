package me.aminmadani.vps_sentinel.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                /* Disable CSRF for simplified API interaction */
                .csrf(AbstractHttpConfigurer::disable)

                /* Define authorization rules using Lambda expressions */
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/v1/monitor/**", "/dashboard").authenticated()
                        .anyRequest().permitAll()
                )

                /* Configure default form-based login */
                .formLogin(form -> form
                        .defaultSuccessUrl("/dashboard", true)
                        .permitAll()
                )
                .formLogin(form -> form
                        .loginPage("/login") // Custom login page URL
                        .defaultSuccessUrl("/dashboard", true)
                        .permitAll()
                )
                /* Configure logout process */
                .logout(logout -> logout
                        .logoutSuccessUrl("/login?logout")
                        .permitAll()
                );


        return http.build();
    }

    @Bean
    public InMemoryUserDetailsManager userDetailsService() {
        /* Define a default administrative user for testing purposes */
        UserDetails user = User.withDefaultPasswordEncoder()
                .username("admin")
                .password("password123")
                .roles("ADMIN")
                .build();

        return new InMemoryUserDetailsManager(user);
    }
}