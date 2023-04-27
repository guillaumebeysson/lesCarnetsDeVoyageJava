package com.carnetdevoyage.models;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;


import com.carnetdevoyage.models.Client;
import com.carnetdevoyage.models.User;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
@NoArgsConstructor
@Entity
public class Client extends User{
	
	private String nationality;
	
	@OneToMany(mappedBy="id.client", cascade=CascadeType.ALL, orphanRemoval = true)
	@JsonIgnore
	private Set<CarnetItem> carnetItems;

}
