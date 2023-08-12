package com.example.saywhonow_backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.config.Customizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.SecurityFilterChain;
import static org.springframework.security.config.Customizer.withDefaults;

import com.example.saywhonow_backend.User;

@Configuration
public class SecurityConfig {
    
    // @Autowired
    // private UserDetailsService userDetailsService;
    
    // @Autowired
    // public void configure(AuthenticationManagerBuilder auth) throws Exception {
    //     // auth
    //     // .userDetailsService(userDetailsService);
      
    //     auth
    //     .ldapAuthentication()
    //       .userDnPatterns("uid={0},ou=people")
    //       .groupSearchBase("ou=groups")
    //       .contextSource()
    //         .url("ldap://localhost:8389/dc=springframework,dc=org")
    //         .and()
    //       .passwordCompare()
    //         .passwordEncoder(new BCryptPasswordEncoder())
    //         .passwordAttribute("userPassword");
    // }

    
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests((authz) -> authz
                .anyRequest().authenticated()
            )
            .httpBasic(withDefaults());
        return http.build();
    }


    	@Bean
	    public UserDetailsService userDetailsService() {
		UserDetails user =
			 User.withDefaultPasswordEncoder()
				.setUsername("user")
				.setPassword("password")
				.setAuthorites("USER")
				.build();

		return new InMemoryUserDetailsManager(user);
	}

    
}
