package com.carnetdevoyage.controller;

import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.carnetdevoyage.UserDto;
import com.carnetdevoyage.services.JwtService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@CrossOrigin
@RestController
@AllArgsConstructor
@Slf4j
public class JwtController {

	private JwtService jwtService;
    private AuthenticationManager authenticationManager;

    @PostMapping("/token")
    public Map<String, String> getToken(@RequestBody UserDto user) {
        System.out.println(user);
        if (user.getGrantType().equals("password")) {
            Authentication authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
            String roles = authentication.getAuthorities()
                    .stream()
                    .map(elt -> elt.getAuthority())
                    .collect(Collectors.joining(" "));
            log.info("Génération de token pour {}", user.getUsername());
            return jwtService.generateTokens(authentication.getName(), roles);
        }else if(user.getGrantType().equals("refreshToken") || user.getGrantType().equals("refresh-token")){
            log.info("Rafraichissement de token pour {}", user.getUsername());
            return jwtService.generateFromRefreshToken(user);
        }
        else {
            log.info("Utilisateur non identifié {}", user);
            return null;
        }

    }

}
