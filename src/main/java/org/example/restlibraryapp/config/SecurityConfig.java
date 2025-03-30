package org.example.restlibraryapp.config;

import org.example.restlibraryapp.service.auth.MyUserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Configuration Class to Enable Spring Security features for the all the endpoints
 */

@Configuration
public class SecurityConfig {
    // Security Configuration

    private MyUserService myUserService;

    public SecurityConfig(MyUserService myUserService) {
        this.myUserService = myUserService;
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService());
        provider.setPasswordEncoder(new BCryptPasswordEncoder());
        return provider;
    }


    @Bean
    public UserDetailsService userDetailsService() {
        return myUserService;
    }

    // Configuring Roles

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        // Grant authorization to users based on their roles
        httpSecurity.authorizeHttpRequests(
                securityConfigurer ->
                        securityConfigurer
                                // ADMINS - securing endpoints that only admins have access to


                                .requestMatchers(HttpMethod.POST, "/library/register-user").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.PUT, "/library/update-user/**").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.DELETE, "/library/delete-user/**").hasRole("ADMIN")

                                .requestMatchers(HttpMethod.PUT, "/library/member/**").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.DELETE, "/library/member/**").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.POST, "/library/member/register").hasRole("ADMIN")

                                // ADMINS AND LIBRARIANS

                                .requestMatchers(HttpMethod.POST,"/library/book", "/library/author", "/library/author/**/add-book/**").hasAnyRole("ADMIN", "LIBRARIAN")
                                .requestMatchers(HttpMethod.GET, "/library/member/**", "/library/book/**", "/library/author/**", "/library/borrowed-books/", "/library/borrowed-books/**").hasAnyRole("ADMIN",  "LIBRARIAN")
                                .requestMatchers(HttpMethod.PUT,  "/library/book/**", "/library/author/**").hasAnyRole("ADMIN", "LIBRARIAN")
                                .requestMatchers(HttpMethod.DELETE,  "/library/book/**", "/library/author/**", "/library/author/**/remove-book/**").hasAnyRole("ADMIN", "LIBRARIAN")
                                // LIBRARIANS AND LIBRARY MEMBERS
                                .requestMatchers(HttpMethod.POST,"/library/borrowed-books/borrow-book/**").hasRole("LIBRARIAN")
                                .requestMatchers(HttpMethod.DELETE,"/library/borrowed-books/return-book/**").hasRole("LIBRARIAN")
                                // LIBRARY MEMBER
                                .requestMatchers(HttpMethod.GET,"/library/borrowed-books/{member_id}").hasRole("LIBRARYMEMBER")
                                .requestMatchers(HttpMethod.POST,"/library/borrowed-books/borrow-book/{member_id}").hasRole("LIBRARYMEMBER")
                                .requestMatchers(HttpMethod.DELETE,"/library/borrowed-books/return-book/{member_id}").hasRole("LIBRARIAN")
                                // EVERYONE
                                .requestMatchers("/library/member/register").permitAll()
                                .requestMatchers(HttpMethod.GET,"/library/book/**", "/library/book").permitAll()
        );

        httpSecurity.httpBasic(Customizer.withDefaults());

        // Disable csrf
        httpSecurity.csrf(httpSecurityCsrfConfigurer -> httpSecurityCsrfConfigurer.disable());

        return httpSecurity.build();



    }
}
