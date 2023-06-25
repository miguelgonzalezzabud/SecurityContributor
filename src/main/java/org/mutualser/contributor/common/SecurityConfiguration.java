/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.mutualser.contributor.common;


import java.time.Duration;
import java.util.Arrays;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import static org.springframework.security.config.Customizer.withDefaults;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;


/**
 *
 * @author MGonzalez
 */
@Configuration
@EnableWebSecurity
public class SecurityConfiguration  {
    
    //Configuración de usuario, contraseña y rol en memoria
     @Bean
    public InMemoryUserDetailsManager userDetailsService() {
        UserDetails user = User.withDefaultPasswordEncoder()
            .username("aportante")
            .password("aportante")
            .roles("aportante")
            .build();
        return new InMemoryUserDetailsManager(user);
    }
        
     @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    // Autenticación basica sin configuración adicional
    /*http.csrf().disable()
            .authorizeHttpRequests()
            .anyRequest()
            .authenticated()
            .and()
            .httpBasic(withDefaults())
            .formLogin(withDefaults());   
  
        return http.build();
    */    
        http
            .authorizeHttpRequests((authz) -> authz
             //urls sin autenticación
            .requestMatchers(
            "/v3/api-docs/**",
            "/configuration/ui", 
            "/swagger-resources/**", 
            "/configuration/security", 
            "/webjars/**", 
            "/login/**",
            "/swagger-ui/**",
            "/h2-console/**").permitAll()

             //Cualquier petición POST que traigan en su ruta affiliates necesitan autenticación
            .requestMatchers(HttpMethod.POST, "/*affiliates*/**").authenticated()
             //Cualquier petición GET que traigan en su ruta affiliates necesitan autenticación con el ROL especifico
            .requestMatchers(HttpMethod.GET,"/*affiliates*/**").hasRole("aportante") 
            )
            .httpBasic(withDefaults())
            .formLogin(withDefaults());
 
        return http.build();
    }
    
      
    
    
       //Configuración basica de origenes cruzados
    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration cc = new CorsConfiguration();
        //Cabeceras http, donde viaja información de autentificación
        cc.setAllowedHeaders(Arrays.asList("Origin,Accept", "X-Requested-With", "Content-Type", "Access-Control-Request-Method", "Access-Control-Request-Headers", "Authorization"));
        cc.setExposedHeaders(Arrays.asList("Access-Control-Allow-Origin", "Access-Control-Allow-Credentials"));
        
        //Origenes, en este caso se deja habilitado cualquier origen
        cc.setAllowedOrigins(Arrays.asList("/*"));
        cc.setAllowedMethods(Arrays.asList("GET", "POST", "OPTIONS", "PUT", "PATCH"));
        cc.addAllowedOriginPattern("*");  
        
        cc.setMaxAge(Duration.ZERO);
        cc.setAllowCredentials(Boolean.TRUE);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", cc);
        return source;
    }
}
