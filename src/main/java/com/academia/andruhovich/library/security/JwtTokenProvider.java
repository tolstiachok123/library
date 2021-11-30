package com.academia.andruhovich.library.security;

import com.academia.andruhovich.library.model.Authority;

import com.academia.andruhovich.library.model.Role;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Component
public class JwtTokenProvider {

    private static final String BEARER = "Bearer ";
    private static final String JWT_REGEXP = "Bearer\\s[\\w-]*\\.[\\w-]*\\.[\\w-]*";

    @Value("${jwt.token.secret}")
    private String secretKey;

    @Value("${jwt.token.expired}")
    private long validityInMilliseconds;

    private final JwtUserDetailsService service;

    @Autowired
    public JwtTokenProvider(JwtUserDetailsService service) {
        this.service = service;
    }

    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    public String createToken(String email, Set<Role> roles) {
        Claims claims = Jwts.claims().setSubject(email);
        claims.put("roles", this.getRoleNames(roles));

        Date now = new Date();
        Date validity = new Date(now.getTime() + validityInMilliseconds);

        return Jwts.builder()//
                .setClaims(claims)//
                .setIssuedAt(now)//
                .setExpiration(validity)//
                .signWith(SignatureAlgorithm.HS256, secretKey)//
                .compact();
    }

    public String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (bearerToken == null) {
            return null;
        }

        if (!bearerToken.matches(JWT_REGEXP)) {
            throw new BadCredentialsException(String.format("JWT token is not matching: '%s'", JWT_REGEXP));
        }

        return bearerToken.startsWith(BEARER) ? bearerToken.substring(7) : null;
    }

    public boolean validateToken(String token) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            return !claims.getBody().getExpiration().before(new Date());
        } catch (JwtException | IllegalArgumentException e) {
            throw new BadCredentialsException("JWT token is expired or invalid.");
        }
    }

    public Authentication getAuthentication(String token) {
        try {
            UserDetails userDetails = service.loadUserByUsername(getUsername(token));
            return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
        } catch (Exception e) {
            throw new BadCredentialsException("JWT token invalid.");
        }
    }

    public String getUsername(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    }

    private Set<String> getRoleNames(Set<Role> userRoles) {
        Set<String> rolesNames = new HashSet<>();
        if (userRoles == null) {
            return rolesNames;
        }

        userRoles.forEach(role -> {
            Set<Authority> authorities = role.getAuthorities();
            authorities.forEach(authority -> rolesNames.add(authority.getName()));
            rolesNames.add(role.getName());
        });

        return rolesNames;
    }
}
