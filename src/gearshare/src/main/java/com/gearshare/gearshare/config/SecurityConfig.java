// package com.gearshare.gearshare.config;

// import com.gearshare.gearshare.security.OAuth2ClientService;
// import jakarta.servlet.http.HttpServletResponse;
// import lombok.RequiredArgsConstructor;
// import org.springframework.beans.factory.annotation.Value;
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.http.HttpStatus;
// import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
// import org.springframework.security.config.annotation.web.builders.HttpSecurity;
// import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
// import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
// import org.springframework.security.web.SecurityFilterChain;
// import org.springframework.security.web.authentication.HttpStatusEntryPoint;
// import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
// import org.springframework.web.cors.CorsConfiguration;
// import org.springframework.web.cors.CorsConfigurationSource;
// import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

// import java.util.List;

// import static org.springframework.security.config.Customizer.withDefaults;

// @Configuration
// @EnableWebSecurity
// @EnableMethodSecurity
// @RequiredArgsConstructor
// public class SecurityConfig {

//     /**
//      * For LOCAL, set this in application.properties:
//      * FRONTEND_URL=http://localhost:5173
//      *
//      * The ":http://localhost:5173" default makes it not crash if missing.
//      */
//     @Value("${FRONTEND_URL:http://localhost:5173}")
//     private String FRONTEND_URL;

//     private final OAuth2ClientService oAuth2ClientService;

//     @Bean
//     public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//         http
//                 .exceptionHandling(ex -> ex
//                         .defaultAuthenticationEntryPointFor(
//                                 new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED),
//                                 new AntPathRequestMatcher("/api/**")
//                         )
//                 )
//                 .csrf(AbstractHttpConfigurer::disable)
//                 .cors(withDefaults())
//                 .authorizeHttpRequests(authorize ->
//                                 authorize
//                                         .requestMatchers("/welcome", "/oauth2/**", "/login/**").permitAll()
//                                         .requestMatchers("/api/me").permitAll()
//                                         .anyRequest().authenticated()
//                 )
//                 .oauth2Login(oauth2 ->
//                         oauth2
//                                 .userInfoEndpoint(info -> info.userService(oAuth2ClientService))
//                                 .defaultSuccessUrl(FRONTEND_URL, true)
//                                 .failureHandler((request, response, exception) -> {
//                                 exception.printStackTrace();
//                                 response.sendRedirect("/login?error");
//                                 })
//                         )

//                 .logout(logout -> logout
//                         .logoutSuccessHandler((request, response, authentication) -> {
//                             response.setStatus(HttpServletResponse.SC_OK);
//                         })
//                         .invalidateHttpSession(true)
//                         .deleteCookies("JSESSIONID")
//                         .permitAll()
//                 );

//         return http.build();
//     }

//     @Bean
//     CorsConfigurationSource corsConfigurationSource(
//             @Value("${FRONTEND_URL:http://localhost:5173}") String frontendUrl
//     ) {
//         CorsConfiguration config = new CorsConfiguration();

//         // Allow your local Vite frontend (and whatever FRONTEND_URL is set to)
//         config.setAllowedOrigins(List.of(
//                 frontendUrl,
//                 "http://localhost:5173"
//         ));

//         // Include PATCH because you have PATCH endpoints
//         config.setAllowedMethods(List.of("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));

//         // With credentials, explicit headers are safer than "*"
//         config.setAllowedHeaders(List.of(
//                 "Authorization",
//                 "Content-Type",
//                 "X-Requested-With",
//                 "Accept",
//                 "Origin",
//                 "Cookie"
//         ));

//         // Helpful for debugging cookies (not required but nice)
//         config.setExposedHeaders(List.of("Set-Cookie"));

//         // REQUIRED for cookies (JSESSIONID)
//         config.setAllowCredentials(true);

//         // Optional: cache preflight for 1 hour
//         config.setMaxAge(3600L);

//         UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//         source.registerCorsConfiguration("/**", config);
//         return source;
//     }
// }



package com.gearshare.gearshare.config;

import com.gearshare.gearshare.security.OAuth2ClientService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.http.HttpStatus;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import static org.springframework.security.config.Customizer.withDefaults;

import java.util.List;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    @Value("${FRONTEND_URL}")
    private String FRONTEND_URL;

    private final OAuth2ClientService oAuth2ClientService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .exceptionHandling(ex -> ex
                    .defaultAuthenticationEntryPointFor(
                        new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED),
                        new AntPathRequestMatcher("/api/**")
                    )
                )
                .csrf(AbstractHttpConfigurer::disable)
                .cors(withDefaults())
                .authorizeHttpRequests(authorize ->
                                authorize
                                        .requestMatchers("/welcome", "/oauth2/**", "/login/**").permitAll()
                                        .requestMatchers("/api/me").permitAll()  
                                        .anyRequest().authenticated()
                )
                .oauth2Login(oauth2 ->
                        oauth2.userInfoEndpoint(info -> info
                                        .userService(oAuth2ClientService))
                                .defaultSuccessUrl(FRONTEND_URL, true)
                               )
                
                .logout(logout -> logout
                        .logoutSuccessHandler((request, response, authentication) -> {
                            response.setStatus(HttpServletResponse.SC_OK);
                        })
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                        .permitAll())

        ;
        return http.build();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource(
            @Value("${FRONTEND_URL}") String frontendUrl
    ) {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(List.of(
            frontendUrl,
            "http://localhost:5173"
        ));
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(List.of("*"));
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }
}


//, "/api/**" // Ova linija autorizira sve API zahtjeve i sluzi samo za development. Za production ju treba zakomentirati