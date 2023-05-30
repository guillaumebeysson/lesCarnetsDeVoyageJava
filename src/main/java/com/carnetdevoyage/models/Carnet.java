package com.carnetdevoyage.models;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
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
	@Column(length = 600)
	private String introduction;
	
	@NotBlank
	@Column(length = 600)
	private String description;
	
	
	private String picture1;
	
	
	private String picture2;
	
	
	private String picture3;
	
	@NotBlank
	private String country;
	
	
	private int durationTrip;
	
	private String departurePeriod;
	
	private String organisation;
	
	private String situation;
	
	private String transport;
	
	private String city;
	
    private LocalDate date;
	
	@OneToMany(mappedBy="id.carnet")
	private Set<CarnetItem> carnetItems;

}
