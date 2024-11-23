//package com.example.demo.configuration;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.AuthenticationProvider;
//import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
//import org.springframework.security.config.Customizer;
//import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//import org.springframework.web.cors.CorsConfiguration;
//import org.springframework.web.cors.CorsConfigurationSource;
//import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
//
////@Configuration
////@EnableWebSecurity
////public class SecurityConfig {
////
////    @Autowired
////    private UserDetailsService userDetailsService;
////
////    @Autowired
////    private JwtFilter jwtFilter;
////
//////    @Bean
//////    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//////        return http.csrf(customizer -> customizer.disable())
//////                .authorizeHttpRequests(request ->
//////                        request.requestMatchers("restaurant/register", "restaurant/login, /v2/api-docs", "/v3/api-docs", "/swagger-resources/**", "/swagger-ui/**", "/webjars/**")
//////                                .permitAll()
//////                                .anyRequest()
//////                                .authenticated())
//////                .httpBasic(Customizer.withDefaults())
//////                .sessionManagement(session ->
//////                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//////                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
//////                .build();
//////    }
////@Bean
////public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
////    return http.csrf(csrf -> csrf.disable())
////            .authorizeHttpRequests(auth -> auth
////                    .requestMatchers("/v2/api-docs", "/v3/api-docs", "/swagger-resources/**", "/swagger-ui/**", "/webjars/**")
////                    .permitAll()
////                    .requestMatchers("restaurant/register", "restaurant/login")
////                    .permitAll()
////                    .anyRequest()
////                    .authenticated())
////            .httpBasic(Customizer.withDefaults())
////            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
////            .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
////            .build();
////}
////
////    @Bean
////    public AuthenticationProvider authenticationProvider() {
////        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
////        provider.setPasswordEncoder(new BCryptPasswordEncoder(12));
////        provider.setUserDetailsService(userDetailsService);
////        return provider;
////    }
////
////    @Bean
////    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
////        return config.getAuthenticationManager();
////    }
//////
////
////}
//@Configuration
//@EnableWebSecurity
//public class SecurityConfig {
//
//    @Autowired
//    private UserDetailsService userDetailsService;
//
//    @Autowired
//    private JwtFilter jwtFilter;
//
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        return http.csrf(csrf -> csrf.disable())
//                .cors(Customizer.withDefaults()) // Enable CORS globally
//                .authorizeHttpRequests(auth -> auth
//                        // Whitelist Swagger UI and API docs paths
//                        .requestMatchers("/swagger-ui/**", "/swagger-resources/**", "/v2/api-docs", "/v3/api-docs", "/webjars/**")
//                        .permitAll() // Allow these without authentication
//                        // Whitelist authentication-related paths (register/login)
//                        .requestMatchers("/restaurant/register", "/restaurant/login")
//                        .permitAll()
//                        // Secure all other endpoints (require authentication)
//                        .requestMatchers("/actuator/**").permitAll()
//                        .anyRequest()
//                        .authenticated())
//                .httpBasic(Customizer.withDefaults()) // Allow basic authentication
//                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // Stateless session
//                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class) // Add JWT filter
//                .build();
//    }
//
//    @Bean
//    public AuthenticationProvider authenticationProvider() {
//        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
//        provider.setPasswordEncoder(new BCryptPasswordEncoder(12));
//        provider.setUserDetailsService(userDetailsService);
//        return provider;
//    }
//
//    @Bean
//    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
//        return config.getAuthenticationManager();
//    }
//    // Optional: Customize CORS settings if needed
//    @Bean
//    public CorsConfigurationSource corsConfigurationSource() {
//        CorsConfiguration configuration = new CorsConfiguration();
//        configuration.addAllowedOrigin("*"); // Allow all origins (adjust as necessary)
//        configuration.addAllowedMethod("*"); // Allow all methods (GET, POST, etc.)
//        configuration.addAllowedHeader("*"); // Allow all headers
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/**", configuration); // Apply globally
//        return source;
//    }
//}
