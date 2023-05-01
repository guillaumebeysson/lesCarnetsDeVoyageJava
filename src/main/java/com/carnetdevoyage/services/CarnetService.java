package com.carnetdevoyage.services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
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
	
	public Carnet updaCarnet(Long id, Carnet carnet) {
		return carnetRepository.findById(id)
		.map(c -> {
			c.setTitle(carnet.getTitle());
			c.setIntroduction(carnet.getIntroduction());
			c.setDescription(carnet.getDescription());
			c.setPicture1(carnet.getPicture1());
			c.setPicture2(carnet.getPicture2());
			c.setPicture3(carnet.getPicture3());
			c.setCountry(carnet.getCountry());
			return carnetRepository.save(c);
		}).orElseThrow(()-> new RuntimeException("Carnet non trouvé !"));
	}
	
	public String deleteById(Long id) {
		carnetRepository.deleteById(id);
		return "Carnet supprimé";
	}

}
