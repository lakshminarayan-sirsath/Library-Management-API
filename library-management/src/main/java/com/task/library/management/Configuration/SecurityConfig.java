package com.task.library.management.Configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.task.library.management.JWT_filter.JwtFilter;
import com.task.library.management.RollBasedAuthentication.CustomUserDetailsService;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private CustomUserDetailsService userDetailsService;
    
    @Autowired
    private JwtFilter jwtFilter;
    
    private static final String[] SWAGGER_ENDPOINTS = {
    	    "/swagger-ui/**",          // Swagger UI resources
    	    "/swagger-ui.html",        // Main Swagger UI page
    	    "/v3/api-docs/**",         // OpenAPI 3.0 specification documents
    	    "/swagger-resources/**",   // Swagger resources
    	    "/webjars/**",             // Static resources used by Swagger UI
    	    "/configuration/ui",       // Swagger UI configuration endpoint
    	    "/configuration/security"  // Swagger security configuration endpoint
    	};
    
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable()) // Cross-Site Request Forgery
                .authorizeHttpRequests(requests -> requests
                		// swagger endpoints
                		.requestMatchers(SWAGGER_ENDPOINTS).permitAll()
                		// For All
                        .requestMatchers("/user/register", "/user/login", "/user/logout").permitAll() // Permit register and login for all
        
                        // ADMIN
                        .requestMatchers("/**").hasRole("ADMIN")
                        
                        // MEMBER
                        .requestMatchers("/borrowing_record/view_br_records/**").hasRole("MEMBER")
                        .requestMatchers("/book/search_by_title/**").hasRole("MEMBER")
                        
                        // LIBRARIAN
                        .requestMatchers("/book/add_book", "/book/select_all", "/book/select_by_id/**",
                        		 "/book/delete_by_id/**", "/book/update_book", "/book/change_book_status/**", 
                        		 "/book/select_all_pagination/**").hasRole("LIBRARIAN")
                        
                        .requestMatchers("/catalog/add_catalog", "/catalog/get_catalog_category", 
                        		"/catalog/add_book_catalog/**", "/catalog/all_categories").hasRole("LIBRARIAN")
                        
                        .requestMatchers("/borrowing_record/borrow_book", "/borrowing_record/return_book/**", 
                        		"/borrowing_record/view_br_records/**").hasRole("LIBRARIAN")
                        
                        // VISITOR
                        .requestMatchers("/member/join_membership").hasAnyRole("VISITOR")
                        
                        .requestMatchers("/catalog/get_catalog_category", "/catalog/all_categories").hasRole("VISITOR")
                        .anyRequest().authenticated())
                		.sessionManagement(session -> session
                				.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                		.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
                		
//                .formLogin(login -> login
//                        .loginPage("/login")
//                        .permitAll())
//                .logout(logout -> logout
//                        .permitAll());

        return http.build();
    }
    
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

//    @Bean
//    public UserDetailsService userDetailsService() {
//        return userDetailsService;
//    }
    
    
  @Bean
  public DaoAuthenticationProvider authenticationProvider() {
      DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
      authProvider.setUserDetailsService(userDetailsService);
      authProvider.setPasswordEncoder(passwordEncoder());
      return authProvider;
  }

  @Bean
  public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
      AuthenticationManagerBuilder authManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
      authManagerBuilder.authenticationProvider(authenticationProvider());
      return authManagerBuilder.build();
  }
   
}



