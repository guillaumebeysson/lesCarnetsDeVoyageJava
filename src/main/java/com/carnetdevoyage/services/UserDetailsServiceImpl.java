package com.carnetdevoyage.services;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.carnetdevoyage.repositories.UserRepository;
import com.carnetdevoyage.models.User;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username); 
        if(user == null) {
            throw new UsernameNotFoundException("Aucun utilisateur trouvé avec la valeur " + username);
        }
        return new UserDetailsImpl(user);
    }

}
