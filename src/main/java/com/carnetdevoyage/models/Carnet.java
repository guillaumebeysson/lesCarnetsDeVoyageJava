package com.carnetdevoyage.models;

import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@Entity
public class Carnet {
	
	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue
	private long id;
	
	@NotBlank
	private String title;
	
	@NotBlank
	private String introduction;
	
	@NotBlank
	private String description;
	
	@NotBlank
	private String picture1;
	
	@NotBlank
	private String picture2;
	
	@NotBlank
	private String picture3;
	
	@NotBlank
	private String country;
	
	@OneToMany(mappedBy="id.carnet")
	private Set<CarnetItem> carnetItems;

}