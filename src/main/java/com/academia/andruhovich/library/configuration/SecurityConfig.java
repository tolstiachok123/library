package com.academia.andruhovich.library.configuration;

import com.academia.andruhovich.library.security.JwtAuthenticationEntryPoint;
import com.academia.andruhovich.library.security.JwtConfigurer;
import com.academia.andruhovich.library.security.JwtTokenFilter;
import com.academia.andruhovich.library.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(
        securedEnabled = true,
        jsr250Enabled = true,
        prePostEnabled = true
)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private static final String LOGIN_ENDPOINT = "/api/auth/login";
    private static final String REGISTRATION_ENDPOINT = "/api/auth/registration";

    private final JwtTokenProvider jwtTokenProvider;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtTokenFilter jwtTokenFilter;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }

    @Bean(BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .httpBasic().disable()
//                .addFilterBefore(myCorsFilter(), WebAsyncManagerIntegrationFilter.class)
//                .cors().and()
                .csrf().disable()
                .addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class)
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) //guarantee that the application will not create any session at all
                .and()
                .authorizeRequests()
                .antMatchers(LOGIN_ENDPOINT).permitAll()
                .antMatchers(REGISTRATION_ENDPOINT).permitAll()
                .antMatchers("/api/auth/test").permitAll()
                .anyRequest().authenticated()
                .and()
                .apply(new JwtConfigurer(jwtTokenProvider, jwtAuthenticationEntryPoint));
    }
}
