package com.carnetdevoyage.controller;

import java.util.Collection;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.carnetdevoyage.models.CarnetItem;
import com.carnetdevoyage.models.Client;
import com.carnetdevoyage.models.User;
import com.carnetdevoyage.models.UserRole;
import com.carnetdevoyage.repositories.UserRepository;
import com.carnetdevoyage.services.UserService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
@Secured("SCOPE_ADMIN")
public class UserController {

    private UserService userService;
    /*private UserRepository userRepository;
    private PasswordEncoder encoder;

    @PostMapping("auth")
    public ResponseEntity<User> checkUser(@RequestBody User user) {
        System.out.println(user);
        var result = userRepository.findByUsername(user.getUsername());
        System.out.println(result);
        if (result == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        if (!encoder.matches(user.getPassword(), result.getPassword())) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        result.setPassword(user.getPassword());
        return new ResponseEntity<User>(result, HttpStatus.OK);
    }*/


    @GetMapping()
    public List<User> findAll() {
        return userService.findAll();
    }

    @GetMapping("/{id}")
    public User findById(@PathVariable long id) {
        return userService.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "no user with id " + id + " exists"));
    }

    @PostMapping()
    public User save(@Valid @RequestBody User user) {
        if (user.getId() != 0)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "a new user cannot have a non-null id");
        return userService.save(user);
    }

    @PutMapping("/{id}")
    public User update(@PathVariable long id, @Valid @RequestBody User user) {
        if (userService.findById(id).isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "no user with id " + id + " exists");
        else if (user.getId() != id)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ids in url and object do no match");
        return userService.update(user);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteById(@PathVariable Long id) throws InterruptedException {
        if (userService.findById(id).isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "no user with id " + id + " exists");
        userService.deleteById(id);
        return ResponseEntity.ok("L'utilisateur avec l'id " + id + " a bien été supprimé.");

    }

    @GetMapping("/{id}/carnets")
    public Collection<CarnetItem> getCarts(@PathVariable long id) {
        User u = userService.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "no user with id " + id + " exists"));
        if (u.getRole() == UserRole.ADMIN)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "admins do not have cart, you dummy");
        return ((Client) u).getCarnetItems();
    }
}
