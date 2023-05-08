package com.carnetdevoyage.repositories;


import org.springframework.data.jpa.repository.JpaRepository;

import com.carnetdevoyage.models.User;

public interface UserRepository extends JpaRepository<User, Long>{
	
	public User findByUsername(String name);

}
