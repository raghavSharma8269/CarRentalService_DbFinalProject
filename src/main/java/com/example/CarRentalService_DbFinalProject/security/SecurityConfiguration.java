package com.example.CarRentalService_DbFinalProject.security;

import com.example.CarRentalService_DbFinalProject.security.CustomUserDetailsService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;

@Configuration
@EnableMethodSecurity
public class SecurityConfiguration {

    @Bean
    public AuthenticationManager authenticationManager(
            HttpSecurity http,
            CustomUserDetailsService userDetailsService,
            PasswordEncoder passwordEncoder
    ) throws Exception {
        return http
                .getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder)
                .and()
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // disable CSRF only if you have no non-GET forms; otherwise remove this
                .csrf(csrf -> csrf.disable())

                // AUTHORIZE
                .authorizeHttpRequests(auth -> auth
                        // public pages
                        .requestMatchers(
                                "/login",
                                "/api/auth/register",
                                "/css/**",
                                "/js/**"
                        ).permitAll()
                        // everything else requires login
                        .anyRequest().authenticated()
                )

                // FORM LOGIN
                .formLogin(form -> form
                        .loginPage("/login")                   // GET /login → your login.html
                        .loginProcessingUrl("/perform_login")  // POST target for credentials
                        .successHandler(roleBasedSuccessHandler())
                        .failureUrl("/login?error")            // on failure
                        .permitAll()
                )

                // LOGOUT
                .logout(logout -> logout
                        .logoutUrl("/perform_logout")
                        .logoutSuccessUrl("/login")
                        .permitAll()
                );

        return http.build();
    }

    @Bean
    public AuthenticationSuccessHandler roleBasedSuccessHandler() {
        return new AuthenticationSuccessHandler() {
            @Override
            public void onAuthenticationSuccess(
                    HttpServletRequest request,
                    HttpServletResponse response,
                    Authentication authentication
            ) throws IOException, ServletException {
                String target = "/profile";
                for (GrantedAuthority ga : authentication.getAuthorities()) {
                    String role = ga.getAuthority();
                    if ("ROLE_ADMIN".equals(role)) {
                        target = "/dashboard/employee/vehicles";
                        break;
                    } else if ("ROLE_EMPLOYEE".equals(role)) {
                        target = "/dashboard/employee/vehicles";
                        break;
                    } else if ("ROLE_CUSTOMER".equals(role)) {
                        target = "/dashboard/customer/vehicles";
                        break;
                    }
                }
                response.sendRedirect(request.getContextPath() + target);
            }
        };
    }
}
