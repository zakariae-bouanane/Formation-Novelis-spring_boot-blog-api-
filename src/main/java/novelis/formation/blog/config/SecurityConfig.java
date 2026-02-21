package novelis.formation.blog.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .csrf(csrf -> csrf.disable()) // APIs don't use CSRF
                .authorizeHttpRequests(auth -> auth
                                .requestMatchers("/api/auth/login").permitAll()
//                        .anyRequest().authenticated()
                        .anyRequest().permitAll()
                )
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS) // making requests stateless, so it doesnt change anything in server (independent requests)
                )
                .formLogin(form -> form.disable())
                .httpBasic(basic -> basic.disable())
                .logout(logout -> logout.disable())

                // set up for JWT with keycloack
                .oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults()))

                /// CORS
                    .cors(cors -> {
                    CorsConfiguration config = new CorsConfiguration();

                    // ✅ allowed frontend origin (React)
                    config.setAllowedOrigins(List.of("http://localhost:3001"));

                    // ✅ allowed HTTP methods
                    config.setAllowedMethods(
                            List.of("GET", "POST", "PUT", "DELETE", "OPTIONS")
                    );

                    // ✅ allowed headers from frontend
                    config.setAllowedHeaders(List.of("*"));

                    // ✅ allow cookies / credentials (important for auth)
                    config.setAllowCredentials(true);

                    // 🔗 map this config to URL patterns
                    UrlBasedCorsConfigurationSource source =
                            new UrlBasedCorsConfigurationSource();
                    source.registerCorsConfiguration("/**", config);

                    // 🚨 VERY IMPORTANT
                    // tell Spring Security to use this CORS source
                    cors.configurationSource(source);
                });

        return http.build();
    }

}
