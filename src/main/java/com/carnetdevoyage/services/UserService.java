package com.carnetdevoyage.services;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.carnetdevoyage.models.Carnet;
import com.carnetdevoyage.models.CarnetItem;
import com.carnetdevoyage.models.CarnetItem.CarnetItemId;
import com.carnetdevoyage.models.Client;
import com.carnetdevoyage.models.User;
import com.carnetdevoyage.models.UserRole;
import com.carnetdevoyage.repositories.CarnetRepository;
import com.carnetdevoyage.repositories.UserRepository;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserService {
	
	private UserRepository userRepository;
	private CarnetRepository carnetRepository;
	
	public List<User> findAll(){
		return userRepository.findAll();
	}
	
	public Optional<User> findById(Long id){
		return userRepository.findById(id);
	}
	
	public User save(User user) {
		return userRepository.save(user);
	}
	
	public User update(User user) {
		if(user.getRole() == UserRole.CLIENT)
			((Client) user).setCarnetItems(((Client)userRepository.findById(user.getId()).get()).getCarnetItems());
			return userRepository.save(user);
	}
	
	public void deleteById(Long id) {
		userRepository.deleteById(id);
	}
	
	@Transactional
	public Collection<CarnetItem> getCarnet(Long id){
		User u = userRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Il n'existe pas d'utilisateur " + id));
		if(u.getRole() == UserRole.ADMIN)
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Les admins n'ont pas de carnet");
		else
			return ((Client)u).getCarnetItems();
	}
	
	@Transactional
	public Collection<CarnetItem> updateCarnet(CarnetItem carnetItem){
		Client c = carnetItem.getId().getClient();
		c.setCarnetItems(c.getCarnetItems().stream()
				.map(ci -> (ci.equals(carnetItem)) ? carnetItem : ci)
				.collect(Collectors.toSet()));
		return c.getCarnetItems();
	}
	
	@Transactional
	public Collection<CarnetItem> addCarnet(long userId, long carnetId){
		User u = userRepository.findById(userId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Il n'existe pas d'utilisateur avec l'id " + userId));
		Carnet carnet = carnetRepository.findById(carnetId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Il n'existe pas de carnet avec l'id " + carnetId));
		if (u.getRole() == UserRole.ADMIN)
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Les admins n'ont pas de carnet");
		((Client) u).getCarnetItems().add(CarnetItem.builder().id(new CarnetItemId((Client) u, carnet)).build());
		return ((Client) u).getCarnetItems();
	}

}
