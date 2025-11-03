package com.example.demoSpringCK.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
    @Bean
    SecurityFilterChain filterChain (HttpSecurity http) throws Exception {

        http
                .authorizeHttpRequests(auth->auth
                        .requestMatchers("login").permitAll()
                        .requestMatchers(HttpMethod.GET, "/products/new").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/products/new").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/products/edit/*").hasAnyRole("ADMIN", "STAFF")
                        .requestMatchers(HttpMethod.POST, "/products/edit/*").hasAnyRole("ADMIN", "STAFF")
                        .requestMatchers(HttpMethod.POST, "/products/delete/*").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/products", "/products/*").permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin(form->form.defaultSuccessUrl("/products", true).permitAll())
                .logout(logout -> logout.permitAll());

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    UserDetailsManager users(){
        PasswordEncoder encoder = passwordEncoder();
        UserDetails admin = User.withUsername("admin").password(encoder.encode("123")).roles("ADMIN","STAFF").build();
        UserDetails staff = User.withUsername("staff").password(encoder.encode("123")).roles("STAFF").build();
        return new InMemoryUserDetailsManager(admin,staff);
    }


}
