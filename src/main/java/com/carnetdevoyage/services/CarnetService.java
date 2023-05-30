package com.carnetdevoyage.services;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.ZonedDateTime;
import java.util.Collection;
import java.util.Optional;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.carnetdevoyage.models.Carnet;
import com.carnetdevoyage.repositories.CarnetRepository;

import jakarta.transaction.Transactional;
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
	
	public Collection<Carnet> findAllReverseOrder() {
	    Sort sort = Sort.by(Sort.Direction.DESC, "id");
	    return carnetRepository.findAll(sort);
	}
	
//	public Carnet save(Carnet carnet) {
//		return carnetRepository.save(carnet);
//	}
	
	
	public Carnet save(Carnet carnet) {
//		try {
			
		return	carnetRepository.save(carnet);
//		} catch (Throwable e) {
//			try {
//				if (c.getPicture1().equals(PostType.IMAGE))
//					Files.deleteIfExists(Paths.get("C:\\Users\\guill\\Pictures\\ImagesPostLesCarnetsDeVoyage" + ((ImagePost) c).getFile()));
//			} catch (Exception e1) {
//				e.addSuppressed(e1);
//				throw e;
//			}
//			throw e;
//		}
	}
	
	public Collection<Carnet> findByCountry(String country) {
        return carnetRepository.findByCountry(country);
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
			c.setCity(carnet.getCity());
			return carnetRepository.save(c);
		}).orElseThrow(()-> new RuntimeException("Carnet non trouvé !"));
	}
	
	public void deleteById(Long id) {
		System.out.println("ID du carnet à supprimer : " + id);
	    carnetRepository.deleteById(id);
	}

}
