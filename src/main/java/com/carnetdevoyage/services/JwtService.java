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

    public Map<String, String> generateToken(String username, Long id, String roles){
        JwtClaimsSet jwtClaims = JwtClaimsSet.builder()
                .issuedAt(Instant.now())
                .expiresAt(Instant.now().plus(50, ChronoUnit.MINUTES))
                .issuer("spring-security-oauth")
                .subject(username)
                .claim("id", id)
                .claim("scope", roles)
                .build();
        var token = jwtEncoder.encode(JwtEncoderParameters.from(jwtClaims)).getTokenValue();
        Map<String, String> idToken = new HashMap<>();
        idToken.put("accessToken", token);
        return idToken;
    }
    
    public Map<String, String> generateTokens(String username, Long id, String roles){
        var idToken = generateToken(username, id, roles);
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
        var idString = decodedJwt.getClaimAsString("id");
        var id = Long.parseLong(idString);
        var connected = userDetailsServiceImpl.loadUserByUsername(nom);
        var roles = connected.getAuthorities().stream().map(elt -> elt.getAuthority())
                .collect(Collectors.joining(" "));
        return generateTokens(nom, id, roles);
    }

}