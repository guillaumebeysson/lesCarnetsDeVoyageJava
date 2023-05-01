package com.carnetdevoyage.repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;

import com.carnetdevoyage.models.Carnet;

public interface CarnetRepository extends JpaRepository<Carnet, Long>{
	Collection<Carnet> findByCountry(String country);

}
