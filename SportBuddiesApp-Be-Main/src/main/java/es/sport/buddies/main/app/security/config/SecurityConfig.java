package es.sport.buddies.main.app.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import es.sport.buddies.main.app.filter.JwtAuthenticationFilter;
import jakarta.servlet.http.HttpServletResponse;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

  @Bean
  AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
      throws Exception {
    return authenticationConfiguration.getAuthenticationManager();
  }

  @Bean
  static BCryptPasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  } 
      
  @Bean
  SecurityFilterChain filterChain(HttpSecurity http, JwtAuthenticationFilter authenticationFilter) throws Exception {
    return http
    .csrf(csrf -> csrf.disable())
    .authorizeHttpRequests(authorize -> authorize
        .requestMatchers("/reservaActividad/listarReserva",
            "/reservaActividad/comboInicio",
            "/reservaActividad/listadoMunicipios",
            "/reservaActividad/listadoReserva",
            "/borrarCookie",
            "/swagger-ui/**",
            "/api-docs/**",
            "/estado/pago",
            "/usuario/crear").permitAll()
        .anyRequest().authenticated())
    .formLogin(login -> login.disable())
    .httpBasic(basic -> basic.disable()) 
    .exceptionHandling(exceptions -> exceptions.authenticationEntryPoint((request, response, authException) 
        -> response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized")))
    .addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class)
    .build();
  }
}
