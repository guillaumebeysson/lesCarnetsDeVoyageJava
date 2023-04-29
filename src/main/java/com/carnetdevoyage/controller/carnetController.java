package com.carnetdevoyage.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.carnetdevoyage.models.Carnet;
import com.carnetdevoyage.services.CarnetService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/carnets")
@AllArgsConstructor
public class carnetController {

	private final CarnetService carnetService;
	
	@PostMapping("/createCarnet")
	public Carnet create(@RequestBody Carnet carnet) {
		return carnetService.save(carnet);
	}
	
	@GetMapping("")
	public Collection<Carnet> read(){
		return carnetService.findAll();
	}
	
	@GetMapping("/{id}")
	public Optional<Carnet> readById(@PathVariable Long id){
		return carnetService.findById(id);
	}
	
	@GetMapping("/lastFour")
	public Collection<Carnet> readLastFour(){
		List<Carnet> carnets = new ArrayList<>(carnetService.findAll());
	    int startIndex = Math.max(0, carnets.size() - 4);
	    return carnets.subList(startIndex, carnets.size());
	}
	
	@PutMapping("/update/{id}")
	public Carnet update(@PathVariable Long id, @RequestBody Carnet carnet) {
		return carnetService.updaCarnet(id, carnet);
	}
	
	@DeleteMapping("/delete/{id}")
	public String delete(@PathVariable Long id) {
		return carnetService.deleteById(id);
	}
}
