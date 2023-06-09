package com.insta.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
public class AppConfig {

    @Bean
    public SecurityFilterChain securityConfiguration(HttpSecurity http) throws Exception {

        

    //     // http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
    //     //         .authorizeHttpRequests()
    //     //         .requestMatchers(HttpMethod.POST, "/signup")

    //     //         .permitAll()
    //     //         .anyRequest().authenticated()
    //     //         .and()
    //     //         .addFilterAfter(null, null)
    //     //         .addFilterBefore(null, null)
    //     //         .csrf().disable()
    //     //         .formLogin().httpBasic();

    //     http
    //             .csrf().disable()
    //             .sessionManagement()
    //             .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
    //             .and()
    //             .authorizeRequests()
    //             .antMatchers(HttpMethod.POST, "/signup").permitAll()
    //             .anyRequest().authenticated()
    //             .and()
    //             .addFilterAfter(null, null)
    //             .addFilterBefore(null, null)
    //             .formLogin()
    //             .and()
    //             .httpBasic();

        return http.build();
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
