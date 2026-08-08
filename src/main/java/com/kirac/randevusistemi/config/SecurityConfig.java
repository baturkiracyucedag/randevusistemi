package com.kirac.randevusistemi.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.kirac.randevusistemi.security.CustomAccessDeniedHandler;
import com.kirac.randevusistemi.security.CustomAuthenticationEntryPoint;
import com.kirac.randevusistemi.security.JwtAuthenticationFilter;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http,
            JwtAuthenticationFilter jwtAuthenticationFilter,
            CustomAuthenticationEntryPoint authenticationEntryPoint,
            CustomAccessDeniedHandler accessDeniedHandler)
            throws Exception {

        http
                .csrf(csrf -> csrf.disable())

                .cors(cors ->
                        cors.configurationSource(
                                corsConfigurationSource()))

                .sessionManagement(session ->
                        session.sessionCreationPolicy(
                                SessionCreationPolicy.STATELESS))

                .exceptionHandling(exception ->
                        exception
                                .authenticationEntryPoint(
                                        authenticationEntryPoint)
                                .accessDeniedHandler(
                                        accessDeniedHandler))

                .authorizeHttpRequests(auth -> auth

                        // Giriş gerektirmeyen adresler
                        .requestMatchers(
                                "/swagger-ui/**",
                                "/swagger-ui.html",
                                "/v3/api-docs/**",
                                "/api/auth/**",
                                "/error")
                        .permitAll()

                        // Kullanıcı yönetimi
                        .requestMatchers(
                                "/api/kullanicilar/**")
                        .hasRole("YONETICI")

                        // Personel görüntüleme
                        .requestMatchers(
                                HttpMethod.GET,
                                "/api/personeller/**")
                        .hasAnyRole(
                                "YONETICI",
                                "KULLANICI")

                        // Personel yönetimi
                        .requestMatchers(
                                HttpMethod.POST,
                                "/api/personeller/**")
                        .hasRole("YONETICI")

                        .requestMatchers(
                                HttpMethod.PUT,
                                "/api/personeller/**")
                        .hasRole("YONETICI")

                        .requestMatchers(
                                HttpMethod.DELETE,
                                "/api/personeller/**")
                        .hasRole("YONETICI")

                        // Hizmet görüntüleme
                        .requestMatchers(
                                HttpMethod.GET,
                                "/api/hizmetler/**")
                        .hasAnyRole(
                                "YONETICI",
                                "KULLANICI")

                        // Hizmet yönetimi
                        .requestMatchers(
                                HttpMethod.POST,
                                "/api/hizmetler/**")
                        .hasRole("YONETICI")

                        .requestMatchers(
                                HttpMethod.PUT,
                                "/api/hizmetler/**")
                        .hasRole("YONETICI")

                        .requestMatchers(
                                HttpMethod.DELETE,
                                "/api/hizmetler/**")
                        .hasRole("YONETICI")

                        // Müşteri yönetimi
                        .requestMatchers(
                                "/api/musteriler/**")
                        .hasRole("YONETICI")

                        // Randevu oluşturma
                        .requestMatchers(
                                HttpMethod.POST,
                                "/api/randevular")
                        .hasAnyRole(
                                "YONETICI",
                                "KULLANICI")

                        // Müsait saat sorguları
                        .requestMatchers(
                                HttpMethod.GET,
                                "/api/randevular/musait-saatler",
                                "/api/randevular/saat-durumlari")
                        .hasAnyRole(
                                "YONETICI",
                                "KULLANICI")

                        // Normal kullanıcı kendi randevularını görebilir
                        .requestMatchers(
                                HttpMethod.GET,
                                "/api/randevular/benim")
                        .hasRole("KULLANICI")

                        // Normal kullanıcı kendi randevusunu iptal edebilir
                        .requestMatchers(
                                HttpMethod.PUT,
                                "/api/randevular/benim/*/iptal")
                        .hasRole("KULLANICI")

                        // Diğer randevu işlemleri yalnızca yönetici
                        .requestMatchers(
                                "/api/randevular/**")
                        .hasRole("YONETICI")

                        // Diğer bütün adreslerde giriş zorunlu
                        .anyRequest()
                        .authenticated())

                .addFilterBefore(
                        jwtAuthenticationFilter,
                        UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(
            UserDetailsService userDetailsService,
            PasswordEncoder passwordEncoder) {

        DaoAuthenticationProvider authenticationProvider =
                new DaoAuthenticationProvider(
                        userDetailsService);

        authenticationProvider.setPasswordEncoder(
                passwordEncoder);

        return new ProviderManager(
                authenticationProvider);
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {

        CorsConfiguration configuration =
                new CorsConfiguration();

        configuration.setAllowedOrigins(
                List.of(
                        "http://localhost:5173"));

        configuration.setAllowedMethods(
                List.of(
                        "GET",
                        "POST",
                        "PUT",
                        "DELETE",
                        "OPTIONS"));

        configuration.setAllowedHeaders(
                List.of("*"));

        configuration.setExposedHeaders(
                List.of(
                        "Authorization"));

        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source =
                new UrlBasedCorsConfigurationSource();

        source.registerCorsConfiguration(
                "/**",
                configuration);

        return source;
    }
}