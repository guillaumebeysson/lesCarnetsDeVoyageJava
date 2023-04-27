package com.carnetdevoyage.services;

import java.util.Collection;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.carnetdevoyage.models.Carnet;
import com.carnetdevoyage.repositories.CarnetRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CarnetService {
	
	private CarnetRepository carnetRepository;
	
	public Collection<Carnet> findAll(){
		return carnetRepository.findAll();
	}
	
	public Optional<Carnet> findById(Long id){
		return carnetRepository.findById(id);
	}
	
	public Carnet save(Carnet carnet) {
		return carnetRepository.save(carnet);
	}
	
	public Carnet updaCarnet(Carnet carnet) {
		return carnetRepository.save(carnet);
	}
	
	public void deleteById(Long id) {
		carnetRepository.deleteById(id);
	}

}
