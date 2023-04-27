package com.carnetdevoyage.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.carnetdevoyage.models.Carnet;

public interface CarnetRepository extends JpaRepository<Carnet, Long>{

}
