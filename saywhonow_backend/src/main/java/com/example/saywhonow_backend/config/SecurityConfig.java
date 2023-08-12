package com.example.saywhonow_backend.config;

//spring security tutorial
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import com.example.saywhonow_backend.domain.User;
import org.springframework.context.annotation.Bean;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        return http
            .csrf( csrf -> csrf.disable())
            .authorizeHttpRequests( auth -> auth.anyRequest().permitAll())
            .build();
    }


    // 	@Bean
	//     public UserDetailsService userDetailsService() {
	// 	UserDetails user =
	// 		 User.withDefaultPasswordEncoder()
	// 			.setUsername("user")
	// 			.setPassword("password")
	// 			.setAuthorites("USER")
	// 			.build();

	// 	return new InMemoryUserDetailsManager(user);
	// }

    
}
