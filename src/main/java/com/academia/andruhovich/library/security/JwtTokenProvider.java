package com.academia.andruhovich.library.security;

import com.academia.andruhovich.library.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Set;

@Component
public class JwtTokenProvider {

    @Value("springMyLove")
    private String secretKey;

    @Value("3600000")
    private long validityInMilliseconds;

    private final JwtUserDetailsService service;

    @Autowired
    public JwtTokenProvider(JwtUserDetailsService service) {        //@Qualifier("jwtUserDetailsService")
        this.service = service;
    }

    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

//    public String createToken(String email, Set<Role> roles) {
//        return null;
//    }
//
//    public String resolveToken(HttpServletRequest request) {
//        return null;
//    }
//
//    public boolean validateToken(String token) {
//        return false;
//    }
//
//    public Authentication getAuthentication(String token) {
//        return null;
//    }
}
