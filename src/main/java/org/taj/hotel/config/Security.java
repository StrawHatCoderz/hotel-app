package org.taj.hotel.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.taj.hotel.filter.JwtAuthenticationFilter;
import org.taj.hotel.repositry.UserRepo;
import org.taj.hotel.service.AppUserDetailService;
import org.taj.hotel.service.IdGenerator;
import org.taj.hotel.service.JWTService;

import java.util.List;

@Configuration
public class Security {

  @Bean
  public JwtAuthenticationFilter jwtAuthenticationFilter(
          JWTService jwtService,
          AppUserDetailService appUserDetailService
  ) {

    return new JwtAuthenticationFilter(
            jwtService,
            appUserDetailService
    );
  }

  @Bean
  public SecurityFilterChain securityFilterChain(
          HttpSecurity http,
          JwtAuthenticationFilter jwtAuthenticationFilter
  ) throws Exception {

    http
            .csrf(csrf -> csrf.disable())
            .cors(Customizer.withDefaults())
            .sessionManagement(session ->
                    session.sessionCreationPolicy(
                            SessionCreationPolicy.STATELESS
                    )
            )
            .authorizeHttpRequests(auth ->
                    auth
                            .requestMatchers(
                                    "/api/users/register",
                                    "/api/users/login",
                                    "/api/search/*"
                            )
                            .permitAll()
                            .anyRequest()
                            .authenticated()
            )
            .addFilterBefore(
                    jwtAuthenticationFilter,
                    UsernamePasswordAuthenticationFilter.class
            );

    return http.build();
  }

  @Bean
  public AuthenticationManager authenticationManager(
          AuthenticationConfiguration config
  ) throws Exception {

    return config.getAuthenticationManager();
  }

  @Bean
  public AppUserDetailService appUserDetailService(
          UserRepo userRepo,
          PasswordEncoder passwordEncoder,
          IdGenerator idGenerator
  ) {

    return new AppUserDetailService(
            userRepo,
            passwordEncoder,
            idGenerator
    );
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public CorsConfigurationSource corsConfigurationSource() {

    CorsConfiguration cors = new CorsConfiguration();

    cors.setAllowedOrigins(
            List.of(
                    "http://localhost:3000",
                    "http://localhost:4000"
            )
    );

    cors.setAllowedMethods(
            List.of(
                    "GET",
                    "POST",
                    "PUT",
                    "DELETE",
                    "PATCH",
                    "OPTIONS"
            )
    );

    cors.setAllowedHeaders(List.of("*"));

    cors.setAllowCredentials(true);

    UrlBasedCorsConfigurationSource source =
            new UrlBasedCorsConfigurationSource();

    source.registerCorsConfiguration("/**", cors);

    return source;
  }
}