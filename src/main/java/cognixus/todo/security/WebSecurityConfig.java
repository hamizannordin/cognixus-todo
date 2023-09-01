package cognixus.todo.security;

import cognixus.todo.service.GoogleUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 *
 * @author hamizan
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Autowired
    private GoogleUserService userService;
    @Autowired
    private CustomAuthenticationSuccessHandler customSuccessHandler;
    //@Autowired
    //private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    @Autowired
    private UserDetailsService jwtUserDetailsService;
    @Autowired
    private JwtAuthenticationFilter jwtRequestFilter;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        // configure AuthenticationManager so that it knows from where to load
        // user for matching credentials
        // Use BCryptPasswordEncoder
        auth.userDetailsService(jwtUserDetailsService).passwordEncoder(new BCryptPasswordEncoder());
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .build();
    }

    // google sign-in
    @Bean
    public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http)
            throws Exception {
        http.csrf(c -> c.disable()).authorizeHttpRequests(authorize
                -> authorize
                        .requestMatchers(HttpMethod.POST, "/todo/detail").permitAll()
                        .requestMatchers("/auth/token").permitAll()
                        .requestMatchers("/auth/authenticate").permitAll()
                        .requestMatchers("/login/***").permitAll()
                        .anyRequest().authenticated()
        )
                // OAuth2 Login handles the redirect to the OAuth 2.0 Login endpoint
                // from the authorization server filter chain
                .oauth2Login(//withDefaults());
                        (o) -> {
                            o.userInfoEndpoint((user) -> user.oidcUserService(userService));
                            o.successHandler(customSuccessHandler);
                        });
        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class)
                //.exceptionHandling(e -> e.authenticationEntryPoint(jwtAuthenticationEntryPoint))
                .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        return http.build();
    }
}
