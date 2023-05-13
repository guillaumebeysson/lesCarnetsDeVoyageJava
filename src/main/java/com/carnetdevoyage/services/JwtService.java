package com.carnetdevoyage.services;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import com.carnetdevoyage.UserDto;

import lombok.AllArgsConstructor;


@Service
@AllArgsConstructor
public class JwtService {
    private JwtEncoder jwtEncoder;
    private JwtDecoder jwtDecoder;
    private UserDetailsServiceImpl userDetailsServiceImpl;

    public Map<String, String> generateToken(String username, String roles){
        JwtClaimsSet jwtClaims = JwtClaimsSet.builder()
                .issuedAt(Instant.now())
                .expiresAt(Instant.now().plus(1, ChronoUnit.HOURS))
                .issuer("spring-security-oauth")
                .subject(username)
                .claim("scope", roles)
                .build();
        var token = jwtEncoder.encode(JwtEncoderParameters.from(jwtClaims)).getTokenValue();
        Map<String, String> idToken = new HashMap<>();
        idToken.put("accessToken", token);
        return idToken;
    }
    
    public Map<String, String> generateTokens(String username, String roles){
        var idToken = generateToken(username, roles);
        JwtClaimsSet jwtClaims = JwtClaimsSet.builder()
                .issuedAt(Instant.now())
                .expiresAt(Instant.now().plus(60, ChronoUnit.MINUTES))
                .issuer("spring-security-oauth")
                .subject(username)
                .build();
        var token = jwtEncoder.encode(JwtEncoderParameters.from(jwtClaims)).getTokenValue();
        idToken.put("refreshToken", token);
        return idToken;
    }

    public Map<String, String> generateFromRefreshToken(UserDto user) {
        var decodedJwt = jwtDecoder.decode(user.getRefreshToken());
        var nom = decodedJwt.getSubject();
        var connected = userDetailsServiceImpl.loadUserByUsername(nom);
        var roles = connected.getAuthorities().stream().map(elt -> elt.getAuthority())
                .collect(Collectors.joining(" "));
        return generateTokens(nom, roles);
    }

}